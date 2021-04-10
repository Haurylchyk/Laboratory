package com.epam.esm;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.mapper.GiftCertificateDTOMapper;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.impl.GiftCertificateServiceImpl;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
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

    private Tag tag;
    private GiftCertificate giftCertificateFirst;
    private GiftCertificate giftCertificateSecond;

    private GiftCertificateDTO notFullyFilledGiftCertificateDTO;
    private GiftCertificateDTO validGiftCertificateDTO;

    private List<Tag> tagList;
    private List<String> tagNamesList;
    private List<GiftCertificate> giftCertificateList;

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

        validGiftCertificateDTO = new GiftCertificateDTO();
        validGiftCertificateDTO.setName(TEST_GC_NAME_FIRST);
        validGiftCertificateDTO.setDescription(TEST_DESCRIPTION);
        validGiftCertificateDTO.setPrice(TEST_PRICE);
        validGiftCertificateDTO.setDuration(TEST_DURATION);
        validGiftCertificateDTO.setTagNames(tagNamesList);

        giftCertificateList = new ArrayList<>();
        giftCertificateList.add(giftCertificateFirst);
        giftCertificateList.add(giftCertificateSecond);

        giftCertificateService = new GiftCertificateServiceImpl(giftCertificateDAO, tagDAO);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void createShouldReturnCreatedGiftCertificate() {
        given(giftCertificateDAO.create(any())).willReturn(giftCertificateFirst);
        given(tagDAO.readTagByName(TEST_TAG_NAME)).willReturn(Optional.of(tag));
        given(tagDAO.readTagsByGiftCertificateId(TEST_GC_ID_FIRST)).willReturn(tagList);
        GiftCertificateDTO createdGCDTO = giftCertificateService.create(validGiftCertificateDTO);
        assertEquals(TEST_GC_NAME_FIRST, createdGCDTO.getName());
        assertEquals(TEST_DESCRIPTION, createdGCDTO.getDescription());
        assertEquals(TEST_PRICE, createdGCDTO.getPrice());
        assertEquals(TEST_DURATION, createdGCDTO.getDuration());
        assertEquals(tagNamesList, createdGCDTO.getTagNames());
    }

    @Test
    public void createShouldException() {
        assertThrows(ServiceException.class,
                () -> giftCertificateService.create(notFullyFilledGiftCertificateDTO));
    }

    @Test
    public void readShouldSuccessfully() {
        given(giftCertificateDAO.read(TEST_GC_ID_FIRST)).willReturn(Optional.of(giftCertificateFirst));
        GiftCertificateDTO readGCDTO = giftCertificateService.read(TEST_GC_ID_FIRST);
        GiftCertificateDTO testedDTO = GiftCertificateDTOMapper.convertToDTO(giftCertificateFirst);
        assertEquals(testedDTO, readGCDTO);
    }

    @Test
    public void readShouldException() {
        given(giftCertificateDAO.read(TEST_GC_ID_FIRST)).willReturn(Optional.empty());
        assertThrows(ServiceException.class, () -> giftCertificateService.read(TEST_GC_ID_FIRST));
    }

    @Test
    public void updateShouldSuccessfully() {
        given(giftCertificateDAO.read(TEST_GC_ID_FIRST)).willReturn(Optional.of(giftCertificateFirst));
        given(giftCertificateDAO.update(any(), anyInt())).willReturn(giftCertificateFirst);
        given(tagDAO.readTagByName(TEST_TAG_NAME)).willReturn(Optional.of(tag));
        given(tagDAO.readTagsByGiftCertificateId(TEST_GC_ID_FIRST)).willReturn(tagList);
        GiftCertificateDTO updatedGCDTO = giftCertificateService.update(validGiftCertificateDTO, TEST_GC_ID_FIRST);
        assertEquals(validGiftCertificateDTO, updatedGCDTO);
    }

    @Test
    public void updateShouldException() {
        given(giftCertificateDAO.read(TEST_GC_ID_FIRST)).willReturn(Optional.empty());
        assertThrows(ServiceException.class,
                () -> giftCertificateService.update(any(), TEST_GC_ID_FIRST));
    }


    @Test
    public void deleteShouldSuccessfully() {
        given(giftCertificateDAO.read(TEST_GC_ID_FIRST)).willReturn(Optional.of(giftCertificateFirst));
        given(tagDAO.readTagsByGiftCertificateId(TEST_GC_ID_FIRST)).willReturn(tagList);
        giftCertificateService.delete(TEST_GC_ID_FIRST);
        verify(giftCertificateDAO, times(1)).delete(TEST_GC_ID_FIRST);
        verify(tagDAO, times(1)).delete(TEST_TAG_ID);
    }

    @Test
    public void deleteShouldException() {
        given(giftCertificateDAO.read(TEST_GC_ID_FIRST)).willReturn(Optional.empty());
        assertThrows(ServiceException.class, () -> giftCertificateService.delete(TEST_GC_ID_FIRST));
    }
}