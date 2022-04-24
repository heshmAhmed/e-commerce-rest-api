package gov.iti.jets.repo.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public enum EntityManagerProvider {
    INSTANCE;
    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("e-commerce");
    public EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
