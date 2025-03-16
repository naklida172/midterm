package kg.alatoo.midterm.controllers;

import kg.alatoo.midterm.dtos.TagDTO;
import kg.alatoo.midterm.entities.Tag;
import kg.alatoo.midterm.services.TagService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TagController.class)
public class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TagService tagService;

    @Test
    public void testGetAllTags() throws Exception {
        Tag tag1 = new Tag();
        tag1.setId(1L);
        tag1.setName("Electronics");
        tag1.setDescription("Electronic gadgets");

        Tag tag2 = new Tag();
        tag2.setId(2L);
        tag2.setName("Gadgets");
        tag2.setDescription("Latest gadgets");

        List<Tag> tags = Arrays.asList(tag1, tag2);

        when(tagService.getAllTags()).thenReturn(tags);

        mockMvc.perform(get("/api/tags")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].name", is("Electronics")))
                .andExpect(jsonPath("$[1].name", is("Gadgets")));

        verify(tagService, times(1)).getAllTags();
    }

    @Test
    public void testGetTagById() throws Exception {
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("Electronics");
        tag.setDescription("Electronic gadgets");

        when(tagService.getTagById(1L)).thenReturn(tag);

        mockMvc.perform(get("/api/tags/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Electronics")))
                .andExpect(jsonPath("$.description", is("Electronic gadgets")));

        verify(tagService, times(1)).getTagById(1L);
    }

    @Test
    public void testCreateTag() throws Exception {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setName("NewTag");
        tagDTO.setDescription("Description of NewTag");

        Tag createdTag = new Tag();
        createdTag.setId(1L);
        createdTag.setName("NewTag");
        createdTag.setDescription("Description of NewTag");

        when(tagService.createTag(Mockito.any(TagDTO.class))).thenReturn(createdTag);

        mockMvc.perform(post("/api/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"NewTag\", \"description\": \"Description of NewTag\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("NewTag")))
                .andExpect(jsonPath("$.description", is("Description of NewTag")));

        verify(tagService, times(1)).createTag(Mockito.any(TagDTO.class));
    }

    @Test
    public void testUpdateTag() throws Exception {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setName("UpdatedTag");
        tagDTO.setDescription("Updated description");

        Tag updatedTag = new Tag();
        updatedTag.setId(1L);
        updatedTag.setName("UpdatedTag");
        updatedTag.setDescription("Updated description");

        when(tagService.updateTag(eq(1L), Mockito.any(TagDTO.class))).thenReturn(updatedTag);

        mockMvc.perform(put("/api/tags/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"UpdatedTag\", \"description\": \"Updated description\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("UpdatedTag")))
                .andExpect(jsonPath("$.description", is("Updated description")));

        verify(tagService, times(1)).updateTag(eq(1L), Mockito.any(TagDTO.class));
    }

    @Test
    public void testDeleteTag() throws Exception {
        doNothing().when(tagService).deleteTag(1L);

        mockMvc.perform(delete("/api/tags/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(tagService, times(1)).deleteTag(1L);
    }
}