package gov.iti.jets.repo.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany(fetch= FetchType.LAZY)
    @JoinTable(name="product_has_categories",
            joinColumns = {@JoinColumn(name="category_id", nullable=false,
                    updatable=false) }, inverseJoinColumns = {
            @JoinColumn(name="product_id", nullable=false, updatable=false)
    })
    private Set<ProductEntity> productEntitySet = new HashSet<>();
    private String category;

    public CategoryEntity(){}

    public CategoryEntity(Long id) {
        this.id = id;
    }

    public CategoryEntity(String category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<ProductEntity> getProductEntitySet() {
        return productEntitySet;
    }

    public void setProductEntitySet(Set<ProductEntity> productEntitySet) {
        this.productEntitySet = productEntitySet;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "CategoryEntity{" +
                "id=" + id +
                ", category='" + category + '\'' +
                '}';
    }
}
