package com.nextshow.service;

import com.nextshow.entity.RewardsWallet;
import com.nextshow.entity.User;
import com.nextshow.exception.BookingException;
import com.nextshow.repository.RewardsWalletRepository;
import com.nextshow.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RewardsService {

    private final RewardsWalletRepository rewardsWalletRepository;
    private final UserRepository userRepository;

    public RewardsService(
            RewardsWalletRepository rewardsWalletRepository,
            UserRepository userRepository) {
        this.rewardsWalletRepository = rewardsWalletRepository;
        this.userRepository = userRepository;
    }

    public RewardsWallet getWallet(Long userId) {

        return rewardsWalletRepository.findByUserId(userId)
                .orElseGet(() -> createWallet(userId));
    }

    private RewardsWallet createWallet(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new BookingException("User not found"));

        RewardsWallet wallet = new RewardsWallet();

        wallet.setUser(user);
        wallet.setBronze(0);
        wallet.setSilver(0);
        wallet.setGold(0);

        return rewardsWalletRepository.save(wallet);
    }

    @Transactional
    public RewardsWallet earnBronze(Long userId) {

        RewardsWallet wallet = getWallet(userId);

        wallet.setBronze(wallet.getBronze() + 1);

        return rewardsWalletRepository.save(wallet);
    }

    @Transactional
    public RewardsWallet convertRewards(Long userId) {

        RewardsWallet wallet = getWallet(userId);

        while (wallet.getBronze() >= 3) {
            wallet.setBronze(wallet.getBronze() - 3);
            wallet.setSilver(wallet.getSilver() + 1);
        }

        while (wallet.getSilver() >= 3) {
            wallet.setSilver(wallet.getSilver() - 3);
            wallet.setGold(wallet.getGold() + 1);
        }

        return rewardsWalletRepository.save(wallet);
    }

    @Transactional
    public RewardsWallet redeemSilver(Long userId, int count) {

        RewardsWallet wallet = getWallet(userId);

        if (count <= 0) {
            throw new BookingException("Invalid silver reward count");
        }

        if (wallet.getSilver() < count) {
            throw new BookingException("Not enough silver rewards");
        }

        wallet.setSilver(wallet.getSilver() - count);

        return rewardsWalletRepository.save(wallet);
    }

    @Transactional
    public RewardsWallet redeemGold(Long userId) {

        RewardsWallet wallet = getWallet(userId);

        if (wallet.getGold() <= 0) {
            throw new BookingException("No gold reward available");
        }

        wallet.setGold(wallet.getGold() - 1);

        return rewardsWalletRepository.save(wallet);
    }
}