import java.awt.*;
import java.awt.image.BufferedImage;

public class Wizard extends GameObject {
    Handler handler;
    Game game;
    private final BufferedImage[] wizardImage = new BufferedImage[3];
    private BufferedImage wizardRight;
    private BufferedImage wizardLeft;
    private BufferedImage wizardTop;
    Animation animation;

    public Wizard(int x, int y, ID id, Handler handler, Game game, SpriteSheet ss) {
        super(x, y, id, ss);
        this.handler = handler;
        this.game = game;
        wizardImage[0] = ss.grabImage(32, 49, 10, 10);
        wizardImage[1] = ss.grabImage(32, 49, 62, 10);
        wizardImage[2] = ss.grabImage(32, 49, 114, 10);
        wizardRight = ss.grabImage(49, 32, 79, 79);
        wizardLeft = ss.grabImage(49, 32, 10, 79);
        wizardTop = ss.grabImage(32, 49, 166, 10);

        animation = new Animation(3, wizardImage);
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;
        collision();

        if (handler.isUp()) velY = -5;
        else if (!handler.isDown()) velY = 0;

        if (handler.isDown()) velY = 5;
        else if (!handler.isUp()) velY = 0;

        if (handler.isRight()) velX = 5;
        else if (!handler.isLeft()) velX = 0;

        if (handler.isLeft()) velX = -5;
        else if (!handler.isRight()) velX = 0;

        animation.runAnimation();
    }

    private void collision() {
        for (int i = 0; i < handler.gameObjects.size(); i++) {
            try {
                GameObject gameObject = handler.gameObjects.get(i);
                if (gameObject.getId() == ID.BLOCK) {
                    if (getBounds().intersects(gameObject.getBounds())) {
                        x += velX * -1;
                        y += velY * -1;
                    }
                }
                if (gameObject.getId() == ID.CRATE) {
                    if (getBounds().intersects(gameObject.getBounds())) {
                        game.ammo += 10;
                        handler.removeObject(gameObject);
                    }
                }
                if (gameObject.getId() == ID.ENEMY) {
                    if (getBounds().intersects(gameObject.getBounds())) {
                        game.hp--;
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void render(Graphics g) {
        if (velX == 0 && velY == 0) {
            g.drawImage(wizardImage[0], x, y, null);
        } else if ((velX < 0 && velY == 0)) {
            g.drawImage(wizardLeft, x, y, null);
        } else if (velX > 0 && velY == 0) {
            g.drawImage(wizardRight, x, y, null);
        } else if (velY < 0 && velX == 0) {
            g.drawImage(wizardTop, x, y, null);
        } else {
            animation.drawAnimation(g, x, y, 0);
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 48);
    }
}
