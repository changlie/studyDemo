package com.changlie.pattern;

public class s01Factory {
    static interface Shape{
        void draw();
    }

    static class Rectangle implements Shape {
        @Override
        public void draw() {
            System.out.println("Inside Rectangle::draw() method.");
        }
    }

    static class Square implements Shape{
        @Override
        public void draw() {
            System.out.println("Inside Square::draw() method. ");
        }
    }

    static class Circle implements Shape{
        @Override
        public void draw() {
            System.out.println("Inside Circle::draw() method. ");
        }
    }

    static class ShapeFactory{
        static Shape getShape(String shapeType){
            if(shapeType==null) return null;

            switch (shapeType){
                case "rectangle": return new Rectangle();
                case "square": return new Square();
                case "circle": return new Circle();
            }

            return null;
        }
    }


    public static void main(String[] args) {
        ShapeFactory.getShape("rectangle").draw();
        ShapeFactory.getShape("square").draw();
        ShapeFactory.getShape("circle").draw();

        System.out.println("null: "+ShapeFactory.getShape(null));
    }
}
