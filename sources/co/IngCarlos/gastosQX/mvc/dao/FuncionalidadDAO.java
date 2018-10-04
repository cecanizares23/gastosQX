/*
 * ContextDataResourceNames.java
 *
 * Proyecto: gastosQX
 * Cliente: CSJ
 * Copyright 2018 by Ing. Carlos Cañizares
 * All rights reserved
 */

package co.IngCarlos.gastosQX.mvc.dao;



import co.IngCarlos.gastosQX.common.util.AsignaAtributoStatement;
import co.IngCarlos.gastosQX.common.util.LoggerMessage;
import co.IngCarlos.gastosQX.mvc.dto.FuncionalidadDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author carlos
 */
public class FuncionalidadDAO {
    
    /**
     *
     * @param conexion
     * @param idMenu
     * @param idUsuario
     * @return
     */
    public ArrayList<FuncionalidadDTO> listarFuncionalidadesPorMenu(Connection conexion, String idMenu, String idUsuario) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<FuncionalidadDTO> listado = null;
        FuncionalidadDTO datos = null;
        StringBuilder cadSQL = null;

        try {
            cadSQL = new StringBuilder();
            cadSQL.append(" SELECT fun.func_id, fun.func_pagina, fun.func_titulo");
            cadSQL.append(" FROM funcionalidad fun");
            cadSQL.append(" INNER JOIN tipousuario_funcionalidad tipfun on tipfun.func_id = fun.func_id");
            cadSQL.append(" WHERE fun.menu_id = ? and tipfun.tius_id = ?");
            cadSQL.append(" ORDER BY tipfun.func_id asc");
            ps = conexion.prepareStatement(cadSQL.toString());
            AsignaAtributoStatement.setString(1, idMenu, ps);
            AsignaAtributoStatement.setString(2, idUsuario, ps);
            rs = ps.executeQuery();
            listado = new ArrayList();
            while (rs.next()) {
                datos = new FuncionalidadDTO();
                datos.setId(rs.getString("func_id"));
                datos.setPagina(rs.getString("func_pagina"));
                datos.setTitulo(rs.getString("func_titulo"));
                listado.add(datos);
            }
            ps.close();
            ps = null;
        } catch (Exception e) {
            LoggerMessage.getInstancia().loggerMessageException(e);
            return null;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                    ps = null;
                }
                if (listado != null && listado.isEmpty()) {
                    listado = null;
                }
            } catch (Exception e) {
                LoggerMessage.getInstancia().loggerMessageException(e);
                return null;
            }
        }
        return listado;
    }
    
}
