package com.mycompany.proyecto_grupo_cliente_servidor;


public class Proyecto_Grupo_Cliente_Servidor {

    public static void main(String[] args) {
        Tablero tablero=new Tablero();
        tablero.PrepararTablero();
        tablero.MostrarTablero();
        tablero.MoverFicha(0, 0, 6);
        tablero.MostrarTablero();
        tablero.MoverFicha(0, 0, 3);
        tablero.MostrarTablero();
        tablero.MoverFicha(0, 1, 4);
        tablero.MostrarTablero();
    }
}
