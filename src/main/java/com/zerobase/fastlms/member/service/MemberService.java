package com.zerobase.fastlms.member.service;

import com.zerobase.fastlms.member.model.MemberInput;

/**
 * 서비스는 하나의 트랜잭션 처리에 대해 진행하는 단위
 */
public interface MemberService {
    boolean register(MemberInput parameter);

    /**
     * uuid에 해당하는 계정을 활성화 함
     */
    boolean emailAuth(String uuid);
}
