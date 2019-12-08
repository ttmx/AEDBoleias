package app;

import app.exception.AlreadyHasRideOnDayException;
import dataStructures.*;
import app.exception.UserNotOnTravelException;

public class TravelImp implements Travel {
    private User travelAuthor;
    private String origin;
    private String destination;
    private Date date;
    private String time;
    private int duration;
    private int seatCap;
    private List<User> usersForTravel;
    private Queue<User> usersInQueueForTravel;

    public TravelImp(User travelAuthor, String origin, String destination, Date date,
                     String time, int duration, int seatCap) {
        this.travelAuthor = travelAuthor;
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.seatCap = seatCap;
        //SinglyLinkedList for no resizing and O(1) adding to end
        this.usersForTravel = new SinglyLinkedList<User>();
        //Queue in list for O(1)
        this.usersInQueueForTravel = new QueueInList<User>();
    }

    @Override
    public String getTravelDriverEmail() {
        return travelAuthor.email();
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
    public Date getDate() {
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
    public Iterator<User> getRideUsers() {
        return usersForTravel.iterator();
    }

    @Override
    public int getNumOfUsersQueueHold() {
        return usersInQueueForTravel.size();
    }

    @Override
    public void addUserForTravel(User user) {
        if (usersForTravel.size() >= seatCap) {
            usersInQueueForTravel.enqueue(user);
        } else {
            usersForTravel.addLast(user);
        }
    }

    @Override
    public void delUserFromTravel(User user) throws UserNotOnTravelException{
        int index = usersForTravel.find(user);
        boolean found = false;
        if(index==-1)
            throw new UserNotOnTravelException();
        usersForTravel.remove(index);
        if(getNumOfAvailableSeats() > 0 && usersInQueueForTravel.size()>0){
            User toAdd = usersInQueueForTravel.dequeue();
            while((toAdd.hasRideOnDate(date)||toAdd.hasTravelOnDate(date))&&usersInQueueForTravel.size()>0){
                if(!(toAdd.hasTravelOnDate(date)||toAdd.hasRideOnDate(date))){
                    found = true;
                }
                toAdd = usersInQueueForTravel.dequeue();
            }
            usersForTravel.addLast(toAdd);
            if(found) try {
                toAdd.addRide(this);
            } catch (AlreadyHasRideOnDayException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean hasRiders() {
        return usersForTravel.size() > 0;
    }

}