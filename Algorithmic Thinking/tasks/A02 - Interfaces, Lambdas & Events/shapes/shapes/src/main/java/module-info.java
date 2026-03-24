module space.zoemcfife.shapes {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens space.zoemcfife.shapes to javafx.fxml;
    exports space.zoemcfife.shapes;
}