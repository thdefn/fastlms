package com.zerobase.fastlms.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Member 엔티티와 달리 한번 가공해서 데이터를 내림
 */
@Getter
@Setter // 안만들어주면 에러남
public class MemberDto {
    String userId;
    String userName;
    String phoneNumber;
    String password;
    LocalDateTime regDt;
    String emailAuthKey;
    boolean emailAuthYn;
    LocalDateTime emailAuthDt;
    String resetPasswordKey;
    LocalDateTime resetPasswordLimitDt;
    boolean adminYn;
}
