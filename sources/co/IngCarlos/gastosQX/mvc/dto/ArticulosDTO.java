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
public class ArticulosDTO implements Serializable {
   
    String id = Generales.EMPTYSTRING;
    String referencia = Generales.EMPTYSTRING;
    String lote = Generales.EMPTYSTRING;
    String descripcion = Generales.EMPTYSTRING;
    String cantidad = Generales.EMPTYSTRING;
    String unidadMedidad = Generales.EMPTYSTRING;
    String cantidadMax = Generales.EMPTYSTRING;
    String cantidadMin = Generales.EMPTYSTRING;
    String registradoPor = Generales.EMPTYSTRING;
    String fechaRegistro = Generales.EMPTYSTRING;
    String estado = Generales.EMPTYSTRING;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidadMedidad() {
        return unidadMedidad;
    }

    public void setUnidadMedidad(String unidadMedidad) {
        this.unidadMedidad = unidadMedidad;
    }

    public String getCantidadMax() {
        return cantidadMax;
    }

    public void setCantidadMax(String cantidadMax) {
        this.cantidadMax = cantidadMax;
    }

    public String getCantidadMin() {
        return cantidadMin;
    }

    public void setCantidadMin(String cantidadMin) {
        this.cantidadMin = cantidadMin;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
