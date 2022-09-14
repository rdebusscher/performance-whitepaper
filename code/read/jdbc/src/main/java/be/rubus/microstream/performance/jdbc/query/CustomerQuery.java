package be.rubus.microstream.performance.jdbc.query;

import be.rubus.microstream.performance.jdbc.query.framework.AbstractJDBCQuery;
import be.rubus.microstream.performance.model.*;
import be.rubus.microstream.performance.model.builders.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerQuery extends AbstractJDBCQuery {

    private static final int PAGE_SIZE = 100;

    public List<Customer> performQuery(Connection connection, int page) {
        List<Customer> result = new ArrayList<>();

        String sql = "SELECT * FROM customer c, address a, city ci, state s, country co WHERE c.address_id = a.id AND a.city_id = ci.id AND ci.state_id = s.id AND s.country_id = co.id LIMIT ? OFFSET ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, PAGE_SIZE);
            pstmt.setInt(2, page * PAGE_SIZE);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    long id = resultSet.getLong(1);
                    Customer customer = querySession.getOrMapInstance(Customer.class, resultSet, id, null);
                    result.add(customer);
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
        if (aClass.isAssignableFrom(State.class)) {
            return (T) getState(resultSet);
        }
        if (aClass.isAssignableFrom(City.class)) {
            return (T) getCity(resultSet);
        }
        if (aClass.isAssignableFrom(Address.class)) {
            return (T) getAddress(resultSet);
        }
        if (aClass.isAssignableFrom(Customer.class)) {
            return (T) getCustomer(resultSet);
        }
        return null;
    }

    private Country getCountry(ResultSet resultSet) {
        try {
            long id = resultSet.getLong(15);
            String name = resultSet.getString(16);
            String code = resultSet.getString(17);

            return new CountryBuilder()
                    .withId(id)
                    .withName(name)
                    .withCode(code)
                    .build();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private State getState(ResultSet resultSet) {
        try {
            long id = resultSet.getLong(12);
            String name = resultSet.getString(13);
            long countryId = resultSet.getLong(14);

            return new StateBuilder()
                    .withId(id)
                    .withName(name)
                    .withCountry(querySession.getOrMapInstance(Country.class, resultSet, countryId, null))
                    .build();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private City getCity(ResultSet resultSet) {
        try {
            long id = resultSet.getLong(9);
            String name = resultSet.getString(10);
            long stateId = resultSet.getLong(11);

            return new CityBuilder()
                    .withId(id)
                    .withName(name)
                    .withState(querySession.getOrMapInstance(State.class, resultSet, stateId, null))
                    .build();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private Customer getCustomer(ResultSet resultSet) {
        try {
            long id = resultSet.getLong(1);
            String name = resultSet.getString(2);
            long addressId = resultSet.getLong(3);

            return new CustomerBuilder()
                    .withId(id)
                    .withName(name)
                    .withAddress(querySession.getOrMapInstance(Address.class, resultSet, addressId, null))
                    .build();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Address getAddress(ResultSet resultSet) {
        try {
            long id = resultSet.getLong(4);
            String address = resultSet.getString(5);
            String address2 = resultSet.getString(6);
            String zipCode = resultSet.getString(7);
            long cityId = resultSet.getLong(8);

            return new AddressBuilder()
                    .withId(id)
                    .withAddress(address)
                    .withAddress2(address2)
                    .withZipCode(zipCode)
                    .withCity(querySession.getOrMapInstance(City.class, resultSet, cityId, null))
                    .build();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
