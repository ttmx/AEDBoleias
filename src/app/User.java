package app;

import dataStructures.Iterator;
import dataStructures.Map;
import exception.AlreadyHasRideOnDayException;

/**
 * @author Rodrigo Rosa
 */
public interface User {
    String name();
    String email();
    String password();
    int loginNumber();
    int numberOfTravels();
    Iterator travels();
    Iterator rides();
    Map<String,Travel> travelMap();
    Map<String,Travel> rideMap();
    void incLoginNum();
    void addTravel(Travel travel); // Missing parameters
    void delTravel(String date); // Missing parameters
    void addRide(Travel travelToRide) throws AlreadyHasRideOnDayException; // Missing parameters
    void delRide(String date); // Missing parameters

}
