package com.codetest.jwtstudy.domain.users;

import com.codetest.jwtstudy.domain.BaseEntity;
import com.codetest.jwtstudy.security.StringCryptoConverter;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 20, nullable = false, unique = true)
    private String userId;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    @Convert(converter = StringCryptoConverter.class)
    private String regNo;

    public Users(String userId, String password, String name, String regNo, PasswordEncoder passwordEncoder) {
        this.userId = userId;
        this.password = passwordEncoder.encode(password);
        this.name = name;
        this.regNo = regNo;
    }

}
