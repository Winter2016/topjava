package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealServiceImpl implements MealService {

    private MealRepository repository;
    private UserService userService;

    @Autowired
    public MealServiceImpl(MealRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    @Override
    public MealWithExceed create(Meal meal, int authUserId) {
        Meal newMeal = repository.save(meal, authUserId);
        checkNotFound(newMeal, "this id");
        return get(newMeal.getId(), authUserId);
    }

    @Override
    public void delete(int id, int authUserId) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id, authUserId), id);
    }

    @Override
    public MealWithExceed get(int id, int authUserId) throws NotFoundException {
        return getAll(authUserId)
                .stream()
                .filter(meal -> id == meal.getId())
                .peek(meal -> checkNotFoundWithId(meal, id))
                .findAny()
                .get();
    }

    @Override
    public Collection<MealWithExceed> getAllFiltered(LocalDateTime startDateTime, LocalDateTime endDateTime, int authUserId) {
        Collection<Meal> meals = repository.getAll(authUserId);
        int caloriesPerDay = userService.get(authUserId).getCaloriesPerDay();
        return MealsUtil.getFilteredWithExceeded(meals, caloriesPerDay, startDateTime, endDateTime);
    }

    @Override
    public Collection<MealWithExceed> getAll(int authUserId) {
        Collection<Meal> meals = repository.getAll(authUserId);
        int caloriesPerDay = userService.get(authUserId).getCaloriesPerDay();
        return MealsUtil.getWithExceeded(meals, caloriesPerDay);
    }

    @Override
    public void update(Meal meal, int authUserId) {
        checkNotFoundWithId(repository.save(meal, authUserId), meal.getId());
    }
}