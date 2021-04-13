package com.epam.esm;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.impl.GiftCertificateDAOImpl;
import com.epam.esm.dao.query.GiftCertificateParam;
import com.epam.esm.entity.GiftCertificate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class GiftCertificateDAOImplTest {

    private static final String TEST_NAME = "Test gift certificate name";
    private static final String TEST_DESCRIPTION = "Test gift certificate description";
    private static final Integer TEST_PRICE = 500;
    private static final Integer TEST_DURATION = 50;

    private static final String NAME = "Gym";
    private static final String DESCRIPTION = "Many different locations and much more";
    private static final String TAG_NAME = "Spa";

    private static final Integer TEST_ID = 1;
    private static final Integer INVALID_ID = 100;

    private static final String UPDATED_NAME = "Updated gift certificate name";
    private static final Integer UPDATED_DURATION = 100;

    private EmbeddedDatabase embeddedDatabase;
    private GiftCertificateDAO giftCertificateDAO;

    @BeforeEach
    public void setUp() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql")
                .addScript("classpath:fill_tables.sql")
                .build();
        giftCertificateDAO = new GiftCertificateDAOImpl(embeddedDatabase);
    }

    @AfterEach
    public void tearDown() {
        embeddedDatabase.shutdown();
    }

    @Test
    public void createGiftCertificate() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName(TEST_NAME);
        giftCertificate.setDescription(TEST_DESCRIPTION);
        giftCertificate.setPrice(TEST_PRICE);
        giftCertificate.setDuration(TEST_DURATION);

        GiftCertificate newCertificate = giftCertificateDAO.create(giftCertificate);

        assertNotNull(newCertificate);
        assertEquals(TEST_NAME, newCertificate.getName());
        assertEquals(TEST_DESCRIPTION, newCertificate.getDescription());
        assertEquals(TEST_PRICE, newCertificate.getPrice());
        assertEquals(TEST_DURATION, newCertificate.getDuration());
    }

    @Test
    public void deleteGiftCertificate() {
        giftCertificateDAO.delete(TEST_ID);
        Optional<GiftCertificate> giftCertificate = giftCertificateDAO.find(TEST_ID);

        assertFalse(giftCertificate.isPresent());
    }

    @Test
    public void updateGiftCertificate() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(TEST_ID);
        giftCertificate.setName(UPDATED_NAME);
        giftCertificate.setDuration(UPDATED_DURATION);

        GiftCertificate updatedCertificate = giftCertificateDAO.update(giftCertificate, TEST_ID);

        assertEquals(UPDATED_NAME, updatedCertificate.getName());
        assertEquals(UPDATED_DURATION, updatedCertificate.getDuration());
    }

    @Test
    public void getCertificateById() {
        Optional<GiftCertificate> existGiftCertificate = giftCertificateDAO.find(TEST_ID);
        assertTrue(existGiftCertificate.isPresent());

        Optional<GiftCertificate> notExistGiftCertificate = giftCertificateDAO.find(INVALID_ID);
        assertFalse(notExistGiftCertificate.isPresent());
    }

    @Test
    public void getAllCertificates() {
        final int EXIST_GС_NUMBER = 3;
        final List<GiftCertificate> giftCertificateList = giftCertificateDAO.findAll();

        assertNotNull(giftCertificateList);
        assertEquals(EXIST_GС_NUMBER, giftCertificateList.size());
    }

    @Test
    public void getCertificatesByTagName() {
        final int GC_NUMBER = 1;
        GiftCertificateParam compositeParameter = new GiftCertificateParam(
                TAG_NAME, null, null, null, null);

        List<GiftCertificate> giftCertificateList = giftCertificateDAO.findByParam(compositeParameter);

        assertEquals(GC_NUMBER, giftCertificateList.size());
    }

    @Test
    public void getCertificatesByName() {
        final int GC_NUMBER = 1;
        GiftCertificateParam compositeParameter = new GiftCertificateParam(
                null, NAME, null, null, null);

        List<GiftCertificate> giftCertificateList = giftCertificateDAO.findByParam(compositeParameter);

        assertEquals(GC_NUMBER, giftCertificateList.size());
    }

    @Test
    public void getCertificatesByDescription() {
        final int GC_NUMBER = 1;
        GiftCertificateParam compositeParameter = new GiftCertificateParam(
                null, null, DESCRIPTION, null, null);

        List<GiftCertificate> giftCertificateList = giftCertificateDAO.findByParam(compositeParameter);

        assertEquals(GC_NUMBER, giftCertificateList.size());
    }
}
