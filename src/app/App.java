package app;
import dataStructures.Iterator;
import app.exception.*;

import java.io.*;

/**
 * @author Tiago Teles 54953
 */
public interface App extends Serializable {
    //O(1)
    boolean hasSession();

    //O(1)
    String getCurrentUserId();

    //O(1)
    String getCurrentUserName();

    //O(1)
    boolean hasEmail(String email);
    //O(log(nUsers))
    void addUser(String email, String name, String password);

    //O(1)
    int getNumOfUsers();

    //O(1)
    boolean matchesPw(String email, String password);

    //O(1)
    void setCurrentUser(String email);

    //O(1)
    int getUserLoginNum();

    //O(1)
    String closeSession();
    //O(log(numberOfTravelDates+log(numberOfTravelsInDate))
    void addTravel(String origin, String destination, Date date, String time, int travelDuration, int availableSeats) throws InvalidDateException, AlreadyHasEntryOnDayException;
    //O(1)
    int getUserTravelsNum();

    //O(log(numberOfTravelDates)+log(numberOfTravelsInDate))
    void delTravel(Date date) throws InvalidDateException, InvalidTravelException, HasRidesException;

    //O(log(numberOfTravelDates)+log(numberOfTravelsInDate)+numOfUsersInRide)
    void addRide(String travelUserEmail, Date date) throws InvalidDateException,SamePersonException,PlacedInQueueException,NoRideOnDateException,UserIsNullException, AlreadyHasRideOnDayException;

    //O(log(numberOfTravelDates)+log(numberOfTravelsInDate)+numOfUsersInRide)
    void delRide(Date date) throws NoRideOnDateException, InvalidDateException;

    //O(log(numberOfTravelDates)+log(numberOfTravelsInDate))
    Travel getTravel(String travelUserEmail, Date date) throws InvalidUserException, InvalidDateException, InvalidTravelException;

    //O(1)
    Iterator<Travel> getUserTravels();

    //O(log(numberOfUsers))
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

    //O(1)
    Iterator<Travel> getUserRides();

    //O(log(datesWithTravel)+numberOfUsersWithTravelOnDate)
    Iterator<String> usersWithFreeTravelOnDate(String date) throws NoRideOnDateException, InvalidDateException;

    //O(numberOfTotalTravels)
    Iterator<String> allRideMinInfo();
}
