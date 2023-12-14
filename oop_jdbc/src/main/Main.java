import java.sql.*;
import java.util.Scanner;

public class Main {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/oop_jdbc";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            displayMenu();

            System.out.print("Select operation: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> createStudentRecord();
                case 2 -> readStudentRecords();
                case 3 -> updateStudentRecord();
                case 4 -> deleteStudentRecord();
                case 5 -> {
                    System.out.println("Exiting program.");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static Connection establishDatabaseConnection() {
        try {
            return DriverManager.getConnection(DATABASE_URL);
        } catch (SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
            throw new RuntimeException("Database connection error", e);
        }
    }

    private static void createStudentRecord() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        String insertQuery = "INSERT INTO students (first_name, last_name, address) VALUES (?, ?, ?)";
        try (Connection connection = establishDatabaseConnection(); PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, address);
            statement.executeUpdate();
            System.out.println("Record created successfully.");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void readStudentRecords() {
        String selectQuery = "SELECT * FROM students";
        try (Connection connection = establishDatabaseConnection(); PreparedStatement statement = connection.prepareStatement(selectQuery); ResultSet resultSet = statement.executeQuery()) {
            displayTableHeader();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String address = resultSet.getString("address");
                displayStudentRecord(id, firstName, lastName, address);
            }
            displayTableFooter();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void updateStudentRecord() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter ID to update student: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter new first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter new last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter new address: ");
        String address = scanner.nextLine();

        String updateQuery = "UPDATE students SET first_name=?, last_name=?, address=? WHERE id=?";
        try (Connection connection = establishDatabaseConnection(); PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, address);
            statement.setInt(4, id);
            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Record updated successfully.");
            } else {
                System.out.println("No record found with ID: " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deleteStudentRecord() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter record ID to delete: ");
        int id = scanner.nextInt();

        String deleteQuery = "DELETE FROM students WHERE id=?";
        try (Connection connection = establishDatabaseConnection(); PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Record deleted successfully.");
            } else {
                System.out.println("No record found with ID: " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
