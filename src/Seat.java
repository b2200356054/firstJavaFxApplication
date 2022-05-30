public class Seat {
    private final String movieName; private final String hallName;
    private final String rowOfSeat; private final String columnOfSeat;
    private String owner; private int boughtPrice;
    public Seat(String movieName, String hallName, String rowOfSeat, String columnOfSeat, String owner,int boughtPrice){
        this.movieName = movieName;
        this.hallName = hallName;
        this.rowOfSeat = rowOfSeat;
        this.columnOfSeat = columnOfSeat;
        if (owner.equals("null")){
            this.owner = null;
        } else {
            this.owner = owner;
        }
        this.boughtPrice = boughtPrice;
    }

    public String getRowOfSeat() {
        return rowOfSeat;
    }

    public String getColumnOfSeat() {
        return columnOfSeat;
    }

    public String getHallName() {
        return hallName;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getImagePath(){
        if (owner == null){
            return "assets\\\\icons\\\\empty_seat.png";
        } else {
            return "assets\\\\icons\\\\reserved_seat.png";
        }
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getBoughtPrice() {
        return boughtPrice;
    }

    public void setBoughtPrice(int boughtPrice) {
        this.boughtPrice = boughtPrice;
    }
}
