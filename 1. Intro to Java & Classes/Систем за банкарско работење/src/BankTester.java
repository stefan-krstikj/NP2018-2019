import java.util.*;
import java.util.stream.Collectors;

public class BankTester {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        String test_type = jin.nextLine();
        switch (test_type) {
            case "typical_usage":
                testTypicalUsage(jin);
                break;
            case "equals":
                testEquals();
                break;
        }
        jin.close();
    }

    private static void testEquals() {
        Account a1 = new Account("Andrej", "20.00$");
        Account a2 = new Account("Andrej", "20.00$");
        Account a3 = new Account("Andrej", "30.00$");
        Account a4 = new Account("Gajduk", "20.00$");
        List<Account> all = Arrays.asList(a1, a2, a3, a4);
        if (!(a1.equals(a1)&&!a1.equals(a2)&&!a2.equals(a1)&&!a3.equals(a1)&&!a4.equals(a1)
                && !a1.equals(null))) {
            System.out.println("Your account equals method does not work properly.");
            return;
        }
        Set<Long> ids = all.stream().map(Account::getId).collect(Collectors.toSet());
        if (ids.size() != all.size()) {
            System.out.println("Different accounts have the same IDS. This is not allowed");
            return;
        }
        FlatAmountProvisionTransaction fa1 = new FlatAmountProvisionTransaction(10, 20, "20.00$", "10.00$");
        FlatAmountProvisionTransaction fa2 = new FlatAmountProvisionTransaction(20, 20, "20.00$", "10.00$");
        FlatAmountProvisionTransaction fa3 = new FlatAmountProvisionTransaction(20, 10, "20.00$", "10.00$");
        FlatAmountProvisionTransaction fa4 = new FlatAmountProvisionTransaction(10, 20, "50.00$", "50.00$");
        FlatAmountProvisionTransaction fa5 = new FlatAmountProvisionTransaction(30, 40, "20.00$", "10.00$");
        FlatPercentProvisionTransaction fp1 = new FlatPercentProvisionTransaction(10, 20, "20.00$", 10);
        FlatPercentProvisionTransaction fp2 = new FlatPercentProvisionTransaction(10, 20, "20.00$", 10);
        FlatPercentProvisionTransaction fp3 = new FlatPercentProvisionTransaction(10, 10, "20.00$", 10);
        FlatPercentProvisionTransaction fp4 = new FlatPercentProvisionTransaction(10, 20, "50.00$", 10);
        FlatPercentProvisionTransaction fp5 = new FlatPercentProvisionTransaction(10, 20, "20.00$", 30);
        FlatPercentProvisionTransaction fp6 = new FlatPercentProvisionTransaction(30, 40, "20.00$", 10);
        if (fa1.equals(fa1) &&
                !fa2.equals(null) &&
                fa2.equals(fa1) &&
                fa1.equals(fa2) &&
                fa1.equals(fa3) &&
                !fa1.equals(fa4) &&
                !fa1.equals(fa5) &&
                !fa1.equals(fp1) &&
                fp1.equals(fp1) &&
                !fp2.equals(null) &&
                fp2.equals(fp1) &&
                fp1.equals(fp2) &&
                fp1.equals(fp3) &&
                !fp1.equals(fp4) &&
                !fp1.equals(fp5) &&
                !fp1.equals(fp6)) {
            System.out.println("Your transactions equals methods do not work properly.");
            return;
        }
        Account accounts[] = new Account[]{a1, a2, a3, a4};
        Account accounts1[] = new Account[]{a2, a1, a3, a4};
        Account accounts2[] = new Account[]{a1, a2, a3};
        Account accounts3[] = new Account[]{a1, a2, a3, a4};

        Bank b1 = new Bank("Test", accounts);
        Bank b2 = new Bank("Test", accounts1);
        Bank b3 = new Bank("Test", accounts2);
        Bank b4 = new Bank("Sample", accounts);
        Bank b5 = new Bank("Test", accounts3);

        if (!(b1.equals(b1) &&
                !b1.equals(null) &&
                !b1.equals(b2) &&
                !b2.equals(b1) &&
                !b1.equals(b3) &&
                !b3.equals(b1) &&
                !b1.equals(b4) &&
                b1.equals(b5))) {
            System.out.println("Your bank equals method do not work properly1.");
            return;
        }
        accounts[2] = a1;
        if (!b1.equals(b5)) {
            System.out.println("Your bank equals method do not work properly2.");
            return;
        }
        long from_id = a2.getId();
        long to_id = a3.getId();
        Transaction t = new FlatAmountProvisionTransaction(from_id, to_id, "3.00$", "3.00$");
        b1.makeTransaction(t);
        if (b1.equals(b5)) {
            System.out.println("Your bank equals method do not work properly3.");
            return;
        }
        b5.makeTransaction(t);
        if (!b1.equals(b5)) {
            System.out.println("Your bank equals method do not work properly4.");
            return;
        }
        System.out.println("All your equals methods work properly.");
    }

    private static void testTypicalUsage(Scanner jin) {
        String bank_name = jin.nextLine();
        int num_accounts = jin.nextInt();
        jin.nextLine();
        Account accounts[] = new Account[num_accounts];
        for (int i = 0; i < num_accounts; ++i)
            accounts[i] = new Account(jin.nextLine(), jin.nextLine());
        Bank bank = new Bank(bank_name, accounts);
        while (true) {
            String line = jin.nextLine();
            switch (line) {
                case "stop":
                    return;
                case "transaction":
                    String descrption = jin.nextLine();
                    String amount = jin.nextLine();
                    String parameter = jin.nextLine();
                    int from_idx = jin.nextInt();
                    int to_idx = jin.nextInt();
                    jin.nextLine();
                    Transaction t = getTransaction(descrption, from_idx, to_idx, amount, parameter, bank);
                    System.out.println("Transaction amount: " + t.getAmount());
                    System.out.println("Transaction description: " + t.getDescription());
                    System.out.println("Transaction successful? " + bank.makeTransaction(t));
                    break;
                case "print":
                    System.out.println(bank.toString());
                    System.out.println("Total provisions: " + bank.totalProvision());
                    System.out.println("Total transfers: " + bank.totalTransfers());
                    System.out.println();
                    break;
            }
        }
    }

    private static Transaction getTransaction(String description, int from_idx, int to_idx, String amount, String o, Bank bank) {
        switch (description) {
            case "FlatAmount":
                return new FlatAmountProvisionTransaction(bank.getAccounts()[from_idx].getId(),
                        bank.getAccounts()[to_idx].getId(), amount, o);
            case "FlatPercent":
                return new FlatPercentProvisionTransaction(bank.getAccounts()[from_idx].getId(),
                        bank.getAccounts()[to_idx].getId(), amount, Integer.parseInt(o));
        }
        return null;
    }


}

