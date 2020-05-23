package app;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.text.Text;

public class Main extends Application{
    private static Text status = new Text("Status: No Connection");
    private static TextArea textArea = new TextArea("WAITING INCOMING MESSAGES");

    public static void main(String[] args) {
        Server server = Server.getServer();

        Thread threadServer = new Thread(server);
        threadServer.start();

        Application.launch();
    }

    @Override
    public void start(Stage stage) {
        stage.setWidth(700);
        stage.setHeight(500);
        textArea.setPrefHeight(400);
        textArea.setEditable(false);
        // установка надписи
        VBox vBox = new VBox(status, textArea);
        Scene scene = new Scene(vBox);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        stage.setScene(scene);
        stage.setTitle("ServerSellingApp");

        stage.show();
    }

    public static void displayUserMessage(String s){
        textArea.setText(textArea.getText() + "\n\nNEW ORDER:\n" + s);
    }

    public static void changeStatus(String s){
        status.setText(s);
    }
}