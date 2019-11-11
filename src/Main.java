/*
 * Project: FCT Boleias
 * Description: Main project for Algorithms & Data Structures classes.
 * Version:
 * Copyright (C) 2019, Rodrigo Rosa <https://github.com/v0n3x> (Student ID: 56505)
 * Copyright (C) 2019, Tiago Teles <https://github.com/ttmx> (Student ID: 54953)
 * All rights reserved.
 */

import app.*;
import exception.*;

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
    private static final String USER_NOT_EXIST = "Utilizador nao existente.";

    public static void main(String[] args) {
    	
        Locale.setDefault(new Locale("en", "US"));
        App app = new AppImp();
        Scanner scan = new Scanner(System.in);
        //Inicio do Programa
        mainMenu(scan, app);
    }
    //Estado de menu principal(sem sessao iniciada)
    public static void mainMenu(Scanner scan, App app) {

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
                register(scan, app);
                break;
            case LOGIN:
                login(scan, app);
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
    public static void loggedIn(User user, Scanner scan, App app) {
        String lCmd = "";
        //Enquanto que o comando nao for sai
        while (!lCmd.equals(LOGOFF)) {
        	//Criacao do prompt
            System.out.print(user.getEmail() + " > ");
            lCmd = readCommand(scan);
            switch (lCmd) {
            case LOGOFF:
                logoff();
                break;
            case NEWRIDE:
                newRide(scan, app);
                break;
            case LISTRIDES:
                listRides(scan, app, user);
                break;
            case GETINFO:
                getInfo(scan, app);
                break;
            case TAKEARIDE:
                takeARide(user, scan, app);
                break;
            case REMOVERIDE:
            	removeRide(scan, user,app);
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
    private static void register(Scanner scan, App app) {
        //Contador de passwords falhadas
        int failCount = 0;
        //Leitura do email 
        String email = scan.next();
        String name = "";
        String pass = "";
        scan.nextLine();
        //Verificacao da existencia de um email igual no programa
        try{
            app.hasEmail(email);
        }catch (HasEmailException e){
            System.out.println("Utilizador ja existente.");
        }
        //Se nao existirem nenhumas contas com esse email
        //Leitura do nome
        System.out.print("nome (maximo 50 caracteres): ");
        name = scan.nextLine();
        //Ciclo responsavel pela verificacao da password
        while (failCount < 3 ) {
            System.out.print("password (entre 4 e 6 caracteres - digitos e letras): ");
            pass = scan.next();
            scan.nextLine();
            //se password for valida
            if (isPwValid(pass)) {
                //Cria a conta no objeto controlador
                app.addUser(email, name, pass);
                System.out.println(REG_SUCCESS);
            } else {
                failCount++;
                System.out.println("Password incorrecta.");
            }
        }
        if (failCount>=3) {
            System.out.println("Registo nao realizado.");
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

    private static void login(Scanner scan, App app) {
        String email = scan.next();
        scan.nextLine();
        String pass = "";
        try{
            app.userExistsCheck(email);
        }catch (UserIsNullException e){
            System.out.println(USER_NOT_EXIST);
        }
        for (int i = 0; i < 3; i++) {
            System.out.print("password: ");
            pass = scan.next();
            scan.nextLine();
            try{
                User user = app.getUserWithPass(email,pass);
                loggedIn(user, scan, app);
                break;
            } catch(WrongPasswordException e) {
                System.out.println("Password incorrecta.");
            }
        }

    }

    private static void logoff() {
        System.out.println(BYEBYE);
    }

    private static void getInfo(Scanner scan, App app) {
        String lEmail = scan.next().trim();
        int[] lDate = app.dateFromString(scan.next().trim());
        UserImp lPerson = app.getPersonFromEmail(lEmail);
        app.sortAccounts();
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

    private static void listRides(Scanner scan, App app, User personObj) {
        String lDate = scan.nextLine().trim();

        int[] laDate;
        if (!lDate.equals("")) {
            laDate = app.dateFromString(lDate);
            listRidesWDate(laDate, app,personObj);
        } else {
            RideIterator lIterator = personObj.createRideIterator();
            app.sortAccounts();
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

    private static void listRidesWDate(int[] date, App app, User personObj) {
        int userCount = app.getUserCount();
        UserImp person = null;
        boolean hasFound = false;
        if(!personObj.isDateValid(date)) {
        	
        	System.out.println("Data invalida.");      	
        }else{
            for (int i = 0; i < userCount; i++) {
            	app.sortAccounts();
                person = app.getPersonFromIndex(i);
                
                RideIterator iterator = person.createRideIterator();
                iterator.sort();
                while (iterator.hasNext()) {
                    Itinerary lRide = iterator.nextRide();
                    if (date[0] == lRide.getDate()[0] && date[1] == lRide.getDate()[1] && date[2] == lRide.getDate()[2]) {
                        printRideInfo(lRide, person, true, false);
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

    private static void removeRide(Scanner scan, User pObj, App app) {
        String date = scan.next();
        try {
            app.removeRide(date);
            System.out.println("Deslocacao removida.");
        } catch (HasRidesException e){
            System.out.println(e.getMessage()+" ja nao pode eliminar esta deslocacao.");
        } catch(NoRideOnDateException e){
            System.out.println(e.getMessage() + " nesta data nao tem registo de deslocacao.");
        }

    }

    private static void newRide(Scanner scan, App app,User user) {
        scan.nextLine();
        String origin = scan.nextLine();

        String destination = scan.nextLine();

        String date = scan.next();

        String hour = scan.next();

        int duration = scan.nextInt();
        int seats = scan.nextInt();

        // 0 if good, 1 if invalid data, 2 if already registered
        try {
            app.addTravel(origin,destination,date,hour,duration,seats);
            System.out.println("Deslocacao "+ user.getNumberOfTravels()+" registada. Obrigado "+user.getName()+".");
        } catch(AlreadyHasRideOnDayException e) {
            System.out.println(user.getName()+" ja tem uma deslocacao ou boleia nesta data.");
        } catch(InvalidDataException e){
            System.out.println("Dados invalidos.");
        }
    }

    private static void takeARide(User pObj, Scanner scan, App app) {
        String lEmail = scan.next().trim();
        int[] lDate = app.dateFromString(scan.next().trim());
        UserImp lPerson = app.getPersonFromEmail(lEmail);

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

    private static void printRideInfo(Itinerary ride, User person, boolean needDriver, boolean freeSpots) {
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