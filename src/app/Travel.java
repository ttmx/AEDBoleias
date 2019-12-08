package app;

import dataStructures.Iterator;
import app.exception.UserNotOnTravelException;

import java.io.Serializable;

/**
 * @author Tiago Teles 54953
 */
public interface Travel extends Serializable {
    //O(1)
    String getTravelDriverEmail();
    //O(1)
    String getOrigin();
    //O(1)
    String getDestination();
    //O(1)
    Date getDate();
    //O(1)
    String getTime();
    //O(1)
    int getDuration();
    //O(1)
    int getNumOfAvailableSeats();
    //O(1)
    Iterator<User> getRideUsers();
    //O(1)
    int getNumOfUsersQueueHold();
    //O(1)
    void addUserForTravel(User user);
    //O(numberOfUsersInTravel)
    void delUserFromTravel(User user) throws UserNotOnTravelException;
    //O(1)
    boolean hasRiders();
}
