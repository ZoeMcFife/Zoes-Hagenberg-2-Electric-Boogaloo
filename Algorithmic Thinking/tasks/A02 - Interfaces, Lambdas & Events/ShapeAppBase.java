package at.fhooe.ald.shapes;

import at.fhooe.ald.shapes.Drawing.DrawingMode;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Pre-built base class — students do not modify this file.
 *
 * Sets up the canvas, toolbar, color pickers, and layout.
 * Subclasses implement two template methods:
 *   setupShapes()    — add initial shapes to `drawing`
 *   registerEvents() — register lambda handlers on `canvas`
 */
public abstract class ShapeAppBase extends Application {

    protected Canvas  canvas;
    protected Drawing drawing;

    // ---------------------------------------------------------------
    // Application entry point — sealed so students cannot override it
    // ---------------------------------------------------------------

    @Override
    public final void start(Stage stage) {
        canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawing = new Drawing(50, gc);

        setupShapes();
        drawing.drawAll(gc);

        System.out.println("Total area: " + drawing.getTotalArea());
        System.out.println("Number of shapes: " + drawing.getCount());

        // --- Mode toggle buttons ---
        ToggleGroup modeGroup = new ToggleGroup();

        ToggleButton btnSelect    = new ToggleButton("↖ Select");
        ToggleButton btnCircle    = new ToggleButton("◯ Circle");
        ToggleButton btnRectangle = new ToggleButton("▭ Rectangle");
        ToggleButton btnLine      = new ToggleButton("/ Line");
        ToggleButton btnTriangle  = new ToggleButton("△ Triangle");
        ToggleButton btnEllipse   = new ToggleButton("⬯ Ellipse");
        ToggleButton btnStar      = new ToggleButton("★ Star");

        btnSelect.setToggleGroup(modeGroup);
        btnCircle.setToggleGroup(modeGroup);
        btnRectangle.setToggleGroup(modeGroup);
        btnLine.setToggleGroup(modeGroup);
        btnTriangle.setToggleGroup(modeGroup);
        btnEllipse.setToggleGroup(modeGroup);
        btnStar.setToggleGroup(modeGroup);

        btnSelect.setSelected(true);

        btnSelect.setOnAction(e    -> drawing.setMode(DrawingMode.SELECT));
        btnCircle.setOnAction(e    -> drawing.setMode(DrawingMode.CREATE_CIRCLE));
        btnRectangle.setOnAction(e -> drawing.setMode(DrawingMode.CREATE_RECTANGLE));
        btnLine.setOnAction(e      -> drawing.setMode(DrawingMode.CREATE_LINE));
        btnTriangle.setOnAction(e  -> drawing.setMode(DrawingMode.CREATE_TRIANGLE));
        btnEllipse.setOnAction(e   -> drawing.setMode(DrawingMode.CREATE_ELLIPSE));
        btnStar.setOnAction(e      -> drawing.setMode(DrawingMode.CREATE_STAR));

        // --- Transform buttons ---
        Button btnRotate    = new Button("↻ Rotate 45°");
        Button btnScaleUp   = new Button("⊞ Scale ×1.5");
        Button btnMirrorH   = new Button("↔ Mirror H");
        Button btnScaleDown = new Button("⊟ Scale ×0.75");
        Button btnDelete    = new Button("✕ Delete");

        btnRotate.setOnAction(e    -> drawing.rotateSelected(Math.PI / 4));
        btnScaleUp.setOnAction(e   -> drawing.scaleSelected(1.5, 1.5));
        btnMirrorH.setOnAction(e   -> drawing.mirrorSelectedHorizontal());
        btnScaleDown.setOnAction(e -> drawing.scaleSelected(0.75, 0.75));
        btnDelete.setOnAction(e    -> drawing.deleteSelected());

        // --- Color pickers ---
        ColorPicker fillPicker   = new ColorPicker(Color.DODGERBLUE);
        ColorPicker strokePicker = new ColorPicker(Color.BLACK);

        fillPicker.setOnAction(e   -> drawing.setCurrentFillColor(fillPicker.getValue()));
        strokePicker.setOnAction(e -> drawing.setCurrentStrokeColor(strokePicker.getValue()));

        drawing.setOnSelectionChanged(() -> {
            fillPicker.setValue(drawing.getSelectedFillColor());
            strokePicker.setValue(drawing.getSelectedStrokeColor());
        });

        ToolBar toolbar = new ToolBar(
            btnSelect, btnCircle, btnRectangle, btnLine, btnTriangle, btnEllipse, btnStar,
            new Separator(),
            btnRotate, btnScaleUp, btnMirrorH, btnScaleDown, btnDelete,
            new Separator(),
            new Label("Fill:"), fillPicker,
            new Label("Stroke:"), strokePicker
        );

        // --- Layout ---
        BorderPane root = new BorderPane();
        root.setTop(toolbar);
        root.setCenter(canvas);

        Scene scene = new Scene(root);
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.DELETE) drawing.deleteSelected();
        });

        registerEvents();

        stage.setTitle("UE4: Interfaces, Lambdas & Events");
        stage.setScene(scene);
        stage.show();
    }

    // ---------------------------------------------------------------
    // Template methods — students override these in ShapeApp
    // ---------------------------------------------------------------

    /** Add initial shapes to {@code drawing}. */
    protected abstract void setupShapes();

    /** Register lambda event handlers on {@code canvas} and {@code drawing}. */
    protected abstract void registerEvents();
}
