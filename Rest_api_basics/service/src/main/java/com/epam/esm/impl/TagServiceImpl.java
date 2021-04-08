package com.epam.esm.impl;

import com.epam.esm.TagService;
import com.epam.esm.constant.ErrorCodeMessage;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.mapper.TagDTOMapper;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.impl.ExistingTagException;
import com.epam.esm.exception.impl.TagInvalidDataException;
import com.epam.esm.exception.impl.TagNotFoundException;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Class implements interface TagService. Describes the service
 * for working with TagDTO.
 *
 * @author Haurylchyk Aliaksei
 * @version 1.0
 * @since JDK 1.8
 */
@Service
public class TagServiceImpl implements TagService {

    /**
     * Object of the TagDAO type.
     */
    private final TagDAO tagDAO;

    /**
     * Constructor with parameter.
     *
     * @param tagDAO interface providing DAO methods.
     */
    @Autowired
    public TagServiceImpl(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }

    /**
     * Accesses the corresponding DAO method to create a new Tag object.
     *
     * @param tagDTO object with Tag data.
     * @return created object with Tag data.
     */
    @Override
    @Transactional
    public TagDTO createTag(TagDTO tagDTO) {
        String tagName = tagDTO.getName();
        if (!TagValidator.isNameValid(tagName)) {
            throw new TagInvalidDataException(ErrorCodeMessage.ERROR_CODE_TAG_INVALID_DATA);
        }
        Optional<Tag> existingTag = tagDAO.getTagByName(tagName);

        if (existingTag.isPresent()) {
            throw new ExistingTagException(ErrorCodeMessage.ERROR_CODE_TAG_EXISTS);
        }

        Tag tag = tagDAO.createTag(tagName);

        return TagDTOMapper.convertToDTO(tag);
    }

    /**
     * Accesses the corresponding DAO method to get Tag object with specific id.
     *
     * @param id Tag id.
     * @return object with Tag data.
     */
    @Override
    public TagDTO getTagById(Integer id) {
        Optional<Tag> optionalTag = tagDAO.getTagById(id);
        if (!optionalTag.isPresent()) {
            throw new TagNotFoundException(ErrorCodeMessage.ERROR_CODE_TAG_BY_ID_NOT_FOUND);
        }
        Tag tag = optionalTag.get();
        return TagDTOMapper.convertToDTO(tag);
    }

    /**
     * Accesses the corresponding DAO method to delete Tag object with specific id.
     *
     * @param id Tag id.
     */
    @Override
    public void deleteTag(Integer id) {
        if (!tagDAO.getTagById(id).isPresent()) {
            throw new TagNotFoundException(ErrorCodeMessage.ERROR_CODE_TAG_BY_ID_NOT_FOUND);
        }
        tagDAO.deleteTag(id);
    }

    /**
     * Accesses the corresponding DAO method to get all Tags.
     *
     * @return List of objects with Tag data.
     */
    @Override
    public List<TagDTO> getAllTags() {
        List<Tag> tagList = tagDAO.getAllTags();
        return TagDTOMapper.convertToDTO(tagList);
    }

    /**
     * Accesses the corresponding DAO method to get Tag object with specific name.
     *
     * @param name Tag name.
     * @return object with Tag data.
     */
    @Override
    public TagDTO getTagByName(String name) {
        Optional<Tag> optionalTag = tagDAO.getTagByName(name);

        if (!optionalTag.isPresent()) {
            throw new TagNotFoundException(ErrorCodeMessage.ERROR_CODE_TAG_BY_NAME_NOT_FOUND);
        }
        Tag tag = optionalTag.get();
        return TagDTOMapper.convertToDTO(tag);
    }

    /**
     * Accesses the corresponding DAO method to get List of all Tags
     * that linked with specific GiftCertificate.
     *
     * @param id GiftCertificate id.
     * @return List of objects with tag data.
     */
    @Override
    public List<TagDTO> getTagsByGiftCertificateId(Integer id) {
        List<Tag> tagList = tagDAO.getTagsByGiftCertificateId(id);
        return TagDTOMapper.convertToDTO(tagList);
    }
}
