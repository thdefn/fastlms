package com.zerobase.fastlms.member.service;

import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.model.MemberInput;
import com.zerobase.fastlms.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service //빈으로 등록
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;

    @Override
    public boolean register(MemberInput parameter) {
        Member member = new Member();
        member.setUserId(parameter.getUserId());
        member.setUserName(parameter.getUserName());
        member.setPhoneNumber(parameter.getPhone());
        member.setPassword(parameter.getPassword());
        member.setRegDt(LocalDateTime.now());

        memberRepository.save(member); // 기존에 있는 데이터는 자동 업데이트

        return false;
    }
}
