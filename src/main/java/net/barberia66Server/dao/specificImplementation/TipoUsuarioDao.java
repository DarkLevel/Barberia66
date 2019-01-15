/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.barberia66Server.dao.specificImplementation;

import java.sql.Connection;
import net.barberia66Server.dao.genericImplementation.GenericDaoImplementation;
import net.barberia66Server.dao.publicInterface.DaoInterface;

/**
 *
 * @author a073597589g
 */
public class TipoUsuarioDao extends GenericDaoImplementation implements DaoInterface {
    
    public TipoUsuarioDao(Connection oConnection, String ob) {
        super(oConnection, ob);
    }
    
}
