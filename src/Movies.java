import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Movies {
    private final String name;
    private final int length;
    private final String trailerPath;

    private final TreeMap<String, Halls> halls = new TreeMap<>();

    public TreeMap<String, Halls> getHalls(){
        return halls;
    }

    public Movies(String name, String trailerPath, int length){
        this.name = name;
        this.trailerPath = trailerPath;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public String getTrailerPath() {
        return trailerPath;
    }
    public ArrayList<Map.Entry<String, Halls>> getHallsEntries(){
        Set<Map.Entry<String, Halls>> theSet = halls.entrySet();
        return new ArrayList<>(theSet);
    }
    public void removeHall(String hallName){
        this.halls.remove(hallName);
    }
    public static void addSeat(String movieName, String hallName, String  rowOfSeat, String  columnOfSeat, String owner,int price){
        Seat theSeat = new Seat(movieName, hallName, rowOfSeat, columnOfSeat, owner, price);
        Data.getTheMovies().get(movieName).getHalls().get(hallName).getSeats().put(rowOfSeat+" "+columnOfSeat, theSeat);
    }
    public static void addHall(String filmName, String hallName, int pricePerSeat, int row, int column){
        Halls theHall = new Halls(filmName, hallName, pricePerSeat, row, column);
        Data.getTheMovies().get(filmName).getHalls().put(hallName, theHall);
    }

}
