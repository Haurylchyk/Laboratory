package com.epam.esm;

import com.epam.esm.dto.TagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/tags")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDTO create(@RequestBody TagDTO tagDTO) {
        return tagService.create(tagDTO);
    }

    @GetMapping("/{id}")
    public TagDTO read(@PathVariable Integer id) {
        return tagService.read(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        tagService.delete(id);
    }

    @GetMapping
    public List<TagDTO> readAllTags() {
        return tagService.readAllTags();
    }

}
