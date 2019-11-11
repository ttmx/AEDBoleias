package app;
import exception.*;

/**
 * @author Rodrigo Rosa
 */
public interface App {
    void addUser(String email, String name, String password);
    User getUser(String email);
    int getNumOfUsers();
    void addTravel(String origin, String destination, String date, String time, int travelDuration, int availableSeats) throws AlreadyHasRideOnDayException, InvalidDataException;
    void delTravel(String date) throws NoTravelOnDateException, HasRidesException;
    void addRide(String travelUserEmail, String date);
    void delRide(String date) throws NoRideOnDateException;
    Travel getTravel(String travelUserEmail, String date);

    void hasEmail(String email) throws HasEmailException;

    void userExistsCheck(String email) throws UserIsNullException;

    User getUserWithPass(String email, String pass) throws WrongPasswordException;

    void removeRide(String date)throws NoRideOnDateException, HasRidesException;
}
