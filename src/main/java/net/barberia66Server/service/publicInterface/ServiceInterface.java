/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.barberia66Server.service.publicInterface;

import net.barberia66Server.bean.specificImplementation.ReplyBean;

/**
 *
 * @author a073597589g
 */
public interface ServiceInterface {

    public ReplyBean get() throws Exception;

    public ReplyBean remove() throws Exception;

    public ReplyBean getcount() throws Exception;

    public ReplyBean create() throws Exception;

    public ReplyBean update() throws Exception;

    public ReplyBean getpage() throws Exception;
}
