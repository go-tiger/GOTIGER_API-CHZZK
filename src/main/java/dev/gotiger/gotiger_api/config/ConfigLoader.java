package dev.gotiger.gotiger_api.config;

import org.json.JSONObject;
import java.io.*;

public class ConfigLoader {
    private final File dataFolder;
    private String dbTable;
    private String clientId;
    private String clientSecret;
    private int port;
    private boolean https;
    private String displayHost;

    private String callbackPath;

    public ConfigLoader(File dataFolder) {
        this.dataFolder = dataFolder;
    }

    public void load() {
        File configFile = new File(dataFolder, "config.json");
        if (!configFile.exists()) {
            dbTable = "DEFAULT_TABLE";
            clientId = "";
            clientSecret = "";
            port = 20153;
            https = false;
            displayHost = "localhost";
            callbackPath = "/callback";
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) sb.append(line);

            JSONObject json = new JSONObject(sb.toString());
            dbTable = json.optString("table", "DEFAULT_TABLE");
            clientId = json.optString("CLIENT_ID", "");
            clientSecret = json.optString("CLIENT_SECRET", "");
            port = json.optInt("port", 20153);
            https = json.optBoolean("https", false);
            displayHost = json.optString("displayHost", "localhost");
            callbackPath = json.optString("callbackPath","/callback");

        } catch (Exception e) {
            e.printStackTrace();
            dbTable = "DEFAULT_TABLE";
            clientId = "";
            clientSecret = "";
            port = 20153;
            https = false;
            displayHost = "localhost";
            callbackPath = "/callback";
        }
    }

    public String getDbTable() { return dbTable; }
    public String getClientId() { return clientId; }
    public String getClientSecret() { return clientSecret; }
    public int getPort() { return port; }
    public boolean isHttps() { return https; }
    public String getDisplayHost() { return displayHost; }

    public String getCallbackPath() {return callbackPath; }
}
