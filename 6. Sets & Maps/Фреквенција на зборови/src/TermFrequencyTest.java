import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class TermFrequencyTest {
    public static void main(String[] args) throws FileNotFoundException {
        String[] stop = new String[] { "во", "и", "се", "за", "ќе", "да", "од",
                "ги", "е", "со", "не", "тоа", "кои", "до", "го", "или", "дека",
                "што", "на", "а", "но", "кој", "ја" };
        TermFrequency tf = new TermFrequency(System.in,
                stop);
        System.out.println(tf.countTotal());
        System.out.println(tf.countDistinct());
        System.out.println(tf.mostOften(10));
    }
}
// vasiot kod ovde
class TermFrequency{
    public HashMap<String, Integer> hashMap;

    TermFrequency(InputStream inputStream, String[] stopWords){
        Scanner sc = new Scanner(inputStream);
        hashMap = new HashMap<>();
        while(sc.hasNext()){
            String inLine = sc.nextLine();
            String[] args = inLine.split(" ");
            for(int i = 0; i < args.length; i++) {
                args[i] = args[i].replaceAll(",", "");
                args[i] = args[i].replaceAll("\\.", "");
                args[i] = args[i].replaceAll("“", "");
                args[i] = args[i].replaceAll("”", "");

                args[i] = args[i].toLowerCase();
                // check if it's a stop word
                boolean found = true;
                for(int k = 0; k < stopWords.length; k++){
                    if(args[i].equals(stopWords[k])){
                        found = false;
                        break;
                    }
                }

                if(args[i].equals(""))
                    found = false;

                if(!found)
                    continue;


                if (hashMap.containsKey(args[i])) {
                    int j = hashMap.get(args[i]);
                    j++;
                    hashMap.put(args[i], j);
                } else {
                    hashMap.put(args[i], 1);
                }

            }
        }

        System.out.println();
    }

    public int countTotal(){
        int total = 0;
        for(Map.Entry<String, Integer> entry : hashMap.entrySet())
            total+=entry.getValue();
        return total;
    }

    public int countDistinct(){
        return hashMap.size();
    }

    public String mostOften(int n){
        List<Map.Entry<String, Integer>> list = new LinkedList<>(hashMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2){
                if(o2.getValue().equals(o1.getValue())){
                    return o2.getKey().compareTo(o1.getKey());
                }
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        Map<String, Integer> tmp = new LinkedHashMap<>();
        for(Map.Entry<String, Integer> aa : list){
            tmp.put(aa.getKey(), aa.getValue());
        }

        String s = "[";

        int i = 1;
        for(Map.Entry<String, Integer> entry : tmp.entrySet()){
            s+=entry.getKey();
            i++;
            if(i == n)
                break;
            else{
                s+= ", ";
            }
        }
        return s + "]";

    }
}