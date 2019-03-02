/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author hp
 */
public class Lab2 {

    private static int numOfP, numOfInstance;
    private static int[][] allocation;
    private static int[][] max;
    private static int[][] need;
    private static int[] avail;
    private static int[] request;
    private static Scanner sc;

    private static boolean[] finish;
    private static ArrayList<String> finished;

    public static void main(String[] args) {

        sc = new Scanner(System.in);
        System.out.println("Enter num of procces");
        numOfP = sc.nextInt();
        System.out.println("Enter num of resource");
        numOfInstance = sc.nextInt();
        getALloctaion();
        getMax();
        getAvail();
        CalNeed();
        initializeFinish();
        request();        
        //runBanker();
        printFinished();

    }

    static private void printFinished(){
        for (int i = 0; i < finished.size(); i++) {
            System.out.print(finished.get(i)+"  ");
        }
    }
    
    static private void initializeFinish() {
        finish = new boolean[numOfP];
        for (int i = 0; i < numOfP; i++) {
            finish[i] = false;
        }
    }

    static public void getALloctaion() {

        System.out.println("Enter available");
        allocation = new int[numOfP][numOfInstance];
        for (int i = 0; i < numOfP; i++) {
            System.out.println("Enter available for P: " + i);
            for (int j = 0; j < numOfInstance; j++) {
                allocation[i][j] = sc.nextInt();
            }
        }
    }

    static public void getMax() {

        System.out.println("Enter Max");
        max = new int[numOfP][numOfInstance];
        for (int i = 0; i < numOfP; i++) {
            System.out.println("Enter max for P: " + i);
            for (int j = 0; j < numOfInstance; j++) {
                max[i][j] = sc.nextInt();
            }
        }
    }

    static public void getAvail() {
        avail = new int[numOfInstance];
        for (int i = 0; i < numOfInstance; i++) {
            System.out.println("Enter avail for instance: " + i);
            avail[i] = sc.nextInt();
        }
    }

    static private void CalNeed() {

        need = new int[numOfP][numOfInstance];
        for (int i = 0; i < numOfP; i++) {
            for (int j = 0; j < numOfInstance; j++) {
                need[i][j] = max[i][j] - allocation[i][j];
            }
        }
    }
    
    static public void getRequest(int n){
        request = new int [numOfInstance];
         for(int i =0;i<numOfInstance; i++){
             System.out.println("instance of: " + i);
            request[i] = sc.nextInt();
        }
    }
    
    static public boolean checkLessThanNeed(int n){
        for(int i =0;i<numOfInstance; i++){
            if(!(request[i] <= need[n][i]))
                return false;
        }
        return true;
    }
    
     static public boolean checkLessThanAvail(){
        for(int i =0;i<numOfInstance; i++){
            if(!(request[i] <= avail[i]))
                return false;
        }
        return true;
    }
    
    static public void request(){
        System.out.println("For which process");
        int n = sc.nextInt();
        getRequest(n);
        if(checkLessThanNeed(n)){
            if(checkLessThanAvail()){
                for(int i =0;i<numOfInstance; i++){
                    avail[i] -= request[i];
                    allocation[n][i] += request[i];
                    need[n][i] -= request[i]; 
                }
                runBanker();
            }else{
                System.out.println("This process cannot request now");
            }
        }else{
            System.out.println("This process cannot request now");
        }
        
       
    }
    
    static public boolean checkSafe(){
          for (int i = 0; i < numOfP; i++) {
            if(!finish[i]){
                return false;
            }
        }
          return true;
    }

    static public void runBanker() {
        finished= new ArrayList<>();
        int n = numOfP;
        // hanlf b3dd el processes
        while (n > 0) {
            boolean flag = true;
            for (int i = 0; i < numOfP; i++) {
                // law el process kant 5lsana kaml
                if (!finish[i]) {
                    for (int j = 0; j < numOfInstance; j++) {
                        if (need[i][j] > avail[j]) {
                            flag = false;
                        }
                    }
                    if(flag){
                        // law kant 5lst yeb2a zawedel avail we 7otaha fel finish
                        for (int j = 0; j < numOfInstance; j++) {
                            avail[j] += allocation[i][j];
                        }
                        finish[i] = true;
                        finished.add("P"+i);
                    }
                    flag = true;
                }
            }
            n--;
        }
    }
}
