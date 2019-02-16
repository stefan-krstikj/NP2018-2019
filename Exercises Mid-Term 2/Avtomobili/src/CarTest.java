import java.util.*;

public class CarTest {
    public static void main(String[] args) {
        CarCollection carCollection = new CarCollection();
        String manufacturer = fillCollection(carCollection);
        carCollection.sortByPrice(true);
        System.out.println("=== Sorted By Price ASC ===");
        print(carCollection.getList());
        carCollection.sortByPrice(false);
        System.out.println("=== Sorted By Price DESC ===");
        print(carCollection.getList());
        System.out.printf("=== Filtered By Manufacturer: %s ===\n", manufacturer);
        List<Car> result = carCollection.filterByManufacturer(manufacturer);
        print(result);
    }

    static void print(List<Car> cars) {
        for (Car c : cars) {
            System.out.println(c);
        }
    }

    static String fillCollection(CarCollection cc) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            if(parts.length < 4) return parts[0];
            Car car = new Car(parts[0], parts[1], Integer.parseInt(parts[2]),
                    Float.parseFloat(parts[3]));
            cc.addCar(car);
        }
        scanner.close();
        return "";
    }
}


// vashiot kod ovde
class CarCollection{
    ArrayList<Car> cars;

    public CarCollection(){
        cars = new ArrayList<>();
    }

    public void addCar(Car car){
        cars.add(car);
    }

    public void sortByPrice(boolean ascending){
        Comparator priceComp = new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Car c1 = (Car) o1;
                Car c2 = (Car) o2;

                if(c1.price == c2.price){
                    if(c1.power == c2.power){
                        return c1.manufacturer.compareTo(c2.manufacturer);
                    }
                    return Float.compare(c1.power, c2.power);
                }
                return Integer.compare(c1.price, c2.price);
            }
        };

        cars.sort(priceComp);
        if(!ascending)
            Collections.reverse(cars);
    }

    public List<Car> filterByManufacturer(String manufacturer){
        ArrayList<Car> outputArr = new ArrayList<>();
        for(Car c : cars){
            if(c.manufacturer.toLowerCase().equals(manufacturer.toLowerCase())){
                outputArr.add(c);
            }
        }

        Comparator manufacturerComp = new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Car c1 = (Car) o1;
                Car c2 = (Car) o2;

                return c1.model.compareTo(c2.model);
            }
        };

        outputArr.sort(manufacturerComp);
        return outputArr;

    }

    public List<Car> getList(){
        return cars;
    }
}

class Car{
    String manufacturer, model;
    int price;
    float power;

    public Car(String manufacturer, String model, int price, float power) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.price = price;
        this.power = power;
    }

    @Override
    public String toString() {
        return manufacturer + " " + model + " (" + (int)power + "KW) " + price;
    }
}