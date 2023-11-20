package com.ssafy.web.friend.model;

public class FriendAddDto {
    private String to,from,status;

    @Override
    public String toString() {
        return "FriendAddDto{" +
                "to='" + to + '\'' +
                ", from='" + from + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
