//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.api.util;

import java.util.Objects;
import net.minecraft.client.network.NetHandlerPlayClient;
import java.util.Iterator;
import club.minnced.discord.rpc.DiscordEventHandlers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.Minecraft;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;

public class Discord
{
    private static String discordID;
    private static DiscordRichPresence discordRichPresence;
    private static DiscordRPC discordRPC;
    
    public static void startRPC() {
        final String server = Minecraft.getMinecraft().isSingleplayer() ? "Singleplayer" : Minecraft.getMinecraft().getCurrentServerData().serverIP;
        String ping = "NONE";
        for (final EntityPlayer player : Minecraft.getMinecraft().world.playerEntities) {
            if (player.getName() == Minecraft.getMinecraft().player.getName()) {
                ping = getPing(player) + "MS";
            }
        }
        final DiscordEventHandlers eventHandlers = new DiscordEventHandlers();
        eventHandlers.disconnected = ((var1, var2) -> System.out.println("Discord RPC disconnected, var1: " + var1 + "var2: " + var2));
        Discord.discordRPC.Discord_Initialize(Discord.discordID, eventHandlers, true, Discord.discordID);
        Discord.discordRichPresence.startTimestamp = System.currentTimeMillis() / 1000L;
        Discord.discordRichPresence.details = Minecraft.getMinecraft().getSession().getUsername() + " | " + server;
        Discord.discordRichPresence.largeImageKey = "icon";
        Discord.discordRichPresence.largeImageText = "rw v0.1";
        Discord.discordRichPresence.state = ping;
        Discord.discordRPC.Discord_UpdatePresence(Discord.discordRichPresence);
    }
    
    public static void stopRPC() {
        Discord.discordRPC.Discord_Shutdown();
        Discord.discordRPC.Discord_ClearPresence();
    }
    
    public static int getPing(final EntityPlayer player) {
        int ping = 0;
        try {
            ping = (int)MathUtil.clamp((float)Objects.requireNonNull(Minecraft.getMinecraft().getConnection()).getPlayerInfo(player.getUniqueID()).getResponseTime(), 1.0f, 300.0f);
        }
        catch (NullPointerException ex) {}
        return ping;
    }
    
    public void onUpdate() {
        updateRpc();
    }
    
    public static void updateRpc() {
        Discord.discordRPC.Discord_UpdatePresence(Discord.discordRichPresence);
    }
    
    void updateStatus() {
    }
    
    static {
        Discord.discordID = "932261513106948118";
        Discord.discordRichPresence = new DiscordRichPresence();
        Discord.discordRPC = DiscordRPC.INSTANCE;
    }
}
