class Ride {

    private String destination;
    private String origin;
    private int[] date;
    private int hour;
    private float duration;
    private int seats;
    private Person driver;
    private int emptySeats;
    private int dateAsNumber;

    public Ride(String origin,String destination, int[] date,int hour, float duration, int seats) {
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.hour = hour;
        this.duration = duration;
        this.seats = seats;
        this.emptySeats = seats;
        dateAsNumber = date[0]+date[1]*100+date[2]*10000;
    }
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

    public Person getDriver() {
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
    }
}