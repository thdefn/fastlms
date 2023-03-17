package com.zerobase.fastlms.member.exception;

public class MemberStopUserException extends RuntimeException { // 런타임 익셉션을 상속받으면 코드에서 처리를 안해도 된다
    public MemberStopUserException(String error) {
        super(error);
    }
}
