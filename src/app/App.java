package app;
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
    void addTravel(String origin, String destination, String date, String time, int travelDuration, int availableSeats) throws InvalidDateException, AlreadyHasEntryOnDayException;
    int getUserTravelsNum();
    void delTravel(String date) throws InvalidDateException, InvalidTravelException, HasRidesException;
    void addRide(String travelUserEmail, String date) throws InvalidDateException,SamePersonException,PlacedInQueueException,NoRideOnDateException,UserIsNullException, AlreadyHasRideOnDayException;
    void delRide(String date) throws NoRideOnDateException, InvalidDateException;
    Travel getTravel(String travelUserEmail, String date) throws InvalidUserException, InvalidDateException, InvalidTravelException;
    User getUserWithPass(String email, String pass) throws WrongPasswordException;
    Iterator<Travel> getAppRegisteredTravels();
    Iterator<Travel> getAppRegisteredTravelsOnDate(String date);
    Iterator<Travel> getUserTravels();

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
}
