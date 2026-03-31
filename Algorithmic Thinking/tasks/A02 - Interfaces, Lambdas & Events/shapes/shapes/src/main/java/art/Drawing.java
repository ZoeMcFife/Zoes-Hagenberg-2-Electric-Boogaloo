package art;

import geometry.*;
import geometry.interfaces.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import math.Matrix3;
import math.TransformFactory;
import math.Vector2;

import java.util.Arrays;

public class Drawing {

    // ========================================
    // DRAWING MODE
    // ========================================

    public enum DrawingMode {
        SELECT,
        CREATE_CIRCLE, CREATE_RECTANGLE, CREATE_LINE,
        CREATE_TRIANGLE, CREATE_ELLIPSE, CREATE_STAR
    }

    // ========================================
    // FIELDS
    // ========================================

    private Polygon[] polygons;
    private int count;
    private int selectedIndex = -1;
    private double lastDragX = -1;
    private double lastDragY = -1;
    private boolean wasDragging = false;
    private GraphicsContext gc;
    private double canvasWidth;
    private double canvasHeight;

    private DrawingMode mode = DrawingMode.SELECT;
    private double pressX, pressY;
    private double cursorX = -1, cursorY = -1;   // current mouse position (for preview)
    private boolean mouseButtonPressed = false;
    private int triangleStep = 0;
    private Vector2[] triangleBuffer = new Vector2[3];
    private Color currentFillColor   = Color.DODGERBLUE;
    private Color currentStrokeColor = Color.BLACK;
    private Runnable onSelectionChanged;

    // ========================================
    // CONSTRUCTOR
    // ========================================

    public Drawing(int maxShapes, GraphicsContext gc) {
        this.polygons = new Polygon[maxShapes];
        this.count = 0;
        this.gc = gc;
        this.canvasWidth = gc.getCanvas().getWidth();
        this.canvasHeight = gc.getCanvas().getHeight();
    }

    // ========================================
    // METHODS
    // ========================================

    public void add(Polygon shape) {
        if (this.count < this.polygons.length) {
            this.polygons[this.count++] = shape;
        }
    }

    public int getCount() {
        return this.count;
    }

    public double getTotalArea() {
        double total = 0;
        for (int i = 0; i < this.count; i++) {
            total += this.polygons[i].getArea();
        }
        return total;
    }

    public void drawAll(GraphicsContext gc) {
        for (int i = 0; i < this.count; i++) {
            this.polygons[i].draw(gc);
        }
    }

    // ========================================
    // MODE CONTROL
    // ========================================

    public void setMode(DrawingMode m) {
        this.mode = m;
        triangleStep = 0;
        redraw();
    }

    public void setOnSelectionChanged(Runnable r) {
        this.onSelectionChanged = r;
    }

    // Returns the fill/stroke color of the selected shape, or the current default if none selected.
    public Color getSelectedFillColor() {
        return selectedIndex >= 0 ? polygons[selectedIndex].getFillColor() : currentFillColor;
    }

    public Color getSelectedStrokeColor() {
        return selectedIndex >= 0 ? polygons[selectedIndex].getStrokeColor() : currentStrokeColor;
    }

    public void setCurrentFillColor(Color c) {
        this.currentFillColor = c;
        if (selectedIndex >= 0) {
            polygons[selectedIndex].setFillColor(c);
            redraw();
        }
    }

    public void setCurrentStrokeColor(Color c) {
        this.currentStrokeColor = c;
        if (selectedIndex >= 0) {
            polygons[selectedIndex].setStrokeColor(c);
            redraw();
        }
    }

    // Applies the current fill and stroke colors to a shape, then adds it.
    private void addWithColors(Polygon s) {
        s.setFillColor(currentFillColor);
        s.setStrokeColor(currentStrokeColor);
        add(s);
    }

    // ========================================
    // MOUSE EVENT HANDLERS
    // ========================================

    // Called on every mouse move (no button held). Used to update the preview
    // in CREATE modes, especially for triangle's 3-click flow.
    public void handleMouseMoved(double x, double y) {
        if (mode == DrawingMode.SELECT) return;
        cursorX = x;
        cursorY = y;
        redraw();
    }

    // Called when the mouse button is pressed.
    public void handleMousePressed(double x, double y) {
        pressX = x;
        pressY = y;
        cursorX = x;
        cursorY = y;
        mouseButtonPressed = true;

        if (mode == DrawingMode.SELECT) {
            lastDragX = x;
            lastDragY = y;
        } else if (mode == DrawingMode.CREATE_TRIANGLE) {
            triangleBuffer[triangleStep] = new Vector2(x, y);
            triangleStep++;
            if (triangleStep == 3) {
                addWithColors(new Triangle(triangleBuffer[0], triangleBuffer[1], triangleBuffer[2], currentFillColor));
                triangleStep = 0;
            }
            redraw();
        }
    }

