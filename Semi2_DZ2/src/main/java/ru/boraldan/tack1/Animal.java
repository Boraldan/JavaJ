package ru.boraldan.tack1;


public abstract class Animal {

    protected String name;
    protected int age;

    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }
   abstract void makeSound();

}
