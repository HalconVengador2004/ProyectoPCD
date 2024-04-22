/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectocliente_servidor;

/**
 *
 * @author alumno
 */
public class Jugador {
    private Ficha[] fichas;
    private ColorEnum colorJugador;
    private int  numeroFichasEnJuego;

    public Jugador(ColorEnum colorJugador) {
        this.colorJugador = colorJugador;
        this.numeroFichasEnJuego = 0;
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
