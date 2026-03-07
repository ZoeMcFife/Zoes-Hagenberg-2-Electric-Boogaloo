package space.zoemcfife.shapes;

import geometry.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class ShapeApplication extends Application
{
    @Override
    public void start(Stage stage) throws IOException
    {
        stage.setTitle("Drawing Operations Test");
        Group root = new Group();
        Canvas canvas = new Canvas(800, 600);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        //drawShapes(gc);
        creativeThings(gc);

        root.getChildren().add(canvas);
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void creativeThings(GraphicsContext gc)
    {
        Random random = new Random();

        // Background
        Rectangle background = new Rectangle(new Vector2(400, 300), 800, 600);
        background.draw(gc, Color.BLACK);

        // Random star field
        for (int i = 0; i < 2000; i++)
        {
            double x = random.nextDouble() * 800;
            double y = random.nextDouble() * 600;

            Point starPoint = new Point(new Vector2(x, y));
            starPoint.draw(gc, Color.WHITE);
        }

        // Big central star
        Circle star = new Circle(new Vector2(400, 300), 200);
        star.draw(gc, Color.rgb(255, 245, 180));

        // Planet to the right
        Circle planet = new Circle(new Vector2(730, 300), 50);
        planet.draw(gc, Color.rgb(150, 60, 40));

        // Orbital ellipse around the planet
        Ellipse orbit = new Ellipse(new Vector2(760, 200), 25, 15);
        orbit.draw(gc, Color.rgb(120, 140, 120));

        // Small station rectangle left of the star
        Rectangle station = new Rectangle(new Vector2(110, 300), 40, 25);
        station.draw(gc, Color.GRAY);

        Rectangle station2 = new Rectangle(new Vector2(100, 300), 10, 40);
        station2.draw(gc, Color.GRAY);

        Rectangle station3 = new Rectangle(new Vector2(130, 300), 10, 40);
        station3.draw(gc, Color.GRAY);

        // Surrounding lines
        Line l1 = new Line(new Vector2(95, 280), new Vector2(75, 260));
        Line l2 = new Line(new Vector2(145, 280), new Vector2(165, 260));
        Line l3 = new Line(new Vector2(95, 320), new Vector2(75, 340));
        Line l4 = new Line(new Vector2(145, 320), new Vector2(165, 340));

        Line l5 = new Line(new Vector2(120, 260), new Vector2(120, 240));
        Line l6 = new Line(new Vector2(120, 340), new Vector2(120, 360));

        l1.draw(gc, Color.LIGHTGRAY);
        l2.draw(gc, Color.LIGHTGRAY);
        l3.draw(gc, Color.LIGHTGRAY);
        l4.draw(gc, Color.LIGHTGRAY);
        l5.draw(gc, Color.LIGHTGRAY);
        l6.draw(gc, Color.LIGHTGRAY);

        // Star on top of the station
        Star stationStar = new Star(new Vector2(110, 300), 6, 10, 10);
        stationStar.draw(gc, Color.DARKGRAY);
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
