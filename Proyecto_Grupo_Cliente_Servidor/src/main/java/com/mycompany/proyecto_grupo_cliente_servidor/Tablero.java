
package com.mycompany.proyecto_grupo_cliente_servidor;

public class Tablero {
    private final static int NUM_JUGADORES = 1;
    private final static int NUM_FICHAS= 2;
    private Ficha[] listaFichas=new Ficha[NUM_JUGADORES*NUM_FICHAS];
    
    public void PrepararTablero(){
        for(int i=0;i<NUM_FICHAS;i=i+NUM_JUGADORES){
            listaFichas[i]=new Ficha(Color.Amarillo,i);
            if(NUM_JUGADORES>1){
                listaFichas[i+1]=new Ficha(Color.Azul,i);
            }else if(NUM_JUGADORES>2){
                listaFichas[i+2]=new Ficha(Color.Rojo,i);
            }if(NUM_JUGADORES>3){
                listaFichas[i+3]=new Ficha(Color.Verde,i);
            }
        }
    }
    public void MoverFicha(int numJugador, int numFicha,int tirada){
        listaFichas[numFicha*(NUM_FICHAS-1)+numJugador].MoverFichaGeneral(tirada);
    }

    public void MostrarTablero(){
        for(int i=0;i<listaFichas.length;i++){
            System.out.println("Numero ficha: "+listaFichas[i].getNumero()+ " esta en la posicion: "+listaFichas[i].getPosicion());
        }
    }
    
}
