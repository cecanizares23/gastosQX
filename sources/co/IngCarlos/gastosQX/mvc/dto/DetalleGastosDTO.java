/*
 * ContextDataResourceNames.java
 *
 * Proyecto: gastosQX
 * Cliente: CSJ
 * Copyright 2018 by Ing. Carlos Cañizares
 * All rights reserved
 */

package co.IngCarlos.gastosQX.mvc.dto;

import co.IngCarlos.gastosQX.common.util.Generales;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.Serializable;

/**
 *
 * @author PC
 */
public class DetalleGastosDTO implements Serializable{
    
    String id = Generales.EMPTYSTRING;
    String idGastos = Generales.EMPTYSTRING;
    String idArticulos = Generales.EMPTYSTRING;
    String registradoPor = Generales.EMPTYSTRING;
    String fechaRegistro = Generales.EMPTYSTRING;
    String cantidad = Generales.EMPTYSTRING;
    String referencia = Generales.EMPTYSTRING;
    String lote = Generales.EMPTYSTRING;
    String unidadMedidad = Generales.EMPTYSTRING;
    String descripcionArt = Generales.EMPTYSTRING;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdGastos() {
        return idGastos;
    }

    public void setIdGastos(String idGastos) {
        this.idGastos = idGastos;
    }

    public String getIdArticulos() {
        return idArticulos;
    }

    public void setIdArticulos(String idArticulos) {
        this.idArticulos = idArticulos;
    }

    public String getRegistradoPor() {
        return registradoPor;
    }

    public void setRegistradoPor(String registradoPor) {
        this.registradoPor = registradoPor;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getUnidadMedidad() {
        return unidadMedidad;
    }

    public void setUnidadMedidad(String unidadMedidad) {
        this.unidadMedidad = unidadMedidad;
    }

    public String getDescripcionArt() {
        return descripcionArt;
    }

    public void setDescripcionArt(String descripcionArt) {
        this.descripcionArt = descripcionArt;
    }
    
    public String toStringJson() {
        String dtoJsonString = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            dtoJsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (Exception e) {
        }
        return dtoJsonString;
    }
    
}
