module gay.fox.pacman {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;
    requires javafx.base;
    requires gay.fox.pacman;

    opens gay.fox.pacman.ui to javafx.fxml;
    exports gay.fox.pacman.ui;
}