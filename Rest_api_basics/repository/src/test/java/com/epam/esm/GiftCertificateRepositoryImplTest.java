package com.epam.esm;

import com.epam.esm.config.RepositoryConfigTest;
import com.epam.esm.dao.GiftCertificateRepository;
import com.epam.esm.dao.query.filter.Filter;
import com.epam.esm.dao.query.filter.FilterType;
import com.epam.esm.dao.query.parameter.GiftCertificateParam;
import com.epam.esm.dao.query.specification.GiftCertificateSpecification;
import com.epam.esm.entity.GiftCertificate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = GiftCertificateRepository.class)
@ContextConfiguration(classes = RepositoryConfigTest.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class GiftCertificateRepositoryImplTest {

    private static final String TEST_NAME = "Test gift certificate name";
    private static final String TEST_DESCRIPTION = "Test gift certificate description";
    private static final Integer TEST_PRICE = 500;
    private static final Integer TEST_DURATION = 50;

    private static final String NAME = "Quest";
    private static final String DESCRIPTION = "Spa services and much more";
    private static final String TAG_NAME = "Spa";
    private static final Filter PRICE_FILTER = new Filter(FilterType.GT, 180);
    private static final Filter DURATION_FILTER = new Filter(FilterType.GT, 15);

    private static final List<String> TAG_NAME_LIST = new ArrayList<>();
    private static final List<Filter> PRICE_FILTER_LIST = new ArrayList<>();
    private static final List<Filter> DURATION_FILTER_LIST = new ArrayList<>();

    private static final Integer TEST_ID = 1;
    private static final Integer INVALID_ID = 100;

    @Autowired
    private GiftCertificateRepository giftCertificateRepository;

    @Test
    public void createShouldReturnCreatedGiftCertificate() {

        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName(TEST_NAME);
        giftCertificate.setDescription(TEST_DESCRIPTION);
        giftCertificate.setPrice(TEST_PRICE);
        giftCertificate.setDuration(TEST_DURATION);
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        giftCertificate.setCreateDate(LocalDateTime.now());

        GiftCertificate newCertificate = giftCertificateRepository.save(giftCertificate);

        assertNotNull(newCertificate);
        assertEquals(TEST_NAME, newCertificate.getName());
        assertEquals(TEST_DESCRIPTION, newCertificate.getDescription());
        assertEquals(TEST_PRICE, newCertificate.getPrice());
        assertEquals(TEST_DURATION, newCertificate.getDuration());
    }

    @Test
    public void findShouldReturnGiftCertificate() {
        Optional<GiftCertificate> existGiftCertificate = giftCertificateRepository.findById(TEST_ID);
        assertTrue(existGiftCertificate.isPresent());
    }

    @Test
    public void findShouldReturnNull() {
        Optional<GiftCertificate> notExistGiftCertificate = giftCertificateRepository.findById(INVALID_ID);
        assertFalse(notExistGiftCertificate.isPresent());
    }

    @Test
    public void deleteShouldSuccessfully() {
        giftCertificateRepository.deleteById(TEST_ID);
        Optional<GiftCertificate> giftCertificate = giftCertificateRepository.findById(TEST_ID);
        assertFalse(giftCertificate.isPresent());
    }

    @Test
    public void findAllShouldReturnAllGiftCertificates() {
        final int EXIST_GС_NUMBER = 4;
        final List<GiftCertificate> giftCertificateList = giftCertificateRepository.findAll();

        assertNotNull(giftCertificateList);
        assertEquals(EXIST_GС_NUMBER, giftCertificateList.size());
    }

    @Test
    public void findByNameShouldReturnGiftCertificateList() {
        final Integer GC_NUMBER = 1;
        GiftCertificateParam compositeParameter = new GiftCertificateParam(
                NAME, null, null, null, null, null, null);
        Page<GiftCertificate> giftCertificatePage = giftCertificateRepository.findAll(GiftCertificateSpecification
                .findByParam(compositeParameter), PageRequest.of(0, 20));

        assertEquals(GC_NUMBER, giftCertificatePage.toList().size());
    }

    @Test
    public void findByDescriptionShouldReturnGiftCertificateList() {
        final int GC_NUMBER = 1;
        GiftCertificateParam compositeParameter = new GiftCertificateParam(
                null, DESCRIPTION, null, null, null, null, null);
        Page<GiftCertificate> giftCertificatePage = giftCertificateRepository.findAll(GiftCertificateSpecification
                .findByParam(compositeParameter), PageRequest.of(0, 20));

        assertEquals(GC_NUMBER, giftCertificatePage.toList().size());
    }

    @Test
    public void findByPriceFilterShouldReturnGiftCertificateList() {
        final int GC_NUMBER = 2;
        PRICE_FILTER_LIST.add(PRICE_FILTER);
        GiftCertificateParam compositeParameter = new GiftCertificateParam(
                null, null, PRICE_FILTER_LIST, null, null, null, null);
        Page<GiftCertificate> giftCertificatePage = giftCertificateRepository.findAll(GiftCertificateSpecification
                .findByParam(compositeParameter), PageRequest.of(0, 20));

        assertEquals(GC_NUMBER, giftCertificatePage.toList().size());
    }

    @Test
    public void findByDurationFilterShouldReturnGiftCertificateList() {
        final int GC_NUMBER = 2;
        DURATION_FILTER_LIST.add(DURATION_FILTER);
        GiftCertificateParam compositeParameter = new GiftCertificateParam(
                null, null, null, DURATION_FILTER_LIST, null, null, null);
        Page<GiftCertificate> giftCertificatePage = giftCertificateRepository.findAll(GiftCertificateSpecification
                .findByParam(compositeParameter), PageRequest.of(0, 20));

        assertEquals(GC_NUMBER, giftCertificatePage.toList().size());
    }

    @Test
    public void findByTagNameShouldReturnGiftCertificateList() {
        final int GC_NUMBER = 1;
        TAG_NAME_LIST.add(TAG_NAME);
        GiftCertificateParam compositeParameter = new GiftCertificateParam(
                null, null, null, null, TAG_NAME_LIST, null, null);
        Page<GiftCertificate> giftCertificatePage = giftCertificateRepository.findAll(GiftCertificateSpecification
                .findByParam(compositeParameter), PageRequest.of(0, 20));

        assertEquals(GC_NUMBER, giftCertificatePage.toList().size());
    }
}