class Account{
    String name;
    long id;
    double balance;

    public Account(String name, String balance){
        this.name = name;
        this.balance = Double.parseDouble(balance.substring(0, balance.length()-1));
        Random random = new Random();
        id = random.nextLong();
    }

    public String getBalance(){
        return String.format("%.2f$", balance);
    }

    public String getName(){
        return name;
    }

    public long getId(){
        return id;
    }

    public void setBalance(String balance){
        this.balance = Double.parseDouble(balance);
    }

    public String toString(){
        return String.format("Name: %s\nBalance: %.2f$", name, balance);
    }

    @Override
    public boolean equals(Object o){
        if (o == null){
            return false;
        }
        else if (getClass() != o.getClass()){
            return false;
        }
        else{
            Account tmp = (Account) o;
            if (this.getId() == tmp.getId() && name.equals(tmp.getName())&& balance == tmp.balance){
                return true;
            }
            return false;
        }

    }
}

class Transaction{
    long fromId, toId;
    String description, amount;

    public Transaction(long fromId, long toId, String description, String amount) {
        this.fromId = fromId;
        this.toId = toId;
        this.description = description;
        this.amount = amount;
    }

    public String getAmount(){
        return amount;
    }

    public long getFromId(){
        return fromId;
    }

    public long getToId(){
        return toId;
    }


