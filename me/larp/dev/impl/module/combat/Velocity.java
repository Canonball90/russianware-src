//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.impl.module.combat;

import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import me.larp.dev.impl.event.PacketReceiveEvent;
import me.larp.dev.api.module.Category;
import me.larp.dev.api.module.Module;

public class Velocity extends Module
{
    public Velocity() {
        super("Velocity", "", Category.COMBAT);
    }
    
    @SubscribeEvent
    public void listener(final PacketReceiveEvent event) {
        if (this.mc.player == null || this.mc.world == null) {
            return;
        }
        if (event.getPacket() instanceof SPacketEntityVelocity && ((SPacketEntityVelocity)event.getPacket()).getEntityID() == this.mc.player.getEntityId()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketExplosion) {
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.mc.player == null || this.mc.world == null) {
            return;
        }
        this.mc.player.entityCollisionReduction = 1.0f;
    }
}
