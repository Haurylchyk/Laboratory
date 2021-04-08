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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {

    private static final int TEST_GC_ID = 1;
    private static final int TEST_TAG_ID = 3;
    private static final String TEST_TAG_NAME = "Test Tag name";
    private static final String TEST_GC_NAME = "Test GiftCertificate name";
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

    private Tag testTag;
    private GiftCertificate giftCertificate;
    private GiftCertificateDTO notFullyValuedGiftCertificateDTO;
    private GiftCertificateDTO validGiftCertificateDTO;

    private List<String> tagNamesList;
    private List<Tag> giftTagList;
    private List<GiftCertificate> giftCertificateList;

    @BeforeEach
    public void setUp() {
        testTag = new Tag();
        testTag.setId(TEST_TAG_ID);
        testTag.setName(TEST_TAG_NAME);

        giftTagList = new ArrayList<>();
        giftTagList.add(testTag);

        giftCertificate = new GiftCertificate();
        giftCertificate.setId(TEST_GC_ID);
        giftCertificate.setName(TEST_GC_NAME);
        giftCertificate.setDescription(TEST_DESCRIPTION);
        giftCertificate.setPrice(TEST_PRICE);
        giftCertificate.setDuration(TEST_DURATION);
        giftCertificate.setTagList(giftTagList);

        notFullyValuedGiftCertificateDTO = new GiftCertificateDTO();
        notFullyValuedGiftCertificateDTO.setName(TEST_NEW_NAME);
        notFullyValuedGiftCertificateDTO.setDescription(TEST_NEW_DESCRIPTION);

        tagNamesList = new ArrayList<>();
        tagNamesList.add(TEST_TAG_NAME);

        validGiftCertificateDTO = new GiftCertificateDTO();
        validGiftCertificateDTO.setName(TEST_GC_NAME);
        validGiftCertificateDTO.setDescription(TEST_DESCRIPTION);
        validGiftCertificateDTO.setPrice(TEST_PRICE);
        validGiftCertificateDTO.setDuration(TEST_DURATION);
        validGiftCertificateDTO.setTagNames(tagNamesList);

        giftCertificateList = new ArrayList<>();
        giftCertificateList.add(giftCertificate);
        giftCertificateList.add(giftCertificate);
        giftCertificateList.add(giftCertificate);

        giftCertificateService = new GiftCertificateServiceImpl(giftCertificateDAO, tagDAO);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void deleteCertificate() {
        given(giftCertificateDAO.getGiftCertificateById(TEST_GC_ID)).willReturn(Optional.of(giftCertificate));
        given(tagDAO.getTagsByGiftCertificateId(TEST_GC_ID)).willReturn(giftTagList);

        giftCertificateService.deleteGiftCertificate(TEST_GC_ID);

        verify(giftCertificateDAO, times(1)).deleteGiftCertificate(TEST_GC_ID);
        verify(tagDAO, times(1)).deleteTag(TEST_TAG_ID);
    }

    @Test
    public void deleteCertificateShouldException() {
        given(giftCertificateDAO.getGiftCertificateById(TEST_GC_ID)).willReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> giftCertificateService.deleteGiftCertificate(TEST_GC_ID));
    }

    @Test
    public void getGiftCertificateByID() {
        given(giftCertificateDAO.getGiftCertificateById(TEST_GC_ID)).willReturn(Optional.of(giftCertificate));
        GiftCertificateDTO receivedCertificateDTO = giftCertificateService.getGiftCertificateById(TEST_GC_ID);

        GiftCertificateDTO testedDTO = GiftCertificateDTOMapper.convertToDTO(giftCertificate);
        assertEquals(testedDTO, receivedCertificateDTO);
    }


    @Test
    public void getGiftCertificateByIDShouldException() {
        given(giftCertificateDAO.getGiftCertificateById(TEST_GC_ID)).willReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> giftCertificateService.getGiftCertificateById(TEST_GC_ID));
    }


    @Test
    public void updateCertificate() {
        given(giftCertificateDAO.getGiftCertificateById(TEST_GC_ID)).willReturn(Optional.of(giftCertificate));
        given(giftCertificateDAO.updateGiftCertificate(any(), anyInt())).willReturn(giftCertificate);
        given(tagDAO.getTagByName(TEST_TAG_NAME)).willReturn(Optional.of(testTag));
        given(tagDAO.getTagsByGiftCertificateId(TEST_GC_ID)).willReturn(giftTagList);

        GiftCertificateDTO receivedDTO = giftCertificateService.updateGiftCertificate(validGiftCertificateDTO, TEST_GC_ID);
        assertEquals(validGiftCertificateDTO, receivedDTO);
    }

    @Test
    public void updateCertificateShouldException() {
        given(giftCertificateDAO.getGiftCertificateById(TEST_GC_ID)).willReturn(Optional.empty());

        assertThrows(ServiceException.class,
                () -> giftCertificateService.updateGiftCertificate(any(), TEST_GC_ID));
    }

    @Test
    public void createGiftCertificate() {
        given(giftCertificateDAO.createGiftCertificate(any())).willReturn(giftCertificate);
        given(tagDAO.getTagByName(TEST_TAG_NAME)).willReturn(Optional.of(testTag));
        given(tagDAO.getTagsByGiftCertificateId(TEST_GC_ID)).willReturn(giftTagList);

        GiftCertificateDTO receivedDTO = giftCertificateService.createGiftCertificate(validGiftCertificateDTO);

        assertEquals(TEST_GC_NAME, receivedDTO.getName());
        assertEquals(TEST_DESCRIPTION, receivedDTO.getDescription());
        assertEquals(TEST_PRICE, receivedDTO.getPrice());
        assertEquals(TEST_DURATION, receivedDTO.getDuration());
        assertEquals(tagNamesList, receivedDTO.getTagNames());
    }

    @Test
    public void createGiftCertificateShouldException() {
        assertThrows(ServiceException.class,
                () -> giftCertificateService.createGiftCertificate(notFullyValuedGiftCertificateDTO));
    }

//    @Test
//    public void getCertificates() {
//        given(giftCertificateDAO.getAllGiftCertificates()).willReturn(giftCertificateList);
//        given(tagDAO.getTagsByGiftCertificateId(TEST_GC_ID)).willReturn(giftTagList);
//
//        List<GiftCertificateDTO> receivedDTOList = giftCertificateService.getAllGiftCertificates();
//        List<GiftCertificateDTO> testDTOList = GiftCertificateDTOMapper.convertToDTO(giftCertificateList);
//
//        assertIterableEquals(testDTOList, receivedDTOList);
//    }
}