package com.service.concurrencyprac.payment.controller;

import com.service.concurrencyprac.auth.domain.member.Member;
import com.service.concurrencyprac.common.response.CommonResponse;
import com.service.concurrencyprac.payment.dto.OrderInfoDto;
import com.service.concurrencyprac.payment.dto.PaymentResultDto;
import com.service.concurrencyprac.payment.facade.PaymentFacade;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentFacade paymentFacade;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/checkout")
    public CommonResponse getCheckoutData(@RequestParam Long orderId) {
        OrderInfoDto orderInfoDto = paymentFacade.getOrderInfo(orderId);
        return CommonResponse.success(orderId);
    }

    /*토스의 결제 - 인증 + 승인
     * 1.인증:결제 해당 고객이며 지불능력이 있는가
     * 2.승인: 결제처리 승인절차
     * 토스는 클라이언트에서 우리서버를 다시 호출하도록 구현되어있음.
     * 하기의 경우 승인처리컨트롤러의 예시.
     * @param 요청처리데이터에 대해 결제처리 결과 응답데이터*/
    @RequestMapping(value = "/confirm")
    public CommonResponse<PaymentResultDto> confirmPayment(@RequestBody String jsonBody,
        @AuthenticationPrincipal
        Member member) throws Exception {

        PaymentResultDto result = new PaymentResultDto();
        JSONParser parser = new JSONParser();
        String orderId;
        String amount;
        String paymentKey;
        try {
            JSONObject requestData = (JSONObject) parser.parse(jsonBody);
            paymentKey = (String) requestData.get("paymentKey");
            orderId = (String) requestData.get("orderId");
            amount = (String) requestData.get("amount");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        ;

        //요청정보 조작방지를 위해 확인.
        OrderInfoDto orderInfo = paymentFacade.getOrderInfo(Long.parseLong(orderId));
        if (Integer.parseInt(amount) != orderInfo.getTotalPrice()) {
            throw new Exception("주문 정보가 상이합니다.");
        }

        //결제준비단계
        paymentFacade.prepareOrder(Long.parseLong(orderId));
        JSONObject obj = new JSONObject();
        obj.put("orderId", orderId);
        obj.put("amount", amount);
        obj.put("paymentKey", paymentKey);

        String widgetSecretKey = "test_gsk_docs_OaPz8L5KdmQXkzRz3y47BMw6";
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode((widgetSecretKey + ":").getBytes("UTF-8"));
        String authorizations = "Basic " + new String(encodedBytes, 0, encodedBytes.length);

        URL url = new URL("https://api.tosspayments.com/v1/payments/confirm");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", authorizations);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("POS");
        connection.setDoOutput(true);

        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(obj.toString().getBytes("UTF-8"));

        int code = connection.getResponseCode();
        boolean isSuccess = code == 200;

        InputStream responseStream = isSuccess ? connection.getInputStream() :
            connection.getErrorStream();

        Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
        JSONObject jsonObject = (JSONObject) parser.parse(reader);
        responseStream.close();

        String status = (String) jsonObject.get("status");
        if (isSuccess && status.equalsIgnoreCase("DONE")) {
            try {
                //rufwpdhksfycjflwlsgod
                paymentFacade.completeOrder(Long.parseLong(orderId), member);
            } catch (Exception e) {
                String cancelUrlString =
                    "https://api.tosspayments.com/v1/payments/" + paymentKey + "/cancel";
                URL cancelUrl = new URL(cancelUrlString);
                HttpURLConnection cancelConnection = (HttpURLConnection) cancelUrl.openConnection();

                cancelConnection.setRequestProperty("Authoization", authorizations);
                cancelConnection.setRequestProperty("Content-Type", "application/json");
                cancelConnection.setRequestMethod("POST");
                cancelConnection.setDoOutput(true);

                OutputStream cancelOutputStream = connection.getOutputStream();
                cancelOutputStream.write(obj.toString().getBytes("UTF-8"));

                result.paymentFalse();
                result.enrollMessage("결제 처리중 에러발생으로 취소 api호출 성공");
                return CommonResponse.success(result);
            }

            result.paymentSuccess();
            result.enrollMessage("결제 성공");
            return CommonResponse.success(result);
        }

        paymentFacade.undoOrder(Long.parseLong(orderId));
        result.paymentFalse();
        result.enrollMessage("결제 승인 처리 정상작동하지 않음 " + (String) jsonObject.get("status"));
        return CommonResponse.success(result);
    }
}
