package gov.iti.jets.repo.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date fireDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private BigDecimal price = new BigDecimal(0);

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.REMOVE)
    private Set<OrderProductEntity> orderProducts = new HashSet<>();

    public Long getId() {
        return id;
    }

    public Set<OrderProductEntity> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(Set<OrderProductEntity> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFireDate() {
        return fireDate;
    }

    public void setFireDate(Date fireDate) {
        this.fireDate = fireDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus orderStatus) {
        this.status = orderStatus;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customerEntity) {
        this.customer = customerEntity;
    }
}
