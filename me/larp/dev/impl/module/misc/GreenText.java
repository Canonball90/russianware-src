//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.impl.module.misc;

import me.larp.dev.api.util.LoggerUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.Iterator;
import java.util.Arrays;
import net.minecraftforge.client.event.ClientChatEvent;
import me.larp.dev.api.module.Category;
import me.larp.dev.api.module.Module;

public class GreenText extends Module
{
    public GreenText() {
        super("GreenText", Category.MISC);
    }
    
    @SubscribeEvent
    public void onChat(final ClientChatEvent event) {
        for (final String s : Arrays.asList("/", ".", "-", ",", ":", ";", "'", "\"", "+", "\\")) {
            if (event.getMessage().startsWith(s)) {
                return;
            }
        }
        event.setMessage("> " + event.getMessage());
    }
    
    @Override
    public void enable() {
        if (Minecraft.getMinecraft().isSingleplayer()) {
            LoggerUtil.sendMessage("Doesnt work in singeplayer.");
            this.set_disable();
        }
    }
    
    private void set_disable() {
    }
}
