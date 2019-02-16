import java.util.*;

public class BooksTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        BookCollection booksCollection = new BookCollection();
        Set<String> categories = fillCollection(scanner, booksCollection);
        System.out.println("=== PRINT BY CATEGORY ===");
        for (String category : categories) {
            System.out.println("CATEGORY: " + category);
            booksCollection.printByCategory(category);
        }
        System.out.println("=== TOP N BY PRICE ===");
        print(booksCollection.getCheapestN(n));
    }

    static void print(List<Book> books) {
        for (Book book : books) {
            System.out.println(book);
        }
    }

    static TreeSet<String> fillCollection(Scanner scanner,
                                          BookCollection collection) {
        TreeSet<String> categories = new TreeSet<String>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            Book book = new Book(parts[0], parts[1], Float.parseFloat(parts[2]));
            collection.addBook(book);
            categories.add(parts[1]);
        }
        return categories;
    }
}

// Вашиот код овде
class BookCollection{
    TreeMap<String, TreeSet<Book>> bookList;

    public BookCollection(){
        bookList = new TreeMap<>();
    }

    public void addBook(Book book){
        String insertCat = book.category.toUpperCase();

        if(bookList.containsKey(insertCat)){
            bookList.get(insertCat).add(book);
        }
        else{
            TreeSet<Book> newSet = new TreeSet<>();
            newSet.add(book);
            bookList.put(insertCat, newSet);
        }
    }

    public void printByCategory(String category){
        String findCategory = category.toUpperCase();

        TreeSet<Book> set = bookList.get(findCategory);
        for(Book b : set){
            System.out.println(b);
        }
    }

    public List<Book> getCheapestN(int n){
        ArrayList<Book> outPut = new ArrayList<>();
        for(Map.Entry<String, TreeSet<Book>> entry : bookList.entrySet()){
            for(Book b : entry.getValue()){
                outPut.add(b);
            }
        }

        Comparator comparator = new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Book book1 = (Book) o1;
                Book book2 = (Book) o2;
                if(book1.price == book2.price){
                    return book1.title.compareTo(book2.title);
                }
                return Float.compare(book1.price, book2.price);
            }
        };

        outPut.sort(comparator);

        return outPut;
    }

}

class Book implements Comparable{
    String title, category;
    float price;

    public Book(String title, String category, float price){
        this.title = title;
        this.category = category;
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) %.2f", title, category, price);
    }

    @Override
    public int compareTo(Object o) {
        Book obj = (Book) o;
        if(title.equals(obj.title)){
            return Float.compare(price, obj.price);
        }
        return title.compareTo(obj.title);

    }
}

