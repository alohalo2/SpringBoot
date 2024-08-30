package com.bit.springboard.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
<<<<<<< HEAD
import org.springframework.security.oauth2.core.user.OAuth2User;
=======
>>>>>>> b9d78aa26fac4abae323212beef8cda07166f268

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
<<<<<<< HEAD
import java.util.Map;
=======
>>>>>>> b9d78aa26fac4abae323212beef8cda07166f268

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
<<<<<<< HEAD
public class CustomUserDetails implements UserDetails, OAuth2User {
    private Member member;

    // 소셜 로그인 시 사용자 정보를 담아줄 Map
    private Map<String, Object> attributes;

=======
public class CustomUserDetails implements UserDetails {
    private Member member;

>>>>>>> b9d78aa26fac4abae323212beef8cda07166f268

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<>();

        auths.add(
                new GrantedAuthority() {
                    @Override
                    public String getAuthority() {
                        return member.getRole();
                    }
                }
        );
        return auths;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
<<<<<<< HEAD

    // OAuth2를 구현하기 위해 추가한 메소드
    // 구현할 필요없음
    @Override
    public String getName() {
        return "";
    }
=======
>>>>>>> b9d78aa26fac4abae323212beef8cda07166f268
}
