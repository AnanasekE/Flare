package ananaseke.flare.Utils;

import ananaseke.flare.FlareClient;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class ItemPriceUtils {
    private static final String BAZAAR_URL = "https://api.hypixel.net/v2/skyblock/bazaar?API-Key=" + APIUtils.API_KEY;
    private static final Identifier BAZAAR_FILE_IDENTIFIER = Identifier.of("flare", "bazaar-data.json");
    private static final File BAZAAR_DATA_FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), BAZAAR_FILE_IDENTIFIER.getPath());
    public static final Logger LOGGER = FlareClient.LOGGER;

    // Cache variables
    private static JsonObject cachedJsonObject = null;
    private static long lastUpdateTime = 0;
    private static final long CACHE_REFRESH_INTERVAL_MS = 300000; // 5 minutes in milliseconds

    static {
        // Load initial data into cache
        try {
            cachedJsonObject = getJsonObject();
            lastUpdateTime = System.currentTimeMillis();
        } catch (FileNotFoundException e) {
            LOGGER.warn("Failed to load initial JSON data: " + e.getMessage());
        }

        // Start a timer to refresh cache periodically
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    cachedJsonObject = getUpdatedBazaarData();
                    lastUpdateTime = System.currentTimeMillis();
                    saveDataToFile(cachedJsonObject);
                } catch (Exception e) {
                    LOGGER.warn("Failed to refresh Bazaar data: " + e.getMessage());
                }
            }
        }, CACHE_REFRESH_INTERVAL_MS, CACHE_REFRESH_INTERVAL_MS);
    }

    public static void updateBazaar() {
        try {
            cachedJsonObject = getUpdatedBazaarData();
            lastUpdateTime = System.currentTimeMillis();
            saveDataToFile(cachedJsonObject);
        } catch (Exception e) {
            LOGGER.warn("Failed to update Bazaar data: " + e.getMessage());
        }
    }

    public static void updateAH() {
        // Implement the logic for updating AH prices
    }

    public static Optional<Float> getBazaarItemBuyPrice(String internalName) {
        try {
            JsonObject jsonObject = getCachedJsonObject();

            if (jsonObject == null) return Optional.empty();

            // Check if the JSON contains necessary structures
            if (!jsonObject.has("products")) {
//                System.err.println("Invalid JSON format: Missing 'products' field.");
                return Optional.empty();
            }

            JsonObject products = jsonObject.getAsJsonObject("products");

            // Check if the product exists in the JSON data
            if (!products.has(internalName)) {
//                System.err.println("Product '" + internalName + "' not found in JSON data.");
                return Optional.empty();
            }

            JsonObject product = products.getAsJsonObject(internalName);

            // Check if the product has sell_summary with pricePerUnit
            if (!product.has("sell_summary")) {
//                System.err.println("Product '" + internalName + "' does not have 'sell_summary' field.");
                return Optional.empty();
            }

            // Get the first sell summary (assuming there's at least one)
            if (product.getAsJsonArray("sell_summary").size() == 0) {
                return Optional.empty();
            }
            JsonElement sellSummary = product.getAsJsonArray("sell_summary").get(0);
            float pricePerUnit = sellSummary.getAsJsonObject().get("pricePerUnit").getAsFloat();

            return Optional.of(pricePerUnit);

        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static Optional<Float> getBazaarItemSellPrice(String internalName) {
        try {
            JsonObject jsonObject = getCachedJsonObject();

            if (jsonObject == null) return Optional.empty();

            // Check if the JSON contains necessary structures
            if (!jsonObject.has("products")) {
//                System.err.println("Invalid JSON format: Missing 'products' field.");
                return Optional.empty();
            }

            JsonObject products = jsonObject.getAsJsonObject("products");

            // Check if the product exists in the JSON data
            if (!products.has(internalName)) {
//                System.err.println("Product '" + internalName + "' not found in JSON data.");
                return Optional.empty();
            }

            JsonObject product = products.getAsJsonObject(internalName);

            // Check if the product has buy_summary with pricePerUnit
            if (!product.has("buy_summary")) {
//                System.err.println("Product '" + internalName + "' does not have 'buy_summary' field.");
                return Optional.empty();
            }

            // Get the first buy summary (assuming there's at least one)
            if (product.getAsJsonArray("buy_summary").size() == 0) {
                return Optional.empty();
            }
            JsonElement buySummary = product.getAsJsonArray("buy_summary").get(0);
            float pricePerUnit = buySummary.getAsJsonObject().get("pricePerUnit").getAsFloat();

            return Optional.of(pricePerUnit);

        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static Optional<Float> getBazaarItemQuickSellPrice(String internalName) {
        try {
            JsonObject jsonObject = getCachedJsonObject();

            if (jsonObject == null) return Optional.empty();

            // Check if the JSON contains necessary structures
            if (!jsonObject.has("products")) {
//                System.err.println("Invalid JSON format: Missing 'products' field.");
                return Optional.empty();
            }

            JsonObject products = jsonObject.getAsJsonObject("products");

            // Check if the product exists in the JSON data
            if (!products.has(internalName)) {
//                System.err.println("Product '" + internalName + "' not found in JSON data.");
                return Optional.empty();
            }

            JsonObject product = products.getAsJsonObject(internalName);

            // Check if the product has quick_status with sellPrice
            if (!product.has("quick_status")) {
//                System.err.println("Product '" + internalName + "' does not have 'quick_status' field.");
                return Optional.empty();
            }

            // Get the sell price from quick_status
            float sellPrice = product.getAsJsonObject("quick_status").get("sellPrice").getAsFloat();

            return Optional.of(sellPrice);

        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static Optional<Float> getBazaarItemQuickBuyPrice(String internalName) {
        try {
            JsonObject jsonObject = getCachedJsonObject();

            if (jsonObject == null) return Optional.empty();
            
            // Check if the JSON contains necessary structures
            if (!jsonObject.has("products")) {
//                System.err.println("Invalid JSON format: Missing 'products' field.");
                return Optional.empty();
            }

            JsonObject products = jsonObject.getAsJsonObject("products");

            // Check if the product exists in the JSON data
            if (!products.has(internalName)) {
//                System.err.println("Product '" + internalName + "' not found in JSON data.");
                return Optional.empty();
            }

            JsonObject product = products.getAsJsonObject(internalName);

            // Check if the product has quick_status with buyPrice
            if (!product.has("quick_status")) {
//                System.err.println("Product '" + internalName + "' does not have 'quick_status' field.");
                return Optional.empty();
            }

            // Get the buy price from quick_status
            float buyPrice = product.getAsJsonObject("quick_status").get("buyPrice").getAsFloat();

            return Optional.of(buyPrice);

        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private static JsonObject getCachedJsonObject() throws IOException {
        long currentTime = System.currentTimeMillis();
        if (cachedJsonObject == null || currentTime - lastUpdateTime > CACHE_REFRESH_INTERVAL_MS) {
            synchronized (ItemPriceUtils.class) {
                if (cachedJsonObject == null || currentTime - lastUpdateTime > CACHE_REFRESH_INTERVAL_MS) {
                    cachedJsonObject = getJsonObject();
                    lastUpdateTime = currentTime;
                }
            }
        }
        return cachedJsonObject;
    }

    private static JsonObject getUpdatedBazaarData() throws IOException {
        JsonElement jsonData = loadDataFromURL(BAZAAR_URL);
        return jsonData.getAsJsonObject();
    }

    private static JsonObject getJsonObject() throws FileNotFoundException {
        JsonElement jsonElement = null;
        try {
            jsonElement = JsonParser.parseReader(new FileReader(BAZAAR_DATA_FILE));
        } catch (JsonSyntaxException exception) {
            LOGGER.warn("Failed to parse JSON data from file: " + exception.getMessage());
        }
        if (jsonElement != null) {
            if (!jsonElement.isJsonObject()) return null;
            return jsonElement.getAsJsonObject();
        }
        return null;
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
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + responseCode);
            }

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }

            String jsonResponse = responseBuilder.toString();
            return JsonParser.parseString(jsonResponse);

        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        } finally {
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
