package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final Meal meal1 = new Meal(START_SEQ + 2, LocalDateTime.of(2018, Month.MAY, 16, 8, 36, 38), "Breakfast", 500);
    public static final Meal meal2 = new Meal(START_SEQ + 3, LocalDateTime.of(2018, Month.MAY, 16, 15, 36, 38), "Lunch", 1000);
    public static final Meal meal3 = new Meal(START_SEQ + 4, LocalDateTime.of(2018, Month.MAY, 16, 20, 36, 38), "Dinner", 500);
    public static final Meal meal4 = new Meal(START_SEQ + 5, LocalDateTime.of(2018, Month.MAY, 18, 8, 36, 38), "Breakfast", 500);
    public static final Meal meal5 = new Meal(START_SEQ + 6, LocalDateTime.of(2018, Month.MAY, 18, 15, 36, 38), "Lunch", 1000);
    public static final Meal meal6 = new Meal(START_SEQ + 7, LocalDateTime.of(2018, Month.MAY, 18, 20, 36, 38), "Dinner", 520);
    public static final Meal meal7 = new Meal(START_SEQ + 8, LocalDateTime.of(2018, Month.MAY, 16, 8, 36, 38), "Breakfast", 500);
    public static final Meal meal8 = new Meal(START_SEQ + 9, LocalDateTime.of(2018, Month.MAY, 16, 15, 36, 38), "Lunch", 1000);
    public static final Meal meal9 = new Meal(START_SEQ + 10, LocalDateTime.of(2018, Month.MAY, 16, 20, 36, 38), "Dinner", 500);


    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
