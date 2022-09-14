package be.rubus.microstream.performance.jdbc.query.framework;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class QuerySession {

    private final JDBCQuery query;
    private final Map<Class<?>, Map<Long, Object>> cache = new HashMap<>();

    public QuerySession(JDBCQuery query) {
        this.query = query;
    }


    public <T> T getOrMapInstance(Class<T> aClass, ResultSet rs, long id, Object extractMetadata) {
        Map<Long, Object> instanceCache = cache.computeIfAbsent(aClass, k -> new HashMap<>());

        return (T) instanceCache.computeIfAbsent(id, k -> query.extractInstance(aClass, rs, extractMetadata));
    }
}
