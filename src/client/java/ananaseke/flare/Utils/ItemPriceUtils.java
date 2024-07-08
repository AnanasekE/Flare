package ananaseke.flare.Utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ItemPriceUtils {
    private static final String BAZAAR_URL = APIUtils.API_KEY; // Replace with your actual URL
    private static final Identifier BAZAAR_FILE_IDENTIFIER = Identifier.of("flare", "bazaar-data.json");

    // Item check for AH or Bazaar
    // Item price based on AH or Bazaar
    // Update item prices - every 5 min or something

    public static void updateBazaar() {
        try {
            InputStream bazaarData = MinecraftClient.getInstance().getResourceManager().getResource(BAZAAR_FILE_IDENTIFIER).get().getInputStream();
            saveJsonToFile(BAZAAR_URL, bazaarData);
        } catch (IOException e) {
            System.err.println("Failed to get bazaar data: " + e.getMessage());
        }
    }

    public static void updateAH() {
        // Implement the logic for updating AH prices
    }

    public static Float getBazaarItemPrice(String internalName) {
        try {
            InputStream bazaarData = MinecraftClient.getInstance().getResourceManager().getResource(BAZAAR_FILE_IDENTIFIER).get().getInputStream();
            JsonElement jsonElement = JsonParser.parseReader(new InputStreamReader(bazaarData));
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            if (jsonObject.has("products") && jsonObject.get("products").getAsJsonObject().has(internalName)) {
                JsonObject productObject = jsonObject.get("products").getAsJsonObject().get(internalName).getAsJsonObject();
                if (productObject.has("sell_summary")) {
                    float pricePerUnit = productObject.get("sell_summary").getAsJsonArray().get(0).getAsJsonObject().get("pricePerUnit").getAsFloat();
                    return pricePerUnit;
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to get bazaar data: " + e.getMessage());
        }

        return null;
    }

    public static Float getAHItemPrice(String internalName) {
        // Implement the logic to get the AH item price
        return 1.0F;
    }

    public static void saveJsonToFile(String urlString, InputStream inputStream) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + responseCode);
            }

            // Specify the path to the file where you want to save the JSON data
            String filePath = "src/main/resources/bazaar-data.json";
            try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, bytesRead);
                }
            }

        } catch (MalformedURLException e) {
            System.err.println("Malformed URL: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    // example bazaar-data.json
//    {
//        "success": true,
//            "lastUpdated": 1590854517479,
//            "products": {
//        "INK_SACK:3": {
//            "product_id": "INK_SACK:3",
//                    "sell_summary": [
//            {
//                "amount": 20569,
//                    "pricePerUnit": 4.2,
//                    "orders": 1
//            },
//            {
//                "amount": 140326,
//                    "pricePerUnit": 3.8,
//                    "orders": 2
//            }
//      ],
//            "buy_summary": [
//            {
//                "amount": 640,
//                    "pricePerUnit": 4.8,
//                    "orders": 1
//            },
//            {
//                "amount": 640,
//                    "pricePerUnit": 4.9,
//                    "orders": 1
//            },
//            {
//                "amount": 25957,
//                    "pricePerUnit": 5,
//                    "orders": 3
//            }
//      ],
//            "quick_status": {
//                "productId": "INK_SACK:3",
//                        "sellPrice": 4.2,
//                        "sellVolume": 409855,
//                        "sellMovingWeek": 8301075,
//                        "sellOrders": 11,
//                        "buyPrice": 4.99260315136572,
//                        "buyVolume": 1254854,
//                        "buyMovingWeek": 5830656,
//                        "buyOrders": 85
//            }
//        }
//    }
//    }

}
