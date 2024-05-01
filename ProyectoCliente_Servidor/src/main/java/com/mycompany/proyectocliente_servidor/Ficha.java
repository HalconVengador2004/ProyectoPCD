    package com.mycompany.proyectocliente_servidor;

public class Ficha {
    private int numFicha;
    private int posicion; //Les asignaremos la posicion 0 a las fichas que se hayan comido y vuelto a casa
    private int casillasRecorridas;
    private int casillasRecorridasFinal;
    public ColorEnum color;
    private boolean fuera=false;
    private boolean acabado;
    private boolean segura=true; //Si acaba de salir de casa y no puede ser comida
    private  boolean posFinal;
    private static final int CASILLAS_RECORRER = 63;
    private static final int CASILLAS_FINAL = 8;

    public Ficha(int numFicha, int posicion, ColorEnum color) { //Constructor de Ficha
        this.acabado = false;
        this.casillasRecorridasFinal = 0;
        this.posFinal = false;
        this.numFicha = numFicha;
        this.posicion = posicion;
        this.casillasRecorridas = 0;
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

    public boolean isFuera() {
        return fuera;
    }

    public boolean isAcabado() {
        return acabado;
    }

    public boolean isPosFinal() {
        return posFinal;
    }

    public void AumentarCasillasRecorridas() {
        this.casillasRecorridas = casillasRecorridas+1;
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
        casillasRecorridas=0;
        casillasRecorridasFinal=0;
        posFinal=false;
        segura=false;
    }

    public boolean isSegura() {
        return segura;
    }

    public void setSegura(boolean segura) {
        this.segura = segura;
    }

    @Override
    public String toString() {
        String inicial="err";
        if(null!=color)switch (color) {
            case AMARILLO:
                inicial="Y";
                break;
            case AZUL:
                inicial="B";
                break;
            case ROJO:
                inicial="R";
                break;
            case VERDE:
                inicial="G";
                break;
            default:
                break;
        }
        return numFicha+inicial;
    }

    public void setPosFinal(boolean posFinal) {
        this.posFinal=posFinal;
    }
    public boolean DadoVuelta(){
        return casillasRecorridas==CASILLAS_RECORRER;
    }
    
    public int getCasillasRecorridasFinal() {
        return casillasRecorridasFinal;
    }

    public void AumentarCasillasRecorridasFinal(int casillasRecorridasFinal) {
        this.casillasRecorridasFinal = this.casillasRecorridasFinal+casillasRecorridasFinal;
    }
    public void ComprobarAcabado(){
        if(casillasRecorridasFinal>=CASILLAS_FINAL){
            this.acabado=true;
        }
    }

    public void setFuera(boolean fuera) {
        this.fuera = fuera;
    }
}

