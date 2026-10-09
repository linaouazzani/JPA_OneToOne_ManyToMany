package cat.tecnocampus.onetoonemanytomany;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OneToOne_ManyToManyApplication {

    public static void main(String[] args) {
        SpringApplication.run(OneToOne_ManyToManyApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(ProductService productService) {
        return args -> {
            productService.createProductsDetailsAndCategories();
            productService.printAllProducts();

            productService.deleteCategoryFromProduct(1L, "Home");
            productService.printAllProducts();
        };
    }
}
