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
    public TagDTO create(TagDTO tagDTO) {
        Tag tag = TagDTOMapper.convertToEntity(tagDTO);
        String tagName = tagDTO.getName();
        if (!TagValidator.isNameValid(tagName)) {
            throw new TagInvalidDataException(ErrorCodeMessage.ERROR_CODE_TAG_INVALID_DATA);
        }
        Optional<Tag> optionalExistingTag = tagDAO.readTagByName(tagName);
        if (optionalExistingTag.isPresent()) {
            throw new ExistingTagException(ErrorCodeMessage.ERROR_CODE_TAG_EXISTS);
        }
        Tag newTag = tagDAO.create(tag);
        return TagDTOMapper.convertToDTO(newTag);
    }

    /**
     * Accesses the corresponding DAO method to get Tag object with specific id.
     *
     * @param id Tag id.
     * @return object with Tag data.
     */
    @Override
    public TagDTO read(Integer id) {
        Optional<Tag> optionalTag = tagDAO.read(id);
        Tag tag = optionalTag.orElseThrow(() -> new TagNotFoundException(
                ErrorCodeMessage.ERROR_CODE_TAG_NOT_FOUND));
        return TagDTOMapper.convertToDTO(tag);
    }

    /**
     * Accesses the corresponding DAO method to delete Tag object with specific id.
     *
     * @param id Tag id.
     */
    @Override
    public void delete(Integer id) {
        Optional<Tag> optionalTag = tagDAO.read(id);
        optionalTag.orElseThrow(() -> new TagNotFoundException(
                ErrorCodeMessage.ERROR_CODE_TAG_NOT_FOUND));
        tagDAO.delete(id);
    }

    /**
     * Accesses the corresponding DAO method to get all Tags.
     *
     * @return List of objects with Tag data.
     */
    @Override
    public List<TagDTO> readAllTags() {
        List<Tag> tagList = tagDAO.readAllTags();
        if (tagList.isEmpty()) {
            throw new TagNotFoundException(ErrorCodeMessage.ERROR_CODE_TAG_NOT_FOUND);
        }
        return TagDTOMapper.convertToDTO(tagList);
    }
}
