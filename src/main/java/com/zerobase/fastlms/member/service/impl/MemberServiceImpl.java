package com.zerobase.fastlms.member.service.impl;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.mapper.MemberMapper;
import com.zerobase.fastlms.admin.model.MemberSearchParam;
import com.zerobase.fastlms.component.MailComponent;
import com.zerobase.fastlms.member.entity.LoginHistory;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.entity.UserStatus;
import com.zerobase.fastlms.member.exception.MemberNotEmailAuthException;
import com.zerobase.fastlms.member.exception.MemberStopUserException;
import com.zerobase.fastlms.member.model.MemberInput;
import com.zerobase.fastlms.member.model.ResetPasswordRequestInput;
import com.zerobase.fastlms.member.repository.LoginHistoryRepository;
import com.zerobase.fastlms.member.repository.MemberRepository;
import com.zerobase.fastlms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service //빈으로 등록
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    private final LoginHistoryRepository loginHistoryRepository;

    private final MailComponent mailComponent;

    private final MemberMapper memberMapper;

    /**
     * 회원 가입
     */
    @Override
    public boolean register(MemberInput parameter) {

        // jpa의 아이디로 엔티티 찾기 메서드
        Optional<Member> optionalMember = memberRepository.findById(parameter.getUserId());
        if (optionalMember.isPresent()) {
            // 현재 userId에 해당하는 데이터 존재
            return false;
        }

        String encPassword = BCrypt.hashpw(parameter.getPassword(), BCrypt.gensalt());
        String uuid = UUID.randomUUID().toString();

        Member member = Member.builder()
                .userId(parameter.getUserId())
                .userName(parameter.getUserName())
                .phoneNumber(parameter.getPhone())
                .password(encPassword)
                .regDt(LocalDateTime.now())
                .userStatus(UserStatus.REQ)
                .emailAuthYn(false)
                .emailAuthKey(uuid)
                .build();

        String email = parameter.getUserId();
        String subject = "fast lms 사이트 가입을 축하드립니다.";
        String text = "<p>fastlms 사이트 가입을 축하드립니다.</p>" +
                "<p>아래 링크를 클릭하셔서 가입을 완료하세요.</p>"
                + "<div><a href='http://localhost:8080/member/email-auth?id=" + uuid + "'>가입 완료</a></div>";
        mailComponent.sendMail(email, subject, text);

        memberRepository.save(member); // 기존에 있는 데이터는 자동 업데이트

        return true;
    }

    @Override
    public boolean emailAuth(String uuid) {
        Optional<Member> optionalMember = memberRepository.findByEmailAuthKey(uuid);

        if (!optionalMember.isPresent()) {
            return false;
        }

        Member member = optionalMember.get();

        if (member.isEmailAuthYn()) {
            return false;
        }

        member.setEmailAuthYn(true);
        member.setEmailAuthDt(LocalDateTime.now());
        member.setUserStatus(UserStatus.ING);

        memberRepository.save(member);

        return true;
    }

    @Override
    public boolean sendResetPassword(ResetPasswordRequestInput parameter) {
        Optional<Member> optionalMember = memberRepository
                .findByUserIdAndAndUserName(parameter.getUserId(), parameter.getUserName());

        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다");
        }

        String uuid = UUID.randomUUID().toString();

        Member member = optionalMember.get();
        member.setResetPasswordKey(uuid);
        member.setResetPasswordLimitDt(LocalDateTime.now().plusDays(1));

        String email = parameter.getUserId();
        String subject = "[fast lms] 비밀번호 초기화 메일입니다.";
        String text = "<p>fastlms 비밀번호 초기화 메일입니다.</p>" +
                "<p>아래 링크를 클릭하셔서 비밀번호를 초기화 해주세요.</p>"
                + "<div><a href='http://localhost:8080/member/reset/password?id=" + uuid + "'>비밀번호 초기화 링크</a></div>";
        mailComponent.sendMail(email, subject, text);

        memberRepository.save(member);

        return true;
    }

    @Override
    public boolean resetPassword(String id, String password) {
        Optional<Member> optionalMember = memberRepository.findByResetPasswordKey(id);

        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다");
        }

        Member member = optionalMember.get();

        // 초기화 날짜가 유효한지 체크
        if (member.getResetPasswordLimitDt() == null) {
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }

        if (LocalDateTime.now().isAfter(member.getResetPasswordLimitDt())) {
            System.out.println(member.getResetPasswordLimitDt().toString());
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }

        String encPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        member.setPassword(encPassword);
        member.setResetPasswordKey(null);
        member.setResetPasswordLimitDt(null);

        memberRepository.save(member);

        return true;
    }

    @Override
    public boolean checkResetPassword(String uuid) {

        Optional<Member> optionalMember = memberRepository.findByResetPasswordKey(uuid);

        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다");
        }

        Member member = optionalMember.get();

        // 초기화 날짜가 유효한지 체크
        if (member.getResetPasswordLimitDt() == null) {
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }

        if (LocalDateTime.now().isAfter(member.getResetPasswordLimitDt())) {
            System.out.println(member.getResetPasswordLimitDt().toString());
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }

        return true;
    }

    // jpa는 api가 직접 쿼리를 만들지만 mybatis는 쿼리를 임의 작성해서 실행할 수 있다
    @Override
    public List<MemberDto> list(MemberSearchParam parameter) {
        long totalCount = memberMapper.selectListCount(parameter);
        List<MemberDto> list = memberMapper.selectList(parameter);

        if(!CollectionUtils.isEmpty(list)){
            int i = 0;
            for(MemberDto x : list){
                x.setTotalCount(totalCount);
                x.setSeq(totalCount - (parameter.getPageStart() + i++));
            }
        }

        return list;
        //return memberRepository.findAll();
    }

    @Override
    public MemberDto detail(String userId) {
        Optional<Member> optionalMember = memberRepository.findById(userId);

        if(!optionalMember.isPresent()){
            return null;
        }

        return MemberDto.of(optionalMember.get());
    }

    @Override
    public void loginHistory(String userIp, String userAgent, String userId) {
        loginHistoryRepository.save(
                LoginHistory.builder()
                        .userAgent(userAgent)
                        .userIp(userIp)
                        .loginDt(LocalDateTime.now())
                        .userId(userId)
                        .build()
        );
    }

    @Override
    public boolean updateStatus(String userId, String userStatus) {

        Optional<Member> optionalMember = memberRepository.findById(userId);
        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다");
        }
        Member member = optionalMember.get();

        member.setUserStatus(UserStatus.valueOf(userStatus));
        memberRepository.save(member);

        return true;
    }

    @Override
    public boolean updatePassword(String userId, String password) {
        Optional<Member> optionalMember = memberRepository.findById(userId);
        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다");
        }
        Member member = optionalMember.get();
        String encPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        member.setPassword(encPassword);
        memberRepository.save(member);
        return true;
    }


    // 여기서 username은 이메일임
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.findById(username);
        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다");
        }
        Member member = optionalMember.get();

        if (UserStatus.REQ.equals(member.getUserStatus())) {
            throw new MemberNotEmailAuthException("이메일 활성화 이후에 로그인을 해주세요");
        }

        if (UserStatus.STOP.equals(member.getUserStatus())) {
            throw new MemberStopUserException("정지된 회원입니다");
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        if(member.isAdminYn()){
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN")); // 관리자 일때는 관리자 롤을 추가
        }

        return new User(member.getUserId(), member.getPassword(), grantedAuthorities);
    }
}
