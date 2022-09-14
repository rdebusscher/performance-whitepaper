
package be.rubus.microstream.performance.generator.data;

public class DataMetrics {
    private final int bookCount;
    private final int countryCount;
    private final int shopCount;

    public DataMetrics(int bookCount, int countryCount, int shopCount) {
        this.bookCount = bookCount;
        this.countryCount = countryCount;
        this.shopCount = shopCount;
    }

    public int bookCount() {
        return bookCount;
    }

    public int countryCount() {
        return countryCount;
    }

    public int shopCount() {
        return shopCount;
    }

    @Override
    public String toString() {
        return bookCount + " books, "
                + shopCount + " shops in "
                + countryCount + " countries";
    }

}
