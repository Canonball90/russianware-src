//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.impl.module.misc;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.network.play.client.CPacketUseEntity;
import me.larp.dev.impl.event.PacketEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.Arrays;
import me.larp.dev.api.module.Category;
import java.util.concurrent.ConcurrentHashMap;
import me.larp.dev.api.setting.Setting;
import me.larp.dev.api.module.Module;

public class AutoEZ extends Module
{
    private final Setting delay;
    private final Setting message;
    private final Setting zCounter;
    private final Setting watermark;
    private final Setting naked;
    private ConcurrentHashMap<String, Integer> targetedPlayers;
    
    public AutoEZ() {
        super("AutoEZ", Category.MISC);
        this.delay = new Setting("Delay", this, 30, 0, 200);
        this.message = new Setting("Message", this, Arrays.asList("GG", "EZ"));
        this.zCounter = new Setting("zCount", this, 3, 1, 10);
        this.watermark = new Setting("Watermark", this, true);
        this.naked = new Setting("Naked", this, true);
        this.targetedPlayers = new ConcurrentHashMap<String, Integer>();
    }
    
    @Override
    public void onEnable() {
        this.targetedPlayers = new ConcurrentHashMap<String, Integer>();
    }
    
    @Override
    public void onDisable() {
        this.targetedPlayers = null;
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.mc.player == null || this.mc.world == null) {
            return;
        }
        if (this.targetedPlayers == null) {
            this.targetedPlayers = new ConcurrentHashMap<String, Integer>();
        }
        for (final EntityPlayer player : this.mc.world.playerEntities) {
            if (player.getHealth() > 0.0f) {
                continue;
            }
            if (this.isNaked(this.mc.player) && !this.naked.getBooleanValue()) {
                continue;
            }
            final String name2 = player.getName();
            if (this.targetedPlayers.containsKey(name2)) {
                this.sendMessage(name2);
                break;
            }
        }
        this.targetedPlayers.forEach((name, timeout) -> {
            if (timeout <= 0) {
                this.targetedPlayers.remove(name);
            }
            else {
                this.targetedPlayers.put(name, timeout - 1);
            }
        });
    }
    
    private boolean isNaked(final EntityPlayerSP player) {
        return this.isEmpty(0, (EntityPlayer)player) && this.isEmpty(1, (EntityPlayer)player) && this.isEmpty(2, (EntityPlayer)player) && this.isEmpty(3, (EntityPlayer)player);
    }
    
    public boolean isEmpty(final int slotNumber, final EntityPlayer player) {
        return ((ItemStack)player.inventory.armorInventory.get(slotNumber)).isEmpty();
    }
    
    @SubscribeEvent
    public void onPacket(final PacketEvent.Send event) {
        if (this.mc.player == null || this.mc.world == null || !(event.getPacket() instanceof CPacketUseEntity)) {
            return;
        }
        final CPacketUseEntity cPacketUseEntity = (CPacketUseEntity)event.getPacket();
        if (!cPacketUseEntity.getAction().equals((Object)CPacketUseEntity.Action.ATTACK)) {
            return;
        }
        final Entity targetEntity = cPacketUseEntity.getEntityFromWorld((World)this.mc.world);
        if (targetEntity instanceof EntityPlayer) {
            this.addTargetedPlayer(targetEntity.getName());
        }
    }
    
    @SubscribeEvent
    public void onLivingDeath(final LivingDeathEvent event) {
        if (this.mc.player == null || this.mc.world == null) {
            return;
        }
        final EntityLivingBase entity = event.getEntityLiving();
        if (!(entity instanceof EntityPlayer)) {
            return;
        }
        final EntityPlayer player = (EntityPlayer)entity;
        if (player.getHealth() > 0.0f) {
            return;
        }
        final String name = player.getName();
        if (this.targetedPlayers.containsKey(name)) {
            this.sendMessage(name);
        }
    }
    
    private void sendMessage(final String name) {
        this.targetedPlayers.remove(name);
        final StringBuilder text = new StringBuilder();
        if (this.message.getEnumValue().equals("GG")) {
            text.append("GG ").append(name);
        }
        else {
            text.append("E");
            for (int i = 0; i < this.zCounter.getIntegerValue(); ++i) {
                text.append("Z");
            }
            text.append(" ").append(name);
        }
        if (this.watermark.getBooleanValue()) {
            text.append("! RussianWare owns me and all");
        }
        text.append("!");
        this.mc.player.connection.sendPacket((Packet)new CPacketChatMessage(text.toString()));
    }
    
    public void addTargetedPlayer(final String name) {
        if (name.equals(this.mc.player.getName())) {
            return;
        }
        this.targetedPlayers.put(name, this.delay.getIntegerValue());
    }
}
