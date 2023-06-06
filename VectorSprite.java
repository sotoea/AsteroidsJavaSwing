import java.awt.*;

public class VectorSprite {

    double xposition, yposition;
    double xspeed, yspeed;
    double angle;
    boolean active;
    int frameCounter = 0;

    Polygon shape, drawShape;   // each vectorSprite will have 2 polygons, for rotation, for drawing

    // Constructor
    public VectorSprite(int[][] vertices){
        shape = new Polygon();
        for(int i = 0; i < vertices.length; i++){
            shape.addPoint(vertices[i][0], vertices[i][1]);
        }

        drawShape = new Polygon();
        for(int i = 0; i < vertices.length; i++){
            drawShape.addPoint(vertices[i][0], vertices[i][1]);
        }

        xposition = (double) Game.width /2;
        yposition = (double) Game.height /2;
    }

    public void paint(Graphics g){
        g.setColor(Color.GREEN);
        g.drawPolygon(drawShape);
    }

    // Add a paint function for our vectorsprites
    public void paint(Graphics g, Color fill, Color outline){
        g.setColor(fill);
        g.fillPolygon(drawShape);
        g.setColor(outline);
        g.drawPolygon(drawShape);
    }

    public void updatePosition() {
        frameCounter++;
        xposition += xspeed;
        yposition += yspeed;

        screenWrap();

        int x, y;

        for(int i = 0; i < shape.npoints; i++){
            x = (int) Math.round(shape.xpoints[i] * Math.cos(angle) - shape.ypoints[i] * Math.sin(angle));
            y = (int) Math.round(shape.xpoints[i] * Math.sin(angle) + shape.ypoints[i] * Math.cos(angle));

            drawShape.xpoints[i] = x;
            drawShape.ypoints[i] = y;
        }
        drawShape.translate((int)Math.round(xposition), (int)Math.round(yposition));
        drawShape.invalidate();

    }
    public void hit(){
        active = false;
        frameCounter = 0;
    }
    public void screenWrap(){
        if(xposition < -15) xposition = Game.width + 15;
        else if(xposition > Game.width + 15) xposition = -15;
        if(yposition < -15) yposition = Game.height + 15;
        else if(yposition > Game.height + 15) yposition = -15;
    }
}
