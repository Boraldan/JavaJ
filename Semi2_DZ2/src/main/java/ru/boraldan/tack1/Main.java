package ru.boraldan.tack1;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Задача 1:
 * Создайте абстрактный класс "Animal" с полями "name" и "age".
 * Реализуйте два класса-наследника от "Animal" (например, "Dog" и "Cat") с уникальными полями и методами.
 * Создайте массив объектов типа "Animal" и с использованием Reflection API выполните следующие действия:
 * Выведите на экран информацию о каждом объекте.
 * Вызовите метод "makeSound()" у каждого объекта, если такой метод присутствует.
 */

public class Main {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        ArrayList<Animal> zoo = new ArrayList<>();
        zoo.add(new Cat("Cat1", 2, "max"));
        zoo.add(new Cat("Cat2", 5, "medium"));
        zoo.add(new Dog("Dog1", 7, 80));
        zoo.add(new Dog("Dog2", 1, 10));

        info(zoo);
    }

    public static void info(List<Animal> list) throws IllegalAccessException, InvocationTargetException {
        for (Animal el : list) {
            Class<?> any = el.getClass();
            Class<?> anySuper = any.getSuperclass();
            List<Field> fields = new ArrayList<>(Arrays.stream(anySuper.getDeclaredFields()).toList());
            fields.addAll(Arrays.stream(any.getDeclaredFields()).toList());

            for (Field field : fields) {
                field.setAccessible(true);
                System.out.printf("%s: %s\n", field.getName(), field.get(el));
            }

            Method[] methods = any.getDeclaredMethods();
            for (Method method : methods) {
                if(method.getName().equals("makeSound")){
                    method.invoke(el);
                }
            }
        }
    }
}