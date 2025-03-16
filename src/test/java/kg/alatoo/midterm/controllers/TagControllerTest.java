package kg.alatoo.midterm.controllers;

import kg.alatoo.midterm.entities.Tag;
import kg.alatoo.midterm.services.TagService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
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
}