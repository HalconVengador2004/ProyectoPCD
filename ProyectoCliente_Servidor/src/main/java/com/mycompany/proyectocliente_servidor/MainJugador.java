/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.proyectocliente_servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Usuario
 */
public class MainJugador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            Socket socket = new Socket("127.0.0.1",4444);
            PrintWriter pw = new PrintWriter(socket.getOutputStream(),true);            
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream()); 
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            
            String mensaje = "¡Hola desde el cliente!";
            Scanner scanner = new Scanner(System.in);        

           // pw.flush(); //Vaciar el buffer  No hace falta si ponemos el true en pw
            socket.close(); //Cerrar despues de trabajar con el socket

        }catch(IOException e){
            System.err.println("Capturada InterruptedException. Mensaje: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
    
}
