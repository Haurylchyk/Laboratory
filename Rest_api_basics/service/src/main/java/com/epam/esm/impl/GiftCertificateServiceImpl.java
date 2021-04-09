package com.epam.esm.impl;

import com.epam.esm.GiftCertificateService;
import com.epam.esm.constant.ErrorCodeMessage;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.query.GiftCertificateCompositeQuery;
import com.epam.esm.dao.query.GiftCertificateCompositeParameter;
import com.epam.esm.dao.query.builder.GiftCertificateQueryBuilder;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.mapper.GiftCertificateDTOMapper;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.impl.GiftCertificateInvalidDataException;
import com.epam.esm.exception.impl.GiftCertificateNotFoundException;
import com.epam.esm.validator.GiftCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public GiftCertificateDTO createGiftCertificate(GiftCertificateDTO giftCertificateDTO) {

        if (!GiftCertificateValidator.isValidData(giftCertificateDTO)) {
            throw new GiftCertificateInvalidDataException(ErrorCodeMessage.ERROR_CODE_GС_INVALID_DATA);
        }

        GiftCertificate giftCertificate = GiftCertificateDTOMapper.convertToEntity(giftCertificateDTO);
        GiftCertificate newGiftCertificate = giftCertificateDAO.createGiftCertificate(giftCertificate);

        List<String> tagNamesList = giftCertificateDTO.getTagNames();
        for (String tagName : tagNamesList) {
            Optional<Tag> optionalTag = tagDAO.getTagByName(tagName);
            Tag newTag = optionalTag.orElseGet(() -> tagDAO.createTag(tagName));
            giftCertificateDAO.putCertificateTag(newGiftCertificate.getId(), newTag.getId());
        }
        List<Tag> tagList = tagDAO.getTagsByGiftCertificateId(newGiftCertificate.getId());
        giftCertificate.setTagList(tagList);
        return GiftCertificateDTOMapper.convertToDTO(giftCertificate);
    }

    /**
     * Accesses the corresponding DAO method to get GiftCertificate object with specific id.
     *
     * @param id GiftCertificate id.
     * @return object with GiftCertificate data.
     */
    @Override
    public GiftCertificateDTO getGiftCertificateById(Integer id) {
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDAO.getGiftCertificateById(id);

        if (!optionalGiftCertificate.isPresent()) {
            throw new GiftCertificateNotFoundException(ErrorCodeMessage.ERROR_CODE_GС_NOT_FOUND);
        }
        GiftCertificate giftCertificate = optionalGiftCertificate.get();
        List<Tag> tagList = tagDAO.getTagsByGiftCertificateId(id);
        giftCertificate.setTagList(tagList);
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
    public GiftCertificateDTO updateGiftCertificate(GiftCertificateDTO updatedCertificateDTO, Integer id) {

        if (!giftCertificateDAO.getGiftCertificateById(id).isPresent()) {
            throw new GiftCertificateNotFoundException(ErrorCodeMessage.ERROR_CODE_GС_NOT_FOUND);
        }
        updatedCertificateDTO.setId(id);

        GiftCertificate giftCertificate = GiftCertificateDTOMapper.convertToEntity(updatedCertificateDTO);

        GiftCertificateQueryBuilder queryBuilder = GiftCertificateQueryBuilder.getInstance();
        GiftCertificateCompositeQuery compositeQuery = queryBuilder.buildUpdateQuery(giftCertificate);
        GiftCertificate updatedGiftCertificate = giftCertificateDAO.updateGiftCertificate(compositeQuery, id);
        List<String> tagNamesList = updatedCertificateDTO.getTagNames();
        if (tagNamesList != null) {
            giftCertificateDAO.deleteCertificateTagsById(id);
            for (String tagName : tagNamesList) {
                Optional<Tag> optionalTag = tagDAO.getTagByName(tagName);
                Tag tag = optionalTag.orElseGet(() -> tagDAO.createTag(tagName));
                giftCertificateDAO.putCertificateTag(id, tag.getId());
            }
        }
        List<Tag> tagList = tagDAO.getTagsByGiftCertificateId(id);
        updatedGiftCertificate.setTagList(tagList);

        return GiftCertificateDTOMapper.convertToDTO(updatedGiftCertificate);
    }

    /**
     * Accesses the corresponding DAO method to delete GiftCertificate object with specific id.
     *
     * @param id GiftCertificate id.
     */
    @Override
    @Transactional
    public void deleteGiftCertificate(Integer id) {
        if (!giftCertificateDAO.getGiftCertificateById(id).isPresent()) {
            throw new GiftCertificateNotFoundException(ErrorCodeMessage.ERROR_CODE_GС_NOT_FOUND);
        }
        List<Tag> tagList = tagDAO.getTagsByGiftCertificateId(id);
        giftCertificateDAO.deleteGiftCertificate(id);
        for (Tag tag : tagList) {
            if (giftCertificateDAO.getGiftCertificatesByTagName(tag.getName()).isEmpty()) {
                tagDAO.deleteTag(tag.getId());
            }
        }
    }

    /**
     * Accesses the corresponding DAO method to get all GiftCertificates.
     *
     * @return List of objects with GiftCertificate data.
     */
    private List<GiftCertificateDTO> getAllGiftCertificates() {
        List<GiftCertificate> giftCertificates = giftCertificateDAO.getAllGiftCertificates();

        if (giftCertificates.isEmpty()) {
            throw new GiftCertificateNotFoundException(ErrorCodeMessage.ERROR_CODE_GС_NOT_FOUND);
        }
        List<GiftCertificateDTO> giftCertificatesDTO = new ArrayList<>();

        for (GiftCertificate giftCertificate : giftCertificates) {
            List<Tag> tagList = tagDAO.getTagsByGiftCertificateId(giftCertificate.getId());
            giftCertificate.setTagList(tagList);
            giftCertificatesDTO.add(GiftCertificateDTOMapper.convertToDTO(giftCertificate));
        }
        return giftCertificatesDTO;
    }

    /**
     * Accesses the corresponding DAO method to get all GiftCertificates
     * that have Tag with specific name.
     *
     * @return List of objects with GiftCertificate data.
     */
    @Override
    public List<GiftCertificateDTO> getGiftCertificatesByTagName(String name) {
        List<GiftCertificate> giftCertificates = giftCertificateDAO.getGiftCertificatesByTagName(name);

        if (giftCertificates.isEmpty()) {
            throw new GiftCertificateNotFoundException(ErrorCodeMessage.ERROR_CODE_GС_NOT_FOUND);
        }
        List<GiftCertificateDTO> giftCertificatesDTO = new ArrayList<>();

        for (GiftCertificate giftCertificate : giftCertificates) {
            List<Tag> tagList = tagDAO.getTagsByGiftCertificateId(giftCertificate.getId());
            giftCertificate.setTagList(tagList);
            giftCertificatesDTO.add(GiftCertificateDTOMapper.convertToDTO(giftCertificate));
        }
        return giftCertificatesDTO;
    }

    /**
     * Accesses the corresponding DAO method to get GiftCertificates that matches parameters.
     *
     * @param giftCertificateCompositeParameter special object containing requested parameters.
     * @return list of GiftCertificates.
     */
    public List<GiftCertificateDTO> getGiftCertificates(GiftCertificateCompositeParameter giftCertificateCompositeParameter) {
        if (giftCertificateCompositeParameter.isEmpty()) {
            return getAllGiftCertificates();
        }
        GiftCertificateCompositeQuery giftCertificateCompositeQuery = GiftCertificateQueryBuilder.getInstance()
                .buildGetQuery(giftCertificateCompositeParameter);

        List<GiftCertificate> giftCertificates = giftCertificateDAO.getGiftCertificates(giftCertificateCompositeQuery);

        List<GiftCertificateDTO> giftCertificatesDTO = new ArrayList<>();

        for (GiftCertificate giftCertificate : giftCertificates) {
            List<Tag> tagList = tagDAO.getTagsByGiftCertificateId(giftCertificate.getId());
            giftCertificate.setTagList(tagList);
            giftCertificatesDTO.add(GiftCertificateDTOMapper.convertToDTO(giftCertificate));
        }
        return giftCertificatesDTO;
    }
}
