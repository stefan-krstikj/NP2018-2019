import java.util.*;

class IntegerList{

    private ArrayList<Integer> list;

    public IntegerList(){
        list = new ArrayList<>();
    }

    public IntegerList(Integer... numbers){
        list = new ArrayList<>(Arrays.asList(numbers)); // gresen konstruktor
    }

    public void add(int el, int idx){
        if(idx < list.size())
            list.add(idx, el);
        else{
            int toAdd = idx - list.size();
            for(int i = 0; i < toAdd; i++)
                list.add(0);
            list.add(el);
        }
    }


    public int remove(int idx){
        if(idx < 0 || idx >= list.size())
            throw new ArrayIndexOutOfBoundsException();
        int tmp = list.get(idx);
        list.remove(idx);
        return tmp;
    }

    public void set(int el, int idx){
        if(idx < 0 || idx >= list.size())
            throw new ArrayIndexOutOfBoundsException();
        list.set(idx, el);
    }

    public int get(int idx){
        if(idx < 0 || idx >= list.size())
            throw new ArrayIndexOutOfBoundsException();
        int a = list.get(idx);
        return a;
    }

    public int size(){
        return list.size();
    }

    public int count(int el){
        int c = 0;
        for(int i = 0; i < list.size(); i++){
            if(list.get(i) == el)
                c++;
        }
        return c;
    }

    public void removeDuplicates(){
        for(int i = 0; i < list.size(); i++){
            for(int j = 0; j < i; j++){
                if(list.get(i) == list.get(j)) {
                    list.remove(j);
                    i--;
                    break;
                }
            }
        }
    }

    public int sumFirst(int k){
        if(k<0)
            k = 0;
        if(k > list.size())
            k = list.size();
        int sum = 0;
        for(int i = 0; i < k; i++)
            sum+=list.get(i);
        return sum;
    }

    public int sumLast(int k) {
        int sum = 0;
        int counter = 0;
        for (int i = list.size()-1; i >= 0; i--) {
            sum += list.get(i);
            counter++;
            if(counter == k)
                break;
        }
        return sum;
    }
    public void shiftRight(int idx, int k){
        int newPos = (idx+k) % list.size();
        int tmp = list.remove(idx);
        list.add(newPos, tmp);
    }

    public void shiftLeft(int idx, int k){
        int newPos = (idx-k) % list.size();
        if(newPos < 0)
            newPos += list.size();
        int tmp = list.remove(idx);
        list.add(newPos, tmp);
    }

    public IntegerList addValue(int value){
        IntegerList newArr = new IntegerList();
        for(int i = 0; i < list.size(); i++){
            newArr.add(list.get(i) + value, i);
        }
        return newArr;
    }

}


public class IntegerListTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if ( k == 0 ) { //test standard methods
            int subtest = jin.nextInt();
            if ( subtest == 0 ) {
                IntegerList list = new IntegerList();
                while ( true ) {
                    int num = jin.nextInt();
                    if ( num == 0 ) {
                        list.add(jin.nextInt(), jin.nextInt());
                    }
                    if ( num == 1 ) {
                        list.remove(jin.nextInt());
                    }
                    if ( num == 2 ) {
                        print(list);
                    }
                    if ( num == 3 ) {
                        break;
                    }
                }
            }
            if ( subtest == 1 ) {
                int n = jin.nextInt();
                Integer a[] = new Integer[n];
                for ( int i = 0 ; i < n ; ++i ) {
                    a[i] = jin.nextInt();
                }
                IntegerList list = new IntegerList(a);
                print(list);
            }
        }
        if ( k == 1 ) { //test count,remove duplicates, addValue
            int n = jin.nextInt();
            Integer a[] = new Integer[n];
            for ( int i = 0 ; i < n ; ++i ) {
                a[i] = jin.nextInt();
            }
            IntegerList list = new IntegerList(a);
            while ( true ) {
                int num = jin.nextInt();
                if ( num == 0 ) { //count
                    System.out.println(list.count(jin.nextInt()));
                }
                if ( num == 1 ) {
                    list.removeDuplicates();
                }
                if ( num == 2 ) {
                    print(list.addValue(jin.nextInt()));
                }
                if ( num == 3 ) {
                    list.add(jin.nextInt(), jin.nextInt());
                }
                if ( num == 4 ) {
                    print(list);
                }
                if ( num == 5 ) {
                    break;
                }
            }
        }
        if ( k == 2 ) { //test shiftRight, shiftLeft, sumFirst , sumLast
            int n = jin.nextInt();
            Integer a[] = new Integer[n];
            for ( int i = 0 ; i < n ; ++i ) {
                a[i] = jin.nextInt();
            }
            IntegerList list = new IntegerList(a);
            while ( true ) {
                int num = jin.nextInt();
                if ( num == 0 ) { //count
                    list.shiftLeft(jin.nextInt(), jin.nextInt());
                }
                if ( num == 1 ) {
                    list.shiftRight(jin.nextInt(), jin.nextInt());
                }
                if ( num == 2 ) {
                    System.out.println(list.sumFirst(jin.nextInt()));
                }
                if ( num == 3 ) {
                    System.out.println(list.sumLast(jin.nextInt()));
                }
                if ( num == 4 ) {
                    print(list);
                }
                if ( num == 5 ) {
                    break;
                }
            }
        }
    }

    public static void print(IntegerList il) {
        if ( il.size() == 0 ) System.out.print("EMPTY");
        for ( int i = 0 ; i < il.size() ; ++i ) {
            if ( i > 0 ) System.out.print(" ");
            System.out.print(il.get(i));
        }
        System.out.println();
    }

}