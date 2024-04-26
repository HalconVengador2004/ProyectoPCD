/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.proyectocliente_servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class MainJugador {

    private static boolean partidaAcabada = false;

    public static void main(String[] args) {

        try (Socket socket = new Socket("127.0.0.1", 44444)) {
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in);
            while (!partidaAcabada) {
                //Falta actualizar cuando acaba
                String mensaje = br.readLine();
                System.out.println(mensaje);
                if ("¿Que ficha quieres mover?".equals(mensaje)) {
                    String input = scanner.next();
                    pw.println(input);
                }
            }
            //Cerrar despues de trabajar con el socket
        } catch (IOException e) {
            System.err.println("Capturada InterruptedException. Mensaje: " + e.getMessage());
            System.exit(1);
        }
    }

}
