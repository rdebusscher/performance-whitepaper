package be.rubus.microstream.performance.generator.insert;

class ForeignIdItem<T> {

    final T item;
    final Long foreignId;

    ForeignIdItem(T item, Long foreignId) {
        this.item = item;
        this.foreignId = foreignId;
    }

}
