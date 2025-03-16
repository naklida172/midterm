package kg.alatoo.midterm.exceptions;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/cause-resource-not-found")
    public void triggerResourceNotFoundException() {
        throw new ResourceNotFoundException("Resource not found");
    }

    @GetMapping("/cause-illegal-argument")
    public void triggerIllegalArgumentException() {
        throw new IllegalArgumentException("Invalid argument provided");
    }

    @GetMapping("/cause-global-exception")
    public void triggerGlobalException() {
        throw new RuntimeException("Unexpected error");
    }
}
