
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateOnlineStoreDB {
    private Connection connection;
    private static final String url = "jdbc:sqlite:db.sqlite";

    public CreateOnlineStoreDB() {
        try {
            connection = DriverManager.getConnection(url);
            if (connection != null) {
                System.out.println("Успешно подключено к базе данных.");

                createTables();
                insertInitialData();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private void createTables() throws SQLException {
        String createUserTable = "CREATE TABLE IF NOT EXISTS Users ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "username TEXT NOT NULL,"
                + "email TEXT NOT NULL,"
                + "password TEXT NOT NULL"
                + ")";

        String createProductTable = "CREATE TABLE IF NOT EXISTS Products ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT NOT NULL,"
                + "price REAL NOT NULL"
                + ")";

        String createOrderTable = "CREATE TABLE IF NOT EXISTS Orders ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "user_id INTEGER NOT NULL,"
                + "order_date TEXT NOT NULL,"
                + "FOREIGN KEY (user_id) REFERENCES Users(id)"
                + ")";
        String createOrderItemsTable = "CREATE TABLE IF NOT EXISTS OrderItems ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "order_id INTEGER NOT NULL,"
                + "product_id INTEGER NOT NULL,"
                + "quantity INTEGER NOT NULL,"
                + "FOREIGN KEY (order_id) REFERENCES Orders(id),"
                + "FOREIGN KEY (product_id) REFERENCES Products(id)"
                + ")";

        Statement statement = connection.createStatement();
        statement.executeUpdate(createUserTable);
        statement.executeUpdate(createProductTable);
        statement.executeUpdate(createOrderTable);
        statement.executeUpdate(createOrderItemsTable);
    }

    private void insertInitialData() {
        try {
            Statement statement = connection.createStatement();
            String insertUser = "INSERT INTO Users (username, email, password) VALUES ('JohnDoe', 'johndoe@example.com', 'password123')";
            String insertProduct = "INSERT INTO Products (name,  price) VALUES ('Product1',  19.99)";
            String insertOrder = "INSERT INTO Orders (user_id, order_date) VALUES (1, '2024-02-13')";
            String insertOrderItem = "INSERT INTO OrderItems (order_id, product_id, quantity) VALUES (1, 1, 2)";

            statement.executeUpdate(insertUser);
            statement.executeUpdate(insertProduct);
            statement.executeUpdate(insertOrder);
            statement.executeUpdate(insertOrderItem);

            System.out.println("Данные успешно добавлены.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
