package com.epam.esm.impl;

import com.epam.esm.GiftCertificateService;
import com.epam.esm.constant.ErrorCodeMessage;
import com.epam.esm.dao.GiftCertificateRepository;
import com.epam.esm.dao.TagRepository;
import com.epam.esm.dao.query.parameter.GiftCertificateParam;
import com.epam.esm.dao.query.specification.GiftCertificateSpecification;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.impl.EntityNotFoundException;
import com.epam.esm.exception.impl.InvalidDataException;
import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.model.dto.GiftCertificateParamDTO;
import com.epam.esm.model.dto.mapper.impl.GiftCertificateDTOMapper;
import com.epam.esm.model.dto.mapper.impl.GiftCertificateParamDTOMapper;
import com.epam.esm.validator.GiftCertificateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    private final GiftCertificateRepository giftCertificateRepository;

    /**
     * Object of the TagDAO type.
     */
    private final TagRepository tagRepository;

    /**
     * Object intended for converting GiftCertificate to GiftCertificateDTO and vice versa.
     */
    private GiftCertificateDTOMapper giftCertificateDTOMapper;

    /**
     * Constructor with parameter.
     *
     * @param giftCertificateRepository interface for working with
     *                                  the corresponding DAO methods.
     * @param tagRepository             interface for working with
     *                                  the corresponding DAO methods.
     */
    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository, TagRepository tagRepository,
                                      GiftCertificateDTOMapper giftCertificateDTOMapper) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.tagRepository = tagRepository;
        this.giftCertificateDTOMapper = giftCertificateDTOMapper;
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

        GiftCertificate giftCertificate = giftCertificateDTOMapper.convertToEntity(giftCertificateDTO);
        giftCertificate.setTagList(tagList);
        giftCertificate.setCreateDate(LocalDateTime.now());
        giftCertificate.setLastUpdateDate(LocalDateTime.now());

        GiftCertificate newGiftCertificate = giftCertificateRepository.save(giftCertificate);

        return giftCertificateDTOMapper.convertToDTO(newGiftCertificate);
    }

    /**
     * Accesses the corresponding DAO method to find GiftCertificate object with specific id.
     *
     * @param id GiftCertificate id.
     * @return object with GiftCertificate data.
     */
    @Override
    public GiftCertificateDTO findById(Integer id) {
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateRepository.findById(id);
        GiftCertificate giftCertificate = optionalGiftCertificate.orElseThrow(
                () -> new EntityNotFoundException(ErrorCodeMessage.ERROR_CODE_GC_NOT_FOUND));
        return giftCertificateDTOMapper.convertToDTO(giftCertificate);
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
        Optional<GiftCertificate> optionalGiftCertificateFromDB = giftCertificateRepository.findById(id);
        GiftCertificate giftCertificateFromDB = optionalGiftCertificateFromDB.orElseThrow(() -> new EntityNotFoundException(
                ErrorCodeMessage.ERROR_CODE_GC_NOT_FOUND));
        updatedCertificateDTO.setId(id);

        GiftCertificate giftCertificateFromDTO = giftCertificateDTOMapper.convertToEntity(updatedCertificateDTO);

        List<Tag> tagList = returnCreatedOrExistingTags(updatedCertificateDTO.getTagNames());

        giftCertificateDTOMapper.map(giftCertificateFromDTO, giftCertificateFromDB);
        giftCertificateFromDB.setLastUpdateDate(LocalDateTime.now());
        if (!tagList.isEmpty()) {
            giftCertificateFromDB.setTagList(tagList);
        }

        GiftCertificate updatedGiftCertificate = giftCertificateRepository.save(giftCertificateFromDB);

        return giftCertificateDTOMapper.convertToDTO(updatedGiftCertificate);
    }

    /**
     * Accesses the corresponding DAO method to delete GiftCertificate object with specific id.
     *
     * @param id GiftCertificate id.
     */
    @Override
    @Transactional
    public void delete(Integer id) {
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateRepository.findById(id);
        optionalGiftCertificate.orElseThrow(() -> new EntityNotFoundException(
                ErrorCodeMessage.ERROR_CODE_GC_NOT_FOUND));
        List<Tag> tagList = optionalGiftCertificate.get().getTagList();
        giftCertificateRepository.deleteById(id);
        deleteTagsIfNotUsed(tagList);
    }

    /**
     * Accesses the corresponding DAO method to find GiftCertificates that matches parameters.
     *
     * @param pageable     object contains page number and page size.
     * @param parameterDTO special object containing requested parameters.
     * @return list of GiftCertificates for the current page.
     */
    @Override
    public Page<GiftCertificateDTO> findByParam(GiftCertificateParamDTO parameterDTO, Pageable pageable) {
        GiftCertificateParam parameter = GiftCertificateParamDTOMapper.convertToEntity(parameterDTO);
        Page<GiftCertificate> giftCertificatePage = giftCertificateRepository.findAll(GiftCertificateSpecification
                .findByParam(parameter), pageable);
        List<GiftCertificate> giftCertificateList = giftCertificatePage.toList();
        return new PageImpl<>(giftCertificateDTOMapper.convertToDTO(giftCertificateList), pageable,
                giftCertificatePage.getTotalElements());
    }

    private List<Tag> returnCreatedOrExistingTags(List<String> tagNamesList) {
        List<Tag> tagList = new ArrayList<>();
        if (tagNamesList == null) {
            return tagList;
        }

        tagNamesList.forEach(tagName -> {
            Optional<Tag> optionalTag = tagRepository.findByName(tagName);

            Tag tagForSave = new Tag();
            tagForSave.setName(tagName);

            Tag tag = optionalTag.orElseGet(() -> tagRepository.save(tagForSave));
            tagList.add(tag);
        });

        return tagList;
    }

    private void deleteTagsIfNotUsed(List<Tag> tagList) {
        for (Tag tag : tagList) {
            if (giftCertificateRepository.findByTagList_NameContaining(tag.getName()).isEmpty()) {
                tagRepository.deleteById(tag.getId());
            }
        }
    }
}