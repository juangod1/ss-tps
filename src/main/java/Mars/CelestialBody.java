package Mars;

public class CelestialBody {
    int id;
    double vx,vy,x,y,radius,mass;
    Force force;

    public CelestialBody(int id, double vx0, double vy0, double x0, double y0, double radius, double mass){
        this.id = id;
        this.radius = radius;
        this.mass = mass;
        vx=vx0;
        vy=vy0;
        x=x0;
        y=y0;
    }
}