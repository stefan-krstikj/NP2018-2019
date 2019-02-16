import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Partial exam II 2016/2017
 */
public class FootballTableTest {
    public static void main(String[] args) throws IOException {
        FootballTable table = new FootballTable();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.lines()
                .map(line -> line.split(";"))
                .forEach(parts -> table.addGame(parts[0], parts[1],
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3])));
        reader.close();
        System.out.println("=== TABLE ===");
        System.out.printf("%-19s%5s%5s%5s%5s%5s\n", "Team", "P", "W", "D", "L", "PTS");
        table.printTable();
    }
}

// Your code here
class FootballTable{

    public Map<String, FootballTeam> teams;

    public FootballTable(){
        teams = new HashMap();
    }

    public void addGame(String homeTeam, String awayTeam, int homeGoals, int awayGoals){
        FootballTeam homeT;
        FootballTeam awayT;
        if(teams.containsKey(homeTeam)) {
            homeT = teams.get(homeTeam);
            teams.remove(homeTeam);
        }else {
            homeT = new FootballTeam(homeTeam);
        }

        if(teams.containsKey(awayTeam)) {
            awayT = teams.get(awayTeam);
            teams.remove(awayTeam);
        } else {
            awayT = new FootballTeam(awayTeam);
        }

        homeT.matches++;
        awayT.matches++;
        homeT.goals++;
        awayT.goals++;

        if(homeGoals > awayGoals){
            homeT.wins++;
            awayT.losses++;
        }
        else if(homeGoals == awayGoals){
            homeT.draws++;
            awayT.draws++;
        }
        else{
            homeT.losses++;
            awayT.wins++;
        }

        teams.put(homeTeam, homeT);
        teams.put(awayTeam, awayT);
    }

    public void printTable(){
        ArrayList<FootballTeam> teamsArr = new ArrayList<>();
        for(Map.Entry<String, FootballTeam> entry : teams.entrySet()){
            teamsArr.add(entry.getValue());
        }
        Collections.sort(teamsArr);

        for(int i = 0; i < teamsArr.size(); i++){
            System.out.printf("%2d. %-15s%5d%5d%5d%5d%5d\n",i+1 ,teamsArr.get(i).name, teamsArr.get(i).matches, teamsArr.get(i).wins,
                    teamsArr.get(i).draws, teamsArr.get(i).losses, teamsArr.get(i).getPts());
        }
    }
}



class FootballTeam implements Comparable {
    public String name;
    public int matches, wins, draws, losses, goals;

    public FootballTeam(String name){
        this.name = name;
        matches = 0;
        wins = 0;
        draws = 0;
        losses = 0;
        goals = 0;
    }

    public void setThings(int matches, int wins, int draws, int losses){
        this.matches = matches;
        this.wins = wins;
        this.draws = draws;
        this.losses = losses;
        int goals;
    }

    public int getPts(){
        return (wins * 3) + draws;
    }

    @Override
    public int compareTo(Object o) {
        FootballTeam o1 = (FootballTeam) o;
        if(getPts() > o1.getPts())
            return -1;
        if(getPts() < o1.getPts())
            return 1;
        else{
            if(goals > o1.goals){
                return -1;
            }
            else if(goals < o1.goals){
                return 1;
            }
            else{
                if(name.compareTo(o1.name) > 1)
                    return -1;
                else if(name.compareTo(o1.name) < 1)
                    return 1;
                else
                    return 0;
            }
        }

    }
}
