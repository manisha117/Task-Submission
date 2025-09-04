import java.util.ArrayList;
import java.util.Scanner;

// Book class
class Book {
    String title;
    String author;

    // Constructor
    Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    // Display book details
    public String toString() {
        return "Title: " + title + ", Author: " + author;
    }
}

// Main class
public class LibraryCatalog {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Book> books = new ArrayList<>();

        while (true) {
            System.out.println("\n=== LIBRARY CATALOG SYSTEM ===");
            System.out.println("1. Add a Book");
            System.out.println("2. Search by Title");
            System.out.println("3. Search by Author");
            System.out.println("4. List All Books");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            
            int choice = sc.nextInt();
            sc.nextLine();  // clear buffer

            switch (choice) {
                case 1: // Add book
                    System.out.print("Enter Book Title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter Book Author: ");
                    String author = sc.nextLine();
                    books.add(new Book(title, author));
                    System.out.println("Book added successfully!");
                    break;

                case 2: // Search by title
                    System.out.print("Enter Title to Search: ");
                    String searchTitle = sc.nextLine();
                    boolean foundTitle = false;
                    for (Book b : books) {
                        if (b.title.equalsIgnoreCase(searchTitle)) {
                            System.out.println(b);
                            foundTitle = true;
                        }
                    }
                    if (!foundTitle) System.out.println("No book found with that title.");
                    break;

                case 3: // Search by author
                    System.out.print("Enter Author to Search: ");
                    String searchAuthor = sc.nextLine();
                    boolean foundAuthor = false;
                    for (Book b : books) {
                        if (b.author.equalsIgnoreCase(searchAuthor)) {
                            System.out.println(b);
                            foundAuthor = true;
                        }
                    }
                    if (!foundAuthor) System.out.println("No book found with that author.");
                    break;

                case 4: // List all books
                    if (books.isEmpty()) {
                        System.out.println("No books available.");
                    } else {
                        System.out.println("\n--- All Books ---");
                        for (Book b : books) {
                            System.out.println(b);
                        }
                    }
                    break;

                case 5: // Exit
                    System.out.println("Exiting... Goodbye!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
