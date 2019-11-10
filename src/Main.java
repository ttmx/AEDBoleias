/*
 * Project: FCT Boleias
 * Description: Main project for Algorithms & Data Structures classes.
 * Version:
 * Copyright (C) 2019, Rodrigo Rosa <https://github.com/v0n3x> (Student ID: 56505)
 * Copyright (C) 2019, Tiago Teles <https://github.com/ttmx> (Student ID: 54953)
 * All rights reserved.
 */

import app.App;
import app.AppImp;
import app.UserImp;

import java.util.Locale;
import java.util.Scanner;

class Main {
	//Constantes
    private static final String HELP = "ajuda";
    private static final String END = "termina";
    private static final String REGISTER = "regista";
    private static final String LOGIN = "entrada";
    private static final String LOGOFF = "sai";
    private static final String NEWRIDE = "nova";
    private static final String LISTRIDES = "lista";
    private static final String GETINFO = "consulta";
    private static final String TAKEARIDE = "boleia";
    private static final String REMOVERIDE = "remove";
    private static final String BYEBYE = "Obrigado. Ate a proxima.";
    private static final String INV_COMMAND = "Comando invalido.";
    private static final String REG_SUCCESS = "Registo efetuado.";
    private static final String MOV_NOT_EXIST = "Deslocacao nao existe.";

