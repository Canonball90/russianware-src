//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.impl.module.combat;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import me.larp.dev.impl.event.PacketSendEvent;
import me.larp.dev.api.module.Category;
import me.larp.dev.api.module.Module;

public class Criticals extends Module
{
    public Criticals() {
        super("Criticals", "", Category.COMBAT);
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketSendEvent event) {
        if (this.nullCheck()) {
            return;
        }
        if (event.getPacket() instanceof CPacketUseEntity && ((CPacketUseEntity)event.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK && this.mc.player.onGround) {
            this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + 0.1, this.mc.player.posZ, false));
            this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ, false));
        }
    }
}
