import java.util.*;

public class AuditionTest {
    public static void main(String[] args) {
        Audition audition = new Audition();
        List<String> cities = new ArrayList<String>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            if (parts.length > 1) {
                audition.addParticpant(parts[0], parts[1], parts[2],
                        Integer.parseInt(parts[3]));
            } else {
                cities.add(line);
            }
        }
        for (String city : cities) {
            System.out.printf("+++++ %s +++++\n", city);
            audition.listByCity(city);
        }
        scanner.close();
    }
}

class Audition{
    HashMap<String, HashSet<Participant>> map;

    public Audition(){
        map = new HashMap<>();
    }

    public void addParticpant(String city, String code, String name, int age){
        Participant ins = new Participant(name, code, age);
        if(!map.containsKey(city)){
            HashSet<Participant> insertSet = new HashSet<>();
            insertSet.add(ins);
            map.put(city, insertSet);
        }
        else{
            HashSet<Participant> insertSet = map.get(city);
            map.get(city).add(ins);
        }
    }

    public void listByCity(String city){
        HashSet<Participant> citySetHash = map.get(city);
        TreeSet<Participant> citySetTree = new TreeSet<>();
        citySetTree.addAll(citySetHash);

        for(Participant p : citySetTree){
            System.out.println(p);
        }
    }
}

class Participant implements Comparable{
    String name, code;
    int age;

    public Participant(String name, String code, int age) {
        this.name = name;
        this.code = code;
        this.age = age;
    }

    @Override
    public String toString() {
        return code + " " + name + " " + age;
    }

    @Override
    public int compareTo(Object o) {
        Participant obj = (Participant) o;

        if(name.equals(obj.name)){
            if(age != obj.age)
                return Integer.compare(age, obj.age);
            return code.compareTo(obj.code);
        }
        else{
            return name.compareTo(obj.name);
        }
    }

    @Override
    public boolean equals(Object o) {
        Participant p = (Participant) o;

        return code.equals(p.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}