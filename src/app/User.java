package app;

import app.exception.AlreadyHasRideOnDayException;
import dataStructures.Iterator;
import app.exception.NoRideOnDateException;

import java.io.Serializable;

/**
 * @author Tiago Teles
 */
public interface User extends Serializable{
    //O(1)
    String name();
    //O(1)
    String email();
    //O(1)
    String password();
    //O(1)
    int loginNumber();
    //O(1)
    int numberOfTravels();
    //O(1)
    Iterator<Travel> travels();
    //O(1)
    Iterator<Travel> rides();
    //O(1)
    void incLoginNum();
    //O(log(size))
    void addTravel(Travel travel);
    //O(log(size))
    void delTravel(Date date);
    //O(log(size))
    Travel getTravel(Date date);
    //O(log(size))
    void addRide(Travel travelToRide) throws AlreadyHasRideOnDayException;
    //O(log(size))
    void delRide(Date date) throws NoRideOnDateException;
    //O(log(size))
    boolean hasTravelOnDate(Date date);
    //O(log(size))
    boolean hasRideOnDate(Date date);
}
