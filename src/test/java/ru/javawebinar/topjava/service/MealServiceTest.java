package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.ADMIN_ID;
import static ru.javawebinar.topjava.MealTestData.USER_ID;
import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.MealTestData.meal1;
import static ru.javawebinar.topjava.MealTestData.meal2;
import static ru.javawebinar.topjava.MealTestData.meal3;
import static ru.javawebinar.topjava.MealTestData.meal4;
import static ru.javawebinar.topjava.MealTestData.meal5;
import static ru.javawebinar.topjava.MealTestData.meal6;
import static ru.javawebinar.topjava.MealTestData.meal7;
import static ru.javawebinar.topjava.MealTestData.meal8;
import static ru.javawebinar.topjava.MealTestData.meal9;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(START_SEQ + 8, ADMIN_ID);
        assertMatch(meal, meal7);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        service.get(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getForbidden() {
        service.get(START_SEQ + 8, USER_ID);
    }

    @Test
    public void delete() {
        service.delete(START_SEQ + 8, ADMIN_ID);
        assertMatch(service.getAll(ADMIN_ID), meal9, meal8);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() {
        service.delete(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteForbidden() {
        service.delete(START_SEQ + 8, USER_ID);
    }

    @Test
    public void getBetweenDates() {
        List<Meal> mealList = service.getBetweenDates(LocalDate.of(2018, Month.MAY, 16), LocalDate.of(2018, Month.MAY, 16), USER_ID);
        assertMatch(mealList, meal3, meal2, meal1);
    }

    @Test
    public void getBetweenDateTimes() {
        List<Meal> mealList = service.getBetweenDateTimes(LocalDateTime.of(2018, Month.MAY, 16, 9, 36, 38), LocalDateTime.of(2018, Month.MAY, 16, 20, 36, 38), ADMIN_ID);
        assertMatch(mealList, meal9, meal8);
    }

    @Test
    public void getAll() {
        List<Meal> mealList = service.getAll(USER_ID);
        assertMatch(mealList, meal6, meal5, meal4, meal3, meal2, meal1);
    }

    @Test
    public void update() {
        Meal updated = new Meal(meal1);
        updated.setDescription("New breakfast");
        service.update(updated, USER_ID);
        assertMatch(service.get(START_SEQ + 2, USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateForbidden() {
        Meal updated = new Meal(meal1);
        updated.setDateTime(LocalDateTime.now());
        service.update(updated, ADMIN_ID);
        assertMatch(service.get(START_SEQ + 2, USER_ID), updated);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(null, LocalDateTime.now(), "Breakfast", 800);
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatch(service.getAll(USER_ID), newMeal, meal6, meal5, meal4, meal3, meal2, meal1);
    }
}