package com.epam.esm;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.assembler.TagModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public TagDTO create(@RequestBody TagDTO tagDTO) {
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

    @GetMapping(params = "page")
    public List<TagDTO> findAll(@RequestParam(name = "page") Integer pageNumber) {
        return tagModelAssembler.toModel(tagService.findAll(pageNumber));
    }

    @GetMapping("/most")
    public TagDTO findMostUsedTagUserWithHighestAmountOrders() {
        return tagModelAssembler.toModel(tagService.findMostUsedTagUserWithHighestAmountOrders());
    }
}
