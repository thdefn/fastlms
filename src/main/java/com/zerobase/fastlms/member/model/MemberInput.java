package com.zerobase.fastlms.member.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberInput { //model
    private String userId;
    private String userName;
    private String password;
    private String phone;
}
