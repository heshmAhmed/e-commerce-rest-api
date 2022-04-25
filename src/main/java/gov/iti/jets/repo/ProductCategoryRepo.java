package gov.iti.jets.repo;

import gov.iti.jets.repo.entity.CategoryEntity;
import gov.iti.jets.repo.entity.ProductEntity;
import gov.iti.jets.repo.util.EntityManagerProvider;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ProductCategoryRepo {
    private final EntityManager entityManager = EntityManagerProvider.INSTANCE.getEntityManager();
    private static final ProductCategoryRepo productCategoryRepo = new ProductCategoryRepo();
    private ProductCategoryRepo() {}
    public static ProductCategoryRepo getInstance() {
        return productCategoryRepo;
    }

    public List<ProductEntity> getProductsByCategory(CategoryEntity category) {
        Query query =
                entityManager.createQuery("select c.productEntitySet from CategoryEntity c where c.id = :id or c.category = :cat"
                ,ProductEntity.class)
                .setParameter("id", category.getId())
                .setParameter("cat", category.getCategory());

        return query.getResultList();
    }

    public List<CategoryEntity> getProductCategories(Long productId){
        Query query = entityManager.createQuery("select p.categories from ProductEntity p where p.id = :id", CategoryEntity.class)
                .setParameter("id", productId);
        return query.getResultList();
    }

    public CategoryEntity insertCategory(CategoryEntity category) {
        Optional<CategoryEntity> optionalCategory = findCategoryByName(category);
        if(optionalCategory.isEmpty()) {
            entityManager.getTransaction().begin();
            category = entityManager.merge(category);
            entityManager.getTransaction().commit();
        }
        else
            category.setId(optionalCategory.get().getId());
        return category;
    }

    private Optional<CategoryEntity> findCategoryByName(CategoryEntity category) {
        Optional<CategoryEntity> optionalCategory = Optional.empty();
        try {
            category = entityManager.createQuery("from CategoryEntity where category = :category", CategoryEntity.class)
                    .setParameter("category", category.getCategory()).getSingleResult();
            optionalCategory = Optional.of(category);
        }catch (NoResultException e) {
            e.printStackTrace();
        }
        return optionalCategory;
    }



}
