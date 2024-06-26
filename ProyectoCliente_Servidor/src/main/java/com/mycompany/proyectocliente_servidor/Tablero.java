package com.mycompany.proyectocliente_servidor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Tablero {

    private final int NUMJUGADORES = 4;
    private final int NUMFICHAS = 4;
    private final int NUMCASILLAS = 68;
    private Ficha[][] lFichas = new Ficha[NUMJUGADORES][NUMFICHAS];
    private boolean[] casillas = new boolean[68];
    private int turnoJugador = 0;  //Debe ser (modulo 4).
    private Jugador[] lJugadores = new Jugador[4];
    private ArrayList<ArrayList<Ficha>> tablero; // En cada casilla puede haber dos fichas COMO MAXIMO
    private Ficha ultimaFichaMovida;

    public Tablero(Jugador[] lJugadores) {
        this.lJugadores = lJugadores;
        //Preparamos el tablero
        for (int j = 0; j < NUMJUGADORES; j++) {
            for (int f = 0; f < NUMFICHAS; f++) {
                lFichas[j][f] = new Ficha(f, 0, ColorEnum.values()[j]);
            }
        }
        this.tablero = new ArrayList<>(NUMCASILLAS);
        for (int i = 0; i < NUMCASILLAS + 1; i++) {
            this.tablero.add(new ArrayList<>());
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
        lFichas[numJugador][numFicha].setFuera(true);
        tablero.get(lFichas[numJugador][numFicha].getPosicion()).add(lFichas[numJugador][numFicha]); //Pasamos la ficha de la lista al tablero
    }

    public int lanzarDado(int jugadorQueLanza) {
        MainServidor.notificarTodos("---TURNO DE " + ColorEnum.values()[jugadorQueLanza] + "---");
        System.out.println("---TURNO DE " + ColorEnum.values()[jugadorQueLanza] + "---");
        MainServidor.notificarTodos("Se lanza el dado");
        int dado = (int) (Math.random() * 6 + 1);
        MainServidor.notificarTodos("Ha salido el numero " + dado);
        if (dado == 5) {
            MainServidor.notificarJugador("Has sacado un cinco, si selecionas una ficha que este en tu casa saldra al tablero", jugadorQueLanza);
        }
        
        for (int j = 0; j < NUMJUGADORES; j++) {    
            System.out.println(ColorEnum.values()[j] + ":");
            MainServidor.notificarTodos(ColorEnum.values()[j] + ":");
            for (int i = 0; i < lFichas[j].length; i++) {
                if (lFichas[j][i] != null && lFichas[j][i].getPosicion() != 0) {
                    if(lFichas[j][i].isPosFinal()){
                          MainServidor.notificarTodos("Ficha " + lFichas[j][i].getNumFicha() + ": Recta final  " +  (8-lFichas[j][i].getCasillasRecorridasFinal())+" para terminar");
                    }else{
                        MainServidor.notificarTodos("Ficha " + lFichas[j][i].getNumFicha() + ": Casilla " + lFichas[j][i].getPosicion());
                    }
                    System.out.println("Ficha " + lFichas[j][i].getNumFicha() + ": Casilla " + lFichas[j][i].getPosicion());
                }

            }
        }
        int codigo;
        int contador = 0;
        do {
            MainServidor.notificarJugador("¿Que ficha quieres mover?", jugadorQueLanza);
            System.out.println("---ESPERANDO ELECCION JUGADOR---");
            String notificacion = MainServidor.leerNotificacion(jugadorQueLanza);

            while (notificacion == null) {
                notificacion = MainServidor.leerNotificacion(jugadorQueLanza);
            }

            int ficha = Integer.parseInt(notificacion);
            System.out.println("---ELECCION JUGADOR RECIBIDA---");
            if (ficha <= 3 & ficha >= 0) {
                codigo = moverFicha(jugadorQueLanza, ficha, dado);
                if (codigo == 1) {
                    MainServidor.notificarJugador("No puedes mover la ficha escogida, por favor selecione otra. ", jugadorQueLanza);
                    contador = contador + 1;
                }
            } else if (ficha == 5) {
                codigo = 0;
                MainServidor.notificarJugador("Tu turno ha finalizado", jugadorQueLanza);
            } else {
                MainServidor.notificarJugador("No has seleccionado una ficha valida", jugadorQueLanza);
                codigo = 1;
            }
            if (contador == 4) {
                MainServidor.notificarJugador("No tienes moviemiento valido, o has intentado mover varias veces la misma fica, por lo tanto se te pasa el turno.", jugadorQueLanza);
                codigo = 0;
            }
        } while (codigo == 1); //Como no es valido, no pasamos el turno sino que le dejamos volver a elegir
        return dado;
    }

    public boolean hayBarrera(int numCasilla) {
        return tablero.get(numCasilla).size() > 1; //Solo verdadero si hay dos fichas en una casilla
    }

    public boolean comprobar1Mov(Ficha ficha) {
        //Cuando haces un movimiento, puede que adelante tengas una barrera,
        //Se va comprobando en cada casilla si la siguiente tiene barrera, de manera que si se llega a una
        //Se pierde el turno y se vuelve a la casilla inicial
        int casillaSig = ficha.getPosicion() + 1;
        if (casillaSig == 69) {
            casillaSig = 1;
        }
        return hayBarrera(casillaSig);//Comprueba si hay barrera 
    }

    public int moverFicha(int numJugador, int numFicha, int tirada) {
        Ficha ficha = lFichas[numJugador][numFicha];
        int posInicial = ficha.getPosicion();
        if (ficha.isAcabado()) {
            MainServidor.notificarJugador("La ficha ya ha acabado, no puedes moverla", numJugador);
            return 1;
        } else if (ficha.isPosFinal()) {
            ficha.AumentarCasillasRecorridasFinal(tirada);
            ficha.ComprobarAcabado();
            if (ficha.isAcabado()) {
                MainServidor.notificarTodos("La ficha " + ficha + " ha llegado al final del tablero");
            }
            if (ficha.getPosicion() != 0) {//La quita de las casillas
                tablero.get(posInicial).remove(tablero.get(posInicial).size() - 1);//Como va a entrar a la recta final quita de la lista de casillas
                ficha.setPosicion(0);
            }
            return 0;
        } else if (ficha.isFuera()) {//Caso general
            for (int i = 0; i < tirada; i++) {
                if (!comprobar1Mov(ficha)) {
                    ficha.setPosicion(ficha.getPosicion() + 1);
                    ficha.AumentarCasillasRecorridas();
                    if (ficha.getPosicion() == 69) {//Que el tablero sea circular
                        ficha.setPosicion(1);
                    }
                    if (ficha.DadoVuelta()) {//Si esta en la casilla de antes de entrar al final
                        ficha.setPosFinal(true);
                        if (i + 1 < tirada) {//Si no va a entrar en la recta se actualiza la casilla normal y la siguiente vez entrara  
                            return moverFicha(numJugador, numFicha, tirada - i - 1);
                        }
                    }
                } else {
                    MainServidor.notificarJugador("Movimiento inválido. Elige otro movimiento.", numJugador);
                    ficha.setPosicion(posInicial); // Si hay una barrera retrocedemos la ficha a donde estaba antes de comprobar si se puede mover
                    return 1;
                }
            }
            ultimaFichaMovida = ficha;
            //Actualizamos la posicion de la ficha en el tablero una vez se ha movido
            tablero.get(ficha.getPosicion()).add(ficha);
            tablero.get(posInicial).remove(tablero.get(posInicial).size() - 1);

            //Si se ha movido ya no estara en casilla de su color y se puede comer
            if (posInicial != ficha.getPosicion()) {
                ficha.setSegura(false);
            }
            ArrayList<Ficha> fichasCasilla = tablero.get(ficha.getPosicion());
            if (fichasCasilla.size() > 1 && fichasCasilla.get(0).color != fichasCasilla.get(1).color && !fichasCasilla.get(0).isSegura()) {
                fichasCasilla.get(0).fichaComida();//Se le vueven a poner los valores por defecto
                fichasCasilla.remove(0); //La ficha comida es la que estaba antes que la que acaba de llegar, asi que esta la primera en el array
                int comer = moverFicha(numJugador, numFicha, 20);
                if (comer == 0) {
                    MainServidor.notificarTodos("La ficha " + lFichas[numJugador][numFicha] + " ha comida una ficha y ha avanzado 20 posiciones.");
                } else {
                    MainServidor.notificarTodos("La ficha " + lFichas[numJugador][numFicha] + " ha comida una ficha y ha intentado avanzar 20 posiciones, pero ha sido bloqueada.");
                }
            }
            return 0;
        } else {//Quiere salir de casa
            if (tirada == 5) {
                int casillaSalida;
                switch (numJugador) {
                    case 0 ->
                        casillaSalida = 5;
                    case 1 ->
                        casillaSalida = 39;
                    case 2 ->
                        casillaSalida = 22;
                    case 3 ->
                        casillaSalida = 56;
                    default -> {
                        casillaSalida = 0;
                    }
                }
                if (hayBarrera(casillaSalida)) {
                    MainServidor.notificarJugador("Movimiento invalido, la casilla de salida esta bloqueda.", numJugador);//la funcion ya la agrega a casillas
                    return 1;
                } else {
                    agregarFichaATablero(numJugador, numFicha);
                    return 0;
                }
            } else {
                MainServidor.notificarJugador("La ficha seleccionada no puede salir de casa porque no has sacado un 5.", numJugador);
                return 1;
            }
        }
    }

    public int getTurnoJugador() { //Devuelve el turno del jugador al que le toca 
        return turnoJugador;
    }

    public void setTurnoJugador(int turnoJugador) { //Se modifica el turno del jugador al que le toca
        this.turnoJugador = turnoJugador;
    }

    public Ficha getUltimaFichaMovida() {
        return ultimaFichaMovida;
    }

    public void setUltimaFichaMovida(Ficha ultimaFichaMovida) {
        this.ultimaFichaMovida = ultimaFichaMovida;
    }
    
    public int PartidaAcabada() {
        int contador = 0;
        for (int i = 0; i < NUMJUGADORES; i++) {
            for (int j = 0; j < NUMFICHAS; j++) {
                if (lFichas[i][j].isAcabado()) {
                    contador = contador + 1;
                }
            }
            if (contador == 4) {
                return i;
            }
        }
        return 5;
    }
}
