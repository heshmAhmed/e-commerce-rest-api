package gov.iti.jets.repo;

import gov.iti.jets.repo.entity.CustomerEntity;
import gov.iti.jets.repo.entity.OrderEntity;
import gov.iti.jets.repo.entity.OrderProductEntity;
import gov.iti.jets.repo.entity.OrderStatus;
import gov.iti.jets.repo.util.EntityManagerProvider;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class OrderRepo {
    private static final OrderRepo orderRepo = new OrderRepo();
    private final EntityManager entityManager = EntityManagerProvider.INSTANCE.getEntityManager();
    private OrderRepo() {}
    public static OrderRepo getInstance() {
        return orderRepo;
    }

    public Optional<OrderEntity> findOrderById(Long id) {
        OrderEntity orderEntity = entityManager.find(OrderEntity.class, id);
        if(orderEntity == null)
            return Optional.empty();
        return Optional.of(orderEntity);
    }
    public List<OrderEntity> getAllOrders() {
        return entityManager.createQuery("from OrderEntity ", OrderEntity.class).getResultList();
    }
    public OrderEntity insertOrder(OrderEntity order) {
        return null;
    }


    public boolean updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("update OrderEntity set status = :status where id = :id")
                .setParameter("status", orderStatus)
                .setParameter("id", orderId);
        boolean rowUpdated = query.executeUpdate() > 0;
        entityManager.getTransaction().commit();
        return rowUpdated;
    }

//    public boolean convertCartToOrder(Long cartId){
//        entityManager.getTransaction().begin();
//        Query query = entityManager.createQuery("update OrderEntity set isSubmitted = true where id = :id")
//                .setParameter("id", cartId);
//        boolean rowUpdated = query.executeUpdate() > 0;
//        entityManager.getTransaction().commit();
//        return rowUpdated;
//    }

    public boolean deleteOrder(Long id) {
        OrderEntity orderEntity = entityManager.find(OrderEntity.class, id);
        if(orderEntity == null)
            return false;
        entityManager.getTransaction().begin();
        entityManager.remove(orderEntity);
        entityManager.getTransaction().commit();
        return true;
    }

    public List<OrderEntity> getCustomerOrders(Long id) {
        return entityManager.createQuery("from OrderEntity where customer.id = :id", OrderEntity.class)
                .setParameter("id", id).getResultList();
    }

    public Optional<OrderEntity> getCustomerOrder(Long customerId, Long orderId) {
        Optional<OrderEntity> optionalOrderEntity = Optional.empty();
        try {
            OrderEntity orderEntity =
                    entityManager.createQuery("from OrderEntity where  customer.id = :customerId and id = :orderId"
                            ,OrderEntity.class)
                            .setParameter("customerId", customerId)
                            .setParameter("orderId", orderId)
                            .getSingleResult();
            optionalOrderEntity = Optional.of(orderEntity);
        }catch (NoResultException e) {
            e.printStackTrace();
        }
        return optionalOrderEntity;
    }

    public Optional<OrderEntity> createOrder(Long customerId, OrderEntity orderEntity) {
        Optional<OrderEntity> optionalOrderEntity = Optional.empty();
        Set<OrderProductEntity> orderProductEntities = new HashSet<>(orderEntity.getOrderProducts());
        orderEntity.setOrderProducts(null);
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(customerId);
        orderEntity.setCustomer(customerEntity);
        try {
            entityManager.getTransaction().begin();
            orderEntity = entityManager.merge(orderEntity);

            for (OrderProductEntity orderProductEntity : orderProductEntities) {
                orderProductEntity.getId().setOrderId(orderEntity.getId());
                orderProductEntity.getId().setProductId(orderProductEntity.getId().getProductId());
                entityManager.merge(orderProductEntity);
            }
            entityManager.getTransaction().commit();
            orderEntity.setOrderProducts(orderProductEntities);
            optionalOrderEntity = Optional.of(orderEntity);
            entityManager.clear();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return optionalOrderEntity;
    }

    public boolean cancelOrder(Long customerId, Long orderId) {
        boolean cancelled = false;
        try {
            entityManager.getTransaction().begin();
            OrderEntity orderEntity =
                    entityManager.createQuery("from OrderEntity where customer.id = :cid and id = :oid", OrderEntity.class)
                            .setParameter("cid", customerId)
                            .setParameter("oid", orderId)
                            .getSingleResult();
            orderEntity.setStatus(OrderStatus.CANCELLED);
            entityManager.merge(orderEntity);
            entityManager.getTransaction().commit();
             cancelled = true;
        }catch (NoResultException e) {
            e.printStackTrace();
        }
        return cancelled;
    }
}
