package ru.boraldan.tack1;

public class Cat extends Animal{
    private String softness;

    public Cat(String name, int age, String softness) {
        super(name, age);
        this.softness = softness;
    }

    public void makeSound(){
        System.out.println("Myau!");
    }

    public void capcarap(){
        System.out.println("Zzzzzzzzzz");
    }


}
