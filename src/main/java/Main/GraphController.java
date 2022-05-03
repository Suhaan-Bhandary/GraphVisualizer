package Main;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GraphController {
    private final Graph graph = new Graph();

    // Variables related to graph drawing
    @FXML
    private AnchorPane graphArea;
    private int count = 0;

    // Variable to contain the first circle selected
    private Circle initialSelectedCircle = null;

    // Function to draw circle on the graphArea
    public void addCircle(MouseEvent e)
    {
        System.out.println("hi: " + e.getTarget());
        if(e.getTarget() != graphArea) return;

        // Create a circle with the help of the DrawShapes class
        System.out.println("Creating a Circle");
        DrawShapes.drawNode(e.getX(), e.getY(), count, Color.LIGHTSKYBLUE, graphArea);

        // Add event listener to the circle and the text for creating an edge between two nodes
        Circle circle = (Circle) graphArea.lookup("#circle__" + count);
        circle.setOnMouseClicked(circleClickEvent -> createEdge((Circle) circleClickEvent.getTarget()));

        // Update the values, as a new node is inserted in the graph
        count++;
        graph.insertVertex();
        graph.display();
    }

    private void createEdge(Circle target)
    {
        if (initialSelectedCircle == null)
        {
            System.out.println("First Circle Selected");
            initialSelectedCircle = target;
            target.setFill(Color.CRIMSON);
        }
        else
        {
            // First Check if the current circle and the initialSelectedCircle is same or not, if same return
            if(initialSelectedCircle == target) return;

            System.out.println("Second Circle Selected and Drawing the edge");
            DrawShapes.drawEdge(initialSelectedCircle, target, graphArea);

            // Add edges in the graph matrix
            int u = Integer.parseInt(initialSelectedCircle.getId().split("__")[1]);
            int v = Integer.parseInt(target.getId().split("__")[1]);
            graph.insertEdge(u,v, 1); // Edge weight is constant for now
            graph.display();

            // Reset the color of the initially selected circle
            initialSelectedCircle.setFill(Color.LIGHTSKYBLUE);
            initialSelectedCircle = null;
        }
    }

    // Function to reset the graph and the graph area
    public void resetGraph()
    {
        System.out.println("Resetting the graph");

        // Clearing all the graph data in memory
        count = 0;
        graph.reset();

        // Removing all the visual elements except the headerText
        Label headerText = (Label) graphArea.lookup("#headerText");
        graphArea.getChildren().clear();
        graphArea.getChildren().add(headerText);
    }
}
