package kg.alatoo.midterm.controllers;

import kg.alatoo.midterm.dtos.TagDTO;
import kg.alatoo.midterm.entities.Tag;
import kg.alatoo.midterm.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @PostMapping
    public Tag createTag(@RequestBody TagDTO tagDTO) {
        return tagService.createTag(tagDTO);
    }

    @GetMapping("/{id}")
    public Tag getTagById(@PathVariable Long id) {
        return tagService.getTagById(id);
    }

    @GetMapping
    public List<Tag> getAllTags() {
        return tagService.getAllTags();
    }

    @PutMapping("/{id}")
    public Tag updateTag(@PathVariable Long id, @RequestBody TagDTO tagDTO) {
        return tagService.updateTag(id, tagDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
    }
}
