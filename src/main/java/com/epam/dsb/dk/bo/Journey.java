package com.epam.dsb.dk.bo;

public class Journey {

    private String from;
    private String to;
    private String date;
    private String time;
    private String travellers;
    private String seatReservations;

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getDate() {
        return date;
    }

    public String getTravellers() {
        return travellers;
    }

    public String getSeatReservations() {
        return seatReservations;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTravellers(String travellers) {
        this.travellers = travellers;
    }

    public void setSeatReservations(String seatReservations) {
        this.seatReservations = seatReservations;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
