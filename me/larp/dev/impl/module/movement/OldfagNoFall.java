//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.impl.module.movement;

import net.minecraft.init.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.Arrays;
import me.larp.dev.api.module.Category;
import net.minecraft.util.math.BlockPos;
import me.larp.dev.api.setting.Setting;
import me.larp.dev.api.module.Module;

public class OldfagNoFall extends Module
{
    Setting mode;
    Setting disconnect;
    Setting fallDist;
    BlockPos n1;
    
    public OldfagNoFall() {
        super("OldfagNoFall", "", Category.MOVEMENT);
        this.mode = new Setting("Mode", this, Arrays.asList("Predict", "Old"));
        this.disconnect = new Setting("Disconnect", this, false);
        this.fallDist = new Setting("FallDistance", this, 4, 3, 20);
        this.addSetting(this.mode);
        this.addSetting(this.disconnect);
        this.addSetting(this.fallDist);
    }
    
    @SubscribeEvent
    public void onUpdate(final TickEvent.ClientTickEvent event) {
        if (this.nullCheck()) {
            return;
        }
        if (this.mode.getEnumValue().equals("Predict") && this.mc.player.fallDistance > this.fallDist.getIntegerValue() && this.predict(new BlockPos(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ))) {
            this.mc.player.motionY = 0.0;
            this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, (double)this.n1.getY(), this.mc.player.posZ, false));
            this.mc.player.fallDistance = 0.0f;
            if (this.disconnect.getBooleanValue()) {
                this.mc.player.connection.getNetworkManager().closeChannel((ITextComponent)new TextComponentString(ChatFormatting.GOLD + "NoFall"));
            }
        }
        if (this.mode.getEnumValue().equals("Old") && this.mc.player.fallDistance > this.fallDist.getIntegerValue()) {
            this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, 0.0, this.mc.player.posZ, false));
            this.mc.player.fallDistance = 0.0f;
        }
    }
    
    private boolean predict(final BlockPos blockPos) {
        final Minecraft mc = Minecraft.getMinecraft();
        this.n1 = blockPos.add(0, -this.fallDist.getIntegerValue(), 0);
        return mc.world.getBlockState(this.n1).getBlock() != Blocks.AIR;
    }
}
