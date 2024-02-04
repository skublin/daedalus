package map;

import enums.CommandEnum;
import enums.MoveEnum;

import java.util.HashMap;
import java.util.Map;

public class Walls<N, E, S, W> {
    public N n;
    public E e;
    public S s;
    public W w;

    public Walls(N n, E e, S s, W w) {
        this.n = n;
        this.e = e;
        this.s = s;
        this.w = w;
    }

    public void setN(N newN) {
        n = newN;
    }

    public void setE(E newE) {
        e = newE;
    }

    public void setS(S newS) {
        s = newS;
    }

    public void setW(W newW) {
        w = newW;
    }

    public Boolean getN() {
        try {
            if (n != null && (Boolean) n) return true;
            if (n != null && !((Boolean) n)) return false;
            return false;
        } catch (ClassCastException ex) {
            throw new IllegalArgumentException("N, E, S, and W must be of type Boolean.");
        }
    }

    public Boolean getE() {
        try {
            if (e != null && (Boolean) e) return true;
            if (e != null && !((Boolean) e)) return false;
            return false;
        } catch (ClassCastException ex) {
            throw new IllegalArgumentException("N, E, S, and W must be of type Boolean.");
        }
    }

    public Boolean getS() {
        try {
            if (s != null && (Boolean) s) return true;
            if (s != null && !((Boolean) s)) return false;
            return false;
        } catch (ClassCastException ex) {
            throw new IllegalArgumentException("N, E, S, and W must be of type Boolean.");
        }
    }

    public Boolean getW() {
        try {
            if (w != null && (Boolean) w) return true;
            if (w != null && !((Boolean) w)) return false;
            return false;
        } catch (ClassCastException ex) {
            throw new IllegalArgumentException("N, E, S, and W must be of type Boolean.");
        }
    }

    public String getTrueWalls() {
        StringBuilder result = new StringBuilder();

        try {
            if (n != null && (Boolean) n) result.append(" North");
            if (e != null && (Boolean) e) result.append(" East");
            if (s != null && (Boolean) s) result.append(" South");
            if (w != null && (Boolean) w) result.append(" West");
        } catch (ClassCastException ex) {
            // Handle the case where N, E, S, or W is not a Boolean
            // For example, you could log this or throw a more specific exception
            throw new IllegalArgumentException("N, E, S, and W must be of type Boolean");
        }

        if (result.length() > 0) result.append(".\n");

        return result.toString();
    }
}