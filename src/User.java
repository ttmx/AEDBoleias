//Refered to as PObj

class User {
    private String email;
    private String name;
    private String pw;
    private Itinerary[] rides;
    private int rideCount;
    public User() {
    	
    }
    public User(String email, String name, String pw) {
        this.email= email;
        this.name = name;
        this.pw = pw;
        rides = new Itinerary[0];
        rideCount = 0;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPw() {
        return pw;
    }
    public int getRideCount() {
    	return rideCount;
    }

    public RideIterator createRideIterator() {
    	RideIterator lIterator = new RideIterator(rides,rideCount);
    	return lIterator;
    }
    public Itinerary getRideFromDate(int[] date){
        Itinerary lRide = null;
        for(int i = 0; i < rideCount; i++){
            if(rides[i].getDate()[0] == date[0] && rides[i].getDate()[1] == date[1] && rides[i].getDate()[2] == date[2]){
                lRide = rides[i];
            }
        }
        return lRide;
    }

    public Itinerary[] increaseRides() {
        Itinerary[] bigRides = new Itinerary[rides.length + 2];
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
            rides[rideCount] = new Itinerary(origin, destination, date, hour, duration, seats);
            rideCount++;
        }
        return lErrorCode;
    }

    public boolean isRideAlreadyRegistered(int[] date) {
        boolean lCheck = false;
        RideIterator lRI = createRideIterator();
        lRI.sort();
        for (int i = 0; i < rideCount; i++) {
        	Itinerary lRide = lRI.nextRide();
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
    }
}