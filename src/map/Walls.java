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

    public N getN() {
        return n;
    }

    public E getE() {
        return e;
    }

    public S getS() {
        return s;
    }

    public W getW() {
        return w;
    }
}