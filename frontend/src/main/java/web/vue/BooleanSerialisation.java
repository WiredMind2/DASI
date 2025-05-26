/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.vue;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author aberton1
 */
public class BooleanSerialisation extends Serialisation {

    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if ((boolean) request.getAttribute("result")) {
            response.setContentType("text/plain;charset=UTF-8");

            PrintWriter out = response.getWriter();
            out.println("ok");
            out.close();

        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }

    }

}
