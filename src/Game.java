import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {
    private boolean isRunning = false;
    private Thread thread;
    private Handler handler;
    private BufferedImage level = null;
    private BufferedImage spriteSheet = null;
    private Camera camera;
    private SpriteSheet ss;
    public int ammo = 100;
    private BufferedImage floor = null;
    public int hp = 100;

    public Game() {
        new Window(1280, 720, "Wizard Game", this);
        start();

        handler = new Handler();
        camera = new Camera(0, 0);
        this.addKeyListener(new KeyInput(handler));
        BufferedImageLoader loader = new BufferedImageLoader();
        level = loader.loadImage("/level.png");
        //spriteSheet = loader.loadImage("/sprite_sheet.png");
        spriteSheet = loader.loadImage("/images/index.png");
        ss = new SpriteSheet(spriteSheet);
        floor = ss.grabImage(32, 32, 10, 131);
        this.addMouseListener(new MouseInput(handler, camera, this, ss));
        loadLevel(level);
    }

    public static void main(String[] args) {
        new Game();
    }

    private void loadLevel(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();

        for (int xx = 0; xx < w; xx++) {
            for (int yy = 0; yy < h; yy++) {
                int pixel = image.getRGB(xx, yy);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;
                if (red == 255) {
                    handler.addObject(new Block(xx * 32, yy * 32, ID.BLOCK, ss));
                }
                if (blue == 255 && green == 0) {
                    handler.addObject(new Wizard(xx * 32, yy * 32, ID.PLAYER, handler, this, ss));
                }
                if (green == 255 && blue == 0) {
                    handler.addObject(new Enemy(xx * 32, yy * 32, ID.ENEMY, handler, ss));
                }
                if (green == 255 && blue == 255) {
                    handler.addObject(new Crate(xx * 32, yy * 32, ID.CRATE, ss));
                }
            }
        }
    }

    private void start() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    private void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frames = 0;
            }
        }
        stop();
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;
        ///////////////////////////////////
        g2d.translate(-camera.getX(), -camera.getY());

        for (int xx = 0; xx < 30 * 72; xx += 32) {
            for (int yy = 0; yy < 30 * 72; yy += 32) {
                g.drawImage(floor, xx, yy, null);
            }
        }

        handler.render(g);
        g2d.translate(camera.getX(), camera.getY());

        g.setColor(Color.GRAY);
        g.fillRect(5,5,200,32);
        g.setColor(Color.GREEN);
        g.fillRect(5,5,hp*2,32);
        g.setColor(Color.black);
        g.drawRect(5, 5, 200, 32);

        g.setColor(Color.WHITE);
        g.drawString("Bullets: " + ammo, 5, 50);
        ////////////////////////////////////////
        g.dispose();
        bs.show();
    }

    private void tick() {
        for (int i = 0; i < handler.gameObjects.size(); i++) {
            if (handler.gameObjects.get(i).getId() == ID.PLAYER) {
                camera.tick(handler.gameObjects.get(i));
            }
        }
        handler.tick();
    }
}
