import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LoginPage extends JFrame {
    private JTextField usernameTextField;
    private JButton signInButton;
    private JButton createAnAccountButton;
    private JPanel panel;
    private JPasswordField passwordField;

    public LoginPage() {
        panel = new JPanel();
        usernameTextField = new JTextField(20);
        passwordField = new JPasswordField(20);
        signInButton = new JButton("Sign In");
        createAnAccountButton = new JButton("Create an Account");

        panel.setLayout(new GridLayout(4, 2));
        panel.add(new JLabel("Username:"));
        panel.add(usernameTextField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(signInButton);
        panel.add(createAnAccountButton);

        setContentPane(panel);
        setTitle("Log In");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(640, 480);
        setLocationRelativeTo(null);
        setVisible(true);

        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    signIn();
                } catch (InvalidLoginCredentialsException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        createAnAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (actionEvent.getSource() == createAnAccountButton) {
                    dispose();
                    new AccountCreationPage();
                }
            }
        });
    }

    public void signIn() throws InvalidLoginCredentialsException {
        String username = usernameTextField.getText();
        char[] passwordArray = passwordField.getPassword();
        String password = new String(passwordArray);
        File file = new File("users.txt");
        boolean loggedIn = false;

        try (Scanner dataReader = new Scanner(file)) {
            while (dataReader.hasNextLine()) {
                String line = dataReader.nextLine();
                String[] data = line.split(";");
                String fileUsername = "";
                String filePassword = "";
                if (data.length == 2) {
                    fileUsername = data[0];
                    filePassword = data[1];
                }
                if (fileUsername.equals(username) && filePassword.equals(password)) {
                    JOptionPane.showMessageDialog(this, "You've successfully logged in!");
                    dispose();
                    new LandingPage(username);
                    loggedIn = true;
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (!loggedIn) {
            JOptionPane.showMessageDialog(this, "You've entered incorrect credentials! Try again.");
            throw new InvalidLoginCredentialsException("You've entered incorrect credentials! Try again.");
        }
    }
}
