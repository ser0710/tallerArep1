package edu.escuelaing.arep.app;

import java.net.*;
import java.io.*;
public class HttpServer {
    public static void main(String[] args) throws IOException {
        String urlToUse1 = "http://www.omdbapi.com/?t=";
        String urlToUse2 = "&apikey=18afbfbc";
        String urlFinal = null;
        URL urlToUse = null;
        StringBuffer response = null;

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        boolean running = true;
        while(running) {
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
                    String name = names[1];
                    names = name.split("=");
                    name = names[1];
                    urlFinal = urlToUse1 + name + urlToUse2;
                    urlToUse = new URL(urlFinal);

                    HttpURLConnection con = (HttpURLConnection) urlToUse.openConnection();
                    con.setRequestMethod("GET");
                    con.setRequestProperty("User-Agent", "application/json");
                    BufferedReader consulta = new BufferedReader(new InputStreamReader(
                            con.getInputStream()));
                    String inputConsulta;
                    response = new StringBuffer();
                    while ((inputConsulta = consulta.readLine()) != null) {
                        response.append(inputConsulta);
                    }
                    consulta.close();
                }
                System.out.println("Received: " + inputLine);
                if (!in.ready()) {
                    break;
                }
            }
            outputLine = "HTTP/1.1 200 \r\n" +
                    "Content-Type: text/html \r\n" +
                    "\r\n" +
                    htmlWithForms(response);

            out.println(outputLine);

            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }



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

    public static String htmlWithForms(StringBuffer response){
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
                "        <div id=\"getrespmsg\"></div>\n" + response +
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
