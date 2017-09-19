package com.incamp.mhs;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public abstract class BaseRepository<Entity, Pk> {

    @PersistenceContext
    protected EntityManager entityManager;

    protected final Class<Entity> entityClass;

    public BaseRepository() {
        entityClass = (Class<Entity>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void remove(Entity entity) {
        entityManager.remove(entity);
    }

    public void persist(Entity entity) {
        entityManager.persist(entity);
    }

    public List<Entity> findBy(EntitySpecification<Entity> specification) {
        CriteriaQuery<Entity> userCriteriaQuery = specification.toCriteria(entityManager.getCriteriaBuilder());
        return entityManager.createQuery(userCriteriaQuery).getResultList();
    }

    public Optional<Entity> findOneByPk(Pk primaryKey) {
        return Optional.ofNullable(entityManager.find(entityClass, primaryKey));
    }
}
