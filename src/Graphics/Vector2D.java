package Graphics;

public class Vector2D {
    public float x;
    public float y;

    public Vector2D(float x, float y){
        this.x = x;
        this.y = y;
    }

    public Vector2D(Vector2D vector){
        this.x = vector.x;
        this.y = vector.y;
    }
}
