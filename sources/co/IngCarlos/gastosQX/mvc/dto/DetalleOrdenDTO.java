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
public class DetalleOrdenDTO implements Serializable{
    
    String id = Generales.EMPTYSTRING;
    String idOrdenCompra = Generales.EMPTYSTRING;
    String idArticulo = Generales.EMPTYSTRING;    
    String registradoPor = Generales.EMPTYSTRING;
    String fechaRegistro = Generales.EMPTYSTRING;
    String cantidadArt = Generales.EMPTYSTRING;
    String referenciaArt = Generales.EMPTYSTRING;
    String loteArt = Generales.EMPTYSTRING;
    String descripcionArt = Generales.EMPTYSTRING;
    String unidadMedida = Generales.EMPTYSTRING;
    String estadoArt = Generales.EMPTYSTRING;
    String idGasto = Generales.EMPTYSTRING;
    String fechaOrdenCompra = Generales.EMPTYSTRING;
    String estadoOrdenCompra = Generales.EMPTYSTRING;
    String confirmadoOrdenCompra = Generales.EMPTYSTRING;
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdOrdenCompra() {
        return idOrdenCompra;
    }

    public void setIdOrdenCompra(String idOrdenCompra) {
        this.idOrdenCompra = idOrdenCompra;
    }

    public String getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(String idArticulo) {
        this.idArticulo = idArticulo;
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

    public String getCantidadArt() {
        return cantidadArt;
    }

    public void setCantidadArt(String cantidadArt) {
        this.cantidadArt = cantidadArt;
    }

    public String getReferenciaArt() {
        return referenciaArt;
    }

    public void setReferenciaArt(String referenciaArt) {
        this.referenciaArt = referenciaArt;
    }

    public String getLoteArt() {
        return loteArt;
    }

    public void setLoteArt(String loteArt) {
        this.loteArt = loteArt;
    }

    public String getDescripcionArt() {
        return descripcionArt;
    }

    public void setDescripcionArt(String descripcionArt) {
        this.descripcionArt = descripcionArt;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public String getEstadoArt() {
        return estadoArt;
    }

    public void setEstadoArt(String estadoArt) {
        this.estadoArt = estadoArt;
    }

    public String getIdGasto() {
        return idGasto;
    }

    public void setIdGasto(String idGasto) {
        this.idGasto = idGasto;
    }

    public String getFechaOrdenCompra() {
        return fechaOrdenCompra;
    }

    public void setFechaOrdenCompra(String fechaOrdenCompra) {
        this.fechaOrdenCompra = fechaOrdenCompra;
    }

    public String getEstadoOrdenCompra() {
        return estadoOrdenCompra;
    }

    public void setEstadoOrdenCompra(String estadoOrdenCompra) {
        this.estadoOrdenCompra = estadoOrdenCompra;
    }

    public String getConfirmadoOrdenCompra() {
        return confirmadoOrdenCompra;
    }

    public void setConfirmadoOrdenCompra(String confirmadoOrdenCompra) {
        this.confirmadoOrdenCompra = confirmadoOrdenCompra;
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
