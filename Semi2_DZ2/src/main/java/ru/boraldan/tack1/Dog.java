package ru.boraldan.tack1;


public class Dog extends Animal{

    private int angry;

    public Dog(String name, int age, int angry) {
        super(name, age);
        this.angry = angry;
    }

    public void makeSound(){
        System.out.println("Gau!");
    }


    public void kyskys(){
        System.out.println("Rrrrrrrrrrr");
    }
}
