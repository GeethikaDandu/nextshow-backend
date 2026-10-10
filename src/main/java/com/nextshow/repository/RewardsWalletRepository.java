package com.nextshow.repository;

import com.nextshow.entity.RewardsWallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RewardsWalletRepository extends JpaRepository<RewardsWallet, Long> {

    Optional<RewardsWallet> findByUserId(Long userId);
}
