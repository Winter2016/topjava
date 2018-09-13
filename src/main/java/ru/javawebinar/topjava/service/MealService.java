package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

public interface MealService {
    MealWithExceed create(Meal meal, int authUserId);

    void delete(int id, int authUserId) throws NotFoundException;

    MealWithExceed get(int id, int authUserId) throws NotFoundException;

    void update(Meal meal, int authUserId);

    Collection<MealWithExceed> getAll(int authUserId);

    Collection<MealWithExceed> getAllFiltered(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int authUserId);
}