package es.udc.sistemasinteligentes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class EstrategiaBusquedaGrafo implements EstrategiaBusqueda{

    //Constructor predeterminado para inicializar la estrategia de búsqueda en grafo.
    public EstrategiaBusquedaGrafo(){
    }

    @Override
    public Nodo[] soluciona(ProblemaBusqueda p) throws Exception{




        //Estructura base para la fronteera, usaremos simplemente stack(LIFO) en vez de cola (FIFO)
        //Queue<Nodo> frontera = new LinkedList<>(); //anchura
        Stack<Nodo> frontera = new Stack<>(); //profundidad
        //Nodo inicial, basado en el estado inicial del probelma
        Nodo nodoActual = new Nodo(p.getEstadoInicial(),null , null, null);
        //Agregar nodo inicial a frontera
        frontera.add(nodoActual);
        //Aqui almacenamos los estadis ya explorados evitando ciclos extra
        ArrayList<Estado> explorados = new ArrayList<>();
        //Contadores de print
        int i = 1;
        int nCreados = 1;
        System.out.println((i++) + " - Empezando búsqueda en " + nodoActual.getE());

        while(true) {
            //Si frontea esta vacia y no hay solucion, lanzamos error.
            if (frontera.isEmpty())

                throw new Exception("No se ha podido encontrar una solución");
            //Seleccionamos el siguiente nodo a explorar
            //Pop para profundidad y remove para anchura
//            nodoActual = frontera.remove();
            nodoActual = frontera.pop();
            System.out.println((i++) + " ! Estado actual cambiado a " + nodoActual.getE());
            //Si el nodo actual cumple con el criterio de meta, salimos del bucle
            if (p.esMeta(nodoActual.getE())) break;
            else {
                System.out.println((i++) + " - " + nodoActual.getE() + " no es meta");
                explorados.add(nodoActual.getE());
                //Generamos nodos hijos aplicando todas las acciones posibles
                Accion[] accionesDisponibles = p.acciones(nodoActual.getE());
                for (Accion acc : accionesDisponibles) {
                    //Obtenemos un nuevo estado al aplicar una accion
                    Estado sc = p.result(nodoActual.getE(), acc);
                    //Creamos el nodo correspondiente
                    Nodo nodo = new Nodo(sc,nodoActual , acc, null); nCreados++;
                    System.out.println((i++) + " - RESULT(" + nodoActual.getE() + ","+ acc + ")=" +sc);
                    //Evitamos nodos repetidos ya visitados o ya en frontera
                    if (!frontera.contains(nodo) && !explorados.contains(sc)) {
                        //Anhadimos nodo a la frontera
                        frontera.add(nodo);
                        System.out.println((i++) + " - " + sc + " NO explorado");
                        System.out.println((i++) + " - Nodo anadido a frontera" + nodo);
                    }
                    else
                        System.out.println((i++) + " - " + sc + " ya explorado");
                }
            }
        }
        System.out.println((i) + " - FIN - " + nodoActual);
        System.out.println("Nodos expandidos: " + explorados.size());
        System.out.println("Nodos creados: " + nCreados);
        return reconstruye_sol(nodoActual);
    }

    public Nodo[] reconstruye_sol(Nodo nodo) {
        ArrayList<Nodo> solucion = new ArrayList<>();
        Nodo actual = nodo;
        while(actual != null){
            solucion.add(actual);
            actual = actual.getP();
        }
        return solucion.toArray(new Nodo[0]);
    }
}
