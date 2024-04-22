package com.mycompany.proyectocliente_servidor;
//Reglas:
//Si comes una ficha la ficha comida volvera a casa y el jugador que mueve puede mover 20 una de sus fichas

import java.util.ArrayList;
import java.util.Scanner;

//Es necesario sacar un 5 para salir de casa
//Dos fichas del mismo color en la misma casilla forma un bloqueo
//En el momento que decidas mover una ficha se movera dicha ficha hasta donde pueda, esto quiere decir que si eliges una ficha con un bloqueo justo delante no avanzara nada
//Si intentas sacar una ficha de casa que no puede ser sacada no se movera y perderas el turno
//Sacar un seis de un turno extra, pero si lo haces tres veces seguidas la ultima ficha que moviste volvera a casa FALTA POR IMPLEMENTAR
public class Tablero {

    private final int NUMJUGADORES = 4;
    private final int NUMFICHAS = 4;
    private final int NUMCASILLAS = 68;
    private Ficha[][] lFichas = new Ficha[NUMJUGADORES][NUMFICHAS];
    private boolean[] casillas = new boolean[68];
    private int turnoJugador = 0; //Debe ser (mod 4). Para esto falta por ver cuando acaba el turno de un jugador
    private Jugador[] lJugadores = new Jugador[4];
    private ArrayList<ArrayList<Ficha>> tablero = new ArrayList<>(); // En cada casilla puede haber hasta dos fichas

    

    public Tablero(Jugador[] lJugadores) {
        this.lJugadores = lJugadores;
        //Preparamos el tablero
        for (int j = 0; j < NUMJUGADORES; j++) {
            for (int f = 0; f < NUMFICHAS; f++) {
                lFichas[j][f] = null;//Al principio de la partida ningun jugador tiene fichas. Las añadimos cuando saquen 5
            }
        }
    }

    private void agregarFichaATablero(int numJugador) {

        int numFichasEnJuego = lJugadores[numJugador].getNumeroFichasEnJuego();
        switch (numJugador) {
            case 0 -> //Amarillo
                lFichas[numJugador][numFichasEnJuego] = new Ficha(numFichasEnJuego, 5, 0, ColorEnum.AMARILLO);
            case 1 -> //rojo
                lFichas[numJugador][numFichasEnJuego] = new Ficha(numFichasEnJuego, 39, 0, ColorEnum.ROJO);
            case 2 -> //azul
                lFichas[numJugador][numFichasEnJuego] = new Ficha(numFichasEnJuego, 22, 0, ColorEnum.AZUL);
            case 3 -> //verde
                lFichas[numJugador][numFichasEnJuego] = new Ficha(numFichasEnJuego, 56, 0, ColorEnum.VERDE);
            default -> {
            }
        }
        lJugadores[numJugador].setNumeroFichasEnJuego(lJugadores[numJugador].getNumeroFichasEnJuego()+1);

    }

    public void lanzarDado(int jugadorQueLanza) {
        System.out.println("Se lanza el dado");
        int dado = (int) (Math.random() * 6 + 1);
        if (dado == 5 && numeroFichasEnJuegoJugador(jugadorQueLanza) < 4) {
            agregarFichaATablero(jugadorQueLanza);
            System.out.println("Ha salido un 5. El jugador " + lJugadores[jugadorQueLanza] + " ha obtenido una ficha!");
        } else if (numeroFichasEnJuegoJugador(jugadorQueLanza) > 0){
            System.out.println("Ha salido el numero " + dado);
            for(int i = 0 ; i < lFichas[jugadorQueLanza].length ; i++){
                if (lFichas[jugadorQueLanza][i] != null){
                    System.out.println("La ficha " + lFichas[jugadorQueLanza][i].getNumFicha() + " se encuentra en la posicion " + lFichas[jugadorQueLanza][i].getPosicion() + ". Le quedan por recorrer " + lFichas[jugadorQueLanza][i].getCasillasRecorridas() + " casillas");
                }
                
            }
            System.out.println("¿Que ficha quieres mover?");
            Scanner scanner = new Scanner(System.in);
            int ficha = scanner.nextInt();
            moverFicha(jugadorQueLanza, ficha, dado);
        }
    }

    public int numeroFichasEnJuegoJugador(int numJugador) {
        int contador = 0;
        for (int i = 0; i < lFichas[numJugador].length; i++) {
            if (lFichas[numJugador][i] != null) {
                contador++;
            }
        }
        return contador;
    }

    public boolean hayBarrera(int numCasilla) {
        return tablero.get(numCasilla).size() > 1; //Solo verdadero si hay dos fichas en una casilla
    }

    public boolean comprobar1Mov(Ficha ficha) {

        int casillaSig = ficha.getPosicion() + 1;
        if (casillaSig == 69) {
            casillaSig = 1;
        }
        return hayBarrera(casillaSig);//Comprueba si hay barrera 
        //Otro caso posible entra en la recta final se puede bloquear a si mismo?
    }

    public void moverFicha(int numJugador, int numFicha, int tirada) {
        Ficha ficha = lFichas[numJugador][numFicha];
        int posInicial = ficha.getPosicion();
        if (ficha.isFuera()) {//Caso general
            for (int i = 0; i < tirada; i++) {
                if (comprobar1Mov(ficha)) {
                    ficha.setPosicion(ficha.getPosicion() + 1);
                } else {
                    System.out.println("La ficha ha sido bloqueda");
                    ficha.setPosicion(posInicial); // Si hay una barrera retrocedemos la ficha a donde estaba antes de comprobar si se puede mover
                    break;
                }
            }
            //Actualizamos la posicion de la ficha en el tablero una vez se ha movido
            tablero.get(ficha.getPosicion()).add(ficha);
            tablero.get(posInicial).remove(tablero.get(posInicial).size() - 1);

        }
        /*
        if (posInicial != ficha.getPosicion()) {//Si se ha llegado a mover actualizamos las barreras y vemos si comemos
            //casillas[posInicial] = false;//Sabemos seguro que de donde se ha ido si habia barrera ya no hay
            for (int j = 0; j < NUMJUGADORES; j++) {//Comparamos la posicion de la ficha con el resto de fichas
                for (Ficha i : lFichas[j]) {
                    if (j == numJugador) {//Si la ficha comparada es del mismo jugador miramos barrera
                        if (i.getPosicion() == ficha.getPosicion() && !i.equals(ficha)) {//Si otra ficha del jugador esta en la misma posicion ponemos la barrera
                            casillas[ficha.getPosicion()] = true;
                        }
                    } else if (i.getPosicion() == ficha.getPosicion()) {
                        i.fichaComida();
                        //Creo que seria ya parte del servidor para preguntar al jugador que ficha desea mover, pero ya con la funcion seria poner la funcion con tirada de 20
                    }
                }
            }

        }
         */
        ArrayList<Ficha> fichasCasilla = tablero.get(ficha.getPosicion());
        if (fichasCasilla.size() > 1 && fichasCasilla.get(0).color != fichasCasilla.get(1).color) {
            fichasCasilla.remove(0); //La ficha comida es la que estaba antes que la que acaba de llegar, asi que esta la primera en el array
            //Falta preguntar al jugador que ficha desea mover
        }
    }
    public int getTurnoJugador() {
        return turnoJugador;
    }

    public void setTurnoJugador(int turnoJugador) {
        this.turnoJugador = turnoJugador;
    }

}
