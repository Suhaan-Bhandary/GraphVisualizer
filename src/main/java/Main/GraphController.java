package Main;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Set;

public class GraphController {
    private final Graph graph = new Graph();

    // Variables related to graph drawing
    @FXML
    private AnchorPane graphArea, gridMatrixArea;
    @FXML
    private TextField sourceField, destinationField, weightField;

    @FXML
    private Slider speedSlider;

    @FXML
    private Label algorithmStatus;

    private int count = 0;

    // Variable to contain the first circle selected
    private Circle initialSelectedCircle = null;

    // Function to visualize BFS
    public void animateBFS()
    {
        if(sourceField.getText().isEmpty() || destinationField.getText().isEmpty())
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

        // Change the text of the label
        if(reversePath.size() > 0)
        {
            algorithmStatus.setText("Status: BFS Completed");
        }
        else
        {
            algorithmStatus.setText("Status: Source and destination are not connected!");
        }
    }

    public void animateDFS()
    {
        if(sourceField.getText().isEmpty() || destinationField.getText().isEmpty())
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
        System.out.println("DFS: " + source + " -> " + destination);
        Pair<ArrayList<Integer>, Boolean> result = graph.dfs(source, destination);

        ArrayList<Integer> layer = result.getKey();
        Boolean isDestinationFound = result.getValue();

        // Animate the layers and path using thread and sleep methods
        Animation.animateDFS(source, destination, layer, graphArea);

        // Change the text of the label
        if(isDestinationFound)
        {
            algorithmStatus.setText("Status: DFS Completed");
        }
        else
        {
            algorithmStatus.setText("Status: No Connection between Source and Destination");
        }
    }

    // animate dijkstra
    public void animateDijkstra(){
        if(sourceField.getText().isEmpty() || destinationField.getText().isEmpty())
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

        // Run dijkstra on the graph class and get the layers and path
        System.out.println("DFS: " + source + " -> " + destination);


        Pair<ArrayList<Integer>, ArrayList<Integer>> result =  graph.dijkstra(source,destination);
        ArrayList<Integer> layer = result.getKey();
        ArrayList<Integer> reversePath = result.getValue();

        // Animate the layers and path using thread and sleep methods
        Animation.animateDijkstra(source, destination, layer, reversePath, graphArea);

        // Change the text of the label
        if(reversePath.size() > 0)
        {
            algorithmStatus.setText("Status: Dijkstra Completed");
        }
        else
        {
            algorithmStatus.setText("Status: Source and destination are not connected!");
        }
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

        // Updating the matrix
        DrawShapes.drawMatrix(graph.getMatrix(), graph.getVerticesCount(), gridMatrixArea);
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
            // Get the weight for the edge
            if(weightField.getText().isEmpty())
            {
                // Alert the user that the fields are required
                Alert alert = new Alert(Alert.AlertType.ERROR);
                String content = "Please fill the weight field!!";
                alert.setContentText(content);
                alert.showAndWait();
                return;
            }

            int edgeWeight;
            try
            {
                edgeWeight = Integer.parseInt(weightField.getText());
                // Checking if the fields are in valid range, if not then exception is raised
                if(edgeWeight <= 0 ) throw new Exception();
            }
            catch (Exception e)
            {
                // Alert the user that the given input is invalid
                Alert alert = new Alert(Alert.AlertType.ERROR);
                String content = "Edge weight cannot be negative";
                alert.setContentText(content);
                alert.showAndWait();
                return;
            }

            // Draw the edge
            DrawShapes.drawEdge(initialSelectedCircle, target, edgeWeight, graphArea);

            // Add edges in the graph matrix
            int u = Integer.parseInt(initialSelectedCircle.getId().split("__")[1]);
            int v = Integer.parseInt(target.getId().split("__")[1]);
            graph.insertEdge(u,v, edgeWeight); // Edge weight is constant for now
            graph.display();

            // Reset the color of the initially selected circle
            initialSelectedCircle.setFill(Color.LIGHTSKYBLUE);
            initialSelectedCircle = null;

            // Update the matrix
            DrawShapes.drawMatrix(graph.getMatrix(), graph.getVerticesCount(), gridMatrixArea);
        }
    }

    public void showComponents()
    {
        ArrayList<ArrayList<Integer>> components = graph.getComponents();
        System.out.println("Components in the graph: " + components);
        Animation.animateComponents(components, graphArea);
    }

    public void showSpanningTree()
    {
        ArrayList<Pair<Integer, Integer>> components = graph.getSpanningTree();
        System.out.println("Components in the graph: " + components);

        if(components.size() > 0)
        {
            algorithmStatus.setText("Found the Minimum Spanning Tree using Kruskal");
            Animation.animateSpanningTree(components, graphArea);
        }
        else
        {
            algorithmStatus.setText("Cannot find Minimum Spanning Tree");
        }
    }

    public void showBridges()
    {
        ArrayList<Pair<Integer, Integer>> bridges = graph.getBridges();
        System.out.println("Bridges in the graph: " + bridges);

        if(bridges.size() > 0)
        {
            algorithmStatus.setText("Calculated All the Bridges in the Graph");
            Animation.animateBridges(bridges, graphArea);
        }
        else
        {
            algorithmStatus.setText("No Bridges Found in the graph");
        }
    }

    public void showArticulationPoints()
    {
        Set<Integer> articulationPoints = graph.getArticulationPoints();
        System.out.println("Articulation points in the graph are: " + articulationPoints);

        if(articulationPoints.size() > 0)
        {
            algorithmStatus.setText("Calculated all Articulation Points in the Graph");
            Animation.animateArticulationPoints(articulationPoints, graphArea);
        }
        else
        {
            algorithmStatus.setText("No Articulation Point Found in the graph");
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

        // Resetting the matrix
        DrawShapes.drawMatrix(graph.getMatrix(), graph.getVerticesCount(), gridMatrixArea);

        // Make the initialSelectedCircle graph as null
        initialSelectedCircle = null;

        // Clear the algorithm labels
        algorithmStatus.setText("");
    }

    public void clearGraph()
    {
        System.out.println("Clearing the Graph");

        // Removing the selected node selection
        initialSelectedCircle = null;

        // Clear all the circles and lines to its default value
        for(Node node: graphArea.getChildren())
        {
            if (node instanceof Line line)
            {
                line.setStroke(Color.BLACK);
                line.setStrokeWidth(3);
                line.setViewOrder(100);
            }
            else if(node instanceof Circle circle)
            {
                circle.setStrokeWidth(0);
                circle.setFill(Color.LIGHTSKYBLUE);
            }
        }

        // Clear the algorithm labels
        algorithmStatus.setText("");
    }

    public void setAnimationSpeed()
    {
        // Set the value of static in the Animation class
        Animation.setSliderSpeed((int)speedSlider.getValue());
    }
}
