package customData;

import java.util.*;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CustomAuthenticator Auth = new CustomAuthenticator("userData.txt");
        CustomDatabase customDatabase = new CustomDatabase();

        while (true) {
            displayMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character left in the buffer.

            switch (choice) {
                case 1:
                    handleUserAuthentication(Auth, customDatabase, scanner);
                    break;
                case 2:
                    Auth.saveUserData();
                    System.out.println("Exiting Your Unique Auth System.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println("1. User Authentication");
        System.out.println("2. Database Operations");
        System.out.println("3. Exit");
        System.out.print("Select an option: ");
    }

    private static void handleUserAuthentication(CustomAuthenticator Auth, CustomDatabase customDatabase, Scanner scanner) {
        while (true) {
            displayUserAuthMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character left in the buffer.

            switch (choice) {
                case 1:
                    signUp(Auth, scanner);
                    break;
                case 2:
                    boolean loginSuccessful = login(Auth, scanner);
                    if (loginSuccessful) {
                        Scanner queryInput = new Scanner(System.in);

                        while (true) {
                            System.out.print("Enter an SQL query (or 'exit' to return to the main menu): ");
                            String query = queryInput.nextLine();

                            if (query.equalsIgnoreCase("exit")) {
                                break; // Exit the loop and return to the main menu
                            }

                            customDatabase.executeSQLQuery(query);
                        }

                        scanner.close();
                    }
                    break;
                case 3:
                    return; // Back to the main menu
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    private static void displayUserAuthMenu() {
        System.out.println("1. Sign up");
        System.out.println("2. Login");
        System.out.println("3. Back to Main Menu");
        System.out.print("Select an option: ");
    }

    private static void signUp(CustomAuthenticator Auth, Scanner scanner) {
        System.out.println("Sign Up");
        System.out.print("Enter a username: ");
        String username = scanner.next();
        scanner.nextLine(); // Consume the newline character left in the buffer.
        System.out.print("Enter a password: ");
        String password = scanner.next();
        scanner.nextLine(); // Consume the newline character left in the buffer

        try {
            boolean signUpResult = Auth.signUp(username, password);
            if (signUpResult) {
                System.out.println("Sign up successful.");
            } else {
                System.out.println("Sign up failed.");
            }
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error while hashing the password.");
        }
    }

    private static boolean login(CustomAuthenticator Auth, Scanner scanner) {
        System.out.println("Login");
        System.out.print("Enter your username: ");
        String username = scanner.next();
        scanner.nextLine(); // Consume the newline character left in the buffer.
        System.out.print("Enter your password: ");
        String password = scanner.next();
        scanner.nextLine(); // Consume the newline character left in the buffer.

        try {
            if (Auth.login(username, password)) {
                System.out.println("Login successful.");
                return true;
            } else {
                System.out.println("Login failed. Incorrect username or password.");
            }
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error while hashing the password.");
        }

        return false;
    }

    private static void createTableWithStructure(CustomDatabase customDatabase, Scanner scanner) {
        System.out.print("Enter table name: ");
        String tableName = scanner.nextLine();
        System.out.print("Enter table structure (column names separated by '|'): ");
        String tableStructure = scanner.nextLine();

        customDatabase.createTableWithStructure(tableName, tableStructure);
    }

}
