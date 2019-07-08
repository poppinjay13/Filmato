package poppinjay13.projects.android.model;

public class Ticket {
    String email;
    String cinema;
    String date;
    String movie;
    String time;
    String seats;
    String price;


    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCinema() {
        return cinema;
    }

    public void setCinema(String cinema) {
        this.cinema = cinema;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public Ticket(String email, String cinema, String date, String time, String seats, String price, String movie) {
        this.email = email;
        this.cinema = cinema;
        this.date = date;
        this.time = time;
        this.seats = seats;
        this.price = price;
        this.movie = movie;
    }

    public Ticket(String email){
        this.email = email;
    }

}
