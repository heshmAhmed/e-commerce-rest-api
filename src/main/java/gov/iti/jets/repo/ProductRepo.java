package gov.iti.jets.repo;

import gov.iti.jets.repo.entity.CategoryEntity;
import gov.iti.jets.repo.entity.ProductEntity;
import gov.iti.jets.repo.util.EntityManagerProvider;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    public boolean updateProduct(ProductEntity productEntity) {
        ProductEntity productEntity1 = entityManager.find(ProductEntity.class, productEntity.getId());
        if(productEntity1 == null)
            return false;
        System.out.println(productEntity1);
        productEntity1.setImg(productEntity.getImg());
        productEntity1.setStock(productEntity.getStock());
        productEntity1.setDesc(productEntity.getDesc());
        productEntity1.setName(productEntity.getName());
        productEntity1.setPrice(productEntity.getPrice());
        entityManager.getTransaction().begin();
        entityManager.merge(productEntity1);
        entityManager.getTransaction().commit();
        return true;
    }

    public boolean deleteProduct(Long id) {
        ProductEntity productEntity = entityManager.find(ProductEntity.class, id);
        boolean removed = false;
        try {
            if(productEntity != null) {
                entityManager.getTransaction().begin();
                entityManager.remove(productEntity);
                entityManager.getTransaction().commit();
                removed = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return removed;
    }

    public List<ProductEntity> getAllProduct(int page, int rpp) {
        Query query = entityManager.createQuery("from ProductEntity", ProductEntity.class);
        query.setFirstResult((page-1) * rpp);
        query.setMaxResults(rpp);
        return query.getResultList();
    }

    public Optional<ProductEntity> findProductById(Long id) {
        Optional<ProductEntity> optionalProduct = Optional.empty();
        ProductEntity productEntity = entityManager.find(ProductEntity.class, id);
        if(productEntity != null)
            optionalProduct = Optional.of(productEntity);
        return optionalProduct;
    }
    public boolean updateProductCategories(Long id, Set<CategoryEntity> categoryEntities) {
        ProductEntity productEntity = entityManager.find(ProductEntity.class,  id);
        if(productEntity == null)
            return false;
        productEntity.setCategories(categoryEntities);
        entityManager.getTransaction().begin();
        entityManager.merge(productEntity);
        entityManager.getTransaction().commit();
        return true;
    }

    public List<ProductEntity> getProductsByCategory(CategoryEntity category) {
        Query query =
                entityManager.createQuery("select c.productEntitySet from CategoryEntity c where c.id = :id or c.category = :cat"
                                ,ProductEntity.class)
                        .setParameter("id", category.getId())
                        .setParameter("cat", category.getCategory());

        return query.getResultList();
    }
}
