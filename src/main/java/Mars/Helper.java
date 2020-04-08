package Mars;

public class Helper {
    // theta is in radians
    public static double RAtoTheta(double h, double m, double s){
        return h*Math.PI/12 + m*Math.PI/720 + s*Math.PI/43200;
    }
}
