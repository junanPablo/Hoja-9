/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Juan Pablo Merck
 */
import java.util.*;
public class graph {
    ArrayList<String> nodes = new ArrayList<>();
    ArrayList<ArrayList<Integer>> edges = new ArrayList<>();
    ArrayList<ArrayList<Integer>> floyd = new ArrayList<>();
    ArrayList<ArrayList<Integer>> floydNodes = new ArrayList<>();
    
    //Atributo de tipo FloydAlgorithm, que contiene el algoritmo
    FloydAlgorithm algorithm;

    public graph () {
        algorithm = new FloydAlgorithm();
    }

    //ARISTA
    public void addEdge(String line) {
        
        String city1 = "", city2 = "";
        String temp = "", tempNum = "";
        
        for (int i = 0; i < line.length(); i++) {
            if (Character.isLetter(line.charAt(i)) || Character.isDigit(line.charAt(i))) {
                if (Character.isLetter(line.charAt(i)))
                    temp += line.charAt(i);
                else if (Character.isDigit(line.charAt(i)))
                    tempNum += line.charAt(i);
            } else {
                //REVISIÓN O AÑADIDURA DE CIUDAD
                if (!temp.isEmpty()){
                    if (!nodes.contains(temp.toLowerCase()))
                        addNode(temp.toLowerCase());
                    if (city1.isEmpty())
                        city1 = temp.toLowerCase();
                    else
                        city2 = temp.toLowerCase();
                    temp = "";
                }
            }
            
            // DUSTANCIA ENTRE CIUDADES
            if (!tempNum.isEmpty() && i == line.length()-1) {
                edges.get(nodes.indexOf(city1)).set(nodes.indexOf(city2), Integer.parseInt(tempNum));
                edges.get(nodes.indexOf(city2)).set(nodes.indexOf(city1), Integer.parseInt(tempNum));

                floydNodes.get(nodes.indexOf(city1)).set(nodes.indexOf(city2), nodes.indexOf(city1));
                floydNodes.get(nodes.indexOf(city2)).set(nodes.indexOf(city1), nodes.indexOf(city2));
            }
        }

        //RECONSTRUCCION DE MATRIZ, POR MEDIO DE ALGORITMO FLOYD (PROVENIENTE DE LA OTRA CLASE)
        algorithm.floyd(floyd.size(), edges, floydNodes, floyd);
    }

    //PARA NODOS
    private void addNode(String city) {
        //Se añade una nueva fila en cada matriz
        nodes.add(city);
        edges.add(new ArrayList<>());
        floydNodes.add(new ArrayList<>());

        //Se añade una nueva columna con valores iniciales en las matrices
        for (int i = 0; i < nodes.size()-1; i++) {
            edges.get(i).add(Integer.MAX_VALUE);
            edges.get(edges.size()-1).add(Integer.MAX_VALUE);

            floydNodes.get(i).add(-1);
            floydNodes.get(floydNodes.size()-1).add(-1);
        }

        //Por ultimo se añade el ultimo valor de la matriz
        edges.get(edges.size()-1).add(0);
        floydNodes.get(floydNodes.size()-1).add(-1);
    }

    //Funcion que devuelve la distancia mas corta entre dos ciudades
    public int distBetweenNodes(String city1, String city2) {
        int indexCity1 = nodes.indexOf(city1);
        int indexCity2 = nodes.indexOf(city2);

        int dist = floyd.get(indexCity1).get(indexCity2);

        return dist;
    }

    //Funcion para eliminar una arista
    public void deleteEdge (String city1, String city2) {
        //Los valores correspondientes de las ciudades se convierten en valores iniciales
        edges.get(nodes.indexOf(city1)).set(nodes.indexOf(city2), Integer.MAX_VALUE);
        edges.get(nodes.indexOf(city2)).set(nodes.indexOf(city1), Integer.MAX_VALUE);

        floydNodes.get(nodes.indexOf(city1)).set(nodes.indexOf(city2), -1);
        floydNodes.get(nodes.indexOf(city2)).set(nodes.indexOf(city1), -1);

        //RECONSTRUCCIÓN MATRIZ  
        algorithm.floyd(floyd.size(), edges, floydNodes, floyd);
    }

    //Funcion que revisa si se contiene a un elemento en el grafo
    public boolean contains (String city) {
        return nodes.contains(city);
    }

    public String toString() {
        return floydNodes.toString();
    }

    //Función que devuelve un string diciendo la distancia entre ciudades y las ciudades intermedias
    public String toString(String city1, String city2) {
        int index1 = nodes.indexOf(city1);
        int index2 = nodes.indexOf(city2);

        String s = "";

        //Se revisan las ciudades intermedias segun el algoritmo de Floyd y se van agregando a un string
        while (floydNodes.get(index1).get(index2) != index1) {
            s += nodes.get(floydNodes.get(index1).get(index2)).toUpperCase().charAt(0) + nodes.get(floydNodes.get(index1).get(index2)).substring(1) + " -> ";
            index1 = floydNodes.get(index1).get(index2);
        }

        if (!s.isEmpty())
            s = s.substring(0,s.length()-4);

        return s;
    }

    //Función que devuelve el centro de la matriz
    public String getCenter () {
        int centerEx = Integer.MAX_VALUE, ex = 0, center = 0;

        //Se revisan los valores maximos de las columnas de la matriz de Floyd para obtener las excentricidades de cada nodo
        for (int i = 0; i < floyd.size(); i++) {
            for (int j = 0; j < floyd.size(); j++) {
                if (floyd.get(j).get(i) > ex)
                    ex = floyd.get(j).get(i);
            }

            //Se lleva un contador de la excentricidad minima para obtener el centro
            if (centerEx > ex) {
                centerEx = ex;
                center = i;
            }
        }

        return nodes.get(floydNodes.get(1).get(center)).toUpperCase().charAt(0) + nodes.get(floydNodes.get(1).get(center)).substring(1);
    }
}
