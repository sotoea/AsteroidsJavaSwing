import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JPanel {

    private Game game;
    private JButton restart;

    public Window(Game game){

        this.game = game;
        this.setDoubleBuffered(true);

        setPreferredSize(new Dimension(Game.width, Game.height));

        this.setLayout(null);

        restart = new JButton("Restart");
        Dimension restartSize = restart.getPreferredSize();
        restart.setBounds(Game.width/2 - restartSize.width/2,
                            Game.height - 250,
                            restartSize.width,
                            restartSize.height);
        restart.setBackground(Color.GREEN);
        restart.setForeground(Color.BLACK);
        restart.setFocusable(false);
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.timer.stop();
                game.restartGame();
                restart.setVisible(false);
            }
        });

        this.add(restart);
        restart.setVisible(false);
    }

    @Override
    public void paintComponent(Graphics g){
        // Run default code in parent class
        super.paintComponent(g);
        this.setSize(new Dimension(game.getWidth(), game.getHeight()));

        Graphics2D g2d = (Graphics2D)g;

        // Followed by our own code
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(0, 0, Game.width, Game.height);



        if (game.asteroidList.isEmpty()) {
            g2d.setColor(Color.GREEN);
            g2d.setFont(new Font("SansSerif", Font.PLAIN, 25));
            printSimpleString("Game Over - You Win!!! Brrbrr", Game.width / 2, Game.height / 2, g2d);
        }

        if (game.ship.active) {

            g2d.setColor(Color.GREEN);
            if(game.ship.immuneTimer <= game.ship.immuneTime){
                ImmunityRing ring =
                        new ImmunityRing(
                                game.ship.xposition - 20,
                                game.ship.yposition - 20,
                                360 - (game.ship.immuneTimer * ((double) 360 /game.ship.immuneTime))
                        );
                g2d.draw(ring.ring);
            }
            Color shipColor = (game.ship.immuneTimer > game.ship.immuneTime) ? new Color(0, 255, 255) : new Color(255, 255, 255);
            game.ship.paint(g2d,
                    new Color(0, 127, 127),
                    shipColor
            );
            game.thrusterSprite.paint(g2d,new Color(0, 127, 127),
                    shipColor);
        }

        for (Asteroid asteroid : game.asteroidList) {
            if (asteroid.active)
                asteroid.paint(g2d,
                        new Color(112, 70, 30),
                        new Color(63, 35, 4)
                );
        }

        for (Bullet bullet : game.bulletList) {
            if (bullet.active)
                bullet.paint(g2d,
                        new Color(137, 26, 26),
                        Color.red
                );
        }
        g2d.setColor(Color.GREEN);
        g2d.setFont(new Font("SansSerif", Font.BOLD, 12));
        if(game.ship.lives >= 0) {
            printSimpleString("Lives: " + game.ship.lives, Game.width / 2, 20, g2d);
        }else{
            restart.setVisible(true);
            printSimpleString("Game Over\nYou LOSE!!!\nBrrbrr", Game.width / 2, Game.height / 2, g2d);
        }

    }

    public static void printSimpleString(String s, int XPos, int YPos, Graphics2D g2d){
        String[] sList = s.split("\n");
        for(int i = 0; i < sList.length; i++){
            int stringLen = (int)
                    g2d.getFontMetrics().getStringBounds(sList[i], g2d).getWidth();
            int start = -stringLen/2;
            g2d.drawString(sList[i], start + XPos, YPos + i * 20);
        }
    }
}
