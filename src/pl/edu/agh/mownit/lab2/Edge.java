package pl.edu.agh.mownit.lab2;

/**
 * Created by Paweł Grochola on 03.11.2017.
 */
public class Edge {
    public enum Type {
        RESISTANCE,
        EMF
    }

    private final String firstVertex;
    private final String secondVertex;
    private final Type type;
    private final double value;
    private final int index;

    public Edge(final String firstVertex, final String secondVertex, final Type type, final double value, final int index) {
        this.firstVertex = firstVertex;
        this.secondVertex = secondVertex;
        this.type = type;
        this.value = value;
        this.index = index;
    }

    public String getFirstVertex() {
        return firstVertex;
    }

    public String getSecondVertex() {
        return secondVertex;
    }

    public Type getType() {
        return type;
    }

    public double getValue() {
        return value;
    }

    public int getIndex() {
        return index;
    }

    public boolean containsVertices(final String vertex1, final String vertex2) {
        return firstVertex.equals(vertex1) && secondVertex.equals(vertex2)
                || firstVertex.equals(vertex2) && secondVertex.equals(vertex1);
    }

    public String getSmallerVertex() {
        if (Integer.parseInt(firstVertex) < Integer.parseInt(secondVertex)) {
            return firstVertex;
        } else {
            return secondVertex;
        }
    }

    @Override
    public String toString() {
        return "Edge{" +
                "type=" + type +
                ", value=" + value +
                ", index=" + index +
                '}';
    }
}

