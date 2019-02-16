import java.util.*;

public class StaduimTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] sectorNames = new String[n];
        int[] sectorSizes = new int[n];
        String name = scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            sectorNames[i] = parts[0];
            sectorSizes[i] = Integer.parseInt(parts[1]);
        }
        Stadium stadium = new Stadium(name);
        stadium.createSectors(sectorNames, sectorSizes);
        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            try {
                stadium.buyTicket(parts[0], Integer.parseInt(parts[1]),
                        Integer.parseInt(parts[2]));
            } catch (SeatNotAllowedException e) {
                System.out.println("SeatNotAllowedException");
            } catch (SeatTakenException e) {
                System.out.println("SeatTakenException");
            }
        }
        stadium.showSectors();
    }
}
class Sector implements Comparable{
    String sectorName;
    int seats;
    boolean[] seatOccupied;
    int seatType;

    Sector(String name, int size){
        this.sectorName = name;
        this.seats = size;
        seatType = 0;
        seatOccupied = new boolean[size];
        for(int i = 0; i < size; i++)
            seatOccupied[i] = false;
    }

    int freeSeats(){
        int c = 0;
        for(int i =0; i < seatOccupied.length; i++){
            if(seatOccupied[i])
                c++;
        }
        return c;
    }

    @Override
    public int compareTo(Object o) {
        Sector s = (Sector) o;

        if(freeSeats() == s.freeSeats()){
            return sectorName.compareTo(s.sectorName);
        }
        else{
            return Integer.compare(freeSeats(), s.freeSeats());
        }
    }

    @Override
    public String toString() {
        int occupied = seats - freeSeats();
        double percentOcc = 100- ((double) occupied*100 /  seats);

        return String.format("%-1s\t%d/%d\t%.1f%%", sectorName, occupied, seats, percentOcc);
    }
}

class Stadium{
    String stadiumName;
    HashMap<String, Sector> sectorMap;

    public Stadium(String name){
        this.stadiumName = name;
        sectorMap = new HashMap<>();
    }

    public void createSectors(String[] sectorNames, int[] sizes) {
        for(int i =0; i < sectorNames.length; i++){
            Sector insertSector = new Sector(sectorNames[i], sizes[i]);
            sectorMap.put(sectorNames[i], insertSector);
        }
    }

    public void buyTicket(String sectorName, int seat, int type) throws SeatTakenException, SeatNotAllowedException{
        Sector buyTicketFromSector = sectorMap.get(sectorName);

        if(buyTicketFromSector.seatOccupied[seat-1]){
            throw new SeatTakenException();
        }

        if(type != 0) {
            if (buyTicketFromSector.seatType != 0) {
                if (type != buyTicketFromSector.seatType) {
                    throw new SeatNotAllowedException();
                }
            }
        }

        buyTicketFromSector.seatOccupied[seat-1] = true;
        if(type != 0)
            buyTicketFromSector.seatType = type;
    }

    public void showSectors(){
        TreeSet<Sector> sectorsSet = new TreeSet<>();
        for(Map.Entry<String, Sector> entry : sectorMap.entrySet()){
            Sector currSector = entry.getValue();
            sectorsSet.add(currSector);
        }

        for(Sector s : sectorsSet){
            System.out.println(s);
        }
    }


}

class SeatTakenException extends Exception{
    public SeatTakenException(){
    }
}

class SeatNotAllowedException extends Exception{
    public SeatNotAllowedException(){

    }
}