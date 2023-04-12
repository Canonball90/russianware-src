//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.impl.module.combat;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.Iterator;
import me.larp.dev.api.util.PlayerUtil;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import me.larp.dev.api.util.LoggerUtil;
import java.util.Collection;
import java.util.ArrayList;
import net.minecraft.util.math.Vec3d;
import java.util.Arrays;
import me.larp.dev.api.module.Category;
import java.util.List;
import me.larp.dev.api.setting.Setting;
import me.larp.dev.api.module.Module;

public class Surround extends Module
{
    private static boolean exposed;
    private final Setting blocksPerTick;
    private final Setting disable;
    private final List positions;
    private boolean finished;
    
    public Surround() {
        super("Surround", "", Category.COMBAT);
        this.blocksPerTick = new Setting("BPT", this, 1, 1, 10);
        this.disable = new Setting("Disable", this, Arrays.asList("WhenDone", "OnLeave", "Off"));
        this.positions = new ArrayList(Arrays.asList(new Vec3d(1.0, -1.0, 0.0), new Vec3d(-1.0, -1.0, 0.0), new Vec3d(0.0, -1.0, 1.0), new Vec3d(0.0, -1.0, -1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(0.0, 0.0, -1.0)));
        this.addSetting(this.blocksPerTick);
        this.addSetting(this.disable);
    }
    
    public static boolean isExposed() {
        return Surround.exposed;
    }
    
    public static void setExposed(final boolean exposed) {
        Surround.exposed = exposed;
    }
    
    @Override
    public void onEnable() {
        this.finished = false;
        LoggerUtil.sendMessage("Surround ON");
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (!this.nullCheck()) {
            if (this.finished && (this.disable.getEnumValue().equalsIgnoreCase("WhenDone") || (this.disable.getEnumValue().equalsIgnoreCase("OnLeave") && !this.mc.player.onGround))) {
                this.disable();
            }
            int blocksPlaced = 0;
            for (final Vec3d position : this.positions) {
                final BlockPos pos = new BlockPos(position.add(this.mc.player.getPositionVector()));
                if (this.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR)) {
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
    
    @Override
    public void onDisable() {
        LoggerUtil.sendMessage("Surround OFF");
    }
}
