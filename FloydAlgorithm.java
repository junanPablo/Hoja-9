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
public class FloydAlgorithm {
        private int[][] P;
    
    public void floyd(int n, ArrayList<ArrayList<Integer>> W, ArrayList<ArrayList<Integer>> P, ArrayList<ArrayList<Integer>> D){
	D = W;
	for (int i = 0; i<n; i++)
	 	for(int j = 0; j<n; j++)
	 		P.get(i).set(j, 0);
	for (int k = 0; k < n; k++)
            for(int i = 0; i < n; i++)
	 	for(int j = 0; j<n; j++){
                    if (D.get(i).get(k) + D.get(k).get(j)< D.get(i).get(j)){
                        int temp = D.get(i).get(k) + D.get(k).get(j);
	 		P.get(i).set(j, k);
                    }
	 	}
}

    public void path(int q, int r){
	path (q, P[q][r]);
	System.out.println("v" + P[q][r]);
	path (P[q][r], r);
    }
}
