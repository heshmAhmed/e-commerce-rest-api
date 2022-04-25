package gov.iti.jets.repo;

import gov.iti.jets.repo.entity.AdminEntity;
import gov.iti.jets.repo.entity.ClerkEntity;
import gov.iti.jets.repo.entity.CustomerEntity;
import gov.iti.jets.repo.entity.UserEntity;
import gov.iti.jets.repo.util.EntityManagerProvider;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.validation.constraints.Email;

import java.util.List;
import java.util.Optional;

import com.google.protobuf.Option;

public class UserRepo {
    private static final UserRepo userRepo = new UserRepo();
    private final EntityManagerProvider entityManagerProvider = EntityManagerProvider.INSTANCE;
    private final EntityManager entityManager = entityManagerProvider.getEntityManager();
    private UserRepo() {}
    public static UserRepo getInstance() {
        return userRepo;
    }

    public Optional<UserEntity> findUserById(Long id) {
//        EntityManager entityManager = entityManagerProvider.getEntityManager();
        Optional<UserEntity> optionalUser = Optional.empty();
        UserEntity userEntity = entityManager.find(UserEntity.class, id);
        if(userEntity != null)
            optionalUser = Optional.of(userEntity);
//        entityManager.close();
        return optionalUser;
    }

    public Optional<ClerkEntity> findClerkById(Long id) {
//        EntityManager entityManager = entityManagerProvider.getEntityManager();
        Optional<ClerkEntity> optionalClerk = Optional.empty();
        ClerkEntity clerkEntity = entityManager.find(ClerkEntity.class, id);
        if(clerkEntity != null)
            optionalClerk = Optional.of(clerkEntity);
//        entityManager.close();
        return optionalClerk;
    }

    public Optional<CustomerEntity> findCustomerById(Long id) {
//        EntityManager entityManager = entityManagerProvider.getEntityManager();
        Optional<CustomerEntity> optionalCustomer = Optional.empty();
        CustomerEntity customerEntity = entityManager.find(CustomerEntity.class, id);
        if(customerEntity != null)
            optionalCustomer = Optional.of(customerEntity);
//        entityManager.close();;
        return optionalCustomer;
    }

    public Optional<AdminEntity> findAdminById(Long id) {
//        EntityManager entityManager = entityManagerProvider.getEntityManager();
        Optional<AdminEntity> optionalAdmin = Optional.empty();
        AdminEntity adminEntity = entityManager.find(AdminEntity.class, id);
        if(adminEntity != null)
            optionalAdmin = Optional.of(adminEntity);
//        entityManager.close();
        return optionalAdmin;
    }

    public UserEntity insertUser(UserEntity userEntity) {
        if(findUserByEmail(userEntity.getEmail()).isPresent())
            return userEntity;
//        EntityManager entityManager = entityManagerProvider.getEntityManager();
        try {
            entityManager.getTransaction().begin();
            userEntity = entityManager.merge(userEntity);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
//        entityManager.close();
        
        return userEntity;
    }

    public UserEntity updateUser(UserEntity userEntity) {
//        EntityManager entityManager = entityManagerProvider.getEntityManager();
        entityManager.getTransaction().begin();
        userEntity = entityManager.merge(userEntity);
        entityManager.getTransaction().commit();
//        entityManager.close();
        return userEntity;
    }

    public boolean removeClerk(Long clerkId) {
//        EntityManager entityManager = entityManagerProvider.getEntityManager();
        boolean removed = false;
        ClerkEntity clerkEntity = entityManager.find(ClerkEntity.class, clerkId);
        if(clerkEntity != null){
            entityManager.getTransaction().begin();
            entityManager.remove(clerkEntity);
            entityManager.getTransaction().commit();
            removed = true;
        }
//        entityManager.close();
        return removed;
    }

    public List<ClerkEntity> getAllClerks(){
//        EntityManager entityManager = entityManagerProvider.getEntityManager();
        List<ClerkEntity> clerkEntities = entityManager.createQuery("from ClerkEntity", ClerkEntity.class).getResultList(); 
//        entityManager.close();
        return clerkEntities;
    }

    public Optional<UserEntity> findUserByEmail(String email) {
        Optional<UserEntity> userEntityOptional = Optional.empty();
        try{
//            EntityManager entityManager = entityManagerProvider.getEntityManager();
            UserEntity userEntity = entityManager.createQuery("from UserEntity where email = :email", UserEntity.class)
            .setParameter("email",email).getSingleResult();
            userEntityOptional = Optional.of(userEntity);
//            entityManager.close();
        }catch(NoResultException e){
            e.printStackTrace();
        }
      
        return userEntityOptional;
    }

}
