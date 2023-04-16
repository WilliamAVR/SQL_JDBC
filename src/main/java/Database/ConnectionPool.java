package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

public class ConnectionPool {
    private static final int SIZE = 10;
    private static ConnectionPool connectionPool = new ConnectionPool();
    private final Stack<Connection> connectionStack = new Stack<>();

    private ConnectionPool() {
        for (int i = 0; i < SIZE; i++) {
            try {
                connectionStack.push(DriverManager.getConnection(Credentials.URL,Credentials.USER,Credentials.PASSWORD));
            } catch (SQLException e) {
                System.out.println("unable to open new connection : "+e.getMessage());
            }
        }
    }
    public static ConnectionPool getConnectionPool(){
        return connectionPool;
    }
    public Connection getConnection(){
        synchronized (this.connectionStack){
            if(connectionStack.isEmpty()){
                try {
                    this.connectionStack.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return connectionStack.pop();
        }
    }
    public void returnConnection(Connection connection){
        synchronized (this.connectionStack){
            connectionStack.push(connection);
            this.connectionStack.notify();
        }
    }
    public void closeConnectionPool(){
        synchronized (this.connectionStack){
            while(connectionStack.size()!=SIZE){
                try {
                    this.connectionStack.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            this.connectionStack.clear();
        }
    }

}
