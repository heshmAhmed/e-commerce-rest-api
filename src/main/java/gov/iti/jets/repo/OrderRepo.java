package gov.iti.jets.repo;

import gov.iti.jets.repo.entity.OrderEntity;
import gov.iti.jets.repo.entity.OrderStatus;
import gov.iti.jets.repo.util.EntityManagerProvider;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public class OrderRepo {
    private static final OrderRepo orderRepo = new OrderRepo();
    private final EntityManager entityManager = EntityManagerProvider.INSTANCE.getEntityManager();
    private OrderRepo() {}
    public static OrderRepo getInstance() {
        return orderRepo;
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

    public boolean convertCartToOrder(Long cartId){
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("update OrderEntity set isSubmitted = true where id = :id")
                .setParameter("id", cartId);
        boolean rowUpdated = query.executeUpdate() > 0;
        entityManager.getTransaction().commit();
        return rowUpdated;
    }
}
