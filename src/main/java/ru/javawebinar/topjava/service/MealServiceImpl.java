package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealServiceImpl implements MealService {

    private MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public MealWithExceed create(Meal meal, int authUserId) {
        Meal newMeal = repository.save(meal, authUserId);
        //TODO add real exceeded
        return MealsUtil.createWithExceed(newMeal, false);
    }

    @Override
    public void delete(int id, int authUserId) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id, authUserId), id);
    }

    @Override
    public MealWithExceed get(int id, int authUserId) throws NotFoundException {
        Meal meal = checkNotFoundWithId(repository.get(id, authUserId), id);
        return MealsUtil.createWithExceed(meal, true);
    }

    @Override
    public Collection<MealWithExceed> getAllFiltered(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int authUserId){
        Collection<Meal> meals = repository.getAll(authUserId);
        //TODO add real
        return MealsUtil.getWithExceeded(meals, 200);
    }

    @Override
    public Collection<MealWithExceed> getAll(int authUserId) {
        Collection<Meal> meals = repository.getAll(authUserId);
        return MealsUtil.getWithExceeded(meals, 200);
    }

    @Override
    public void update(Meal meal, int authUserId) {
        checkNotFoundWithId(repository.save(meal, authUserId), meal.getId());
    }
}