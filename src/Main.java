/*
 * Project: FCT Boleias
 * Description: Main project for Algorithms & Data Structures classes.
 * Version:
 * Copyright (C) 2019, Rodrigo Rosa <https://github.com/v0n3x> (Student ID: 56505)
 * Copyright (C) 2019, Tiago Teles <https://github.com/ttmx> (Student ID: 54953)
 * All rights reserved.
 */

/*
 * Register - Working
 * Login - Working
 * Logout - Working
 * Help - Working (initial and session)
 * New Travels looks like its working and consultation too (missing emails list of riders tho)
 * Removing travels looks like it's working too.
 * List minhas parece funcionar mas está fora de ordem (fixable easily I think? we just use comparator i guess)
 * GetInfo - works too from what it looks (missing emails list of riders too)
 *
 * TODO:
 * Notes: We can't pass any future tests without serializable - SUPER IMPORTANT
 *
 */

import app.*;
import exception.*;

import java.util.Scanner;
import dataStructures.Iterator;

class Main {

	// Commands
    private static final String HELP = "AJUDA";
    private static final String EXIT = "TERMINA";
    private static final String REGISTER = "REGISTA";
    private static final String LOGIN = "ENTRADA";
    private static final String LOGOUT = "SAI";
    private static final String NEW_TRAVEL = "NOVA";
    private static final String LISTING = "LISTA";
    private static final String GETINFO = "CONSULTA";
    private static final String NEW_RIDE = "BOLEIA";
    private static final String REMOVE_TRAVEL = "REMOVE";
    private static final String REMOVE_RIDE = "RETIRA";

