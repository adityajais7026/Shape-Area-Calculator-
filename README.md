# area-calculator
Area Calculator Using Abstract Class in Java🔥

This project is to show the use of abstract classes and polymorphism in Java in calculating the area of geometric figures such as Circle, Rectangle, and Triangle. It is a simple console-based application that illustrates basic OOP concepts such as abstraction, inheritance, and dynamic method dispatch.

Features :-
- Abstract class "Shape" with subclasses of its own: Circle(⭕️), Rectangle(▬), Triangle(🔺).
- Polymorphism at runtime using overridden methods called area()
- Clean and extensible object-oriented design
- Console I/O for user interaction

Future Improvements :-
- Calculations for perimeters
- Addition of more shapes (e.g., Square, Trapezium and many more to be added)
- GUI-based implementation using Java Swing or JavaFX
                                                         JAVA CODE FOR THE AREA CALCULATOR ⬇️
  

import java.util.Scanner;


abstract class Shape {22
    abstract double area();
}


class Circle extends Shape {
    private double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    @Override
    double area() {
        return Math.PI * radius * radius;
    }
}


class Rectangle extends Shape {
    private double length, width;

    Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    @Override
    double area() {
        return length * width;
    }
}


class Triangle extends Shape {
    private double base, height;

    Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    @Override
    double area() {
        return 0.5 * base * height;
    }
}

// Main class
public class AreaCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Shape shape = null;

        System.out.println("Choose a shape to calculate area:");
        System.out.println("1. Circle");
        System.out.println("2. Rectangle");
        System.out.println("3. Triangle");
        System.out.print("Enter your choice (1-3): ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.print("Enter radius: ");
                double radius = scanner.nextDouble();
                shape = new Circle(radius);
                break;
            case 2:
                System.out.print("Enter length and width: ");
                double length = scanner.nextDouble();
                double width = scanner.nextDouble();
                shape = new Rectangle(length, width);
                break;
            case 3:
                System.out.print("Enter base and height: ");
                double base = scanner.nextDouble();
                double height = scanner.nextDouble();
                shape = new Triangle(base, height);
                break;
            default:
                System.out.println("Invalid choice.");
                scanner.close();
                return;
        }

        System.out.println("Area: " + shape.area());
        scanner.close();
    }
}
