package com.blbilink.blbilogin.modules.stats;

import com.blbilink.blbilogin.BlbiLogin;
import com.blbilink.blbilogin.vars.Configvar;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

/**
 * Tracks how many times players have joined the server.
 */
public class JoinCounter {
    private final File file;

    public JoinCounter(BlbiLogin plugin) {
        this.file = new File(plugin.getDataFolder(), "joincount.dat");
        load();
    }

    public void increment() {
        Configvar.joinCount++;
        save();
    }

    public void load() {
        if (file.exists()) {
            try {
                String content = Files.readString(file.toPath()).trim();
                Configvar.joinCount = Integer.parseInt(content);
            } catch (IOException | NumberFormatException e) {
                Configvar.joinCount = 0;
            }
        } else {
            Configvar.joinCount = 0;
        }
    }

    public void save() {
        try {
            Files.writeString(file.toPath(), Integer.toString(Configvar.joinCount),
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            // ignore
        }
    }
}
