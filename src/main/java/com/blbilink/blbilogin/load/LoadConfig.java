package com.blbilink.blbilogin.load;

import com.blbilink.blbilogin.BlbiLogin;
import com.blbilink.blbilogin.vars.Configvar;
import org.blbilink.blbiLibrary.I18n;
import org.blbilink.blbiLibrary.utils.ConfigUtil;

public class LoadConfig {
    public static void loadConfig(BlbiLogin plugin) {
        // Load configuration
        plugin.config = new ConfigUtil(plugin);
        Configvar.config = plugin.config.loadConfig("config.yml");
        Configvar.configFile = plugin.config.configFile;
        plugin.i18n = new I18n(plugin,Configvar.config.getString("prefix"), Configvar.config.getString("language","zh_CN"));
        plugin.i18n.loadLanguage();
    }
}

// Reserved: load multiple config files

// Get or create config file
// Export config template on each run
//plugin.saveResource("config.yml", true);
        /*
        plugin.getDataFolder().mkdirs(); // ensure data folder exists
        if (!configFile.exists()) {
            // If file does not exist, copy template using config.yml.template as resourcePath
            try {
                File templateFile = new File(plugin.getDataFolder(), "config.yml");
                Files.copy(templateFile.toPath(), configFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (
                    IOException e) {
                e.printStackTrace();
            }
        }
         */
