package app;
import dataStructures.Date;
import dataStructures.Iterator;
import exception.*;

import java.io.*;

/**
 * @author Rodrigo Rosa
 */
public interface App extends Serializable {
    boolean hasSession();
    String getCurrentUserId();
    String getCurrentUserName();
    boolean hasEmail(String email);
    void addUser(String email, String name, String password);
    int getNumOfUsers();
    boolean matchesPw(String email, String password);
    void setCurrentUser(String email);
    int getUserLoginNum();
    String closeSession();
    void addTravel(String origin, String destination, Date date, String time, int travelDuration, int availableSeats) throws InvalidDateException, AlreadyHasEntryOnDayException;
    int getUserTravelsNum();
    void delTravel(Date date) throws InvalidDateException, InvalidTravelException, HasRidesException;
    void addRide(String travelUserEmail, Date date) throws InvalidDateException,SamePersonException,PlacedInQueueException,NoRideOnDateException,UserIsNullException, AlreadyHasRideOnDayException;
    void delRide(Date date) throws NoRideOnDateException, InvalidDateException;
    Travel getTravel(String travelUserEmail, Date date) throws InvalidUserException, InvalidDateException, InvalidTravelException;
    User getUserWithPass(String email, String pass) throws WrongPasswordException;
    Iterator<Travel> getAppRegisteredTravels();
    Iterator<Travel> getAppRegisteredTravelsOnDate(Date date);
    Iterator<Travel> getUserTravels();
    Iterator<Travel> getUserTravels(String user) throws UserIsNullException;

    static App load() {
        App app = null;
        try{
            ObjectInputStream file = new ObjectInputStream(
                    new FileInputStream("./App.ser")
            );
            app = (App) file.readObject();
            file.close();
        } catch (FileNotFoundException e) {
            return new AppImp();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return app;
    }


    void store();

    Iterator<Travel> getUserRides();

    Iterator<String> usersWithTravelOnDate(String date) throws NoRideOnDateException, InvalidDateException;

    Iterator<String> allRideMinInfo();
}
