package gov.iti.jets.repo;

import gov.iti.jets.repo.entity.CategoryEntity;
import gov.iti.jets.repo.util.EntityManagerProvider;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;
import java.util.Optional;

public class CategoryRepo {
    private final EntityManager entityManager = EntityManagerProvider.INSTANCE.getEntityManager();
    private static final CategoryRepo productCategoryRepo = new CategoryRepo();
    private CategoryRepo() {}
    public static CategoryRepo getInstance() {
        return productCategoryRepo;
    }


    public List<CategoryEntity> getCategories() {
        return entityManager.createQuery("from CategoryEntity", CategoryEntity.class).getResultList();
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
