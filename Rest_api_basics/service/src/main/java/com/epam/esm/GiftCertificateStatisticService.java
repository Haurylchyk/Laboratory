package com.epam.esm;

/**
 * Interface describes the service for obtaining statistical data.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
public interface GiftCertificateStatisticService {
    /**
     * Accesses the corresponding DAO method
     * to get the price of the most expensive GiftCertificate.
     *
     * @return the value of the price.
     */
    Integer findTopPrice();

    /**
     * Accesses the corresponding DAO method to get the duration
     * of GiftCertificate with the longest duration.
     *
     * @return the value of the duration.
     */
    Integer findTopDuration();
}
