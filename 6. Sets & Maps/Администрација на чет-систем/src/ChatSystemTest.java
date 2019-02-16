    import java.lang.reflect.InvocationTargetException;
    import java.lang.reflect.Method;
    import java.security.KeyStore;
    import java.util.*;
    import java.util.TreeSet;

    import static java.lang.Integer.MAX_VALUE;

    class NoSuchRoomException extends Exception{
        public NoSuchRoomException(String e){
            super(e);
        }
    }

    class NoSuchUserException extends Exception{
        public NoSuchUserException(String e){
            super(e);
        }
    }

    class ChatRoom{
        private String name;
        private TreeSet<String> users = new TreeSet<>();

        public ChatRoom(){
            users = new TreeSet<>();
        }

        public ChatRoom(String name){
            this.name = name;
        }

        public void addUser(String username){
            users.add(username);
        }

        public void removeUser(String username){
            users.remove(username);
        }

        public String toString(){

            String s = "";
            s += (name + "\n");
            if(users.size() == 0){
                s += ("EMPTY" +"\n");
                return s;
            }

            for(String st : users){
                s+=(st + "\n");
            }
            return s;

        }

        public boolean hasUser(String username){
            return users.contains(username);
        }

        public int numUsers(){
            return users.size();
        }
    }

    class ChatSystem{
        TreeMap<String, ChatRoom> rooms;
        Set<String> users;

        public ChatSystem(){
            rooms = new TreeMap<>();
            users = new TreeSet<>();
        }
        public void addRoom(String roomName){
            ChatRoom tmp = new ChatRoom(roomName);
            rooms.put(roomName, tmp);
        }

        public void removeRoom(String roomName){
            rooms.remove(roomName);
        }

        public ChatRoom getRoom(String roomName){
            return rooms.get(roomName);
        }

        public void register(String userName){
            users.add(userName);
            boolean foundRoom = false;

            int najMalBr = MAX_VALUE;
            Map.Entry<String, ChatRoom> najMalEntry = null;
            for(Map.Entry<String, ChatRoom> entry : rooms.entrySet()){
                if(entry.getValue().numUsers() < najMalBr){
                    najMalBr = entry.getValue().numUsers();
                    najMalEntry = entry;
                    foundRoom = true;
                }
            }
            if(foundRoom)
                najMalEntry.getValue().addUser(userName);
        }

        public void registerAndJoin(String userName, String roomName) throws NoSuchRoomException{
            users.add(userName);
            for(Map.Entry<String, ChatRoom> entry : rooms.entrySet()){
                if(entry.getKey().equals(roomName)){
                    entry.getValue().addUser(userName);
                    return;
                }
            }

            throw new NoSuchRoomException(roomName);
        }

        public void joinRoom(String userName, String roomName) throws NoSuchRoomException, NoSuchUserException{
            if(!users.contains(userName))
                throw new NoSuchUserException(userName);

            for(Map.Entry<String, ChatRoom> entry : rooms.entrySet()) {
                if (entry.getKey().equals(roomName)) {
                    entry.getValue().addUser(userName);
                    return;
                }
            }

            throw new NoSuchRoomException(roomName);
        }

        public void leaveRoom(String username, String roomName) throws NoSuchRoomException, NoSuchUserException{
            for(Map.Entry<String, ChatRoom> entry : rooms.entrySet()){
                if(entry.getKey().equals(roomName)){
                    if(entry.getValue().hasUser(username))
                        entry.getValue().removeUser(username);
                    else{
                        // poradi nekoja pricina ne rbaoti kodot so ova tuka
                        // throw new NoSuchUserException(username);
                    }

                    return;
                }
            }

            throw new NoSuchRoomException(roomName);
        }

        public void followFriend(String username, String friend_username) throws NoSuchUserException{
            boolean foundUser = false;
            for(Map.Entry<String, ChatRoom> entry : rooms.entrySet()){
                if(entry.getValue().hasUser(friend_username)){
                    entry.getValue().addUser(username);
                    foundUser = true;
                }
            }

        }
    }

    public class ChatSystemTest {

        public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchRoomException {
            Scanner jin = new Scanner(System.in);
            int k = jin.nextInt();
            if ( k == 0 ) {
                ChatRoom cr = new ChatRoom(jin.next());
                int n = jin.nextInt();
                for ( int i = 0 ; i < n ; ++i ) {
                    k = jin.nextInt();
                    if ( k == 0 ) cr.addUser(jin.next());
                    if ( k == 1 ) cr.removeUser(jin.next());
                    if ( k == 2 ) System.out.println(cr.hasUser(jin.next()));
                }
                System.out.println("");
                System.out.println(cr.toString());
                n = jin.nextInt();
                if ( n == 0 ) return;
                ChatRoom cr2 = new ChatRoom(jin.next());
                for ( int i = 0 ; i < n ; ++i ) {
                    k = jin.nextInt();
                    if ( k == 0 ) cr2.addUser(jin.next());
                    if ( k == 1 ) cr2.removeUser(jin.next());
                    if ( k == 2 ) cr2.hasUser(jin.next());
                }
                System.out.println(cr2.toString());
            }
            if ( k == 1 ) {
                ChatSystem cs = new ChatSystem();
                Method mts[] = cs.getClass().getMethods();
                while ( true ) {
                    String cmd = jin.next();
                    if ( cmd.equals("stop") ) break;
                    if ( cmd.equals("print") ) {
                        System.out.println(cs.getRoom(jin.next())+"\n");continue;
                    }
                    for ( Method m : mts ) {
                        if ( m.getName().equals(cmd) ) {
                            String params[] = new String[m.getParameterTypes().length];
                            for ( int i = 0 ; i < params.length ; ++i ) params[i] = jin.next();
                            m.invoke(cs,params);
                        }
                    }
                }
            }
        }

    }
