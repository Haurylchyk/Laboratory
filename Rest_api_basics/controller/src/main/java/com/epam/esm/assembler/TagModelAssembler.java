package com.epam.esm.assembler;

import com.epam.esm.TagController;
import com.epam.esm.model.dto.TagDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagModelAssembler implements RepresentationModelAssembler<TagDTO, TagDTO> {

    private static final String LINK_NAME_MOST_USED_TAG = "MostWidelyUsedOfTopOrderUser";
    private static final String LINK_NAME_ALL_TAGS = "All Tags";

    @Override
    public TagDTO toModel(TagDTO tagDTO) {
        tagDTO.add(
                linkTo(methodOn(TagController.class).findById(tagDTO.getId())).withSelfRel(),
                linkTo(methodOn(TagController.class).findMostWidelyUsedOfTopOrderUser()).withRel(LINK_NAME_MOST_USED_TAG),
                linkTo(methodOn(TagController.class).findAll(Pageable.unpaged(), null)).withRel(LINK_NAME_ALL_TAGS)
        );
        return tagDTO;
    }

    public List<TagDTO> toModel(List<TagDTO> tagDTOList) {
        return tagDTOList.stream().map(tagDTO -> toModel(tagDTO)).collect(Collectors.toList());
    }
}
