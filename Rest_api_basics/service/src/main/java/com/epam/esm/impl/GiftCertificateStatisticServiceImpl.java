package com.epam.esm.impl;

import com.epam.esm.GiftCertificateStatisticService;
import com.epam.esm.dao.GiftCertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GiftCertificateStatisticServiceImpl implements GiftCertificateStatisticService {
    /**
     * Object of the GiftCertificateDAO type.
     */
    private final GiftCertificateRepository giftCertificateRepository;

    /**
     * Constructor with parameter.
     *
     * @param giftCertificateRepository interface for working with
     *                                  the corresponding DAO methods..
     */
    @Autowired
    public GiftCertificateStatisticServiceImpl(GiftCertificateRepository giftCertificateRepository) {
        this.giftCertificateRepository = giftCertificateRepository;
    }

    /**
     * Accesses the corresponding DAO method
     * to get the price of the most expensive GiftCertificate.
     *
     * @return the value of the price.
     */
    @Override
    public Integer findTopPrice() {
        return giftCertificateRepository.findFirstByOrderByPriceDesc().getPrice();
    }

    /**
     * Accesses the corresponding DAO method to get the duration
     * of GiftCertificate with the longest duration.
     *
     * @return the value of the duration.
     */
    @Override
    public Integer findTopDuration() {
        return giftCertificateRepository.findFirstByOrderByDurationDesc().getDuration();
    }
}
