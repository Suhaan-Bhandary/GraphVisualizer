package Main;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class DrawShapes {
    public static void drawNode(double x, double y, int number, Color color, AnchorPane graphArea)
    {
        // This function creates a circle and also add a text to it and makes it a child of graphArea
        // And also assigns an id to it
        Circle circle = new Circle(x, y, 30, color);
        Text text = new Text(x - 3,y + 3, String.valueOf(number));

        // Setting the view order of the circle and text, so that text is visible and circle doesn't overlap
        circle.setViewOrder(1);
        text.setViewOrder(0);

        // Creating id's for circle and the text
        circle.setId("circle__" + number);
        text.setId("text__" + number);

        // Adding both circle and text as children of the graph area
        graphArea.getChildren().add(circle);
        graphArea.getChildren().add(text);
    }

    public static void drawEdge(Circle source, Circle destination, int weight, AnchorPane graphArea) {
        // getting the starting and ending points for drawing the line
        double x1 = source.getCenterX(), y1 = source.getCenterY();
        double x2 = destination.getCenterX(), y2 = destination.getCenterY();

        // Creating a line :
        Line line = new Line(x1, y1, x2, y2);
        line.setStrokeWidth(3);
        line.setViewOrder(100);

        // Setting id of the line
        int u = Integer.parseInt(source.getId().split("__")[1]);
        int v = Integer.parseInt(destination.getId().split("__")[1]);
        line.setId("line__" + u + "__" + v);

        // Adding the line to the graphArea
        graphArea.getChildren().add(line);

        // Let's add text to show the weight of the edge
        Label edgeWeightText = new Label(Integer.toString(weight));
        edgeWeightText.setLayoutX((x1 + x2) / 2 - 12);
        edgeWeightText.setLayoutY((y1 + y2) / 2 - 12);
        edgeWeightText.setBackground(Background.fill(Color.WHITE));
        edgeWeightText.setPadding(new Insets(2));
        edgeWeightText.setStyle("-fx-border-color: black;-fx-border-radius: 5px;");
        edgeWeightText.setViewOrder(0);
        if(weight != 1) graphArea.getChildren().add(edgeWeightText);
    }
}
