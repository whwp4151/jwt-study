package com.codetest.jwtstudy.domain.users;

import java.util.Optional;

public interface UsersRepository {
    Users save(Users user);
    boolean existsByUserId(String userId);
    Optional<Users> findByUserId(String userId);
    boolean existsByRegNo(String regNo);
}
