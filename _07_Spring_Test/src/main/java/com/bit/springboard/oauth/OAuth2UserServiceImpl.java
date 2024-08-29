package com.bit.springboard.oauth;

import com.bit.springboard.entity.CustomUserDetails;
import com.bit.springboard.entity.Member;
import com.bit.springboard.oauth.provider.KaKaoUserInfo;
import com.bit.springboard.oauth.provider.OAuth2UserInfo;
import com.bit.springboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service // 자동으로 bean 객체 생성
@RequiredArgsConstructor
public class OAuth2UserServiceImpl extends DefaultOAuth2UserService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    /*
    *   소셜 로그인 버튼 클릭 -> 인증서버로 요청 -> 인증서버에서 인증코드 발급
    *   -> 발급받은 인증코드로 다시 한번 인증서버로 요청 -> 인증서버는 인증코드의 유효성 검사 후 토큰 발금
    *   -> 발급받은 토큰으로 자원서버에 요청 -> 자원서버는 토큰의 유효성을 검사 후 사용자 정보를 리턴
    *   -> SecurityConfiguration에 설정된 userEndpoint에 지정된 Service 클래스의 loadUser 메소드가
    *      자동으로 호출되면서 커스터마이징된 사용자의 정보를 리턴한다.
    * */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) {
        OAuth2User oAuth2User = super.loadUser(request);

        String nickname = "";
        String providerId = "";

        // 커스터마이징해서 사용자 정보를 받아줄 객체 생성
        OAuth2UserInfo oAuth2UserInfo = null;

        // 소셜 카테고리 검증
        if(request.getClientRegistration().getRegistrationId().equalsIgnoreCase("kakao")) {
            oAuth2UserInfo = new KaKaoUserInfo(oAuth2User.getAttributes());

            providerId = oAuth2UserInfo.getProviderId();
            nickname = oAuth2UserInfo.getName();
        } else if(request.getClientRegistration().getRegistrationId().equalsIgnoreCase("naver")) {

        } else if (request.getClientRegistration().getRegistrationId().equalsIgnoreCase("google")) {

        } else {
            return null;
        }

        String provider = oAuth2UserInfo.getProvider();
        // kakao_11235325646
        String uesrname = oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId();
        // OAuth2를 이용한 로그인은 비밀번호가 필요없기 때문에
        // 그냥 닉네임을 암호화해서 사용
        String password = passwordEncoder.encode(nickname);

        String email = oAuth2UserInfo.getEmail();

        // 소셜 로그인을 했던 이력이 있는지 검사할 Member 엔티티
        // 소셜 로그인을 했던 이력이 있으면 그대로 소셜 로그인을 진행하고
        // 소셜 로그인 이력이 없으면 게시판 DB에 사용자 정보를 저장
        Member member;

        if(memberRepository.findByUsername(uesrname).isPresent()) { // 소셜 로그인 이력 있는 경우
            member = memberRepository.findByUsername(uesrname)
                    .orElseThrow(() -> new RuntimeException("member not exist"));
        } else { // 소셜 로그인 이력 없는 경우
            member = Member.builder()
                    .username(uesrname)
                    .password(password)
                    .email(email)
                    .nickname(nickname)
                    .build();

            memberRepository.save(member);
        }
        return CustomUserDetails.builder()
                .member(member)
                .attributes(oAuth2User.getAttributes())
                .build();















    }

}
