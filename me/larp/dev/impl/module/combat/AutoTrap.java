//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.impl.module.combat;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import java.util.Iterator;
import me.larp.dev.api.util.PlayerUtil;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import me.larp.dev.api.util.LoggerUtil;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import net.minecraft.util.math.Vec3d;
import me.larp.dev.api.module.Category;
import java.util.List;
import me.larp.dev.api.setting.Setting;
import me.larp.dev.api.module.Module;

public class AutoTrap extends Module
{
    private final Setting blocksPerTick;
    private final Setting disable;
    private final List positions;
    private boolean finished;
    
    public AutoTrap() {
        super("AutoTrap", Category.COMBAT);
        this.blocksPerTick = new Setting("BPT", this, 1, 1, 10);
        this.disable = new Setting("Disable", this, true);
        this.positions = new ArrayList(Arrays.asList(new Vec3d(0.0, -1.0, -1.0), new Vec3d(1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, 1.0), new Vec3d(-1.0, -1.0, 0.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(0.0, 2.0, -1.0), new Vec3d(0.0, 2.0, 1.0), new Vec3d(0.0, 2.0, 0.0)));
    }
    
    @Override
    public void onEnable() {
        this.finished = false;
        LoggerUtil.sendMessage("AutoTrap ON");
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (!this.nullCheck()) {
            if (this.finished && this.disable.getBooleanValue()) {
                this.disable();
            }
            int blocksPlaced = 0;
            for (final Vec3d position : this.positions) {
                final EntityPlayer closestPlayer = this.getClosestPlayer();
                if (closestPlayer != null) {
                    final BlockPos pos = new BlockPos(position.add(this.getClosestPlayer().getPositionVector()));
                    if (!this.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR)) {
                        continue;
                    }
                    final int oldSlot = this.mc.player.inventory.currentItem;
                    this.mc.player.inventory.currentItem = PlayerUtil.getSlot(Blocks.OBSIDIAN);
                    PlayerUtil.placeBlock(pos);
                    this.mc.player.inventory.currentItem = oldSlot;
                    if (++blocksPlaced == this.blocksPerTick.getIntegerValue()) {
                        return;
                    }
                    continue;
                }
            }
            if (blocksPlaced == 0) {
                this.finished = true;
            }
        }
    }
    
    private EntityPlayer getClosestPlayer() {
        EntityPlayer closestPlayer = null;
        double range = 1000.0;
        for (final EntityPlayer playerEntity : this.mc.world.playerEntities) {
            if (!playerEntity.equals((Object)this.mc.player)) {
                final double distance = this.mc.player.getDistance((Entity)playerEntity);
                if (distance >= range) {
                    continue;
                }
                closestPlayer = playerEntity;
                range = distance;
            }
        }
        return closestPlayer;
    }
    
    @Override
    public void onDisable() {
        LoggerUtil.sendMessage("AutoTrap OFF");
    }
}
