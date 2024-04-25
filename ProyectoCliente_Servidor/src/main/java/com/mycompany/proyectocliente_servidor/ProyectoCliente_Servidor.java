/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.proyectocliente_servidor;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author alumno
 */
public class ProyectoCliente_Servidor {

    public static void main(String[] args) {

        Jugador[] jugadores = new Jugador[4];
        for (int i = 0; i < jugadores.length; i++) {
            jugadores[i] = new Jugador(ColorEnum.values()[i]);

        }
        Tablero tablero = new Tablero(jugadores);
        int jugadorInicial = 0;
        while (true) {//Falta determinar cuando acaba la partida y quien empieza
            System.out.println("Turno de Jugador " + ColorEnum.values()[jugadorInicial]);
            tablero.lanzarDado(jugadorInicial);
            tablero.setTurnoJugador(jugadorInicial);
            jugadorInicial = (jugadorInicial + 1)%4;
        }
    }
}
