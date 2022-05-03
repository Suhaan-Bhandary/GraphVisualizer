package Main;

import javafx.fxml.FXML;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class GraphController {
    private final Graph graph = new Graph();

    // Variables related to graph drawing
    @FXML
    private AnchorPane graphArea;
    private int count = 0;

    // Function to draw circle on the graphArea
    public void addCircle(MouseEvent e)
    {
        System.out.println("hi: " + e.getTarget());
        if(e.getTarget() != graphArea) return;

        // Create a circle with the help of the DrawShapes class
        System.out.println("Creating a Circle");
        DrawShapes.drawCircle(e.getX(), e.getY(), count, Color.LIGHTSKYBLUE, graphArea);

        // Update the values, as a new node is inserted in the graph
        count++;
        graph.insertVertex();
        graph.display();
    }

}
