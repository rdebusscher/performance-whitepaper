package be.rubus.microstream.performance.jdbc.model.builder;

import be.rubus.microstream.performance.jdbc.model.Purchase;
import be.rubus.microstream.performance.jdbc.model.PurchaseItem;
import be.rubus.microstream.performance.jdbc.model.Shop;
import be.rubus.microstream.performance.model.Customer;
import be.rubus.microstream.performance.model.Employee;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class PurchaseBuilder {
    private Long id;
    private Shop shop;
    private Employee employee;
    private Customer customer;
    private LocalDateTime timestamp;
    private List<PurchaseItem> items = Collections.emptyList();

    public PurchaseBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public PurchaseBuilder withShop(Shop shop) {
        this.shop = shop;
        return this;
    }

    public PurchaseBuilder withEmployee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public PurchaseBuilder withCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public PurchaseBuilder withTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public PurchaseBuilder withItems(List<PurchaseItem> items) {
        this.items = items;
        return this;
    }

    public Purchase build() {
        return new Purchase(id, shop, employee, customer, timestamp, items);
    }
}