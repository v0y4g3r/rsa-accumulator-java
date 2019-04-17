package edu.pku;

public class Pair<T> {
    private T frist;

    public T getSecond() {
        return second;
    }

    public Pair<T> setSecond(T second) {
        this.second = second;
        return this;
    }

    private T second;

    public Pair(T frist, T second) {
        this.frist = frist;
        this.second = second;
    }

    public T getFrist() {
        return frist;
    }

    public Pair<T> setFrist(T frist) {
        this.frist = frist;
        return this;
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("Pair{");
        sb.append("frist=").append(frist);
        sb.append(", second=").append(second);
        sb.append('}');
        return sb.toString();
    }
}
