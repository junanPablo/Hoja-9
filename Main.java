/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Juan Pablo Merck
 */
import java.util.Scanner;
import java.io.*;
public class Main {
    
    public static void main(String[]args){
        Scanner teclado = new Scanner(System.in); //Variable para leer los datos ingresados por el usuario
        int op = 0;
        graph cities = new graph();
        
        try {
            BufferedReader bf = new BufferedReader(new FileReader(System.getProperty("user.dir") + "\\guategrafo.txt")); // Direccion donde se encuentra el archivo

            String line;

            //Realiza el ciclo, mientras se encuentren datos
            while ((line = bf.readLine()) != null) {
                //Se agrega la arista correspondiente (y nodo si es necesario)
                cities.addEdge(line);
            }

            //En caso de no poder abrir el archivo, imprime "ERROR"
        } catch (Exception e) {
            System.err.println("ERROR: No se encontro el archivo.");
        }
        
        //****************************************************************************************************
         
        System.out.print("Bienvenido al programa, se presenta el uso del algoritmo Floyd\n" + "*********************************\n" + 
            "1. Ruta más corta\n" +
            "2. Centro\n" +
            "3. Tráfico interrumpido\n" +
            "4. Conexión entre ciudades\n" +
            "5. Salir");
        
        op = (int)teclado.nextDouble();
        
        switch(op){
            
            case 0:
                System.out.print("Menu\n" + "*********************************\n" + 
            "1. Ruta más corta\n" +
            "2. Centro\n" +
            "3. Tráfico interrumpido\n" +
            "4. Conexión entre ciudades\n" +
            "5. Salir");
                break;
                
            case 1:
                System.out.println ("Escriba el nombre de la ciudad origen:");
                String city1 = teclado.next();
                while (!cities.contains(city1)) {
                    System.out.println("Esa ciudad no se encuentra registrada.");
                    city1 = teclado.next();
                }

                System.out.println("Escriba el nombre de la ciudad destino:");
                String city2 = teclado.next();
                while (!cities.contains(city2)) {
                    System.out.println("Esa ciudad no se encuentra registrada.");
                    city2 = teclado.next();
                }

                city1 = city1.toLowerCase();
                city2 = city2.toLowerCase();

                //Se muestra la distancia y las ciudades intermedias
                String temp = cities.toString(city1, city2);
                if (temp.isEmpty())
                    System.out.println("La distancia mas corta es de " + cities.distBetweenNodes(city1, city2) + "KM");
                else
                    System.out.println("La distancia mas corta es de " + cities.distBetweenNodes(city1, city2) + "KM y debe pasar por: " + temp);
                break;
                
            case 2:
                System.out.println("El centro del grafo es: " + cities.getCenter());
                break;
                
            case 3:
                //Se leen las ciudades ingresadas por el usuario
                System.out.println ("Escriba el nombre la primera ciudad:");
                String cit1 = teclado.next();
                while (!cities.contains(cit1)) {
                    System.out.println("Esa ciudad no se encuentra registrada.");
                    cit1 = teclado.next();
                }

                System.out.println ("Escriba el nombre la segunda ciudad:");
                String cit2 = teclado.next();
                while (!cities.contains(cit2)) {
                    System.out.println("Esa ciudad no se encuentra registrada.");
                    cit2 = teclado.next();
                }

                //Se elimina la arista correspondiente
                cities.deleteEdge(cit1, cit2);
                
            case 4:
                //Se leen las ciudades y la distancia
                System.out.println("Escriba el nombre de las ciudades y su distancia. (Ej. Ciudad1 Ciudad2 XXX)");
                String line = teclado.next() + " " + teclado.next() + " " + teclado.next();

                //Se agrega la arista correspondientes (y nodo si es necesario)
                cities.addEdge(line);
                
            case 5:
                System.exit(0);
                break;

                
                
            
        }
        
    }
}
