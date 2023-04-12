//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.api.module;

import me.larp.dev.Client;
import me.larp.dev.api.setting.Setting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.client.Minecraft;

public class Module
{
    private String name;
    private String description;
    private Category category;
    private int bind;
    private boolean enabled;
    public final Minecraft mc;
    
    public Module(final String name, final Category category) {
        this.mc = Minecraft.getMinecraft();
        this.name = name;
        this.category = category;
    }
    
    public Module(final String name, final String description, final Category category) {
        this.mc = Minecraft.getMinecraft();
        this.name = name;
        this.description = description;
        this.category = category;
    }
    
    public Module() {
        this.mc = Minecraft.getMinecraft();
    }
    
    public void onEnable() {
    }
    
    public void onDisable() {
    }
    
    public void enable() {
        this.setEnabled(true);
        this.onEnable();
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    public void disable() {
        this.setEnabled(false);
        this.onDisable();
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
    
    public void toggle() {
        if (this.isEnabled()) {
            this.disable();
        }
        else {
            this.enable();
        }
    }
    
    public boolean nullCheck() {
        return this.mc.player == null || this.mc.world == null;
    }
    
    public void addSetting(final Setting setting) {
        Client.settingManager.addSetting(setting);
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(final String description) {
        this.description = description;
    }
    
    public Category getCategory() {
        return this.category;
    }
    
    public void setCategory(final Category category) {
        this.category = category;
    }
    
    public int getBind() {
        return this.bind;
    }
    
    public void setBind(final int bind) {
        this.bind = bind;
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
}
