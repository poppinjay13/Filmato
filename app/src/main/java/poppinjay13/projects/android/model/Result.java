package poppinjay13.projects.android.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {
    @SerializedName("status")
    private Boolean status;

    @SerializedName("message")
    private String message;

    @SerializedName("user")
    private User user;

    @SerializedName("ticket")
    private List<Ticket> ticket;

    public Result(Boolean status, String message, User user) {
        this.status = status;
        this.message = message;
        this.user = user;
    }

    public Result(Boolean status, String message, List<Ticket> ticket) {
        this.status = status;
        this.message = message;
        this.ticket = ticket;
    }

    public Result(Boolean status, String message){
        this.status = status;
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public List<Ticket> getTicket() {
        return ticket;
    }
}
