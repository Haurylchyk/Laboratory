package com.epam.esm;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.mapper.TagDTOMapper;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exception.impl.ExistingTagException;
import com.epam.esm.exception.impl.TagInvalidDataException;
import com.epam.esm.exception.impl.TagNotFoundException;
import com.epam.esm.impl.TagServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    private static final Integer TEST_FIRST_ID = 3;
    private final Integer TEST_SECOND_ID = 5;
    private final String TEST_FIRST_NAME = "New";
    private final String TEST_SECOND_NAME = "Test Second Tag";
    private final String MOST_USED_TAG_NAME = "Activity";

    private final Integer USER_ID = 4;
    private final Long TOTAL_NUMBER_TAGS = 5L;
    private final Long TOTAL_NUMBER_PAGES = 2L;

    @InjectMocks
    private TagServiceImpl tagService;

    @Mock
    private TagDAO tagDAO;
    @Mock
    private UserDAO userDAO;

    private Tag tagFirst;
    private Tag tagSecond;
    private Tag mostUsedTag;
    private TagDTO tagDTO;
    private TagDTO emptyTagDTO;
    private List<Tag> tagList;
    private List<Tag> emptyTagList;

    @BeforeEach
    public void setUp() {
        tagFirst = new Tag();
        tagFirst.setId(TEST_FIRST_ID);
        tagFirst.setName(TEST_FIRST_NAME);
        tagSecond = new Tag();
        tagSecond.setId(TEST_SECOND_ID);
        tagSecond.setName(TEST_SECOND_NAME);

        emptyTagList = new ArrayList<>();
        tagList = new ArrayList<>();
        tagList.add(tagFirst);
        tagList.add(tagSecond);

        emptyTagDTO = new TagDTO();
        tagDTO = new TagDTO();
        tagDTO.setName(TEST_FIRST_NAME);

        mostUsedTag = new Tag(MOST_USED_TAG_NAME);
    }

    @Test
    public void createShouldReturnCreatedTag() {
        given(tagDAO.save(any())).willReturn(tagFirst);
        TagDTO createdTagDTO = tagService.create(tagDTO);
        assertEquals(TEST_FIRST_NAME, createdTagDTO.getName());
    }

    @Test
    public void createShouldInvalidDataException() {
        assertThrows(TagInvalidDataException.class,
                () -> tagService.create(emptyTagDTO));
    }

    @Test
    public void createShouldExistingTagException() {
        given(tagDAO.findByName(TEST_FIRST_NAME)).willReturn(Optional.of(tagFirst));
        assertThrows(ExistingTagException.class,
                () -> tagService.create(tagDTO));
    }

    @Test
    public void findShouldSuccessfully() {
        given(tagDAO.find(TEST_FIRST_ID)).willReturn(Optional.of(tagFirst));
        TagDTO foundTagDTO = tagService.findById(TEST_FIRST_ID);
        TagDTO testedDTO = TagDTOMapper.convertToDTO(tagFirst);
        assertEquals(testedDTO, foundTagDTO);
    }

    @Test
    public void findShouldTagNotFoundException() {
        given(tagDAO.find(TEST_FIRST_ID)).willReturn(Optional.empty());
        assertThrows(TagNotFoundException.class, () -> tagService.findById(TEST_FIRST_ID));
    }

    @Test
    public void deleteShouldSuccessfully() {
        given(tagDAO.find(TEST_FIRST_ID)).willReturn(Optional.of(tagFirst));
        tagService.delete(TEST_FIRST_ID);
        verify(tagDAO, times(1)).delete(TEST_FIRST_ID);
    }

    @Test
    public void deleteShouldTagNotFoundException() {
        given(tagDAO.find(TEST_FIRST_ID)).willReturn(Optional.empty());
        assertThrows(TagNotFoundException.class,
                () -> tagService.delete(TEST_FIRST_ID));
    }

    @Test
    public void findMostWidelyUsedOfTopOrderUserShouldSuccessfully() {
        User userWithTopOrders = new User();
        userWithTopOrders.setId(USER_ID);
        given(userDAO.findUserWithTopOrders()).willReturn(userWithTopOrders);
        given(tagDAO.findMostWidelyUsedByUserId(USER_ID)).willReturn(mostUsedTag);

        TagDTO foundTagDTO = tagService.findMostWidelyUsedOfTopOrderUser();
        TagDTO testedTagDTO = TagDTOMapper.convertToDTO(mostUsedTag);

        assertEquals(testedTagDTO, foundTagDTO);
    }

    @Test
    public void findNumberPagesForAllTagsShouldSuccessfully() {
        given(tagDAO.findTotalNumberTags()).willReturn(TOTAL_NUMBER_TAGS);
        Long foundNumber = tagService.findNumberPagesForAllTags();

        assertEquals(TOTAL_NUMBER_PAGES, foundNumber);
    }
}