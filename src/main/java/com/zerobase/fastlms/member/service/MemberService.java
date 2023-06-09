package com.zerobase.fastlms.member.service;

import com.zerobase.fastlms.admin.dto.MemberDetailDto;
import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.model.MemberSearchParam;
import com.zerobase.fastlms.member.model.MemberInput;
import com.zerobase.fastlms.member.model.ResetPasswordRequestInput;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * 서비스는 하나의 트랜잭션 처리에 대해 진행하는 단위
 */
public interface MemberService extends UserDetailsService {
    boolean register(MemberInput parameter);

    /**
     * uuid에 해당하는 계정을 활성화 함
     */
    boolean emailAuth(String uuid);

    /**
     * 입력한 이메일로 비밀번호 초기화 정보를 전송
     */
    boolean sendResetPassword(ResetPasswordRequestInput parameter);

    /**
     * 입력받은 uuid에 대해 password로 초기화 함
     */
    boolean resetPassword(String id, String password);

    /**
     * 입력받은 uuid 값이 유효한지 확인
     */
    boolean checkResetPassword(String uuid);

    /**
     * 회원의 목록 리턴 (관리자에서만 사용 가능)
     */
    List<MemberDto> list(MemberSearchParam parameter);

    /**
     * 회원 상세 정보
     */
    MemberDetailDto detail(String userId);

    void loginHistory(String userIp, String userAgent, String userId);

    /**
     * 회원 상태 변경
     */
    boolean updateStatus(String userId, String userStatus);

    /**
     * 회원 비밀번호 초기화
     */
    boolean updatePassword(String userId, String password);
}
