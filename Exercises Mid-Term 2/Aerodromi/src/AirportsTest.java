import java.util.*;

public class AirportsTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Airports airports = new Airports();
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] codes = new String[n];
        for (int i = 0; i < n; ++i) {
            String al = scanner.nextLine();
            String[] parts = al.split(";");
            airports.addAirport(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]));
            codes[i] = parts[2];
        }
        int nn = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < nn; ++i) {
            String fl = scanner.nextLine();
            String[] parts = fl.split(";");
            airports.addFlights(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
        }
        int f = scanner.nextInt();
        int t = scanner.nextInt();
        String from = codes[f];
        String to = codes[t];
        System.out.printf("===== FLIGHTS FROM %S =====\n", from);
        airports.showFlightsFromAirport(from);
        System.out.printf("===== DIRECT FLIGHTS FROM %S TO %S =====\n", from, to);
        airports.showDirectFlightsFromTo(from, to);
        t += 5;
        t = t % n;
        to = codes[t];
        System.out.printf("===== DIRECT FLIGHTS TO %S =====\n", to);
        airports.showDirectFlightsTo(to);
    }
}

// vashiot kod ovde
class Airports{
    HashMap<String,Airport> map;

    public Airports(){
        map = new HashMap<>();
    }

    public void addAirport(String name, String country, String code, int passangers){
        Airport insertAirport = new Airport(name, country, code, passangers);
        map.put(code, insertAirport);
    }

    public void addFlights(String from, String to, int time, int duration){
       Airport fromAirport = map.get(from);
       Flight insertFlight = new Flight(from, to, time, duration);
       fromAirport.flightsList.add(insertFlight);
    }

    public void showFlightsFromAirport(String code) {
        ArrayList<Flight> outArr = new ArrayList<>();
        Airport fromAirport = map.get(code);
        System.out.println(fromAirport);
        int i = 1;
        for (Flight f : fromAirport.flightsList) {
            outArr.add(f);
        }

        Comparator sort = new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Flight f1 = (Flight) o1;
                Flight f2 = (Flight) o2;

                if (f1.to.equals(f2.to)) {
                    return Integer.compare(f1.time, f2.time);
                }
                return f1.to.compareTo(f2.to);
            }
        };

        outArr.sort(sort);
        for (Flight f : outArr){
            System.out.println(i + ". " + f);
            i++;
        }
    }

    public void showDirectFlightsFromTo(String from, String to){
        boolean foundFlight = false;
        Airport fromAirport = map.get(from);

        for(Flight f : fromAirport.flightsList){
            if(f.to.equals(to)) {
                System.out.println(f);
                foundFlight = true;
            }
        }
        if(!foundFlight){
            System.out.println("No flights from " + from + " to " + to);
        }
    }

    public void showDirectFlightsTo(String to){
        ArrayList<Flight> flightsList = new ArrayList<>();
        for(Map.Entry<String, Airport> entry: map.entrySet()){
            for(Flight f : entry.getValue().flightsList){
                if(f.to.equals(to))
                    flightsList.add(f);
            }
        }

        Comparator sort = new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Flight f1 = (Flight) o1;
                Flight f2 = (Flight) o2;

                if (f1.to.equals(f2.to)) {
                    if(f1.time == f2.time){
                        return f1.from.compareTo(f2.from);
                    }
                    return Integer.compare(f1.time, f2.time);
                }
                return f1.to.compareTo(f2.to);
            }
        };

        flightsList.sort(sort);

        for(Flight f : flightsList)
            System.out.println(f);
    }
}

class Airport{
    String name, country, code;
    int passangers;
    ArrayList<Flight> flightsList;

    public Airport(String name, String country, String code, int passangers) {
        this.name = name;
        this.country = country;
        this.code = code;
        this.passangers = passangers;
        flightsList = new ArrayList<>();
    }

    @Override
    public String toString() {
        return name + " (" + code + ")\n" + country + "\n" + passangers;
    }
}

class Flight{
    String from, to;
    int time, duration;

    public Flight(String from, String to, int time, int duration){
        this.from = from;
        this.to = to;
        this.time =time;
        this.duration = duration;
    }

    @Override
    public String toString() {
        int durationHours = duration / 60;
        int durationMins = duration % 60;
        int startHour = time / 60;
        int startMin = time % 60;

        int endHour = startHour + durationHours;
        int endMin = startMin + durationMins;
        if(endMin >= 60){
            endHour += endMin / 60;
            endMin = endMin % 60;
        }

        boolean extraDays = false;
        int extraDaysLength = 0;

        if((time + duration) > 1440 ){
            extraDays = true;
            extraDaysLength = (time + duration) / 1440;
            endHour = endHour % 24;
        }
        if(!extraDays)
        return String.format("%s-%s %02d:%02d-%02d:%02d %dh%02dm",
                from, to, startHour, startMin, endHour, endMin, durationHours, durationMins);

        return String.format("%s-%s %02d:%02d-%02d:%02d +%dd %dh%02dm",
                from, to, startHour, startMin, endHour, endMin, extraDaysLength, durationHours, durationMins);
    }
}
