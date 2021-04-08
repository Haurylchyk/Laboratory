package com.epam.esm.dto.mapper;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * The class converts Tag to TagDTO and vice versa.
 */
public class TagDTOMapper {

    private TagDTOMapper() {
    }

    /**
     * Converts TagDTO to Tag.
     *
     * @param tagDTO object of TagDTO type.
     * @return object of Tag type.
     */
    public static Tag convertToEntity(TagDTO tagDTO) {
        Tag tag = new Tag();
        tag.setId(tagDTO.getId());
        tag.setName(tagDTO.getName());
        return tag;
    }

    /**
     * Converts Tag to TagDTO.
     *
     * @param tag object of Tag type.
     * @return object of TagDTO type.
     */
    public static TagDTO convertToDTO(Tag tag) {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setId(tag.getId());
        tagDTO.setName(tag.getName());
        return tagDTO;
    }

    /**
     * Converts list of Tags to list of TagDTOs.
     *
     * @param tagList list of Tags.
     * @return list of TagDTOs.
     */
    public static List<TagDTO> convertToDTO(List<Tag> tagList) {
        List<TagDTO> tagDTOList = new ArrayList<>();
        tagList.forEach(tag -> tagDTOList.add(convertToDTO(tag)));
        return tagDTOList;
    }

    /**
     * Converts list of TagDTOs to list of Tags.
     *
     * @param tagDTOList list of TagDTOs.
     * @return list of Tags.
     */
    public static List<Tag> convertToEntity(List<TagDTO> tagDTOList) {
        List<Tag> tagList = new ArrayList<>();
        tagDTOList.forEach(tagDTO -> tagList.add(convertToEntity(tagDTO)));
        return tagList;
    }
}
