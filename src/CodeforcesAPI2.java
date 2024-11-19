import java.net.*;
import java.io.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class CodeforcesAPI2{
    public static void main(String[] args) {
        // Handle del usuario que deseas consultar
        String targetHandle = "ignacio_benq";

        try {
            // URL de la API para obtener la información del usuario
            String urlString = "https://codeforces.com/api/user.info?handles=" + targetHandle;
            URL url = new URL(urlString);

            // Abrir conexión HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Obtener la respuesta de la API
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parsear la respuesta JSON
            JSONObject jsonResponse = new JSONObject(response.toString());

            // Verificar si la respuesta es exitosa
            if (jsonResponse.getString("status").equals("OK")) {
                // Obtener el array de usuarios (en este caso solo uno)
                JSONArray users = jsonResponse.getJSONArray("result");
                JSONObject user = users.getJSONObject(0);

                // Obtener información del usuario
                String handle = user.getString("handle");
                int rating = user.has("rating") ? user.getInt("rating") : 0;
                int maxRating = user.has("maxRating") ? user.getInt("maxRating") : 0;
                String rank = user.has("rank") ? user.getString("rank") : "N/A";
                String maxRank = user.has("maxRank") ? user.getString("maxRank") : "N/A";
                int contribution = user.has("contribution") ? user.getInt("contribution") : 0;
                int friendOfCount = user.has("friendOfCount") ? user.getInt("friendOfCount") : 0;

                // Mostrar la información del usuario
                System.out.println("Informacion del usuario: " + handle);
                System.out.println("Calificacion actual: " + rating);
                System.out.println("Maxima calificacion: " + maxRating);
                System.out.println("Rango actual: " + rank);
                System.out.println("Maximo rango: " + maxRank);
                System.out.println("Contribucion: " + contribution);
                System.out.println("Número de amigos: " + friendOfCount);
            } else {
                System.out.println("Error: " + jsonResponse.getString("comment"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
