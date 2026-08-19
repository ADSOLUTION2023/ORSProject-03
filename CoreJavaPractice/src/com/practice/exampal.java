package com.practice;

public class exampal {

    public void display() {
        System.out.println(
            "Object created using Class.forName() and newInstance()"
        );
    }

    public static void main(String[] args) {
        try {
            
           	Class<?> clazz = Class.forName("com.practice.exampal");

            exampal example =
                (exampal) clazz.getDeclaredConstructor().newInstance();

            example.display();

            System.out.println(example);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}