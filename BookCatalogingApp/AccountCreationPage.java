import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

public class AccountCreationPage extends JFrame {
    private JTextField usernameTextField;
    private JButton createAccountButton;
    private JPanel panel;
    private JLabel headLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JPasswordField passwordField;

    public AccountCreationPage() {
        setContentPane(panel);
        setTitle("Account Creation");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(640, 480);
        setVisible(true);

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    addInfo();
                } catch (InvalidAccountCreationCharacterException | UsernameAlreadyExistsException e) {
                    e.getMessage();
                }
                dispose();
                LoginPage loginPage = new LoginPage();

            }
        });
    }

    public void addInfo() throws UsernameAlreadyExistsException, InvalidAccountCreationCharacterException {
        String username = usernameTextField.getText();
        char[] passwordCharArray = passwordField.getPassword();
        String password = new String(passwordCharArray);
        File file = new File("users.txt");
        try (Scanner dataReader = new Scanner(file)) {
            while (dataReader.hasNextLine()) {
                String line = dataReader.nextLine();
                String[] data = line.split(";");

                String fileUsername = data[0];
                System.out.println(fileUsername);
                if (fileUsername.equals(username)) {
                    JOptionPane.showMessageDialog(this, "This username already exists! Try again.");
                    throw new UsernameAlreadyExistsException("This username already exists!");
                }
            }
        } catch (FileNotFoundException e) {
            e.getMessage();
        }

        if (username.contains(";")){
            JOptionPane.showMessageDialog(this, "Semi-colons (;) are not a valid character for usernames! Try again.");
            throw new InvalidAccountCreationCharacterException("Semi-colons (;) are not a valid character for usernames!");
        }

        if (password.contains(";")) {
            JOptionPane.showMessageDialog(this, "Semi-colons (;) are not a valid character for passwords! Try again.");
            throw new InvalidAccountCreationCharacterException("Semi-colons (;) are not a valid character for passwords!");
        } else {
            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("users.txt", true)))) {
                writer.println(username + ";" + password);
                writer.close();
                JOptionPane.showMessageDialog(this, "You've successfully created an account!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
