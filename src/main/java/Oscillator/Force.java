package Oscillator;

public class Force {
    static double k = 10000;
    static double gamma = 100;

    public static double oscillatorForce(double position, double velocity){
        double force = -1*k*position - gamma*velocity;
        return force;
    }
}
