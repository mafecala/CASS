package com.example.hellofx;
import java.util.*;
import java.io.*;

public class dataStore extends HelloController
{
    public static int getTotalDataCount()
    {
        int count = 0;
        for (Map<String, FoodData> innerMap : store.values())
        {
            count += innerMap.size();
        }
        return count;
    }
    static class generator
    {
        private static final String chars = "abcdefghijklmnopqrstuvwxyz";
        public static String dataGenerator()
        {
            Random rand = new Random();
            char letter = chars.charAt(rand.nextInt(chars.length()));
            String word = stringGenerator(2, chars);
            StringBuilder number = new StringBuilder();

            for (int i = 0; i < 4; i++)
            {
                if (i > 0)
                {
                    number.append(" ");
                }
                number.append(rand.nextInt(501));
            }
            return letter + " " + word + " " + number.toString();
        }
        public static String stringGenerator(int len, String chars)
        {
            Random rand = new Random();
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < len; i++)
            {
                sb.append(chars.charAt(rand.nextInt(chars.length())));
            }
            return sb.toString();
        }
        public static void dataSaver(int n, String archivoDestino) throws IOException
        {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoDestino)))
            {
                for (int i = 0; i < n; i++)
                {
                    writer.write(dataGenerator() + "\n");
                }
                writer.write("//");
            }
        }
    }
    static class FoodData
    {
        String foodType;
        String foodName;
        int kcal, protein, carbs, fats;
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
    public static FoodData getDataByTypeAndName(String foodType, String foodName) throws Exception //obtiene los datos numericos de la comida indicada por tipo y nombre
    {
        if((foodType.isEmpty()) || (foodName.isEmpty())) throw new Exception();
        Map<String, FoodData> tempMap = store.get(foodType); // establece el mapa foodData con respecto al mapa de foodType especificado
        return tempMap != null ? tempMap.get(foodName) : null; // mientras foodData no sea nulo, devuelve los valores asociados a foodName
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
}