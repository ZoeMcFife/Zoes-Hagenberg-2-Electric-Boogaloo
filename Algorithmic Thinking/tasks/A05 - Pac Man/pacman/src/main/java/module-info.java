module gay.fox.pacman {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens gay.fox.pacman to javafx.fxml;
    exports gay.fox.pacman.ui;
    opens gay.fox.pacman.ui to javafx.fxml;
}