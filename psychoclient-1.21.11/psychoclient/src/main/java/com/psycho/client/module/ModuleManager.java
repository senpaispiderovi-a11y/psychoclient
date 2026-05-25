package com.psycho.client.module;

import com.psycho.client.modules.performance.FPSBoost;
import com.psycho.client.modules.performance.EntityCulling;
import com.psycho.client.modules.performance.NoLag;
import com.psycho.client.modules.visual.MotionBlur;
import com.psycho.client.modules.visual.FullBright;
import com.psycho.client.modules.visual.Crosshair;
import com.psycho.client.modules.movement.Sprint;
import com.psycho.client.modules.movement.NoFall;
import com.psycho.client.modules.combat.Reach;
import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    public final List<Module> modules = new ArrayList<>();

    public ModuleManager() {
        // Performance
        register(new FPSBoost());
        register(new EntityCulling());
        register(new NoLag());

        // Visual
        register(new MotionBlur());
        register(new FullBright());
        register(new Crosshair());

        // Movement
        register(new Sprint());
        register(new NoFall());

        // Combat
        register(new Reach());
    }

    private void register(Module m) {
        modules.add(m);
    }

    public void tick(MinecraftClient mc) {
        for (Module m : modules) {
            if (m.enabled) m.onTick(mc);
        }
    }

    public List<Module> getByCategory(Module.Category cat) {
        List<Module> result = new ArrayList<>();
        for (Module m : modules) {
            if (m.category == cat) result.add(m);
        }
        return result;
    }

    public Module getByName(String name) {
        return modules.stream().filter(m -> m.name.equals(name)).findFirst().orElse(null);
    }
}
