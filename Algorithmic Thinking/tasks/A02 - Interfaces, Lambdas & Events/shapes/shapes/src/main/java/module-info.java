module space.zoemcfife.shapes
{
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.xml;

    opens space.zoemcfife.shapes to javafx.fxml;
    exports space.zoemcfife.shapes;
}