    public static void main(String[] args) {
    	
        Locale.setDefault(new Locale("en", "US"));
        App CObj = new AppImp();
        Scanner scan = new Scanner(System.in);
        //Inicio do Programa
        //mainMenu(scan, CObj);
    }
    //Estado de menu principal(sem sessao iniciada)
  /*  public static void mainMenu(Scanner scan, App CObj) {

        String lCmd = "";
        //Enquanto o comando nao for termina
        while (!lCmd.equals(END)) {
        	//Criacao do prompt
            System.out.print("> ");
            //Leitura dos comandos
            lCmd = readCommand(scan);
            //Processamento dos comandos
            switch (lCmd) {
            case HELP:
                printMenuHelp();
                scan.nextLine();
                break;
            case REGISTER:
                register(scan, CObj);
                break;
            case LOGIN:
                login(scan, CObj);
                break;
            case END:
                printEnd();
                break;
            default:
                printInvalidCmd();
                scan.nextLine();
                break;
            }

        }
    }
    //Estado de sessao iniciada
    //@param O objecto Person definido no login
    public static void loggedIn(UserImp personObj, Scanner scan, App CObj) {
        String lCmd = "";
        //Enquanto que o comando nao for sai
        while (!lCmd.equals(LOGOFF)) {
        	//Criacao do prompt
            System.out.print(personObj.getEmail() + " > ");
            lCmd = readCommand(scan);
            switch (lCmd) {
            case LOGOFF:
                logoff();
                break;
            case NEWRIDE:
                newRide(personObj, scan, CObj);
                break;
            case LISTRIDES:
                listRides(scan, CObj, personObj);
                break;
            case GETINFO:
                getInfo(scan, CObj);
                break;
            case TAKEARIDE:
                takeARide(personObj, scan, CObj);
                break;
            case REMOVERIDE:
            	removeRide(scan, personObj,CObj);
                break;
            case HELP:
                printLoggedInHelp();
                break;
            default:
                printInvalidCmd();
                scan.nextLine();
                break;
            }

        }

    }
    //Leitura e a formatacao do comando
    public static String readCommand(Scanner scan) {
        String lRead = "";
        lRead = scan.next().toLowerCase();

        return lRead;
    }
    //Verificao da password
    //@param uma password
    private static boolean isPwValid(String pw) {
        boolean lIsValid = true;
        //Verifica se cada caracter e um digito ou letra
        //se algum dos caracteres for algo que nao um digito ou letra entao o metodo retorna falso
        for (int i = 0; i < pw.length(); i++) {
            if (!Character.isLetterOrDigit(pw.charAt(i))) {
                lIsValid = false;
            }
        }
        return (pw.length() <= 6 && pw.length() >= 4 && lIsValid);
    }
    
    private static void printInvalidCmd() {
        System.out.println(INV_COMMAND);
    }
    //Registo de uma conta
    //@param O objeto controlador 
    private static void register(Scanner scan, App CObj) {
        boolean lHasCreated = false;
        //Contador de passwords falhadas
        int lFailCount = 0;
        //Leitura do email 
        String lEmail = scan.next();
        String lName = "";
        String lPass = "";
        scan.nextLine();
        //Verificacao da existencia de um email igual no programa
        boolean repeatedEmail = CObj.repeatedEmail(lEmail);
        //Se nao existirem nenhumas contas com esse email
        if (!repeatedEmail) {
        	//Leitura do nome
            System.out.print("nome (maximo 50 caracteres): ");
            lName = scan.nextLine();
            //Ciclo responsavel pela verificacao da password
            while (lFailCount < 3 && !lHasCreated) {
                System.out.print("password (entre 4 e 6 caracteres - digitos e letras): ");
                lPass = scan.next();
                scan.nextLine();
                //se password for valida
                if (isPwValid(lPass)) {
                	//Cria a conta no objeto controlador 
                    CObj.createAccount(lEmail, lName, lPass);
                    System.out.println(REG_SUCCESS);
                    lHasCreated = true;
                } else {
                    lFailCount++;
                    System.out.println("Password incorrecta.");
                }
            }
        }
        if (repeatedEmail) {
            System.out.println("Utilizador ja existente.");
        }
        if (!lHasCreated) {
            System.out.println("Registo nao efetuado.");
        }

    }

    private static void printMenuHelp() {
        System.out.println("ajuda - Mostra os comandos existentes");
        System.out.println("termina - Termina a execucao do programa");
        System.out.println("regista - Regista um novo utilizador no programa");
        System.out.println("entrada - Permite a entrada (\"login\") dum utilizador no programa");
    }

    private static void printLoggedInHelp() {
        System.out.println("ajuda - Mostra os comandos existentes");
        System.out.println("sai - Termina a sessao deste utilizador no programa");
        System.out.println("nova - Regista uma nova deslocacao");
        System.out.println("lista - Lista todas ou algumas deslocacoes registadas");
        System.out.println("boleia - Regista uma boleia para uma dada deslocacao");
        System.out.println("consulta - Lista a informacao de uma dada deslocacao");
        System.out.println("retira - Retira uma dada boleia");
        System.out.println("remove - elimina uma dada deslocacao");
    }

    private static void login(Scanner scan, App CObj) {
        String lEmail = scan.next();
        scan.nextLine();
        String lPass = "";
        UserImp lPerson = CObj.getPersonFromEmail(lEmail);
        boolean lLoggedIn = false;
        if (lPerson != null) {
            for (int i = 0; !lLoggedIn && i < 3; i++) {
                System.out.print("password: ");
                lPass = scan.next();
                scan.nextLine();
                if (lPass.equals(lPerson.getPw())) {
                    lLoggedIn = true;
                    loggedIn(lPerson, scan, CObj);
                } else {
                    System.out.println("Password incorrecta.");
                }
            }
        } else {
            System.out.println("Utilizador nao existente.");
        }

    }

    private static void logoff() {
        System.out.println(BYEBYE);
    }

    private static void getInfo(Scanner scan, App CObj) {
        String lEmail = scan.next().trim();
        int[] lDate = CObj.dateFromString(scan.next().trim());
        UserImp lPerson = CObj.getPersonFromEmail(lEmail);
        CObj.sortAccounts();
        boolean hasFound = false;
        if (lPerson == null) {
            System.out.println(MOV_NOT_EXIST);
        } else {
            RideIterator lRI = lPerson.createRideIterator();
            Itinerary lRide;
            if (lRI.hasNext()) {
                do {
                    lRide = lRI.nextRide();
                    if (lRide.getDate()[0] == lDate[0] && lRide.getDate()[1] == lDate[1]&& lRide.getDate()[2] == lDate[2]) {
                        printRideInfo(lRide, lPerson, false, true);
                        hasFound = true;
                    }
                } while (lRI.hasNext());
            } if(!hasFound){
                System.out.println(MOV_NOT_EXIST);
            }
        }
    }

    private static void listRides(Scanner scan, App CObj, UserImp personObj) {
        String lDate = scan.nextLine().trim();

        int[] laDate;
        if (!lDate.equals("")) {
            laDate = CObj.dateFromString(lDate);
            listRidesWDate(laDate, CObj,personObj);
        } else {
            RideIterator lIterator = personObj.createRideIterator();
            CObj.sortAccounts();
            lIterator.sort();
            if (lIterator.hasNext()) {
                do {
                    Itinerary lRide = lIterator.nextRide();
                    printRideInfo(lRide, personObj, false, false);
                    System.out.print("\n");
                } while (lIterator.hasNext());
            }else{
                System.out.println(personObj.getName()+" nao tem deslocacoes registadas.");
            }
        }
    }

    private static void listRidesWDate(int[] date, App CObj, UserImp personObj) {
        int lUserCount = CObj.getUserCount();
        UserImp lPerson = null;
        boolean hasFound = false;
        if(!personObj.isDateValid(date)) {
        	
        	System.out.println("Data invalida.");      	
        }else{
            for (int i = 0; i < lUserCount; i++) {
            	CObj.sortAccounts();
                lPerson = CObj.getPersonFromIndex(i);
                
                RideIterator lIterator = lPerson.createRideIterator();
                lIterator.sort();
                while (lIterator.hasNext()) {
                    Itinerary lRide = lIterator.nextRide();
                    if (date[0] == lRide.getDate()[0] && date[1] == lRide.getDate()[1] && date[2] == lRide.getDate()[2]) {
                        printRideInfo(lRide, lPerson, true, false);
                        System.out.print("\n");
                        hasFound = true;
                    }
                }
                 
            }
            if(!hasFound) {
            	System.out.println(personObj.getName() + " nao existem deslocacoes registadas para " + date[0]+"-"+ date[1]+"-"+ date[2]+".");
            }
        }
    }

    private static void removeRide(Scanner scan, UserImp pObj, App CObj) {
        switch(pObj.removeRide(CObj.dateFromString(scan.next().trim()))){
            case 0:
                System.out.println("Deslocacao removida.");
                break;
            case 1:
                System.out.println("Data invalida.");
                break;
            case 2:
                System.out.println("Deslocacao nao existe.");
                break;
            case 3:
                System.out.println(pObj.getName() + " ja nao pode eliminar esta deslocacao.");
                break;

        }
    }

    private static void newRide(UserImp personObj, Scanner scan, App CObj) {
        scan.nextLine();
        String lOrigin = scan.nextLine();

        String lDestination = scan.nextLine();

        String lDate = scan.next();
        int[] laDate = CObj.dateFromString(lDate);

        int lHour = scan.nextInt();

        float lDuration = scan.nextFloat();
        int lSeats = scan.nextInt();

        // 0 if good, 1 if invalid data, 2 if already registered
        switch (personObj.newRide(lOrigin, lDestination, laDate, lHour, lDuration, lSeats)) {
        case 0:
            System.out.println("Deslocacao registada. Obrigado " + personObj.getName() + ".");
            break;
        case 1:
            System.out.println("Dados invalidos.");
            System.out.println("Deslocacao nao registada.");
            break;
        case 2:
            System.out.println(personObj.getName() + " ja tem uma deslocacao registada nesta data");
            System.out.println("Deslocacao nao registada.");
            break;
        default:
            System.out.println("I have absolutely no idea how you got here");
            break;
        }

    }

    private static void takeARide(UserImp pObj, Scanner scan, App CObj) {
        String lEmail = scan.next().trim();
        int[] lDate = CObj.dateFromString(scan.next().trim());
        UserImp lPerson = CObj.getPersonFromEmail(lEmail);

        if (!pObj.isDateValid(lDate)) {
            System.out.println("Data invalida.");
        } else if (lPerson == null) {
            System.out.println("Utilizador inexistente.");
        } else if (!lPerson.isRideAlreadyRegistered(lDate)) {
            System.out.println("Deslocacao nao existe.");
        } else if (lPerson.getEmail().equals(pObj.getEmail())) {
            System.out.println(pObj.getName() + " nao pode dar boleia a si propria. Boleia nao registada.");
        } else {
            //System.out.println("Empty Seats: " + lPerson.getRideFromDate(lDate).getEmptySeats() + "; date: " + lPerson.getRideFromDate(lDate).getDateNumber()+ "-" + lDate[1] + "-" + lDate[2]);
            if (lPerson.getRideFromDate(lDate).getEmptySeats() == 0) {
                System.out.println(pObj.getName() + " nao existe lugar. Boleia nao registada.");
            } else {
                lPerson.getRideFromDate(lDate).incPerson();
                System.out.println("Boleia registada.");
            }
        }
    }

    private static void printEnd() {
        System.out.println(BYEBYE);
    }

    private static void printRideInfo(Itinerary ride, UserImp person, boolean needDriver, boolean freeSpots) {
        if (needDriver) {
            System.out.println(person.getEmail());
        }
        System.out.println(ride.getOrigin());
        System.out.println(ride.getDestination());
        System.out.print(ride.getDate()[0] + "-" + ride.getDate()[1] + "-" + ride.getDate()[2]);
        System.out.print(" " + ride.getHour());
        System.out.print(" " + ride.getDuration());
        System.out.println(" " + ride.getSeats());
        if (!freeSpots) {
            System.out.println("Boleias registadas: " + (ride.getSeats() - ride.getEmptySeats()));
        } else {
            System.out.println("Lugares vagos: " + ride.getEmptySeats());
        }
    }*/

}