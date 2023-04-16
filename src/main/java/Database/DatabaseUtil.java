package Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseUtil {
    private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();

    public static void runStatement(String SQL) {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement = createPreparedStatement(SQL, connection);
        executeStatement(preparedStatement);
        closeStatement(preparedStatement);
        connectionPool.returnConnection(connection);
    }

    public static void runStatement(String SQL, Map<Integer, Object> placeHoldersMap) {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement = createPreparedStatement(SQL, connection);
        setMap(preparedStatement, placeHoldersMap);
        executeStatement(preparedStatement);
        closeStatement(preparedStatement);
        connectionPool.returnConnection(connection);
    }

    public static List<?> runQuery(String SQL, Map<Integer, Object> placeHoldersMap) {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement = createPreparedStatement(SQL, connection);
        setMap(preparedStatement, placeHoldersMap);
        ResultSet resultSet = executeQuery(preparedStatement);
        List<?> list = getResultList(resultSet);
        closeResultSet(resultSet);
        closeStatement(preparedStatement);
        connectionPool.returnConnection(connection);
        return list;
    }
    public static List<?> runQuery(String SQL) {
        Connection connection = connectionPool.getConnection();
        PreparedStatement preparedStatement = createPreparedStatement(SQL, connection);
        ResultSet resultSet = executeQuery(preparedStatement);
        List<?> list = getResultList(resultSet);
        closeResultSet(resultSet);
        closeStatement(preparedStatement);
        connectionPool.returnConnection(connection);
        return list;
    }

    private static List<?> getResultList(ResultSet resultSet){
        try {
            return listFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static List<?> listFromResultSet(ResultSet resultSet) throws SQLException {
        List<HashMap<String, Object>> list = new ArrayList<>();
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columns = metaData.getColumnCount();
            while (resultSet.next()) {
                HashMap<String, Object> row = new HashMap<>(columns);
                for (int i = 1; i <= columns; ++i) {
                    row.put(metaData.getColumnName(i), resultSet.getObject(i));
                }
                list.add(row);
            }
        } catch (Exception e) {
            System.out.println("Unable to read result set : " + e.getMessage());
        }
        return list;

    }

    public static PreparedStatement createPreparedStatement(String SQL, Connection connection) {
        try {
            return connection.prepareStatement(SQL);
        } catch (SQLException e) {
            System.out.println("unable to prepare statement : " + e.getMessage());
            return null;
        }
    }

    private static void executeStatement(PreparedStatement preparedStatement) {
        try {
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("unable to execute statement : " + e.getMessage());
        }
    }

    private static ResultSet executeQuery(PreparedStatement preparedStatement) {
        try {
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            System.out.println("unable to execute statement : " + e.getMessage());
        }
        return null;
    }

    private static void closeStatement(PreparedStatement preparedStatement) {
        try {
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("unable to close statement : " + e.getMessage());
        }
    }

    private static void closeResultSet(ResultSet resultSet) {
        try {
            resultSet.close();
        } catch (Exception e) {
            System.out.println("unable to close result set : " + e.getMessage());
        }
    }

    public static void setPlaceHolder(PreparedStatement preparedStatement, Map.Entry<Integer, Object> entry) {
        try {
            if (entry.getValue() instanceof Integer) {
                preparedStatement.setInt(entry.getKey(), (Integer) entry.getValue());
                return;
            }
            if (entry.getValue() instanceof Double) {
                preparedStatement.setDouble(entry.getKey(), (Double) entry.getValue());
                return;
            }
            if (entry.getValue() instanceof String) {
                preparedStatement.setString(entry.getKey(), (String) entry.getValue());
                return;
            }
            if (entry.getValue() instanceof Boolean) {
                preparedStatement.setBoolean(entry.getKey(), (Boolean) entry.getValue());
                return;
            }
            if (entry.getValue() instanceof Date) {
                preparedStatement.setDate(entry.getKey(), (Date) entry.getValue());
            }
        } catch (Exception e) {
            System.out.println("unable to set parameters in placeholder of statement : " + preparedStatement.toString() + ", " + e.getMessage());
        }
    }

    public static void setMap(PreparedStatement preparedStatement, Map<Integer, Object> map) {
        for (Map.Entry<Integer, Object> entry : map.entrySet()) {
            setPlaceHolder(preparedStatement, entry);
        }
    }
}
