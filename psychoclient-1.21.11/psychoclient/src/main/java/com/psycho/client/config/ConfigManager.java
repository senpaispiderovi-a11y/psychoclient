package com.psycho.client.config;

import com.google.gson.*;
import com.psycho.client.PsychoClient;
import com.psycho.client.module.Module;
import com.psycho.client.modules.visual.MotionBlur;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Path;

public class ConfigManager {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final Path configPath = FabricLoader.getInstance().getConfigDir().resolve("psychoclient.json");

    public void save() {
        JsonObject root = new JsonObject();

        // Save module states
        JsonObject modules = new JsonObject();
        for (Module m : PsychoClient.moduleManager.modules) {
            modules.addProperty(m.name, m.enabled);
        }
        root.add("modules", modules);

        // Save motion blur strength
        root.addProperty("motionBlurStrength", MotionBlur.strength);

        try (FileWriter w = new FileWriter(configPath.toFile())) {
            GSON.toJson(root, w);
        } catch (IOException e) {
            PsychoClient.LOGGER.error("Failed to save config", e);
        }
    }

    public void load() {
        File f = configPath.toFile();
        if (!f.exists()) { save(); return; }

        try (FileReader r = new FileReader(f)) {
            JsonObject root = GSON.fromJson(r, JsonObject.class);
            if (root == null) return;

            if (root.has("modules")) {
                JsonObject modules = root.getAsJsonObject("modules");
                for (Module m : PsychoClient.moduleManager.modules) {
                    if (modules.has(m.name)) {
                        boolean shouldEnable = modules.get(m.name).getAsBoolean();
                        if (shouldEnable && !m.enabled) m.toggle();
                        else if (!shouldEnable && m.enabled) m.toggle();
                    }
                }
            }

            if (root.has("motionBlurStrength")) {
                MotionBlur.strength = root.get("motionBlurStrength").getAsFloat();
            }

        } catch (IOException e) {
            PsychoClient.LOGGER.error("Failed to load config", e);
        }
    }
}
