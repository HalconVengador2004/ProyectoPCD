package com.mycompany.proyectocliente_servidor;

public class MainPruebaTablero {

    public static void main(String[] args) {
        Jugador a=new Jugador(ColorEnum.ROJO);
        Jugador b=new Jugador(ColorEnum.VERDE);
        Jugador c=new Jugador(ColorEnum.AMARILLO);
        Jugador d=new Jugador(ColorEnum.AZUL);
        Jugador[] lJugadores=new Jugador[4];
        lJugadores[0]=a;
        lJugadores[1]=b;
        lJugadores[2]=c;
        lJugadores[3]=d;
        Tablero tab=new Tablero(lJugadores);
        
        tab.mostrarTablero();
    }
    
}
