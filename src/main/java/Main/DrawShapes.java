package Main;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class DrawShapes {
    public static void drawCircle(double x, double y, int number,Color color, AnchorPane graphArea)
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
}
