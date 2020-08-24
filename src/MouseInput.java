import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {
    private Handler handler;
    private Camera camera;
    private Game game;
    private SpriteSheet ss;

    public MouseInput(Handler handler, Camera camera, Game game, SpriteSheet ss) {
        this.handler = handler;
        this.camera = camera;
        this.game = game;
        this.ss = ss;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = (int) (e.getX() + camera.getX());
        int my = (int) (e.getY() + camera.getY());

        for (int i = 0; i < handler.gameObjects.size(); i++) {
            GameObject gameObject = handler.gameObjects.get(i);
            if (gameObject.getId() == ID.PLAYER && game.ammo >= 1) {
                handler.addObject(new Bullet(gameObject.getX() + 16, gameObject.getY() + 24, ID.BULLET, handler, mx, my, ss));
                game.ammo--;
            }
        }
    }
}
