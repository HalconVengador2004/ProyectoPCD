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

    private void agregarFichaATablero(int numJugador, int numFicha) {
        switch (numJugador) {
            case 0 -> //Amarillo
                lFichas[numJugador][numFicha].setPosicion(5);
            case 1 -> //rojo
                lFichas[numJugador][numFicha].setPosicion(39);
            case 2 -> //azul
                lFichas[numJugador][numFicha].setPosicion(22);
            case 3 -> //verde
                lFichas[numJugador][numFicha].setPosicion(56);
            default -> {
            }
        }
        lJugadores[numJugador].setNumeroFichasEnJuego(lJugadores[numJugador].getNumeroFichasEnJuego() + 1);

    }

    public void lanzarDado(int jugadorQueLanza) {
        System.out.println("Se lanza el dado");
        int dado = (int) (Math.random() * 6 + 1);
        System.out.println("Ha salido el numero " + dado);
        mostrarTablero();
        System.out.println("¿Que ficha quieres mover?");
        Scanner scanner = new Scanner(System.in);
        int ficha = scanner.nextInt();
        moverFicha(jugadorQueLanza, ficha, dado);
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
                    if (ficha.getPosicion() == 69) {//Que el tablero sea circular
                        ficha.setPosicion(1);
                    }
                } else {
                    System.out.println("La ficha ha sido bloqueda");
                    ficha.setPosicion(posInicial); // Si hay una barrera retrocedemos la ficha a donde estaba antes de comprobar si se puede mover
                    break;
                }
            }
            //Actualizamos la posicion de la ficha en el tablero una vez se ha movido
            tablero.get(ficha.getPosicion()).add(ficha);
            if(posInicial!=0){//Si estaba en casa(pos 0) no estab en el tablero y no hace falta borrarla, solo añadirla
                tablero.get(posInicial).remove(tablero.get(posInicial).size() - 1);
            }
            //Si se ha movido ya no estara en casilla de su color y se puede comer
            if (posInicial != ficha.getPosicion()) {
                ficha.setSegura(false);
            }
        }else{
            if(tirada==5){
                agregarFichaATablero(numJugador, numFicha);
            }else{
                System.out.println("No has podido sacar la ficha de casa, necesitas un 5 para salir. ");
            }
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
        if (fichasCasilla.size() > 1 && fichasCasilla.get(0).color != fichasCasilla.get(1).color && !fichasCasilla.get(0).isSegura()) {
            fichasCasilla.get(0).fichaComida();
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

    public void mostrarTablero() {//version provisional para comprobar las funciones
        for (int i = 1; i < 11; i++) {
            System.out.print(i + " ,_");
        }
        for (int i = 10; i < 69; i++) {
            System.out.print(i + ",_");
        }
        for (int n = 0; n < NUMFICHAS; n++) {
            for (int c = 1; c < 69; c++) {
                if (tablero.get(c).get(n) != null) {
                    System.out.print(tablero.get(c).get(n) + ",_");
                } else {
                    System.out.print("  ,_");
                }
            }
        }

    }
}
