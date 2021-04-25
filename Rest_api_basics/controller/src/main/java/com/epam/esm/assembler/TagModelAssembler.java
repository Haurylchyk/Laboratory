package com.epam.esm.assembler;

import com.epam.esm.TagController;
import com.epam.esm.TagService;
import com.epam.esm.dto.TagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagModelAssembler implements RepresentationModelAssembler<TagDTO, TagDTO> {

    private static final String LINK_NAME_FOR_FIND_MOST_USED_TAG = "MostUsedTagUserWithHighestAmountOrders";
    private static final String LINK_NAME_FOR_FIND_ALL_TAGS = "AllTags";

    private final TagService tagService;

    @Autowired
    public TagModelAssembler(TagService tagService) {
        this.tagService = tagService;
    }

    @Override
    public TagDTO toModel(TagDTO tagDTO) {
        tagDTO.add(
                linkTo(methodOn(TagController.class).findById(tagDTO.getId())).withSelfRel(),
                linkTo(methodOn(TagController.class).findMostUsedTagUserWithHighestAmountOrders())
                        .withRel(LINK_NAME_FOR_FIND_MOST_USED_TAG));
        for (int i = 1; i <= tagService.findNumberPagesForAllTags() ; i++) {
            tagDTO.add(linkTo(methodOn(TagController.class).findAll(i)).withRel(LINK_NAME_FOR_FIND_ALL_TAGS));
        }
        return tagDTO;
    }

    public List<TagDTO> toModel(List<TagDTO> tagDTOList) {
        return tagDTOList.stream().map(tagDTO -> toModel(tagDTO)).collect(Collectors.toList());
    }
}
