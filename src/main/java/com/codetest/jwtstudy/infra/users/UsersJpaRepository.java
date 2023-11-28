package com.codetest.jwtstudy.infra.users;

import com.codetest.jwtstudy.domain.users.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersJpaRepository extends JpaRepository<Users, Long> {
    boolean existsByUserId(String userId);
    Optional<Users> findByUserId(String userId);
    boolean existsByRegNo(String regNo);
}
