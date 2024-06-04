package com.service.concurrencyprac.auth.dto;

import com.service.concurrencyprac.auth.domain.member.Member;
import com.service.concurrencyprac.auth.domain.member.Member.UserRole;
import com.service.concurrencyprac.auth.domain.member.MemberCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class MemberDTO {

    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SignupRequest {

        @Email(message = "email형식을 지켜주세요")
        @NotBlank(message = "email은 필수값입니다.")
        private String email;
        @NotBlank(message = "이름은 필수값입니다.")
        private String memberName;
        @NotBlank(message = "비밀번호는 필수값입니다.")
        private String password;
        @NotBlank(message = "닉네임은 필수값입니다.")
        private String nickName;

        public MemberCommand.SignupMemberRequest toCommand() {
            return MemberCommand.SignupMemberRequest.builder()
                .email(email)
                .password(password)
                .memberName(memberName)
                .nickName(nickName)
                .role(UserRole.USER)
                .build();
        }
    }
}
