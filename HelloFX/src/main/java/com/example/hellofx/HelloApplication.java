package com.example.hellofx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javafx.scene.image.Image;
public class HelloApplication extends Application
{
    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("firstscene.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 850, 540);
        scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
        stage.setTitle("CASS");
        stage.getIcons().add(new Image("file:icon.png"));
        stage.setScene(scene);
        stage.show();
        Parent root = FXMLLoader.load(getClass().getResource("secondscene.fxml"));
    }

    public static void main(String[] args)
    {
        dataStore.initializer(5);
        launch();
    }
}