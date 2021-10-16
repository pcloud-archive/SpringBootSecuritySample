package org.pcloud.jwtsecurity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * UserDetails 구현 클래스
 * 만료 여부에 대해 체크할 필요가 없다면 반환 값을 true 로 지정해둔다.
 */
public class User implements UserDetails {
    private String name;
    private List<String> roles;

    public User(String name, List<String> roles) {
        this.name = name;
        this.roles = roles;
    }

    /**
     * 계정이 갖고 있는 권한 목록을 반환한다.
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();

        for (String role : this.roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        return authorities;
    }

    /**
     * 계정의 비밀번호를 반환한다.
     * @return password
     */
    @Override
    public String getPassword() {
        return null;
    }

    /**
     * 계정의 이름을 반환한다.
     * @return name
     */
    @Override
    public String getUsername() {
        return name;
    }

    /**
     * 계정의 만료 여부를 반환한다.
     * @return (true: 활성, false: 만료)
     */
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    /**
     * 계정이 잠겨 있는지 여부를 반환한다.
     * @return (true: 잠겨있지 않음, false: 잠김)
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 비밀번호 만료 여부를 반환한다.
     * @return (true: 활성화, false: 만료됨)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    /**
     * 계정이 사용가능한 상태에 대한 여부를 반환한다.
     * @return (true: 활성화, false: 비활성화)
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}