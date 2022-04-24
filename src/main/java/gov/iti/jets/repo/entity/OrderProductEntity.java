package gov.iti.jets.repo.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "order_has_products")
public class OrderProductEntity implements Serializable {
    @EmbeddedId
    private OrderProductIdEntity id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, insertable = false, updatable = false)
    private OrderEntity order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, insertable = false, updatable = false)
    private ProductEntity product;

    private Long quantity;

    public OrderProductIdEntity getId() {
        return id;
    }

    public void setId(OrderProductIdEntity id) {
        this.id = id;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity productEntity) {
        this.product = productEntity;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
