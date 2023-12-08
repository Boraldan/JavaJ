package ru.boraldan.task2;

import java.lang.reflect.Field;

public class Program {

    /**
     Реализуйте обобщенный метод, который принимает объект и выводит в консоль значения всех его полей.
     * Создайте класс "Car" с различными полями (например, модель, цвет, год выпуска).
     * Примените Reflection API для вывода значений полей созданного объекта класса "Car"
     * с использованием ранее созданного метода.
     * @param args
     */
    public static void main(String[] args) throws IllegalAccessException {
            Car car = new Car("BMW", "Blue", 2022);
        processObj(car);
    }

    public static <T> void processObj(T obj) throws IllegalAccessException {
        Class<?> objClass = obj.getClass();
        Field[] fields = objClass.getDeclaredFields();
        for (Field field : fields){
            field.setAccessible(true);
            System.out.printf("%s: %s\n", field.getName(), field.get(obj));
        }

    }

}
