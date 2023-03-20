package com.zerobase.fastlms.member.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
public class LoginHistory {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime loginDt;

    private String userIp;

    private String userAgent;

    @ManyToOne
    private Member member;
}
