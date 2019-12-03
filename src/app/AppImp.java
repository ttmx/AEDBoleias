package app;

import dataStructures.*;
import exception.*;

import java.io.*;

public class AppImp implements App {

    static final long serialVersionUID = 0L;

    private boolean session;
    private User currentUser;
    private Map<String, User> users;
    private SortedMap<Date, SortedMap<String,Travel>> travels;

    public AppImp() {
        // TODO: Change expected number of users from 0
        session = false;
        users = new MapWithJavaClass<String, User>(); // It's expected more than 10000 users

        //<Date,<User,Travel>>
        travels = new SortedMapWithJavaClass<Date, SortedMap<String,Travel>>();
    }


    @Override
    public boolean hasSession() {
        return session;
    }

    @Override
    public String getCurrentUserId() {
        return currentUser.email();
    }

    @Override
    public String getCurrentUserName() {
        return currentUser.name();
    }

    @Override
    public void addUser(String email, String name, String password) {
        users.insert(email, new UserImp(email, name, password));
    }

    // Classes outside this package should use the method that requires a password.
    /*protected User getUser(String email) {
        return users.find(email);
    }*/

    @Override
    public int getNumOfUsers() {
        return users.size();
    }

    @Override
    public boolean matchesPw(String email, String password) {
        User user = users.find(email);
        return user.password().equals(password);
    }

    @Override
    public void setCurrentUser(String email) {
        currentUser = users.find(email);
        currentUser.incLoginNum();
        session = true;
    }

    @Override
    public int getUserLoginNum() {
        return currentUser.loginNumber();
    }

    @Override
    public String closeSession() {
        String name = currentUser.name();
        currentUser = null;
        session = false;
        return name;
    }

    @Override
    public void addTravel(String origin, String destination, Date date, String time, int travelDuration, int availableSeats) throws InvalidDateException, AlreadyHasEntryOnDayException {
        if (!date.isValid() || travelDuration <= 0 || availableSeats > 10 || availableSeats <= 0) {
            throw new InvalidDateException();
        }
        if (currentUser.hasTravelOnDate(date) || currentUser.hasRideOnDate(date)) {
            throw new AlreadyHasEntryOnDayException();
        }
        Travel travel = new TravelImp(currentUser, origin, destination, date, time, travelDuration, availableSeats);
        if(travels.find(date)==null){
            travels.insert(date, new SortedMapWithJavaClass<String, Travel>());
        }

        Map<String, Travel> travelsUserMap = travels.find(date);
        travelsUserMap.insert(currentUser.email(), travel);
        currentUser.addTravel(travel);
    }

    @Override
    public int getUserTravelsNum() {
        return currentUser.numberOfTravels();
    }

    @Override
    public void delTravel(Date date) throws InvalidDateException, InvalidTravelException, HasRidesException {

        if (!date.isValid()) {
            throw new InvalidDateException();
        }
        if (!currentUser.hasTravelOnDate(date)) {
            throw new InvalidTravelException();
        }
        Travel travel = currentUser.getTravel(date);
        if (travel.hasRiders()) {
            throw new HasRidesException();
        }
        Map<String, Travel> travelToRemove = travels.find(date);
        travelToRemove.remove(currentUser.email());
        currentUser.delTravel(date);
    }

    /*
    private boolean dateIsInvalid(String dateStr) {
        String[] strArr = dateStr.split("-");
        int[] date = new int[strArr.length];
        for (int i = 0; i < strArr.length; i++){
           date[i] = Integer.parseInt(strArr[i]);
        }

        boolean niceDate = true;
        int febDays = 28;

        if ((2000 - date[2]) % 4 == 0) {
            febDays += 1;
        }

        int[] daysPerMonth = { 31, febDays, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

        if (date[1] <= 12) {
            if (date[0] > daysPerMonth[date[1] - 1])
                niceDate = false;
        } else {
            niceDate = false;
        }
        return !niceDate;
    }*/

    @Override
    public void addRide(String travelUserEmail, Date date) throws InvalidDateException, SamePersonException, PlacedInQueueException, NoRideOnDateException, UserIsNullException, AlreadyHasRideOnDayException{

        if(!date.isValid())
            throw new InvalidDateException();

        User travelUser = users.find(travelUserEmail);
        if(travelUser == null)
            throw new UserIsNullException();

        if(!travelUser.hasTravelOnDate(date))
            throw new NoRideOnDateException();

        if(travelUserEmail.equals(currentUser.email()))
            throw new SamePersonException(currentUser.name());

        Travel travel = travelUser.getTravel(date);
        currentUser.addRide(travel);
        travel.addUserForTravel(currentUser);

        if(travel.getNumOfUsersQueueHold()>0)
            throw new PlacedInQueueException(travel.getNumOfUsersQueueHold());

    }

    @Override
    public void delRide(Date date) throws NoRideOnDateException, InvalidDateException {
       if(!date.isValid())
           throw new InvalidDateException();
       currentUser.delRide(date);
    }

    @Override
    public Travel getTravel(String travelUserEmail, Date date) throws InvalidUserException, InvalidDateException, InvalidTravelException {
        if (users.find(travelUserEmail) == null) {
            throw new InvalidUserException();
        }
        if (!date.isValid()) {
            throw new InvalidDateException();
        }
        if (!users.find(travelUserEmail).hasTravelOnDate(date)) {
            throw new InvalidTravelException();
        }
         /*
        travels.find(date).find(travelUserEmail); iria buscar a deslocacao em causa
        */
        return users.find(travelUserEmail).getTravel(date);
    }

    @Override
    public boolean hasEmail(String email) {
        return users.find(email) != null;
    }

    @Override
    public User getUserWithPass(String email, String pass) throws WrongPasswordException {
        User user = users.find(email);
        if(!user.password().equals(pass))
            throw new WrongPasswordException();
        return user;
    }

    @Override
    public Iterator<Travel> getAppRegisteredTravels() {
        return null;
    }

    @Override
    public Iterator<Travel> getAppRegisteredTravelsOnDate(Date date) {
        return null;
    }

    @Override
    public Iterator<Travel> getUserTravels() {
        return currentUser.travels();
    }

    @Override
    public Iterator<Travel> getUserTravels(String email) throws UserIsNullException {
        User toReturn = users.find(email);
        if(toReturn==null)
            throw new UserIsNullException();
        return toReturn.travels();
    }

    @Override
    public Iterator<Travel> getUserRides() {
        return currentUser.rides();
    }

    @Override
    public Iterator<String> usersWithTravelOnDate(String dateStr) throws NoRideOnDateException,InvalidDateException{
        Date date = new BasicDate(dateStr);
        if(!date.isValid())
            throw new InvalidDateException();
        Map<String,Travel> trMap = travels.find(date);
        if(trMap==null)
            throw new NoRideOnDateException();
        return trMap.keys();
    }

    @Override
    public Iterator<String> allRideMinInfo() {
        Iterator<SortedMap<String,Travel>> allMaps= travels.values();
        Iterator<Travel> smallTravels;
        List<String> allStrings = new Array<String>();
        Travel travel;
        while(allMaps.hasNext()){
            smallTravels = allMaps.next().values();
            while(smallTravels.hasNext()){
                travel = smallTravels.next();
                if(travel.getNumOfAvailableSeats()>0)
                    allStrings.addLast(travel.getDate().stringDate()+" "+travel.getTravelDriverEmail());
            }
        }
        return allStrings.iterator();
    }

    @Override
    public void store() {
        try{
            ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream("./App.ser"));
            file.writeObject(this);
            file.flush();
            file.close();
        }catch (IOException e){
            e.printStackTrace();
        }
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