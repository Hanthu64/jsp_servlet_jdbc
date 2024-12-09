package org.iesvdm.jsp_servlet_jdbc.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.iesvdm.jsp_servlet_jdbc.dao.SocioDAO;
import org.iesvdm.jsp_servlet_jdbc.dao.SocioDAOImpl;
import org.iesvdm.jsp_servlet_jdbc.model.Socio;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "EditarSociosServlet", value = "/EditarSociosServlet")
public class EditarSociosServlet extends HttpServlet {
    private SocioDAO socioDAO = new SocioDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String codigoStr = request.getParameter("codigo");//Conseguimos el código
        Integer codigo = null;

        //Y lo parseamos a Integer con un try catch para controlar errores
        try{
            codigo = Integer.parseInt(codigoStr);
        }catch (NumberFormatException nfe){
            nfe.printStackTrace();
        }

        //Si el codigo no es null...
        if(codigo != null){
            Optional<Socio> auxSocio = socioDAO.find(codigo); //Nuestro socio ahora mismo es una variable Optional, la cual retiramos usando el código.
            if (auxSocio.isPresent()) {//Si hay algo dentro de la variable opcional...
                Socio socio = auxSocio.get();//Pasamos nuestra variable Optional a una de socio, por lo que ya podemos trabajar con ella
                request.setAttribute("socio", socio);//Añadimos el atributo
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/formularioEditarSocio.jsp");//Y lo mandamos al formulario
                dispatcher.forward(request, response);
            } else {//Si no hay nada en auxSocio, lo controlamos con un error.
                response.sendRedirect("ListarSociosServlet?err-cod=2");
            }
        }else{//Si el código es null, lo controlamos con un error
            response.sendRedirect("ListarSociosServlet?err-cod=1");
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        RequestDispatcher dispatcher;
        //He aquí todas las variables extraídas del formulario.
        String nombre = request.getParameter("nombre");
        String estaturaStr = request.getParameter("estatura");
        Integer estatura = null;
        String edadStr = request.getParameter("edad");
        Integer edad = null;
        String localidad = request.getParameter("localidad");

        String codigoStr = request.getParameter("codigo");
        Integer codigo = null;

        try{//Mismo procedimiento que con el código en doGet, solo que ahora añadiendo edad y estatura
            edad = Integer.parseInt(edadStr);
            estatura = Integer.parseInt(estaturaStr);
            codigo = Integer.parseInt(codigoStr);
        }catch (NumberFormatException nfe){
            nfe.printStackTrace();
        }

        if(codigo != null){
            Socio socio = new Socio(codigo, nombre, estatura, edad, localidad);//Creamos una clase Socio nueva con lo recibido del formulario
            //LÍNEA DE CÓDIGO UTILIZADA PARA COMPROBAR QUE NUESTRO SOCIO LLEGA BIEN ABAJO
            //System.out.println(socio.getSocioId() + socio.getNombre() + socio.getEstatura() + socio.getEdad() + socio.getLocalidad());
            socioDAO.update(socio);//La pasamos a la función update y dejamos que haga su magia
            List<Socio> listado = this.socioDAO.getAll();
            request.setAttribute("listado", listado);//Actualizamos la lista
            dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/listadoSociosB.jsp");
            dispatcher.forward(request, response);
        }else{//Controlamos que el código no sea null
            response.sendRedirect("ListarSociosServlet?err-cod=1");
        }
    }

}
