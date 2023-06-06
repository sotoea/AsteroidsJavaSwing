public class Asteroid extends VectorSprite {

    public double rotation;
    private double speedFactor;
    public int iteration;

    public Asteroid(double xposition, double yposition, int iteration){
        super(new int[][]{
                {30/iteration, 3},
                {5/iteration, (int)((Math.random()+1)*15)/iteration},
                {-25/iteration, 10/iteration},
                {-17/iteration, -15/iteration},
                {20/iteration, -(int)((Math.random()+1)*15/iteration)}
        });
        this.iteration = iteration;
        initializeAsteroid(xposition, yposition);
    }

    public Asteroid(int[][] vertices) {
        super(vertices);
        iteration = 0;
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
