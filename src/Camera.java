public class Camera {
    private float x, y;

    public Camera(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void tick(GameObject gameObject) {
        x += ((gameObject.getX() - x) - 1280 / 2 * 0.05f);
        y += ((gameObject.getY() - y) - 720 / 2 * 0.05f);
        if (x <= 0) x = 0;
        if (x >= 800 - 16) x = 800 - 16;
        if (y <= 0) y = 0;
        if (y >= 1400 - 36) y = 1400 - 36;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
