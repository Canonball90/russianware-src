//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.impl.module.movement;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.Arrays;
import me.larp.dev.api.module.Category;
import me.larp.dev.api.setting.Setting;
import me.larp.dev.api.module.Module;

public class NoFall extends Module
{
    private final Setting fallDist;
    private final Setting fallDist2;
    private final Setting mode;
    
    public NoFall() {
        super("NoFall", "", Category.MOVEMENT);
        this.fallDist = new Setting("FallDistance", this, 4, 3, 20);
        this.fallDist2 = new Setting("FallDistance2", this, 15, 10, 40);
        this.mode = new Setting("Mode", this, Arrays.asList("Predict", "Old"));
        this.addSetting(this.fallDist);
        this.addSetting(this.fallDist2);
        this.addSetting(this.mode);
    }
    
    @SubscribeEvent
    public void onUpdate(final TickEvent.ClientTickEvent event) {
        if (this.mc.world == null) {
            return;
        }
        if (this.mode.getEnumValue().equals("Predict")) {
            final Vec3d vec = new Vec3d(this.mc.player.lastTickPosX + (this.mc.player.posX - this.mc.player.lastTickPosX) * this.mc.getRenderPartialTicks(), this.mc.player.lastTickPosY + (this.mc.player.posY - this.mc.player.lastTickPosY) * this.mc.getRenderPartialTicks(), this.mc.player.lastTickPosZ + (this.mc.player.posZ - this.mc.player.lastTickPosZ) * this.mc.getRenderPartialTicks());
            final BlockPos pos = new BlockPos(vec.x, vec.y - 2.0, vec.z);
            final BlockPos[] array;
            final BlockPos[] posList = array = new BlockPos[] { pos.north(), pos.south(), pos.east(), pos.west(), pos.down(), pos.down() };
            for (final BlockPos blockPos : array) {
                final Block block = this.mc.world.getBlockState(blockPos).getBlock();
                if (this.mc.player.dimension == 1) {
                    if (this.mc.player.fallDistance > this.fallDist2.getIntegerValue()) {
                        this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(0.0, 64.0, 0.0, false));
                        this.mc.player.fallDistance = (float)(this.fallDist.getIntegerValue() + 1);
                    }
                    if (this.mc.player.fallDistance > this.fallDist.getIntegerValue() && block != Blocks.AIR) {
                        this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(0.0, 64.0, 0.0, false));
                        this.mc.player.fallDistance = 0.0f;
                    }
                }
                else {
                    if (this.mc.player.fallDistance > this.fallDist2.getIntegerValue()) {
                        this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, 0.0, this.mc.player.posZ, false));
                        this.mc.player.fallDistance = (float)(this.fallDist.getIntegerValue() + 1);
                    }
                    if (this.mc.player.fallDistance > this.fallDist.getIntegerValue() && block != Blocks.AIR) {
                        this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, 0.0, this.mc.player.posZ, false));
                        this.mc.player.fallDistance = 0.0f;
                    }
                }
            }
        }
        if (this.mode.getEnumValue().equals("Old") && this.mc.player.fallDistance > this.fallDist.getIntegerValue()) {
            this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, 0.0, this.mc.player.posZ, false));
            this.mc.player.fallDistance = 0.0f;
        }
    }
}
