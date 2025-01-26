package ru.leonchenko;

import ru.leonchenko.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class TestRunner {

    public static void runTests(Class<?> clazz) {

        List<Method> beforeSuiteMethods = new ArrayList<>();
        List<Method> afterSuiteMethods = new ArrayList<>();
        List<Method> beforeTestMethods = new ArrayList<>();
        List<Method> afterTestMethods = new ArrayList<>();
        List<Method> testMethods = new ArrayList<>();
        List<Method> csvMethods = new ArrayList<>();

        for (Method method : clazz.getDeclaredMethods()) {

            if (method.isAnnotationPresent(Test.class)) {
                var priority = method.getAnnotation(Test.class).priority();
                if (priority < 1 || priority > 10) {
                    throw new IllegalArgumentException("@Test priority must be between 1 and 10.");
                }
                testMethods.add(method);
            }
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                checkIsStatic(method);
                beforeSuiteMethods.add(method);
            }
            if (method.isAnnotationPresent(AfterSuite.class)) {
                checkIsStatic(method);
                afterSuiteMethods.add(method);
            }
            if (method.isAnnotationPresent(BeforeTest.class)) {
                beforeTestMethods.add(method);
            }
            if (method.isAnnotationPresent(AfterTest.class)) {
                afterTestMethods.add(method);
            }
            if (method.isAnnotationPresent(CsvSource.class)) {
                csvMethods.add(method);
            }
        }


        try {

            var instance = clazz.getDeclaredConstructor().newInstance();

            sortTestMethods(testMethods);

            suiteMethodsInvoke(beforeSuiteMethods, "@BeforeSuite");

            executeCsvMethods(csvMethods, instance);

            executeBeforeAndAfterTestMethodsWithingTestMethods(testMethods, beforeTestMethods, instance, afterTestMethods);

            suiteMethodsInvoke(afterSuiteMethods, "@AfterSuite");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void executeBeforeAndAfterTestMethodsWithingTestMethods(List<Method> testMethods, List<Method> beforeTestMethods, Object instance, List<Method> afterTestMethods) throws IllegalAccessException, InvocationTargetException {
        for (Method testMethod : testMethods) {

            for (Method beforeTestMethod : beforeTestMethods) {
                beforeTestMethod.invoke(instance);
            }

            testMethod.invoke(instance);

            for (Method afterTestMethod : afterTestMethods) {
                afterTestMethod.invoke(instance);
            }
        }
    }

    private static void executeCsvMethods(List<Method> csvMethods, Object instance) throws IllegalAccessException, InvocationTargetException {
        if (!csvMethods.isEmpty()) {
            for (Method csvMethod : csvMethods) {
                var csvData = csvMethod.getAnnotation(CsvSource.class).value();
                var parts = csvData.split(",\\s*");
                var params = convertParameters(csvMethod, parts);
                csvMethod.invoke(instance, params);
            }
        }
    }

    private static void suiteMethodsInvoke(List<Method> suiteMethods, String annotationType) throws IllegalAccessException, InvocationTargetException {
        switch (suiteMethods.size()) {
            case 0 -> {}
            case 1 -> {
                var method = suiteMethods.get(0);
                method.invoke(null);
            }
            default -> throw new RuntimeException("Может быть только один метод с аннотацией " + annotationType + ".");
        }
    }

    private static void sortTestMethods(List<Method> testMethods) {
        testMethods.sort((m1, m2) -> {
            int p1 = m1.getAnnotation(Test.class).priority();
            int p2 = m2.getAnnotation(Test.class).priority();
            return Integer.compare(p2, p1);
        });
    }

    private static void checkIsStatic(Method method) {
        if (!Modifier.isStatic(method.getModifiers())) {
            throw new RuntimeException("Методы аннотированные @BeforeSuite или @AfterSuite должны быть статичными.");
        }
    }

    private static Object[] convertParameters(Method method, String[] csvParts) {

        var parameterTypes = method.getParameterTypes();

        if (parameterTypes.length != csvParts.length) {
            throw new RuntimeException("Нарушен формат заполнения данными аннотации @CsvSource");
        }

        var params = new Object[csvParts.length];
        for (int i = 0; i < csvParts.length; i++) {
            var paramType = parameterTypes[i];
            params[i] = switch (paramType.getSimpleName()) {
                case "int" -> Integer.parseInt(csvParts[i]);
                case "boolean" -> Boolean.parseBoolean(csvParts[i]);
                case "String" -> csvParts[i];
                case "double" -> Double.parseDouble(csvParts[i]);
                default -> throw new RuntimeException("Неподдерживаемый тип данных: " + paramType);
            };
        }
        return params;
    }

    public static void main(String[] args) {
        runTests(Tests.class);
    }
}