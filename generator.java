package CASS;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class generator 
{

    private static final String LETRAS = "abcdefghijklmnopqrstuvwxyz";

    public static void main(String[] args) 
    {
        int n = 100000000;  // Número de datos a generar
        String archivoDestino = "datos_aleatorios.txt";
        
        try 
        {
            guardarDatos(n, archivoDestino);
            System.out.println("Datos almacenados en " + archivoDestino);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    public static String generarDato() 
    {
        Random rand = new Random();
        char letra = LETRAS.charAt(rand.nextInt(LETRAS.length()));
        String palabra = generarCadenaAleatoria(7, LETRAS);
        
        StringBuilder numero = new StringBuilder();
        for (int i = 0; i < 4; i++) 
        {
            if (i > 0) 
            {
                numero.append(" ");
            }
            numero.append(rand.nextInt(501));  // Añade un número aleatorio entre 0 y 500 (inclusive)
        }
        return letra + " " + palabra + " " + numero.toString();
    }

    public static String generarCadenaAleatoria(int longitud, String caracteres) 
    {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < longitud; i++) {
            sb.append(caracteres.charAt(rand.nextInt(caracteres.length())));
        }
        return sb.toString();
    }

    public static void guardarDatos(int n, String archivoDestino) throws IOException 
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoDestino))) 
        {
            for (int i = 0; i < n; i++) 
            {
                writer.write(generarDato() + "\n");
            }
            writer.write("//");
        }
    }
}

