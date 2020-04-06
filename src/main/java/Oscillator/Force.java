package Oscillator;

public class Force {
    static double k = 10000;
    static double gamma = 100;

    public static double oscillatorForce(double position, double velocity){
        return -1*k*position - -1*gamma*velocity;
    }
}
