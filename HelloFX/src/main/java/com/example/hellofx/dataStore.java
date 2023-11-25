package com.example.hellofx;
import java.util.*;
import java.io.*;

public class dataStore extends HelloController
{
    public static int getTotalDataCount()
    {
        int count = 0;
        // Iterar a través de cada foodType en store
        for (Map<String, FoodData> innerMap : store.values())
        {
            count += innerMap.size();  // Sumar el número de foodName dentro de este foodType
        }
        return count;
    }
    static class generator //esta clase es un generador aleatorio de comidas (letra palabra numero numero numero numero)
    {
        private static final String chars = "abcdefghijklmnopqrstuvwxyz"; // el banco de letras elegibles, no hay mayusculas ni ñ
        public static String dataGenerator()
        {
            Random rand = new Random();
            char letter = chars.charAt(rand.nextInt(chars.length())); // la letra que se usará como grupo o tipo de comida
            String word = stringGenerator(2, chars); // invoca el metodo que genera la palabra que se usará como el nombre de la comida
            StringBuilder number = new StringBuilder();

            for (int i = 0; i < 4; i++)  // añade cuatro numeros aleatorios que seran kilocalorias, gramos de proteina, gramos de carbohidratos y gramos de grasa
            {
                if (i > 0)
                {
                    number.append(" ");
                }
                number.append(rand.nextInt(501));  // define el limite numero sobre el que se generarán los numeros
            }
            return letter + " " + word + " " + number.toString(); // los separa con espacios para poder ser legibles en el siguiente paso
        }
        public static String stringGenerator(int len, String chars) // este metodo crea una palabra usando el banco de letras
        {
            Random rand = new Random();
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < len; i++) // añade la cantidad indicada de letras aleatorias a la palabra
            {
                sb.append(chars.charAt(rand.nextInt(chars.length())));
            }
            return sb.toString();
        }
        public static void dataSaver(int n, String archivoDestino) throws IOException // crea la cantidad indicada de palabras y las añade a un archivo para ser leido posteriormente
        {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoDestino)))
            {
                for (int i = 0; i < n; i++)
                {
                    writer.write(dataGenerator() + "\n");
                }
                writer.write("//"); // añade como ultima linea "//" al archivo
            }
        }
    }
    static class FoodData
    {
        String foodType;
        String foodName;
        int kcal, protein, carbs, fats;
        //se definen los campos para crear el objeto

        FoodData(String foodType, String foodName, int kcal, int protein, int carbs, int fats)
        {
            this.foodType = foodType;
            this.foodName = foodName;
            this.kcal = kcal;
            this.protein = protein;
            this.carbs = carbs;
            this.fats = fats;
        }
        @Override
        public String toString()
        {
            return "FoodData [foodType= " + foodType + ", foodName= " + foodName + ", kcal= " + kcal + ", protein= " + protein + ", carbs= " + carbs + ", fats= " + fats + "]";
        }
    }
    private static Map<String, Map<String, FoodData>> store = new HashMap<>(); //crea los dos hashmaps (uno externo y uno interno)
    public static void addData(String foodType, String foodName, int kcal, int protein, int carbs, int fats)
    {
        FoodData newNode = new FoodData(foodType, foodName, kcal, protein, carbs, fats); // crea el nodo con los datos ingresados
        store.putIfAbsent(foodType, new HashMap<>()); // si es un tipo de comida nuevo, añade un nuevo hashmap para ese tipo
        store.get(foodType).put(foodName, newNode); // añade la comida y sus datos en el hashmap de su tipo
    }
    public FoodData getDataByTypeAndName(String foodType, String foodName) //obtiene los datos numericos de la comida indicada por tipo y nombre
    {
        Map<String, FoodData> tempMap = store.get(foodType); // establece el mapa foodData con respecto al mapa de foodType especificado
        return tempMap != null ? tempMap.get(foodName) : null; // mientras foodData no sea nulo, devuelve los valores asociados a foodName
    }

    //esos dos metodos tambien son para debug y para limpiar el HelloApplication
    public static void printer()
    {
        System.out.println(store);
    }
    public static void initializer(int n)
    {
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
    }
/*
    public static void main(String[] args)
    {
        dataStore dataStore = new dataStore();

            String sexo;
            int edad,altura,peso,ejercicio,ideal;
            double TMB;
            float IMC;

            while (true)
            {

                int totalKcal = 0, totalProtein = 0, totalCarbs = 0, totalFats = 0;

                System.out.println("Ingrese el tipo y nombre de comida de su dieta, en el formato: foodType foodName");
                System.out.println("Escriba '//' para finalizar y calcular calorias, proteina, carbohidratos y grasas consumidas.");

                BufferedReader dietReader = new BufferedReader(new InputStreamReader(System.in));

                while (true)
                {
                    input = dietReader.readLine();

                    if (input.equals("//")) // si encuentra la linea "//" deja de leer datos y pasa al siguiente paso
                    {
                        break;
                    }

                    String[] parts = input.split(" "); // divide la linea en partes cuando encuentra un espacio

                    if (parts.length != 2) // comprueba que la linea ingresada tenga todos los campos requeridos
                    {
                        System.out.println("Formato incorrecto. Por favor, inténtelo de nuevo.");
                        continue;
                    }

                    String searchType = parts[0];
                    String searchName = parts[1];

                    FoodData result = dataStore.getDataByTypeAndName(searchType, searchName); // busca los valores numericos de la comida ingresada

                    if (result != null)
                    {
                        totalKcal += result.kcal;
                        totalProtein += result.protein;
                        totalCarbs += result.carbs;
                        totalFats += result.fats;
                        //suma los valores numericos en variables totales, si encuentra la comida ingresada
                    }
                    else
                    {
                        System.out.println("Comida no encontrada para tipo: " + searchType + " y nombre: " + searchName);
                        // vuelve al inicio del bucle si no encuentra la comida especificada
                    }
                }
                // imprime la cantidad consumida segun las comidas ingresadas
                System.out.println("La comida ingerida actualmente representa " + totalKcal + " calorias, " + totalProtein + " gramos de proteina, " + totalFats + " gramos de grasa y " + totalCarbs + " gramos de carbohidratos.");
                System.out.println("Para llegar a tu objetivo tienes que consumir " + (idealKcal-totalKcal) + " calorias, " + (idealProtein-totalProtein) + " gramos de proteina, " + (idealFats-totalFats) + " gramos de grasa y " + (idealCarbs-totalCarbs) + " gramos de carbohidratos.");
            }
        }
    }
 */
}