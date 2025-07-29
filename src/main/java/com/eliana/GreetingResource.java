package com.eliana;

import java.util.ArrayList;
import java.util.List;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/hello")
public class GreetingResource {

    private static final List<Person> people = new ArrayList<>();

    // Clase interna simple para persona
    public static class Person {
        public String name;
        public String email;
        public Person(String name, String email) {
            this.name = name;
            this.email = email;
        }
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String showForm() {
        return buildHtml(null);
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    public Response submitForm(@FormParam("name") String name, @FormParam("email") String email) {
        if (name != null && !name.isEmpty() && email != null && !email.isEmpty()) {
            people.add(new Person(name, email));
        }
        // Mostrar la página con los datos nuevos
        String html = buildHtml("¡Persona guardada correctamente!");
        return Response.ok(html).build();
    }

    private String buildHtml(String message) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><head><title>CRUD Simple</title>");
        sb.append("<style>");
        sb.append("body { font-family: Arial, sans-serif; background: #f9f9f9; margin: 20px; }");
        sb.append("h1, h2 { color: #333; }");
        sb.append("form { background: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); max-width: 400px; }");
        sb.append("input[type=text], input[type=email] { width: 100%; padding: 8px; margin: 8px 0 15px 0; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }");
        sb.append("button { background-color: #4CAF50; color: white; padding: 10px 18px; border: none; border-radius: 4px; cursor: pointer; font-size: 16px; }");
        sb.append("button:hover { background-color: #45a049; }");
        sb.append("ul { list-style-type: none; padding-left: 0; max-width: 400px; }");
        sb.append("li { background: #fff; margin-bottom: 8px; padding: 10px 15px; border-radius: 4px; box-shadow: 0 0 5px rgba(0,0,0,0.05); }");
        sb.append(".message { color: green; margin-bottom: 15px; font-weight: bold; }");
        sb.append("</style>");
        sb.append("</head><body>");

        sb.append("<h1>Ingreso de Nombre y Correo</h1>");
        if (message != null) {
            sb.append("<div class='message'>").append(message).append("</div>");
        }
        sb.append("<form method='POST' action='/hello'>");
        sb.append("<label for='name'>Nombre:</label><br/>");
        sb.append("<input type='text' id='name' name='name' required/><br/>");
        sb.append("<label for='email'>Correo:</label><br/>");
        sb.append("<input type='email' id='email' name='email' required/><br/>");
        sb.append("<button type='submit'>Guardar</button>");
        sb.append("</form><hr/>");

        sb.append("<h2>Personas guardadas:</h2>");
        sb.append("<ul>");
        for (Person p : people) {
            sb.append("<li>").append(p.name).append(" - ").append(p.email).append("</li>");
        }
        sb.append("</ul>");

        sb.append("</body></html>");
        return sb.toString();
    }
}
