package pl.edu.agh.mownit.lab2;

import org.jgrapht.graph.SimpleWeightedGraph;

/**
 * Created by Paweł Grochola on 03.11.2017.
 */
public class Graph extends SimpleWeightedGraph<String, Edge> {
    public Graph() {
        super(Edge.class);
    }
}
