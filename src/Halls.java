
import java.util.*;


public class Halls {
    private final String hallName; private final String movieName;
    private final int pricePerSeat; private final int row; private final int column;
    private final TreeMap<String, Seat> seats = new TreeMap<>();

    public Halls(String movieName, String hallName, int pricePerSeat, int row, int column){
        this.movieName = movieName;
        this.hallName = hallName;
        this.pricePerSeat = pricePerSeat;
        this.row = row;
        this.column = column;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getHallName() {
        return hallName;
    }

    public TreeMap<String, Seat> getSeats() {
        return seats;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getPricePerSeat() {
        return pricePerSeat;
    }


    public ArrayList<Map.Entry<String, Seat>> getSeatEntries(){
        Set<Map.Entry<String , Seat>> theSet = seats.entrySet();
        return new ArrayList<>(theSet);
    }


}
