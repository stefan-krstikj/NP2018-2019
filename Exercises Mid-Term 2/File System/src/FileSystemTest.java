import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Partial exam II 2016/2017
 */
public class FileSystemTest {
    public static void main(String[] args) {
        FileSystem fileSystem = new FileSystem();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            fileSystem.addFile(parts[0].charAt(0), parts[1],
                    Integer.parseInt(parts[2]),
                    LocalDateTime.of(2016, 12, 29, 0, 0, 0).minusDays(Integer.parseInt(parts[3]))
            );
        }
        int action = scanner.nextInt();
        if (action == 0) {
            scanner.nextLine();
            int size = scanner.nextInt();
            System.out.println("== Find all hidden files with size less then " + size);
            List<File> files = fileSystem.findAllHiddenFilesWithSizeLessThen(size);
            files.forEach(System.out::println);
        } else if (action == 1) {
            scanner.nextLine();
            String[] parts = scanner.nextLine().split(":");
            System.out.println("== Total size of files from folders: " + Arrays.toString(parts));
            int totalSize = fileSystem.totalSizeOfFilesFromFolders(Arrays.stream(parts)
                    .map(s -> s.charAt(0))
                    .collect(Collectors.toList()));
            System.out.println(totalSize);
        } else if (action == 2) {
            System.out.println("== Files by year");
            Map<Integer, Set<File>> byYear = fileSystem.byYear();
            byYear.keySet().stream().sorted()
                    .forEach(key -> {
                        System.out.printf("Year: %d\n", key);
                        Set<File> files = byYear.get(key);
                        files.stream()
                                .sorted()
                                .forEach(System.out::println);
                    });
        } else if (action == 3) {
            System.out.println("== Size by month and day");
            Map<String, Long> byMonthAndDay = fileSystem.sizeByMonthAndDay();
            byMonthAndDay.keySet().stream().sorted()
                    .forEach(key -> System.out.printf("%s -> %d\n", key, byMonthAndDay.get(key)));
        }
        scanner.close();
    }
}

// Your code here
class FileSystem{
    HashMap<Character, TreeSet<File>> filesMap;

    public FileSystem(){
        filesMap = new HashMap<>();
    }

    public void addFile(char folder, String name, int size, LocalDateTime createdAt){
        File insertFile = new File(name, size, createdAt);
        if(filesMap.containsKey(folder)){
            TreeSet<File> oldInsertSet = filesMap.get(folder);
            oldInsertSet.add(insertFile);
            filesMap.put(folder, oldInsertSet);
        }
        else{
            TreeSet<File> newInsertSet = new TreeSet<>();
            newInsertSet.add(insertFile);
            filesMap.put(folder, newInsertSet);
        }
    }

    public List<File> findAllHiddenFilesWithSizeLessThen(int size){
        ArrayList<File> outputArr = new ArrayList<>();

        for(Map.Entry<Character, TreeSet<File>> entry : filesMap.entrySet()){
            TreeSet<File> currSet = entry.getValue();
            for(File f : currSet){
                if(f.name.startsWith(".")){
                    outputArr.add(f);
                }
            }
        }

        return outputArr;
    }

    public int totalSizeOfFilesFromFolders(List<Character> folders){
        int total = 0;
        for(int i = 0; i < folders.size(); i++){
            Character currFolder = folders.get(i);
            TreeSet<File> currFolderFiles = filesMap.get(currFolder);
            for(File f : currFolderFiles){
                total+=f.size;
            }
        }
        return total;
    }

    public Map<Integer, Set<File>> byYear(){
        HashMap<Integer, Set<File>> filesToOutput = new HashMap<>();
        for(Map.Entry<Character, TreeSet<File>> entry : filesMap.entrySet()){
            TreeSet<File> currFilesSet = new TreeSet<>();
            currFilesSet = entry.getValue();

            for(File f : currFilesSet){
                int currFileYear = f.created.getYear();
                if(filesToOutput.containsKey(currFileYear)){
                    filesToOutput.get(currFileYear).add(f);
                }
                else{
                    HashSet<File> newSetOfFiles = new HashSet<>();
                    newSetOfFiles.add(f);
                    filesToOutput.put(currFileYear, newSetOfFiles);
                }
            }
        }

        return filesToOutput;
    }

    public Map<String, Long> sizeByMonthAndDay(){
        // String e denot i mesecot, long e goleminata
        HashMap<String, Long> filesToOutput = new HashMap<>();
        for(Map.Entry<Character, TreeSet<File>> entry : filesMap.entrySet()){
            TreeSet<File> currSetFromYear = new TreeSet<>();
            currSetFromYear = entry.getValue();
            for(File f : currSetFromYear){
                // get current day + month
                String currDay = f.created.getDayOfMonth() +"";
                String currMonth = f.created.getMonth() + "";
                String DayMonth = currMonth + "-" + currDay;


                if(filesToOutput.containsKey(DayMonth)){
                    long oldLong = filesToOutput.get(DayMonth);
                    oldLong+=f.size;
                    filesToOutput.put(DayMonth, oldLong);
                }
                else {
                    long newLongEntry = f.size;
                    filesToOutput.put(DayMonth, newLongEntry);
                }
            }
        }

        return filesToOutput;
    }

}

class File implements Comparable<File>{
    String name;
    int size;
    LocalDateTime created;

    File(String name, int size, LocalDateTime created){
        this.name = name;
        this.size = size;
        this.created = created;
    }

    @Override
    public String toString() {
        return String.format("%-10s %5dB", name, size) + " " + created;
    }

    @Override
    public int compareTo(File o) {
        if(created.equals(o.created)){
            if(name.equals(o.name)){
                return Integer.compare(size, o.size);
            }
            return name.compareTo(o.name);
        }
        else{
            return created.compareTo(o.created);
        }
    }
}
