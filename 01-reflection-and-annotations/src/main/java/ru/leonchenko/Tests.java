package ru.leonchenko;

import ru.leonchenko.annotation.*;

public class Tests {

    @Test(priority = 3)
    //    @Test(priority = 11) //раскомментировать для получения ошибки валидации
    public void priorityValidationTestWithPriority3() {
        System.out.println("Запуск теста с приоритеторм 3");
    }

    @Test(priority = 6)
    public void priorityValidationTestWithPriority6() {
        System.out.println("Запуск теста с приоритеторм 6");
    }

    @BeforeSuite
    public static void beforeSuite() {
        System.out.println("Вызов метода с аннотацией @BeforeSuite на статическом методе");
    }

    @AfterSuite
    public static void afterSuite() {
        System.out.println("Вызов метода с аннотацией @AfterSuite на статическом методе");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("Метод вызывается до каждого метода аннотированного @Test");
    }

    @AfterTest
    public void afterTest() {
        System.out.println("Метод вызывается после каждого метода аннотированного @Test");
    }

    @CsvSource("10, Java, 20, true")
    public void firstMethodWithCsvAnnotation(int a, String b, int c, boolean d) {
        System.out.printf("Параметры метода с аннотацией @CsvSource: %d, %s, %d, %b%n", a, b, c, d);
    }

    @CsvSource("20, Kotlin, 40, false")
    public void secondMethodWithCsvAnnotation(int a, String b, int c, boolean d) {
        System.out.printf("Параметры метода с аннотацией @CsvSource: %d, %s, %d, %b%n", a, b, c, d);
    }
}