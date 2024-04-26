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
    private int  numeroFichasEnJuego;
    private Socket socket;

    public Jugador(ColorEnum colorJugador, Socket socket) {
        this.colorJugador = colorJugador;
        this.numeroFichasEnJuego = 0;
        this.socket = socket;
    }

    public ColorEnum getColorJugador() {
        return colorJugador;
    }

    public int getNumeroFichasEnJuego() {
        return numeroFichasEnJuego;
    }

    public void setNumeroFichasEnJuego(int numeroFichasEnJuego) {
        this.numeroFichasEnJuego = numeroFichasEnJuego;
    }


    
    
    

}
