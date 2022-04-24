package gov.iti.jets.repo;

import gov.iti.jets.repo.entity.UserEntity;
import gov.iti.jets.repo.util.EntityManagerProvider;
import jakarta.persistence.EntityManager;
import java.util.Optional;

public class UserRepo {
    private final EntityManager entityManager = EntityManagerProvider.INSTANCE.getEntityManager();
    private static final UserRepo userRepo = new UserRepo();
    private UserRepo() {}
    public static UserRepo getInstance() {
        return userRepo;
    }

    public Optional<UserEntity> findUserById(Long id) {
        Optional<UserEntity> optionalUser = Optional.empty();
        UserEntity userEntity = entityManager.find(UserEntity.class, id);
        if(userEntity != null)
            optionalUser = Optional.of(userEntity);
        return optionalUser;
    }

    public UserEntity insertUser(UserEntity userEntity) {
        entityManager.getTransaction().begin();
        entityManager.persist(userEntity);
        entityManager.getTransaction().commit();
        return userEntity;
    }

    public UserEntity updateUser(UserEntity userEntity) {
        entityManager.getTransaction().begin();
        userEntity = entityManager.merge(userEntity);
        entityManager.getTransaction().commit();
        return userEntity;
    }

    public boolean removeUser(Long id) {
        entityManager.getTransaction().begin();
        int rowsUpdated= entityManager.createQuery("delete from UserEntity where id = :id").setParameter("id", id).executeUpdate();
        entityManager.getTransaction().commit();
        return rowsUpdated > 0;
    }
}
