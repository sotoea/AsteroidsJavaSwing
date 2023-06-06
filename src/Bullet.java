public class Bullet extends VectorSprite {
    public Bullet(int[][] vertices, double shipX, double shipY, double shipAngle, double shipXSpeed, double shipYSpeed) {
        super(vertices);
        active = true;
        xposition = shipX;
        yposition = shipY;
        angle = shipAngle;
        double shipSpeed = Math.sqrt(Math.pow(shipXSpeed, 2) + Math.pow(shipYSpeed, 2));
        xspeed = Math.cos(angle) * (shipSpeed + 6);
        yspeed = Math.sin(angle) * (shipSpeed + 6);
    }

    // Replace screen-wrapping with removing object for bullets
    @Override
    public void screenWrap(){
        if(xposition < -15 || xposition > Game.width + 15 ||
           yposition < -15 || yposition > Game.height + 15)
            active = false;

    }
}
