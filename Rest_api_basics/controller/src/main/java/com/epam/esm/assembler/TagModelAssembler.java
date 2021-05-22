package com.epam.esm.assembler;

import com.epam.esm.TagController;
import com.epam.esm.TagService;
import com.epam.esm.constant.PaginationConstant;
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

    private static final String LINK_NAME_MOST_USED_TAG = "MostWidelyUsedOfTopOrderUser";
    private static final String LINK_NAME_ALL_TAGS_FIRST = "All Tags - FirstPage";
    private static final String LINK_NAME_ALL_TAGS_LAST = "All Tags - LastPage";
    private static final Integer size = PaginationConstant.DEFAULT_NUMBER_ON_PAGE;
    private static final Integer page = PaginationConstant.DEFAULT_PAGE;

    private final TagService tagService;

    @Autowired
    public TagModelAssembler(TagService tagService) {
        this.tagService = tagService;
    }

    @Override
    public TagDTO toModel(TagDTO tagDTO) {
        tagDTO.add(
                linkTo(methodOn(TagController.class).findById(tagDTO.getId())).withSelfRel(),
                linkTo(methodOn(TagController.class).findMostWidelyUsedOfTopOrderUser()).withRel(LINK_NAME_MOST_USED_TAG),
                linkTo(methodOn(TagController.class).findAll(page, size))
                    .withRel(LINK_NAME_ALL_TAGS_FIRST),
                linkTo(methodOn(TagController.class).findAll(tagService.findNumberPagesForAllTags(size), size))
                    .withRel(LINK_NAME_ALL_TAGS_LAST));
        return tagDTO;
    }

    public List<TagDTO> toModel(List<TagDTO> tagDTOList) {
        return tagDTOList.stream().map(tagDTO -> toModel(tagDTO)).collect(Collectors.toList());
    }
}
