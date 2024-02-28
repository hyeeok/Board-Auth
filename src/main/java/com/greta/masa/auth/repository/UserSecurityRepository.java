package com.greta.masa.auth.repository;

import com.greta.masa.auth.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserSecurityRepository extends JpaRepository<UserSecurity, Long> {
    UserSecurity findByUser(User user);

}
