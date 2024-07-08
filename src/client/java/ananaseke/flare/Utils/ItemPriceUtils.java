package ananaseke.flare.Utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

public class ItemPriceUtils {
    private static final String BAZAAR_URL = "https://api.hypixel.net/v2/skyblock/bazaar?API-Key=" + APIUtils.API_KEY; // Replace with your actual URL
    private static final Identifier BAZAAR_FILE_IDENTIFIER = Identifier.of("flare", "bazaar-data.json");
    private static final File BAZAAR_DATA_FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), BAZAAR_FILE_IDENTIFIER.getPath());

    public static void updateBazaar() {
        JsonElement jsonData = loadDataFromURL(BAZAAR_URL);
        saveDataToFile(jsonData);
    }

    public static void updateAH() {
        // Implement the logic for updating AH prices
    }

    public static Optional<Float> getBazaarItemPrice(String internalName) {
        try {
            // Parse the JSON file
            JsonElement jsonElement = JsonParser.parseReader(new FileReader(BAZAAR_DATA_FILE));
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            // Check if the JSON contains necessary structures
            if (!jsonObject.has("products")) {
                System.err.println("Invalid JSON format: Missing 'products' field.");
                return Optional.empty();
            }

            JsonObject products = jsonObject.getAsJsonObject("products");

            // Check if the product exists in the JSON data
            if (!products.has(internalName)) {
                System.err.println("Product '" + internalName + "' not found in JSON data.");
                return Optional.empty();
            }

            JsonObject product = products.getAsJsonObject(internalName);

            // Check if the product has sell_summary with pricePerUnit
            if (!product.has("sell_summary")) {
                System.err.println("Product '" + internalName + "' does not have 'sell_summary' field.");
                return Optional.empty();
            }

            // Get the first sell summary (assuming there's at least one)
            JsonElement sellSummary = product.getAsJsonArray("sell_summary").get(0);
            float pricePerUnit = sellSummary.getAsJsonObject().get("pricePerUnit").getAsFloat();

            return Optional.of(pricePerUnit);

        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static void saveDataToFile(JsonElement jsonData) {
        if (jsonData == null) {
            System.out.println("No data to save.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BAZAAR_DATA_FILE))) {
            writer.write(jsonData.toString());
            System.out.println("Data saved to " + BAZAAR_DATA_FILE.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JsonElement loadDataFromURL(String urlString) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            // Create a URL object
            URL url = new URL(urlString);

            // Open a connection to the URL
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            // Check the response code
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + responseCode);
            }

            // Read the response
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }

            // Parse the JSON response
            String jsonResponse = responseBuilder.toString();
            return JsonParser.parseString(jsonResponse);

        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        } finally {
            // Close the connections
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
