
package be.rubus.microstream.performance.microstream.database.model;

import be.rubus.microstream.performance.microstream.database.model.NamedWithCode;

/**
 * Country entity which holds a name and an ISO 3166 2-letter country code.
 * <p>
 * This type is immutable and therefor inherently thread safe.
 */
public class Country extends NamedWithCode {
    public Country(Long id, String name, String code) {
        super(id, name, code);
    }

}
