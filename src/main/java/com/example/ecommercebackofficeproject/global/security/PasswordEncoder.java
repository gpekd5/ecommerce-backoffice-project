package com.example.ecommercebackofficeproject.global.security;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

/**
 * 유저의 비밀번호를 안전하게 암호화하고 검증하는 클래스입니다.
 * BCrypt 알고리즘을 사용하여 단방향 해시 암호화를 수행합니다.
 */
@Component
public class PasswordEncoder {

    /**
     * 평문 비밀번호를 BCrypt 알고리즘으로 암호화합니다.
     * @param rawPassword 암호화되지 않은 사용자의 입력 비밀번호
     * @return 암호화되어 해시 처리된 문자열
     */
    public String encode(String rawPassword) {
        return BCrypt.withDefaults().hashToString(BCrypt.MIN_COST, rawPassword.toCharArray());
    }

    /**
     * 사용자가 입력한 평문 비밀번호와 DB에 저장된 암호화된 비밀번호가 일치하는지 확인합니다.
     * @param rawPassword 사용자가 입력한 평문 비밀번호
     * @param encodedPassword 데이터베이스에 저장된 기존의 암호화된 비밀번호
     * @return 일치 여부 (true: 일치, false: 불일치)
     */
    public boolean matches(String rawPassword, String encodedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), encodedPassword);
        return result.verified;
    }
}
