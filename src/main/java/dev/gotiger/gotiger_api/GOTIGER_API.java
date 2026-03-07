package dev.gotiger.gotiger_api;

import dev.gotiger.gotiger_api.config.ConfigLoader;
import dev.gotiger.gotiger_api.config.CustomChzzkOauthLoginAdapter;
import dev.gotiger.gotiger_api.listener.PlayerListener;
import dev.gotiger.gotiger_api.placeholder.ChzzkPlaceholder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public final class GOTIGER_API extends JavaPlugin {

    private ConfigLoader configLoader;
    private CustomChzzkOauthLoginAdapter sharedAdapter;

    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        File configFile = new File(getDataFolder(), "config.json");
        if (!configFile.exists()) {
            saveResource("config.json", false);
            getLogger().info("📝 기본 config.json 복사 완료");
        }

        configLoader = new ConfigLoader(getDataFolder());
        configLoader.load();

        initDatabase();

        sharedAdapter = new CustomChzzkOauthLoginAdapter(
                getDataFolder(),
                "0.0.0.0",
                configLoader.getPort(),
                configLoader.isHttps(),
                configLoader.getCallbackPath(),
                configLoader.getDisplayHost()
        );
        sharedAdapter.setClientCredentials(configLoader.getClientId(), configLoader.getClientSecret());
        sharedAdapter.startServerOnce();

        // 리스너 등록
        PlayerListener playerListener = new PlayerListener(this, configLoader, sharedAdapter);
        Bukkit.getPluginManager().registerEvents(playerListener, this);

        // PlaceholderAPI 등록
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new ChzzkPlaceholder(this, playerListener).register();
            getLogger().info("✅ PlaceholderAPI 연동 완료");
        } else {
            getLogger().warning("⚠ PlaceholderAPI가 없습니다. 플레이스홀더를 사용할 수 없습니다.");
        }

        getLogger().info("✅ GOTIGER_API 플러그인 활성화 완료");
    }

    @Override
    public void onDisable() {
        getLogger().info("❌ GOTIGER_API 플러그인 비활성화 완료");
    }

    public String getDBUrl() {
        File dbFile = new File(getDataFolder(), "tokens.db");
        return "jdbc:sqlite:" + dbFile.getAbsolutePath();
    }

    private void initDatabase() {
        String sql = """
        CREATE TABLE IF NOT EXISTS DEFAULT_TABLE (
            uuid TEXT PRIMARY KEY,
            refreshToken TEXT,
            accessToken TEXT,
            refreshDate TEXT,
            accessDate TEXT
        );
        """;

        try (Connection conn = DriverManager.getConnection(getDBUrl());
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            getLogger().info("✅ DB 테이블 생성 확인 완료: DEFAULT_TABLE");
        } catch (Exception e) {
            getLogger().severe("❌ DB 초기화 실패: " + e.getMessage());
        }
    }
}
