package be.rubus.microstream.performance.jdbc.query;

import be.rubus.microstream.performance.jdbc.query.framework.AbstractJDBCQuery;
import be.rubus.microstream.performance.model.Country;
import be.rubus.microstream.performance.model.builders.CountryBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CountryQuery extends AbstractJDBCQuery {

    public List<Country> performQuery(Connection connection) {
        List<Country> result = new ArrayList<>();

        String sql = "SELECT * FROM country";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    long id = resultSet.getLong(1);
                    Country country = querySession.getOrMapInstance(Country.class, resultSet, id, null);
                    result.add(country);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public <T> T extractInstance(Class<T> aClass, ResultSet resultSet, Object extractMetadata) {
        if (aClass.isAssignableFrom(Country.class)) {
            return (T) getCountry(resultSet);
        }
        return null;
    }

    private Country getCountry(ResultSet resultSet) {
        try {
            long id = resultSet.getLong(1);
            String name = resultSet.getString(2);
            String code = resultSet.getString(3);

            return new CountryBuilder()
                    .withId(id)
                    .withName(name)
                    .withCode(code)
                    .build();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
