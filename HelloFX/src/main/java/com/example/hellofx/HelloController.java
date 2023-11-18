package com.example.hellofx;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javafx.scene.control.Label.*;

public class HelloController
{
    @FXML
    private Button ButtonEnviar,ButtonEnviarPesoIdeal,ButtonCalcular;
    @FXML
    private RadioButton ButtonMujer,ButtonHombre;
    @FXML
    private TextField FieldTalla, FieldPeso, FieldEdad, FieldPesoIdeal;
    @FXML
    private Slider ActividadSlider;
    @FXML
    public Label aviso1, labelIMC, labelTMB, labelaviso, labelcarbohidrato, labelgrasa, labelproteina;

    static Integer edad, altura, ejercicio, ideal;
    static Double peso, TMB, IMC, idealKcal, idealProtein, idealCarbs, idealFats;
    static String sexo;

    public void clickEnviar(ActionEvent event) throws IOException
    {
        try
        {
            edad = Integer.parseInt(FieldEdad.getText());
            peso = Double.parseDouble(FieldPeso.getText());
            altura = Integer.parseInt(FieldTalla.getText());
            if (ButtonMujer.isSelected())
            {
                sexo = String.valueOf('F');
                TMB = (10 * peso) + (6.25 * altura) - (5 * edad) - 161;
            }
            else if (ButtonHombre.isSelected())
            {
                sexo = String.valueOf('M');
                TMB = (10 * peso) + (6.25 * altura) - (5 * edad) + 5;
            }
            ejercicio = (int) ActividadSlider.getValue();
            TMB = (1.025 + (0.175 * ejercicio)) * TMB;
            IMC = peso / ((0.01 * altura) * (0.01 * altura));
        }
        catch (Exception e)
        {
            aviso1.setText("Asegúrate de completar todos los campos e ingresar los datos en la medida numérica indicada");
        }

        if (edad!=null && peso!=null && altura!=null && sexo!=null)
        {
            Parent root = FXMLLoader.load(getClass().getResource("thirdscene.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        }
    }
    public void clickCalcular(ActionEvent event)
    {
        labelIMC.setText(Long.toString(Math.round(IMC)));
        labelTMB.setText(Long.toString(Math.round(TMB)));
    }

    public void clickEnviarPeso(ActionEvent event)
    {
        ideal = Integer.parseInt(FieldPesoIdeal.getText());

        if ((ideal/((0.01*altura)*(0.01*altura)))<=18.5 || (ideal/((0.01*altura)*(0.01*altura)))>=30)
        {
            labelaviso.setText("Intentar conseguir ese peso es riesgoso para tu salud, por favor escoge otro");
            ideal=null;
        }

        if(ideal!=null)
        {
            if(peso>ideal) // quiere bajar
            {
                idealKcal=(TMB - TMB*0.1);
                idealProtein=(7.2*peso);
                idealFats=(4.5*peso);
                idealCarbs=((idealKcal-(idealProtein+idealFats))/4);

                labelaviso.setText("");
                labelcarbohidrato.setText(idealCarbs.toString());
                labelgrasa.setText(idealFats.toString());
                labelproteina.setText(idealProtein.toString());
            }
            if(Math.round(peso)==ideal) // quiere mantenerse
            {
                idealKcal=TMB;
                idealProtein=(8.4*peso);
                idealFats=(9*peso);
                idealCarbs=((idealKcal-(idealProtein+idealFats))/4);

                labelaviso.setText("");
                labelcarbohidrato.setText(idealCarbs.toString());
                labelgrasa.setText(idealFats.toString());
                labelproteina.setText(idealProtein.toString());
            }
            if(peso<ideal) // quiere subir
            {
                idealKcal=(TMB + TMB*0.1);
                idealProtein=(9.2*peso);
                idealFats=(13.5*peso);
                idealCarbs=((idealKcal-(idealProtein+idealFats))/4);

                labelaviso.setText("");
                labelcarbohidrato.setText(idealCarbs.toString());
                labelgrasa.setText(idealFats.toString());
                labelproteina.setText(idealProtein.toString());
            }
        }
    }

    private Stage stage;
    private Scene scene;
    private Parent root;

}