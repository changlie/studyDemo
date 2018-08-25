package com.changlie.pattern;

import org.junit.Test;

public class s25Visitor {

    interface ComputerPartVisitor {
        public void visit(Computer computer);
        public void visit(Mouse mouse);
        public void visit(Keyboard keyboard);
        public void visit(Monitor monitor);
    }

    interface ComputerPart {
        public void accept(ComputerPartVisitor computerPartVisitor);
        String getName();
    }

    class Keyboard  implements ComputerPart {

        @Override
        public void accept(ComputerPartVisitor computerPartVisitor) {
            computerPartVisitor.visit(this);
        }

        @Override
        public String getName() {
            return "Keyboard";
        }
    }

    class Monitor  implements ComputerPart {

        @Override
        public void accept(ComputerPartVisitor computerPartVisitor) {
            computerPartVisitor.visit(this);
        }

        @Override
        public String getName() {
            return "Monitor";
        }
    }

    class Mouse  implements ComputerPart {

        @Override
        public void accept(ComputerPartVisitor computerPartVisitor) {
            computerPartVisitor.visit(this);
        }

        @Override
        public String getName() {
            return "Mouse";
        }

    }

    class Computer implements ComputerPart {


        ComputerPart[] parts;

        public Computer(){
            parts = new ComputerPart[] {new Mouse(), new Keyboard(), new Monitor()};
        }


        @Override
        public void accept(ComputerPartVisitor computerPartVisitor) {
            for (int i = 0; i < parts.length; i++) {
                parts[i].accept(computerPartVisitor);
            }
            computerPartVisitor.visit(this);
        }

        @Override
        public String getName() {
            return "Computer";
        }
    }

    class ComputerPartDisplayVisitor implements ComputerPartVisitor {

        @Override
        public void visit(Computer computer) {
            System.out.println("Displaying Computer. @"+computer.getName());
        }

        @Override
        public void visit(Mouse mouse) {
            System.out.println("Displaying Mouse. @"+mouse.getName());
        }

        @Override
        public void visit(Keyboard keyboard) {
            System.out.println("Displaying Keyboard. @"+keyboard.getName());
        }

        @Override
        public void visit(Monitor monitor) {
            System.out.println("Displaying Monitor. @"+monitor.getName());
        }
    }


    @Test
    public void test() throws Exception{
        ComputerPart computer = new Computer();
        computer.accept(new ComputerPartDisplayVisitor());
    }
}
