package com.aykacltd.stream;

/**
 * Represents a physical address associated with a {@link Person}.
 *
 * <p>This immutable record captures city, country, postal code,
 * and the type of address (HOME, WORK, or SECONDARY). It is used
 * throughout the Stream API exercises for geographic grouping,
 * filtering, and multi-level aggregation examples.</p>
 *
 * @param street   the street line of the address
 * @param city     the city name
 * @param country  the country name
 * @param postCode the postal/zip code
 * @param type     the classification of this address
 */
public record Address(
    String street,
    String city,
    String country,
    String postCode,
    AddressType type
) {

    /**
     * Classifies the purpose of an address.
     */
    public enum AddressType {
        HOME, WORK, SECONDARY
    }
}
