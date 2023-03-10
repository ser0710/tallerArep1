package edu.escuelaing.arep.app;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class HttpServer {

    /**
     * Método principal, inicia un socket
     * recibe la petición get y agrega el nombre a de la
     * película seleccionada a la URL de la API
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        JSONArray JSON = null;
        String urlToUse1 = "http://www.omdbapi.com/?t=";
        String urlToUse2 = "&apikey=18afbfbc";
        String urlFinal = null;
        String response = "[";
        String table;
        String name = null;

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        boolean running = true;
        while(running) {
            response = "[";
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            String inputLine, outputLine;
            while ((inputLine = in.readLine()) != null) {
                if(inputLine.startsWith("G") && inputLine.contains("?")){
                    String[] names = inputLine.split(" ");
                    name = names[1];
                    names = name.split("=");
                    name = names[1];
                    urlFinal = urlToUse1 + name + urlToUse2;
                }
                System.out.println("Received: " + inputLine);
                if (!in.ready()) {
                    break;
                }
            }
            if(urlFinal != null){
//                response += HttpConnectionExample.answer(urlFinal);
                response += Cache.cache(urlFinal, name);
                response += "]";
                table = table(response);

                outputLine = "HTTP/1.1 200 \r\n" +
                        "Content-Type: text/html \r\n" +
                        "\r\n" +
                        "<table border=\" 1\"\n" + table;
            }else{
                outputLine = "HTTP/1.1 200 \r\n" +
                        "Content-Type: text/html \r\n" +
                        "\r\n" + htmlWithForms();
            }
            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }

    /**
     * Método que genera una tabla que contiene la información
     * de la película, ordenada alfabéticamente
     * @param jsonA json que contiene la información de la película
     * @return
     */
    private static String table(String jsonA){
        ArrayList<String> keys = new ArrayList<String>();
        String table = "<tr> \n";
        String value = null;
        JSONArray json = new JSONArray(jsonA);
        JSONObject obj = json.getJSONObject(0);
        for(String key : obj.keySet()){
            keys.add(key);
        }
        Collections.sort(keys);
        for(String key : keys){
            value = obj.get(key).toString();
            table += "<td>" + key + "</td>\n";
            table += "<td>" + value + "</td>\n";
            table += "<tr> \n";
        }
        return table;
    }

    /**
     * Método que genera una pagina html simple
     * @return String con el código del html
     */
    public static String htmlSimple(){
        return "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<title>Title of the document</title>\n"
                + "</head>"
                + "<body>"
                + "My Web Site"
                + "</body>"
                + "</html>";
    }

    /**
     * Método que genera una pagina con posibilidad de introducir texto
     * @return String con el código del html
     */
    public static String htmlWithForms(){
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <title>Form Example</title>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <h1>Form with GET</h1>\n" +
                "        <form action=\"/hello\">\n" +
                "            <label for=\"name\">Name:</label><br>\n" +
                "            <input type=\"text\" id=\"name\" name=\"name\"><br><br>\n" +
                "            <input type=\"button\" value=\"Submit\" onclick=\"loadGetMsg()\">\n" +
                "        </form> \n" +
                "        <div id=\"getrespmsg\"></div>\n" +
                "\n" +
                "        <script>\n" +
                "            function loadGetMsg() {\n" +
                "                let nameVar = document.getElementById(\"name\").value;\n" +
                "                const xhttp = new XMLHttpRequest();\n" +
                "                xhttp.onload = function() {\n" +
                "                    document.getElementById(\"getrespmsg\").innerHTML =\n" +
                "                    this.responseText;\n" +
                "                }\n" +
                "                xhttp.open(\"GET\", \"/hello?name=\"+nameVar);\n" +
                "                xhttp.send();\n" +
                "            }\n" +
                "        </script>\n" +
                "\n" +
                "    </body>\n" +
                "</html>";
    }
}
