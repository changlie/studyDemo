package com.changlie.pattern;

public class s02AbstractFactory {

    static interface Shape{
        void draw();
    }

    static class Rectangle implements Shape {
        @Override
        public void draw() {
            System.out.println("Inside Rectangle::draw() method.");
        }
    }

    static class Square implements Shape {
        @Override
        public void draw() {
            System.out.println("Inside Square::draw() method. ");
        }
    }

    static class Circle implements Shape {
        @Override
        public void draw() {
            System.out.println("Inside Circle::draw() method. ");
        }
    }

    static interface Color{
        void fill();
    }

    static class Blue implements Color{
        @Override
        public void fill() {
            System.out.println("fill with Blue");
        }
    }

    static class Green implements Color{
        @Override
        public void fill() {
            System.out.println("fill with Green");
        }
    }

    static class Yellow implements Color{
        @Override
        public void fill() {
            System.out.println("fill with Yellow");
        }
    }

    static abstract class AbstractFactory{
        abstract Shape getShape(String shapeType);
        abstract Color getColor(String colorType);
    }



    static class ShapeFactory extends AbstractFactory{
        @Override
        Shape getShape(String shapeType) {
            if(shapeType==null) return null;

            switch (shapeType){
                case "rectangle": return new Rectangle();
                case "square": return new Square();
                case "circle": return new Circle();
            }
            return null;
        }

        @Override
        Color getColor(String colorType) {
            return null;
        }
    }

    static class ColorFactory extends AbstractFactory{

        @Override
        Shape getShape(String shapeType) {
            return null;
        }
        @Override
        Color getColor(String colorType) {
            if(colorType==null) return null;
            switch (colorType){
                case "blue": return new Blue();
                case "green": return new Green();
                case "yellow": return new Yellow();
            }
            return null;
        }
    }

    static class Factory{
        static AbstractFactory getFactory(String factoryType){
            if(factoryType==null) return null;

            switch (factoryType){
                case "shape": return new ShapeFactory();
                case "color": return new ColorFactory();
            }

            return null;
        }
    }

    public static void main(String[] args) {
        ShapeFactory shapeFactory = (ShapeFactory) Factory.getFactory("shape");

        shapeFactory.getShape("square").draw();
        shapeFactory.getShape("circle").draw();

        ColorFactory colorFactory = (ColorFactory) Factory.getFactory("color");

        colorFactory.getColor("blue").fill();

    }
}
