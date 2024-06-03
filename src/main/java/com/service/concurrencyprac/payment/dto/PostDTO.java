package com.service.concurrencyprac.payment.dto;

import com.service.concurrencyprac.payment.entity.post.Post;
import com.service.concurrencyprac.payment.entity.post.PostCommand;
import com.service.concurrencyprac.payment.entity.post.PostCommand.UpdateCommand;
import com.service.concurrencyprac.payment.entity.post.PostInfo;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
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

        public PostCommand.PostingCommand toCommand(String nickName) {
            return PostCommand.PostingCommand.builder()
                .title(title)
                .contents(contents)
                .nickName(nickName)
                .build();
        }
    }

    @Getter
    @ToString
    public static class fetchPostOne {

        @NotBlank(message = "제목은 필수값입니다.")
        private String title;
        @NotBlank(message = "내용은 필수값입니다.")
        private String contents;
        @NotBlank(message = "닉네임은 필수값입니다.")
        private String nickName;

        public PostCommand.PostingCommand toCommand() {
            return PostCommand.PostingCommand.builder()
                .title(title)
                .contents(contents)
                .nickName(nickName)
                .build();
        }
    }

    @Getter
    public static class PostUpdateDto {

        @NotBlank(message = "제목은 필수값입니다.")
        private String title;
        @NotBlank(message = "내용은 필수값입니다.")
        private String contents;

        public PostCommand.UpdateCommand toCommand() {
            return UpdateCommand.builder()
                .title(title)
                .contents(contents)
                .build();
        }
    }

    @Getter
    @ToString
    public static class PostResponse {

        private final String postToken;
        private final String title;
        private final String contents;
        private final String nickName;
        private final Post.Status Status;

        public PostResponse(PostInfo postInfo) {
            this.postToken = postInfo.getPostToken();
            this.title = postInfo.getTitle();
            this.contents = postInfo.getContents();
            this.nickName = postInfo.getNickName();
            this.Status = postInfo.getStatus();
        }
    }
}
