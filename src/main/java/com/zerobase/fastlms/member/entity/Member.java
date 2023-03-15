package com.zerobase.fastlms.member.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Builder
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
}
