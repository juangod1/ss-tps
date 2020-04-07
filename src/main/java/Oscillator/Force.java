package Oscillator;

class Force {
    static double k = 10000;
    static double gamma = 100;

    static double oscillatorForce(double position, double velocity){
        return -1*k*position - gamma*velocity;
    }
}
