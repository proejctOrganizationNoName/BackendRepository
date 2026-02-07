package com.project.demo.notify;

import com.project.demo.IntegralTestEnv;
import com.project.demo.notify.domain.Notify;
import com.project.demo.notify.domain.RequestNotifyDto;
import com.project.demo.notify.domain.ResponseNotifyDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

import static com.project.demo.notify.domain.RequestNotifyDto.*;
import static com.project.demo.notify.domain.ResponseNotifyDto.*;
import static org.assertj.core.api.Assertions.*;

public class NotifyTest extends IntegralTestEnv {

    Notify notify;

    @BeforeEach
    void setting(){
        notify=createNotify("test","test",1L);
    }



    @Test
    @DisplayName("공지 생성 테스트")
    void createNotifyTest(){
        notifyService.createNotify(1L,"TEST","TEST");
        assertThat(notifyRepository.findAll().size()).isEqualTo(2);
    }
    @Test
    @DisplayName("공지 삭제 테스트")
    void delNotifyTest(){
        notifyService.delNotify(notify.getNotifyId());
        assertThat(notifyRepository.findAll().getFirst().getDeleted()).isTrue();
    }
    @Test
    @DisplayName("공지 수정 테스트")
    void updateNotfiyTest(){
        RequestNotifyUpdateDto notifyUpdateDto=
                RequestNotifyUpdateDto.builder()
                        .notifyId(notify.getNotifyId())
                        .title("xxx")
                        .content("xxx")
                        .build();
        notifyService.updateNotify(notifyUpdateDto);

        Notify notify1=notifyRepository.findById(notify.getNotifyId()).get();
        assertThat(notify1.getTitle()).isEqualTo("xxx");
        assertThat(notify1.getContent()).isEqualTo("xxx");
    }

    @Test
    @DisplayName("공지 리스트 가져오기 테스트")
    void getNotifyListTest(){
        Notify notify1=createNotify("test","Test",1L);
        RequestNotifyList notifyList=RequestNotifyList.builder()
                .offSet(1)
                .projectId(1L)
                .build();
        Page<NotifyDto>notifyDtos=notifyService.getNotifyList(notifyList);
        assertThat(notifyDtos.getTotalElements()).isEqualTo(2);
    }

    @Test
    @DisplayName("공지 가져오기 테스트")
    void getNotifyTest(){
        NotifyDto notifyDto=notifyService.findById(notify.getNotifyId());
        System.out.println(notifyDto.getCreateDate());
    }
}
