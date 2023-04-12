//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.api.util;

import java.util.Iterator;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3i;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.item.ItemBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import java.util.Objects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.init.MobEffects;
import net.minecraft.client.Minecraft;

public class PlayerUtil
{
    private static final Minecraft mc;
    
    public static double vanillaSpeed() {
        double baseSpeed = 0.272;
        if (Minecraft.getMinecraft().player.isPotionActive(MobEffects.SPEED)) {
            final int amplifier = Objects.requireNonNull(Minecraft.getMinecraft().player.getActivePotionEffect(MobEffects.SPEED)).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * amplifier;
        }
        return baseSpeed;
    }
    
    public static boolean isMoving() {
        return Minecraft.getMinecraft().player.moveForward != 0.0 || Minecraft.getMinecraft().player.moveStrafing != 0.0;
    }
    
    public static int getSlot(final Item item) {
        for (int i = 0; i < 9; ++i) {
            final Item item2 = Minecraft.getMinecraft().player.inventory.getStackInSlot(i).getItem();
            if (item.equals(item2)) {
                return i;
            }
        }
        return -1;
    }
    
    public static int getSlot(final Block block) {
        for (int i = 0; i < 9; ++i) {
            final Item item = Minecraft.getMinecraft().player.inventory.getStackInSlot(i).getItem();
            if (item instanceof ItemBlock && ((ItemBlock)item).getBlock().equals(block)) {
                return i;
            }
        }
        return -1;
    }
    
    public static void placeBlock(final BlockPos pos) {
        for (final EnumFacing enumFacing : EnumFacing.values()) {
            if (!PlayerUtil.mc.world.getBlockState(pos.offset(enumFacing)).getBlock().equals(Blocks.AIR) && !isIntercepted(pos)) {
                final Vec3d vec = new Vec3d(pos.getX() + 0.5 + enumFacing.getXOffset() * 0.5, pos.getY() + 0.5 + enumFacing.getYOffset() * 0.5, pos.getZ() + 0.5 + enumFacing.getZOffset() * 0.5);
                final float[] old = { PlayerUtil.mc.player.rotationYaw, PlayerUtil.mc.player.rotationPitch };
                PlayerUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation((float)Math.toDegrees(Math.atan2(vec.z - PlayerUtil.mc.player.posZ, vec.x - PlayerUtil.mc.player.posX)) - 90.0f, (float)(-Math.toDegrees(Math.atan2(vec.y - (PlayerUtil.mc.player.posY + PlayerUtil.mc.player.getEyeHeight()), Math.sqrt((vec.x - PlayerUtil.mc.player.posX) * (vec.x - PlayerUtil.mc.player.posX) + (vec.z - PlayerUtil.mc.player.posZ) * (vec.z - PlayerUtil.mc.player.posZ))))), PlayerUtil.mc.player.onGround));
                PlayerUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)PlayerUtil.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                PlayerUtil.mc.playerController.processRightClickBlock(PlayerUtil.mc.player, PlayerUtil.mc.world, pos.offset(enumFacing), enumFacing.getOpposite(), new Vec3d((Vec3i)pos), EnumHand.MAIN_HAND);
                PlayerUtil.mc.player.swingArm(EnumHand.MAIN_HAND);
                PlayerUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)PlayerUtil.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                PlayerUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(old[0], old[1], PlayerUtil.mc.player.onGround));
                return;
            }
        }
    }
    
    public static boolean isIntercepted(final BlockPos pos) {
        for (final Entity entity : PlayerUtil.mc.world.loadedEntityList) {
            if (new AxisAlignedBB(pos).intersects(entity.getEntityBoundingBox())) {
                return true;
            }
        }
        return false;
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
}
