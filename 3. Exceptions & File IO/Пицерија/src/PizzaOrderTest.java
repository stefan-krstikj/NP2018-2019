import java.util.ArrayList;
import java.util.Scanner;
import java.util.Formatter;

class InvalidExtraTypeException extends Exception{
    public InvalidExtraTypeException(String e){
        super(e);
    }
}

class InvalidPizzaTypeException extends Exception{
    public InvalidPizzaTypeException(String e){
        super(e);
    }
}

class ItemOutOfStockException extends Exception{
    public ItemOutOfStockException(Item e) {

    }
}

class ArrayIndexOutOfBоundsException extends Exception{
    public ArrayIndexOutOfBоundsException(int id) {

    }
}

class OrderLockedException extends Exception{
    public OrderLockedException(String e) {
        super(e);
    }
}

class EmptyOrder extends Exception{
    public EmptyOrder(String e) {
        super(e);
    }
}

interface Item{
    public String getType();
    public int getPrice();
}

class ExtraItem implements Item {
    private String type;
    public ExtraItem(String type) throws InvalidExtraTypeException {
        if(!type.equals("Coke")&&!type.equals("Ketchup"))
            throw new InvalidExtraTypeException("");
        this.type = type;
    }
    public String getType() {
        return type;
    }

    public int getPrice() {
        return (type.equals("Coke") ? 5 : 3);
    }
}

class PizzaItem implements Item{
    private String type;
    public PizzaItem(String type) throws InvalidPizzaTypeException{
        if(!type.equals("Standard") && !type.equals("Pepperoni") && !type.equals("Vegetarian"))
            throw new InvalidPizzaTypeException("Not valid input");
        this.type = type;

    }

    public String getType() {
        return type;
    }

    public int getPrice() {
        if(type.equals("Standard"))
            return 10;
        if(type.equals("Pepperoni"))
            return 12;
        return 8;
    }
}
// RESI GO OVA SO ARRAYLIST
class Order{
    private ArrayList<Item> items;
    private ArrayList<Integer> count;
    private boolean lock;

    public Order() {
        items = new ArrayList<Item>();
        count = new ArrayList<Integer>();
        lock = false;
    }

    public void addItem(Item item, int count) throws ItemOutOfStockException, InvalidPizzaTypeException, InvalidExtraTypeException, OrderLockedException {
        if(lock)
            throw new OrderLockedException("");
        if(count > 10)
            throw new ItemOutOfStockException(item);

        // if exists, change count to new count
        for(int i = 0; i < items.size(); i++) {
            if(items.get(i).getType().equals(item.getType())) {
                this.count.set(i, count);
                return;
            }
        }

        // if it doesn't exist, add new
        if(item instanceof PizzaItem) {
            items.add(new PizzaItem(item.getType()));
        }
        else {
            items.add(new ExtraItem(item.getType()));
        }
        this.count.add(count);
    }

    public int getPrice() {
        int tot = 0;
        for(int i = 0; i < items.size(); i++)
            tot+=items.get(i).getPrice() * count.get(i);
        return tot;
    }

    public void displayOrder() {
        String s;
        for(int i = 0; i < items.size(); i++) {
            int orderedNumbdisplay = i + 1;
            System.out.printf(String.format("%3d.%-15sx%2d%5d$\n", i+1, items.get(i).getType(),
                    count.get(i), items.get(i).getPrice() * count.get(i)));
			
			/*"  " + orderedNumbdisplay + "." + items.get(i).getType()  + 
			"\t\tx " + count.get(i) + "\t" + items.get(i).getPrice() * count.get(i) +"$")*/
        }
        System.out.printf(String.format("%-22s%5d$\n", "Total:", getPrice()));
    }

    public void removeItem(int idx) throws ArrayIndexOutOfBоundsException, OrderLockedException {
        if(idx > count.size())
            throw new ArrayIndexOutOfBoundsException();
        if(lock)
            throw new OrderLockedException("");
        count.remove(idx);
        items.remove(idx);
    }

    public void lock() throws EmptyOrder {
        int a = items.size();
        if(a < 1) {
            //	System.out.println("EmptyOrder");
            throw new EmptyOrder("EmptyOrder");
        }
        else {
            lock = true;
        }
    }
}

public class PizzaOrderTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) { //test Item
            try {
                String type = jin.next();
                String name = jin.next();
                Item item = null;
                if (type.equals("Pizza")) item = new PizzaItem(name);
                else item = new ExtraItem(name);
                System.out.println(item.getPrice());
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
        if (k == 1) { // test simple order
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 2) { // test order with removing
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (jin.hasNextInt()) {
                try {
                    int idx = jin.nextInt();
                    order.removeItem(idx);
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 3) { //test locking & exceptions
            Order order = new Order();
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.addItem(new ExtraItem("Coke"), 1);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.removeItem(0);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
    }

}