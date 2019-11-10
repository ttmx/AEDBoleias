package app;

/**
 * @author Rodrigo Rosa
 */
public interface App {
    void addUser(String email, String name, String password);
    User getUser(String email);
    int getNumOfUsers();
    void addTravel(String origin, String destination, String date, String time, int travelDuration, int availableSeats);
    void delTravel(String date);
    void addRide(String travelUserEmail, String date);
    void delRide(String date);
    Travel getTravel(String travelUserEmail, String date);
}
