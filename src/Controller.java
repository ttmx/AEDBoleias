//Referred to as CObj

class Controller {
    private Person[] accounts;
    private int personCount;

    public Controller() {
        accounts = new Person[0];
        personCount = 0;
    }

    public Boolean repeatedEmail(String emailToCheck) {
        boolean isRepeated = false;
        if (personCount != 0) {
            for (int i = 0; i < personCount; i++) {
                if (accounts[i].getEmail().equals(emailToCheck)) {
                    isRepeated = true;
                }
            }
        }
        return isRepeated;
    }

    public Boolean createAccount(String email, String name, String password) {
    	if(accounts.length -2 <= personCount) {
    		accounts = increaseAccounts();
    	}
        
        accounts[personCount] = new Person(email, name, password);
        personCount++;
        return true;
    }

    public Person getPersonFromEmail(String emailToCheck) {
        Person lPerson = null;
        for (int i = 0; i < personCount; i++) {
            if (accounts[i].getEmail().equals(emailToCheck)) {
                lPerson = accounts[i];
            }
        }
        return lPerson;
    }

    private Person[] increaseAccounts() {
        Person[] bigAccounts = new Person[accounts.length + 20];
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

    public Person getPersonFromIndex(int index) {
        return accounts[index];
    }

    public void sortAccounts() {
        int len = personCount; 
        for (int i=1; i<len; ++i) { 
            Person key = accounts[i]; 
            int j = i-1;
            
            while (j>=0 && accounts[j].getEmail().compareTo(key.getEmail())==1){ 
                accounts[j+1] = accounts[j]; 
                j = j-1; 
            } 
            accounts[j+1] = key; 
        } 
    }

}