package kg.alatoo.midterm.services;

import kg.alatoo.midterm.dtos.TagDTO;
import kg.alatoo.midterm.entities.Tag;

import java.util.List;

public interface TagService {
    Tag createTag(TagDTO tagDTO);
    Tag getTagById(Long id);
    List<Tag> getAllTags();
    Tag updateTag(Long id, TagDTO tagDTO);
    void deleteTag(Long id);
}
