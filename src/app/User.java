package app;

import dataStructures.Iterator;

/**
 * @author Rodrigo Rosa
 */
public interface User {
    String getName();
    String getEmail();
    String getPassword();
    int getLoginNumber();
    int getNumberOfTravels();
    Iterator getTravels();
    Iterator getRides();
    void incLoginNum();
    void addTravel(Travel travel); // Missing parameters
    void delTravel(String date); // Missing parameters
    void addRide(Travel travelToRide); // Missing parameters
    void delRide(String date); // Missing parameters
}
