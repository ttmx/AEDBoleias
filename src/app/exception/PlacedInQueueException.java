package app.exception;

public class PlacedInQueueException extends Exception {
    public PlacedInQueueException(int numOfUsersQueueHold) {
       super(""+numOfUsersQueueHold);
    }
}
