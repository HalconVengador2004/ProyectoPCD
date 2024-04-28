/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.proyectocliente_servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class MainServidor {

    private static final int NUMJUGADORES = 4;
    private static final ArrayList<Socket> socketJugadores = new ArrayList<>();
    private static final ArrayList<PrintWriter> printWriterJugadores = new ArrayList<>();
    private static final ArrayList<BufferedReader> bufferedReaderJugadores = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(44444);  //Una vez creado nos quedamos a la espera de que alguien se conecte
            System.out.println("Servidor abierto. Esperando a los jugadores...");
            while (socketJugadores.size() < NUMJUGADORES) {
                Socket jugador = serverSocket.accept();
                socketJugadores.add(jugador);

                //Notificamos al jugador de que está conectado al servidor y le informamos del color que le ha tocado
                printWriterJugadores.add(new PrintWriter(jugador.getOutputStream(), true));
                bufferedReaderJugadores.add(new BufferedReader(new InputStreamReader(jugador.getInputStream())));
                System.out.println("Jugadores conectados: " + socketJugadores.size());
                notificarTodos("Jugadores conectados: " + socketJugadores.size());

            }

            notificarTodos("La partida está comenzando...");
            Jugador[] jugadores = new Jugador[4];
            for (int i = 0; i < jugadores.length; i++) {
                jugadores[i] = new Jugador(ColorEnum.values()[i], socketJugadores.get(i));

            }
            Tablero tablero = new Tablero(jugadores);
            int jugadorInicial = 0;
            int dado, contadorSeisesSeguidos = 0;
            while (true) {
                System.out.println("Turno de Jugador " + ColorEnum.values()[jugadorInicial]);
                dado = tablero.lanzarDado(jugadorInicial);
                if (dado != 6){
                    tablero.setTurnoJugador(jugadorInicial);
                    jugadorInicial = (jugadorInicial + 1) % 4;
                    contadorSeisesSeguidos = 0;
                } else {
                    contadorSeisesSeguidos++;
                    if (contadorSeisesSeguidos == 3){
                        if (tablero.getUltimaFichaMovida() != null){
                            tablero.getUltimaFichaMovida().fichaComida();
                        }
                        contadorSeisesSeguidos = 0;
                        tablero.setTurnoJugador(jugadorInicial);
                        jugadorInicial = (jugadorInicial + 1) % 4;
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Capturada InterruptedException. Mensaje: " + e.getMessage());
            e.printStackTrace(System.out);
            System.exit(1);
        }
    }

    public static void notificarTodos(String mensaje) {
        for (int i = 0; i < socketJugadores.size(); i++) {
            printWriterJugadores.get(i).println(mensaje);
        }
    }

    public static void notificarJugador(String mensaje, int numJugador) {
        printWriterJugadores.get(numJugador).println(mensaje);
    }

    public static String leerNotificacion(int numJugador) {
        String notificacion = null;
        try {
            // Restringimos la lectura para que otros jugadores no puedan cambiar el movimiento del jugador activo
            notificacion = bufferedReaderJugadores.get(numJugador).readLine();
        } catch (IOException ex) {
            Logger.getLogger(MainServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return notificacion;
    }

}
