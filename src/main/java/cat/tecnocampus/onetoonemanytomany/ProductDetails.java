package cat.tecnocampus.onetoonemanytomany;

import jakarta.persistence.*;

import java.time.LocalDate;

public class ProductDetails {
    private Long id;
    private String createdBy;
    private LocalDate createdOn;

    private Product product;

    public ProductDetails() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "ProductDetails{" +
                "id=" + id +
                ", createdBy='" + createdBy + '\'' +
                ", createdOn=" + createdOn +
                ", product name=" + product.getName() +
                '}';
    }
}
