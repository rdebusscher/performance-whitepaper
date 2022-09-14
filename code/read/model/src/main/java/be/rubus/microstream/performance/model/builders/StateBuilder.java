package be.rubus.microstream.performance.model.builders;

import be.rubus.microstream.performance.model.Country;
import be.rubus.microstream.performance.model.State;

public class StateBuilder {

    private Long id;

    private String name;
    private Country country;


    public StateBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public StateBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public StateBuilder withCountry(Country country) {
        this.country = country;
        return this;
    }

    public State build() {
        return new State(id, name, country);
    }
}