package com.zerobase.fastlms.member.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResetPasswordInput { //model
    private String userId;
    private String userName;
}
