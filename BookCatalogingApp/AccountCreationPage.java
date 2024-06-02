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
        panel = new JPanel();
        usernameTextField = new JTextField(20);
        passwordField = new JPasswordField(20);
        createAccountButton = new JButton("Create Account");
        headLabel = new JLabel("Create an Account");
        usernameLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(headLabel);
        panel.add(usernameLabel);
        panel.add(usernameTextField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(createAccountButton);

        setContentPane(panel);
        setTitle("Account Creation");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(640, 480);
        setLocationRelativeTo(null);
        setVisible(true);

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    addInfo();
                    dispose();
                    new LoginPage();
                } catch (InvalidAccountCreationCharacterException | UsernameAlreadyExistsException e) {
                    JOptionPane.showMessageDialog(AccountCreationPage.this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
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
                String fileUsername = "";
                if (data.length == 2) {
                    fileUsername = data[0];
                }
                if (fileUsername.equals(username)) {
                    throw new UsernameAlreadyExistsException("This username already exists!");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (password.contains(";")) {
            throw new InvalidAccountCreationCharacterException("Semi-colons (;) are not a valid character for passwords!");
        }

        if (username.contains(";")) {
            throw new InvalidAccountCreationCharacterException("semi-colons (;) are not valid character for usernames");
        }

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {
            writer.println(username + ";" + password);
            JOptionPane.showMessageDialog(this, "You've successfully created an account!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
