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
import co.IngCarlos.gastosQX.mvc.dto.MenuDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author carlos
 */
public class MenuDAO {
    
    /**
     *
     * @param conexion
     * @param idUsuario
     * @return
     */
    public ArrayList<MenuDTO> listarMenusPorUsuario(Connection conexion, String idUsuario) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<MenuDTO> listado = null;
        MenuDTO datosMenu = null;
        StringBuilder cadSQL = null;
        try {
            System.out.print("llega a dao consultar menu por usuario");
            cadSQL = new StringBuilder();
            cadSQL.append(" SELECT DISTINCT func.menu_id, menu.menu_titulo, menu.menu_icono ");
            cadSQL.append(" FROM  tipousuario_funcionalidad tifu ");
            cadSQL.append(" INNER JOIN funcionalidad func ON func.func_id = tifu.func_id ");
            cadSQL.append(" INNER JOIN menu menu ON menu.menu_id = func.menu_id ");
            cadSQL.append(" WHERE tifu.tius_id = ? ");
            ps = conexion.prepareStatement(cadSQL.toString());
            AsignaAtributoStatement.setString(1, idUsuario, ps);
            rs = ps.executeQuery();
            listado = new ArrayList();
            while (rs.next()) {
                datosMenu = new MenuDTO();
                datosMenu.setId(rs.getString("menu_id"));
                datosMenu.setTituloMenu(rs.getString("menu_titulo"));
                datosMenu.setIconoMenu(rs.getString("menu_icono"));
                System.out.print("datosMenu" + datosMenu.toStringJson());
                listado.add(datosMenu);
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
