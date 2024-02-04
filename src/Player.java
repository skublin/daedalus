import map.Position;

public class Player {
    public final String name;
    public final Character sign;
    public Position<Integer, Integer> position;

    public Player(String name) {
        this.name = name;
        this.sign = name.toUpperCase().charAt(0);
        this.position = null;
    }

    public void setPosition(Position<Integer, Integer> p) {
        this.position = p;
    }

    public void moveTo(Integer x, Integer y) {
        position.setXY(x, y);
    }

    @Override
    public String toString() {
        return String.format("" + sign);
    }
}
