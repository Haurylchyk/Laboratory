package com.epam.esm.impl;

import com.epam.esm.GiftCertificateService;
import com.epam.esm.constant.ErrorCodeMessage;
import com.epam.esm.dao.GiftCertificateRepository;
import com.epam.esm.dao.TagRepository;
import com.epam.esm.dao.query.parameter.GiftCertificateParam;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.impl.EntityNotFoundException;
import com.epam.esm.exception.impl.InvalidDataException;
import com.epam.esm.exception.impl.NotExistingPageException;
import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.model.dto.GiftCertificateParamDTO;
import com.epam.esm.model.dto.mapper.GiftCertificateDTOMapper;
import com.epam.esm.model.dto.mapper.GiftCertificateParamDTOMapper;
import com.epam.esm.validator.GiftCertificateValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    private ModelMapper modelMapper;

    /**
     * Constructor with parameter.
     *
     * @param giftCertificateRepository interface for working with
     *                                  the corresponding DAO methods.
     * @param tagRepository             interface for working with
     *                                  the corresponding DAO methods.
     */
    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository, TagRepository tagRepository, ModelMapper modelMapper) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.tagRepository = tagRepository;
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

        GiftCertificate newGiftCertificate = giftCertificateRepository.save(giftCertificate);

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
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateRepository.findById(id);
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
        Optional<GiftCertificate> optionalGiftCertificateFromDB = giftCertificateRepository.findById(id);
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

        GiftCertificate updatedGiftCertificate = giftCertificateRepository.save(giftCertificateFromDB);

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
        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateRepository.findById(id);
        optionalGiftCertificate.orElseThrow(() -> new EntityNotFoundException(
                ErrorCodeMessage.ERROR_CODE_GC_NOT_FOUND));
        List<Tag> tagList = tagRepository.findByGiftCertificateId(id);
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
    public Page<GiftCertificateDTO> findByParam(Pageable pageable, GiftCertificateParamDTO parameterDTO) {
        Integer count = 0;
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        GiftCertificateParam parameter = GiftCertificateParamDTOMapper.convertToEntity(parameterDTO);
        Map<Integer, List<GiftCertificate>> giftCertificatesMap = giftCertificateRepository.findByParam(pageable.getPageNumber(), pageable.getPageSize(), parameter);
        for (Map.Entry<Integer, List<GiftCertificate>> entry : giftCertificatesMap.entrySet()) {
            count = entry.getKey();
            giftCertificates = entry.getValue();
        }
        if (giftCertificates.isEmpty()) {
            throw new NotExistingPageException(ErrorCodeMessage.ERROR_CODE_PAGE_NOT_FOUND);
        }
        List<GiftCertificateDTO> giftCertificatesDTO = new ArrayList<>();

        for (GiftCertificate giftCertificate : giftCertificates) {
            List<Tag> tagList = tagRepository.findByGiftCertificateId(giftCertificate.getId());
            giftCertificate.setTagList(tagList);
            giftCertificatesDTO.add(GiftCertificateDTOMapper.convertToDTO(giftCertificate));
        }
        return new PageImpl<>(giftCertificatesDTO, pageable, count);
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