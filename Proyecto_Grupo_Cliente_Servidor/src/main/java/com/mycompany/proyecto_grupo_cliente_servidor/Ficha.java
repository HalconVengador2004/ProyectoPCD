package com.mycompany.proyecto_grupo_cliente_servidor;

import com.mycompany.proyecto_grupo_cliente_servidor.Color;

public class Ficha {
    private int posicion;
    private Color color;
    private int casillasFaltantes;
    private boolean rectaFinal;
    private boolean segura;
    private boolean fuera=false;
    private int numero;

    public Ficha(Color color,int numero) {
        this.color = color;
        this.numero=numero;
    }  
    public void SacarCasa(int tirada){
        switch (color) {
            case Amarillo -> posicion=5;
            case Azul -> posicion=22;
            case Rojo -> posicion=39;
            case Verde -> posicion=56;
        }
        posicion=posicion+tirada-1;
        fuera=true;
    }
    public void MoverFichaAfuera(int tirada){
        posicion=posicion+tirada;
    }
    public void MoverFichaGeneral(int tirada){
        if(fuera==false){
            SacarCasa(tirada);
        }else{
            MoverFichaAfuera(tirada);
        }
        if(posicion>63){
            posicion=posicion-63;
        }
    }

    public int getPosicion() {
        return posicion;
    }

    public int getNumero() {
        return numero;
    }
}
