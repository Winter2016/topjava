package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface MealRepository {
    Meal save(Meal meal, int authUserId);

    boolean delete(int id, int authUserId);

    Meal get(int id, int authUserId);

    Collection<Meal> getAll(int authUserId);
}
