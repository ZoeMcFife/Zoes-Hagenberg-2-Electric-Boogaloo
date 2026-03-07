package space.zoemcfife.shapes;

import geometry.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import java.io.IOException;

public class ShapeApplication extends Application
{
    @Override
    public void start(Stage stage) throws IOException
    {
        stage.setTitle("Drawing Operations Test");
        Group root = new Group();
        Canvas canvas = new Canvas(800, 600);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawShapes(gc);

        root.getChildren().add(canvas);
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void drawShapes(GraphicsContext gc)
    {
        Point point = new Point(new Vector2(100, 100));
        Circle circle = new Circle(new Vector2(400, 300), 150);
        Ellipse ellipse = new Ellipse(new Vector2(600, 200), 100, 50);
        Line line = new Line(new Vector2(200, 500), new Vector2(700, 200));
        Rectangle rectangle = new Rectangle(new Vector2(300, 400), 200, 100);
        Star star = new Star(new Vector2(500, 100), 25, 50, 5);
        Triangle triangle = new Triangle(new Vector2(160, 500), 60, 90, 50);

        Drawer drawer = new Drawer(gc);

        drawer.addPolygon(point);
        drawer.addPolygon(circle);
        drawer.addPolygon(ellipse);
        drawer.addPolygon(line);
        drawer.addPolygon(rectangle);
        drawer.addPolygon(star);
        drawer.addPolygon(triangle);

        drawer.drawAll();
    }
}
