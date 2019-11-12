package app;

import dataStructures.Iterator;
import dataStructures.Map;
import exception.AlreadyHasRideOnDayException;
import exception.NoRideOnDateException;

import java.io.Serializable;

/**
 * @author Rodrigo Rosa
 */
public interface User {
    String name();
    String email();
    String password();
    int loginNumber();
    int numberOfTravels();
    Iterator<Travel> travels();
    Iterator rides();
    Map<String,Travel> travelMap();
    Map<String,Travel> rideMap();
    void incLoginNum();
    void addTravel(Travel travel); // Missing parameters
    void delTravel(String date); // Missing parameters
    Travel getTravel(String date);
    void addRide(Travel travelToRide) throws AlreadyHasRideOnDayException; // Missing parameters
    void delRide(String date) throws NoRideOnDateException; // Missing parameters
    boolean hasTravelOnDate(String date);
    boolean hasRideOnDate(String date);
}
