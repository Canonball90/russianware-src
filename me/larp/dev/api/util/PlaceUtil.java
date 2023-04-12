//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.api.util;

import java.util.Arrays;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.MathHelper;
import net.minecraft.entity.EntityLivingBase;
import java.util.Iterator;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3i;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.EnumFacing;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.item.Item;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.Block;
import java.util.List;
import net.minecraft.client.Minecraft;

public class PlaceUtil
{
    private static final Minecraft mc;
    public static List<Block> emptyBlocks;
    public static List<Block> rightClickableBlocks;
    
    public static boolean placeBlock(final BlockPos pos, final int slot, final boolean rotate, final boolean rotateBack) {
        if (isBlockNotEmpty(pos) || PlaceUtil.mc.player.inventory.getStackInSlot(slot).getItem() == Item.getItemFromBlock(Blocks.WEB)) {
            for (final Entity entity : PlaceUtil.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos))) {
                if (!(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb)) {
                    return false;
                }
            }
            if (slot != PlaceUtil.mc.player.inventory.currentItem) {
                PlaceUtil.mc.player.inventory.currentItem = slot;
            }
            final EnumFacing[] values;
            final EnumFacing[] enumFacings = values = EnumFacing.values();
            for (final EnumFacing enumFacing : values) {
                final Block neighborBlock = PlaceUtil.mc.world.getBlockState(pos.offset(enumFacing)).getBlock();
                final Vec3d vec = new Vec3d(pos.getX() + 0.5 + enumFacing.getXOffset() * 0.5, pos.getY() + 0.5 + enumFacing.getYOffset() * 0.5, pos.getZ() + 0.5 + enumFacing.getZOffset() * 0.5);
                if (!PlaceUtil.emptyBlocks.contains(neighborBlock) && PlaceUtil.mc.player.getPositionEyes(PlaceUtil.mc.getRenderPartialTicks()).distanceTo(vec) <= 4.25) {
                    final float[] rot = { PlaceUtil.mc.player.rotationYaw, PlaceUtil.mc.player.rotationPitch };
                    if (rotate) {
                        rotatePacket(vec.x, vec.y, vec.z);
                    }
                    if (PlaceUtil.rightClickableBlocks.contains(neighborBlock)) {
                        PlaceUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)PlaceUtil.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                    }
                    PlaceUtil.mc.playerController.processRightClickBlock(PlaceUtil.mc.player, PlaceUtil.mc.world, pos.offset(enumFacing), enumFacing.getOpposite(), new Vec3d((Vec3i)pos), EnumHand.MAIN_HAND);
                    if (PlaceUtil.rightClickableBlocks.contains(neighborBlock)) {
                        PlaceUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)PlaceUtil.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                    }
                    if (rotateBack) {
                        PlaceUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rot[0], rot[1], PlaceUtil.mc.player.onGround));
                    }
                    return true;
                }
            }
        }
        return false;
    }
    
    public static boolean isBlockNotEmpty(final BlockPos pos) {
        if (PlaceUtil.emptyBlocks.contains(PlaceUtil.mc.world.getBlockState(pos).getBlock())) {
            final AxisAlignedBB axisAlignedBB = new AxisAlignedBB(pos);
            for (final Entity entity : PlaceUtil.mc.world.loadedEntityList) {
                if (entity instanceof EntityLivingBase && axisAlignedBB.intersects(entity.getEntityBoundingBox())) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    public static boolean canPlaceBlock(final BlockPos pos) {
        if (isBlockNotEmpty(pos)) {
            final EnumFacing[] values;
            final EnumFacing[] enumFacings = values = EnumFacing.values();
            for (final EnumFacing enumFacing : values) {
                if (!PlaceUtil.emptyBlocks.contains(PlaceUtil.mc.world.getBlockState(pos.offset(enumFacing)).getBlock()) && PlaceUtil.mc.player.getPositionEyes(PlaceUtil.mc.getRenderPartialTicks()).distanceTo(new Vec3d(pos.getX() + 0.5 + enumFacing.getXOffset() * 0.5, pos.getY() + 0.5 + enumFacing.getYOffset() * 0.5, pos.getZ() + 0.5 + enumFacing.getZOffset() * 0.5)) <= 4.25) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static EnumFacing getClosestFacing(final BlockPos pos) {
        return EnumFacing.DOWN;
    }
    
    public static void rotateClient(final double x, final double y, final double z) {
        final double diffX = x - PlaceUtil.mc.player.posX;
        final double diffY = y - (PlaceUtil.mc.player.posY + PlaceUtil.mc.player.getEyeHeight());
        final double diffZ = z - PlaceUtil.mc.player.posZ;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        final EntityPlayerSP player = PlaceUtil.mc.player;
        player.rotationYaw += MathHelper.wrapDegrees(yaw - PlaceUtil.mc.player.rotationYaw);
        final EntityPlayerSP player2 = PlaceUtil.mc.player;
        player2.rotationPitch += MathHelper.wrapDegrees(pitch - PlaceUtil.mc.player.rotationPitch);
    }
    
    public static void rotatePacket(final double x, final double y, final double z) {
        final double diffX = x - PlaceUtil.mc.player.posX;
        final double diffY = y - (PlaceUtil.mc.player.posY + PlaceUtil.mc.player.getEyeHeight());
        final double diffZ = z - PlaceUtil.mc.player.posZ;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        PlaceUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(yaw, pitch, PlaceUtil.mc.player.onGround));
    }
    
    static {
        mc = Minecraft.getMinecraft();
        PlaceUtil.emptyBlocks = Arrays.asList(Blocks.AIR, (Block)Blocks.FLOWING_LAVA, (Block)Blocks.LAVA, (Block)Blocks.FLOWING_WATER, (Block)Blocks.WATER, Blocks.VINE, Blocks.SNOW_LAYER, (Block)Blocks.TALLGRASS, (Block)Blocks.FIRE);
        PlaceUtil.rightClickableBlocks = Arrays.asList((Block)Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.ENDER_CHEST, Blocks.WHITE_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.ANVIL, Blocks.WOODEN_BUTTON, Blocks.STONE_BUTTON, (Block)Blocks.UNPOWERED_COMPARATOR, (Block)Blocks.UNPOWERED_REPEATER, (Block)Blocks.POWERED_REPEATER, (Block)Blocks.POWERED_COMPARATOR, Blocks.OAK_FENCE_GATE, Blocks.SPRUCE_FENCE_GATE, Blocks.BIRCH_FENCE_GATE, Blocks.JUNGLE_FENCE_GATE, Blocks.DARK_OAK_FENCE_GATE, Blocks.ACACIA_FENCE_GATE, Blocks.BREWING_STAND, Blocks.DISPENSER, Blocks.DROPPER, Blocks.LEVER, Blocks.NOTEBLOCK, Blocks.JUKEBOX, (Block)Blocks.BEACON, Blocks.BED, Blocks.FURNACE, (Block)Blocks.OAK_DOOR, (Block)Blocks.SPRUCE_DOOR, (Block)Blocks.BIRCH_DOOR, (Block)Blocks.JUNGLE_DOOR, (Block)Blocks.ACACIA_DOOR, (Block)Blocks.DARK_OAK_DOOR, Blocks.CAKE, Blocks.ENCHANTING_TABLE, Blocks.DRAGON_EGG, (Block)Blocks.HOPPER, Blocks.REPEATING_COMMAND_BLOCK, Blocks.COMMAND_BLOCK, Blocks.CHAIN_COMMAND_BLOCK, Blocks.CRAFTING_TABLE);
    }
}
