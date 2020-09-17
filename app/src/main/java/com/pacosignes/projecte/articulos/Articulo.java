package com.pacosignes.projecte.articulos;

import androidx.annotation.Nullable;

public class Articulo implements Comparable<Articulo> {

    private int ref;
    private String descripcion;
    private String img;

    public Articulo(int ref, String descripcion, @Nullable String img) {
        this.ref = ref;
        this.descripcion = descripcion;
        this.img = img;
    }

    public int getRef() {
        return ref;
    }

    public void setRef(int ref) {
        this.ref = ref;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Articulo{" +
                "ref=" + ref +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }

    @Override
    public int compareTo(Articulo o) {
        return ref-o.getRef();
    }
}
