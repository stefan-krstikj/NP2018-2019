import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Scanner;

abstract class Contact{
    private String date;

    public Contact() {}
    public Contact(String date){
        this.date = date;
    }

    public boolean isNewerThan(Contact c){
        int year1 = Integer.parseInt(date.substring(0, 4));
        int year2 = Integer.parseInt(c.date.substring(0, 4));

        if(year1 > year2)
            return true;
        else if(year1 < year2){
            return false;
        }

        int month1 = Integer.parseInt(date.substring(5, 7));
        int month2 = Integer.parseInt(c.date.substring(5, 7));

        if(month1 > month2)
            return true;
        else if(month1 < month2) {
            return false;
        }

        int day1 = Integer.parseInt(date.substring(0, 10));
        int day2 = Integer.parseInt(c.date.substring(0, 10));

        return (day1 >= day2);
    }

    abstract public String getType();
}

class EmailContact extends Contact{
    private String email;

    public EmailContact(String date, String email) {
        super(date);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getType() { return "Email"; }
    @Override
    public String toString() {
        return "\""+email+"\"";
    }
}

class PhoneContact extends Contact{
    private String phone;
    enum Operator{VIP, ONE, TMOBILE};

    public PhoneContact(String date, String phone){
        super(date);
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public Operator getOperator() {
        int br = Integer.parseInt(Character.toString(phone.charAt(2)));
        if(br == 0 || br == 1 || br == 2) {
            return Operator.TMOBILE;
        }
        else if(br == 5 || br == 6) {
            return Operator.ONE;
        }
        else if(br == 7 || br == 8) {
            return Operator.VIP;
        }

        return Operator.TMOBILE;
    }

    public String getType() {
        return "Phone";
    }

    @Override
    public String toString() {
        return "\""+phone+"\"";
    }
}

class Student{
    private Contact[] contacts;
    private String firstName;
    private String lastName;
    private String City;
    private int age;
    private long index;

    public Student() {}

    public Student(String firstName, String lastName, String city, int age, long index) {
        //super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.City = city;
        this.age = age;
        this.index = index;
        contacts = new Contact[0];
    }

    public void addEmailContact(String date, String email) {
        Contact[] tmp = new Contact[contacts.length+1];
        for(int i = 0; i < contacts.length; i++)
            tmp[i] = contacts[i];
        contacts = tmp;
        contacts[contacts.length-1] = new EmailContact(date, email);
    }

    public void addPhoneContact(String date, String phone) {
        Contact[] tmp = new Contact[contacts.length+1];
        for(int i = 0; i < contacts.length; i++)
            tmp[i] = contacts[i];
        contacts = tmp;
        contacts[contacts.length-1] = new PhoneContact(date, phone);
    }

    public Contact[] getEmailContacts() {
        Contact[] newArray = new Contact[contacts.length];
        int newArrayC = 0;
        for(int i = 0; i < contacts.length; i++){
            if(contacts[i].getType() == "Email"){
                newArray[newArrayC++] = contacts[i];
            }
        }
        newArray = Arrays.copyOfRange(newArray, 0, newArrayC);
        return newArray;
    }

    public Contact[] getPhoneContacts() {
        Contact[] phoneCons = new Contact[contacts.length];
        int phoneConsCount = 0;
        for(int i = 0; i < contacts.length; i++) {
            if(contacts[i].getType() == "Phone") {
                phoneCons[phoneConsCount++] = contacts[i];
            }
        }
        phoneCons = Arrays.copyOfRange(phoneCons, 0, phoneConsCount);
        return phoneCons;
    }

    public String getCity() {
        return City;
    }
    public String getFullName() {
        return firstName + lastName;
    }
    public long getIndex() {
        return index;
    }
    public Contact getLatestContact() {
        int recentInd = 0;
        for(int i = 1; i < contacts.length; i++){
            if(contacts[i].isNewerThan(contacts[recentInd]))
                recentInd = i;
        }
        return contacts[recentInd];
    }

    @Override
    public String toString() {
        String out ="{\"ime\":\""+firstName+"\", \"prezime\":\""+lastName+"\", \"vozrast\":"+age+", \"grad\":\""+City+"\", \"indeks\":"+index+", ";
        String phones = Arrays.toString(getPhoneContacts());
        String emails = Arrays.toString(getEmailContacts());
        out += "\"telefonskiKontakti\":"+phones+", \"emailKontakti\":"+emails+"}";
        return out;
    }
}

class Faculty{
    private String name;
    private Student[] students;

    public Faculty(String name, Student[] students) {
        super();
        this.name = name;
        this.students = students;
    }

    public int countStudentsFromCity(String cityName) {
        int counter = 0;
        for(int i = 0; i < students.length; i++) {
            if(students[i].getCity().equals(cityName))
                counter++;
        }
        return counter;
    }

