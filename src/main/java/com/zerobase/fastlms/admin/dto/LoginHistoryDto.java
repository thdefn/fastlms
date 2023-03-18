package com.zerobase.fastlms.admin.dto;

import com.zerobase.fastlms.member.entity.LoginHistory;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Member 엔티티와 달리 한번 가공해서 데이터를 내림
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginHistoryDto {
    long seq;
    LocalDateTime loginAt;
    String userIp;
    String userAgent;

    public static LoginHistoryDto of(LoginHistory history){
        return LoginHistoryDto.builder()
                .loginAt(history.getLoginDt())
                .userAgent(history.getUserAgent())
                .userIp(history.getUserIp())
                .build();
    }
}
