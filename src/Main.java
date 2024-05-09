import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    private static Connection connection = null;

    public static void main(String[] args) {
        try {
            connection = getConnection();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        String deleteTableQuery = "DROP TABLE IF EXISTS figuras";
        String createTableQuery = "CREATE TABLE IF NOT EXISTS figuras (id INT PRIMARY KEY AUTO_INCREMENT, figura VARCHAR(50) NOT NULL, color VARCHAR(20) NOT NULL)";
        String createQuery1 = "Insert into figuras (figura,color) values ('Circulo','rojo')";
        String createQuery2 = "Insert into figuras (figura,color) values ('Circulo','azul')";
        String createQuery3 = "Insert into figuras (figura,color) values ('Cuadrado','verde')";
        String createQuery4 = "Insert into figuras (figura,color) values ('Cuadrado','amarillo')";
        String createQuery5 = "Insert into figuras (figura,color) values ('Cuadrado','naranja')";
        ArrayList<String> queries = new ArrayList<>();
        queries.add(createQuery1);
        queries.add(createQuery2);
        queries.add(createQuery3);
        queries.add(createQuery4);
        queries.add(createQuery5);

        try {
            connection.createStatement().executeUpdate(deleteTableQuery);

            connection.createStatement().executeUpdate(createTableQuery);

            for (String query : queries) {
                connection.createStatement().executeUpdate(query);
            }

            String query = "SELECT * FROM figuras";
            ResultSet result = connection.createStatement().executeQuery(query);
            while (result.next()) {
                System.out.println(result.getInt("id") + " " + result.getString("figura") + " " + result.getString("color"));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        closeConnection();
    }

    public static Connection getConnection() throws SQLException {
        Connection connection;

        String databaseURL = "jdbc:mysql://localhost:9000/dh";
        String databaseUser = "root";
        String databasePassword = "root";

        connection = DriverManager.getConnection(databaseURL, databaseUser, databasePassword);

        System.out.println("Successful connection");
//        String databaseName = connection.getMetaData().getDatabaseProductName();
//        String databaseVersion = connection.getMetaData().getDatabaseProductVersion();
//        System.out.println(databaseName + " " + databaseVersion);
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Connection closed");
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }
}