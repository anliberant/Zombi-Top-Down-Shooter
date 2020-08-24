import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy extends GameObject {
    private Handler handler;
    Random r = new Random();
    int choose = 0, hp = 100;
    private BufferedImage[] enemyImage = new BufferedImage[3];
    private Animation animation;

    public Enemy(int x, int y, ID id, Handler handler, SpriteSheet ss) {
        super(x, y, id, ss);
        this.handler = handler;
//        enemyImage[0] = ss.grabImage(1, 11, 32, 32);
//        enemyImage[1] = ss.grabImage(1, 12, 32, 32);
//        enemyImage[2] = ss.grabImage(1, 13, 32, 32);
        enemyImage[0] = ss.grabImage(32, 30, 218, 10);
        enemyImage[1] = ss.grabImage(32, 30, 166, 131);
        enemyImage[2] = ss.grabImage(32, 30, 218, 60);


        animation = new Animation(3, enemyImage);
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;
        choose = r.nextInt(10);
        for (int i = 0; i < handler.gameObjects.size(); i++) {
            GameObject gameObject = handler.gameObjects.get(i);
            if (gameObject.getId() == ID.BLOCK) {
                if (getBoundsBig().intersects(gameObject.getBounds())) {
                    x += (velX * 2) * -1;
                    y += (velY * 2) * -1;
                    velX *= -1;
                    velY *= -1;
                } else if (choose == 0) {
                    velX = (r.nextInt(4 - -4) + -4);
                    velY = (r.nextInt(4 - -4) + -4);
                }
            }
            if (gameObject.getId() == ID.BULLET) {
                if (getBounds().intersects(gameObject.getBounds())) {
                    hp -= 50;
                    handler.removeObject(gameObject);
                }
            }
        }
        animation.runAnimation();
        if (hp <= 0) {
            handler.removeObject(this);
        }
    }

    @Override
    public void render(Graphics g) {
        animation.drawAnimation(g, x, y, 0);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }

    public Rectangle getBoundsBig() {
        return new Rectangle(x - 16, y - 16, 64, 64);
    }
}
