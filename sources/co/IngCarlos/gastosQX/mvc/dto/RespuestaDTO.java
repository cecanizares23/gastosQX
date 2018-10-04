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
import java.util.ArrayList;

/**
 *
 * @author carlos
 */
public class RespuestaDTO implements Serializable {

    String condicion = Generales.EMPTYSTRING;
    String mensaje = Generales.EMPTYSTRING;
    String tabla = Generales.EMPTYSTRING;
    String registro = Generales.EMPTYSTRING;
    ArrayList mensajes = new ArrayList();
   
    String actividadesViejas = Generales.EMPTYSTRING;

    public String getActividadesViejas() {
        return actividadesViejas;
    }

    public void setActividadesViejas(String actividadesViejas) {
        this.actividadesViejas = actividadesViejas;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
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

    public ArrayList getMensajes() {
        return mensajes;
    }

    public void setMensajes(ArrayList mensajes) {
        this.mensajes = mensajes;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

}
