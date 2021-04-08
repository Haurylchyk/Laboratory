package com.epam.esm;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.impl.GiftCertificateDAOImpl;
import com.epam.esm.entity.GiftCertificate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class GiftCertificateDAOImplTest {

    private static final Integer TEST_ID = 1;
    private static final Integer INVALID_ID = 100;
    private static final String TEST_NAME = "Test gift certificate name";
    private static final String TEST_DESCRIPTION = "Test gift certificate description";
    private static final Integer TEST_PRICE = 300;
    private static final Integer TEST_DURATION = 30;
    private static final String NEW_TEST_NAME = "New test gift certificate name";
    private static final String NEW_TEST_DESCRIPTION = "New test gift certificate description";
    private static final Integer NEW_TEST_PRICE = 500;
    private static final Integer NEW_TEST_DURATION = 50;
    private static final String TEST_TAG_NAME_1 = "Rest";
    private static final String TEST_TAG_NAME_2 = "Spa";
    private static final String TEST_TAG_NAME_3 = "Activity";


    private GiftCertificate giftCertificate;
    private EmbeddedDatabase embeddedDatabase;
    private GiftCertificateDAO giftCertificateDAO;

    @BeforeEach
    public void setUp() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql")
                .addScript("classpath:data.sql")
                .build();

        giftCertificateDAO = new GiftCertificateDAOImpl(embeddedDatabase);

        giftCertificate = new GiftCertificate();
        giftCertificate.setName(TEST_NAME);
        giftCertificate.setDescription(TEST_DESCRIPTION);
        giftCertificate.setPrice(TEST_PRICE);
        giftCertificate.setDuration(TEST_DURATION);
    }

    @AfterEach
    public void tearDown() {
        embeddedDatabase.shutdown();
    }

    @Test
    public void createGiftCertificate() {
        GiftCertificate savedGift = giftCertificateDAO.createGiftCertificate(giftCertificate);

        assertNotNull(savedGift);
        assertEquals(TEST_NAME, savedGift.getName());
        assertEquals(TEST_DESCRIPTION, savedGift.getDescription());
        assertEquals(TEST_PRICE, savedGift.getPrice());
        assertEquals(TEST_DURATION, savedGift.getDuration());
    }

    @Test
    public void deleteGiftCertificate() {
        giftCertificateDAO.deleteGiftCertificate(TEST_ID);

        Optional<GiftCertificate> giftCertificate = giftCertificateDAO.getGiftCertificateById(TEST_ID);
        assertFalse(giftCertificate.isPresent());

    }

    @Test
    public void updateGiftCertificate() {
        GiftCertificate gift = giftCertificateDAO.getGiftCertificateById(TEST_ID).get();

        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(TEST_ID);
        giftCertificate.setName(NEW_TEST_NAME);
        giftCertificate.setDescription(NEW_TEST_DESCRIPTION);
        giftCertificate.setPrice(NEW_TEST_PRICE);
        giftCertificate.setDuration(NEW_TEST_DURATION);

        giftCertificate.setLastUpdateDate(LocalDateTime.now());

//        GiftCertificate updatedGift = giftCertificateDAO.updateGiftCertificate(giftCertificate, TEST_ID);

//        assertEquals(NEW_TEST_PRICE, updatedGift.getPrice());
//        assertNotEquals(gift.getLastUpdateDate(), updatedGift.getLastUpdateDate());
    }

    @Test
    public void getCertificateById() {
        Optional<GiftCertificate> existGiftCertificate = giftCertificateDAO.getGiftCertificateById(TEST_ID);
        assertTrue(existGiftCertificate.isPresent());

        Optional<GiftCertificate> notExistGiftCertificate = giftCertificateDAO.getGiftCertificateById(INVALID_ID);
        assertFalse(notExistGiftCertificate.isPresent());
    }

    @Test
    public void getCertificates() {
        final int EXIST_GIFT_CERTIFICATE_NUMBER = 3;
        final List<GiftCertificate> giftCertificateList = giftCertificateDAO.getAllGiftCertificates();

        assertNotNull(giftCertificateList);
        assertEquals(EXIST_GIFT_CERTIFICATE_NUMBER, giftCertificateList.size());
    }

    @Test
    public void getCertificatesByTagName() {
        final int GIFT_CERTIFICATES_WITH_TAG_1 = 2;
        final int GIFT_CERTIFICATES_WITH_TAG_2 = 1;
        final int GIFT_CERTIFICATES_WITH_TAG_3 = 2;

        List<GiftCertificate> firstGiftCertificateList = giftCertificateDAO.getGiftCertificatesByTagName(TEST_TAG_NAME_1);
        List<GiftCertificate> secondGiftCertificateList = giftCertificateDAO.getGiftCertificatesByTagName(TEST_TAG_NAME_2);
        List<GiftCertificate> thirdGiftCertificateList = giftCertificateDAO.getGiftCertificatesByTagName(TEST_TAG_NAME_3);

        assertEquals(GIFT_CERTIFICATES_WITH_TAG_1, firstGiftCertificateList.size());
        assertEquals(GIFT_CERTIFICATES_WITH_TAG_2, secondGiftCertificateList.size());
        assertEquals(GIFT_CERTIFICATES_WITH_TAG_3, thirdGiftCertificateList.size());
    }
}
