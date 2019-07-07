package poppinjay13.projects.android.model;

import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("status")
    private Boolean status;

    @SerializedName("message")
    private String message;

    @SerializedName("user")
    private User user;

    public Result(Boolean status, String message, User user) {
        this.status = status;
        this.message = message;
        this.user = user;
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
}
