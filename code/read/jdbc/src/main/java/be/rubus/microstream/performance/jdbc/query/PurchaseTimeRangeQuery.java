package be.rubus.microstream.performance.jdbc.query;

import be.rubus.microstream.performance.Range;
import be.rubus.microstream.performance.jdbc.query.framework.AbstractJDBCQuery;

import java.sql.*;

public class PurchaseTimeRangeQuery extends AbstractJDBCQuery {

    public Range<Integer> performQuery(Connection connection) {
        Range<Integer> result = null;

        String sql = "SELECT min(p.time_stamp), max(p.time_stamp) FROM purchase p";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    Timestamp min = resultSet.getTimestamp(1);
                    Timestamp max = resultSet.getTimestamp(2);

                    result = new Range<>(min.toLocalDateTime().getYear(), max.toLocalDateTime().getYear());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public <T> T extractInstance(Class<T> aClass, ResultSet resultSet, Object extractMetadata) {
        throw new IllegalArgumentException("No data extract required for PurchaseTimeRangeQuery");
    }


}
