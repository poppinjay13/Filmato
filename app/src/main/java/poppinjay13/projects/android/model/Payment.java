package poppinjay13.projects.android.model;

public class Payment {
    private String phone;
    private String amount;

    public Payment(String phone, String amount){
        this.phone = phone;
        this.amount = amount;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
