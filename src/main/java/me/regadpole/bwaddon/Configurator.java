package me.regadpole.bwaddon;

import org.bukkit.configuration.file.FileConfiguration;

public class Configurator {
    private static final FileConfiguration config = BwAddon.INSTANCE.getConfig();

    public static void loadConfig() {
        Config.runCommandWhenGameStart = config.getString("runCommandWhenGameStart");
        Config.runCommandWhenGameEnd = config.getString("runCommandWhenGameEnd");
        Config.giveResourceWhenPlayerKillInGame = config.getString("giveResourceWhenPlayerKillInGame");
        Config.giveAnotherPlayerWhenPickRes = config.getDouble("giveAnotherPlayerWhenPickRes");
        Config.resShare = config.getBoolean("resShare");
    }

    public static class Config {
        public static String runCommandWhenGameStart;
        public static String runCommandWhenGameEnd;
        public static String giveResourceWhenPlayerKillInGame;
        public static double giveAnotherPlayerWhenPickRes;
        public static boolean resShare;
    }
}