    public String getDescription(){
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        Transaction that = (Transaction) o;
        return fromId == that.fromId &&
                toId == that.toId &&
                Objects.equals(description, that.description) &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromId, toId, description, amount);
    }
}

class FlatAmountProvisionTransaction extends Transaction{
    String flatProvision;

    public FlatAmountProvisionTransaction(long fromId, long toId, String amount, String flatProvision){
        super(fromId, toId, "FlatAmount", amount);
        this.flatProvision = flatProvision;
    }

    public String getFlatAmount(){
        return flatProvision;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FlatAmountProvisionTransaction)) return false;
        FlatAmountProvisionTransaction that = (FlatAmountProvisionTransaction) o;
        return Objects.equals(flatProvision, that.flatProvision);
    }

}

class FlatPercentProvisionTransaction extends Transaction{
    int centsPerDollar;

    FlatPercentProvisionTransaction (long fromId, long toId, String amount, int centsPerDolar){
        super(fromId, toId, "FlatPercent", amount);
        this.centsPerDollar = centsPerDolar;
    }

    public int getPercent(){
        return centsPerDollar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FlatPercentProvisionTransaction)) return false;
        FlatPercentProvisionTransaction that = (FlatPercentProvisionTransaction) o;
        return centsPerDollar == that.centsPerDollar;
    }

    @Override
    public int hashCode() {
        return Objects.hash(centsPerDollar);
    }
}

class Bank{
    String name;
    Account accounts[];
    double totalTrans;
    double totalProv;

    public Bank(String name, Account accounts[]){
        this.name = name;
        this.accounts = new Account[accounts.length];
        this.accounts = accounts;
        totalProv = 0;
        totalTrans = 0;
    }

    public boolean makeTransaction(Transaction t){
        Account fromAcc = null;
        Account toAcc = null;

        for(int i = 0; i < accounts.length; i++){
            if(accounts[i].id == t.fromId) {
                fromAcc = accounts[i];
            }
            if(accounts[i].id == t.toId) {
                toAcc = accounts[i];
            }
        }

        if(fromAcc == null || toAcc == null)
            return false;

        if(fromAcc.balance < Double.parseDouble(t.amount.substring(0, t.amount.length()-1)))
            return false;

        double transactionAmount = Double.parseDouble(t.amount.substring(0, t.amount.length()-1));

        fromAcc.balance-= transactionAmount;
        toAcc.balance+= transactionAmount;

        totalTrans+=transactionAmount;

        double provision = 0;

        if(t instanceof FlatAmountProvisionTransaction){
            FlatAmountProvisionTransaction fapt = (FlatAmountProvisionTransaction) t;
            provision = Double.parseDouble(fapt.flatProvision.substring(0, fapt.flatProvision.length()-1));
        }
        else{
            FlatPercentProvisionTransaction fppt = (FlatPercentProvisionTransaction) t;
            provision = fppt.centsPerDollar * (int) transactionAmount / 100.0000000;
        }
        totalProv+= provision;
        fromAcc.balance-=provision;


        return true;
    }

    public String totalTransfers(){
        return String.format("%.2f$", totalTrans);
    }

    public String totalProvision(){
        return String.format("%.2f$", totalProv);
    }

    public String toString(){
        String out = "Name: " + name + "\n\n";
        for(int i = 0; i < accounts.length; i++)
            out+=accounts[i] + "\n";
        return out;
    }

    public Account[] getAccounts(){
        return accounts;
    }

    @Override
    public boolean equals(Object o){
        if (o == null)
            return false;
        else if (getClass() != o.getClass())
            return false;

        Bank tmp = (Bank) o;
        if (!name.equals(tmp.name))
            return false;

        if (accounts.length != tmp.getAccounts().length)
            return false;

        for(int i = 0; i < accounts.length; i++){
            Account a1 = accounts[i];
            Account a2 = tmp.accounts[i];

            if(a1 != a2)
                return false;
        }

        if (!totalTransfers().equals(tmp.totalTransfers()))
            return false;

        if (!totalProvision().equals(tmp.totalProvision()))
            return false;


        return true;

    }
}