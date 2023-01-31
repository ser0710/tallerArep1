package edu.escuelaing.arep.app;

import java.io.*;
import java.net.*;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        String urlToUse1 = "http://www.omdbapi.com/";
        String urlToUse2 = "&apikey=18afbfbc";
        String prueba = "?i=tt3896198";
        String urlFinal = urlToUse1 + prueba + urlToUse2;
        URL urlToUse = new URL(urlFinal);

        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        boolean running = true;


        try (BufferedReader reader
                     = new BufferedReader(new InputStreamReader(urlToUse.openStream()))) {
            String inputLine = null;
            while ((inputLine = reader.readLine()) != null) {
                System.out.println(inputLine);
            }
        } catch (IOException x) {
            System.err.println(x);
        }

        serverSocket.close();
    }
}