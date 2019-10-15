class RideIterator {
	
	private Ride[] rides;
	private int currElement;
	private int numElements;
	
	public RideIterator(Ride[] rides, int numElements) {
		this.rides = rides;
		this.numElements = numElements;
		currElement = 0;
		
	}
	public boolean hasNext() {
		return currElement < numElements;
	}
	public Ride nextRide() {
		return rides[currElement++];
	}
	public void sort(){ 
		int len = numElements; 
        for (int i=1; i<len; ++i) { 
            Ride key = rides[i]; 
			int j = i-1;
            while (j>=0 && rides[j].getDateNumber() > key.getDateNumber()){ 
                rides[j+1] = rides[j]; 
                j = j-1; 
            } 
            rides[j+1] = key; 
        } 
    }
}