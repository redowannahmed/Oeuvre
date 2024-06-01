import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ProfilePage extends JFrame {
    private JPanel profilePanel;
    private String username;

    public ProfilePage(String username) {
        this.username = username;
        setTitle(username + "'s profile page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome, " + username + "!");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(welcomeLabel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton homePage = new JButton("Home");
        homePage.setPreferredSize(new Dimension(100, 40));
        rightPanel.add(homePage);
        homePage.addActionListener(e -> {
            dispose();
            new LandingPage(username);
        });
        topPanel.add(rightPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        profilePanel = new JPanel();
        profilePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        profilePanel.setSize(300, 200);
        add(new JScrollPane(profilePanel), BorderLayout.CENTER);

        JButton seeReadBooksList = new JButton("Books You've Read!");
        customizeButtons(seeReadBooksList);
        seeReadBooksList.addActionListener(e -> readList(username));

        JButton seeFavoriteBooksList = new JButton("Favorite Books");
        customizeButtons(seeFavoriteBooksList);
        seeFavoriteBooksList.addActionListener(e -> favoritesList(username));

        JButton seeWantsToReadList = new JButton("Books You Want To Read");
        customizeButtons(seeWantsToReadList);
        seeWantsToReadList.addActionListener(e -> wishToReadList(username));

        profilePanel.add(seeReadBooksList);
        profilePanel.add(seeFavoriteBooksList);
        profilePanel.add(seeWantsToReadList);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void customizeButtons(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(240, 60));
        button.setBackground(new Color(204, 204, 255));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
    }

    public void readList(String username) {
        StringBuilder bookList = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("readBooks.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length == 2) {
                    String title = data[1];
                    bookList.append(title).append("\n");
                }
            }
            JOptionPane.showMessageDialog(null, bookList, "Already Read Books for" + username, JOptionPane.PLAIN_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void wishToReadList(String username) {
        StringBuilder bookList = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("wishToReadBooks.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length == 2) {
                    String title = data[1];
                    bookList.append(title).append("\n");
                }
            }
            JOptionPane.showMessageDialog(null, bookList, username + " wants to read the following books", JOptionPane.PLAIN_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void favoritesList(String username) {
        StringBuilder bookList = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("favorites.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length == 2) {
                    String title = data[1];
                    bookList.append(title).append("\n");
                }
            }
            JOptionPane.showMessageDialog(null, bookList, "Favorite Books for " + username, JOptionPane.PLAIN_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }

    public static void main(String[] args) {
        new ProfilePage("redowan ahmed");
    }
}
