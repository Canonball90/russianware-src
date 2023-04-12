//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.impl.module.movement;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import me.larp.dev.api.module.Category;
import me.larp.dev.api.setting.Setting;
import me.larp.dev.api.module.Module;

public class ReverseStep extends Module
{
    Setting holeOnly;
    
    public ReverseStep() {
        super("ReverseStep", "", Category.MOVEMENT);
        this.holeOnly = new Setting("HoleOnly", this, true);
    }
    
    @SubscribeEvent
    public void onTickClientTick(final TickEvent.ClientTickEvent event) {
        if (this.nullCheck() || this.mc.player.isInLava() || this.mc.player.isInWater()) {
            return;
        }
        if ((!this.holeOnly.getBooleanValue() && this.mc.player.onGround) || (this.holeOnly.getBooleanValue() && this.mc.player.onGround && this.fallingIntoHole())) {
            final EntityPlayerSP player = this.mc.player;
            --player.motionY;
        }
    }
    
    private boolean fallingIntoHole() {
        final Vec3d vec = new Vec3d(this.mc.player.lastTickPosX + (this.mc.player.posX - this.mc.player.lastTickPosX) * this.mc.getRenderPartialTicks(), this.mc.player.lastTickPosY + (this.mc.player.posY - this.mc.player.lastTickPosY) * this.mc.getRenderPartialTicks(), this.mc.player.lastTickPosZ + (this.mc.player.posZ - this.mc.player.lastTickPosZ) * this.mc.getRenderPartialTicks());
        final BlockPos pos = new BlockPos(vec.x, vec.y - 1.0, vec.z);
        final BlockPos[] posList = { pos.north(), pos.south(), pos.east(), pos.west(), pos.down() };
        int blocks = 0;
        for (final BlockPos blockPos : posList) {
            final Block block = this.mc.world.getBlockState(blockPos).getBlock();
            if (block == Blocks.OBSIDIAN || block == Blocks.BEDROCK) {
                ++blocks;
            }
        }
        return blocks == 5;
    }
}
