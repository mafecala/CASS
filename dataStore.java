package CASS;
import java.util.*;
import java.io.*;

public class dataStore 
{
    public int getTotalDataCount() 
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

    private Map<String, Map<String, FoodData>> store = new HashMap<>(); //crea los dos hashmaps (uno externo y uno interno)

    public void addData(String foodType, String foodName, int kcal, int protein, int carbs, int fats) 
    {
        FoodData newNode = new FoodData(foodType, foodName, kcal, protein, carbs, fats); // crea el nodo con los datos ingresados
        store.putIfAbsent(foodType, new HashMap<>()); // si es un tipo de comida nuevo, añade un nuevo hashmap para ese tipo
        store.get(foodType).put(foodName, newNode); // añade la comida y sus datos en el hashmap de su tipo
    }

    public FoodData getDataByTypeAndName(String foodType, String foodName) //obtiene los datos numericos de la comida indicada por tipo y nombre
    {
        Map<String, FoodData> foodData = store.get(foodType); // establece el mapa foodData con respecto al mapa de foodType especificado
        return foodData != null ? foodData.get(foodName) : null; // mientras foodData no sea nulo, devuelve los valores asociados a foodName
    }

    public static void main(String[] args) 
    {
        {
            int n = 100;  // Número de datos a generar
            String destinyFile = "datos_aleatorios.txt";
            try 
            {
                generator.dataSaver(n, destinyFile); // llama al metodo para pasar los datos generados al archivo
                System.out.println("Datos almacenados en " + destinyFile);
            } 
            catch (IOException e) 
            {
                e.printStackTrace(); // si falla al guardar o generar los datos, envia esta excepción mostrando donde se dio el error
            }
        }

        dataStore dataStore = new dataStore(); // crea un objeto dataStore para acceder a los metodos para almacenar datos
        BufferedReader reader = null;
    
        try 
        {
            reader = new BufferedReader(new FileReader("datos_aleatorios.txt")); // lee los datos en el archivo
            String input,sexo;
            int edad,altura,peso,ejercicio,ideal;
            double TMB;
            float IMC;
    
            while ((input = reader.readLine()) != null) // mientras la linea actual no sea nula
            {
                if (input.equals("//")) // si encuentra la linea "//" deja de leer datos y pasa al siguiente paso
                {
                    break;
                }
    
                String[] parts = input.split(" "); // divide la linea en partes cuando encuentra un espacio

                if (parts.length != 6) 
                {
                    System.out.println("Formato incorrecto en línea: " + input); // comprueba que la linea ingresada tenga todos los campos requeridos
                    continue;
                }
    
                String foodType = parts[0];
                String foodName = parts[1];
                int kcal = Integer.parseInt(parts[2]);
                int protein = Integer.parseInt(parts[3]);
                int carbs = Integer.parseInt(parts[4]);
                int fats = Integer.parseInt(parts[5]);
    
                dataStore.addData(foodType, foodName, kcal, protein, carbs, fats); // añade al hashmap los datos encontrados
            }

            int totalDataCount = dataStore.getTotalDataCount();
            System.out.println("Total de datos: " + totalDataCount);
            System.out.println("1. Iniciar Programa"); // ofrece estas dos opciones al terminar de añadir todos los datos
            System.out.println("2. Salir del Programa");

            while (true) 
            {
                System.out.print("Seleccione una opción: ");
                BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in)); 
                int choice;
                try 
                {
                    choice = Integer.parseInt(consoleReader.readLine()); // lee la opcion escogida
                } 
                catch (NumberFormatException e) 
                {
                    System.out.println("Por favor, introduzca un número válido.");
                    continue; // Esto volverá al principio del bucle, pidiendo al usuario que introduzca de nuevo.
                } 
                catch (IOException e) 
                {
                    System.out.println("Error al leer el input. Intente de nuevo.");
                    continue; // Esto volverá al principio del bucle, pidiendo al usuario que introduzca de nuevo.
                }
                switch (choice) 
                {
                    case 1:
                        System.out.println("Necesitaremos que ingrese unos cuantos datos");
                        BufferedReader infoReader = new BufferedReader(new InputStreamReader(System.in));

                        System.out.println("Ingrese su edad");
                        edad = Integer.parseInt(infoReader.readLine());

                        System.out.println("Ingrese su altura en centimetros");
                        altura = Integer.parseInt(infoReader.readLine());

                        System.out.println("Ingrese su peso aproximado en kilogramos");
                        peso = Integer.parseInt(infoReader.readLine());

                        while(true)
                        {
                            System.out.println("Ingrese su sexo como M para masculino y F para femenino");
                            sexo = infoReader.readLine();
                            System.out.println(sexo);
                            if (sexo.equals("M"))
                            {
                                TMB = (10*peso) + (6.25*altura) - (5*edad) + 5;
                                break;
                            }
                            if (sexo.equals("F"))
                            {
                                TMB = (10*peso) + (6.25*altura) - (5*edad) - 161;
                                break;
                            }
                            System.out.println("Ingrese su sexo de la forma indicada");
                        }

                        while(true)
                        {
                            System.out.println("Ingrese su cantidad de ejercicio del 1 al 4, siendo 4 un ejercicio muy alto");
                            ejercicio = Integer.parseInt(infoReader.readLine());
                            if (ejercicio==1)
                            {
                                TMB = TMB*1.2;
                                break;
                            }
                            if (ejercicio==2)
                            {
                                TMB = TMB*1.375;
                                break;
                            }
                            if (ejercicio==3)
                            {
                                TMB = TMB*1.55;
                                break;
                            }
                            if (ejercicio==4)
                            {
                                TMB = TMB*1.725;
                                break;
                            }
                            System.out.println("Ingrese su cantidad de ejercicio de la forma indicada");
                        }

                        IMC = peso/(altura^2);

                        if (IMC<18.5)
                        {
                            System.out.println("actualmente te encuentras en Bajo Peso");
                        }
                        if (IMC>18.5 && IMC<24.9)
                        {
                            System.out.println("actualmente te encuentras en el Rango Normal");
                        }
                        if (IMC>24.9 && IMC<29.9)
                        {
                            System.out.println("actualmente te encuentras en Sobrepeso");
                        }
                        if (IMC>30)
                        {
                            System.out.println("actualmente te encuentras en el Rango de Obesidad");
                        }

                        while(true)
                        {
                            System.out.println("Ingrese su peso ideal");
                            ideal = Integer.parseInt(infoReader.readLine());
                            if ((ideal/altura^2)<=18.5 || (ideal/altura^2)>=30)
                            {
                                break;
                            }
                            System.out.println("Intentar conseguir ese peso es riesgoso para tu salud, por favor escoge otro");
                        }

                        double idealKcal = 0, idealProtein = 0, idealCarbs = 0, idealFats = 0;

                        if(peso>ideal) // quiere bajar
                        {
                            idealKcal=(TMB - TMB*0.1);
                            idealProtein=(7.2*peso);
                            idealFats=(4.5*peso);
                            idealCarbs=((idealKcal-(idealProtein+idealFats))/4);

                            System.out.println("La ingesta ideal debe ser de " + idealKcal + " calorias, " + idealProtein + " gramos de proteina, " + idealFats + " gramos de grasa y " + idealCarbs + " gramos de carbohidratos.");
                        }
                        if(peso==ideal) // quiere mantenerse
                        {
                            idealKcal=TMB;
                            idealProtein=(8.4*peso);
                            idealFats=(9*peso);
                            idealCarbs=((idealKcal-(idealProtein+idealFats))/4);

                            System.out.println("La ingesta ideal debe ser de " + idealKcal + " calorias, " + idealProtein + " gramos de proteina, " + idealFats + " gramos de grasa y " + idealCarbs + " gramos de carbohidratos.");
                        }
                        if(peso<ideal) // quiere subir
                        {
                            idealKcal=(TMB + TMB*0.1);
                            idealProtein=(9.2*peso);
                            idealFats=(13.5*peso);
                            idealCarbs=((idealKcal-(idealProtein+idealFats))/4);

                            System.out.println("La ingesta ideal debe ser de " + idealKcal + " calorias, " + idealProtein + " gramos de proteina, " + idealFats + " gramos de grasa y " + idealCarbs + " gramos de carbohidratos.");
                        }

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

                        break;

                    case 2:
                        try 
                        {
                            if (consoleReader != null) 
                            {
                                consoleReader.close();
                            }
                        } 
                        catch (IOException e) 
                        {
                            e.printStackTrace();
                        }
                        return;
                        // cierra el programa

                    default:
                        System.out.println("Opción no válida.");
            
                }
            }
        } 
        catch (FileNotFoundException e) 
        {
            System.out.println("Error: Archivo no encontrado.");
            e.printStackTrace();
        } 
        catch (IOException e) 
        {
            System.out.println("Error al leer el archivo.");
            e.printStackTrace();
        } 
        finally 
        {
            try 
            {
                if (reader != null) 
                {
                    reader.close();
                    // cierra el lector del archivo
                }
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
    }
}
