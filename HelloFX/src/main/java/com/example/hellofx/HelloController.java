package com.example.hellofx;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.scene.control.Label.*;

import static com.example.hellofx.dataStore.addData;

public class HelloController
{
    @FXML
    public Text avisoVerdeDay, avisoRojoDay;
    @FXML
    private RadioButton ButtonMujer,ButtonHombre;
    @FXML
    private TextField FieldTalla, FieldPeso, FieldEdad, FieldPesoIdeal, FieldTipo, FieldNombre, FieldKcal, FieldProteina, FieldCarbohidrato, FieldGrasa, FieldTipoDay, FieldNombreDay;
    @FXML
    private Slider ActividadSlider;
    @FXML
    public Label numKcal, numCarb, numGrasa, numProt, avisoAgregar, avisoFoods, aviso1, labelIMC, labelTMB, labelaviso, labelcarbohidrato, labelgrasa, labelproteina, labelcalorias;
    static Integer edad, altura, ejercicio, ideal;
    static int totalKcal, totalProtein, totalCarbs, totalFats;
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
        try
        {
            ideal = Integer.parseInt(FieldPesoIdeal.getText());
            labelaviso.setText("");
        }
        catch(Exception e)
        {
            labelaviso.setText("Por favor, ingresa un peso valido");
            FieldPesoIdeal.clear();
            ideal=null;
            labelcalorias.setText("");
            labelcarbohidrato.setText("");
            labelgrasa.setText("");
            labelproteina.setText("");
        }

        if ((ideal/((0.01*altura)*(0.01*altura)))<=18.5 || (ideal/((0.01*altura)*(0.01*altura)))>=30)
        {
            labelaviso.setText("Intentar conseguir ese peso es riesgoso para tu salud, por favor escoge otro");
            FieldPesoIdeal.clear();
            ideal=null;
            labelcalorias.setText("");
            labelcarbohidrato.setText("");
            labelgrasa.setText("");
            labelproteina.setText("");
        }

        if(ideal!=null)
        {
            if(peso>ideal) // quiere bajar
            {
                idealKcal=(TMB - TMB*0.1);
                idealProtein=(1.5*peso);
                idealFats=(0.5*peso);
                idealCarbs=((idealKcal-((idealProtein*4)+(idealFats*9)))/4);

                labelcalorias.setText(String.valueOf(Math.round(idealKcal)));
                labelcarbohidrato.setText(String.valueOf(Math.round(idealCarbs)));
                labelgrasa.setText(String.valueOf(Math.round(idealFats)));
                labelproteina.setText(String.valueOf(Math.round(idealProtein)));
            }
            if(Math.round(peso)==ideal) // quiere mantenerse
            {
                idealKcal=TMB;
                idealProtein=(2*peso);
                idealFats=(peso);
                idealCarbs=((idealKcal-((idealProtein*4)+(idealFats*9)))/4);

                labelcalorias.setText(String.valueOf(Math.round(idealKcal)));
                labelcarbohidrato.setText(String.valueOf(Math.round(idealCarbs)));
                labelgrasa.setText(String.valueOf(Math.round(idealFats)));
                labelproteina.setText(String.valueOf(Math.round(idealProtein)));
            }
            if(peso<ideal) // quiere subir
            {
                idealKcal=(TMB + TMB*0.1);
                idealProtein=(2.5*peso);
                idealFats=(1.5*peso);
                idealCarbs=((idealKcal-((idealProtein*4)+(idealFats*9)))/4);

                labelcalorias.setText(String.valueOf(Math.round(idealKcal)));
                labelcarbohidrato.setText(String.valueOf(Math.round(idealCarbs)));
                labelgrasa.setText(String.valueOf(Math.round(idealFats)));
                labelproteina.setText(String.valueOf(Math.round(idealProtein)));
            }
        }
    }
    public void clickHaciaTerceraEscena(ActionEvent event)
    {
        if(ideal!=null)
        {
            try
            {
                Parent root = FXMLLoader.load(getClass().getResource("secondscene.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
                stage.setScene(scene);
                stage.show();
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
        labelaviso.setText("Por favor, Ingresa tu peso ideal");
    }
    public void clickAgregar(ActionEvent event)
    {
        try
        {
            addData(FieldTipo.getText(),FieldNombre.getText(),Integer.parseInt(FieldKcal.getText()),Integer.parseInt(FieldProteina.getText()),Integer.parseInt(FieldCarbohidrato.getText()),Integer.parseInt(FieldGrasa.getText()));
            avisoFoods.setText("");

            avisoAgregar.setText("Comida de nombre '" + FieldNombre.getText() + "' y de tipo '" + FieldTipo.getText() + "' agregada con exito");
            FieldTipo.clear();
            FieldNombre.clear();
            FieldKcal.clear();
            FieldProteina.clear();
            FieldCarbohidrato.clear();
            FieldGrasa.clear();
        }
        catch (Exception e)
        {
            avisoFoods.setText("Recuerda llenar todos los campos de la manera indicada");
        }
    }
    public void clickFinalizar(ActionEvent event)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("fourthscene.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    public void clickButtonEnviarDay(ActionEvent event)
    {
        try
        {
            dataStore.FoodData result = dataStore.getDataByTypeAndName(FieldTipoDay.getText(), FieldNombreDay.getText());
            if (result != null)
            {
                totalKcal += result.kcal;
                totalProtein += result.protein;
                totalCarbs += result.carbs;
                totalFats += result.fats;
                avisoRojoDay.setText("");
                avisoVerdeDay.setText("Comida de nombre '" + FieldNombreDay.getText() +  "' y de tipo '" + FieldTipoDay.getText() + "' añadida a la dieta diaria");
                FieldTipoDay.clear();
                FieldNombreDay.clear();
            }
            else
            {
                avisoVerdeDay.setText("");
                avisoRojoDay.setText("Comida de nombre '" + FieldNombreDay.getText() +  "' y de tipo '" + FieldTipoDay.getText() + "' no encontrada");
                FieldTipoDay.clear();
                FieldNombreDay.clear();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            avisoRojoDay.setText("Por favor, llena ambos campos correctamente");
            FieldTipoDay.clear();
            FieldNombreDay.clear();
        }
    }
    public void clickButtonSiguienteDay(ActionEvent event)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("fifthscene.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    public void clickCalcularTotales(ActionEvent event)
    {
        numKcal.setText(String.valueOf(Math.round(idealKcal-totalKcal)));
        numProt.setText(String.valueOf(Math.round(idealProtein-totalProtein)));
        numGrasa.setText(String.valueOf(Math.round(idealFats-totalFats)));
        numCarb.setText(String.valueOf(Math.round(idealCarbs-totalCarbs)));
    }
    private Stage stage;
    private Scene scene;
    private Parent root;
}

