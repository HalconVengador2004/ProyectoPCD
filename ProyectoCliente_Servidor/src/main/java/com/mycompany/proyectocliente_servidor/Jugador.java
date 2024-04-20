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
    private colorEnum colorJugador;

    public Jugador(Ficha[] fichas, colorEnum colorJugador) {
        this.fichas = fichas;
        this.colorJugador = colorJugador;
    }

    public colorEnum getColorJugador() {
        return colorJugador;
    }
    

}
