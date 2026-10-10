package com.nextshow.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class CreateBookingRequest {

    @NotNull(message = "Show ID is required")
    private Long showId;

    @NotEmpty(message = "At least one seat must be selected")
    private List<Long> showSeatIds;

    private String couponCode;

    private int silverRewards;

    private boolean goldReward;

    public CreateBookingRequest() {
    }

    public Long getShowId() {
        return showId;
    }

    public void setShowId(Long showId) {
        this.showId = showId;
    }

    public List<Long> getShowSeatIds() {
        return showSeatIds;
    }

    public void setShowSeatIds(List<Long> showSeatIds) {
        this.showSeatIds = showSeatIds;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public int getSilverRewards() {
        return silverRewards;
    }

    public void setSilverRewards(int silverRewards) {
        this.silverRewards = silverRewards;
    }

    public boolean isGoldReward() {
        return goldReward;
    }

    public void setGoldReward(boolean goldReward) {
        this.goldReward = goldReward;
    }
}