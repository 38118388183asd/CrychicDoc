package io.github.steveplays28.noisium.forge;

import io.github.steveplays28.noisium.Noisium;
import net.minecraftforge.fml.common.Mod;

@Mod("noisium")
public class NoisiumForge {

    public NoisiumForge() {
        Noisium.initialize();
    }
}