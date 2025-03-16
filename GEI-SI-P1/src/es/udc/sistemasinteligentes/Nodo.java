package es.udc.sistemasinteligentes;

public class Nodo implements Comparable<Nodo> {
//Para el ejercicio 1 se nos va a pedir implementar una clase que sea la
//Clase NODO mediante esta clase lo que vamos a hacer va a ser modificar la
//Estratrgia 4 para que funcione con Nodos


    //Vamos a necesitar una serie de atributos, entre ellos serán estos
    //Estado,nodo,acción coste
    private Estado e;
    private Nodo p;
    private Accion a;
    private float coste;
    private float f;


    public Nodo(Estado e, Nodo p, Accion a, Heuristica h) {

        this.e = e;
        this.p = p;
        this.a = a;
        if (p != null) {
            //Si el nodo no es nulo, entonces la funcíon coste pasa a valer la suma del coste de accion y del nodo

            this.coste = p.coste + a.getCoste();
            if (h != null) {
                //Por otra parte en caso de que haya heurística, es decir que sea != de nula, vamos a sumar coste
                //+ lo que valga en cuestión la función evalúa.
                this.f = this.coste + h.evalua(e);
            }

        }


    }

    //Getters necesarios ya que hemos declarado nuestros atributos como privados y por lo tanto vamos a necesitar
    //De alguna manera recopilar su valor.
    public Accion getA() {
        return a;
    }
    public Estado getE() {
        return e;
    }
    public Nodo getP() {
        return p;
    }
    public float getF() {
        return f;
    }
    public float getCoste() {
        return coste;
    }

    //Representación gráfica sin más de cada atributo menos Nodo
    @Override
    public String toString() {
        return "(" + e +
                ", " + a +
                ", " + coste +
                ", " + f +
                ')';

    }

    //El orden se va a basare en la función F
    public int compareTo(Nodo o) {
        return o.f < this.f ? 1 : -1;
    }
    //Si o.f es menos que this.f, el nodo actual se clasifica
    //Después del nodo o en el orden, queremos que nuestro algoritmo expanda el nodo
    //Con el menor valor de f.

}
