/**
 * Created by Paweł Grochola on 03.11.2017.
 */
public class Edge {
    public enum Type {
        RESISTANCE,
        EMF
    }
    private final Type type;
    private final double value;

    public Edge(final Type type, final double value) {
        this.type = type;
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public double getValue() {
        return value;
    }
}

