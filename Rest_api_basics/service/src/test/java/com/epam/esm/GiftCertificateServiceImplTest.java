package com.epam.esm;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.query.parameter.GiftCertificateParam;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.GiftCertificateParamDTO;
import com.epam.esm.dto.mapper.GiftCertificateParamDTOMapper;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.impl.EntityNotFoundException;
import com.epam.esm.exception.impl.InvalidDataException;
import com.epam.esm.impl.GiftCertificateServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;

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
@MockitoSettings(strictness = Strictness.LENIENT)
class GiftCertificateServiceImplTest {

    private static final int TEST_GC_ID_FIRST = 1;
    private static final int TEST_GC_ID_SECOND = 2;
    private static final int TEST_TAG_ID = 3;
    private static final String TEST_TAG_NAME = "Rest";
    private static final String TEST_GC_NAME_FIRST = "Test GiftCertificate name first";
    private static final String TEST_GC_NAME_SECOND = "Test GiftCertificate name second";
    private static final String TEST_DESCRIPTION = "Test description";
    private static final String TEST_NEW_NAME = "Test new GiftCertificate name";
    private static final String TEST_NEW_DESCRIPTION = "Test new description";
    private static final int TEST_PRICE = 50;
    private static final int TEST_DURATION = 60;

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    @Mock
    private GiftCertificateDAO giftCertificateDAO;
    @Mock
    private TagDAO tagDAO;
    @Mock
    private ModelMapper modelMapper;

    private Tag tag;
    private GiftCertificate giftCertificateFirst;
    private GiftCertificate giftCertificateSecond;

    private GiftCertificateDTO notFullyFilledGiftCertificateDTO;
    private GiftCertificateDTO updatedGiftCertificateDTO;
    private GiftCertificateDTO createdGiftCertificateDTO;


    private List<Tag> tagList;
    private List<String> tagNamesList;
    private List<GiftCertificate> giftCertificateList;
    private List<GiftCertificate> emptyGiftCertificateList;

    private GiftCertificateParamDTO compositeParameterDTO;
    private GiftCertificateParamDTO emptyCompositeParameterDTO;
    private GiftCertificateParam compositeParameter;
    private GiftCertificateParam emptyCompositeParameter;


    @BeforeEach
    public void setUp() {
        tag = new Tag();
        tag.setId(TEST_TAG_ID);
        tag.setName(TEST_TAG_NAME);

        tagList = new ArrayList<>();
        tagList.add(tag);

        giftCertificateFirst = new GiftCertificate();
        giftCertificateFirst.setId(TEST_GC_ID_FIRST);
        giftCertificateFirst.setName(TEST_GC_NAME_FIRST);
        giftCertificateFirst.setDescription(TEST_DESCRIPTION);
        giftCertificateFirst.setPrice(TEST_PRICE);
        giftCertificateFirst.setDuration(TEST_DURATION);
        giftCertificateFirst.setTagList(tagList);

        giftCertificateSecond = new GiftCertificate();
        giftCertificateSecond.setId(TEST_GC_ID_SECOND);
        giftCertificateSecond.setName(TEST_GC_NAME_SECOND);
        giftCertificateSecond.setDescription(TEST_DESCRIPTION);
        giftCertificateSecond.setPrice(TEST_PRICE);
        giftCertificateSecond.setDuration(TEST_DURATION);
        giftCertificateSecond.setTagList(tagList);

        notFullyFilledGiftCertificateDTO = new GiftCertificateDTO();
        notFullyFilledGiftCertificateDTO.setName(TEST_NEW_NAME);
        notFullyFilledGiftCertificateDTO.setDescription(TEST_NEW_DESCRIPTION);

        tagNamesList = new ArrayList<>();
        tagNamesList.add(TEST_TAG_NAME);

        createdGiftCertificateDTO = new GiftCertificateDTO();
        createdGiftCertificateDTO.setId(TEST_GC_ID_FIRST);
        createdGiftCertificateDTO.setName(TEST_GC_NAME_FIRST);
        createdGiftCertificateDTO.setDescription(TEST_DESCRIPTION);
        createdGiftCertificateDTO.setPrice(TEST_PRICE);
        createdGiftCertificateDTO.setDuration(TEST_DURATION);
        createdGiftCertificateDTO.setTagNames(tagNamesList);

        updatedGiftCertificateDTO = new GiftCertificateDTO();
        updatedGiftCertificateDTO.setName(TEST_GC_NAME_FIRST);
        updatedGiftCertificateDTO.setDescription(TEST_DESCRIPTION);
        updatedGiftCertificateDTO.setPrice(TEST_PRICE);
        updatedGiftCertificateDTO.setDuration(TEST_DURATION);
        updatedGiftCertificateDTO.setTagNames(tagNamesList);

        emptyGiftCertificateList = new ArrayList<>();
        giftCertificateList = new ArrayList<>();
        giftCertificateList.add(giftCertificateFirst);
        giftCertificateList.add(giftCertificateSecond);

        emptyCompositeParameterDTO = new GiftCertificateParamDTO();
        emptyCompositeParameter = new GiftCertificateParam();
        compositeParameterDTO = new GiftCertificateParamDTO();
        compositeParameter = new GiftCertificateParam();

        compositeParameterDTO.setName(TEST_NEW_NAME);
        compositeParameter.setName(TEST_NEW_NAME);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void createShouldReturnCreatedGiftCertificate() {
        given(giftCertificateDAO.save(any())).willReturn(giftCertificateFirst);
        given(tagDAO.findByName(TEST_TAG_NAME)).willReturn(Optional.of(tag));
        GiftCertificateDTO createdGCDTO = giftCertificateService.create(updatedGiftCertificateDTO);
        assertEquals(TEST_GC_NAME_FIRST, createdGCDTO.getName());
        assertEquals(TEST_DESCRIPTION, createdGCDTO.getDescription());
        assertEquals(TEST_PRICE, createdGCDTO.getPrice());
        assertEquals(TEST_DURATION, createdGCDTO.getDuration());
        assertEquals(tagNamesList, createdGCDTO.getTagNames());
    }

    @Test
    public void createShouldInvalidDataException() {
        assertThrows(InvalidDataException.class,
                () -> giftCertificateService.create(notFullyFilledGiftCertificateDTO));
    }

    @Test
    public void findByIdShouldSuccessfully() {
        given(giftCertificateDAO.find(TEST_GC_ID_FIRST)).willReturn(Optional.of(giftCertificateFirst));
        GiftCertificateDTO readGCDTO = giftCertificateService.findById(TEST_GC_ID_FIRST);
        assertEquals(createdGiftCertificateDTO, readGCDTO);
    }

    @Test
    public void findByIdShouldNotFoundException() {
        given(giftCertificateDAO.find(TEST_GC_ID_FIRST)).willReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> giftCertificateService.findById(TEST_GC_ID_FIRST));
    }

