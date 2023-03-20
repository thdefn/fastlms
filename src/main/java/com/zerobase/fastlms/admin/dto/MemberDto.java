package com.zerobase.fastlms.admin.dto;

import com.zerobase.fastlms.member.entity.Member;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Member 엔티티와 달리 한번 가공해서 데이터를 내림
 */
@Getter
@Setter // 안만들어주면 에러남
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
    LocalDateTime loginDt;

    //추가 컬럼
    long totalCount;
    long seq;
    String userStatus;

    public static MemberDto of(Member member){
        return MemberDto.builder()
                .userId(member.getUserId())
                .userName(member.getUserName())
                .phoneNumber(member.getPhoneNumber())
                .regDt(member.getRegDt())
                .emailAuthDt(member.getEmailAuthDt())
                .emailAuthYn(member.isEmailAuthYn())
                .emailAuthKey(member.getEmailAuthKey())
                .resetPasswordKey(member.getResetPasswordKey())
                .resetPasswordLimitDt(member.getResetPasswordLimitDt())
                .adminYn(member.isAdminYn())
                .userStatus(member.getUserStatus().name())
                .build();
    }
}
