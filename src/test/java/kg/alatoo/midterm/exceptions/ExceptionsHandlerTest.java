package kg.alatoo.midterm.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {ExceptionsHandler.class, TestController.class})
public class ExceptionsHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenGlobalException_thenReturns500() throws Exception {
        mockMvc.perform(get("/test/cause-global-exception"))
               .andExpect(status().isInternalServerError())
               .andExpect(content().string("An error occurred: Unexpected error"));
    }

    @Test
    public void whenIllegalArgumentException_thenReturns400() throws Exception {
        mockMvc.perform(get("/test/cause-illegal-argument"))
               .andExpect(status().isBadRequest())
               .andExpect(content().string("Invalid argument provided"));
    }

    @Test
    public void whenResourceNotFoundException_thenReturns404() throws Exception {
        mockMvc.perform(get("/test/cause-resource-not-found"))
               .andExpect(status().isNotFound())
               .andExpect(content().string("Resource not found"));
    }
}
