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
        point.draw(gc, javafx.scene.paint.Color.RED);

        Circle circle = new Circle(new Vector2(400, 300), 150);
        circle.draw(gc, javafx.scene.paint.Color.GREEN);

        Ellipse ellipse = new Ellipse(new Vector2(600, 200), 100, 50);
        ellipse.draw(gc, javafx.scene.paint.Color.BLUE);

        Line line = new Line(new Vector2(200, 500), new Vector2(700, 200));
        line.draw(gc, javafx.scene.paint.Color.ORANGE);

        Rectangle rectangle = new Rectangle(new Vector2(300, 400), 200, 100);
        rectangle.draw(gc, javafx.scene.paint.Color.PURPLE);

        Star star = new Star(new Vector2(500, 100), 25, 50, 5);
        star.draw(gc, javafx.scene.paint.Color.YELLOW);

        Triangle triangle = new Triangle(new Vector2(160, 500), 60, 90, 50);
        triangle.draw(gc, javafx.scene.paint.Color.CYAN);
    }
}
