package edu.escuelaing.arep.app;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.IOException;
import java.util.ArrayList;

public class AppTest extends TestCase {
    public AppTest(String testName){
        super(testName);
    }

    public static Test suite(){
        return new TestSuite(AppTest.class);
    }

    /**
     * Prueba el funcionamiento del cache, que una vez se consulta una película no se deba volver a preguntar a la API
     * @throws IOException
     * @throws InterruptedException
     */
    public void testCache() throws IOException, InterruptedException {
        ArrayList<HilosTest> ThreadList = new ArrayList<>();
        HilosTest hilo1 = new HilosTest("http://www.omdbapi.com/?t=Avengers&apikey=18afbfbc", "Avengers");
        ThreadList.add(hilo1);
        HilosTest hilo2 = new HilosTest("http://www.omdbapi.com/?t=Avengers&apikey=18afbfbc", "Avengers");
        ThreadList.add(hilo2);
        HilosTest hilo3 = new HilosTest("http://www.omdbapi.com/?t=Jhon&apikey=18afbfbc", "Jhon");
        ThreadList.add(hilo3);
        HilosTest hilo4 = new HilosTest("http://www.omdbapi.com/?t=alo&apikey=18afbfbc", "alo");
        ThreadList.add(hilo4);
        HilosTest hilo5 = new HilosTest("http://www.omdbapi.com/?t=Avengers&apikey=18afbfbc", "Avengers");
        ThreadList.add(hilo5);
        for(HilosTest Thread : ThreadList){
            Thread.start();
        }
        for(HilosTest Thread : ThreadList){
            Thread.join();
        }
        int answer = Cache.getNumKeys();
        assertEquals(3, answer);
    }

    /**
     * Prueba la respuesta de la API y que esta sea acorde a los datos esperados
     * @throws InterruptedException
     */
    public void testAPIResponse() throws InterruptedException {
        Cache.clean();
        ArrayList<HilosTest> ThreadList = new ArrayList<>();
        HilosTest hilo1 = new HilosTest("http://www.omdbapi.com/?t=Avengers&apikey=18afbfbc", "Avengers");
        ThreadList.add(hilo1);
        HilosTest hilo2 = new HilosTest("http://www.omdbapi.com/?t=Jhon&apikey=18afbfbc", "Jhon");
        ThreadList.add(hilo2);
        HilosTest hilo3 = new HilosTest("http://www.omdbapi.com/?t=alo&apikey=18afbfbc", "alo");
        ThreadList.add(hilo3);

        String answer1 = "{\"Title\":\"The Avengers\",\"Year\":\"2012\",\"Rated\":\"PG-13\",\"Released\":\"04 May 2012\",\"Runtime\":\"143 min\",\"Genre\":\"Action, Sci-Fi\",\"Director\":\"Joss Whedon\",\"Writer\":\"Joss Whedon, Zak Penn\",\"Actors\":\"Robert Downey Jr., Chris Evans, Scarlett Johansson\",\"Plot\":\"Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop the mischievous Loki and his alien army from enslaving humanity.\",\"Language\":\"English, Russian, Hindi\",\"Country\":\"United States\",\"Awards\":\"Nominated for 1 Oscar. 38 wins & 80 nominations total\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BNDYxNjQyMjAtNTdiOS00NGYwLWFmNTAtNThmYjU5ZGI2YTI1XkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SX300.jpg\",\"Ratings\":[{\"Source\":\"Internet Movie Database\",\"Value\":\"8.0/10\"},{\"Source\":\"Rotten Tomatoes\",\"Value\":\"91%\"},{\"Source\":\"Metacritic\",\"Value\":\"69/100\"}],\"Metascore\":\"69\",\"imdbRating\":\"8.0\",\"imdbVotes\":\"1,397,515\",\"imdbID\":\"tt0848228\",\"Type\":\"movie\",\"DVD\":\"25 Sep 2012\",\"BoxOffice\":\"$623,357,910\",\"Production\":\"N/A\",\"Website\":\"N/A\",\"Response\":\"True\"}";
        String answer2 = "{\"Title\":\"Jhon en Martian\",\"Year\":\"2019\",\"Rated\":\"N/A\",\"Released\":\"22 Jun 2019\",\"Runtime\":\"N/A\",\"Genre\":\"Comedy, Romance, Sci-Fi\",\"Director\":\"N/A\",\"Writer\":\"Joma Labayen\",\"Actors\":\"Pepe Herrera, Arci Muñoz, Rufa Mae Quinto\",\"Plot\":\"Jhon (Pepe Herrera), the human representation of bad luck, is sent by his narcissistic boss Nestor on his last delivery errand before flying to Malaysia. Jhon is resetting his life; he wants to start anew abroad. But as luck would...\",\"Language\":\"Tagalog, Filipino\",\"Country\":\"Philippines\",\"Awards\":\"N/A\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BYTI3YTdlZjUtMjcyZi00ODYyLWE2MWEtYzM0MDI5N2UxMTJiXkEyXkFqcGdeQXVyNTI5NjIyMw@@._V1_SX300.jpg\",\"Ratings\":[],\"Metascore\":\"N/A\",\"imdbRating\":\"N/A\",\"imdbVotes\":\"N/A\",\"imdbID\":\"tt10161712\",\"Type\":\"series\",\"totalSeasons\":\"1\",\"Response\":\"True\"}";
        String answer3 = "{\"Title\":\"Alo\",\"Year\":\"2003\",\"Rated\":\"N/A\",\"Released\":\"28 Nov 2003\",\"Runtime\":\"N/A\",\"Genre\":\"Family\",\"Director\":\"Tarun Majumdar\",\"Writer\":\"Bibhutibhushan Bandyopadhyay, Tarun Majumdar\",\"Actors\":\"Rituparna Sengupta, Kunal Mitra, Roy Angana\",\"Plot\":\"The true love between a couple living in a village.\",\"Language\":\"Bengali\",\"Country\":\"India\",\"Awards\":\"N/A\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BNGJhZDA3MTQtZWFhNy00YzUyLTkyYTYtN2RiNzBkMDEyNDc3XkEyXkFqcGdeQXVyNjA3OTI5MjA@._V1_SX300.jpg\",\"Ratings\":[{\"Source\":\"Internet Movie Database\",\"Value\":\"8.1/10\"}],\"Metascore\":\"N/A\",\"imdbRating\":\"8.1\",\"imdbVotes\":\"151\",\"imdbID\":\"tt0390831\",\"Type\":\"movie\",\"DVD\":\"N/A\",\"BoxOffice\":\"N/A\",\"Production\":\"N/A\",\"Website\":\"N/A\",\"Response\":\"True\"}";

        for(HilosTest Thread : ThreadList){
            Thread.start();
        }
        for(HilosTest Thread : ThreadList){
            Thread.join();
        }

        String answer = hilo1.getAnswer();
        assertEquals(answer1, answer);
        answer = hilo2.getAnswer();
        assertEquals(answer2, answer);
        answer = hilo3.getAnswer();
        assertEquals(answer3, answer);
    }
}
