package com.example.hellofx;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class HelloController
{
    @FXML
    private Button ButtonEnviar;
    @FXML
    private RadioButton ButtonMujer,ButtonHombre;
    @FXML
    private TextField FieldTalla, FieldPeso, FieldEdad;
    @FXML
    private Slider ActividadSlider;
    @FXML
    private Label aviso1;

    static Integer edad;
    static Integer altura;
    static Double peso;
    static Integer ejercicio;
    static String sexo;
    static double TMB;
    static double IMC;

    public void clickEnviar(ActionEvent event) throws IOException
    {
        try
        {
            edad = Integer.parseInt(FieldEdad.getText());
            peso = Double.parseDouble(FieldPeso.getText());
            altura = Integer.parseInt(FieldTalla.getText());
            if(ButtonMujer.isSelected())
            {
                sexo = String.valueOf('F');
                TMB = (10*peso) + (6.25*altura) - (5*edad) - 161;
            }
            else if(ButtonHombre.isSelected())
            {
                sexo = String.valueOf('M');
                TMB = (10*peso) + (6.25*altura) - (5*edad) + 5;
            }
            ejercicio = (int) ActividadSlider.getValue();
            TMB = (1.025 + (0.175*ejercicio))*TMB;
            IMC = peso /((altura)*(altura));

            System.out.println(IMC);
            System.out.println(TMB);
        }
        catch(Exception e)
        {
            aviso1.setText("Asegúrate de completar todos los campos e ingresar los datos en la medida numérica indicada");
        }



        if (edad!=null && peso!=null && altura!=null && sexo!=null)
        {
            Parent root = FXMLLoader.load(getClass().getResource("secondscene.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    private Stage stage;
    private Scene scene;
    private Parent root;




}