package com.epam.esm;

import com.epam.esm.dao.TagRepository;
import com.epam.esm.dao.UserRepository;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exception.impl.EntityNotFoundException;
import com.epam.esm.exception.impl.ExistingTagException;
import com.epam.esm.exception.impl.InvalidDataException;
import com.epam.esm.exception.impl.NotExistingPageException;
import com.epam.esm.impl.TagServiceImpl;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.dto.mapper.impl.TagDTOMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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

    private static final Integer SIZE = 1;
    private static final Integer PAGE_NUMBER_INVALID = 100;

    @InjectMocks
    private TagServiceImpl tagService;

    @Mock
    private TagRepository tagRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TagDTOMapper tagDTOMapper;

    private Tag tagFirst;
    private Tag tagSecond;
    private Tag mostUsedTag;
    private TagDTO tagDTOFirst;
    private TagDTO tagDTOSecond;
    private TagDTO emptyTagDTO;
    private List<Tag> tagList;
    private List<Tag> mostUserTagList;
    private List<TagDTO> tagListDTO;
    private List<Tag> emptyTagList;
    private List<User> userList;


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

        mostUserTagList = new ArrayList<>();
        mostUsedTag = new Tag(MOST_USED_TAG_NAME);
        mostUserTagList.add(mostUsedTag);

        tagListDTO = new ArrayList<>();
        tagListDTO.add(tagDTOFirst);
        tagListDTO.add(tagDTOSecond);

        emptyTagList = new ArrayList<>();
        userList = new ArrayList<>();
    }

    @Test
    public void createShouldReturnCreatedTag() {
        given(tagRepository.save(any())).willReturn(tagFirst);
        given(tagDTOMapper.convertToDTO(tagFirst)).willReturn(tagDTOFirst);
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
        given(tagRepository.findByName(TEST_FIRST_NAME)).willReturn(Optional.of(tagFirst));
        assertThrows(ExistingTagException.class,
                () -> tagService.create(tagDTOFirst));
    }

    @Test
    public void findShouldSuccessfully() {
        given(tagRepository.findById(TEST_FIRST_ID)).willReturn(Optional.of(tagFirst));
        TagDTO foundTagDTO = tagService.findById(TEST_FIRST_ID);
        TagDTO testedDTO = tagDTOMapper.convertToDTO(tagFirst);
        assertEquals(testedDTO, foundTagDTO);
    }

    @Test
    public void findShouldNotFoundException() {
        given(tagRepository.findById(TEST_FIRST_ID)).willReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> tagService.findById(TEST_FIRST_ID));
    }

    @Test
    public void deleteShouldSuccessfully() {
        given(tagRepository.findById(TEST_FIRST_ID)).willReturn(Optional.of(tagFirst));
        tagService.delete(TEST_FIRST_ID);
        verify(tagRepository, times(1)).deleteById(TEST_FIRST_ID);
    }

    @Test
    public void deleteShouldNotFoundException() {
        given(tagRepository.findById(TEST_FIRST_ID)).willReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,
                () -> tagService.delete(TEST_FIRST_ID));
    }

    @Test
    public void findMostWidelyUsedOfTopOrderUserShouldSuccessfully() {
        User userWithTopOrders = new User();
        userWithTopOrders.setId(USER_ID);
        userList.add(userWithTopOrders);
        Page<User> page = new PageImpl<>(userList, Pageable.unpaged(), 1);
        given(userRepository.findUserWithTopOrders(PageRequest.of(0, 1))).willReturn(page);
        given(tagRepository.findMostWidelyUsedByUserId(USER_ID, PageRequest.of(0, 1))).willReturn(mostUserTagList);

        TagDTO foundTagDTO = tagService.findMostWidelyUsedOfTopOrderUser();
        TagDTO testedTagDTO = tagDTOMapper.convertToDTO(mostUsedTag);

        assertEquals(testedTagDTO, foundTagDTO);
    }

    @Test
    public void findAllShouldSuccessfully() {
        Page<Tag> page = new PageImpl<>(tagList, Pageable.unpaged(), 2);
        given(tagRepository.findAll(Pageable.unpaged())).willReturn(page);
        Page<TagDTO> pageTagDTO = tagService.findAll(Pageable.unpaged());
        List<TagDTO> foundTagDTOList = pageTagDTO.toList();
        List<TagDTO> testedDTO = tagDTOMapper.convertToDTO(tagList);
        assertEquals(testedDTO, foundTagDTOList);
    }

    @Test
    public void findAllShouldNotExistingPageException() {
        Page<Tag> page = new PageImpl<>(emptyTagList, PageRequest.of(PAGE_NUMBER_INVALID, SIZE), 0);
        given(tagRepository.findAll(PageRequest.of(PAGE_NUMBER_INVALID, SIZE))).willReturn(page);
        assertThrows(NotExistingPageException.class, () -> tagService.findAll(PageRequest.of(PAGE_NUMBER_INVALID, SIZE)));
    }
}