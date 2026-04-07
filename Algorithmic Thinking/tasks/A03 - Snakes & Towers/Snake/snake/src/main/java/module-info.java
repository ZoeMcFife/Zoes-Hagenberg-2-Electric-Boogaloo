module gay.fox.snake {
    requires javafx.controls;
    requires javafx.fxml;


    opens gay.fox.snake to javafx.fxml;
    exports gay.fox.snake;
}