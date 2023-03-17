package com.zerobase.fastlms.member.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Member {

    @Id
    private String userId;

    private String userName;

    private String phoneNumber;

    private String password;

    private LocalDateTime regDt;

    private boolean emailAuthYn;

    private String emailAuthKey;

    private LocalDateTime emailAuthDt;

    private String resetPasswordKey;

    private LocalDateTime resetPasswordLimitDt;

    private boolean adminYn;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus = UserStatus.ING;
}