    // Called when the mouse button is released.
    public void handleMouseReleased(double x, double y) {
        mouseButtonPressed = false;

        if (mode == DrawingMode.SELECT) {
            lastDragX = -1;
            lastDragY = -1;
            return;
        }
        if (mode == DrawingMode.CREATE_TRIANGLE) {
            return;
        }

        double dx = x - pressX;
        double dy = y - pressY;
        switch (mode) {
            case CREATE_CIRCLE:
            {
                double dist = Math.sqrt(dx * dx + dy * dy);
                double radius = (dist < 5) ? 30 : dist;
                addWithColors(new Circle(new Vector2(pressX, pressY), radius, currentFillColor));
                break;
            }
            case CREATE_RECTANGLE: {
                double w = Math.abs(dx);
                double h = Math.abs(dy);
                if (w < 5) w = 80;
                if (h < 5) h = 50;
                addWithColors(new Rectangle(Math.min(pressX, x), Math.min(pressY, y), w, h, currentFillColor));
                break;
            }
            case CREATE_LINE:
            {
                double len = Math.sqrt(dx * dx + dy * dy);
                if (len < 5) {
                    addWithColors(new Line(new Vector2(pressX, pressY), new Vector2(pressX + 80, pressY), currentFillColor));
                } else {
                    addWithColors(new Line(new Vector2(pressX, pressY), new Vector2(x, y), currentFillColor));
                }
                break;
            }
            case CREATE_ELLIPSE: {
                double semiMajor = Math.abs(dx) / 2;
                double semiMinor = Math.abs(dy) / 2;
                if (semiMajor < 5) semiMajor = 60;
                if (semiMinor < 5) semiMinor = 35;
                addWithColors(new Ellipse(new Vector2((pressX + x) / 2, (pressY + y) / 2), semiMajor, semiMinor, currentFillColor));
                break;
            }
            case CREATE_STAR: {
                double dist = Math.sqrt(dx * dx + dy * dy);
                double outerR = (dist < 5) ? 45 : dist / 2;
                addWithColors(new Star(new Vector2(pressX, pressY), outerR * (20.0 / 45.0), outerR, 5, currentFillColor));
                break;
            }
        }
        cursorX = -1;
        redraw();
    }

    // Called when the user clicks on the canvas.
    // Only active in SELECT mode: finds the topmost shape and selects it.
    public void handleClick(double x, double y)
    {
        if (mode != DrawingMode.SELECT) return;

        if (wasDragging)
        {
            wasDragging = false;
            return;
        }

        for (int i = 0; i < count; i++)
        {
            polygons[i].setSelected(false);
        }

        selectedIndex = -1;

        for (int i = count - 1; i >= 0; i--)
        {
            Rectangle bb = polygons[i].getBoundingBox();

            double padding = 15; // allow some leniency for clicking near the edge of a shape, especially straight lines!!!!!!!

            boolean isXInBounds = x + padding >= bb.getX() - bb.getWidth() / 2 && x - padding <= bb.getX() + bb.getWidth() / 2;
            boolean isYInBounds = y + padding >= bb.getY() - bb.getHeight() / 2 && y - padding <= bb.getY() + bb.getHeight() / 2;

            if (isXInBounds && isYInBounds)
            {
                polygons[i].setSelected(true);
                selectedIndex = i;
                break;
            }
        }

        redraw();
        if (onSelectionChanged != null) onSelectionChanged.run();
    }

    // Called while the user drags (button held + mouse moved).
    public void handleDrag(double x, double y) {
        cursorX = x;
        cursorY = y;

        if (mode == DrawingMode.SELECT && selectedIndex >= 0) {
            if (lastDragX >= 0) {
                double dx = x - lastDragX;
                double dy = y - lastDragY;
                polygons[selectedIndex].translate(dx, dy);
                wasDragging = true;
            }
            lastDragX = x;
            lastDragY = y;
        }
        redraw();
    }

    // ========================================
    // TRANSFORM OPERATIONS ON SELECTED SHAPE
    // ========================================

    public void rotateSelected(double radians) {
        if (selectedIndex < 0) return;

        Polygon p = polygons[selectedIndex];
        Rectangle bb = p.getBoundingBox();

        double cx = bb.getX();
        double cy = bb.getY();
        Matrix3 toOrigin = TransformFactory.createTranslation(-cx, -cy);
        Matrix3 rotate =  TransformFactory.createRotation(radians);
        Matrix3 toBack = TransformFactory.createTranslation(cx, cy);

        p.applyTransform(toBack.mult(rotate.mult(toOrigin)));

        redraw();
    }

    public void scaleSelected(double fx, double fy) {
        if (selectedIndex < 0) return;

        Polygon p = polygons[selectedIndex];
        Rectangle bb = p.getBoundingBox();

        double cx = bb.getX();
        double cy = bb.getY();
        Matrix3 toOrigin = TransformFactory.createTranslation(-cx, -cy);
        Matrix3 scaling =  TransformFactory.createScaling(fx, fy);
        Matrix3 toBack = TransformFactory.createTranslation(cx, cy);

        p.applyTransform(toBack.mult(scaling.mult(toOrigin)));

        redraw();

    }

