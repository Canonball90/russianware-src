//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.impl.module.hud;

import net.minecraft.client.gui.GuiScreen;
import me.larp.dev.Client;
import me.larp.dev.api.module.Category;
import me.larp.dev.api.module.Module;

public class ClickGUI extends Module
{
    public ClickGUI(final String name, final String description, final Category category) {
        super(name, description, category);
        this.setBind(54);
    }
    
    @Override
    public void onEnable() {
        this.mc.displayGuiScreen((GuiScreen)Client.clickGUI);
    }
}
