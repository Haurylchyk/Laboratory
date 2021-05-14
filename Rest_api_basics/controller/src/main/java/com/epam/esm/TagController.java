package com.epam.esm;

import com.epam.esm.assembler.TagModelAssembler;
import com.epam.esm.dto.TagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
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
    public void delete(@PathVariable Integer id) {
        tagService.delete(id);
    }

    @GetMapping
    public List<TagDTO> findAll(@RequestParam(required = false, defaultValue = "1") @Min(1) Integer page,
                                @RequestParam(required = false, defaultValue = "1") @Min(1) Integer size) {
        return tagModelAssembler.toModel(tagService.findAll(page, size));
    }

    @GetMapping("/most-widely-used-of-top-order-user")
    public TagDTO findMostWidelyUsedOfTopOrderUser() {
        return tagModelAssembler.toModel(tagService.findMostWidelyUsedOfTopOrderUser());
    }
}
