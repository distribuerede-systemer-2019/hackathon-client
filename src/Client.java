import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Client {

    //configure your client here
    private static final String BASE = "http://localhost:8080/";

    public static void main(String[] args) {
        clearConsole();
        System.out.println("Main menu:");
        System.out.println("1. Get all customers");
        System.out.println("2. Get customer");
        System.out.println("3. ...");
        System.out.println("4. ...");
        System.out.println("n. Create all necessary options");
        System.out.println("");
        System.out.print("Please select an option from 1-n\r\n");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            //TODO Implement all methods implemented on the server.
            switch (Integer.parseInt(br.readLine())) {
                case 1:
                    getAllCustomers();
                    returnToMainMenu();
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    System.exit(1);
                    break;
                default:
                    System.out.println("invalid selection. Returning to main menu\n");
                    main(null);
            }
        } catch (IOException ioe) {
            System.out.println("IO error trying to read your input!\r\n");
            main(null);
        }
    }

    public static void getAllCustomers() {
        String url = BASE+"customers";
        String response = urlRequest(url, "GET");
        JsonArray customers = new JsonParser().parse(response).getAsJsonArray();
        for(JsonElement c : customers) {
            //This can be formatted for a prettier print solution
            //Maybe you want to use the same Customer-model as you have on your server,
            //and thus you will be able to use the fromJson method.
            System.out.println(c);
        }

    }

    public static String urlRequest(String url, String request) {
        String responseString = null;
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod(request);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();
            responseString = response.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return responseString;
    }

    public static void returnToMainMenu() {
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(System.in)
            );
            System.out.println("Press any key to return to main menu");
            br.readLine();
            main(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void clearConsole() {
        System.out.println(new String(new char[50]).replace("\0", "\r\n")); //work around to clear console
    }
}
