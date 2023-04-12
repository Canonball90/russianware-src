//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.impl.module.render;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import java.util.Objects;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.client.renderer.culling.Frustum;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import me.larp.dev.api.module.Category;
import net.minecraft.client.renderer.culling.ICamera;
import me.larp.dev.api.util.HoleUtil;
import java.util.List;
import me.larp.dev.api.setting.Setting;
import me.larp.dev.api.module.Module;

public class HoleESP extends Module
{
    private final Setting range;
    private final Setting ignoreOwn;
    private final Setting holeMode;
    private final Setting place;
    private final Setting obsidianRed;
    private final Setting obsidianGreen;
    private final Setting obsidianBlue;
    private final Setting obsidianAlpha;
    private final Setting bedrockRed;
    private final Setting bedrockGreen;
    private final Setting bedrockBlue;
    private final Setting bedrockAlpha;
    private final List<HoleUtil> holes;
    private final ICamera camera;
    
    public HoleESP() {
        super("HoleESP", "", Category.RENDER);
        this.range = new Setting("Range", this, 6, 1, 20);
        this.ignoreOwn = new Setting("IgnoreOwn", this, false);
        this.holeMode = new Setting("Mode", this, new ArrayList<String>(Arrays.asList("Block", "Flat")));
        this.place = new Setting("Place", this, new ArrayList<String>(Arrays.asList("Normal", "Down")));
        this.obsidianRed = new Setting("ObbyRed", this, 70, 0, 100);
        this.obsidianGreen = new Setting("ObbyGreen", this, 0, 0, 100);
        this.obsidianBlue = new Setting("ObbyBlue", this, 0, 0, 100);
        this.obsidianAlpha = new Setting("ObbyAlpha", this, 15, 0, 100);
        this.bedrockRed = new Setting("BedrockRed", this, 0, 0, 100);
        this.bedrockGreen = new Setting("BedrockGreen", this, 70, 0, 100);
        this.bedrockBlue = new Setting("BedrockBlue", this, 0, 0, 100);
        this.bedrockAlpha = new Setting("BedrockAlpha", this, 15, 0, 100);
        this.holes = new ArrayList<HoleUtil>();
        this.camera = (ICamera)new Frustum();
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent tickEvent) {
        if (this.mc.player == null || this.mc.world == null) {
            return;
        }
        this.holes.clear();
        final Vec3i playerPos = new Vec3i(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ);
        for (int x = playerPos.getX() - this.range.getIntegerValue(); x < playerPos.getX() + this.range.getIntegerValue(); ++x) {
            for (int z = playerPos.getZ() - this.range.getIntegerValue(); z < playerPos.getZ() + this.range.getIntegerValue(); ++z) {
                for (int y = playerPos.getY() + this.range.getIntegerValue(); y > playerPos.getY() - this.range.getIntegerValue(); --y) {
                    final BlockPos blockPos = new BlockPos(x, y, z);
                    if (!this.ignoreOwn.getBooleanValue() || this.mc.player.getDistanceSq(blockPos) > 1.0) {
                        final IBlockState blockState = this.mc.world.getBlockState(blockPos);
                        final IBlockState downBlockState = this.mc.world.getBlockState(blockPos.down());
                        HoleUtil.HoleTypes holeTypes = this.isBlockValid(blockState, blockPos);
                        if (downBlockState.getBlock() == Blocks.AIR) {
                            final BlockPos downPos = blockPos.down();
                            holeTypes = this.isBlockValid(downBlockState, blockPos);
                            this.holes.add(new HoleUtil(downPos.getX(), downPos.getY(), downPos.getZ(), downPos, holeTypes, true));
                        }
                        else {
                            this.holes.add(new HoleUtil(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos, holeTypes));
                        }
                    }
                }
            }
        }
    }
    
