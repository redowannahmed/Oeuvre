import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class LandingPage extends JFrame {
    private String username;
    private JPanel bookPanel;
    private List<Book> books;

    public LandingPage(String username) {
        books = new ArrayList<>();
        this.username = username;
        setTitle("Welcome, " + username);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 600);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome, " + username + "!");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(welcomeLabel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton goToProfile = new JButton("Profile");
        goToProfile.setFont(new Font("Arial", Font.BOLD, 14));
        goToProfile.setPreferredSize(new Dimension(100, 40));
        rightPanel.add(goToProfile);

        goToProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    dispose();
                    new ProfilePage(username);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error opening profile page.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        topPanel.add(rightPanel, BorderLayout.EAST);
        
        add(topPanel, BorderLayout.NORTH);

        bookPanel = new JPanel(new GridLayout(0, 3, 10, 10)); // 3 columns, gap of 10 pixels
        add(new JScrollPane(bookPanel), BorderLayout.CENTER);
        loadBooks();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadBooks() {
        try (BufferedReader br = new BufferedReader(new FileReader("bookDataBase.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 8) {
                    String title = parts[0];
                    String author = parts[1];
                    String genre = parts[2];
                    String publisher = parts[3];
                    String edition = parts[4];
                    int pages = Integer.parseInt(parts[5]);
                    String synopsis = parts[6];
                    String thumbnailPath  = parts[7];

                    Book book = new Book(title, author, genre, publisher, edition, pages, synopsis, thumbnailPath);
                    books.add(book);

                    addBookToPanel(book);
                } else {
                    System.out.println("Incorrect format: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading books from database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addBookToPanel(Book book) {
        JPanel bookItemPanel = new JPanel();
        bookItemPanel.setLayout(new BorderLayout());
        bookItemPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        ImageIcon icon = new ImageIcon(book.getThumbnailPath());
        Image scaledImage = icon.getImage().getScaledInstance(120, 190, Image.SCALE_SMOOTH); 
        icon = new ImageIcon(scaledImage);

        JButton titleButton = new JButton(icon);

        titleButton.setPreferredSize(new Dimension(120, 190)); 
        titleButton.setContentAreaFilled(false);
        titleButton.setFocusPainted(false);
        titleButton.setBorderPainted(false);
        titleButton.setOpaque(false);

        titleButton.addActionListener(e -> showBookOptions(book));

        bookItemPanel.add(titleButton, BorderLayout.CENTER);

        bookPanel.add(bookItemPanel);
        bookPanel.revalidate();
        bookPanel.repaint();
    }

    private void showBookOptions(Book book) {
        String optionMessage = "Choose an option for \"" + book.getTitle() + "\":\n"
                + "1. Add to Favorites\n"
                + "2. Add to Read List\n"
                + "3. Add to Wish List\n"
                + "4. See Book Details\n"
                + "5. Add a review\n"
                + "6. View reviews\n";

        String userInput = JOptionPane.showInputDialog(null, optionMessage);
    
        if (userInput != null) {
            userInput = userInput.trim();
            switch (userInput) {
                case "1":
                    saveBookToSpecificList("favorites.txt", username, book.getTitle());
                    break;
                case "2":
                    saveBookToSpecificList("readBooks.txt", username, book.getTitle());
                    break;
                case "3":
                    saveBookToSpecificList("wishToreadBooks.txt", username, book.getTitle());
                    break;
                case "4":
                    showBookDetails(book);
                    break;
                case "5":
                    addReview(username, book.getTitle());
                    break;
                case "6":
                    viewReviews(book.getTitle());
                default:
                    JOptionPane.showMessageDialog(null, "Invalid option. Please choose a valid option.");
            }
        }
    }

    public void saveBookToSpecificList (String filePath, String username, String bookTitle) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)))) {
            writer.println(username + ";" + bookTitle);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showBookDetails(Book book) {
        String details = "Title: " + book.getTitle() + "\n"
                + "Author: " + book.getAuthor() + "\n"
                + "Genre: " + book.getGenre() + "\n"
                + "Publisher: " + book.getPublisher() + "\n"
                + "Edition: " + book.getEdition() + "\n"
                + "Pages: " + book.getPages() + "\n"
                + "Synopsis: " + book.getSynopsis();
        JOptionPane.showMessageDialog(null, details, "Book Details", JOptionPane.INFORMATION_MESSAGE);
    }

    public void addReview(String username, String bookTitle) {
        String reviewInput = JOptionPane.showInputDialog(null,"Leave a review");
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("reviews.txt", true)))) {
            writer.write(bookTitle + ";" + username + ";" + reviewInput + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void viewReviews(String bookTitle) {
        boolean bookFound = false;
        DefaultTableModel tableModel = new DefaultTableModel();
        String[] headers = {"User", "Review"};
        tableModel.setColumnIdentifiers(headers);
        File file = new File("reviews.txt");
        try (Scanner dataReader = new Scanner(file)) {
            while (dataReader.hasNextLine()) {
                String currentLine = dataReader.nextLine();
                String[] lineInfo = currentLine.split(";");
                String lineBookTitle = "";
                String lineUsername = "";
                String lineReview = "";
                if (lineInfo.length == 3) {
                    lineBookTitle = lineInfo[0];
                    lineUsername = lineInfo[1];
                    lineReview = lineInfo[2];
                }
                String[] reviewInfo = {lineUsername, lineReview};
                if (lineBookTitle.equals(bookTitle)){
                    bookFound = true;
                    tableModel.addRow(reviewInfo);
                }
            }
            JTable table = new JTable(tableModel);
            JOptionPane.showMessageDialog(this, new JScrollPane(table));
            dataReader.nextLine();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUsername () {
        return username;
    }
}
