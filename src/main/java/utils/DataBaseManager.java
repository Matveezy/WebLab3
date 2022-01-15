package utils;

import entity.Result;

import javax.faces.context.FacesContext;
import javax.faces.view.facelets.FaceletContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseManager {
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:orbis";
    private static final String USER = "";
    private static final String PASS = "";
    private static Connection connection = null;
    private static Statement statement = null;
    private static boolean connected;

    static {
        DataBaseManager.connect();

    }

    public static boolean connect() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

            if (connection != null) {
                System.out.println("Успешное подключение к базе данных");
                statement = connection.createStatement();
                connected = true;
                return true;
            } else {
                System.out.println("Не удалось подключиться к базе данных!");
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Oracle JDBC Driver не найден. Подключите библиотеку PostgreSQL!");
        } catch (SQLException e1) {
            System.out.println(e1.getMessage());
        }
        connected = false;
        return false;
    }

    public static boolean addBean(Result result) {
        String select = "INSERT INTO RESULTS (x, y, r, current_time, execution_time, is_hit , session_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String id = facesContext.getExternalContext().getSessionId(false);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(select);
            preparedStatement.setFloat(1, result.getX());
            preparedStatement.setFloat(2, result.getY());
            preparedStatement.setFloat(3, result.getR());
            preparedStatement.setString(4, result.getCurrentTime());
            preparedStatement.setFloat(5, result.getExecutionTime());
            preparedStatement.setBoolean(6, result.isResult());
            preparedStatement.setString(7, id);
            if (preparedStatement.executeUpdate() != 0) return true;
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении бина в базу" + e.getMessage());
        } catch (ClassCastException e) {
            System.out.println("ClassCastException при добавлении в базу");
        }
        return false;
    }

    public static void load(List<Result> list) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String id = facesContext.getExternalContext().getSessionId(false);
        boolean flag;
        String request = "SELECT * from RESULTS  WHERE SESSION_ID = '" + id + "'";
        try {
            ResultSet resultSet = getStatement().executeQuery(request);
            while (resultSet.next()) {
                flag = true;
                float corX = resultSet.getFloat("x");
                float corY = resultSet.getFloat("y");
                float corR = resultSet.getFloat("r");
                String currentTime = resultSet.getString("current_time");
                long exTime = resultSet.getLong("execution_time");
                boolean isHit = resultSet.getBoolean("is_hit");
                String session = resultSet.getString("SESSION_ID");
                Result result = new Result();
                if (valX(corX) && valY(corY) && valR(corR)) {
                    result.setX(corX);
                    result.setY(corY);
                    result.setR(corR);
                    result.setCurrentTime(currentTime);
                    result.setExecutionTime(exTime);
                    result.setResult(isHit);
                    result.setSession_id(session);
                    for (Result vals : list) {
                        if (vals.equals(result)) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) list.add(result);
                }
            }
        } catch (Exception e) {
            System.out.println("Ex in load block!" + e.getMessage());
        }
    }

    public static boolean valX(Float x) {
        if ((x == null)) return false;
        else return true;
    }

    public static boolean valY(Float y) {
        if ((y == null)) return false;
        else return true;
    }

    public static boolean valR(Float r) {
        if ((r == null)) return false;
        else return true;
    }

    public static boolean clear() {
        String request = "TRUNCATE TABLE RESULTS";
        execute(request);
        return true;
    }

    public static boolean execute(String request) {
        try {
            if (statement.execute(request)) return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


    public static Connection getConnection() {
        return connection;
    }

    public static Statement getStatement() {
        return statement;
    }

    public static boolean isConnected() {
        return connected;
    }
}
