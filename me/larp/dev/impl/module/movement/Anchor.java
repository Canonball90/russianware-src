//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.impl.module.movement;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import me.larp.dev.api.module.Category;
import net.minecraft.util.math.Vec3d;
import me.larp.dev.api.setting.Setting;
import me.larp.dev.api.module.Module;

public class Anchor extends Module
{
    Setting pitch;
    Setting pull;
    int holeblocks;
    public static boolean AnchorING;
    private Vec3d Center;
    
    public Anchor() {
        super("Anchor", "", Category.MOVEMENT);
        this.pitch = new Setting("Pitch", this, 0, 0, 60);
        this.pull = new Setting("Pull", this, true);
        this.Center = Vec3d.ZERO;
        this.addSetting(this.pull);
        this.addSetting(this.pitch);
    }
    
    @SubscribeEvent
    public void onUpdate(final TickEvent.ClientTickEvent event) {
        if (this.nullCheck()) {
            return;
        }
        if (this.mc.player.rotationPitch >= this.pitch.getIntegerValue()) {
            if (this.isBlockHole(this.getPlayerPos().down(1)) || this.isBlockHole(this.getPlayerPos().down(2)) || this.isBlockHole(this.getPlayerPos().down(3)) || this.isBlockHole(this.getPlayerPos().down(4))) {
                Anchor.AnchorING = true;
                if (!this.pull.getBooleanValue()) {
                    this.mc.player.motionX = 0.0;
                    this.mc.player.motionZ = 0.0;
                }
                else {
                    this.Center = this.GetCenter(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ);
                    final double XDiff = Math.abs(this.Center.x - this.mc.player.posX);
                    final double ZDiff = Math.abs(this.Center.z - this.mc.player.posZ);
                    if (XDiff <= 0.1 && ZDiff <= 0.1) {
                        this.Center = Vec3d.ZERO;
                    }
                    else {
                        final double MotionX = this.Center.x - this.mc.player.posX;
                        final double MotionZ = this.Center.z - this.mc.player.posZ;
                        this.mc.player.motionX = MotionX / 2.0;
                        this.mc.player.motionZ = MotionZ / 2.0;
                    }
                }
            }
            else {
                Anchor.AnchorING = false;
            }
        }
    }
    
    @Override
    public void onDisable() {
        Anchor.AnchorING = false;
        this.holeblocks = 0;
    }
    
    public boolean isBlockHole(final BlockPos blockpos) {
        this.holeblocks = 0;
        if (this.mc.world.getBlockState(blockpos.add(0, 3, 0)).getBlock() == Blocks.AIR) {
            ++this.holeblocks;
        }
        if (this.mc.world.getBlockState(blockpos.add(0, 2, 0)).getBlock() == Blocks.AIR) {
            ++this.holeblocks;
        }
        if (this.mc.world.getBlockState(blockpos.add(0, 1, 0)).getBlock() == Blocks.AIR) {
            ++this.holeblocks;
        }
        if (this.mc.world.getBlockState(blockpos.add(0, 0, 0)).getBlock() == Blocks.AIR) {
            ++this.holeblocks;
        }
        if (this.mc.world.getBlockState(blockpos.add(0, -1, 0)).getBlock() == Blocks.OBSIDIAN || this.mc.world.getBlockState(blockpos.add(0, -1, 0)).getBlock() == Blocks.BEDROCK) {
            ++this.holeblocks;
        }
        if (this.mc.world.getBlockState(blockpos.add(1, 0, 0)).getBlock() == Blocks.OBSIDIAN || this.mc.world.getBlockState(blockpos.add(1, 0, 0)).getBlock() == Blocks.BEDROCK) {
            ++this.holeblocks;
        }
        if (this.mc.world.getBlockState(blockpos.add(-1, 0, 0)).getBlock() == Blocks.OBSIDIAN || this.mc.world.getBlockState(blockpos.add(-1, 0, 0)).getBlock() == Blocks.BEDROCK) {
            ++this.holeblocks;
        }
        if (this.mc.world.getBlockState(blockpos.add(0, 0, 1)).getBlock() == Blocks.OBSIDIAN || this.mc.world.getBlockState(blockpos.add(0, 0, 1)).getBlock() == Blocks.BEDROCK) {
            ++this.holeblocks;
        }
        if (this.mc.world.getBlockState(blockpos.add(0, 0, -1)).getBlock() == Blocks.OBSIDIAN || this.mc.world.getBlockState(blockpos.add(0, 0, -1)).getBlock() == Blocks.BEDROCK) {
            ++this.holeblocks;
        }
        return this.holeblocks >= 9;
    }
    
    public Vec3d GetCenter(final double posX, final double posY, final double posZ) {
        final double x = Math.floor(posX) + 0.5;
        final double y = Math.floor(posY);
        final double z = Math.floor(posZ) + 0.5;
        return new Vec3d(x, y, z);
    }
    
    public BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(this.mc.player.posX), Math.floor(this.mc.player.posY), Math.floor(this.mc.player.posZ));
    }
}
