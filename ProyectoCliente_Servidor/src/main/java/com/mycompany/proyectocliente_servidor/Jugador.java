/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectocliente_servidor;

import java.net.Socket;

/**
 *
 * @author alumno
 */
public class Jugador {
    private Ficha[] fichas;
    private ColorEnum colorJugador;
    private Socket socket;

    public Jugador(ColorEnum colorJugador, Socket socket) {
        this.colorJugador = colorJugador;
        this.socket = socket;
    }

    public ColorEnum getColorJugador() {
        return colorJugador;
    }
}
