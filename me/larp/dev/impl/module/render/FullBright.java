//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.impl.module.render;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import me.larp.dev.api.module.Category;
import me.larp.dev.api.module.Module;

public class FullBright extends Module
{
    public FullBright() {
        super("FullBright", "", Category.RENDER);
    }
    
    @SubscribeEvent
    public void onUpdate(final TickEvent.ClientTickEvent event) {
        if (this.nullCheck()) {
            return;
        }
        this.mc.gameSettings.gammaSetting = 666.0f;
    }
}
