package com.mycompany.proyecto_grupo_cliente_servidor;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Servidor {

    public static void main(String[] args) throws Exception {
        System.out.println("The chat server is running...");
        ExecutorService pool = Executors.newFixedThreadPool(500);
        try (ServerSocket listener = new ServerSocket(59001)) {
            while (true) {
                pool.execute(new Handler(listener.accept()));
            }
        }
    }

    /**
     * El gestor de la interacción con el cliente. Definido como una clase
     * privada
     */
    private static class Handler implements Runnable {

        private String name;
        private Socket socket;
        private Scanner in;
        private PrintWriter out;

        /**
         * Construye el hilo gestor, le pasa el socket de comunicación con el
         * cliente. Todo el trabajo se realiza en el método run. Recuerda que se
         * llama desde el método main servidor, así que debe ser lo más simple
         * posible.
         */
        public Handler(Socket socket) {
            this.socket = socket;
        }
        public void run() {
            try {
                in = new Scanner(socket.getInputStream());
                out = new PrintWriter(socket.getOutputStream(), true);

                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Exception: " + e);
                    System.out.println("Message: " + e.getMessage());
                    e.printStackTrace(System.err);
                }
            }catch (IOException e) {
            System.out.println("Exception: " + e);
            System.out.println("Message: " + e.getMessage());
            e.printStackTrace(System.err);
        }
        }
        
    }

}
