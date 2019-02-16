import java.util.*;

public class ComponentTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        Window window = new Window(name);
        Component prev = null;
        while (true) {
            try {
                int what = scanner.nextInt();
                scanner.nextLine();
                if (what == 0) {
                    int position = scanner.nextInt();
                    window.addComponent(position, prev);
                } else if (what == 1) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev = component;
                } else if (what == 2) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev.addComponent(component);
                    prev = component;
                } else if (what == 3) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev.addComponent(component);
                } else if(what == 4) {
                    break;
                }

            } catch (InvalidPositionException e) {
                System.out.println(e.getMessage());
            }
            scanner.nextLine();
        }

        System.out.println("=== ORIGINAL WINDOW ===");
        System.out.println(window);
        int weight = scanner.nextInt();
        scanner.nextLine();
        String color = scanner.nextLine();
        window.changeColor(weight, color);
        System.out.println(String.format("=== CHANGED COLOR (%d, %s) ===", weight, color));
        System.out.println(window);
        int pos1 = scanner.nextInt();
        int pos2 = scanner.nextInt();
        System.out.println(String.format("=== SWITCHED COMPONENTS %d <-> %d ===", pos1, pos2));
        window.swichComponents(pos1, pos2);
        System.out.println(window);
    }
}

// вашиот код овде
class Component implements Comparable{
    String color;
    int weight;
    TreeSet<Component> components;

    public Component(String color, int weight){
        this.color = color;
        this.weight = weight;
        components = new TreeSet<>();
    }

    public void addComponent(Component component){
        components.add(component);
    }

    @Override
    public int compareTo(Object o) {
        Component obj = (Component) o;

        if(obj.weight == weight){
            return color.compareTo(obj.color);
        }
        return Integer.compare(weight, obj.weight);
    }


    public String printRecursively(int n){
        String s = "";

        for(int i = 0; i < n; i++){
            s+= "---";
        }
        s+=weight +":"+color + "\n";

        for(Component co : components){
            s+= co.printRecursively(n+1);
        }
        return s;
    }

    public void changeColor(int weight, String color){
        if(this.weight < weight){
            this.color = color;
        }
        for(Component co : components){
            co.changeColor(weight, color);
        }
    }

}

class Window{
    String name;
    HashMap<Integer, Component> componentsListInWindow;

    public Window(String s){
        this.name = s;
        componentsListInWindow = new HashMap<>();
    }

    public void addComponent(int position, Component component) throws InvalidPositionException{
        if(componentsListInWindow.containsKey(position))
            throw new InvalidPositionException(position);

        componentsListInWindow.put(position, component);
    }

    @Override
    public String toString() {
        String s = "WINDOW " + name +"\n";
        int i = 1;
        for(Map.Entry<Integer, Component> entry : componentsListInWindow.entrySet()){
            Component comp = entry.getValue();
            s+= i + ":" + comp.printRecursively(0);
            i++;
        }
        return s;
    }

    public void changeColor(int weight, String color){
        for(Map.Entry<Integer, Component> entry : componentsListInWindow.entrySet()){
            entry.getValue().changeColor(weight, color);
        }
    }

    public void swichComponents(int pos1, int pos2){
        Component comp1 = componentsListInWindow.get(pos1);
        Component comp2 = componentsListInWindow.get(pos2);

        componentsListInWindow.put(pos1, comp2);
        componentsListInWindow.put(pos2, comp1);
    }
}

class InvalidPositionException extends Exception{
    public InvalidPositionException(int pos){
        super(String.format("Invalid position " + pos + ", alredy taken!"));
    }
}