package Main;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Pair;

import java.util.ArrayList;

public class GraphController {
    private final Graph graph = new Graph();

    // Variables related to graph drawing
    @FXML
    private AnchorPane graphArea;
    @FXML
    private TextField sourceField, destinationField, weightField;

    private int count = 0;

    // Variable to contain the first circle selected
    private Circle initialSelectedCircle = null;

    // Function to visualize BFS
    public void animateBFS()
    {
        if(sourceField.getText().isEmpty() || destinationField.getText().isEmpty() || destinationField.getText().isEmpty())
        {
            // Alert the user that the fields are required
            Alert alert = new Alert(Alert.AlertType.ERROR);
            String content = "Please fill all requested fields. i.e: source, destination";
            alert.setContentText(content);
            alert.showAndWait();
            return;
        }

        int source, destination;
        try
        {
            source = Integer.parseInt(sourceField.getText());
            destination = Integer.parseInt(destinationField.getText());

            // Checking if the fields are in valid range, if not then exception is raised
            if(source < 0 || source >= count || destination < 0 || destination >= count)
            {
                throw new Exception();
            }
        }
        catch (Exception e)
        {
            // Alert the user that the given input is invalid
            Alert alert = new Alert(Alert.AlertType.ERROR);
            String content = "Please enter valid input in Source and Destination Field";
            alert.setContentText(content);
            alert.showAndWait();
            return;
        }

        // Run BFS on the graph class and get the layers and path
        System.out.println("BFS: " + source + " -> " + destination);
        Pair<ArrayList<ArrayList<Integer>>, ArrayList<Integer>> result = graph.bfs(source, destination);
        ArrayList<ArrayList<Integer>> layers = result.getKey();
        ArrayList<Integer> reversePath = result.getValue();

        // Animate the layers and path using thread and sleep methods
        Animation.animateBFS(source, destination, layers, reversePath, graphArea);
    }

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
