package be.rubus.microstream.performance.generator.builders;

import be.rubus.microstream.performance.microstream.database.model.Address;
import be.rubus.microstream.performance.microstream.database.model.Author;

public class AuthorBuilder {
    private Long id;
    private String name;
    private Address address;

    public AuthorBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public AuthorBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public AuthorBuilder withAddress(Address address) {
        this.address = address;
        return this;
    }

    public Author build() {
        return new Author(id, name, address);
    }
}