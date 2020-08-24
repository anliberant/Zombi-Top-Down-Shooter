import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
    Handler handler;

    public KeyInput(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        for (int i = 0; i < handler.gameObjects.size(); i++) {
            try {
                GameObject object = handler.gameObjects.get(i);

                if (object.getId() == ID.PLAYER) {
                    if (keyCode == KeyEvent.VK_W) handler.setUp(true);
                    if (keyCode == KeyEvent.VK_S) handler.setDown(true);
                    if (keyCode == KeyEvent.VK_A) handler.setLeft(true);
                    if (keyCode == KeyEvent.VK_D) handler.setRight(true);
                }
            } catch (NullPointerException exception){
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        for (int i = 0; i < handler.gameObjects.size(); i++) {
            GameObject object = handler.gameObjects.get(i);

            if (object.getId() == ID.PLAYER) {
                if (keyCode == KeyEvent.VK_W) handler.setUp(false);
                if (keyCode == KeyEvent.VK_S) handler.setDown(false);
                if (keyCode == KeyEvent.VK_A) handler.setLeft(false);
                if (keyCode == KeyEvent.VK_D) handler.setRight(false);
            }
        }
    }
}
