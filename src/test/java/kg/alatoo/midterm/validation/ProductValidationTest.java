package kg.alatoo.midterm.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import kg.alatoo.midterm.entities.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductValidationTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void whenNameExceedsMaxLength_thenValidationFails() {
        Product product = Product.builder()
                .name("A".repeat(101)) 
                .description("Valid description")
                .rating((short) 5)
                .build();

        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        assertEquals(1, violations.size());
        ConstraintViolation<Product> violation = violations.iterator().next();
        assertEquals("Name must not exceed 100 characters", violation.getMessage());
    }

    @Test
    public void whenDescriptionExceedsMaxLength_thenValidationFails() {
        Product product = Product.builder()
                .name("Valid Name")
                .description("A".repeat(501))
                .rating((short) 5)
                .build();

        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        assertEquals(1, violations.size());
        ConstraintViolation<Product> violation = violations.iterator().next();
        assertEquals("Description must not exceed 500 characters", violation.getMessage());
    }

    @Test
    public void whenAllFieldsAreValid_thenNoViolations() {
        Product product = Product.builder()
                .name("Valid Name")
                .description("Valid description")
                .rating((short) 5)
                .build();

        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void whenNameIsNull_thenNoViolations() {
        Product product = Product.builder()
                .name(null)
                .description("Valid description")
                .rating((short) 5)
                .build();

        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        assertTrue(violations.isEmpty()); 
    }

    @Test
    public void whenDescriptionIsNull_thenNoViolations() {
        Product product = Product.builder()
                .name("Valid Name")
                .description(null) 
                .rating((short) 5)
                .build();

        Set<ConstraintViolation<Product>> violations = validator.validate(product);

        assertTrue(violations.isEmpty()); 
    }
}
