package gov.iti.jets.repo.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "product_name")
    private String name;
    @Column(name = "product_desc")
    private String desc;
    private String img;
    private Long stock;
    private Double price = 0.0;
    @ManyToMany(fetch= FetchType.LAZY)
    @JoinTable(name="product_has_categories",
            joinColumns = {@JoinColumn(name="product_id", nullable=false,
                    updatable=false) }, inverseJoinColumns = {
            @JoinColumn(name="category_id", nullable=false, updatable=false)
    })
    private Set<CategoryEntity> categories = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Set<CategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryEntity> categories) {
        this.categories = categories;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", img='" + img + '\'' +
                ", stock=" + stock +
                ", price=" + price +
                ", categories=" + categories +
                '}';
    }
}
