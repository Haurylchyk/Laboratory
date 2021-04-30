package com.epam.esm;

import com.epam.esm.config.RepositoryConfigTest;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.impl.TagDAOImpl;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TagDAOImpl.class)
@ContextConfiguration(classes = RepositoryConfigTest.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TagDAOImplTest {

    @Autowired
    private TagDAO tagDAO;

    private static final String TEST_TAG_NAME = "New";
    private static final String TAG_NAME = "Rest";
    private static final String INVALID_NAME = "Go";

    private static final int TEST_ID = 1;
    private static final int INVALID_ID = 100;

    @Test
    public void createShouldReturnCreatedTag() {
        Tag tag = new Tag(TEST_TAG_NAME);
        Tag newTag = tagDAO.save(tag);
        assertNotNull(newTag);
        assertEquals(TEST_TAG_NAME, newTag.getName());

    }

    @Test
    public void findShouldSuccessfully() {
        Optional<Tag> existTag = tagDAO.find(TEST_ID);
        assertTrue(existTag.isPresent());
    }

    @Test
    public void findShouldReturnNull() {
        Optional<Tag> notExistTag = tagDAO.find(INVALID_ID);
        assertFalse(notExistTag.isPresent());
    }

    @Test
    public void deleteShouldSuccessfully() {
        tagDAO.delete(TEST_ID);
        Optional<Tag> tag = tagDAO.find(TEST_ID);
        assertFalse(tag.isPresent());
    }

    @Test
    public void findByNameShouldSuccessfully() {
        Optional<Tag> existTag = tagDAO.findByName(TAG_NAME);
        assertTrue(existTag.isPresent());
    }

    @Test
    public void findByNameShouldReturnNull() {
        Optional<Tag> notExistTag = tagDAO.findByName(INVALID_NAME);
        assertFalse(notExistTag.isPresent());
    }

    @Test
    public void findByGiftCertificateIdShouldSuccessfully() {
        final int TAG_NUMBER = 3;
        final List<Tag> tagList = tagDAO.findByGiftCertificateId(TEST_ID);

        assertNotNull(tagList);
        assertEquals(TAG_NUMBER, tagList.size());
    }

    @Test
    public void findByGiftCertificateIdShouldReturnEmpty() {
        final List<Tag> tagList = tagDAO.findByGiftCertificateId(INVALID_ID);
        assertTrue(tagList.isEmpty());
    }

    @Test
    public void findTotalNumberTagsShouldSuccessfully() {
        final int TAG_NUMBER = 5;
        final Long number = tagDAO.findTotalNumberTags();
        assertEquals(TAG_NUMBER, number);
    }
}
