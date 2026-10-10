
package com.nextshow.controller;

import com.nextshow.entity.RewardsWallet;
import com.nextshow.entity.User;
import com.nextshow.exception.BookingException;
import com.nextshow.repository.UserRepository;
import com.nextshow.service.RewardsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/rewards")
public class RewardsController {

    private final RewardsService rewardsService;
    private final UserRepository userRepository;

    public RewardsController(
            RewardsService rewardsService,
            UserRepository userRepository) {
        this.rewardsService = rewardsService;
        this.userRepository = userRepository;
    }

    @GetMapping("/me")
    public Map<String, Object> getMyRewards() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new BookingException("User not found"));

        RewardsWallet wallet =
                rewardsService.getWallet(user.getId());

        return Map.of(
                "bronze", wallet.getBronze(),
                "silver", wallet.getSilver(),
                "gold", wallet.getGold()
        );
    }
}
