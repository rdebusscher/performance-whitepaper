package be.rubus.microstream.performance.jdbc.query.framework;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public abstract class AbstractJDBCQuery implements JDBCQuery {

    protected QuerySession querySession;

    private boolean metadataShown = false;

    public AbstractJDBCQuery() {
        querySession = new QuerySession(this);
    }

    /**
     * You can use this method to show the metadata of a result set to check the fields and
     * make it easier to create the conversion to Objects.
     * @param resultSet The resultset to get metadata from.
     * @throws SQLException When an issue occured during retrieval of the metadata.
     */
    protected void showMetadata(ResultSet resultSet) throws SQLException {
        if (!metadataShown) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("%s - %s %n", i, metaData.getColumnName(i));
            }
            metadataShown = true;
        }
    }

}
