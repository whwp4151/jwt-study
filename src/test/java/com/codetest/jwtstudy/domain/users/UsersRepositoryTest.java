package com.codetest.jwtstudy.domain.users;

import static com.codetest.jwtstudy.fixture.UsersFixtures.createUser;
import static org.junit.jupiter.api.Assertions.*;

import com.codetest.jwtstudy.infra.users.UsersJpaRepository;
import com.codetest.jwtstudy.utils.Aes256Util;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@DataJpaTest
@Import(Aes256Util.class)
class UsersRepositoryTest {

    @Autowired
    private UsersJpaRepository usersRepository;

    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void before() {
        passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Test
    @DisplayName("회원저장")
    public void save() throws Exception {
        //given
        Users user = createUser("hong12", "123456", passwordEncoder);

        //when
        Users save = usersRepository.save(user);

        //then
        assertEquals(user.getUserId(), save.getUserId());
        assertTrue(passwordEncoder.matches("123456", save.getPassword()));
    }

    @Test
    @DisplayName("회원조회")
    public void findByUserId() throws Exception {
        //given
        Users user = createUser("hong12", "123456", passwordEncoder);

        Users save = usersRepository.save(user);

        //when
        Optional<Users> findUser = usersRepository.findByUserId(user.getUserId());

        //then
        assertTrue(findUser.isPresent());
    }

}