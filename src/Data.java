import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.*;
import java.util.*;

public class Data {
    private static final TreeMap<String, Movies> theMovies = new TreeMap<>();
    private static final TreeMap<String, Users> users = new TreeMap<>();
    private static int maximumError = 0; private static int errorLimit;
    private static String title; private static int discount;
    private static int blockTime;
    private static Media errorSound; private static MediaPlayer mediaPlayer;

    public static void addUser(String username, String password, String admin, String clubMember){
        Users theUser = new Users(username, password, admin, clubMember);
        users.put(theUser.getUsername(), theUser);
    }
    public static void fillChoiceBoxMovies(ChoiceBox<String> choiceBox){
        for (Map.Entry<String, Movies> moviesEntry: getTheMoviesEntries()){
            choiceBox.getItems().add(moviesEntry.getKey());
        }
        try{
            choiceBox.setValue(choiceBox.getItems().get(0));
        } catch (Exception ignored){}
    }
    public static void fillChoiceBoxHalls(String movieName, ChoiceBox<String> choiceBox){
        choiceBox.getItems().clear();
        ArrayList<Map.Entry<String, Halls>> theMovieEntries = Data.getTheMovies().get(movieName).getHallsEntries();
        for (Map.Entry<String, Halls> i: theMovieEntries) {
            choiceBox.getItems().add(i.getKey());
        }
    }

    public static void fillChoiceBoxUsers(ChoiceBox<String> choiceBox){
        choiceBox.getItems().clear();
        ArrayList<Map.Entry<String, Users>> theUserEntries = Data.getUsersEntries();
        for (Map.Entry<String, Users> i: theUserEntries) {
            choiceBox.getItems().add(i.getKey());
        }
    }

    public static void fillTable(TableView<Users> tableView){
        ObservableList<Users> usersList = FXCollections.observableArrayList();
        for (Map.Entry<String, Users> theUser: getUsersEntries()) {
            if (!theUser.getKey().equals(LogInScene.getUsername())){
                if (!theUser.getKey().equals("admin")) {
                    usersList.add(users.get(theUser.getKey()));
                }
            }
        }
        tableView.setItems(usersList);
    }

    public static void addFilm(String name, String trailerPath, int length){
        Movies theFilm = new Movies(name, trailerPath, length);
        theMovies.put(name, theFilm);
    }
    public static void removeFilm(String name){
        theMovies.remove(name);
    }

    public static TreeMap<String, Movies> getTheMovies() {
        return theMovies;
    }

    public static void setMaximumError(int maximumError) {
        Data.maximumError = maximumError;
    }

    public static int getMaximumError() {
        return maximumError;
    }

    public static int getErrorLimit() {
        return errorLimit;
    }

    public static int getDiscount() {
        return discount;
    }

    public static int getBlockTime() {
        return blockTime;
    }

    public static ArrayList<Map.Entry<String, Movies>> getTheMoviesEntries(){
        Set<Map.Entry<String, Movies>> randomList = theMovies.entrySet();
        return new ArrayList<>(randomList);
    }
    public static TreeMap<String, Users> getUsers(){
        return users;
    }
    public static ArrayList<Map.Entry<String, Users>> getUsersEntries(){
        Set<Map.Entry<String, Users>> theSet = getUsers().entrySet();
        return new ArrayList<>(theSet);
    }
    public static void getData(){
        File backupFile; Scanner backupFileScanner;
        try {
            backupFile = new File("assets\\data\\backup.dat");
            backupFileScanner = new Scanner(backupFile);
            while (backupFileScanner.hasNextLine()){
                String[] row = backupFileScanner.nextLine().split("\t");
                try {
                    switch (row[0]) {
                        case "user":
                            addUser(row[1], row[2], row[3], row[4]);
                            break;
                        case "film":
                            addFilm(row[1], row[2], Integer.parseInt(row[3]));
                            break;
                        case "hall":
                            Movies.addHall(row[1], row[2], Integer.parseInt(row[3]), Integer.parseInt(row[4]), Integer.parseInt(row[5]));
                            break;
                        case "seat":
                            Movies.addSeat(row[1], row[2], row[3], row[4], row[5], Integer.parseInt(row[6]));
                            break;
                    }
                } catch (IndexOutOfBoundsException e){
                    continue;
                }
            }
        } catch (FileNotFoundException e){
            addUser("admin", "password","true", "true");
        }
        try{
            FileReader propertiesFile = new FileReader("assets\\data\\properties.dat");
            Properties prop = new Properties();
            prop.load(propertiesFile);

            errorLimit = Integer.parseInt(prop.getProperty("maximum-error-without-getting-blocked"));
            title = prop.getProperty("title");
            discount = Integer.parseInt(prop.getProperty("discount-percentage"));
            blockTime = Integer.parseInt(prop.getProperty("block-time"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        errorSound = new Media(new File("assets\\effects\\error.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(errorSound);
    }
    public static void playErrorSound(){
        mediaPlayer.stop();
        mediaPlayer.play();
    }
    public static void saveData() throws IOException {
        //USER RECORDS
        FileWriter backup = new FileWriter("assets\\data\\backup.dat");
        PrintWriter backupWrite = new PrintWriter(backup);
        for (Map.Entry<String, Users> i: getUsers().entrySet()){
            Users theUser = i.getValue();
            backupWrite.write(String.format("user\t%s\t%s\t%s\t%s\n", theUser.getUsername(), theUser.getPassword(), theUser.isClubMember(), theUser.isAdmin()));
        }

        for (Map.Entry<String, Movies> j: getTheMovies().entrySet()){
            Movies theMovie = j.getValue();
            backupWrite.write(String.format("film\t%s\t%s\t%s\n", theMovie.getName(), theMovie.getTrailerPath(), theMovie.getLength()));
            for (Map.Entry<String,Halls> u: j.getValue().getHallsEntries()){
                Halls theHall = u.getValue();
                backupWrite.write(String.format("hall\t%s\t%s\t%s\t%s\t%s\n", theHall.getMovieName(), theHall.getHallName(),theHall.getPricePerSeat(),theHall.getRow(), theHall.getColumn()));
                for(Map.Entry<String, Seat> p: u.getValue().getSeatEntries()){
                    Seat theSeat = p.getValue();
                    backupWrite.write(String.format("seat\t%s\t%s\t%s\t%s\t%s\t%s\n", theSeat.getMovieName(), theSeat.getHallName(), theSeat.getRowOfSeat(), theSeat.getColumnOfSeat(),
                            theSeat.getOwner(), theSeat.getBoughtPrice()));

                }
            }
        }
        backupWrite.close();
        //SEAT RECORDS
    }



}
