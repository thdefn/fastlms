package com.zerobase.fastlms.member.service.impl;

import com.zerobase.fastlms.component.MailComponent;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.model.MemberInput;
import com.zerobase.fastlms.member.repository.MemberRepository;
import com.zerobase.fastlms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service //빈으로 등록
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    private final MailComponent mailComponent;

    /**
     * 회원 가입
     */
    @Override
    public boolean register(MemberInput parameter) {

        // jpa의 아이디로 엔티티 찾기 메서드
        Optional<Member> optionalMember = memberRepository.findById(parameter.getUserId());
        if(optionalMember.isPresent()){
            // 현재 userId에 해당하는 데이터 존재
            return false;
        }

        String uuid = UUID.randomUUID().toString();

        Member member = Member.builder()
                .userId(parameter.getUserId())
                .userName(parameter.getUserName())
                .phoneNumber(parameter.getPhone())
                .password(parameter.getPassword())
                .regDt(LocalDateTime.now())
                .emailAuthYn(false)
                .emailAuthKey(uuid)
                .build();

        String email = parameter.getUserId();
        String subject = "fast lms 사이트 가입을 축하드립니다.";
        String text = "<p>fastlms 사이트 가입을 축하드립니다.</p>"+
                "<p>아래 링크를 클릭하셔서 가입을 완료하세요.</p>"
                +"<div><a href='http://localhost:8080/member/email-auth?id="+uuid+"'>가입 완료</a></div>";
        mailComponent.sendMail(email, subject, text);

        memberRepository.save(member); // 기존에 있는 데이터는 자동 업데이트

        return true;
    }

    @Override
    public boolean emailAuth(String uuid) {
        Optional<Member> optionalMember = memberRepository.findByEmailAuthKey(uuid);

        if(!optionalMember.isPresent()){
            return false;
        }

        Member member = optionalMember.get();
        member.setEmailAuthYn(true);
        member.setEmailAuthDt(LocalDateTime.now());

        memberRepository.save(member);

        return true;
    }

    // 여기서 username은 이메일임
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.findById(username);
        if(!optionalMember.isPresent()){
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다");
        }
        Member member = optionalMember.get();

        List<GrantedAuthority> grantedAuthorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));

        return new User(member.getUserId(), member.getPassword(), grantedAuthorities);
    }
}
