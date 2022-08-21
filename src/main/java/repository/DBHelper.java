package repository;


import entity.Customer;
import entity.Purchase;
import errors.ErrorMy;
import enums.EnumForErrors;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class DBHelper {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/new_store_db";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "1234";

    // Поиск покупателей по фамилии:
    public static ArrayList<Customer> searchCustomersFromDb(String lastName) {
        ArrayList<Customer> customers = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery("SELECT first_name, last_name FROM customers " +
                    "WHERE (last_name='" + lastName + "')");
            int columns = result.getMetaData().getColumnCount();

            while (result.next()) {
                Customer customer = new Customer();
                for (int i = 1; i <= columns; i++) {
                    switch (i) {
                        case 1 -> customer.setFirstName(result.getString(i));
                        case 2 -> customer.setLastName(result.getString(i));
                    }
                }
                customers.add(customer);
            }

            result.close();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            try {
                ErrorMy errorMy = new ErrorMy();
                errorMy.printError(EnumForErrors.ERROR_DATABASE_CONNECT.getTitle());
            } catch (IOException exception) {
                System.out.println(EnumForErrors.ERROR_FILE_WRITE.getTitle());
            }

            System.out.println(EnumForErrors.ERROR_DATABASE_CONNECT.getTitle());
            System.exit(1);
        }

        return customers;
    }

    // Поиск покупателей, купивших определенный товар не менее, чем указанное число раз:
    public static ArrayList<Customer> searchCustomersPurchaseTimesFromDb(String productName, int minTimes) {
        ArrayList<Customer> customers = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery("SELECT first_name, last_name,\n" +
                    "COUNT (*)\n" +
                    "FROM purchases\n" +
                    "JOIN customers \n" +
                    "ON (purchases.customer_id = customers.customer_id)\n" +
                    "JOIN products\n" +
                    "ON (purchases.product_id = products.product_id)\n" +
                    "WHERE (product_name='" + productName + "')\n" +
                    "GROUP BY customers.customer_id, products.product_name\n" +
                    "HAVING COUNT(*)>=" + minTimes + ";");
            int columns = result.getMetaData().getColumnCount();

            while (result.next()) {
                Customer customer = new Customer();
                for (int i = 1; i <= columns; i++) {
                    switch (i) {
                        case 1 -> customer.setFirstName(result.getString(i));
                        case 2 -> customer.setLastName(result.getString(i));
                    }
                }
                customers.add(customer);
            }

            result.close();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            try {
                ErrorMy errorMy = new ErrorMy();
                errorMy.printError(EnumForErrors.ERROR_DATABASE_CONNECT.getTitle());
            } catch (IOException exception) {
                System.out.println(EnumForErrors.ERROR_FILE_WRITE.getTitle());
            }

            System.out.println(EnumForErrors.ERROR_DATABASE_CONNECT.getTitle());
            System.exit(1);
        }

        return customers;
    }

    // Поиск покупателей, у которых общая стоимость всех покупок за всё время попадает в заданный интервал:
    public static ArrayList<Customer> searchCustomersExpensesFromDb(int minExpenses, int maxExpenses) throws SQLException {
        ArrayList<Customer> customers = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery("SELECT first_name, last_name,\n" +
                    "SUM (product_price)\n" +
                    "FROM purchases\n" +
                    "JOIN customers \n" +
                    "ON (purchases.customer_id = customers.customer_id)\n" +
                    "JOIN products\n" +
                    "ON (purchases.product_id = products.product_id)\n" +
                    "GROUP BY customers.customer_id\n" +
                    "HAVING (SUM (product_price) > " + minExpenses + ") and (SUM (product_price) < " + maxExpenses + ");");
            int columns = result.getMetaData().getColumnCount();

            while (result.next()) {
                Customer customer = new Customer();
                for (int i = 1; i <= columns; i++) {
                    switch (i) {
                        case 1 -> customer.setFirstName(result.getString(i));
                        case 2 -> customer.setLastName(result.getString(i));
                    }
                }
                customers.add(customer);
            }

            result.close();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            try {
                ErrorMy errorMy = new ErrorMy();
                errorMy.printError(EnumForErrors.ERROR_DATABASE_CONNECT.getTitle());
            } catch (IOException exception) {
                System.out.println(EnumForErrors.ERROR_FILE_WRITE.getTitle());
            }

            System.out.println(EnumForErrors.ERROR_DATABASE_CONNECT.getTitle());
            System.exit(1);
        }

        return customers;
    }

    // Поиск покупателей, купивших меньше всего товаров. Возвращается не более, чем указанное число покупателей:
    public static ArrayList<Customer> searchBadCustomersFromDb(int countCustomers) {
        ArrayList<Customer> customers = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery("SELECT first_name, last_name,\n" +
                    "COUNT (*)\n" +
                    "FROM purchases\n" +
                    "JOIN customers \n" +
                    "ON (purchases.customer_id = customers.customer_id)\n" +
                    "JOIN products\n" +
                    "ON (purchases.product_id = products.product_id)\n" +
                    "GROUP BY customers.customer_id\n" +
                    "ORDER BY (COUNT(*))\n" +
                    "LIMIT " + countCustomers + ";");
            int columns = result.getMetaData().getColumnCount();

            while (result.next()) {
                Customer customer = new Customer();
                for (int i = 1; i <= columns; i++) {
                    switch (i) {
                        case 1 -> customer.setFirstName(result.getString(i));
                        case 2 -> customer.setLastName(result.getString(i));
                    }
                }
                customers.add(customer);
            }

            result.close();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            try {
                ErrorMy errorMy = new ErrorMy();
                errorMy.printError(EnumForErrors.ERROR_DATABASE_CONNECT.getTitle());
            } catch (IOException exception) {
                System.out.println(EnumForErrors.ERROR_FILE_WRITE.getTitle());
            }

            System.out.println(EnumForErrors.ERROR_DATABASE_CONNECT.getTitle());
            System.exit(1);
        }

        return customers;
    }

    // Выбор статистики за определенный период по каждому покупателю (товар, сумма покупок товара):
    public static ArrayList<Purchase> getStatFromDb(LocalDate startDate, LocalDate endDate) throws SQLException {
        ArrayList<Purchase> purchases = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery("SELECT customers.customer_id, first_name, last_name, " +
                    "product_name, SUM (product_price)\n" +
                    "FROM purchases\n" +
                    "JOIN customers \n" +
                    "ON (purchases.customer_id = customers.customer_id)\n" +
                    "JOIN products\n" +
                    "ON (purchases.product_id = products.product_id)\n" +
                    "WHERE purchase_date between '" + startDate + "' and '" + endDate + "'\n" +
                    "GROUP BY customers.customer_id, products.product_name, products.product_price\n" +
                    "ORDER BY customers.customer_id;");
            int columns = result.getMetaData().getColumnCount();

            while (result.next()) {
                StringBuilder customerName = new StringBuilder();
                Purchase purchase = new Purchase();
                for (int i = 1; i <= columns; i++) {
                    switch (i) {
                        case 1 -> purchase.setCustomerId(result.getInt(i));
                        case 2 -> {
                            customerName.append(result.getString(i));
                            customerName.append(" ");
                        }
                        case 3 -> {
                            customerName.append(result.getString(i));
                            String strCN = customerName.toString();
                            purchase.setCustomerName(strCN);
                        }
                        case 4 -> purchase.setProductName(result.getString(i));
                        case 5 -> purchase.setExpenses(result.getInt(i));
                    }
                }
                purchases.add(purchase);
            }

            result.close();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            try {
                ErrorMy errorMy = new ErrorMy();
                errorMy.printError(EnumForErrors.ERROR_DATABASE_CONNECT.getTitle());
            } catch (IOException exception) {
                System.out.println(EnumForErrors.ERROR_FILE_WRITE.getTitle());
            }

            System.out.println(EnumForErrors.ERROR_DATABASE_CONNECT.getTitle());
            System.exit(1);
        }

        return purchases;
    }
}
