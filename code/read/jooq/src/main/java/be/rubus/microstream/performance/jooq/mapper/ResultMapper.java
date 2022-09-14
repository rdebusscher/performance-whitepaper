package be.rubus.microstream.performance.jooq.mapper;

import be.rubus.microstream.performance.model.*;
import be.rubus.microstream.performance.model.builders.*;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.TableField;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.*;

public final class ResultMapper {

    private static final ResultMapper INSTANCE = new ResultMapper();

    private final Map<Class<?>, List<WeakReference>> cache = new HashMap<>();

    private ResultMapper() {
    }

    public <T> T map(Record record, Class<T> clazz) {

        if (Customer.class.equals(clazz)) {
            return (T) mapCustomer(record);
        }
        if (Book.class.equals(clazz)) {
            return (T) mapBook(record);
        }
        if (Author.class.equals(clazz)) {
            return (T) mapAuthor(record);
        }
        if (Address.class.equals(clazz)) {
            return (T) mapAddress(record);
        }
        if (City.class.equals(clazz)) {
            return (T) mapCity(record);
        }
        if (State.class.equals(clazz)) {
            return (T) mapState(record);
        }
        if (Country.class.equals(clazz)) {
            return (T) mapCountry(record);
        }
        if (BookSales.class.equals(clazz)) {
            return (T) mapBookSales(record);
        }
        return null;
    }

    private Country mapCountry(Record record) {
        List<TableField<?, ?>> fields = getFields(record, "country");
        CountryBuilder builder = new CountryBuilder();
        for (TableField<?, ?> field : fields) {
            switch (field.getName()) {
                case "id":
                    builder.withId(record.get(asLongField(field)));
                    break;
                case "code":
                    builder.withCode(record.get(asStringField(field)));
                    break;
                case "name":
                    builder.withName(record.get(asStringField(field)));
                    break;
                default:
                    throw new RuntimeException("Unknown Field " + field);
            }
        }
        Country result = builder.build();
        putInCache(Country.class, result);
        return result;
    }


    private BookSales mapBookSales(Record record) {
        List<TableField<?, ?>> fields = getFields(record, "book");
        BookBuilder builder = new BookBuilder();
        for (TableField<?, ?> field : fields) {
            switch (field.getName()) {
                case "id":
                    builder.withId(record.get(asLongField(field)));
                    break;
                case "isbn13":
                    builder.withIsbn13(record.get(asStringField(field)));
                    break;
                case "title":
                    builder.withTitle(record.get(asStringField(field)));
                    break;
                case "purchase_price":
                    builder.withPurchasePrice(record.get(asDoubleField(field)));
                    break;
                case "retail_price":
                    builder.withRetailPrice(record.get(asDoubleField(field)));
                    break;

                case "author_id":
                case "genre_id":
                case "language_id":
                case "publisher_id":
                    // Ignored
                    break;
                default:
                    throw new RuntimeException("Unknown Field " + field);

            }
        }
        Book book = builder.build();
        Integer sum = record.get("sum", Integer.class);
        return new BookSales(book, sum);

    }

    private State mapState(Record record) {

        List<TableField<?, ?>> fields = getFields(record, "state");
        StateBuilder builder = new StateBuilder();
        for (TableField<?, ?> field : fields) {
            switch (field.getName()) {
                case "id":
                    builder.withId(record.get(asLongField(field)));
                    break;
                case "name":
                    builder.withName(record.get(asStringField(field)));
                    break;
                case "country_id":
                    builder.withCountry(computeIfMissingFromCache(record, Country.class, record.get(asLongField(field))));
                    break;
                default:
                    throw new RuntimeException("Unknown Field " + field);
            }
        }
        State result = builder.build();
        putInCache(State.class, result);
        return result;
    }

    private City mapCity(Record record) {
        List<TableField<?, ?>> fields = getFields(record, "city");
        CityBuilder builder = new CityBuilder();
        for (TableField<?, ?> field : fields) {
            switch (field.getName()) {
                case "id":
                    builder.withId(record.get(asLongField(field)));
                    break;
                case "name":
                    builder.withName(record.get(asStringField(field)));
                    break;
                case "state_id":
                    builder.withState(computeIfMissingFromCache(record, State.class, record.get(asLongField(field))));
                    break;
                default:
                    throw new RuntimeException("Unknown Field " + field);
            }
        }
        City result = builder.build();
        putInCache(City.class, result);
        return result;
    }

