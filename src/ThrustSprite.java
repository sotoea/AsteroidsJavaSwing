public class ThrustSprite extends VectorSprite{
    public ThrustSprite(int[][] vertices){
        super(vertices);
    }

    public void setPosition(double x, double y, double a){
        xposition = x;
        yposition = y;
        angle = a;
    }
}