    // Outputs
    private static final String INVALID_CMD = "Comando invalido.";
    private static final String ON_SESSION_INPUT = "%s > ";
    private static final String HELP_DEFAULT = "ajuda - Mostra os comandos existentes\n" +
            "termina - Termina a execucao do programa\n" +
            "regista - Regista um novo utilizador no programa\n" +
            "entrada - Permite a entrada (\"login\") dum utilizador no programa";
    private static final String HELP_ON_SESSION = "ajuda - Mostra os comandos existentes\n" +
            "sai - Termina a sessao deste utilizador no programa\n" +
            "nova - Regista uma nova deslocacao\n" +
            "lista - Lista todas ou algumas deslocacoes registadas\n" +
            "boleia - Regista uma boleia para uma dada deslocacao\n" +
            "consulta - Lista a informacao de uma dada deslocacao\n" +
            "retira - Retira uma dada boleia\n" +
            "remove - Elimina uma dada deslocacao";
    private static final String EXIT_MSG = "Obrigado. Ate a proxima.";
    private static final String LOGOUT_MSG = "Ate a proxima %s.\n";
    private static final String REG_SUCCESS = "Registo %d efetuado.\n";
    private static final String REGISTER_FAIL = "Registo nao realizado.";
    private static final String USER_EXISTS = "Utilizador ja existente.";
    private static final String USER_NOT_EXIST = "Utilizador nao existente.";
    private static final String LOGIN_FAIL = "Entrada nao realizada.";
    private static final String LOGIN_SUCCESS = "Visita %d efetuada.\n";
    private static final String NEW_TRAVEL_SUCCESS = "Deslocacao %d registada. Obrigado %s.\n";
    private static final String NEW_TRAVEL_ERR1 = "Dados invalidos.";
    private static final String NEW_TRAVEL_ERR2 = "%s ja tem uma deslocacao ou boleia registada nesta data.\n";
    private static final String NEW_RIDE_SUCCESS = "Boleia registada.";
    private static final String MOV_NOT_EXIST = "Deslocacao nao existe.";


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        App app = App.load();
        String cmd = getCommand(in, app);
        outSessionMenu(in, app, cmd);
    }

    /**
     * Options menu during program initial state (no user signed in).
     *
     * @param in
     * @param app
     * @param cmd
     */
    public static void outSessionMenu(Scanner in, App app, String cmd) {
        // Enquanto o comando nao for termina
        while (!cmd.equals(EXIT)) {
        	//Criacao do prompt
            //System.out.print("> ");
            //Leitura dos comandos
            //lCmd = readCommand(scan);
            //Processamento dos comandos
            switch (cmd) {
                case HELP:
                    printOutSessionHelp();
                    break;
                case REGISTER:
                    register(in, app);
                    break;
                case LOGIN:
                    login(in, app);
                    break;
                default:
                    System.out.println(INVALID_CMD);
                    break;
            }
            cmd = getCommand(in, app);
        }
        end(app);
        in.close();
    }

    private static void end(App app) {

        app.store();
        System.out.println(EXIT_MSG);
    }

    /**
     * Options menu during program session state (user signed in).
     *
     * @param in
     * @param app
     */
    public static void onSessionMenu(Scanner in, App app) {
        String cmd = getCommand(in, app);
        //Enquanto que o comando nao for sai
        while (!cmd.equals(LOGOUT)) {
        	//Criacao do prompt
            //System.out.print(user.email() + " > ");
            //cmd = readCommand(scan);
            switch (cmd) {
                /*case LOGOUT:
                    logout(app);
                    break;*/
                case HELP:
                    printOnSessionHelp();
                    break;
                case NEW_TRAVEL:
                    newTravel(in, app);
                    break;
                case NEW_RIDE:
                    newRide(in, app);
                    break;
                case LISTING:
                    listing(in, app);
                    break;
                case GETINFO:
                    getInfo(in, app);
                    break;
                case REMOVE_TRAVEL:
                    removeTravel(in, app);
                    break;
                case REMOVE_RIDE:
                    removeRide(in, app);
                    break;
                default:
                    in.nextLine();
                    System.out.println(INVALID_CMD);
                    break;
                }
            cmd = getCommand(in, app);
        }
        logout(app);
        //in.close();
    }

    /**
     * Command input receiver and converter
     *
     * @param in
     * @param app
     * @return
     */
    private static String getCommand(Scanner in, App app) {
        String input;
        if (app.hasSession()) {
            System.out.printf(ON_SESSION_INPUT, app.getCurrentUserId());
        }
        else {
            System.out.print("> ");
        }
        input = in.next().toUpperCase();
        return input;
    }

    /**
     * User register on the app process
     *
     * @param in
     * @param app
     */
    private static void register(Scanner in, App app) {
        //Contador de passwords falhadas
        int failCount = 0;
        //Leitura do email
        String email = in.next();

        if (app.hasEmail(email)) {
            System.out.println(USER_EXISTS);
        }
        else {
            System.out.print("nome (maximo 50 caracteres): ");
            String name = in.next();
            in.nextLine();
            String password;
            while (failCount < 3) {
                System.out.print("password (entre 4 e 6 caracteres - digitos e letras): ");
                password = in.next();
                in.nextLine();
                //se password for valida
                if (isPwValid(password)) {
                    //Cria a conta no objeto controlador
                    app.addUser(email, name, password);
                    System.out.printf(REG_SUCCESS, app.getNumOfUsers());
                    break;
                } else {
                    failCount++;
                    //System.out.println(REGISTER_FAIL);
                }
            }
        }
        //Se nao existirem nenhumas contas com esse email
        //Leitura do nome
        if (failCount >= 3) {
            System.out.println(REGISTER_FAIL);
        }
    }

    /**
     * Checks if the password is valid (4 to 6 characters alphanumerical)
     *
     * @param pw
     * @return
     */
    private static boolean isPwValid(String pw) {
        boolean lIsValid = true;
        /*boolean hasDigits = false;
        boolean hasLetter = false;
        //Verifica se cada caracter e um digito ou letra
        //se algum dos caracteres for algo que nao um digito ou letra entao o metodo retorna falso
        for (int i = 0; i < pw.length(); i++) {
            if (!Character.isLetterOrDigit(pw.charAt(i))) {
                //lIsValid = false;
                return false;
            }
            if (!Character.isLetter(pw.charAt(i))) {

            }
        }*/
        //if (!pw.matches("/[A-Za-z0-9]]+/g")) {
        if (!(pw.matches(".*[a-zA-Z].*") && pw.matches(".*[0-9].*"))) { // regex for alphanumerical with 1 or more matches required.
        //if (!pw.matches("[0-9a-zA-Z]+")) {
            lIsValid = false;
        }
        return (pw.length() <= 6 && pw.length() >= 4 && lIsValid);
    }

    private static void printOutSessionHelp() {
        System.out.println(HELP_DEFAULT);
    }

    private static void printOnSessionHelp() {
        System.out.println(HELP_ON_SESSION);
    }

    private static void login(Scanner in, App app) {
        String email = in.next();
        in.nextLine();
        String password;
        int failCount = 0;
        if (!app.hasEmail(email)) {
            System.out.println(USER_NOT_EXIST);
        }
        else {
            while (failCount < 3) {
                System.out.print("password: ");
                password = in.next();
                in.nextLine();
                if (app.matchesPw(email, password)) {
                    app.setCurrentUser(email);
                    System.out.printf(LOGIN_SUCCESS, app.getUserLoginNum());
                    onSessionMenu(in, app);
                    break;
                }
                failCount++;
            }
            if (failCount >= 3) {
                System.out.println(LOGIN_FAIL);
            }
        }

    }

    private static void logout(App app) {
        String name = app.closeSession();
        System.out.printf(LOGOUT_MSG, name);
    }

    private static void newTravel(Scanner in, App app) {
        in.nextLine();
        String origin = in.nextLine();
        String destination = in.nextLine();
        String date = in.next();
        String hour = in.next();
        int duration = in.nextInt();
        int seats = in.nextInt();

        // 0 if good, 1 if invalid data, 2 if already registered
        try {
            app.addTravel(origin, destination, date, hour, duration, seats);
            //System.out.println("Deslocacao "+ user.numberOfTravels()+" registada. Obrigado "+user.name()+".");
            System.out.printf(NEW_TRAVEL_SUCCESS, app.getUserTravelsNum(), app.getCurrentUserName());
        } catch (InvalidDateException e) {
            System.out.println(NEW_TRAVEL_ERR1);
        } catch (AlreadyHasEntryOnDayException e) {
            System.out.printf(NEW_TRAVEL_ERR2, app.getCurrentUserName());
        }
    }

    private static void newRide(Scanner in, App app) {
        String travelOwnerEmail = in.next().trim();
        String date = in.next().trim();
        try {
            app.addRide(travelOwnerEmail, date);
            System.out.println(NEW_RIDE_SUCCESS);
        } catch (InvalidDateException e) {
            System.out.println("Data invalida.");
        }catch(UserIsNullException e){
            System.out.println("Utilizador inexistente.");
        }catch(NoRideOnDateException e){
            System.out.println(MOV_NOT_EXIST);
        }catch(SamePersonException e){
            System.out.println(e.getMessage() + " nao pode dar boleia a si proprio.");
        }catch(PlacedInQueueException e){
            System.out.println("Ficou na fila de espera (posicao "+ e.getMessage() +").");
        } catch (AlreadyHasRideOnDayException e) {
            System.out.println(e.getMessage() + " ja registou uma boleia ou deslocacao nesta data.");
        }
    }

    private static void removeRide(Scanner in, App app) {
        String date = in.next();
        try {
            app.delRide(date);
            System.out.println(app.getCurrentUserName()+" boleia retirada.");
        } catch (InvalidDateException e) {
            System.out.println("Data invalida.");
        } catch (NoRideOnDateException e) {
            System.out.println(e.getMessage()+" nesta data nao tem registo de boleia.");
        }
    }

    private static void getInfo(Scanner in, App app) {
       /* String lEmail = scan.next().trim();
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
        }*/
       String userEmailToCheck = in.next().trim();
       String travelDate = in.next().trim();
       in.nextLine();

       try {
           Travel travel = app.getTravel(userEmailToCheck, travelDate);
           System.out.printf("%s\n" + //email
                   "%s-%s\n" + //origin-destination
                   "%s %s %d\n" + // date hour duration
                   "Lugares vagos: %d\n" + // available seats
                   "Boleias: %s\n" +
                   "Em espera: %d\n", app.getCurrentUserId(), travel.getOrigin(), travel.getDestination(), travel.getDate(), travel.getTime(), travel.getDuration(), travel.getNumOfAvailableSeats(), userListToString(travel.getRideUsers()),travel.getNumOfUsersQueueHold()); // users em espera
       } catch (InvalidUserException e) {
           System.out.println("Utilizador inexistente");
       } catch (InvalidDateException e) {
           System.out.println("Data invalida.");
       } catch (InvalidTravelException e) {
           System.out.println(MOV_NOT_EXIST);
       }
    }

    private static String userListToString(Iterator<User> userIt){
        String str = "";
        while (userIt.hasNext()){
            str += userIt.next().email()+(userIt.hasNext()?"; ":"");
        }
        return str;
    }


    private static void listing(Scanner in, App app) {
        /*String lDate = scan.nextLine().trim();

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
                System.out.println(personObj.name()+" nao tem deslocacoes registadas.");
            }
        }*/

        String listMode = in.next().toUpperCase();
        in.nextLine();

        if (listMode.equals("MINHAS")) {
            Iterator<Travel> currentUserTravels = app.getUserTravels();
            while (currentUserTravels.hasNext()) {
                Travel travel = currentUserTravels.next();
                System.out.printf("%s\n" + //email
                        "%s-%s\n" + //origin-destination
                        "%s %s %d\n" + // date hour duration
                        "Lugares vagos: %d\n" + // available seats
                        "Boleias: \n" +
                        "Em espera: %d\n", app.getCurrentUserId(), travel.getOrigin(), travel.getDestination(), travel.getDate(), travel.getTime(), travel.getDuration(), travel.getNumOfAvailableSeats(), travel.getNumOfUsersQueueHold());
            }
        }
        else {
            System.out.println("teste");
        }
    }

    /*private static void listRidesWDate(int[] date, App app, User personObj) {
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
            	System.out.println(personObj.name() + " nao existem deslocacoes registadas para " + date[0]+"-"+ date[1]+"-"+ date[2]+".");
            }
        }
    }*/

    private static void removeTravel(Scanner in, App app) {
        String date = in.next();
        in.nextLine();

        try {
            app.delTravel(date);
            System.out.println("Deslocacao removida.");
        } catch (InvalidDateException e) {
            System.out.println("Data invalida.");
        } catch (InvalidTravelException e) {
            System.out.printf("%s nesta data nao tem registo de deslocacao.\n", app.getCurrentUserName());
        } catch (HasRidesException e) {
            System.out.printf("%s ja nao pode eliminar esta deslocacao.\n", app.getCurrentUserName());
        }

    }


    /*
    private static void printRideInfo(Itinerary ride, User person, boolean needDriver, boolean freeSpots) {
        if (needDriver) {
            System.out.println(person.email());
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