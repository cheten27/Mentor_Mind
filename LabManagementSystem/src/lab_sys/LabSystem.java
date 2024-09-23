package lab_sys;
import java.sql.*;
import java.util.Scanner;

public class LabSystem {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            System.out.println("Welcome to the Lab Management System");
            if (login()) {
                showAvailableTests();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean login() throws SQLException {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT * FROM Patients WHERE username=? AND password=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, password);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            System.out.println("Login successful!");
            return true;
        } else {
            System.out.println("Invalid credentials!");
            return false;
        }
    }

    private static void showAvailableTests() throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT * FROM MedicalTests";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        System.out.println("Available Medical Tests:");
        while (resultSet.next()) {
            System.out.println(resultSet.getInt("test_id") + ". " + resultSet.getString("test_name") +
                               " - $" + resultSet.getDouble("price"));
        }

        System.out.print("Enter test ID to book an appointment: ");
        int testId = scanner.nextInt();
        System.out.print("Enter appointment date (YYYY-MM-DD): ");
        String date = scanner.next();

        // Assume the patient ID is known or retrieved from the session
        int patientId = 1; // Example patient ID for testing
        bookAppointment(patientId, testId, date);
    }

    private static void bookAppointment(int patientId, int testId, String date) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String insertQuery = "INSERT INTO Appointments (patient_id, test_id, appointment_date) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
        preparedStatement.setInt(1, patientId);
        preparedStatement.setInt(2, testId);
        preparedStatement.setString(3, date);
        preparedStatement.executeUpdate();

        System.out.println("Appointment booked successfully!");
    }
}
