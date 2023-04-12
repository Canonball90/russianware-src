//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.api.manager;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.larp.dev.Client;
import java.util.function.BiFunction;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import java.util.Map;
import me.larp.dev.api.util.burrow.Globals;

public class PopManager implements Globals
{
    private Map<EntityPlayer, Integer> popList;
    public final List<String> toAnnouce;
    
    public PopManager() {
        this.popList = new ConcurrentHashMap<EntityPlayer, Integer>();
        this.toAnnouce = new ArrayList<String>();
    }
    
    public void onTotemPop(final EntityPlayer player) {
        this.popTotem(player);
        if (!player.equals((Object)PopManager.mc.player) && player.isEntityAlive()) {
            this.toAnnouce.add(this.getPopString(player, this.getTotemPops(player)));
        }
    }
    
    public void onDeath(final EntityPlayer player) {
        if (this.getTotemPops(player) != 0 && !player.equals((Object)PopManager.mc.player)) {
            this.toAnnouce.add(this.getDeathString(player, this.getTotemPops(player)));
        }
        this.resetPops(player);
    }
    
    public void onLogout() {
        this.clearList();
    }
    
    public void clearList() {
        this.popList = new ConcurrentHashMap<EntityPlayer, Integer>();
    }
    
    public void resetPops(final EntityPlayer player) {
        this.setTotemPops(player, 0);
    }
    
    public void popTotem(final EntityPlayer player) {
        this.popList.merge(player, 1, Integer::sum);
    }
    
    public void setTotemPops(final EntityPlayer player, final int amount) {
        this.popList.put(player, amount);
    }
    
    public int getTotemPops(final EntityPlayer player) {
        return (this.popList.get(player) == null) ? 0 : this.popList.get(player);
    }
    
    private String getDeathString(final EntityPlayer player, final int pops) {
        if (Client.friendManager.isFriend(player.getName())) {
            return "DUDE! you just let " + ChatFormatting.AQUA + player.getName() + ChatFormatting.RESET + " DIE after popping " + ChatFormatting.GREEN + ChatFormatting.BOLD + pops + ChatFormatting.RESET + ((pops == 1) ? " totem" : " totems");
        }
        return "LMAO " + ChatFormatting.RED + player.getName() + ChatFormatting.RESET + " just fucking DIED after popping " + ChatFormatting.GREEN + ChatFormatting.BOLD + pops + ChatFormatting.RESET + ((pops == 1) ? " totem" : " totems");
    }
    
    private String getPopString(final EntityPlayer player, final int pops) {
        if (Client.friendManager.isFriend(player.getName())) {
            return "ur buddy " + ChatFormatting.AQUA + player.getName() + ChatFormatting.RESET + " has now popped " + ChatFormatting.RED + ChatFormatting.BOLD + pops + ChatFormatting.RESET + ((pops == 1) ? " totem" : " totems") + " go help them";
        }
        return "cry abt it " + ChatFormatting.RED + player.getName() + ChatFormatting.RESET + " has now popped " + ChatFormatting.RED + ChatFormatting.BOLD + pops + ChatFormatting.RESET + ((pops == 1) ? " totem" : " totems");
    }
}
