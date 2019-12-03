package app;

import dataStructures.*;
import exception.NoRideOnDateException;
import exception.UserNotOnTravelException;

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
        // TODO: Change the expected value from the maps
        travels = new SortedMapWithJavaClass<Date,Travel>();
        rides = new SortedMapWithJavaClass<Date, Travel>();
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

    // Anything down right now is old trash.


    /*public RideIterator createRideIterator() {
        RideIterator lIterator = new RideIterator(rides,rideCount);
        return lIterator;
    }
    public TravelImp getRideFromDate(int[] date){
        TravelImp lRide = null;
        for(int i = 0; i < rideCount; i++){
            if(rides[i].getDate()[0] == date[0] && rides[i].getDate()[1] == date[1] && rides[i].getDate()[2] == date[2]){
                lRide = rides[i];
            }
        }
        return lRide;
    }

    public TravelImp[] increaseRides() {
        TravelImp[] bigRides = new TravelImp[rides.length + 2];
        for (int i = 0; i < rideCount; i++) {
            bigRides[i] = rides[i];
        }
        return bigRides;
    }

    // return 0 if good,1 if invalid date,2 if already registered....
    public int newRide(String origin, String destination, int[] date, int hour, float duration, int seats) {
        int lErrorCode = 0;
        if (!isDateValid(date) || hour > 24 || hour < 0 || duration < 0 || seats < 0) {
            lErrorCode = 1;
        } else if (isRideAlreadyRegistered(date)) {
            lErrorCode = 2;
        } else {
            rides = increaseRides();
            rides[rideCount] = new TravelImp(origin, destination, date, hour, duration, seats);
            rideCount++;
        }
        return lErrorCode;
    }

    public boolean isRideAlreadyRegistered(int[] date) {
        boolean lCheck = false;
        RideIterator lRI = createRideIterator();
        lRI.sort();
        for (int i = 0; i < rideCount; i++) {
            TravelImp lRide = lRI.nextRide();
            if(lRide == null) {
                lCheck = false;
            }else if (lRide.getDate()[0] == date[0] && lRide.getDate()[1] == date[1] && lRide.getDate()[2] == date[2]) {
                lCheck = true;
            }
        }
        return lCheck;
    }
    public int removeRide(int[] date){
        int errorCode = 0;
        if(!isDateValid(date)){
            errorCode = 1;
        }else if(!isRideAlreadyRegistered(date)){
            errorCode = 2;
        }else if(hasPassengers(IndexFromDate(date))){
            errorCode = 3;
        }

        if(errorCode == 0){
            int index = IndexFromDate(date);
            rides[rideCount] = rides[index];
            for(int i = index; i < rideCount-1;i++){
                rides[i] = rides[i+1];
            }
            rideCount--;
        }
        return errorCode;
    }
    private int IndexFromDate(int[] date){
        int index = -1;
        for(int i = 0; i < rideCount;i++){
            if(rides[i].equals(getRideFromDate(date))){
                index = i;
                break;
            }
        }
        return index;
    }
    private boolean hasPassengers(int index) {
        return ((rides[index].getEmptySeats() -rides[index].getSeats()) != 0);
    }

    public boolean isDateValid(int[] date) {

        boolean niceDate = true;
        int febDays = 28;
        if ((2000 - date[2]) % 4 == 0)
            febDays += 1;
        int[] daysPerMonth = { 31, febDays, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        if (date[1] <= 12) {
            if (date[0] > daysPerMonth[date[1] - 1])
                niceDate = false;
        } else {
            niceDate = false;
        }

        return niceDate;
    }*/
}