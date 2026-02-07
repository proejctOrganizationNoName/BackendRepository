package com.project.demo.notify.domain;

import com.project.demo.utility.CustomDateTimeFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ResponseNotifyDto {

    @NoArgsConstructor
    @Getter
    public final static class NotifyDto{
        public Long notifyId;
        public String title;
        public String content;
        public String createDate;

        @Builder
        public NotifyDto(Long notifyId, String title, String content, String createDate) {
            this.notifyId = notifyId;
            this.title = title;
            this.content = content;
            this.createDate = createDate;
        }

        public void updateDateTime(){
            this.createDate= CustomDateTimeFormat.parseServerTimeToClientFormat(
                    CustomDateTimeFormat.parseClientTimetoServerFormat(createDate)
            );
        }
    }
}
