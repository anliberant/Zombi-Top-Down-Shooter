import java.awt.*;
import java.awt.image.BufferedImage;

public class Crate extends GameObject {
    private BufferedImage crateImage;
    public Crate(int x, int y, ID id, SpriteSheet ss) {
        super(x, y, id, ss);
       // crateImage = ss.grabImage(6, 2, 32,32);
        crateImage = ss.grabImage(32, 32, 62,131);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(crateImage, x, y, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }
}
