package com.epam.esm.impl;

import com.epam.esm.GiftCertificateService;
import com.epam.esm.constant.ErrorCodeMessage;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.query.parameter.GiftCertificateParam;
import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.model.dto.GiftCertificateParamDTO;
import com.epam.esm.model.dto.mapper.GiftCertificateDTOMapper;
import com.epam.esm.model.dto.mapper.GiftCertificateParamDTOMapper;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.impl.EntityNotFoundException;
import com.epam.esm.exception.impl.InvalidDataException;
import com.epam.esm.validator.GiftCertificateValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class implements interface GiftCertificateService. Describes the service
 * for working with GiftCertificateDTO.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    /**
     * Object of the GiftCertificateDAO type.
     */
    private final GiftCertificateDAO giftCertificateDAO;

    /**
     * Object of the TagDAO type.
     */
    private final TagDAO tagDAO;

    private ModelMapper modelMapper;

    /**
     * Constructor with parameter.
     *
     * @param giftCertificateDAO interface for working with
     *                           the corresponding DAO methods.
     * @param tagDAO             interface for working with
     *                           the corresponding DAO methods.
     */
    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDAO giftCertificateDAO, TagDAO tagDAO, ModelMapper modelMapper) {
        this.giftCertificateDAO = giftCertificateDAO;
        this.tagDAO = tagDAO;
        this.modelMapper = modelMapper;
    }

    /**
     * Accesses the corresponding DAO method to create a new GiftCertificate object.
     *
     * @param giftCertificateDTO object with GiftCertificate data.
     * @return created object with GiftCertificate data.
     */
    @Override
    @Transactional
    public GiftCertificateDTO create(GiftCertificateDTO giftCertificateDTO) {
        if (!GiftCertificateValidator.isValidDataForCreate(giftCertificateDTO)) {
            throw new InvalidDataException(ErrorCodeMessage.ERROR_CODE_GC_INVALID_DATA);
        }
        List<Tag> tagList = returnCreatedOrExistingTags(giftCertificateDTO.getTagNames());

        GiftCertificate giftCertificate = GiftCertificateDTOMapper.convertToEntity(giftCertificateDTO);
        giftCertificate.setTagList(tagList);
        giftCertificate.setCreateDate(LocalDateTime.now());
        giftCertificate.setLastUpdateDate(LocalDateTime.now());

        GiftCertificate newGiftCertificate = giftCertificateDAO.save(giftCertificate);

        return GiftCertificateDTOMapper.convertToDTO(newGiftCertificate);
    }

    /**
     * Accesses the corresponding DAO method to find GiftCertificate object with specific id.
     *
     * @param id GiftCertificate id.
     * @return object with GiftCertificate data.
     */
    @Override
    public GiftCertificateDTO findById(Integer id) {
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDAO.find(id);
        GiftCertificate giftCertificate = optionalGiftCertificate.orElseThrow(
                () -> new EntityNotFoundException(ErrorCodeMessage.ERROR_CODE_GC_NOT_FOUND));
        return GiftCertificateDTOMapper.convertToDTO(giftCertificate);
    }

    /**
     * Accesses the corresponding DAO method to update GiftCertificate with specific id.
     *
     * @param updatedCertificateDTO object with GiftCertificate data.
     * @param id                    GiftCertificate id.
     * @return object with GiftCertificate data.
     */
    @Override
    @Transactional
    public GiftCertificateDTO update(GiftCertificateDTO updatedCertificateDTO, Integer id) {
        if (!GiftCertificateValidator.isValidDataForUpdate(updatedCertificateDTO)) {
            throw new InvalidDataException(ErrorCodeMessage.ERROR_CODE_GC_INVALID_DATA);
        }
        Optional<GiftCertificate> optionalGiftCertificateFromDB = giftCertificateDAO.find(id);
        GiftCertificate giftCertificateFromDB = optionalGiftCertificateFromDB.orElseThrow(() -> new EntityNotFoundException(
                ErrorCodeMessage.ERROR_CODE_GC_NOT_FOUND));
        updatedCertificateDTO.setId(id);

        GiftCertificate giftCertificateFromDTO = GiftCertificateDTOMapper.convertToEntity(updatedCertificateDTO);

        List<Tag> tagList = returnCreatedOrExistingTags(updatedCertificateDTO.getTagNames());

        modelMapper.map(giftCertificateFromDTO, giftCertificateFromDB);
        giftCertificateFromDB.setLastUpdateDate(LocalDateTime.now());
        if (!tagList.isEmpty()) {
            giftCertificateFromDB.setTagList(tagList);
        }

        GiftCertificate updatedGiftCertificate = giftCertificateDAO.save(giftCertificateFromDB);

        return GiftCertificateDTOMapper.convertToDTO(updatedGiftCertificate);
    }

    /**
     * Accesses the corresponding DAO method to delete GiftCertificate object with specific id.
     *
     * @param id GiftCertificate id.
     */
    @Override
    @Transactional
    public void delete(Integer id) {
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDAO.find(id);
        optionalGiftCertificate.orElseThrow(() -> new EntityNotFoundException(
                ErrorCodeMessage.ERROR_CODE_GC_NOT_FOUND));
        List<Tag> tagList = tagDAO.findByGiftCertificateId(id);
        giftCertificateDAO.delete(id);
        deleteTagsIfNotUsed(tagList);
    }

    /**
     * Accesses the corresponding DAO method to find GiftCertificates that matches parameters.
     *
     * @param page         number of page.
     * @param size         number of GiftCertificates on page.
     * @param parameterDTO special object containing requested parameters.
     * @return list of GiftCertificates.
     */
    @Override
    public List<GiftCertificateDTO> findByParam(Integer page, Integer size, GiftCertificateParamDTO parameterDTO) {
        GiftCertificateParam parameter = GiftCertificateParamDTOMapper.convertToEntity(parameterDTO);

        List<GiftCertificate> giftCertificates = giftCertificateDAO.findByParam(page, size, parameter);

        List<GiftCertificateDTO> giftCertificatesDTO = new ArrayList<>();

        for (GiftCertificate giftCertificate : giftCertificates) {
            List<Tag> tagList = tagDAO.findByGiftCertificateId(giftCertificate.getId());
            giftCertificate.setTagList(tagList);
            giftCertificatesDTO.add(GiftCertificateDTOMapper.convertToDTO(giftCertificate));
        }
        return giftCertificatesDTO;
    }

    /**
     * Calculates the total number of pages
     * required to display all GiftCertificates.
     *
     * @return the total number of pages
     * required to display all GiftCertificates.
     */
    public Integer findNumberPagesForAllGiftCertificates(Integer size) {
        Integer totalNumber = giftCertificateDAO.countAll();
        return totalNumber % size == 0 ? totalNumber / size : totalNumber / size + 1;
    }

    private List<Tag> returnCreatedOrExistingTags(List<String> tagNamesList) {
        List<Tag> tagList = new ArrayList<>();
        if (tagNamesList == null) {
            return tagList;
        }

        tagNamesList.forEach(tagName -> {
            Optional<Tag> optionalTag = tagDAO.findByName(tagName);

            Tag tagForSave = new Tag();
            tagForSave.setName(tagName);

            Tag tag = optionalTag.orElseGet(() -> tagDAO.save(tagForSave));
            tagList.add(tag);
        });

        return tagList;
    }

    private void deleteTagsIfNotUsed(List<Tag> tagList) {
        for (Tag tag : tagList) {
            if (giftCertificateDAO.findByTagName(tag.getName()).isEmpty()) {
                tagDAO.delete(tag.getId());
            }
        }
    }
}