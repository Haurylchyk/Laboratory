package com.epam.esm.impl;

import com.epam.esm.filler.GiftCertificateUpdatedFieldFiller;
import com.epam.esm.GiftCertificateService;
import com.epam.esm.constant.ErrorCodeMessage;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.query.GiftCertificateParam;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.GiftCertificateParamDTO;
import com.epam.esm.dto.mapper.GiftCertificateDTOMapper;
import com.epam.esm.dto.mapper.GiftCertificateParamDTOMapper;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.impl.GiftCertificateInvalidDataException;
import com.epam.esm.exception.impl.GiftCertificateNotFoundException;
import com.epam.esm.validator.GiftCertificateValidator;
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

    /**
     * Constructor with parameter.
     *
     * @param giftCertificateDAO interface for working with
     *                           the corresponding DAO methods.
     * @param tagDAO             interface for working with
     *                           the corresponding DAO methods.
     */
    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDAO giftCertificateDAO, TagDAO tagDAO) {
        this.giftCertificateDAO = giftCertificateDAO;
        this.tagDAO = tagDAO;
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
            throw new GiftCertificateInvalidDataException(ErrorCodeMessage.ERROR_CODE_GС_INVALID_DATA);
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
                () -> new GiftCertificateNotFoundException(ErrorCodeMessage.ERROR_CODE_GС_NOT_FOUND));
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
            throw new GiftCertificateInvalidDataException(ErrorCodeMessage.ERROR_CODE_GС_INVALID_DATA);
        }
        Optional<GiftCertificate> optionalGiftCertificateFromDB = giftCertificateDAO.find(id);
        GiftCertificate giftCertificateFromDB = optionalGiftCertificateFromDB.orElseThrow(() -> new GiftCertificateNotFoundException(
                ErrorCodeMessage.ERROR_CODE_GС_NOT_FOUND));
        updatedCertificateDTO.setId(id);

        GiftCertificate giftCertificateFromDTO = GiftCertificateDTOMapper.convertToEntity(updatedCertificateDTO);

        List<Tag> tagList = returnCreatedOrExistingTags(updatedCertificateDTO.getTagNames());
        giftCertificateFromDTO.setTagList(tagList);

        GiftCertificate giftCertificate = GiftCertificateUpdatedFieldFiller.
                update(giftCertificateFromDTO, giftCertificateFromDB);
        giftCertificate.setLastUpdateDate(LocalDateTime.now());

        GiftCertificate updatedGiftCertificate = giftCertificateDAO.save(giftCertificate);

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
        optionalGiftCertificate.orElseThrow(() -> new GiftCertificateNotFoundException(
                ErrorCodeMessage.ERROR_CODE_GС_NOT_FOUND));
        List<Tag> tagList = tagDAO.findByGiftCertificateId(id);
        giftCertificateDAO.delete(id);
        deleteTagsIfNotUsed(tagList);
    }

    /**
     * Accesses the corresponding DAO method to find GiftCertificates that matches parameters.
     *
     * @param parameterDTO special object containing requested parameters.
     * @return list of GiftCertificates.
     */
    @Override
    public List<GiftCertificateDTO> findByParam(GiftCertificateParamDTO parameterDTO) {
        GiftCertificateParam parameter = GiftCertificateParamDTOMapper.convertToEntity(parameterDTO);

        List<GiftCertificate> giftCertificates = giftCertificateDAO.findByParam(parameter);

        if (giftCertificates.isEmpty()) {
            throw new GiftCertificateNotFoundException(ErrorCodeMessage.ERROR_CODE_GС_NOT_FOUND);
        }

        List<GiftCertificateDTO> giftCertificatesDTO = new ArrayList<>();

        for (GiftCertificate giftCertificate : giftCertificates) {
            List<Tag> tagList = tagDAO.findByGiftCertificateId(giftCertificate.getId());
            giftCertificate.setTagList(tagList);
            giftCertificatesDTO.add(GiftCertificateDTOMapper.convertToDTO(giftCertificate));
        }
        return giftCertificatesDTO;
    }

    private List<Tag> returnCreatedOrExistingTags(List<String> tagNamesList) {
        if (tagNamesList == null) {
            return null;
        }
        List<Tag> tagList = new ArrayList<>();

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