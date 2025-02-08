package com.example.gonggalbbang.domain.auth.domain;

import com.example.gonggalbbang.domain.auth.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<User, String> {
}
