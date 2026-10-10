package com.nextshow.dto;

public class TicketVerificationResponse {

    private boolean valid;
    private String message;
    private String ticketNumber;
    private String customerName;
    private String eventName;
    private String venue;
    private String showDate;
    private String showTime;
    private String ticketStatus;

    public TicketVerificationResponse() {
    }

    public TicketVerificationResponse(
            boolean valid,
            String message,
            String ticketNumber,
            String customerName,
            String eventName,
            String venue,
            String showDate,
            String showTime,
            String ticketStatus) {

        this.valid = valid;
        this.message = message;
        this.ticketNumber = ticketNumber;
        this.customerName = customerName;
        this.eventName = eventName;
        this.venue = venue;
        this.showDate = showDate;
        this.showTime = showTime;
        this.ticketStatus = ticketStatus;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getShowDate() {
        return showDate;
    }

    public void setShowDate(String showDate) {
        this.showDate = showDate;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }
}
