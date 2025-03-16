package es.udc.sistemasinteligentes.ejemplo;

import es.udc.sistemasinteligentes.*;


import java.util.ArrayList;

public class Estrategia4 implements EstrategiaBusqueda {
    //Para Continuar con la primera parte del ejercicio, tenemos aquí presente la siguiente
    //representación en java de lo que es la Estrategia4, vamos ahora a cambiar la original para así poder meterle
    //Nuestra nueva clase Nodo[].

    public Estrategia4() {
    }

    @Override
    //Lo primero que hemos hecho aquí ha sido cambiar el tipo de soluciona
    //Hemos pasado de un tipo estado a un tipo Nodo[].
    public Nodo[] soluciona(ProblemaBusqueda p) throws Exception {
        ArrayList<Estado> explorados = new ArrayList<Estado>();
        Estado estadoActual = p.getEstadoInicial();
        explorados.add(estadoActual);
        //Ahora no nos vale simplemente con los tres atributos que tenemos en el principio
        //Ahora debemos de inicializar nodo.
        Nodo nodoActual = new Nodo(estadoActual, null, null, null);

        int i = 1;

        System.out.println((i++) + " - Empezando búsqueda en " + estadoActual);

        //Mientras p que es problema búsqueda no sea meta en en el estado actual:
        while (!p.esMeta(estadoActual)) {
            //Esto es un printeo simbólico para contar el número de veces que hemos pasado por un
            //Estado que no sea la solución
            System.out.println((i++) + " - " + estadoActual + " no es meta");
            //Ahora, tal y como estreategia4 funciona en la lógica de este programa tenemos que comprobar
            //Las acciones disponibles, siendo cada acción disponible un tipo array Accion, que va a ser la accion en el problema en el estado actual.
            Accion[] accionesDisponibles = p.acciones(estadoActual);
            //De manera predeterminada pondremos modificado a un bool FALSE (realmente es como no poner nada porque)
            //De manera predeterminada ya sabemos que los valores bool están a false pero lo ponemos por si las moscas
            boolean modificado = false;
            //Ahora debemos recorrer todas las acciones, usaremos un enhanced loop para esto, es decir
            //Usaremos un tipo de bucle for que nos permita pillar todos los elementos de una clase.
            for (Accion acc : accionesDisponibles) {
                //Ahora por cada vez que recorramos las accionesDisponibles en acción vamos a crear una variable
                //De tipo estado que guarde el resultado del problema
                Estado sc = p.result(estadoActual, acc);
                //Este printeo nos mostrará el resultado de la acción, que ya hemos guardado en sc
                System.out.println((i++) + " - RESULT(" + estadoActual + "," + acc + ")=" + sc);
                //Ahora si es que los estados explorados NO contienen el resultado sc
                if (!explorados.contains(sc)) {
                    //El estado actual pasa a ser sc
                    estadoActual = sc;
                    //Por lo tanto declaramos evidentemente que no está explorado
                    System.out.println((i++) + " - " + sc + " NO explorado");
                    //El estado actual pasa a aser añadido a la lista de explorados
                    explorados.add(estadoActual);
                    //Modificado pasa a ser true
                    modificado = true;
                    //Printeo simbólico
                    System.out.println((i++) + " - Estado actual cambiado a " + estadoActual);
                    //Como últomo paso, tenemos que actualizar los datos del nodo,
                    nodoActual = new Nodo(estadoActual, nodoActual, acc, null);
                    break;
                } else
                    //evidentemente, si se da la condición de que ha sido exploradp lo mencionamos
                    // y aumentamos i
                    System.out.println((i++) + " - " + sc + " ya explorado");
            }
            if (!modificado) throw new Exception("No se ha podido encontrar una solución");
        }
        System.out.println((i++) + " - FIN - " + estadoActual);
        return reconstruye_sol(nodoActual);
    }

    //Nueva función que necesito de manera exclusica para devolver la solución completa

    public Nodo[] reconstruye_sol(Nodo nodo) {
        //Creamos una lista de soluciones de tipo nodo
        ArrayList<Nodo> solucion = new ArrayList<Nodo>();
        Nodo actual = nodo;
        //Mientras el nodo actual sea distinto de nulo entones añadimos
        //Al array solucion el nodo actual
        //y sacamos el padre, asi vamos a tener el padre anterior y el padre posterior
        //guardado.
        while (actual != null) {
            solucion.add(actual);
            actual = actual.getP();
        }

        return solucion.toArray(new Nodo[0]);

    }


}
