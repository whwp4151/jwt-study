package com.codetest.jwtstudy.infra.users;

import com.codetest.jwtstudy.domain.users.Users;
import com.codetest.jwtstudy.domain.users.UsersRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UsersRepositoryImpl implements UsersRepository {

    private final UsersJpaRepository usersJpaRepository;

    @Override
    public Users save(Users user) {
        return usersJpaRepository.save(user);
    }

    @Override
    public boolean existsByUserId(String userId) {
        return usersJpaRepository.existsByUserId(userId);
    }

    @Override
    public Optional<Users> findByUserId(String userId) {
        return usersJpaRepository.findByUserId(userId);
    }

    @Override
    public boolean existsByRegNo(String regNo) {
        return usersJpaRepository.existsByRegNo(regNo);
    }

}
