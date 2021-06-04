package com.epam.esm.impl;

import com.epam.esm.TagService;
import com.epam.esm.constant.ErrorCodeMessage;
import com.epam.esm.dao.TagRepository;
import com.epam.esm.dao.UserRepository;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exception.impl.EntityNotFoundException;
import com.epam.esm.exception.impl.ExistingTagException;
import com.epam.esm.exception.impl.InvalidDataException;
import com.epam.esm.exception.impl.NotExistingPageException;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.dto.mapper.TagDTOMapper;
import com.epam.esm.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
     * The index of the first item in the list.
     */
    private static final int FIRST_ELEMENT_INDEX = 0;

    /**
     * The request to get a page containing only the first item in the list
     */
    PageRequest PAGE_WITH_FIRST_ELEMENT = PageRequest.of(0,1);

    /**
     * Object of the TagDAO type.
     */
    private final TagRepository tagRepository;

    /**
     * Object of the UserDAO type.
     */
    private final UserRepository userRepository;

    /**
     * Constructor with parameter.
     *
     * @param tagRepository interface providing DAO methods.
     */
    @Autowired
    public TagServiceImpl(TagRepository tagRepository, UserRepository userRepository) {
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
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
            throw new InvalidDataException(ErrorCodeMessage.ERROR_CODE_TAG_INVALID_DATA);
        }
        Optional<Tag> optionalExistingTag = tagRepository.findByName(tagName);
        if (optionalExistingTag.isPresent()) {
            throw new ExistingTagException(ErrorCodeMessage.ERROR_CODE_TAG_EXISTS);
        }
        Tag newTag = tagRepository.save(tag);
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
        Optional<Tag> optionalTag = tagRepository.findById(id);
        Tag tag = optionalTag.orElseThrow(() -> new EntityNotFoundException(
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
        Optional<Tag> optionalTag = tagRepository.findById(id);
        optionalTag.orElseThrow(() -> new EntityNotFoundException(
                ErrorCodeMessage.ERROR_CODE_TAG_NOT_FOUND));
        tagRepository.deleteById(id);
    }

    /**
     * Accesses the corresponding DAO method to find all Tags.
     *
     * @param pageable object contains page number and page size.
     * @return List of objects with Tag data.
     */
    @Override
    public Page<TagDTO> findAll(Pageable pageable) {
        Page<Tag> tagPage = tagRepository.findAll(pageable);
        List<Tag> tagList = tagPage.toList();
        if (tagList.isEmpty()) {
            throw new NotExistingPageException(ErrorCodeMessage.ERROR_CODE_PAGE_NOT_FOUND);
        }
        return new PageImpl<>(TagDTOMapper.convertToDTO(tagList), pageable, tagPage.getTotalElements());
    }

    /**
     * Accesses the corresponding DAO method to find most widely used Tag for
     * the user with the highest cost of all orders.
     *
     * @return object with Tag data.
     */
    @Override
    public TagDTO findMostWidelyUsedOfTopOrderUser() {
        List<User> userList = userRepository.findUserWithTopOrders(PAGE_WITH_FIRST_ELEMENT).toList();
        if (userList.isEmpty()) {
            new EntityNotFoundException(ErrorCodeMessage.ERROR_CODE_USER_NOT_FOUND);
        }
        User user = userList.get(FIRST_ELEMENT_INDEX);
        Tag mostWidelyUsedTag = tagRepository.findMostWidelyUsedByUserId(user.getId(), PAGE_WITH_FIRST_ELEMENT)
                .get(FIRST_ELEMENT_INDEX);
        return TagDTOMapper.convertToDTO(mostWidelyUsedTag);
    }
}