import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class WeatherStationTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        int n = scanner.nextInt();
        scanner.nextLine();
        WeatherStation ws = new WeatherStation(n);
        while (true) {
            String line = scanner.nextLine();
            if (line.equals("=====")) {
                break;
            }
            String[] parts = line.split(" ");
            float temp = Float.parseFloat(parts[0]);
            float wind = Float.parseFloat(parts[1]);
            float hum = Float.parseFloat(parts[2]);
            float vis = Float.parseFloat(parts[3]);
            line = scanner.nextLine();
            Date date = df.parse(line);
            ws.addMeasurment(temp, wind, hum, vis, date);
        }
        String line = scanner.nextLine();
        Date from = df.parse(line);
        line = scanner.nextLine();
        Date to = df.parse(line);
        scanner.close();
        System.out.println(ws.total());
        try {
            ws.status(from, to);
        } catch (RuntimeException e) {
            System.out.println(e);
        }
    }
}

// vashiot kod ovde
class WeatherStation{
    int days;
    TreeSet<WeatherInfo> weatherQueue;

    WeatherStation(int days){
        this.days = days;
        weatherQueue = new LinkedList<>();
    }

    public void addMeasurment(float temperature, float wind, float humidity, float visibility, Date date){
        WeatherInfo ins = new WeatherInfo(temperature, wind, humidity, visibility, date);
        long insertDate = date.getTime();

        if(days == weatherQueue.size()){
            weatherQueue.remove(days-1);
        }


        weatherQueue.add(ins);
    }

    public int total(){
        return weatherQueue.size();
    }

    public void status(Date from, Date to){
        Collections.sort(weatherQueue);
        for(WeatherInfo wi : weatherQueue){
            System.out.println(wi);
        }
    }

}

class WeatherInfo implements Comparable{
    float temperature, wind, humidity, visibility;
    Date date;

    public WeatherInfo(float temperature, float wind, float humidity, float visibility, Date date) {
        this.temperature = temperature;
        this.wind = wind;
        this.humidity = humidity;
        this.visibility = visibility;
        this.date = date;
    }

    @Override
    public String toString() {
        return String.format("%.1f %.1f km/h %.1f %.1f km ", temperature, wind,visibility , humidity) + date;
    }

    @Override
    public int compareTo(Object o) {
        WeatherInfo obj = (WeatherInfo) o;
        return date.compareTo(obj.date);
    }
}