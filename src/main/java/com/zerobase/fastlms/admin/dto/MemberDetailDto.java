package com.zerobase.fastlms.admin.dto;

import com.zerobase.fastlms.member.entity.Member;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberDetailDto {
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
    String userStatus;
    List<LoginHistoryDto> history;

    public static MemberDetailDto of(Member member, List<LoginHistoryDto> historyDtos){
        return MemberDetailDto.builder()
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
                .history(historyDtos)
                .build();
    }
}
