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
        int n = 100;
        String destinyFile = "datos_aleatorios.txt";
        try
        {
            dataStore.generator.dataSaver(n, destinyFile);
            System.out.println("Datos almacenados en " + destinyFile);
            BufferedReader reader = new BufferedReader(new FileReader("datos_aleatorios.txt"));

            String input;

            while ((input = reader.readLine()) != null)
            {
                if (input.equals("//"))
                {
                    break;
                }
                String[] parts = input.split(" ");
                dataStore.addData(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), Integer.parseInt(parts[5]));
            }
            System.out.println("Total de datos: " + dataStore.getTotalDataCount());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        launch();
    }
}