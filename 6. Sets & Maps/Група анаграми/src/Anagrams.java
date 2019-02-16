import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.*;
import java.util.Map.Entry;

public class Anagrams {

    public static void main(String[] args) throws IOException{
        findAll(System.in);
    }

    public static void findAll(InputStream inputStream) throws IOException {
        // Vasiod kod ovde

        Scanner sc = new Scanner(inputStream);
        Map<String, ArrayList<String>> map = new HashMap<>();
        ArrayList<String> inputs = new ArrayList<>();
        while(sc.hasNext()) {

            String in = sc.nextLine();
            inputs.add(in);
            String hash;
            // string hash calculator
            char[] chars = in.toCharArray();
            Arrays.sort(chars);
            hash = new String(chars);

            if(map.containsKey(hash)){
                ArrayList<String> array = map.get(hash);
                array.add(in);
                map.put(hash, array);
            }
            else{
                ArrayList<String> array = new ArrayList<>();
                array.add(in);
                map.put(hash, array);
            }

        }

        for(int i = 0; i < inputs.size(); i++){
            for(Map.Entry<String, ArrayList<String>> entry : map.entrySet()){
                if(entry.getValue().get(0).equals(inputs.get(i))){
                    // print all
                    if(entry.getValue().size() >= 5) {
                        for (int j = 0; j < entry.getValue().size()-1; j++)
                            System.out.print(entry.getValue().get(j) + " ");

                        System.out.println(entry.getValue().get(entry.getValue().size()-1));

                    }
                    // break;
                    break;
                }
            }
        }



//        for(Map.Entry<String, ArrayList<String>> entry : map.entrySet()){
//            String key = entry.getKey();
//            ArrayList<String> value = entry.getValue();
//            if(value.size() >= 5) {
//                for (int i = 0; i < value.size(); i++)
//                    System.out.print(value.get(i) + " ");
//
//                System.out.println();
//            }
//        }
    }
}