    @Test
    public void updateShouldSuccessfully() {
        given(giftCertificateDAO.find(TEST_GC_ID_FIRST)).willReturn(Optional.of(giftCertificateFirst));
        given(giftCertificateDAO.save(giftCertificateFirst)).willReturn(giftCertificateFirst);
        given(tagDAO.findByName(TEST_TAG_NAME)).willReturn(Optional.of(tag));
        Mockito.doNothing().when(modelMapper).map(updatedGiftCertificateDTO, giftCertificateFirst);
        GiftCertificateDTO updatedGCDTO = giftCertificateService.update(updatedGiftCertificateDTO, TEST_GC_ID_FIRST);
        assertEquals(updatedGiftCertificateDTO, updatedGCDTO);
    }

    @Test
    public void updateShouldNotFoundException() {
        given(giftCertificateDAO.find(TEST_GC_ID_FIRST)).willReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,
                () -> giftCertificateService.update(updatedGiftCertificateDTO, TEST_GC_ID_FIRST));
    }

    @Test
    public void deleteShouldSuccessfully() {
        given(giftCertificateDAO.find(TEST_GC_ID_FIRST)).willReturn(Optional.of(giftCertificateFirst));
        given(tagDAO.findByGiftCertificateId(TEST_GC_ID_FIRST)).willReturn(tagList);
        giftCertificateService.delete(TEST_GC_ID_FIRST);
        verify(giftCertificateDAO, times(1)).delete(TEST_GC_ID_FIRST);
        verify(tagDAO, times(1)).delete(TEST_TAG_ID);
    }

    @Test
    public void deleteShouldNotFoundException() {
        given(giftCertificateDAO.find(TEST_GC_ID_FIRST)).willReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> giftCertificateService.delete(TEST_GC_ID_FIRST));
    }

    @Test
    public void findWithEmptyParamShouldSuccessfully() {
        final int CORRECT_SIZE = 2;
        try (MockedStatic<GiftCertificateParamDTOMapper> paramDTOMapper = Mockito.mockStatic(GiftCertificateParamDTOMapper.class)) {
            paramDTOMapper.when(() -> GiftCertificateParamDTOMapper.convertToEntity(emptyCompositeParameterDTO)).thenReturn(emptyCompositeParameter);
            given(giftCertificateDAO.findByParam(2,2, emptyCompositeParameter)).willReturn(giftCertificateList);
            List<GiftCertificateDTO> giftCertificateDTOList = giftCertificateService.findByParam(2, 2, emptyCompositeParameterDTO);
            assertEquals(CORRECT_SIZE, giftCertificateDTOList.size());
        }
    }

    @Test
    public void findByParamShouldSuccessfully() {
        final int CORRECT_SIZE = 2;
        try (MockedStatic<GiftCertificateParamDTOMapper> paramDTOMapper = Mockito.mockStatic(GiftCertificateParamDTOMapper.class)) {
            paramDTOMapper.when(() -> GiftCertificateParamDTOMapper.convertToEntity(compositeParameterDTO)).thenReturn(compositeParameter);
            given(giftCertificateDAO.findByParam(2, 2, compositeParameter)).willReturn(giftCertificateList);
            List<GiftCertificateDTO> giftCertificateDTOList = giftCertificateService.findByParam(2, 2, compositeParameterDTO);
            assertEquals(CORRECT_SIZE, giftCertificateDTOList.size());
        }
    }

    @Test
    public void findWithEmptyParamShouldNotFoundException() {
        try (MockedStatic<GiftCertificateParamDTOMapper> paramDTOMapper = Mockito.mockStatic(GiftCertificateParamDTOMapper.class)) {
            paramDTOMapper.when(() -> GiftCertificateParamDTOMapper.convertToEntity(compositeParameterDTO)).thenReturn(compositeParameter);
            given(giftCertificateDAO.findByParam(2, 2, emptyCompositeParameter)).willReturn(emptyGiftCertificateList);
            assertThrows(EntityNotFoundException.class, () -> giftCertificateService.findByParam(2, 2, emptyCompositeParameterDTO));
        }
    }
}