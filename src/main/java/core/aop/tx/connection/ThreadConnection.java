package core.aop.tx.connection;

import lombok.Getter;

import java.sql.Connection;
import java.sql.SQLException;

@Getter
public class ThreadConnection {
    private final Connection connection;
    private final boolean maintained;

    public ThreadConnection(Connection connection) {
        this.connection = connection;
        this.maintained = false;
    }

    public ThreadConnection(Connection connection, boolean maintained) {
        this.connection = connection;
        this.maintained = maintained;

        try {
            connection.setAutoCommit(!maintained);
        } catch (SQLException e) {
            throw new IllegalStateException("Setting auto commit failed. ", e);
        }
    }

    public void close() throws SQLException {
        if (this.connection == null) {
            return;
        }

        connection.close();
    }
}