    public Student getStudent(long index) {
        for(int i = 0; i < students.length; i++) {
            if(students[i].getIndex() == index) {
                return students[i];
            }
        }
        Student asd = new Student();
        return asd;
    }
    public double getAverageNumberOfContacts() {
        int totalCon = 0;
        for(int i = 0; i < students.length; i++) {
            totalCon += students[i].getEmailContacts().length;
            totalCon += students[i].getPhoneContacts().length;
        }
        return totalCon / (double) students.length;
    }

    public Student getStudentWithMostContacts() {
        int mostNumb = 0;
        int mostIndic = 0;

        for(int i = 0; i < students.length; i++) {
            int currStudentContacts = 0;
            currStudentContacts += students[i].getEmailContacts().length;
            currStudentContacts += students[i].getPhoneContacts().length;
            if(currStudentContacts > mostNumb) {
                mostNumb = currStudentContacts;
                mostIndic = i;
            }
            else if(currStudentContacts == mostNumb){
                if(students[i].getIndex() > students[mostIndic].getIndex()){
                    mostIndic = i;
                }
            }
        }
        return students[mostIndic];
    }

    @Override
    public String toString() {
        String out = "{\"fakultet\":\""+name+"\", \"studenti\":";
        String studenti = Arrays.toString(students);
        out +=studenti+"}";
        return out;
    }


}

public class ContactsTester {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();
        Faculty faculty = null;

        int rvalue = 0;
        long rindex = -1;

        DecimalFormat df = new DecimalFormat("0.00");

        for (int t = 0; t < tests; t++) {

            rvalue++;
            String operation = scanner.next();

            switch (operation) {
                case "CREATE_FACULTY": {
                    String name = scanner.nextLine().trim();
                    int N = scanner.nextInt();

                    Student[] students = new Student[N];

                    for (int i = 0; i < N; i++) {
                        rvalue++;

                        String firstName = scanner.next();
                        String lastName = scanner.next();
                        String city = scanner.next();
                        int age = scanner.nextInt();
                        long index = scanner.nextLong();

                        if ((rindex == -1) || (rvalue % 13 == 0))
                            rindex = index;

                        Student student = new Student(firstName, lastName, city,
                                age, index);
                        students[i] = student;
                    }

                    faculty = new Faculty(name, students);
                    break;
                }

                case "ADD_EMAIL_CONTACT": {
                    long index = scanner.nextInt();
                    String date = scanner.next();
                    String email = scanner.next();

                    rvalue++;

                    if ((rindex == -1) || (rvalue % 3 == 0))
                        rindex = index;

                    faculty.getStudent(index).addEmailContact(date, email);
                    break;
                }

                case "ADD_PHONE_CONTACT": {
                    long index = scanner.nextInt();
                    String date = scanner.next();
                    String phone = scanner.next();

                    rvalue++;

                    if ((rindex == -1) || (rvalue % 3 == 0))
                        rindex = index;

                    faculty.getStudent(index).addPhoneContact(date, phone);
                    break;
                }

                case "CHECK_SIMPLE": {
                    System.out.println("Average number of contacts: "
                            + df.format(faculty.getAverageNumberOfContacts()));

                    rvalue++;

                    String city = faculty.getStudent(rindex).getCity();
                    System.out.println("Number of students from " + city + ": "
                            + faculty.countStudentsFromCity(city));

                    break;
                }

                case "CHECK_DATES": {

                    rvalue++;

                    System.out.print("Latest contact: ");
                    Contact latestContact = faculty.getStudent(rindex)
                            .getLatestContact();
                    if (latestContact.getType().equals("Email"))
                        System.out.println(((EmailContact) latestContact)
                                .getEmail());
                    if (latestContact.getType().equals("Phone"))
                        System.out.println(((PhoneContact) latestContact)
                                .getPhone()
                                + " ("
                                + ((PhoneContact) latestContact).getOperator()
                                .toString() + ")");

                    if (faculty.getStudent(rindex).getEmailContacts().length > 0&&faculty.getStudent(rindex).getPhoneContacts().length > 0) {
                        System.out.print("Number of email and phone contacts: ");
                        System.out
                                .println(faculty.getStudent(rindex)
                                        .getEmailContacts().length
                                        + " "
                                        + faculty.getStudent(rindex)
                                        .getPhoneContacts().length);

                        System.out.print("Comparing dates: ");
                        int posEmail = rvalue
                                % faculty.getStudent(rindex).getEmailContacts().length;
                        int posPhone = rvalue
                                % faculty.getStudent(rindex).getPhoneContacts().length;

                        System.out.println(faculty.getStudent(rindex)
                                .getEmailContacts()[posEmail].isNewerThan(faculty
                                .getStudent(rindex).getPhoneContacts()[posPhone]));
                    }

                    break;
                }

                case "PRINT_FACULTY_METHODS": {
                    System.out.println("Faculty: " + faculty.toString());
                    System.out.println("Student with most contacts: "
                            + faculty.getStudentWithMostContacts().toString());
                    break;
                }

            }

        }

        scanner.close();
    }
}
