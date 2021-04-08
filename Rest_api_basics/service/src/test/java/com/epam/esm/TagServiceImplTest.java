package com.epam.esm;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.mapper.TagDTOMapper;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.impl.TagServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    private static final int TEST_ID = 1;
    private static final String TEST_NAME = "Tag";

    @InjectMocks
    private TagServiceImpl tagService;

    @Mock
    private TagDAO tagDAO;

    private Tag tag;
    private TagDTO tagDTO;
    private TagDTO emptyTagDTO;

    private List<Tag> tagList;

    @BeforeEach
    public void setUp() {
        tag = new Tag();
        tag.setId(TEST_ID);
        tag.setName(TEST_NAME);

        tagList = new ArrayList<>();
        tagList.add(tag);

        tag = new Tag();
        tag.setId(TEST_ID);
        tag.setName(TEST_NAME);

        emptyTagDTO = new TagDTO();

        tagDTO = new TagDTO();
        tagDTO.setName(TEST_NAME);

        tagService = new TagServiceImpl(tagDAO);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void deleteTag() {
        given(tagDAO.getTagById(TEST_ID)).willReturn(Optional.of(tag));

        tagService.deleteTag(TEST_ID);

        verify(tagDAO, times(1)).deleteTag(TEST_ID);
    }

    @Test
    public void deleteCertificateShouldException() {
        given(tagDAO.getTagById(TEST_ID)).willReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> tagService.deleteTag(TEST_ID));
    }

    @Test
    public void getTagByID() {
        given(tagDAO.getTagById(TEST_ID)).willReturn(Optional.of(tag));
        TagDTO receivedTagDTO = tagService.getTagById(TEST_ID);

        TagDTO testedDTO = TagDTOMapper.convertToDTO(tag);
        assertEquals(testedDTO, receivedTagDTO);
    }


    @Test
    public void getTagByIDShouldException() {
        given(tagDAO.getTagById(TEST_ID)).willReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> tagService.getTagById(TEST_ID));
    }

    @Test
    public void createTag() {
        given(tagDAO.createTag(any())).willReturn(tag);

        TagDTO receivedDTO = tagService.createTag(tagDTO);

        assertEquals(TEST_NAME, receivedDTO.getName());
    }

    @Test
    public void createTagShouldValidationException() {
        assertThrows(ServiceException.class,
                () -> tagService.createTag(emptyTagDTO));
    }

    @Test
    public void createTagShouldAlreadyExistsException() {
        given(tagDAO.getTagByName(TEST_NAME)).willReturn(Optional.of(tag));

        assertThrows(ServiceException.class,
                () -> tagService.createTag(tagDTO));
    }

    @Test
    public void getCertificates() {
        given(tagDAO.getAllTags()).willReturn(tagList);

        List<TagDTO> receivedDTOList = tagService.getAllTags();
        List<TagDTO> testDTOList = TagDTOMapper.convertToDTO(tagList);

        assertIterableEquals(testDTOList, receivedDTOList);
    }

}