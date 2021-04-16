package esm;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.mapper.TagDTOMapper;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    private static final Integer TEST_FIRST_ID = 3;
    private final Integer TEST_SECOND_ID = 5;
    private final String TEST_FIRST_NAME = "Test First Tag";
    private final String TEST_SECOND_NAME = "Test Second Tag";


    @InjectMocks
    private TagServiceImpl tagService;

    @Mock
    private TagDAO tagDAO;

    private Tag tagFirst;
    private Tag tagSecond;
    private TagDTO tagDTO;
    private TagDTO emptyTagDTO;
    private List<Tag> tagList;
    private List<Tag> emptyTagList;

    @BeforeEach
    public void setUp() {
        tagService = new TagServiceImpl(tagDAO);

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
    }

    @Test
    public void createShouldReturnCreatedTag() {
        given(tagDAO.create(any())).willReturn(tagFirst);
        TagDTO createdTagDTO = tagService.create(tagDTO);
        assertEquals(TEST_FIRST_NAME, createdTagDTO.getName());
    }

    @Test
    public void createShouldInvalidDataException() {
        assertThrows(ServiceException.class,
                () -> tagService.create(emptyTagDTO));
    }

    @Test
    public void createShouldExistingTagException() {
        given(tagDAO.findByName(TEST_FIRST_NAME)).willReturn(Optional.of(tagFirst));
        assertThrows(ServiceException.class,
                () -> tagService.create(tagDTO));
    }

    @Test
    public void readShouldSuccessfully() {
        given(tagDAO.find(TEST_FIRST_ID)).willReturn(Optional.of(tagFirst));
        TagDTO readTagDTO = tagService.finById(TEST_FIRST_ID);
        TagDTO testedDTO = TagDTOMapper.convertToDTO(tagFirst);
        assertEquals(testedDTO, readTagDTO);
    }

    @Test
    public void readShouldException() {
        given(tagDAO.find(TEST_FIRST_ID)).willReturn(Optional.empty());
        assertThrows(ServiceException.class, () -> tagService.finById(TEST_FIRST_ID));
    }

    @Test
    public void deleteShouldSuccessfully() {
        given(tagDAO.find(TEST_FIRST_ID)).willReturn(Optional.of(tagFirst));
        tagService.delete(TEST_FIRST_ID);
        verify(tagDAO, times(1)).delete(TEST_FIRST_ID);
    }

    @Test
    public void deleteShouldException() {
        given(tagDAO.find(TEST_FIRST_ID)).willReturn(Optional.empty());
        assertThrows(ServiceException.class,
                () -> tagService.delete(TEST_FIRST_ID));
    }

    @Test
    public void readAllTagsShouldSuccessfully() {
        given(tagDAO.findAll()).willReturn(tagList);

        List<TagDTO> readTagDTOList = tagService.findAll();
        List<TagDTO> testTagDTOList = TagDTOMapper.convertToDTO(tagList);

        assertIterableEquals(testTagDTOList, readTagDTOList);
    }

    @Test
    public void readAllTagsShouldException() {
        given(tagDAO.findAll()).willReturn(emptyTagList);
        assertThrows(ServiceException.class,
                () -> tagService.findAll());
    }
}