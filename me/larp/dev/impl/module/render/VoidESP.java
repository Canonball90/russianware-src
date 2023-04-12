//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.impl.module.render;

import me.larp.dev.api.util.RenderUtil;
import java.awt.Color;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import java.util.ArrayList;
import java.util.List;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.Iterator;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.Arrays;
import me.larp.dev.api.module.Category;
import net.minecraft.util.math.BlockPos;
import io.netty.util.internal.ConcurrentSet;
import me.larp.dev.api.setting.Setting;
import me.larp.dev.api.module.Module;

public class VoidESP extends Module
{
    private final Setting range;
    private final Setting color;
    private final Setting red;
    private final Setting green;
    private final Setting blue;
    private final Setting alpha;
    private final Setting rainbowSpeed;
    private final ConcurrentSet<BlockPos> voidHoles;
    private float hue;
    
    public VoidESP() {
        super("VoidESP", "", Category.RENDER);
        this.range = new Setting("Range", this, 1, 8, 50);
        this.color = new Setting("Color", this, Arrays.asList("Static", "Rainbow"));
        this.red = new Setting("Red", this, 0, 255, 255);
        this.green = new Setting("Green", this, 0, 20, 255);
        this.blue = new Setting("Blue", this, 0, 20, 255);
        this.alpha = new Setting("Alpha", this, 0, 100, 255);
        this.rainbowSpeed = new Setting("RainbowSpeed", this, 0, 5, 10);
        this.voidHoles = (ConcurrentSet<BlockPos>)new ConcurrentSet();
        this.hue = 0.0f;
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.mc.player == null || this.mc.world == null || this.mc.player.dimension == 1) {
            return;
        }
        this.voidHoles.clear();
        for (final BlockPos pos : this.getCircle(new BlockPos(Math.floor(this.mc.player.posX), Math.floor(this.mc.player.posY), Math.floor(this.mc.player.posZ)), this.range.getIntValue())) {
            if (!this.mc.world.getBlockState(pos).getBlock().equals(Blocks.BEDROCK) && pos.getY() == 0) {
                this.voidHoles.add((Object)pos);
            }
        }
    }
    
    private List<BlockPos> getCircle(final BlockPos loc, final float r) {
        final List<BlockPos> blocks = new ArrayList<BlockPos>();
        final int cx = loc.getX();
        final int cz = loc.getZ();
        for (int x = cx - (int)r; x <= cx + r; ++x) {
            for (int z = cz - (int)r; z <= cz + r; ++z) {
                final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z);
                if (dist < r * r) {
                    final BlockPos l = new BlockPos(x, 0, z);
                    blocks.add(l);
                }
            }
        }
        return blocks;
    }
    
    @SubscribeEvent
    public void renderWorld(final RenderWorldLastEvent event) {
        if (this.mc.player == null || this.mc.world == null || this.voidHoles.isEmpty()) {
            return;
        }
        this.hue += this.rainbowSpeed.getIntValue() / 1000.0f;
        final int rgb = Color.HSBtoRGB(this.hue, 1.0f, 1.0f);
        final int r = rgb >> 16 & 0xFF;
        final int g = rgb >> 8 & 0xFF;
        final int b = rgb & 0xFF;
        if (this.color.getEnumValue().equals("Rainbow")) {
            this.voidHoles.forEach(blockPos -> RenderUtil.drawBoxFromBlockpos(blockPos, r / 255.0f, g / 255.0f, b / 255.0f, this.alpha.getIntValue() / 255.0f));
        }
        else {
            this.voidHoles.forEach(blockPos -> RenderUtil.drawBoxFromBlockpos(blockPos, this.red.getIntValue() / 255.0f, this.green.getIntValue() / 255.0f, this.blue.getIntValue() / 255.0f, this.alpha.getIntValue() / 255.0f));
        }
    }
}
