package app;

import dataStructures.*;
import exception.UserNotOnTravelException;

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
        this.usersForTravel = new Array<User>();
        this.usersInQueueForTravel = new QueueInArray<User>(); // ou QueueInList idk?
        //dateAsNumber = date[0]+date[1]*100+date[2]*10000;
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
    public void delUserFromTravel(User user) throws UserNotOnTravelException{
        int index = usersForTravel.find(user);
        if(index==-1)
            throw new UserNotOnTravelException();
        usersForTravel.remove(index);
        while(!usersInQueueForTravel.isEmpty()){
            User u = usersInQueueForTravel.dequeue();
            if(!u.hasTravelOnDate(date)&&!u.hasRideOnDate(date)) {
                addUserForTravel(u);
                break;
            }
        }

    }

    @Override
    public boolean hasRiders() {
        return usersForTravel.size() > 0;
    }

}