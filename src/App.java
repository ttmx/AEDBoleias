//Referred to as CObj

import java.util.HashMap;
import java.util.Map;

class App {
    private User[] accounts;
    private int personCount;
    private Map<String,User> users;
    private User currentUser;
    private Map<String,Map<String,Itinerary>> itineraries;

    public App() {
        accounts = new User[0];
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
        users.put(email,new User(email,name,password));
        accounts[personCount] = new User(email, name, password);
        personCount++;
    }

    public User getPersonFromEmail(String emailToCheck) {
        User lPerson = null;
        for (int i = 0; i < personCount; i++) {
            if (accounts[i].getEmail().equals(emailToCheck)) {
                lPerson = accounts[i];
            }
        }

        return lPerson;
    }

    private User[] increaseAccounts() {
        User[] bigAccounts = new User[accounts.length + 20];
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

    public User getPersonFromIndex(int index) {
        return accounts[index];
    }

    public void sortAccounts() {
        int len = personCount; 
        for (int i=1; i<len; ++i) { 
            User key = accounts[i];
            int j = i-1;
            
            while (j>=0 && accounts[j].getEmail().compareTo(key.getEmail()) > 0){
                accounts[j+1] = accounts[j]; 
                j = j-1; 
            } 
            accounts[j+1] = key; 
        } 
    }

}