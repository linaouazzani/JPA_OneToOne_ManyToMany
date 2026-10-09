# Exercise for experts in @OneToOne and @ManyToMany

In this project, you have three classes, Product, ProductDetails and Category. A product has a mandatory ProductDetails and may have several 
categories. A category may have several products.

In the file "OneToOne_ManyToManyApplication", two products with categories and details are created and printed. Then, a category from a product 
is removed, and printed again. When removed, make sure that the JPA/Hibernate does not delete all the product categories and then insert again the ones that
must remain.

You can see the JPA/Hibernate queries in the console. We have configured the Spring Boot to log the JPA activity, see 
the application.properties file.

The exercise consists of making the OneToOne_ManyToManyApplication work properly by adding the @OneToOne and @ManyToMany relationships. It is not
mandatory, but you could follow these articles by Vlad Mihalcea step by step and observe the behaviour of the JPA:

- [The best way to map a @OneToOne relationship with JPA and Hibernate](https://vladmihalcea.com/the-best-way-to-map-a-onetoone-relationship-with-jpa-and-hibernate/)
- [Best way to map the JPA and Hibernate ManyToMany relationship](https://vladmihalcea.com/the-best-way-to-use-the-manytomany-annotation-with-jpa-and-hibernate/)

## Check your solution

The project includes tests that check your solution. Run them with:

```
./mvnw test
```

(`mvnw.cmd test` on Windows), or run the test class `OneToOneManyToManyApplicationTests` from your IDE.
All the tests must pass. If one fails, its message shows the SQL statements Hibernate executed,
which helps you find what is wrong.

Requirements: Java 25 and Spring Boot 4.1.