    @SubscribeEvent
    public void renderWorldEvent(final RenderWorldLastEvent event) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        me/larp/dev/impl/module/render/HoleESP.mc:Lnet/minecraft/client/Minecraft;
        //     4: getfield        net/minecraft/client/Minecraft.player:Lnet/minecraft/client/entity/EntityPlayerSP;
        //     7: ifnull          20
        //    10: aload_0         /* this */
        //    11: getfield        me/larp/dev/impl/module/render/HoleESP.mc:Lnet/minecraft/client/Minecraft;
        //    14: getfield        net/minecraft/client/Minecraft.world:Lnet/minecraft/client/multiplayer/WorldClient;
        //    17: ifnonnull       21
        //    20: return         
        //    21: aload_0         /* this */
        //    22: getfield        me/larp/dev/impl/module/render/HoleESP.mc:Lnet/minecraft/client/Minecraft;
        //    25: invokevirtual   net/minecraft/client/Minecraft.getRenderManager:()Lnet/minecraft/client/renderer/entity/RenderManager;
        //    28: getfield        net/minecraft/client/renderer/entity/RenderManager.options:Lnet/minecraft/client/settings/GameSettings;
        //    31: ifnonnull       35
        //    34: return         
        //    35: new             Ljava/util/ArrayList;
        //    38: dup            
        //    39: aload_0         /* this */
        //    40: getfield        me/larp/dev/impl/module/render/HoleESP.holes:Ljava/util/List;
        //    43: invokespecial   java/util/ArrayList.<init>:(Ljava/util/Collection;)V
        //    46: aload_0         /* this */
        //    47: invokedynamic   BootstrapMethod #0, accept:(Lme/larp/dev/impl/module/render/HoleESP;)Ljava/util/function/Consumer;
        //    52: invokevirtual   java/util/ArrayList.forEach:(Ljava/util/function/Consumer;)V
        //    55: return         
        //    StackMapTable: 00 03 14 00 0D
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Could not infer any expression.
        //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:374)
        //     at com.strobel.decompiler.ast.TypeAnalysis.run(TypeAnalysis.java:96)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:344)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:330)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:251)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:126)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void render(final String mode, final AxisAlignedBB bb, final float r, final float g, final float b, final float a) {
        switch (mode) {
            case "Flat": {
                RenderGlobal.renderFilledBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.minY, bb.maxZ, r, g, b, a);
                break;
            }
            case "Block": {
                RenderGlobal.renderFilledBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, r, g, b, a);
                break;
            }
        }
    }
    
    public HoleUtil.HoleTypes isBlockValid(final IBlockState blockState, final BlockPos blockPos) {
        if (blockState.getBlock() != Blocks.AIR || this.mc.world.getBlockState(blockPos.up()).getBlock() != Blocks.AIR || this.mc.world.getBlockState(blockPos.up(2)).getBlock() != Blocks.AIR || this.mc.world.getBlockState(blockPos.down()).getBlock() == Blocks.AIR) {
            return HoleUtil.HoleTypes.None;
        }
        final BlockPos[] touchingBlocks = { blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west() };
        boolean bedrock = true;
        boolean obsidian = true;
        int validHorizontalBlocks = 0;
        for (final BlockPos touching : touchingBlocks) {
            final IBlockState touchingState = this.mc.world.getBlockState(touching);
            if (touchingState.getBlock() != Blocks.AIR && touchingState.isFullBlock()) {
                ++validHorizontalBlocks;
                if (touchingState.getBlock() != Blocks.BEDROCK && bedrock) {
                    bedrock = false;
                }
                if (!bedrock && touchingState.getBlock() != Blocks.OBSIDIAN && touchingState.getBlock() != Blocks.BEDROCK) {
                    obsidian = false;
                }
            }
        }
        if (validHorizontalBlocks < 4) {
            return HoleUtil.HoleTypes.None;
        }
        if (bedrock) {
            return HoleUtil.HoleTypes.Bedrock;
        }
        if (obsidian) {
            return HoleUtil.HoleTypes.Obsidian;
        }
        return HoleUtil.HoleTypes.Normal;
    }
}