    private Address mapAddress(Record record) {
        List<TableField<?, ?>> fields = getFields(record, "address");
        AddressBuilder builder = new AddressBuilder();
        for (TableField<?, ?> field : fields) {
            switch (field.getName()) {
                case "id":
                    builder.withId(record.get(asLongField(field)));
                    break;
                case "address":
                    builder.withAddress(record.get(asStringField(field)));
                    break;
                case "address2":
                    builder.withAddress2(record.get(asStringField(field)));
                    break;
                case "zipcode":
                    builder.withZipCode(record.get(asStringField(field)));
                    break;
                case "city_id":
                    builder.withCity(computeIfMissingFromCache(record, City.class, record.get(asLongField(field))));
                    break;
                default:
                    throw new RuntimeException("Unknown Field " + field);
            }
        }
        Address result = builder.build();
        putInCache(Address.class, result);
        return result;
    }

    private Book mapBook(Record record) {

        List<TableField<?, ?>> fields = getFields(record, "book");
        BookBuilder builder = new BookBuilder();
        for (TableField<?, ?> field : fields) {
            switch (field.getName()) {
                case "id":
                    builder.withId(record.get(asLongField(field)));
                    break;
                case "isbn13":
                    builder.withIsbn13(record.get(asStringField(field)));
                    break;
                case "title":
                    builder.withTitle(record.get(asStringField(field)));
                    break;
                case "purchase_price":
                    builder.withPurchasePrice(record.get(asDoubleField(field)));
                    break;
                case "retail_price":
                    builder.withRetailPrice(record.get(asDoubleField(field)));
                    break;

                case "author_id":
                    builder.withAuthor(computeIfMissingFromCache(record, Author.class, record.get(asLongField(field))));
                    break;
                case "genre_id":
                case "language_id":
                case "publisher_id":
                    // Ignored for the moment
                    break;
                default:
                    throw new RuntimeException("Unknown Field " + field);
            }
        }
        Book result = builder.build();
        putInCache(Book.class, result);
        return result;
    }

    private Author mapAuthor(Record record) {

        List<TableField<?, ?>> fields = getFields(record, "author");
        AuthorBuilder builder = new AuthorBuilder();
        for (TableField<?, ?> field : fields) {
            switch (field.getName()) {
                case "id":
                    builder.withId(record.get(asLongField(field)));
                    break;
                case "name":
                    builder.withName(record.get(asStringField(field)));
                    break;

                case "address_id":
                    Long addressId = record.get(asLongField(field));
                    // Not every JOOQ query retrieves address from author.
                    //So only if we have Address fields, map it.
                    if (!getFields(record, "address").isEmpty()) {
                        builder.withAddress(computeIfMissingFromCache(record, Address.class, addressId));
                    }
                    break;
                default:
                    throw new RuntimeException("Unknown Field " + field);
            }
        }
        Author result = builder.build();
        putInCache(Author.class, result);
        return result;
    }

    private Customer mapCustomer(Record record) {

        List<TableField<?, ?>> fields = getFields(record, "customer");
        CustomerBuilder builder = new CustomerBuilder();
        for (TableField<?, ?> field : fields) {
            switch (field.getName()) {
                case "id":
                    builder.withId(record.get(asLongField(field)));
                    break;
                case "name":
                    builder.withName(record.get(asStringField(field)));
                    break;

                case "address_id":
                    builder.withAddress(computeIfMissingFromCache(record, Address.class, record.get(asLongField(field))));
                    break;
                default:
                    throw new RuntimeException("Unknown Field " + field);
            }
        }
        Customer result = builder.build();
        putInCache(Customer.class, result);
        return result;
    }

    private void putInCache(Class<?> clazz, Object result) {
        List<WeakReference> references = cache.computeIfAbsent(clazz, c -> new ArrayList<>());
        references.add(new WeakReference<>(result));
    }

    private Field<String> asStringField(TableField<?, ?> field) {
        return (Field<String>) field;
    }

    private Field<Double> asDoubleField(TableField<?, ?> field) {
        return (Field<Double>) field;
    }

    private Field<Long> asLongField(TableField<?, ?> field) {
        return (Field<Long>) field;
    }

    private List<TableField<?, ?>> getFields(Record record, String tableName) {
        List<TableField<?, ?>> result = new ArrayList<>();
        for (int i = 0; i < record.size(); i++) {
            if (record.field(i) instanceof TableField) {
                TableField<?, ?> field = (TableField<?, ?>) record.field(i);
                if (tableName.equals(field.getTable().getName())) {
                    result.add(field);
                }
            }
        }
        return result;
    }

    private <T> T computeIfMissingFromCache(Record record, Class<T> clazz, Long id) {
        List<WeakReference> references = cache.computeIfAbsent(clazz, c -> new ArrayList<>());
        return (T) references.stream().map(Reference::get)
                .filter(Objects::nonNull)
                .filter(obj -> id.equals(((HasId) obj).getId()))
                .findAny()
                .orElse(map(record, clazz));

    }

    public static ResultMapper getInstance() {
        return INSTANCE;
    }
}
