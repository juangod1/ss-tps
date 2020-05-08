package pedestrian;

public class Vector {
    double x,y;
    public Vector(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double module(){
        return Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
    }
}
