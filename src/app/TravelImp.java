package app;

import dataStructures.*;

public class TravelImp implements Travel {
    private User travelAuthor;
    private String origin;
    private String destination;
    private String date;
    private String time;
    private int duration;
    private int seatCap;
    private List<User> usersForTravel;
    private Queue<User> usersInQueueForTravel;

    public TravelImp(User travelAuthor, String origin, String destination, String date,
                     String time, int duration, int seatCap) {
        this.travelAuthor = travelAuthor;
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.seatCap = seatCap;
        this.usersForTravel = new Array<User>();
        this.usersInQueueForTravel = new QueueInArray<>(); // ou QueueInList idk?
        //dateAsNumber = date[0]+date[1]*100+date[2]*10000;
    }

    @Override
    public User getTravelAuthor() {
        return travelAuthor;
    }

    @Override
    public String getOrigin() {
        return origin;
    }

    @Override
    public String getDestination() {
        return destination;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public String getTime() {
        return time;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public int getNumOfAvailableSeats() {
        return seatCap - usersForTravel.size();
    }

    @Override
    public Iterator getRideUsers() {
        return usersForTravel.iterator();
    }

    @Override
    public int getNumOfUsersQueueHold() {
        return usersInQueueForTravel.size();
    }

    // Some review and needs throw.
    @Override
    public void addUserForTravel(User user) {
        if (usersForTravel.size() == seatCap) {
            usersInQueueForTravel.enqueue(user);
        }
        else {
            usersForTravel.addLast(user);
        }
    }

    @Override
    public void delUserFromTravel() {

    }

    /*
    public int getHour() {
    	return hour;
    }
    public String getOrigin() {
    	return origin;
    }
    public String getDestination() {
        return destination;
    }

    public int[] getDate() {
        return date;
    }

    public float getDuration() {
        return duration;
    }

    public int getSeats() {
        return seats;
    }
    public int getEmptySeats() {
    	return emptySeats;
    }

    public UserImp getDriver() {
        return driver;
    }
    public int getDateNumber(){
        return dateAsNumber;
    }
    public boolean incPerson() {
    	boolean temp = false;
    	if(emptySeats > 0) {
            emptySeats--;
    		temp = true;
    	}
    	return temp;
    }*/
}