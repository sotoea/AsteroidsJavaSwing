import java.awt.*;
import java.util.ArrayList;

public class Spacecraft extends VectorSprite {

    private final double thrusterPower;
    private final double frictionFactor;
    private final double rotationFactor;

    public Spacecraft(int[][] vertices) {
        super(vertices);
        thrusterPower = 1;
        frictionFactor = 0.975;
        rotationFactor = Math.PI/20;
        active = true;
    }

    public void accelerate(){
        xspeed += Math.cos(angle) * thrusterPower;
        yspeed += Math.sin(angle) * thrusterPower;
    }

    public void decelerate(){
        xspeed -= Math.cos(angle) * thrusterPower;
        yspeed -= Math.sin(angle) * thrusterPower;
    }

    public void applyFriction(){
        xspeed = xspeed * frictionFactor;
        yspeed = yspeed * frictionFactor;
    }

    public void rotateLeft(){
        angle -= rotationFactor;
    }

    public void rotateRight(){
        angle += rotationFactor;
    }

    public void reset(){
        xposition = (double) Game.width /2;
        yposition = (double) Game.height /2;
        xspeed = 0;
        yspeed = 0;
        angle = 0;
        active = true;
    }

    public boolean isRespawnSafe(ArrayList<Asteroid> asteroidList){
        int x, y, h;
        // We use Pythagorean theorem
        for(Asteroid asteroid : asteroidList){
            x = (int)asteroid.xposition - Game.width/2;
            y = (int)asteroid.yposition - Game.height/2;
            h = (int)Math.sqrt(Math.pow(x, 2) + (y * y));
            if(h < 20){
                return false;
            }
        }
        return true;
    }
}


















