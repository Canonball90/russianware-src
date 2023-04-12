//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.api;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.Iterator;
import me.larp.dev.api.module.Module;
import me.larp.dev.Client;
import org.lwjgl.input.Keyboard;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class EventHandler
{
    @SubscribeEvent
    public void onKeyInput(final InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            for (final Module module : Client.moduleManager.getModules()) {
                if (module.getBind() == Keyboard.getEventKey()) {
                    module.toggle();
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onChatSend(final ClientChatEvent event) {
        if (Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().world != null && event.getMessage().startsWith(Client.commandManager.getPrefix())) {
            event.setCanceled(true);
            Minecraft.getMinecraft().ingameGUI.getChatGUI().addToSentMessages(event.getMessage());
            Client.commandManager.runCommand(event.getMessage());
        }
    }
}
