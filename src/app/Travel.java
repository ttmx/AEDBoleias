package app;

import dataStructures.Iterator;
import exception.UserNotOnTravelException;

import java.io.Serializable;

/**
 * @author Rodrigo Rosa
 */
public interface Travel extends Serializable {
    User getTravelAuthor();
    String getOrigin();
    String getDestination();
    String getDate();
    String getTime();
    int getDuration();
    int getNumOfAvailableSeats();
    Iterator getRideUsers();
    int getNumOfUsersQueueHold();
    void addUserForTravel(User user); // missing params | updated, but needs review
    void delUserFromTravel(User user) throws UserNotOnTravelException; // missing params
    boolean hasRiders();
}
