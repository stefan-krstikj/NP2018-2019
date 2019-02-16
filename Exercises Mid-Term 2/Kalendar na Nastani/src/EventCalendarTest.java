import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class EventCalendarTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        int year = scanner.nextInt();
        scanner.nextLine();
        EventCalendar eventCalendar = new EventCalendar(year);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            String name = parts[0];
            String location = parts[1];
            Date date = df.parse(parts[2]);
            try {
                eventCalendar.addEvent(name, location, date);
            } catch (WrongDateException e) {
                System.out.println(e.getMessage());
            }
        }
        Date date = df.parse(scanner.nextLine());
        eventCalendar.listEvents(date);
        eventCalendar.listByMonth();
    }
}

// vashiot kod ovde
class EventCalendar{
    int year;
    Map<Integer, TreeSet<Event>> events;

    public EventCalendar(int  year){
        this.year = year;
        events = new HashMap<>();
    }

    public void addEvent(String name, String location, Date date) throws WrongDateException{

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        if(cal.get(Calendar.YEAR) != year){
            throw new WrongDateException(date);
        }

        Event ins = new Event(name, location, date);
        int insertDay = cal.get(Calendar.DAY_OF_YEAR);

        if(events.containsKey(insertDay)){
            TreeSet<Event> newArray = events.get(insertDay);
            newArray.add(ins);
            events.put(insertDay, newArray);
        }
        else{
            TreeSet<Event> newArray = new TreeSet<>();
            newArray.add(ins);
            events.put(insertDay, newArray);
        }
            }

    public void listEvents(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_YEAR);
        TreeSet<Event> eventsList = events.get(day);
        if(eventsList == null){
            System.out.println("No events on this day!");
            return;
        }
        for(Event ev : eventsList){
            System.out.println(ev);
        }
    }

    public void listByMonth(){

        for(int i = 0; i < 12; i++){
            int currMonth = i;
            int currMonthCount = 0;
            for(Map.Entry<Integer, TreeSet<Event>> evs : events.entrySet()){
                for(Event e : evs.getValue()){
                    Calendar currIt = Calendar.getInstance();
                    currIt.setTime(e.date);
                    if(currIt.get(Calendar.MONTH) == currMonth)
                        currMonthCount++;
                }
            }
            System.out.println(currMonth+1 + " : " + currMonthCount);
        }
    }

}

class Event implements Comparable{
    String name, location;
    Date date;

    public Event(String name, String location, Date date) {
        this.name = name;
        this.location = location;
        this.date = date;
    }

    @Override
    public int compareTo(Object o) {
        Event obj = (Event) o;
        return date.compareTo(obj.date);
    }

    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat("dd MMM, YYY HH:mm");
        return df.format(date)+ " at " + location +", " + name;
    }
}

class WrongDateException extends Exception{
    public WrongDateException(Date e){
        super(String.format("Wrong date: " + e.toString().replaceAll("GMT", "UTC")));
    }
}