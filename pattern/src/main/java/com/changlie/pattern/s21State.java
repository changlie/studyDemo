package com.changlie.pattern;


import org.junit.Test;

public class s21State {

    class Context {
        private State state;

        public Context(){
            state = null;
        }

        public void setState(State state){
            this.state = state;
        }

        public State getState(){
            return state;
        }
    }


    interface State {
        public void doAction(Context context);
    }

    class StartState implements State {

        public void doAction(Context context) {
            System.out.println("Player is in start state");
            context.setState(this);
        }

        public String toString(){
            return "Start State";
        }
    }

    class StopState implements State {

        public void doAction(Context context) {
            System.out.println("Player is in stop state");
            context.setState(this);
        }

        public String toString(){
            return "Stop State";
        }
    }

    @Test
    public void test() throws Exception{
        Context context = new Context();

//        StartState startState = new StartState();
//        startState.doAction(context);
        context.setState(new StartState());
        System.out.println(context.getState().toString());

//        StopState stopState = new StopState();
//        stopState.doAction(context);
        context.setState(new StopState());
        System.out.println(context.getState().toString());
    }
}
