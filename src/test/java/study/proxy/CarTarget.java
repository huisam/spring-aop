package study.proxy;

public class CarTarget implements Car {
    @Override
    public String start(String name) {
        return "Car " + name + " started!";
    }

    @Override
    public String stop(String name) {
        return "Car " + name + " stopped!";
    }
}
