package app;

import dataStructures.Map;
import dataStructures.MapWithJavaClass;
import exception.*;

public class AppImp implements App {
    private User currentUser;
    private Map<String, User> users;
    private Map<String, Map<String,Travel>> travels;

    public AppImp() {
        // TODO: Change expected number of users from 0
        users = new MapWithJavaClass<String, User>(0);
        travels = new MapWithJavaClass<String, Map<String,Travel>>(0);
    }

    @Override
    public void addUser(String email, String name, String password) {
        users.insert(email, new UserImp(email, name, password));
    }

    // Classes outside this package should use the method that requires a password.
    protected User getUser(String email) {
        return users.find(email);
    }

    @Override
    public int getNumOfUsers() {
        return users.size();
    }

    @Override
    public void addTravel(String origin, String destination, String date, String time,
                          int travelDuration, int availableSeats)throws AlreadyHasRideOnDayException {

    }

    @Override
    public void delTravel(String date) throws NoTravelOnDateException, HasRidesException {
        Travel userTravel = currentUser.travelMap().find(date);
        if(userTravel == null)
            throw new NoTravelOnDateException(currentUser.name());
        if(userTravel.getRideUsers().hasNext())
            throw new HasRidesException(currentUser.name());
        travels.find(date).remove(currentUser.email());

        currentUser.delTravel(date);
    }
    private void dateCheck(String dateStr)throws InvalidDateException{
        String[] strArr = dateStr.split("-");
        int[] date = new int[strArr.length];
        for(int i = 0; i<strArr.length;i++){
           date[i] = Integer.parseInt(strArr[i]);
        }
        boolean niceDate = true;
        int febDays = 28;
        if ((2000 - date[2]) % 4 == 0)
            febDays += 1;
        int[] daysPerMonth = { 31, febDays, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        if (date[1] <= 12) {
            if (date[0] > daysPerMonth[date[1] - 1])
                throw new InvalidDateException();
        } else {
            throw new InvalidDateException();
        }
    }
    @Override
    public void addRide(String travelUserEmail, String date) throws InvalidDateException, SamePersonException, PlacedInQueueException, NoRideOnDateException, UserIsNullException, AlreadyHasRideOnDayException{
        dateCheck(date);
        if(travelUserEmail.equals(currentUser.email()))
            throw new SamePersonException();
        User travelUser = getUser(travelUserEmail);
        if(travelUser == null)
            throw new UserIsNullException();

        Travel travel = travelUser.travelMap().find(date);
        if(travel == null)
            throw new NoRideOnDateException();
        if(currentUser.travelMap().find(date)!=null || currentUser.)

        travel.addUserForTravel(currentUser);
        currentUser.addRide();
        if(travel.getNumOfAvailableSeats()<=0)
            throw new PlacedInQueueException(travel.getNumOfUsersQueueHold());

    }

    @Override
    public void delRide(String date) {

    }

    @Override
    public Travel getTravel(String travelUserEmail, String date) {
         /*
        travels.find(date).find(travelUserEmail); iria buscar a deslocacao em causa
        */
        return null;
    }

    @Override
    public void hasEmail(String email) throws HasEmailException {
        if(users.find(email)!=null) throw new HasEmailException();
    }

    @Override
    public void userExistsCheck(String email) throws UserIsNullException {
        if(users.find(email) == null) throw new UserIsNullException();
    }

    @Override
    public User getUserWithPass(String email, String pass) throws WrongPasswordException {
        User user = users.find(email);
        if(!user.password().equals(pass))
            throw new WrongPasswordException();
        return user;
    }

    @Override
    public void removeRide(String date) throws NoRideOnDateException, HasRidesException{
        Travel inDate = currentUser.travelMap().find(date);
        if(inDate == null)
            throw new NoRideOnDateException();
        if(inDate.getRideUsers().hasNext())
            throw new HasRidesException(currentUser.name());
        inDate.remove(date);
    }

    /*    private UserImp[] accounts;
    private int personCount;
    private Map<String, UserImp> users;
    private UserImp currentUser;
    private Map<String,Map<String, TravelImp>> itineraries;

    public AppImp() {
        accounts = new UserImp[0];
        users = new HashMap<>();
        personCount = 0;
    }

    public Boolean repeatedEmail(String emailToCheck) {
        boolean isRepeated = false;
        for (String s : users.keySet()) {
            if (s.equals(emailToCheck)) {
                isRepeated = true;
                break;
            }
        }
        return isRepeated;
    }

    public void createAccount(String email, String name, String password) {
    	if(accounts.length -2 <= personCount) {
    		accounts = increaseAccounts();
    	}
        users.put(email,new UserImp(email,name,password));
        accounts[personCount] = new UserImp(email, name, password);
        personCount++;
    }

    public UserImp getPersonFromEmail(String emailToCheck) {
        UserImp lPerson = null;
        for (int i = 0; i < personCount; i++) {
            if (accounts[i].getEmail().equals(emailToCheck)) {
                lPerson = accounts[i];
            }
        }

        return lPerson;
    }

    private UserImp[] increaseAccounts() {
        UserImp[] bigAccounts = new UserImp[accounts.length + 20];
        for (int i = 0; i < personCount; i++) {
            bigAccounts[i] = accounts[i];
        }
        return bigAccounts;
    }

    public int[] dateFromString(String sDate) {
        int[] iaDate = new int[3];
        String[] saDate = sDate.split("-");
        for (int i = 0; i < 3; i++) {
            iaDate[i] = Integer.parseInt(saDate[i].trim());
        }
        return iaDate;
    }

    public int getUserCount() {
        return personCount;
    }

    public UserImp getPersonFromIndex(int index) {
        return accounts[index];
    }

    public void sortAccounts() {
        int len = personCount; 
        for (int i=1; i<len; ++i) { 
            UserImp key = accounts[i];
            int j = i-1;
            
            while (j>=0 && accounts[j].getEmail().compareTo(key.getEmail()) > 0){
                accounts[j+1] = accounts[j]; 
                j = j-1; 
            } 
            accounts[j+1] = key; 
        } 
    }*/

}