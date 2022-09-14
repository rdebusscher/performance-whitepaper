package be.rubus.microstream.performance.jdbc.query.framework;

import java.sql.ResultSet;

public interface JDBCQuery {

    <T> T extractInstance(Class<T> aClass, ResultSet resultSet, Object extractMetadata);
}
