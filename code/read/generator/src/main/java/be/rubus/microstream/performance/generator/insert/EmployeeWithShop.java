package be.rubus.microstream.performance.generator.insert;


import be.rubus.microstream.performance.microstream.database.model.Shop;
import be.rubus.microstream.performance.model.Employee;

/**
 * Since we don't have a reference from the Employee to the Shop, this object
 */
public class EmployeeWithShop  {

    private final Employee employee;
    private final Shop shop;

    public EmployeeWithShop(Employee employee, Shop shop) {

        this.employee = employee;
        this.shop = shop;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Shop getShop() {
        return shop;
    }
}
