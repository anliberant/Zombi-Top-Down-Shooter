import java.awt.*;
import java.awt.image.BufferedImage;

public class Block extends GameObject {
    private BufferedImage blockImage;

    public Block(int x, int y, ID id, SpriteSheet ss) {
        super(x, y, id, ss);
        BufferedImageLoader loader = new BufferedImageLoader();

        blockImage = ss.grabImage(32, 32, 166, 79);


    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(blockImage, x, y, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }
}
