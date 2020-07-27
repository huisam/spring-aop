package core.aop.tx.connection;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConnectionHolder {

    private static final ThreadLocal<ThreadConnection> CONNECTION_THREAD = new ThreadLocal<>();

    public static boolean hasConnection() {
        return getThreadConnection() != null;
    }

    public static void setConnection(Connection connection, boolean maintained) {
        ThreadConnection threadConnection = getThreadConnection();
        if (threadConnection == null) {
            ThreadConnection newThreadConnection = new ThreadConnection(connection, maintained);
            CONNECTION_THREAD.set(newThreadConnection);
            return;
        }

        CONNECTION_THREAD.set(threadConnection);
    }

    public static Connection getConnection() {
        ThreadConnection threadConnection = getThreadConnection();
        if (threadConnection == null) {
            throw new IllegalStateException("Connection doesn't exists");
        }

        return threadConnection.getConnection();
    }

    private static ThreadConnection getThreadConnection() {
        return CONNECTION_THREAD.get();
    }

    public static void clear() throws SQLException {
        final ThreadConnection threadConnection = getThreadConnection();
        if (threadConnection == null || threadConnection.isMaintained()) {
            return;
        }

        threadConnection.close();
        CONNECTION_THREAD.remove();
    }

    public static void clearCompletely() throws SQLException {
        ThreadConnection connection = getThreadConnection();
        if (connection == null) {
            return;
        }

        connection.close();
        CONNECTION_THREAD.remove();
    }

}
