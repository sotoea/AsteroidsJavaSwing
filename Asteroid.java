public class Asteroid extends VectorSprite {

    public double rotation;
    private double speedFactor;

    public Asteroid(double xposition, double yposition){
        super(new int[][]{
                {15, 3},
                {5, (int)((Math.random()+1)*8)},
                {-15, 10},
                {-13, -15},
                {12, -(int)((Math.random()+1)*8)}
        });
        initializeAsteroid(xposition, yposition);
    }

    public Asteroid(int[][] vertices) {
        super(vertices);

        double h, a;
        a = Math.random() * 2 * Math.PI;
        h = Math.random() * 400 + 100;

        initializeAsteroid(Math.cos(a) * h + Game.width/2.0,
                           Math.sin(a) * h + Game.height/2.0);
    }

    void initializeAsteroid(double xposition, double yposition){
        rotation = Math.random()/5 - 0.1;
        speedFactor = 7.5;
        xspeed = (Math.random() - 0.5) * speedFactor;
        yspeed = (Math.random() - 0.5) * speedFactor;
        this.xposition = xposition;
        this.yposition = yposition;
        active = true;
    }

    public void updateAsteroid(){
        rotateAsteroid();
    }

    private void rotateAsteroid(){
        angle += rotation;
    }
}
