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

public class ShapeApplication extends ShapeApplicationBase
{
    @Override
    protected void setupShapes()
    {

    }

    @Override
    protected void registerEvents()
    {
        canvas.setOnMouseMoved(e -> drawing.handleMouseMoved(e.getX(), e.getY()));
        canvas.setOnMousePressed(e -> drawing.handleMousePressed(e.getX(), e.getY()));
        canvas.setOnMouseDragged(e -> drawing.handleDrag(e.getX(), e.getY()));
        canvas.setOnMouseReleased(e -> drawing.handleMouseReleased(e.getX(), e.getY()));
        canvas.setOnMouseClicked(e -> drawing.handleClick(e.getX(), e.getY()));

    }
}
