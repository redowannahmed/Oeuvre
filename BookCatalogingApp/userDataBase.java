import java.io.*;
import java.util.*;

public class userDataBase {
    private Map<String, User> users;

    public userDataBase () {
        users = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = br.readLine())!=null) {
                String[] data = line.split(";");
                if (data.length == 3){
                    String username = data[0];
                    int userID = Integer.parseInt(data[1]);
                    String password = data[2];
                    User user = new User(username, password, userID);
                    users.put(username, user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addUser (User user) {
        users.put(user.getUsername(), user);
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("users.txt", true)))) {
            writer.println(user.getUsername()+ ";" + user.getPassword());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public User getUser (String userName) {
        return users.get(userName);
    }
 }
