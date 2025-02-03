package ru.leonchenko.steamapi;

import ru.leonchenko.steamapi.model.Employee;

import java.util.List;

import static ru.leonchenko.steamapi.emuns.Position.ENGINEER;
import static ru.leonchenko.steamapi.emuns.Position.MANAGER;
import static ru.leonchenko.steamapi.utils.StreamUtils.*;

public class StreamTest {

    public static void main(String[] args) {

        var employees = List.of(
                new Employee("Иван Иванов", 45, MANAGER.getPosition()),
                new Employee("Петр Петров", 38, ENGINEER.getPosition()),
                new Employee("Сергей Сергеев", 50, ENGINEER.getPosition()),
                new Employee("Анна Аннова", 42, MANAGER.getPosition()),
                new Employee("Ольга Ольгина", 37, ENGINEER.getPosition()),
                new Employee("Дмитрий Дмитриев", 55, ENGINEER.getPosition())
        );

        var animals = List.of("кошка", "собака", "мышь", "капибара", "енот");

        // 1. Реализуйте удаление из листа всех дубликатов
        var listWithDuplicates = List.of(1,2,4,4);
        var distinctList = removeAllDuplicates(listWithDuplicates);
        System.out.println("Список без дубликатов: " + distinctList);

        //2. Найдите в списке целых чисел 3-е наибольшее число (пример: 5 2 10 9 4 3 10 1 13 => 10)
        var numbersWithoutDuplicates = List.of(5, 2, 10, 9, 4, 3, 10, 1,13);
        var thirdLargestNumber = findThirdLargestNumber(numbersWithoutDuplicates);
        System.out.println("3-е наибольшее число: " + thirdLargestNumber);

        //3.  Найдите в списке целых чисел 3-е наибольшее «уникальное» число (пример: 5 2 10 9 4 3 10 1 13 => 9, в отличие от прошлой задачи здесь разные 10 считает за одно число)
        var numbersWithDuplicates = List.of(5, 2, 10, 9, 4, 3, 10, 1,13);
        var thirdUniqueLargest = findThirdUniqueLargest(numbersWithDuplicates);
        System.out.println("3-е наибольшее «уникальное» число: " + thirdUniqueLargest);

        //4. Имеется список объектов типа Сотрудник (имя, возраст, должность),
        // необходимо получить список имен 3 самых старших сотрудников с должностью «Инженер»,
        // в порядке убывания возраста
        var topThreeOldestEngineers = findTopThreeOldestEngineers(employees);
        System.out.println("3 самых старших сотрудников с должностью «Инженер»: " + topThreeOldestEngineers);

        //5. Имеется список объектов типа Сотрудник (имя, возраст, должность),
        // посчитайте средний возраст сотрудников с должностью «Инженер»
        var averageAgeOfEngineers = getAverageAgeOfEngineers(employees);
        System.out.println("Средний возраст сотрудников с должностью «Инженер»: " + averageAgeOfEngineers);

        //6. Найдите в списке слов самое длинное
        var longestWord = findLongestWord(animals);
        System.out.println("Самое длинное слово: " + longestWord);

        //7. Имеется строка с набором слов в нижнем регистре, разделенных пробелом.
        // Постройте хеш-мапы, в которой будут хранится пары:
        // слово - сколько раз оно встречается во входной строке
        var inputString = "один два два три три три четыре четыре четыре четыре";
        var wordsCountMap = getWordsCountMap(inputString);
        System.out.println("Мапа слов количество-слово: " + wordsCountMap);

        //8. Отпечатайте в консоль строки из списка в порядке увеличения длины слова,
        // если слова имеют одинаковую длины, то должен быть сохранен алфавитный порядок
        var wordsSortedByLengthThenByAlphabet = sortWordsByLengthAndAlphabet(animals);
        System.out.println("Слова в порядке увеличения их длины: " + wordsSortedByLengthThenByAlphabet);

        //9. Имеется массив строк, в каждой из которых лежит набор из 5 строк,
        // разделенных пробелом, найдите среди всех слов самое длинное,
        // если таких слов несколько, получите любое из них
        var animalsArray = new String[]{
                "лев тигр леопард гепард ягуар",
                "слон бегемот носорог кенгуру крокодил",
                "обезьяна верблюд гиппопотам альбатрос попугай"
        };
        var longestAnimalName = findLongestWordInArray(animalsArray);
        System.out.println("Самое длинное слово: " + longestAnimalName);
    }
}
