//package main.states;
//
//import java.util.Stack;
//
//public class Manager {
//    private Stack states;
//
//    Manager() {
//        states = new Stack<State>();
//    }
//
//    public void push(State state) {
//        states.push(state);
//    }
//
//    public void pop() {
//        states.pop();
//    }
//
//    public void set(State state) {
//        states.pop();
//        states.push(state);
//    }
//
//    public void update(float delta) {
//        states.peek().update(delta);
//    }
////    public void render(SpriteBatch sprite){
////        states.peek().render(sprite);
////    }
//
//
//}