package app;

import dataStructures.Date;
import dataStructures.Iterator;
import app.exception.UserNotOnTravelException;

import java.io.Serializable;

/**
 * @author Rodrigo Rosa
 */
public interface Travel extends Serializable {
    String getTravelDriverEmail();
    String getOrigin();
    String getDestination();
    Date getDate();
    String getTime();
    int getDuration();
    int getNumOfAvailableSeats();
    Iterator<User> getRideUsers();
    int getNumOfUsersQueueHold();
    void addUserForTravel(User user); // missing params | updated, but needs review
    void delUserFromTravel(User user) throws UserNotOnTravelException; // missing params
    boolean hasRiders();
}
