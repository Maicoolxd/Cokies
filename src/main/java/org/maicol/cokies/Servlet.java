package org.maicol.cokies;

/*Nombre del programador: Michael Guaman
Materia: Lenguajes de Programacion 2
Fecha: 20/05/2024
Detalle:Uso de cookies
Version:1.1.0*/

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/Servlet")
public class Servlet extends HttpServlet {

        @Override
        public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

            boolean nuevoUsuario = true;
            int contadorVisitas = 0;

            // Obtenemos las cookies
            Cookie[] cookies = req.getCookies();

            // Verificar si las cookies ya existen
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("VisitanteRecurrente") && cookie.getValue().equals("si")) {
                        nuevoUsuario = false;
                    }
                    if (cookie.getName().equals("contadorVisitas")) {
                        contadorVisitas = Integer.parseInt(cookie.getValue());
                    }
                }
            }

            // Si el usuario es nuevo inicia en 1 para contar su vistia
            if (nuevoUsuario) {
                contadorVisitas = 1;
                Cookie visitante = new Cookie("VisitanteRecurrente", "si");
                res.addCookie(visitante);
            } else {
                // Si no es un nuevo usuario, incrementamos el contador
                contadorVisitas++;
            }

            // Actualiza las visitas mediante un contador
            Cookie contadorCookie = new Cookie("contadorVisitas", Integer.toString(contadorVisitas));
            res.addCookie(contadorCookie);

            // Generar el mensaje para el usuario
            String mensaje;
            if (nuevoUsuario) {
                mensaje = "Es la primera vez que ingresa";
            } else {
                mensaje = "Ha ingresado al sitio " + contadorVisitas + " veces";
            }

            res.setContentType("text/html;charset=UTF-8");

            // Enviar la respuesta al cliente utilizando html
            PrintWriter out = res.getWriter();
            out.print("<html><body>");
            out.print("<h1>" + mensaje + "</h1>");
            out.print("</body></html>");
        }
    }

