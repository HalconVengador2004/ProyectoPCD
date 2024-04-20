/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectocliente_servidor;

/**
 *
 * @author alumno
 */
public class Ficha {
    private int numFicha;
    private int posicion;
    private int casillasRecorridas;
    private colorEnum color;

    private static boolean posFinal = false;
    private static int r = 0;
    private static final int CASILLAS_RECORRER = 63;
    private static final int CASILLAS_FINAL = 8;

    public Ficha(int numFicha, int posicion, int casillasRecorridas, colorEnum color) {
        this.numFicha = numFicha;
        this.posicion = posicion;
        this.casillasRecorridas = casillasRecorridas;
        this.color = color;
    }

    public int getPosicion() {
        return posicion;
    }

    public int getNumFicha() {
        return numFicha;
    }

    public int getCasillasRecorridas() {
        return CASILLAS_RECORRER -casillasRecorridas;
    }



    
    public void moverFicha(int valorDado){

        if(casillasRecorridas < CASILLAS_RECORRER){
            casillasRecorridas+=valorDado;
            posicion += valorDado;

            if(posicion > CASILLAS_RECORRER && casillasRecorridas < CASILLAS_RECORRER){
                System.out.println("La ficha se ha movido a la posicion " + (posicion - CASILLAS_RECORRER));
            }else if(posicion <= CASILLAS_RECORRER && casillasRecorridas < CASILLAS_RECORRER )
                System.out.println("La ficha se ha movido a la posicion " + posicion);
            else{
                System.out.println("La ficha ha llegado a la recta final");
                posFinal = true;
                r = casillasRecorridas -CASILLAS_RECORRER;
            }

        }else {
            r+=valorDado;
            if(r>CASILLAS_FINAL){
                r-=(r-CASILLAS_FINAL);
            }else if(r == CASILLAS_FINAL){
                System.out.println("La ficha ha completado el recorrido");
            }
        }
    }
    
}
