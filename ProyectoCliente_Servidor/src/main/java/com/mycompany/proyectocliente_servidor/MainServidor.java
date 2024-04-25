/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.proyectocliente_servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Usuario
 */
public class MainServidor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            ServerSocket serverSocket = new ServerSocket(44444);  //Una vez creado nos quedamos a la espera de que alguien se conecte
            while(true){
                System.out.println("Servidor abierto. Esperando a los jugadores...");
                Socket jugador = serverSocket.accept(); 
                //////////
            }


        }catch(IOException e){
            System.err.println("Capturada InterruptedException. Mensaje: " + e.getMessage());
            e.printStackTrace(System.out);
            System.exit(1);
        }
    }
    
}
