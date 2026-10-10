package com.nextshow.dto;

public class TicketQrResponse {

    private Long ticketId;
    private String ticketNumber;
    private String qrCode;

    public TicketQrResponse() {
    }

    public TicketQrResponse(Long ticketId, String ticketNumber, String qrCode) {
        this.ticketId = ticketId;
        this.ticketNumber = ticketNumber;
        this.qrCode = qrCode;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
}
