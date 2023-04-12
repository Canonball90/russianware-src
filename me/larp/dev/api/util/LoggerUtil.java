//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.api.util;

import java.util.logging.Logger;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.client.Minecraft;

public class LoggerUtil
{
    public static void sendMessage(final String message) {
        sendMessage(message, true);
    }
    
    public static void sendMessage(final String message, final boolean waterMark) {
        final StringBuilder messageBuilder = new StringBuilder();
        if (waterMark) {
            messageBuilder.append("&f[&5Russian&dWare&f] &r");
        }
        messageBuilder.append("&f").append(message);
        if (Minecraft.getMinecraft().ingameGUI != null) {
            Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage((ITextComponent)new TextComponentString(messageBuilder.toString().replace("&", "§")));
        }
        Logger.getGlobal().info(messageBuilder.toString());
    }
    
    public static void sendOverwriteClientMessage(final String string) {
        if (Minecraft.getMinecraft().ingameGUI != null) {
            Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage((ITextComponent)new TextComponentString(string.replace("&", "§")));
        }
    }
}