    public void mirrorSelectedHorizontal()
    {
        if (selectedIndex < 0) return;

        Polygon p = polygons[selectedIndex];
        Rectangle bb = p.getBoundingBox();

        double cx = bb.getX();
        double cy = bb.getY();
        Matrix3 toOrigin = TransformFactory.createTranslation(-cx, -cy);
        Matrix3 mirror =  TransformFactory.createHorizontalMirroring();
        Matrix3 toBack = TransformFactory.createTranslation(cx, cy);

        p.applyTransform(toBack.mult(mirror.mult(toOrigin)));

        redraw();
    }

    public void deleteSelected() {
        if (selectedIndex < 0) return;
        for (int i = selectedIndex; i < count - 1; i++) {
            polygons[i] = polygons[i + 1];
        }
        polygons[count - 1] = null;
        count--;
        selectedIndex = -1;
        redraw();
    }

    // ========================================
    // PRIVATE HELPERS
    // ========================================

    private void redraw() {
        gc.clearRect(0, 0, canvasWidth, canvasHeight);
        drawAll(gc);
        drawPreview();
    }

    // Draws a semi-transparent ghost of the shape currently being created.
    // Builds a temporary Shape, calls getCoordinates(), and renders generically:
    //   2 points  → strokeLine  (Line)
    //   N points  → fillPolygon + strokePolygon  (everything else)
    private void drawPreview() {
        if (mode == DrawingMode.SELECT || cursorX < 0) return;

        // Triangle: draw a dot for every vertex already placed
        if (mode == DrawingMode.CREATE_TRIANGLE && triangleStep > 0) {
            gc.save();
            gc.setGlobalAlpha(0.90);
            gc.setFill(Color.DARKGRAY);
            gc.setLineDashes(0);
            for (int i = 0; i < triangleStep; i++) {
                double px = triangleBuffer[i].getX(), py = triangleBuffer[i].getY();
                gc.fillOval(px - 4, py - 4, 8, 8);
            }
            gc.restore();
        }

        Shape preview = buildPreviewShape();
        if (preview == null) return;

        double[][] coords = preview.getCoordinates();
        int n = coords[0].length;

        gc.save();
        gc.setLineDashes(6, 4);
        gc.setFill(currentFillColor);
        gc.setStroke(Color.DARKGRAY);

        if (n == 2) {
            // Line: stroke only
            gc.setGlobalAlpha(0.85);
            gc.setLineWidth(2);
            gc.strokeLine(coords[0][0], coords[1][0], coords[0][1], coords[1][1]);
        } else {
            gc.setGlobalAlpha(0.30);
            gc.fillPolygon(coords[0], coords[1], n);
            gc.setGlobalAlpha(0.80);
            gc.setLineWidth(1.5);
            gc.strokePolygon(coords[0], coords[1], n);
        }

        gc.restore();
    }

    // Builds a temporary Shape representing the current preview state.
    // Returns null if there is nothing to preview yet.
    private Shape buildPreviewShape() {
        switch (mode) {
            case CREATE_CIRCLE: {
                if (!mouseButtonPressed) return null;
                double r = dist(pressX, pressY, cursorX, cursorY);
                return new Circle(new Vector2(pressX, pressY), r < 5 ? 30 : r, currentFillColor);
            }
            case CREATE_RECTANGLE: {
                if (!mouseButtonPressed) return null;
                double w = Math.max(Math.abs(cursorX - pressX), 5);
                double h = Math.max(Math.abs(cursorY - pressY), 5);
                return new Rectangle(Math.min(pressX, cursorX), Math.min(pressY, cursorY), w, h, currentFillColor);
            }
            case CREATE_LINE: {
                if (!mouseButtonPressed) return null;
                return new Line(new Vector2(pressX, pressY), new Vector2(cursorX, cursorY), currentFillColor);
            }
            case CREATE_ELLIPSE: {
                if (!mouseButtonPressed) return null;
                double semiMajor = Math.max(Math.abs(cursorX - pressX) / 2, 5);
                double semiMinor = Math.max(Math.abs(cursorY - pressY) / 2, 5);
                return new Ellipse(new Vector2( (pressX + cursorX) / 2, (pressY + cursorY) / 2), semiMajor, semiMinor, currentFillColor);
            }
            case CREATE_STAR: {
                if (!mouseButtonPressed) return null;
                double d = dist(pressX, pressY, cursorX, cursorY);
                double outerR = d < 5 ? 45 : d / 2;
                return new Star(new Vector2(pressX, pressY), outerR * (20.0 / 45.0), outerR, 5, currentFillColor);
            }
            case CREATE_TRIANGLE: {
                if (triangleStep == 0) return null;
                if (triangleStep == 1)
                    return new Line(new Vector2(triangleBuffer[0].getX(), triangleBuffer[0].getY()), new Vector2(cursorX, cursorY), currentFillColor);
                return new Triangle(triangleBuffer[0], triangleBuffer[1], new Vector2(cursorX, cursorY), currentFillColor);
            }
            default: return null;
        }
    }

    private double dist(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1, dy = y2 - y1;
        return Math.sqrt(dx * dx + dy * dy);
    }
}

