import java.awt.*;

public class Bullet extends GameObject {
    private Handler handler;

    public Bullet(int x, int y, ID id, Handler handler, int mx, int my, SpriteSheet ss) {
        super(x, y, id, ss);
        this.handler = handler;
        velX = (mx - x) / 10;
        velY = (my - y) / 10;
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        for (int i = 0; i < handler.gameObjects.size(); i++) {
            if (handler.gameObjects.get(i) != null) {
                GameObject gameObject = handler.gameObjects.get(i);

                if (gameObject.getId() == ID.BLOCK) {
                    if (getBounds().intersects(gameObject.getBounds())) {
                        handler.removeObject(this);
                    }
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillOval(x, y, 8, 8);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 8, 8);
    }
}
