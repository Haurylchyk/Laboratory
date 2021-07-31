package com.epam.esm;

import com.epam.esm.assembler.TagModelAssembler;
import com.epam.esm.model.dto.TagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/tags")
public class TagController {

    private final TagService tagService;
    private final TagModelAssembler tagModelAssembler;

    @Autowired
    public TagController(TagService tagService, TagModelAssembler tagModelAssembler) {
        this.tagService = tagService;
        this.tagModelAssembler = tagModelAssembler;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDTO create(@Valid @RequestBody TagDTO tagDTO) {
        return tagModelAssembler.toModel(tagService.create(tagDTO));
    }

    @GetMapping("/{id}")
    public TagDTO findById(@PathVariable Integer id) {
        return tagModelAssembler.toModel(tagService.findById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        tagService.delete(id);
    }

    @GetMapping
    public PagedModel<TagDTO> findAll(Pageable pageable, PagedResourcesAssembler assembler) {
        Page<TagDTO> tagDTOPage = tagService.findAll(pageable);
        tagDTOPage.get().forEach(tagDTO -> tagModelAssembler.toModel(tagDTO));
        return assembler.toModel(tagDTOPage);
    }

    @GetMapping("/most-widely-used-of-top-order-user")
    public TagDTO findMostWidelyUsedOfTopOrderUser() {
        return tagModelAssembler.toModel(tagService.findMostWidelyUsedOfTopOrderUser());
    }
}
