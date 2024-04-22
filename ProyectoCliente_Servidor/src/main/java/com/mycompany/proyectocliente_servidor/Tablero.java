package com.mycompany.proyectocliente_servidor;
//Reglas:
//Si comes una ficha la ficha comida volvera a casa y el jugador que mueve puede mover 20 una de sus fichas
//Es necesario sacar un 5 para salir de casa
//Dos fichas del mismo color en la misma casilla forma un bloqueo
//En el momento que decidas mover una ficha se movera dicha ficha hasta donde pueda, esto quiere decir que si eliges una ficha con un bloqueo justo delante no avanzara nada
//Si intentas sacar una ficha de casa que no puede ser sacada no se movera y perderas el turno
//Sacar un seis de un turno extra, pero si lo haces tres veces seguidas la ultima ficha que moviste volvera a casa FALTA POR IMPLEMENTAR
public class Tablero {
    private final int NUMJUGADORES=4;
    private final int NUMFICHAS=4;
    private Ficha[][] lFichas=new Ficha[NUMJUGADORES][NUMFICHAS];
    private boolean[] casillas=new boolean[68];
    
    public void prepararTablero(){
        for(int j=0;j<NUMJUGADORES;j++){
            for(int f=0;f<NUMFICHAS;f++){
                //lFichas[j][f]=Acabar cuando ficha este acabado
            }
        }
        for(int i=1;i<69;i++){//dejo la posicion 0 libre para que coincida bien y dejamos las pos 0 para las fichas comidas
            casillas[i]=false;
        }
    }
    public boolean hayBarrera(int numCasilla){
        return casillas[numCasilla];
    }
    public boolean comprobar1Mov(Ficha ficha){
        
        if(ficha.getCasillasRecorridas()!=0){//Caso normal
            int casillaSig=ficha.getPosicion()+1;
            if(casillaSig==69){
                casillaSig=1;
            }
            return casillas[casillaSig];//Comprueba si hay barrera 
        }//Otro caso posible entra en la recta final se puede bloquear a si mismo?
        return true;
    }
 
    public void moverFicha(int numJugador,int numFicha,int tirada){      
        Ficha ficha=lFichas[numJugador][numFicha];
        int posInicial=ficha.getPosicion();
        if(ficha.isFuera()){//Caso general
            for(int i=0;i<tirada;i++){
                if(comprobar1Mov(ficha)){
                    ficha.setPosicion(ficha.getPosicion()+1);
                }else{
                    System.out.println("La ficha ha sido bloqueda");
                    break;
                }
            }
        }else{//Caso en el que salga
            if(tirada==5){
                switch (numJugador) {
                    case 0:
                        //Amarillo
                        ficha.setPosicion(5);
                        break;
                    case 1:
                        //rojo
                        ficha.setPosicion(39);
                        break;
                    case 2:
                        //azul
                        ficha.setPosicion(22);
                        break;
                    case 3:
                        //verde
                        ficha.setPosicion(56);
                        break;
                    default:
                        break;
                }
            }
        }
        if(posInicial!=posInicial){//Si se ha llegado a mover actualizamos las barrearas y vemos si comomemos
            casillas[posInicial]=false;//Sabemos seguro que de donde se ha ido si habia barrera ya no hay
            for(int j=0;j<NUMJUGADORES;j++){//Comparamos la posicion de la ficha con el resto de fichas
                for(Ficha i:lFichas[j]){
                    if(j==numJugador){//Si la ficha comparada es del mismo jugador miramos barrera
                        if(i.getPosicion()==ficha.getPosicion() && !i.equals(ficha)){//Si otra ficha del jugador esta en la misma posicion ponemos la barrera
                            casillas[ficha.getPosicion()]=true;
                        }
                    }else
                        if(i.getPosicion()==ficha.getPosicion()){
                            i.fichaComida();
                            //Creo que seria ya parte del servidor para preguntar al jugador que ficha desea mover, pero ya con la funcion seria poner la funcion con tirada de 20
                        }
                }    
            }
                  
        }
    }
    
    
}
