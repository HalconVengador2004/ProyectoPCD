package com.mycompany.proyectocliente_servidor;

public class Ficha {
    private int numFicha;
    private int posicion;//Les asignaremos la posicion 100 a las fichas que se hayan comido y vuelto a casa
    private int casillasRecorridas;
    private colorEnum color;
    private boolean fuera;//Hace referencia ha esta afuera del principio 
    private boolean acabado;//Señale si la ficha ha llegado al final

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

    public colorEnum getColor() {
        return color;
    }

    public boolean isFuera() {
        return fuera;
    }

    public boolean isAcabado() {
        return acabado;
    }

    public static boolean isPosFinal() {
        return posFinal;
    }

    public static int getR() {
        return r;
    }

    public static int getCASILLAS_RECORRER() {
        return CASILLAS_RECORRER;
    }

    public static int getCASILLAS_FINAL() {
        return CASILLAS_FINAL;
    }

    public void setCasillasRecorridas(int casillasRecorridas) {
        this.casillasRecorridas = casillasRecorridas;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ficha other = (Ficha) obj;
        if (this.numFicha != other.numFicha) {
            return false;
        }
        if (this.posicion != other.posicion) {
            return false;
        }
        if (this.casillasRecorridas != other.casillasRecorridas) {
            return false;
        }
        if (this.fuera != other.fuera) {
            return false;
        }
        if (this.acabado != other.acabado) {
            return false;
        }
        return this.color == other.color;
    }
    public void fichaComida(){
        posicion=0;
        fuera=false;
        casillasRecorridas=CASILLAS_RECORRER;
    }
    
    
    
    
}
//Prueba cambio Pablo
