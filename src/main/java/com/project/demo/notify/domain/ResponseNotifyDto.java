package com.project.demo.notify.domain;

import com.project.demo.utility.CustomDateTimeFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class ResponseNotifyDto {

    @NoArgsConstructor
    @Getter
    public final static class NotifyDto{
        public Long notifyId;
        public String title;
        public String content;
        public String createDate;
        public String updateDate;

        @Builder
        public NotifyDto(Long notifyId,
                         String title, String content, String createDate,String updateDate) {
            this.notifyId = notifyId;
            this.title = title;
            this.content = content;
            this.createDate = createDate;
            this.updateDate=updateDate;
        }

        public void parseDateTime(){
            this.createDate= CustomDateTimeFormat.parseServerTimeToClientFormat(
                    LocalDateTime.parse(this.createDate));
            this.updateDate= CustomDateTimeFormat.parseServerTimeToClientFormat(
                    LocalDateTime.parse(this.updateDate)
            );
        }
    }
}
