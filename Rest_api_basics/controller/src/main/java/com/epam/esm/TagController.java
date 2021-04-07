package com.epam.esm;

import com.epam.esm.TagService;
import com.epam.esm.dto.TagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "gift_system/tag")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDTO createTag(@RequestBody TagDTO tagDTO) {
        return tagService.createTag(tagDTO);
    }

    @GetMapping("/{id}")
    public @ResponseBody TagDTO getTagById(@PathVariable Integer id) {
        return tagService.getTagById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable Integer id) {
        tagService.deleteTag(id);
    }

//    @RequestMapping(value = "gift_system/tag/{id}", method = RequestMethod.DELETE)
//    public void deleteTag(@PathVariable Integer id) {
//        tagService.deleteTag(id);
//    }

    @GetMapping
    public List<TagDTO> getAllTags() {
        return tagService.getAllTags();
    }

//    @GetMapping("/{name}")
//    public TagDTO getTagByName(@PathVariable String name) {
//        return tagService.getTagByName(name);
//    }
}
