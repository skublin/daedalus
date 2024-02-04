package map;

public class Position<X, Y> {
    public X x;
    public Y y;

    public Position(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    public void setX(X nx) {
        this.x = nx;
    }

    public void setY(Y ny) {
        this.y = ny;
    }

    public void setXY(X nx, Y ny) {
        setX(nx);
        setY(ny);
    }
}