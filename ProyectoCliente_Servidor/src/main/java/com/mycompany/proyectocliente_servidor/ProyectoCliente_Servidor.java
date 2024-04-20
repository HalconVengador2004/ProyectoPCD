/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyectocliente_servidor;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author alumno
 */
public class ProyectoCliente_Servidor {
    
    private static int lanzarDado(){
        System.out.println("Se lanza el dado");
        int dado = (int)(Math.random()*6 + 1);
        return dado;
    }

    public static void main(String[] args) {
        Ficha[] fichas = new Ficha[5];
        for(int i = 0; i < fichas.length; i++){
            fichas[i]=new Ficha(i+1, 5, 0,colorEnum.AMARILLO);
        }
        Jugador jugador = new Jugador(fichas, colorEnum.AMARILLO);

        int contador = 0;
        Scanner scanner = new Scanner(System.in);
        while(contador < 15){
            int dado =  lanzarDado();

            System.out.println("Ha salido el numero" + dado);
            for(int i = 0 ; i < fichas.length ; i++){
                System.out.println("La ficha " + fichas[i].getNumFicha() + " se encuentra en la posicion " + fichas[i].getPosicion() + ". Le quedan por recorrer " + fichas[i].getCasillasRecorridas() + " casillas");
            }
            System.out.println("¿Que ficha quieres mover?");
            int ficha = scanner.nextInt();
            fichas[ficha].moverFicha(dado);
            contador++;
        }
    }
}
