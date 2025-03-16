package es.udc.sistemasinteligentes;

public abstract class Estado {
    /* El estado deberá sobreescribir estos métodos para mostrarse correctamente y permitir comparaciones. */

    @Override
    public abstract java.lang.String toString();

    @Override
    //Compara si dos estados son equivalentes
    public abstract boolean equals(Object obj);

    @Override
    //Genera un codigo tipo hash para optimizar el almacenamiento
    public abstract int hashCode();

}
