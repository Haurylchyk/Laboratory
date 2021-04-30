package com.epam.esm.impl;

import com.epam.esm.TagService;
import com.epam.esm.constant.ErrorCodeMessage;
import com.epam.esm.constant.PaginationConstant;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.mapper.TagDTOMapper;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
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
     * Object of the UserDAO type.
     */
    private final UserDAO userDAO;

    /**
     * Constructor with parameter.
     *
     * @param tagDAO interface providing DAO methods.
     */
    @Autowired
    public TagServiceImpl(TagDAO tagDAO, UserDAO userDAO) {

        this.tagDAO = tagDAO;
        this.userDAO = userDAO;
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
        Optional<Tag> optionalExistingTag = tagDAO.findByName(tagName);
        if (optionalExistingTag.isPresent()) {
            throw new ExistingTagException(ErrorCodeMessage.ERROR_CODE_TAG_EXISTS);
        }
        Tag newTag = tagDAO.save(tag);
        return TagDTOMapper.convertToDTO(newTag);
    }

    /**
     * Accesses the corresponding DAO method to find Tag object with specific id.
     *
     * @param id Tag id.
     * @return object with Tag data.
     */
    @Override
    public TagDTO findById(Integer id) {
        Optional<Tag> optionalTag = tagDAO.find(id);
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
        Optional<Tag> optionalTag = tagDAO.find(id);
        optionalTag.orElseThrow(() -> new TagNotFoundException(
                ErrorCodeMessage.ERROR_CODE_TAG_NOT_FOUND));
        tagDAO.delete(id);
    }

    /**
     * Accesses the corresponding DAO method to find all Tags.
     *
     * @return List of objects with Tag data.
     */
    @Override
    public List<TagDTO> findAll(Integer pageNumber) {
        List<Tag> tagList = tagDAO.findAll(pageNumber);
        if (tagList.isEmpty()) {
            throw new TagNotFoundException(ErrorCodeMessage.ERROR_CODE_TAG_NOT_FOUND);
        }
        return TagDTOMapper.convertToDTO(tagList);
    }

    /**
     * Accesses the corresponding DAO method to find most widely used Tag for
     * the user with the highest cost of all orders.
     *
     * @return object with Tag data.
     */
    public TagDTO findMostWidelyUsedOfTopOrderUser() {
        User mostRichUser = userDAO.findUserWithTopOrders();
        Tag mostWidelyUsedTag = tagDAO.findMostWidelyUsedByUserId(mostRichUser.getId());

        return TagDTOMapper.convertToDTO(mostWidelyUsedTag);
    }

    /**
     * Calculates the total number of pages required to display all Tags.
     *
     * @return the total number of pages required to display all Tags.
     */
    public Long findNumberPagesForAllTags() {
        Long totalNumberTags = tagDAO.findTotalNumberTags();
        int number = PaginationConstant.TAG_NUMBER_ON_PAGE;
        return totalNumberTags % number == 0 ? totalNumberTags / number : totalNumberTags / number + 1;
    }
}