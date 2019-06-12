/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.barberia66Server.service.specificImplementation;

import com.google.gson.Gson;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.barberia66Server.bean.specificImplementation.ReplyBean;
import net.barberia66Server.service.genericImplementation.GenericServiceImplementation;
import net.barberia66Server.service.publicInterface.ServiceInterface;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author a073597589g
 */
public class ProductoService extends GenericServiceImplementation implements ServiceInterface {
    
    public ProductoService(HttpServletRequest oRequest) {
        super(oRequest);
        ob = oRequest.getParameter("ob");
    }
    
    public ReplyBean addimage() throws Exception {
        String name = "";
        ReplyBean oReplyBean;
        Gson oGson = new Gson();

        HashMap<String, String> hash = new HashMap<>();

        if (ServletFileUpload.isMultipartContent(oRequest)) {
            try {
                List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(oRequest);
                for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                        name = new File(item.getName()).getName();
                        //Creo la carpeta con la ID de usuario
                        (new File(".//..//webapps//imagenesbarberia//")).mkdirs();
                        item.write(new File(".//..//webapps//imagenesbarberia//" + name));
                    } else {
                        hash.put(item.getFieldName(), item.getString());
                    }
                }
                oReplyBean = new ReplyBean(200, oGson.toJson("File upload: " + name));
            } catch (Exception ex) {
                oReplyBean = new ReplyBean(500, oGson.toJson("Error while uploading file: " + name));
            }
        } else {
            oReplyBean = new ReplyBean(500, oGson.toJson("Can't read image"));
        }
        return oReplyBean;
    }

}
