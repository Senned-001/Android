package app;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main extends Application{
    private static Text status = new Text("Status: No Connection");
    private static TextArea textArea = new TextArea("WAITING INCOMING MESSAGES");
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        //log all orders in file
        try {
            LogManager.getLogManager().readConfiguration(Main.class.getResourceAsStream("logging.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        VBox vBox = new VBox(status, textArea);
        Scene scene = new Scene(vBox);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        stage.setScene(scene);
        stage.setTitle("ServerSellingApp");

        stage.show();
    }

    public static synchronized void displayUserMessage(String s){
        logger.log(Level.INFO,s);
        textArea.setText(textArea.getText() + "\n\n========NEW ORDER=======\n" + s);
        textArea.selectEnd();
        textArea.deselect();
    }

    public static void changeStatus(String s){
        status.setText(s);
    }
}