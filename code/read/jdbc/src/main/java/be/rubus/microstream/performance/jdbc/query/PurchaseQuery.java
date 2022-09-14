package be.rubus.microstream.performance.jdbc.query;

import be.rubus.microstream.performance.jdbc.model.Purchase;
import be.rubus.microstream.performance.jdbc.model.Shop;
import be.rubus.microstream.performance.jdbc.model.builder.PurchaseBuilder;
import be.rubus.microstream.performance.jdbc.model.builder.ShopBuilder;
import be.rubus.microstream.performance.jdbc.query.framework.AbstractJDBCQuery;
import be.rubus.microstream.performance.model.*;
import be.rubus.microstream.performance.model.builders.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PurchaseQuery extends AbstractJDBCQuery {

    public List<Purchase> performQuery(Connection connection, Country country, int year) {
        List<Purchase> result = new ArrayList<>();

        String sql = "SELECT * FROM purchase p, shop s, address psa, city psc, state pss, country pscc, customer c, address pca, city pcc, state pcs, country pccc " +
                "WHERE p.shop_id = s.id " +
                "AND s.address_id = psa.id " +
                "AND psa.city_id = psc.id " +
                "AND psc.state_id = pss.id " +
                "AND pss.country_id = pscc.id " +
                "AND p.customer_id = c.id " +
                "AND c.address_id = pca.id " +
                "AND pca.city_id = pcc.id " +
                "AND psa.city_id <> pca.city_id " +
                "AND pscc.id = ? " +
                "AND EXTRACT(YEAR FROM p.time_stamp) = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, country.getId());
            pstmt.setInt(2, year);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    long id = resultSet.getLong(1);
                    Purchase purchase = querySession.getOrMapInstance(Purchase.class, resultSet, id, null);
                    result.add(purchase);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public <T> T extractInstance(Class<T> aClass, ResultSet resultSet, Object extractMetadata) {
        if (aClass.isAssignableFrom(Purchase.class)) {
            return (T) getPurchase(resultSet);
        }
        if (aClass.isAssignableFrom(Country.class)) {
            return (T) getCountry(resultSet, (Integer) extractMetadata);
        }
        if (aClass.isAssignableFrom(State.class)) {
            return (T) getState(resultSet, (Integer) extractMetadata);
        }
        if (aClass.isAssignableFrom(City.class)) {
            return (T) getCity(resultSet, (Integer) extractMetadata);
        }
        if (aClass.isAssignableFrom(Address.class)) {
            return (T) getAddress(resultSet, (Integer) extractMetadata);
        }
        if (aClass.isAssignableFrom(Customer.class)) {
            return (T) getCustomer(resultSet);
        }
        if (aClass.isAssignableFrom(Shop.class)) {
            return (T) getShop(resultSet);
        }
        return null;
    }

    private Shop getShop(ResultSet resultSet) {
        try {

            long id = resultSet.getLong(6);
            String name = resultSet.getString(7);
            long addressId = resultSet.getLong(8);

            return new ShopBuilder()
                    .withId(id)
                    .withName(name)
                    .withAddress(querySession.getOrMapInstance(Address.class, resultSet, addressId, 0))
                    .build();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Purchase getPurchase(ResultSet resultSet) {
        try {

            long id = resultSet.getLong(1);
            Timestamp timestamp = resultSet.getTimestamp(2);
            long customerId = resultSet.getLong(3);
            long employeeId = resultSet.getLong(4);
            long shopId = resultSet.getLong(5);
            return new PurchaseBuilder()
                    .withId(id)
                    .withTimestamp(timestamp.toLocalDateTime())
                    .withCustomer(querySession.getOrMapInstance(Customer.class, resultSet, customerId, null))
                    .withEmployee(querySession.getOrMapInstance(Employee.class, resultSet, employeeId, null))
                    .withShop(querySession.getOrMapInstance(Shop.class, resultSet, shopId, null))
                    .build();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Country getCountry(ResultSet resultSet, int shift) {
        try {
            long id = resultSet.getLong(20);
            String name = resultSet.getString(21);
            String code = resultSet.getString(22);

            return new CountryBuilder()
                    .withId(id)
                    .withName(name)
                    .withCode(code)
                    .build();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private State getState(ResultSet resultSet, int shift) {
        try {
            long id = resultSet.getLong(17 + shift);
            String name = resultSet.getString(18 + shift);
            long countryId = resultSet.getLong(19 + shift);

            return new StateBuilder()
                    .withId(id)
                    .withName(name)
                    .withCountry(querySession.getOrMapInstance(Country.class, resultSet, countryId, shift))
                    .build();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private City getCity(ResultSet resultSet, int shift) {
        try {
            long id = resultSet.getLong(14 + shift);
            String name = resultSet.getString(15 + shift);
            long stateId = resultSet.getLong(16 + shift);

            return new CityBuilder()
                    .withId(id)
                    .withName(name)
                    .withState(querySession.getOrMapInstance(State.class, resultSet, stateId, shift))
                    .build();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private Customer getCustomer(ResultSet resultSet) {
        try {
            long id = resultSet.getLong(23);
            String name = resultSet.getString(24);
            long addressId = resultSet.getLong(25);

            return new CustomerBuilder()
                    .withId(id)
                    .withName(name)
                    .withAddress(querySession.getOrMapInstance(Address.class, resultSet, addressId, 17))
                    .build();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Address getAddress(ResultSet resultSet, int shift) {
        try {
            long id = resultSet.getLong(9 + shift);
            String address = resultSet.getString(10 + shift);
            String address2 = resultSet.getString(11 + shift);
            String zipCode = resultSet.getString(12 + shift);
            long cityId = resultSet.getLong(13 + shift);


            return new AddressBuilder()
                    .withId(id)
                    .withAddress(address)
                    .withAddress2(address2)
                    .withZipCode(zipCode)
                    .withCity(querySession.getOrMapInstance(City.class, resultSet, cityId, shift))
                    .build();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
