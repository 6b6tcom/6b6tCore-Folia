package com.blbilink.blbilogin.vars;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;

public class Configvar {

    public static Map<String, Location> originalLocation = new HashMap<>();
    public static List<String> noLoginPlayerList = new ArrayList<>();
    public static List<String> canFlyingPlayerList = new ArrayList<>();
    public static Set<String> vanishPlayers = new HashSet<>();
    public static int joinCount = 0;
    public static FileConfiguration config;
    public static File configFile;

}
