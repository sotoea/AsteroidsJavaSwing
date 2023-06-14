import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Game extends JFrame implements KeyListener, ActionListener, ComponentListener {

    // Declaring constants
    public final static int width = 800;
    public final static int height = 600;
    final int startingAsteroids = 1;
    // Declaring variables/objects
    public double fps = 60;
    public Window panel;
    public Spacecraft ship;
    public ArrayList<Asteroid> asteroidList;
    public ArrayList<Bullet> bulletList;

    Timer timer;

    boolean upKey, rightKey, leftKey, downKey, spaceKey, impulseKey;

    // Game constructor
    public Game(int x1, int y1, int x2, int y2) {
        this.setVisible(true);
        this.setTitle("rock go brrrrr");
        this.setResizable(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.addKeyListener(this);
        //this.addComponentListener(this);


        ship = new Spacecraft(
                new int[][]{
                        {15, 0},
                        {-10, 10},
                        {-10, -10}
                });

        asteroidList = new ArrayList<>();
        for(int i = 0; i < startingAsteroids; i++){
            asteroidList.add(new Asteroid(new int[][]{
                    {30, 12},
                    {30, (int)((Math.random()+1)*20)},
                    {-25, 17},
                    {-22, -19},
                    {20, -(int)((Math.random()+1)*20)}
            }));
        }

        bulletList = new ArrayList<>();

        this.add(this.panel = new Window(this), BorderLayout.CENTER);

        this.pack();

        timer = new Timer((int) (1000/fps), this);
        timer.start();
    }

    // Action performed will behave as our game loop
    // since it is combined with our timer
    @Override
    public void actionPerformed(ActionEvent e){
        keyCheck();
        respawnShip();

        // Ship stuff
        ship.applyFriction();
        ship.updatePosition();
        if(ship.active){
            ship.immuneTimer++;
        }

        // Asteroid stuff
        for(int i = asteroidList.size() - 1; i >= 0; i--){
            asteroidList.get(i).updateAsteroid();
            asteroidList.get(i).updatePosition();

            if(!asteroidList.get(i).active){
                if(asteroidList.get(i).iteration <= 0) {
                    asteroidList.add(new Asteroid(asteroidList.get(i).xposition, asteroidList.get(i).yposition, ++asteroidList.get(i).iteration));
                    asteroidList.add(new Asteroid(asteroidList.get(i).xposition, asteroidList.get(i).yposition, asteroidList.get(i).iteration));
                    //asteroidList.add(new Asteroid(asteroidList.get(i).xposition, asteroidList.get(i).yposition, asteroidList.get(i).iteration));
                }
                asteroidList.remove(i);
            }
        }

        // Bullets stuff
        for(int i = bulletList.size() - 1; i >= 0; i--){
            bulletList.get(i).updatePosition();
            if(bulletList.get(i).frameCounter >= 90 || !bulletList.get(i).active){
                bulletList.remove(i);
            }
        }

        checkCollision();

        repaint();
    }

    public void respawnShip(){
        if(!ship.active && ship.frameCounter > 5){
            ship.reset();
            impulseForce(200);
        }
    }

    public void impulseForce(int range){
        for(Asteroid asteroid : asteroidList){
            double xDist = asteroid.xposition - ship.xposition;
            double yDist = asteroid.yposition - ship.yposition;
            double distanceFromShip = Math.sqrt(
                                      Math.pow(xDist, 2) +
                                      Math.pow(yDist, 2));
            if(distanceFromShip < range) {
                double angle = Math.atan2(yDist, xDist);
                asteroid.xspeed = Math.cos(angle) * (Math.random() + 1)/2 * 8;
                asteroid.yspeed = Math.sin(angle) * (Math.random() + 1)/2 * 8;
            }
        }
    }

    public void checkCollision(){
        for (Asteroid asteroid : asteroidList) {
            if (collision(ship, asteroid) && ship.immuneTimer > ship.immuneTime) {
                ship.hit();
                ship.immuneTimer = 0;
                ship.lives--;
            }

            for (Bullet bullet : bulletList) {
                if (collision(asteroid, bullet)) {
                    asteroid.active = false;
                    bullet.active = false;
                }
            }

        }
    }

    public boolean collision(VectorSprite p1, VectorSprite p2){

        for(int i = 0; i < p1.drawShape.npoints; i++){
            if(p2.drawShape.contains(p1.drawShape.xpoints[i], p1.drawShape.ypoints[i])){
                return true;
            }
        }

        for(int i = 0; i < p2.drawShape.npoints; i++){
            if(p1.drawShape.contains(p2.drawShape.xpoints[i], p2.drawShape.ypoints[i])){
                return true;
            }
        }

        return false;
    }

    void fireBullet(){
        if(ship.frameCounter > 1 && ship.active) {
            ship.frameCounter = 0;
            bulletList.add(new Bullet(
                    new int[][]{
                            {2, 6},
                            {-2, 1},
                            {-2, -1},
                            {2, -6},
                            {12, 0}
                    },
                    ship.xposition,
                    ship.yposition,
                    ship.angle,
                    ship.xspeed,
                    ship.yspeed
            ));
        }
    }
    public void keyCheck(){
        if(upKey) ship.accelerate();
        if(downKey) ship.decelerate();
        if(leftKey) ship.rotateLeft();
        if(rightKey) ship.rotateRight();
        if(spaceKey) fireBullet();
        if(impulseKey) impulseForce(100);
    }

    //region KeyListener Implementation
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> {
                upKey = true;
            }
            case KeyEvent.VK_S -> {
                downKey = true;
            }
            case KeyEvent.VK_A -> {
                leftKey = true;
            }
            case KeyEvent.VK_D -> {
                rightKey = true;
            }
            case KeyEvent.VK_SPACE -> {
                spaceKey = true;
            }
            case KeyEvent.VK_E -> {
                impulseKey = true;
            }
            default -> System.out.println(e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> {
                upKey = false;
            }
            case KeyEvent.VK_S -> {
                downKey = false;
            }
            case KeyEvent.VK_A -> {
                leftKey = false;
            }
            case KeyEvent.VK_D -> {
                rightKey = false;
            }
            case KeyEvent.VK_SPACE -> {
                spaceKey = false;
            }
            case KeyEvent.VK_E -> {
                impulseKey = false;
            }
            default -> System.out.println(e.getKeyCode());
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {
        //width = getWidth();
        //height = getHeight();
        //this.add(this.panel = new Window(this), BorderLayout.CENTER);
        //this.pack();
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
    //endregion
}
