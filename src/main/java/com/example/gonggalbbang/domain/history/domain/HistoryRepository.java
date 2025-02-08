package com.example.gonggalbbang.domain.history.domain;

import com.example.gonggalbbang.domain.auth.domain.entity.User;
import com.example.gonggalbbang.domain.history.domain.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {
    List<History> findByUserOrderByDonationDateDesc(User user);
} 