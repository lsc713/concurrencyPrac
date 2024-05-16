package com.service.concurrencyprac.api.dto;

import com.service.concurrencyprac.api.domain.post.PostCommand;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.ToString;

public class PostDTO {

    @Getter
    @ToString
    public static class PostRequest {

        @NotBlank(message = "제목은 필수값입니다.")
        private String title;
        @NotBlank(message = "내용은 필수값입니다.")
        private String contents;
        @NotBlank(message = "닉네임은 필수값입니다.")
        private String nickName;

        public PostCommand.PostPostingRequest toCommand() {
            return PostCommand.PostPostingRequest.builder()
                .title(title)
                .contents(contents)
                .nickName(nickName)
                .build();
        }
    }

}
