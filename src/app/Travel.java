package app;

import dataStructures.Iterator;

/**
 * @author Rodrigo Rosa
 */
public interface Travel {
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
    void delUserFromTravel(); // missing params
}
