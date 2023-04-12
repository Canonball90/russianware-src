// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.impl.module.misc;

import me.larp.dev.api.util.Discord;
import me.larp.dev.api.module.Category;
import me.larp.dev.api.module.Module;

public class DiscordRPCModule extends Module
{
    public DiscordRPCModule() {
        super("DiscordRPC", Category.MISC);
    }
    
    @Override
    public void onEnable() {
        Discord.startRPC();
    }
    
    @Override
    public void onDisable() {
        Discord.stopRPC();
    }
    
    public void onUpdate() {
        this.updateRpc();
    }
    
    void updateRpc() {
        Discord.updateRpc();
    }
    
    void updateStatus() {
    }
}
