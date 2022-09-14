package be.rubus.microstream.performance.jdbc.model.builder;

import be.rubus.microstream.performance.jdbc.model.Shop;
import be.rubus.microstream.performance.model.Address;
import be.rubus.microstream.performance.model.Employee;
import be.rubus.microstream.performance.model.Inventory;

import java.util.ArrayList;
import java.util.List;

public class ShopBuilder {
    private Long id;
    private String name;
    private Address address;
    private List<Employee> employees = new ArrayList<>();
    private Inventory inventory = new Inventory();

    public ShopBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public ShopBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ShopBuilder withAddress(Address address) {
        this.address = address;
        return this;
    }

    public ShopBuilder withEmployees(List<Employee> employees) {
        this.employees = employees;
        return this;
    }

    public ShopBuilder withInventory(Inventory inventory) {
        this.inventory = inventory;
        return this;
    }

    public Shop build() {
        return new Shop(id, name, address, employees, inventory);
    }
}