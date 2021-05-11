package com.epam.esm;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.mapper.TagDTOMapper;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exception.impl.EntityNotFoundException;
import com.epam.esm.exception.impl.ExistingTagException;
import com.epam.esm.exception.impl.InvalidDataException;
import com.epam.esm.exception.impl.NotExistingPageException;
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
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
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
    private final Integer TOTAL_NUMBER_TAGS = 5;
    private final Integer TOTAL_NUMBER_PAGES = 2;

    private static final Integer PAGE_NUMBER = 1;
    private static final Integer SIZE = 1;
    private static final Integer PAGE_NUMBER_INVALID = 100;

    @InjectMocks
    private TagServiceImpl tagService;

    @Mock
    private TagDAO tagDAO;
    @Mock
    private UserDAO userDAO;

    private Tag tagFirst;
    private Tag tagSecond;
    private Tag mostUsedTag;
    private TagDTO tagDTOFirst;
    private TagDTO tagDTOSecond;
    private TagDTO emptyTagDTO;
    private List<Tag> tagList;
    private List<TagDTO> tagListDTO;
    private List<Tag> emptyTagList;


    @BeforeEach
    public void setUp() {
        tagFirst = new Tag();
        tagFirst.setId(TEST_FIRST_ID);
        tagFirst.setName(TEST_FIRST_NAME);
        tagSecond = new Tag();
        tagSecond.setId(TEST_SECOND_ID);
        tagSecond.setName(TEST_SECOND_NAME);

        tagList = new ArrayList<>();
        tagList.add(tagFirst);
        tagList.add(tagSecond);

        emptyTagDTO = new TagDTO();

        tagDTOFirst = new TagDTO();
        tagDTOFirst.setId(TEST_FIRST_ID);
        tagDTOFirst.setName(TEST_FIRST_NAME);

        tagDTOSecond = new TagDTO();
        tagDTOSecond.setId(TEST_SECOND_ID);
        tagDTOSecond.setName(TEST_SECOND_NAME);

        mostUsedTag = new Tag(MOST_USED_TAG_NAME);

        tagListDTO = new ArrayList<>();
        tagListDTO.add(tagDTOFirst);
        tagListDTO.add(tagDTOSecond);

        emptyTagList = new ArrayList<>();
    }

    @Test
    public void createShouldReturnCreatedTag() {
        given(tagDAO.save(any())).willReturn(tagFirst);
        TagDTO createdTagDTO = tagService.create(tagDTOFirst);
        assertEquals(TEST_FIRST_NAME, createdTagDTO.getName());
    }

    @Test
    public void createShouldInvalidDataException() {
        assertThrows(InvalidDataException.class,
                () -> tagService.create(emptyTagDTO));
    }

    @Test
    public void createShouldExistingTagException() {
        given(tagDAO.findByName(TEST_FIRST_NAME)).willReturn(Optional.of(tagFirst));
        assertThrows(ExistingTagException.class,
                () -> tagService.create(tagDTOFirst));
    }

    @Test
    public void findShouldSuccessfully() {
        given(tagDAO.find(TEST_FIRST_ID)).willReturn(Optional.of(tagFirst));
        TagDTO foundTagDTO = tagService.findById(TEST_FIRST_ID);
        TagDTO testedDTO = TagDTOMapper.convertToDTO(tagFirst);
        assertEquals(testedDTO, foundTagDTO);
    }

    @Test
    public void findShouldNotFoundException() {
        given(tagDAO.find(TEST_FIRST_ID)).willReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> tagService.findById(TEST_FIRST_ID));
    }

    @Test
    public void deleteShouldSuccessfully() {
        given(tagDAO.find(TEST_FIRST_ID)).willReturn(Optional.of(tagFirst));
        tagService.delete(TEST_FIRST_ID);
        verify(tagDAO, times(1)).delete(TEST_FIRST_ID);
    }

    @Test
    public void deleteShouldNotFoundException() {
        given(tagDAO.find(TEST_FIRST_ID)).willReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,
                () -> tagService.delete(TEST_FIRST_ID));
    }

    @Test
    public void findMostWidelyUsedOfTopOrderUserShouldSuccessfully() {
        User userWithTopOrders = new User();
        userWithTopOrders.setId(USER_ID);
        given(userDAO.findUserWithTopOrders()).willReturn(Optional.of(userWithTopOrders));
        given(tagDAO.findMostWidelyUsedByUserId(USER_ID)).willReturn(mostUsedTag);

        TagDTO foundTagDTO = tagService.findMostWidelyUsedOfTopOrderUser();
        TagDTO testedTagDTO = TagDTOMapper.convertToDTO(mostUsedTag);

        assertEquals(testedTagDTO, foundTagDTO);
    }

    @Test
    public void findAllShouldSuccessfully() {
        given(tagDAO.findAll(PAGE_NUMBER, SIZE)).willReturn(tagList);
        List<TagDTO> foundTagDTOList = tagService.findAll(PAGE_NUMBER, SIZE);
        assertIterableEquals(tagListDTO, foundTagDTOList);
    }

    @Test
    public void findAllShouldNotExistingPageException() {
        given(tagDAO.findAll(PAGE_NUMBER_INVALID, SIZE)).willReturn(emptyTagList);
        assertThrows(NotExistingPageException.class, () -> tagService.findAll(PAGE_NUMBER_INVALID, SIZE));
    }
}