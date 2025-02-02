package ru.leonchenko.steamapi.utils;

import ru.leonchenko.steamapi.model.Employee;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ru.leonchenko.steamapi.emuns.Position.ENGINEER;

public class StreamUtils {

    public static <T> List<T> removeAllDuplicates(List<T> list){
        return list.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    public static Integer findThirdLargestNumber(List<Integer> list) {
        return list.stream()
                .sorted(Comparator.reverseOrder())
                .skip(2)
                .findFirst()
                .orElseThrow();
    }

    public static Integer findThirdUniqueLargest(List<Integer> list) {
        return list.stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(2)
                .findFirst()
                .orElseThrow();
    }

    public static List<String> findTopThreeOldestEngineers(List<Employee> employees) {
        return employees.stream()
                .filter(employee -> employee.position().equals(ENGINEER.getPosition()))
                .sorted(Comparator.comparingInt(Employee::age).reversed())
                .limit(3)
                .map(Employee::name)
                .collect(Collectors.toList());
    }

    public static Double getAverageAgeOfEngineers(List<Employee> employees) {
        return employees.stream()
                .filter(employee -> employee.position().equals(ENGINEER.getPosition()))
                .mapToInt(Employee::age)
                .average()
                .orElseThrow();
    }

    public static String findLongestWord(List<String> words) {
        return words.stream()
                .max(Comparator.comparingInt(String::length))
                .orElseThrow();
    }

    public static HashMap<String, Long> getWordsCountMap(String input) {
        return Arrays.stream(input.split(" "))
                .collect(
                        Collectors.groupingBy(
                                Function.identity(), HashMap::new, Collectors.counting()
                        )
                );
    }

    public static List<String> sortWordsByLengthAndAlphabet(List<String> words) {
        return words.stream()
                .sorted(Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder()))
                .collect(Collectors.toList());
    }

    public static String findLongestWordInArray(String[] array) {
        return Arrays.stream(array)
                .flatMap(string -> Arrays.stream(string.split(" ")))
                .max(Comparator.comparingInt(String::length))
                .orElseThrow();
    }
}
