//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.impl.module.misc;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import me.larp.dev.api.module.Category;
import me.larp.dev.api.module.Module;

public class FastUse extends Module
{
    public FastUse() {
        super("FastUse", "", Category.MISC);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.mc.player == null || this.mc.world == null) {
            return;
        }
        ObfuscationReflectionHelper.setPrivateValue((Class)Minecraft.class, (Object)this.mc, (Object)0, new String[] { "rightClickDelayTimer", "rightClickDelayTimer" });
    }
    
    @Override
    public void onDisable() {
        ObfuscationReflectionHelper.setPrivateValue((Class)Minecraft.class, (Object)this.mc, (Object)4, new String[] { "rightClickDelayTimer", "rightClickDelayTimer" });
    }
}
