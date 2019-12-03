package app;

import dataStructures.Date;
import dataStructures.Iterator;
import dataStructures.Map;
import exception.NoRideOnDateException;

import java.io.Serializable;

/**
 * @author Rodrigo Rosa and Tiago Teles
 */
public interface User extends Serializable{
    String name();
    String email();
    String password();
    int loginNumber();
    int numberOfTravels();
    Iterator<Travel> travels();
    Iterator<Travel> rides();
    Map<String,Travel> travelMap();
    Map<String,Travel> rideMap();
    void incLoginNum();
    void addTravel(Travel travel); // Missing parameters
    void delTravel(Date date); // Missing parameters
    Travel getTravel(Date date);
    void addRide(Travel travelToRide) throws AlreadyHasRideOnDayException; // Missing parameters
    void delRide(Date date) throws NoRideOnDateException; // Missing parameters
    boolean hasTravelOnDate(Date date);
    boolean hasRideOnDate(Date date);
}
