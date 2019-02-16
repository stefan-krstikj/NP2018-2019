import java.util.*;

public class NamesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        Names names = new Names();
        for (int i = 0; i < n; ++i) {
            String name = scanner.nextLine();
            names.addName(name);
        }
        n = scanner.nextInt();
        System.out.printf("===== PRINT NAMES APPEARING AT LEAST %d TIMES =====\n", n);
        names.printN(n);
        System.out.println("===== FIND NAME =====");
        int len = scanner.nextInt();
        int index = scanner.nextInt();
        System.out.println(names.findName(len, index));
        scanner.close();

    }
}

// vashiot kod ovde
class Names {
    HashMap<String, Integer> map;

    public Names() {
        map = new HashMap<>();
    }

    public void addName(String name) {
        if (!map.containsKey(name)) {
            map.put(name, 1);
        } else {
            int oldVal = map.get(name);
            oldVal++;
            map.put(name, oldVal);
        }
    }

    public void printN(int n) {
        ArrayList<String> namesArr = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() >= n) {
                namesArr.add(entry.getKey());
            }
        }

        Comparator lexiOrder = new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                String n1 = (String) o1;
                String n2 = (String) o2;

                return n1.compareTo(n2);
            }
        };
        namesArr.sort(lexiOrder);

        for(String s : namesArr) {
            // find uniqueChars
            Set<Character> uniqueChars = new HashSet<>();
            for(int i = 0; i < s.length(); i++){
                uniqueChars.add(Character.toLowerCase(s.charAt(i)));
            }
            int size = uniqueChars.size();
            System.out.println(s + " (" + map.get(s) + ") " + size);
        }
    }

    public String findName(int len, int x) {
        ArrayList<String> namesList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            namesList.add(entry.getKey());
        }

        Comparator lexiOrder = new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                String n1 = (String) o1;
                String n2 = (String) o2;

                return n1.compareTo(n2);
            }
        };
        namesList.sort(lexiOrder);

        for (int i = 0; i < namesList.size(); i++) {
            if (namesList.get(i).length() >= len) {
                namesList.remove(i);
            }

        }
        int target;
        target = x % namesList.size();
        return namesList.get(target);
    }
}

