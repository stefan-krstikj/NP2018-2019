import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

class SuperString{
    private LinkedList<String> list;
    private Stack<String> stack;

    public SuperString(){
        list = new LinkedList<>();
        stack = new Stack<>();
    }

    public void append(String s){   // should be ok
        list.addLast(s);
        stack.push(s);
    }

    public void insert(String s){   // should be ok
        list.addFirst(s);
        stack.push(s);
    }

    public boolean contains(String s){  // works
        return this.toString().contains(s);
    }

    public void reverse(){
        LinkedList<String> oldList = (LinkedList<String>) list.clone();
        list.clear();
        stack.clear();
        for(int i = 0; i < oldList.size(); i++){
            StringBuilder sb = new StringBuilder();
            String word = oldList.get(i);
            String newWord = "";
            for(int j = word.length()-1; j>=0; j--){
               newWord += word.charAt(j);
            }

            list.addFirst(newWord);
            stack.push(newWord);
        }


    }

    public void removeLast(int k){      // ??
        for(int i = 0; i < k; i++){
            String strToRemove = stack.pop();
            list.remove(strToRemove);
        }
    }

    @Override
    public String toString() {  // works
        String s = "";
        for(int i = 0; i < list.size(); i++){
            s+=list.get(i);
        }
        return s;
    }
}

public class SuperStringTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (  k == 0 ) {
            SuperString s = new SuperString();
            while ( true ) {
                int command = jin.nextInt();
                if ( command == 0 ) {//append(String s)
                    s.append(jin.next());
                }
                if ( command == 1 ) {//insert(String s)
                    s.insert(jin.next());
                }
                if ( command == 2 ) {//contains(String s)
                    System.out.println(s.contains(jin.next()));
                }
                if ( command == 3 ) {//reverse()
                    s.reverse();
                }
                if ( command == 4 ) {//toString()
                    System.out.println(s);
                }
                if ( command == 5 ) {//removeLast(int k)
                    s.removeLast(jin.nextInt());
                }
                if ( command == 6 ) {//end
                    break;
                }
            }
        }
    }

}
