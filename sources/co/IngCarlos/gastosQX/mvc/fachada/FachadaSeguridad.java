/*
 * ContextDataResourceNames.java
 *
 * Proyecto: gastosQX
 * Cliente: CSJ
 * Copyright 2018 by Ing. Carlos Cañizares
 * All rights reserved
 */

package co.IngCarlos.gastosQX.mvc.fachada;

import co.IngCarlos.gastosQX.mvc.dto.DatosUsuarioDTO;
import co.IngCarlos.gastosQX.mvc.mediador.MediadorSeguridad;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.annotations.ScriptScope;

/**
 *
 * @author carlos
 */
@RemoteProxy(name = "ajaxSeguridad", scope = ScriptScope.SESSION)
public class FachadaSeguridad {
   
    /**
     *
     */
    public FachadaSeguridad() {
    }

    /**
     *
     * @return
     */
    @RemoteMethod
    public boolean servicioActivo() {
        return true;
    }

    /**
     *
     * @param usuario
     * @return
     */
    @RemoteMethod
    public DatosUsuarioDTO consultarDatosUsuarioLogueado(String usuario) { 
        System.out.print("llega a fachada");
        return MediadorSeguridad.getInstancia().consultarDatosUsuarioLogueado(usuario);
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     * LOS METODOS APARTIR DE AQUI NO HAN SIDO VALIDADOS
     * -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     */
    
}
