package edu.escuelaing.arep.app;

import java.io.IOException;
import java.util.HashMap;

public class Cache {
    private static HashMap<String, String> cache = new HashMap<>();

    /**
     * Método que almacena una consulta a la API en caso
     * de que no se encuentre almacenado en el HashMap,
     * en caso dado que se encuentre consulta sobre este
     * @param url url de la API de la cual se va a consultar la información de las películas
     * @param name Nombre de la película buscada
     * @return Datos consultados de la película
     * @throws IOException
     */
    public static String cache(String url, String name) throws IOException {
        String valor = "";
        if(!cache.containsKey(name)){
            valor = HttpConnectionExample.answer(url);
            cache.put(name, valor);
        }else {
            valor = cache.get(name);
        }
//        System.out.println(cache.keySet());
        return valor;
    }
}
