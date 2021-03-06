package pl.edu.agh.mownit.lab2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Paweł Grochola on 03.11.2017.
 */

public class GraphReader {
    private final Scanner scanner;

    public GraphReader(final String filePath) throws FileNotFoundException {
        this.scanner = new Scanner(new File(filePath));
    }

    public Graph readFromFile() {
        final Graph graph = new Graph();
        int currentIndex = 0;
        while (scanner.hasNextLine()) {
            final String line = scanner.nextLine();
            final String[] splitLine = line.split(" ");
            final String firstVertex = splitLine[0];
            final String secondVertex = splitLine[1];
            final double value = Double.parseDouble(splitLine[2]);
            final Edge.Type type = scanner.hasNextLine() ? Edge.Type.RESISTANCE : Edge.Type.EMF;
            final Edge edge = new Edge(firstVertex, secondVertex, type, value, currentIndex);
            graph.addVertex(firstVertex);
            graph.addVertex(secondVertex);
            graph.addEdge(firstVertex, secondVertex, edge);
            currentIndex++;
        }
        return graph;
    }
}
