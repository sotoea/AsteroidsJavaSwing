import javax.swing.*;
import java.awt.*;

public class Window extends JPanel {

    private Game game;

    public Window(Game game){

        this.game = game;
        this.setDoubleBuffered(true);
        setPreferredSize(new Dimension(Game.width, Game.height));
        //setBackground(Color.black);
    }

    @Override
    public void paintComponent(Graphics g){
        // Run default code in parent class
        super.paintComponent(g);
        // Followed by our own code
        game.offg.setStroke(new BasicStroke(2));
        game.offg.setColor(Color.DARK_GRAY);
        game.offg.fillRect(0, 0, Game.width, Game.height);
        game.offg.setColor(Color.GREEN);

        if(game.ship.active) {
            game.ship.paint(game.offg,
                            new Color(0, 127, 127),
                            new Color(0, 255, 255)
            );
        }

        for(Asteroid asteroid : game.asteroidList){
            if(asteroid.active)
                asteroid.paint(game.offg,
                    new Color(112, 70, 30),
                    new Color(63, 35, 4)
            );
        }

        for(Bullet bullet : game.bulletList){
            if(bullet.active)
                bullet.paint(game.offg,
                    new Color(137, 26, 26),
                    Color.red
            );
        }
        //game.obj.paint(game.offg);
        g.drawImage(game.offscreen, 0, 0, this);
    }
}
