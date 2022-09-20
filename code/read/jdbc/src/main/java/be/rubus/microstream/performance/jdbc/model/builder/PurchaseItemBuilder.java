package be.rubus.microstream.performance.jdbc.model.builder;

import be.rubus.microstream.performance.jdbc.model.PurchaseItem;
import be.rubus.microstream.performance.model.Book;

public class PurchaseItemBuilder {
    private Long id;
    private Book book;
    private int amount;
    private Long purchaseId;

    public PurchaseItemBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public PurchaseItemBuilder withBook(Book book) {
        this.book = book;
        return this;
    }

    public PurchaseItemBuilder withAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public PurchaseItemBuilder withPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
        return this;
    }

    public PurchaseItem build() {
        return new PurchaseItem(id, book, amount, purchaseId);
    }
}