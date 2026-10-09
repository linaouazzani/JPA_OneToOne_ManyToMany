package cat.tecnocampus.onetoonemanytomany;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Run these tests with {@code ./mvnw test} (or from your IDE) to check your solution.
 * <p>
 * Note: the demo in OneToOne_ManyToManyApplication also runs when the tests start. Some
 * tests check the data it leaves in the database; the others create their own data.
 */
@SpringBootTest(properties =
        "spring.jpa.properties.hibernate.session_factory.statement_inspector=cat.tecnocampus.onetoonemanytomany.SqlStatementCounter")
class OneToOneManyToManyApplicationTests {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Test
    void contextLoads() {
        // Fails if Product, ProductDetails and Category are not properly mapped as JPA entities
    }

    @Test
    void demoLeavesTheExpectedCategories() {
        assertThat(categoryNamesOf("FM Radio")).containsExactlyInAnyOrder("Radio", "Electronics");
        assertThat(categoryNamesOf("Smart TV")).containsExactlyInAnyOrder("Electronics", "Home");
    }

    @Test
    void sharedCategoriesAreStoredOnlyOnce() {
        Long electronics = entityManager
                .createQuery("select count(c) from Category c where c.name = 'Electronics'", Long.class)
                .getSingleResult();

        assertThat(electronics).isEqualTo(1);
    }

    @Test
    void productDetailsAreSavedWithTheirProduct() {
        String createdBy = transactionTemplate.execute(status -> entityManager
                .createQuery("select p.productDetails.createdBy from Product p where p.name = 'Smart TV'", String.class)
                .getSingleResult());
        String productName = transactionTemplate.execute(status -> entityManager
                .createQuery("select d.product.name from ProductDetails d where d.createdBy = 'Admin'", String.class)
                .getSingleResult());

        assertThat(createdBy).isEqualTo("Website");
        assertThat(productName).isEqualTo("FM Radio");
    }

    @Test
    void removingACategoryOnlyDeletesOneRowOfTheJoinTable() {
        Product product = new Product();
        product.setName("Test product");
        product.setDescription("Product with three categories");
        for (String name : List.of("Test A", "Test B", "Test C")) {
            Category category = new Category();
            category.setName(name);
            product.addCategory(category);
        }
        ProductDetails details = new ProductDetails();
        details.setCreatedBy("Test");
        details.setCreatedOn(LocalDate.now());
        product.setProductDetails(details);
        productRepository.save(product);

        SqlStatementCounter.reset();

        productService.deleteCategoryFromProduct(product.getId(), "Test B");

        assertThat(SqlStatementCounter.deletes())
                .as("deletes when removing one category (hint: List or Set?). Statements: %s",
                        SqlStatementCounter.statements())
                .isEqualTo(1);
        assertThat(SqlStatementCounter.inserts())
                .as("inserts when removing one category (hint: List or Set?). Statements: %s",
                        SqlStatementCounter.statements())
                .isZero();
        assertThat(categoryNamesOf("Test product")).containsExactlyInAnyOrder("Test A", "Test C");
    }

    private List<String> categoryNamesOf(String productName) {
        return entityManager
                .createQuery("select c.name from Product p join p.categories c where p.name = :name", String.class)
                .setParameter("name", productName)
                .getResultList();
    }
}
