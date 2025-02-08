package com.example.gonggalbbang.domain.history.domain.entity;

import com.example.gonggalbbang.domain.auth.domain.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@SuperBuilder
@Entity
@Table(name = "tb_history")
@NoArgsConstructor
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDate donationDate;

    @Column(nullable = false)
    private Integer amount;
} 