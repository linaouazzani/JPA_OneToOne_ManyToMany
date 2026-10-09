package cat.tecnocampus.onetoonemanytomany;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public void createProductsDetailsAndCategories() {
        Product product1 = new Product();
        product1.setName("FM Radio");
        product1.setDescription("Stereo FM radio.");

        Product product2 = new Product();
        product2.setName("Smart TV");
        product2.setDescription("55 inch 4K Smart TV.");

        Category category = new Category();
        category.setName("Radio");
        product1.addCategory(category);

        Category category2 = new Category();
        category2.setName("Electronics");
        product1.addCategory(category2);
        product2.addCategory(category2);

        Category category3 = new Category();
        category3.setName("Home");
        product1.addCategory(category3);
        product2.addCategory(category3);

        ProductDetails details1 = new ProductDetails();
        details1.setCreatedBy("Admin");
        details1.setCreatedOn(java.time.LocalDate.now());
        product1.setProductDetails(details1);

        ProductDetails details2 = new ProductDetails();
        details2.setCreatedBy("Website");
        details2.setCreatedOn(java.time.LocalDate.now());
        product2.setProductDetails(details2);

        productRepository.save(product1);
        productRepository.save(product2);
    }

    @Transactional
    public void deleteCategoryFromProduct(Long productId, String categoryName) {
        Product product = productRepository.findById(productId).orElseThrow();
        Category category = new Category();
        category.setName(categoryName);

        product.removeCategory(category);
        productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public void printAllProducts() {
        List<Product> products = productRepository.findAll();
        products.forEach(System.out::println);
    }
}
