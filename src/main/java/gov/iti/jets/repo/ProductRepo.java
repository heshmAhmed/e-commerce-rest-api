package gov.iti.jets.repo;

import gov.iti.jets.repo.entity.ProductEntity;
import gov.iti.jets.repo.util.EntityManagerProvider;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Optional;

public class ProductRepo {
    private final EntityManager entityManager = EntityManagerProvider.INSTANCE.getEntityManager();
    private static final ProductRepo productRepo = new ProductRepo();
    private ProductRepo() {};
    public static ProductRepo getInstance() {
        return productRepo;
    }

    public ProductEntity insertProduct(ProductEntity productEntity) {
        entityManager.getTransaction().begin();
        entityManager.persist(productEntity);
        entityManager.getTransaction().commit();
        return productEntity;
    }

    public ProductEntity updateProduct(ProductEntity productEntity) {
        entityManager.getTransaction().begin();
        productEntity = entityManager.merge(productEntity);
        entityManager.getTransaction().commit();
        return productEntity;
    }

    public boolean deleteProduct(Long id) {
        entityManager.getTransaction().begin();
        int rowUpdate = entityManager.createQuery("delete from ProductEntity where id = :id").setParameter("id", id).executeUpdate();
        entityManager.getTransaction().commit();
        return rowUpdate > 0;
    }

    public List<ProductEntity> getAllProduct(int page, int rpp) {
        Query query = entityManager.createQuery("from ProductEntity", ProductEntity.class);
        query.setFirstResult((page-1) * rpp);
        query.setMaxResults(page);
        return query.getResultList();
    }

    public Optional<ProductEntity> findProductById(Long id) {
        Optional<ProductEntity> optionalProduct = Optional.empty();
        ProductEntity productEntity = entityManager.find(ProductEntity.class, id);
        if(productEntity != null)
            optionalProduct = Optional.of(productEntity);
        return optionalProduct;
    }
}
