package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            User user = em.find(User.class, userId);
            meal.setUser(user);
            em.persist(meal);
            return meal;
        } else {
            return em.merge(meal);
        }

    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        Meal meal = em.find(Meal.class, id);
        if (!meal.getUser().getId().equals(userId)) {
            return false;
        }
        User user = meal.getUser();
        return em.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter("user", user)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = em.find(Meal.class, id);
        if (!meal.getUser().getId().equals(userId)) {
            return null;
        }
        return meal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        User user = em.find(User.class, userId);
        return em.createNamedQuery(Meal.ALL_SORTED, Meal.class)
                .setParameter("user", user)
                .getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        User user = em.find(User.class, userId);
        return em.createNamedQuery(Meal.BETWEEN, Meal.class)
                .setParameter(1, startDate)
                .setParameter(2, endDate)
                .setParameter("user", user)
                .getResultList();
    }
}