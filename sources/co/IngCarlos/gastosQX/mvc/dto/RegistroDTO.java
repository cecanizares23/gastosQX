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
 * @author carlos
 */
public class RegistroDTO implements Serializable {
    
    boolean condicion = false;
    String mensaje = Generales.EMPTYSTRING;
    String fechaFinalProyecto = Generales.EMPTYSTRING;

    public boolean isCondicion() {
        return condicion;
    }

    public void setCondicion(boolean condicion) {
        this.condicion = condicion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getFechaFinalProyecto() {
        return fechaFinalProyecto;
    }

    public void setFechaFinalProyecto(String fechaFinalProyecto) {
        this.fechaFinalProyecto = fechaFinalProyecto;
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
