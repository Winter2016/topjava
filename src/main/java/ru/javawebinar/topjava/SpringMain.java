package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));

            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ROLE_ADMIN));

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            //mealRestController.getAll().forEach(meal -> System.out.println(meal.toString()));
           /* mealRestController.getAllFiltered(LocalDate.of(2015, Month.MAY, 30), LocalDate.of(2015, Month.MAY, 31),
                    LocalTime.of(10, 0), LocalTime.of(12, 0)).forEach(meal -> System.out.println(meal.toString()));*/
            mealRestController.getAll().forEach(meal -> System.out.println(meal.toString()));
            mealRestController.update(new Meal(4, 1, LocalDateTime.now(), "Food", 150), 4);
            mealRestController.getAll().forEach(meal -> System.out.println(meal.toString()));

            //mealRepository.getAll().forEach(meal -> System.out.println(meal.toString()));
       /*     System.out.println(mealRepository.delete(4));
            System.out.println(mealRepository.delete(1));
            System.out.println(mealRepository.get(2));
            System.out.println(mealRepository.get(4));*/
        }
    }
}
