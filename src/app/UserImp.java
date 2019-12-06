package app;

import app.exception.AlreadyHasRideOnDayException;
import dataStructures.*;
import app.exception.NoRideOnDateException;
import app.exception.UserNotOnTravelException;

public class UserImp implements User {

    private String email;
    private String name;
    private String password;
    private int loginNumber;
    private Map<Date, Travel> travels;
    private Map<Date, Travel> rides;

    public UserImp(String email, String name, String password) {
        this.email= email;
        this.name = name;
        this.password = password;
        travels = new AVL<Date,Travel>();
        rides = new BST<Date, Travel>();
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String email() {
        return email;
    }

    @Override
    public String password() {
        return password;
    }

    @Override
    public int loginNumber() {
        return loginNumber;
    }

    @Override
    public int numberOfTravels() {
        return travels.size();
    }

    @Override
    public Iterator<Travel> travels() {
        return travels.values();
    }

    @Override
    public Iterator<Travel> rides() {
        return rides.values();
    }

    @Override
    public void incLoginNum() {
        loginNumber++;
    }

    @Override
    public void addTravel(Travel travel) {
        travels.insert(travel.getDate(), travel);
    }

    @Override
    public void delTravel(Date date) {
        travels.remove(date);
    }

    @Override
    public Travel getTravel(Date date) {
        return travels.find(date);
    }

    @Override
    public void addRide(Travel travelToRide) throws AlreadyHasRideOnDayException {
        if (rides.find(travelToRide.getDate()) != null || travels.find(travelToRide.getDate())!=null)
           throw new AlreadyHasRideOnDayException(name);
        rides.insert(travelToRide.getDate(), travelToRide);
    }

    @Override
    public void delRide(Date date) throws NoRideOnDateException {
        if (rides.find(date) == null) {
            throw new NoRideOnDateException(name);
        }
        Travel removedRide = rides.remove(date);
        try {
            removedRide.delUserFromTravel(this);
        } catch (UserNotOnTravelException e) {
            System.out.println("Dude how did you even get here");
        }
    }

    @Override
    public Map<String, Travel> travelMap() {
        return null;
    }

    @Override
    public Map<String, Travel> rideMap() {
        return null;
    }

    @Override
    public boolean hasTravelOnDate(Date date) {
        return travels.find(date) != null;
    }

    @Override
    public boolean hasRideOnDate(Date date) {
        return rides.find(date) != null;
    }

}