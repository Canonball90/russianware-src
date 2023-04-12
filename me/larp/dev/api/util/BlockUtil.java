//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.api.util;

import net.minecraft.util.math.RayTraceResult;
import java.math.RoundingMode;
import java.math.BigDecimal;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockDeadBush;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockAir;
import java.util.Collections;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.init.Blocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.block.Block;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.util.EnumHand;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class BlockUtil
{
    private static boolean unshift;
    public static final Vec3d[] antiDropOffsetList;
    public static final Vec3d[] platformOffsetList;
    public static final Vec3d[] legOffsetList;
    public static final Vec3d[] offsetList;
    public static final Vec3d[] antiStepOffsetList;
    public static final Vec3d[] antiScaffoldOffsetList;
    
    public static boolean placeBlock(final BlockPos pos) {
        final Block block = Minecraft.getMinecraft().world.getBlockState(pos).getBlock();
        final EnumFacing direction = calcSide(pos);
        if (direction == null) {
            return false;
        }
        final boolean activated = block.onBlockActivated((World)Minecraft.getMinecraft().world, pos, Minecraft.getMinecraft().world.getBlockState(pos), (EntityPlayer)Minecraft.getMinecraft().player, EnumHand.MAIN_HAND, direction, 0.0f, 0.0f, 0.0f);
        if (activated) {
            Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Minecraft.getMinecraft().player, CPacketEntityAction.Action.START_SNEAKING));
        }
        Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos.offset(direction), direction.getOpposite(), EnumHand.MAIN_HAND, 0.5f, 0.5f, 0.5f));
        Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
        if (activated || BlockUtil.unshift) {
            Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Minecraft.getMinecraft().player, CPacketEntityAction.Action.STOP_SNEAKING));
            BlockUtil.unshift = false;
        }
        return true;
    }
    
    public static EnumFacing calcSide(final BlockPos pos) {
        for (final EnumFacing side : EnumFacing.values()) {
            final IBlockState offsetState = Minecraft.getMinecraft().world.getBlockState(pos.offset(side));
            final boolean activated = offsetState.getBlock().onBlockActivated((World)Minecraft.getMinecraft().world, pos, offsetState, (EntityPlayer)Minecraft.getMinecraft().player, EnumHand.MAIN_HAND, side, 0.0f, 0.0f, 0.0f);
            if (activated) {
                Minecraft.getMinecraft().getConnection().sendPacket((Packet)new CPacketEntityAction((Entity)Minecraft.getMinecraft().player, CPacketEntityAction.Action.START_SNEAKING));
                BlockUtil.unshift = true;
            }
            if (offsetState.getBlock().canCollideCheck(offsetState, false) && !offsetState.getMaterial().isReplaceable()) {
                return side;
            }
        }
        return null;
    }
    
    public static boolean canPlaceCrystal(final BlockPos blockPos, final boolean check, final boolean newVer) {
        final BlockPos boost = blockPos.up();
        final BlockPos boost2 = boost.up();
        if (Minecraft.getMinecraft().world.getBlockState(blockPos).getBlock() != Blocks.BEDROCK && Minecraft.getMinecraft().world.getBlockState(blockPos).getBlock() != Blocks.OBSIDIAN) {
            return false;
        }
        if ((Minecraft.getMinecraft().world.getBlockState(boost2).getBlock() != Blocks.AIR && !newVer) || Minecraft.getMinecraft().world.getBlockState(boost).getBlock() != Blocks.AIR) {
            return false;
        }
        for (final Entity entity : Minecraft.getMinecraft().world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost))) {
            if (!entity.isDead) {
                if (entity instanceof EntityEnderCrystal) {
                    continue;
                }
                return false;
            }
        }
        if (check) {
            for (final Entity entity : Minecraft.getMinecraft().world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost2))) {
                if (!entity.isDead) {
                    if (entity instanceof EntityEnderCrystal) {
                        continue;
                    }
                    return false;
                }
            }
        }
        return true;
    }
    
    public static List<Vec3d> getUntrappedBlocksExtended(final int extension, final EntityPlayer player, final boolean antiScaffold, final boolean antiStep, final boolean legs, final boolean platform, final boolean antiDrop, final boolean raytrace) {
        final ArrayList<Vec3d> placeTargets = new ArrayList<Vec3d>();
        if (extension == 1) {
            placeTargets.addAll(targets(player.getPositionVector(), antiScaffold, antiStep, legs, platform, antiDrop, raytrace));
        }
        else {
            int extend = 1;
            final List<Vec3d> blockBlocks = getBlockBlocks((Entity)player);
            for (int size = blockBlocks.size(), i = 0; i < size; ++i) {
                final Vec3d vec3d = blockBlocks.get(i);
                if (extend > extension) {
                    break;
                }
                placeTargets.addAll(targets(vec3d, antiScaffold, antiStep, legs, platform, antiDrop, raytrace));
                ++extend;
            }
        }
        final ArrayList<Vec3d> removeList = new ArrayList<Vec3d>();
        for (final Vec3d vec3d2 : placeTargets) {
            final BlockPos pos = new BlockPos(vec3d2);
            if (isPositionPlaceable(pos, raytrace) != -1) {
                continue;
            }
            removeList.add(vec3d2);
        }
        for (final Vec3d vec3d2 : removeList) {
            placeTargets.remove(vec3d2);
        }
        return placeTargets;
    }
    
    public static boolean isTrapped(final EntityPlayer player, final boolean antiScaffold, final boolean antiStep, final boolean legs, final boolean platform, final boolean antiDrop) {
        return getUntrappedBlocks(player, antiScaffold, antiStep, legs, platform, antiDrop).size() == 0;
    }
    
    public static boolean isTrappedExtended(final int extension, final EntityPlayer player, final boolean antiScaffold, final boolean antiStep, final boolean legs, final boolean platform, final boolean antiDrop, final boolean raytrace) {
        return getUntrappedBlocksExtended(extension, player, antiScaffold, antiStep, legs, platform, antiDrop, raytrace).size() == 0;
    }
    
    public static List<Vec3d> getBlockBlocks(final Entity entity) {
        final ArrayList<Vec3d> vec3ds = new ArrayList<Vec3d>();
        final AxisAlignedBB bb = entity.getEntityBoundingBox();
        final double y = entity.posY;
        final double minX = round(bb.minX, 0);
        final double minZ = round(bb.minZ, 0);
        final double maxX = round(bb.maxX, 0);
        final double maxZ = round(bb.maxZ, 0);
        if (minX != maxX) {
            vec3ds.add(new Vec3d(minX, y, minZ));
            vec3ds.add(new Vec3d(maxX, y, minZ));
            if (minZ != maxZ) {
                vec3ds.add(new Vec3d(minX, y, maxZ));
                vec3ds.add(new Vec3d(maxX, y, maxZ));
                return vec3ds;
            }
        }
        else if (minZ != maxZ) {
            vec3ds.add(new Vec3d(minX, y, minZ));
            vec3ds.add(new Vec3d(minX, y, maxZ));
            return vec3ds;
        }
        vec3ds.add(entity.getPositionVector());
        return vec3ds;
    }
    
    public static Vec3d[] convertVec3ds(final Vec3d vec3d, final Vec3d[] input) {
        final Vec3d[] output = new Vec3d[input.length];
        for (int i = 0; i < input.length; ++i) {
            output[i] = vec3d.add(input[i]);
        }
        return output;
    }
    
    public static List<Vec3d> targets(final Vec3d vec3d, final boolean antiScaffold, final boolean antiStep, final boolean legs, final boolean platform, final boolean antiDrop, final boolean raytrace) {
        final ArrayList<Vec3d> placeTargets = new ArrayList<Vec3d>();
        if (antiDrop) {
            Collections.addAll(placeTargets, convertVec3ds(vec3d, BlockUtil.antiDropOffsetList));
        }
        if (platform) {
            Collections.addAll(placeTargets, convertVec3ds(vec3d, BlockUtil.platformOffsetList));
        }
        if (legs) {
            Collections.addAll(placeTargets, convertVec3ds(vec3d, BlockUtil.legOffsetList));
        }
        Collections.addAll(placeTargets, convertVec3ds(vec3d, BlockUtil.offsetList));
        Label_0248: {
            if (!antiStep) {
                final List<Vec3d> vec3ds = getUnsafeBlocksFromVec3d(vec3d, 2, false);
                if (vec3ds.size() == 4) {
                    for (int size = vec3ds.size(), i = 0; i < size; ++i) {
                        final Vec3d vector = vec3ds.get(i);
                        final BlockPos position = new BlockPos(vec3d).add(vector.x, vector.y, vector.z);
                        switch (isPositionPlaceable(position, raytrace)) {
                            case 0: {
                                break Label_0248;
                            }
                            case 3: {
                                placeTargets.add(vec3d.add(vector));
                                break;
                            }
                        }
                    }
                }
            }
            else {
                Collections.addAll(placeTargets, convertVec3ds(vec3d, BlockUtil.antiStepOffsetList));
            }
        }
        if (antiScaffold) {
            Collections.addAll(placeTargets, convertVec3ds(vec3d, BlockUtil.antiScaffoldOffsetList));
        }
        return placeTargets;
    }
    
    public static boolean canBlockBeSeen(final BlockPos pos) {
        return Minecraft.getMinecraft().world.rayTraceBlocks(new Vec3d(Minecraft.getMinecraft().player.posX, Minecraft.getMinecraft().player.posY + Minecraft.getMinecraft().player.getEyeHeight(), Minecraft.getMinecraft().player.posZ), new Vec3d((double)pos.getX(), pos.getY() + (double)Minecraft.getMinecraft().player.getEyeHeight(), Minecraft.getMinecraft().player.posZ), false, true, false) == null;
    }
    
    public static List<BlockPos> getSphere(final float radius, final boolean ignoreAir) {
        final ArrayList<BlockPos> sphere = new ArrayList<BlockPos>();
        final BlockPos pos = new BlockPos(Minecraft.getMinecraft().player.getPositionVector());
        final int posX = pos.getX();
        final int posY = pos.getY();
        final int posZ = pos.getZ();
        final int radiuss = (int)radius;
        for (int x = posX - radiuss; x <= posX + radius; ++x) {
            for (int z = posZ - radiuss; z <= posZ + radius; ++z) {
                for (int y = posY - radiuss; y < posY + radius; ++y) {
                    final double dist = (posX - x) * (posX - x) + (posZ - z) * (posZ - z) + (posY - y) * (posY - y);
                    final BlockPos position;
                    if (dist < radius * radius && (Minecraft.getMinecraft().world.getBlockState(position = new BlockPos(x, y, z)).getBlock() != Blocks.AIR || !ignoreAir)) {
                        sphere.add(position);
                    }
                }
            }
        }
        return sphere;
    }
    
    public static List<Vec3d> getUnsafeBlocks(final Entity entity, final int height, final boolean floor) {
        return getUnsafeBlocksFromVec3d(entity.getPositionVector(), height, floor);
    }
    
    public static List<EnumFacing> getPossibleSides(final BlockPos pos) {
        final ArrayList<EnumFacing> facings = new ArrayList<EnumFacing>();
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbour = pos.offset(side);
            if (Minecraft.getMinecraft().world.getBlockState(neighbour).getBlock().canCollideCheck(Minecraft.getMinecraft().world.getBlockState(neighbour), false)) {
                final IBlockState blockState;
                if (!(blockState = Minecraft.getMinecraft().world.getBlockState(neighbour)).getMaterial().isReplaceable()) {
                    facings.add(side);
                }
            }
        }
        return facings;
    }
    
    public static Vec3d[] getHelpingBlocks(final Vec3d vec3d) {
        return new Vec3d[] { new Vec3d(vec3d.x, vec3d.y - 1.0, vec3d.z), new Vec3d((vec3d.x != 0.0) ? (vec3d.x * 2.0) : vec3d.x, vec3d.y, (vec3d.x != 0.0) ? vec3d.z : (vec3d.z * 2.0)), new Vec3d((vec3d.x == 0.0) ? (vec3d.x + 1.0) : vec3d.x, vec3d.y, (vec3d.x == 0.0) ? vec3d.z : (vec3d.z + 1.0)), new Vec3d((vec3d.x == 0.0) ? (vec3d.x - 1.0) : vec3d.x, vec3d.y, (vec3d.x == 0.0) ? vec3d.z : (vec3d.z - 1.0)), new Vec3d(vec3d.x, vec3d.y + 1.0, vec3d.z) };
    }
    
    public static BlockPos[] toBlockPos(final Vec3d[] vec3ds) {
        final BlockPos[] list = new BlockPos[vec3ds.length];
        for (int i = 0; i < vec3ds.length; ++i) {
            list[i] = new BlockPos(vec3ds[i]);
        }
        return list;
    }
    
    public static Vec3d[] getUnsafeBlockArray(final Vec3d vec3d, final int height, final boolean floor) {
        final List<Vec3d> list = getUnsafeBlocksFromVec3d(vec3d, height, floor);
        final Vec3d[] array = new Vec3d[list.size()];
        return list.toArray(array);
    }
    
    public static boolean isSafe(final Entity entity, final int height, final boolean floor) {
        return getUnsafeBlocks(entity, height, floor).size() == 0;
    }
    
    public static boolean areVec3dsAligned(final Vec3d vec3d1, final Vec3d vec3d2) {
        return areVec3dsAlignedRetarded(vec3d1, vec3d2);
    }
    
    public static boolean areVec3dsAlignedRetarded(final Vec3d vec3d1, final Vec3d vec3d2) {
        final BlockPos pos1 = new BlockPos(vec3d1);
        final BlockPos pos2 = new BlockPos(vec3d2.x, vec3d1.y, vec3d2.z);
        return pos1.equals((Object)pos2);
    }
    
    public static int isPositionPlaceable(final BlockPos pos, final boolean entityCheck) {
        final Block block = Minecraft.getMinecraft().world.getBlockState(pos).getBlock();
        if (!(block instanceof BlockAir) && !(block instanceof BlockLiquid) && !(block instanceof BlockTallGrass) && !(block instanceof BlockFire) && !(block instanceof BlockDeadBush) && !(block instanceof BlockSnow)) {
            return 0;
        }
        if (entityCheck) {
            final List entitiesWithinAABB = Minecraft.getMinecraft().world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(pos));
            for (int size = entitiesWithinAABB.size(), i = 0; i < size; ++i) {
                final Entity entity = entitiesWithinAABB.get(i);
                if (!(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb)) {
                    return 1;
                }
            }
        }
        final List<EnumFacing> possibleSides = getPossibleSides(pos);
        for (int size = possibleSides.size(), i = 0; i < size; ++i) {
            final EnumFacing side = possibleSides.get(i);
            if (canBeClicked(pos.offset(side))) {
                return 3;
            }
        }
        return 2;
    }
    
    public static boolean canBeClicked(final BlockPos pos) {
        return Minecraft.getMinecraft().world.getBlockState(pos).getBlock().canCollideCheck(Minecraft.getMinecraft().world.getBlockState(pos), false);
    }
    
    public static BlockPos getRoundedBlockPos(final Entity entity) {
        return new BlockPos(roundVec(entity.getPositionVector(), 0));
    }
    
    public static Vec3d roundVec(final Vec3d vec3d, final int places) {
        return new Vec3d(round(vec3d.x, places), round(vec3d.y, places), round(vec3d.z, places));
    }
    
    public static double round(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.FLOOR);
        return bd.doubleValue();
    }
    
    public static List<Vec3d> getUntrappedBlocks(final EntityPlayer player, final boolean antiScaffold, final boolean antiStep, final boolean legs, final boolean platform, final boolean antiDrop) {
        final ArrayList<Vec3d> vec3ds = new ArrayList<Vec3d>();
        if (!antiStep && getUnsafeBlocks((Entity)player, 2, false).size() == 4) {
            vec3ds.addAll(getUnsafeBlocks((Entity)player, 2, false));
        }
        for (int i = 0; i < getTrapOffsets(antiScaffold, antiStep, legs, platform, antiDrop).length; ++i) {
            final Vec3d vector = getTrapOffsets(antiScaffold, antiStep, legs, platform, antiDrop)[i];
            final BlockPos targetPos = new BlockPos(player.getPositionVector()).add(vector.x, vector.y, vector.z);
            final Block block = Minecraft.getMinecraft().world.getBlockState(targetPos).getBlock();
            if (block instanceof BlockAir || block instanceof BlockLiquid || block instanceof BlockTallGrass || block instanceof BlockFire || block instanceof BlockDeadBush || block instanceof BlockSnow) {
                vec3ds.add(vector);
            }
        }
        return vec3ds;
    }
    
    public static List<Vec3d> getUnsafeBlocksFromVec3d(final Vec3d pos, final int height, final boolean floor) {
        final ArrayList<Vec3d> vec3ds = new ArrayList<Vec3d>();
        for (final Vec3d vector : getOffsets(height, floor)) {
            final BlockPos targetPos = new BlockPos(pos).add(vector.x, vector.y, vector.z);
            final Block block = Minecraft.getMinecraft().world.getBlockState(targetPos).getBlock();
            if (block instanceof BlockAir || block instanceof BlockLiquid || block instanceof BlockTallGrass || block instanceof BlockFire || block instanceof BlockDeadBush || block instanceof BlockSnow) {
                vec3ds.add(vector);
            }
        }
        return vec3ds;
    }
    
    public static List<Vec3d> getOffsetList(final int y, final boolean floor) {
        final ArrayList<Vec3d> offsets = new ArrayList<Vec3d>();
        offsets.add(new Vec3d(-1.0, (double)y, 0.0));
        offsets.add(new Vec3d(1.0, (double)y, 0.0));
        offsets.add(new Vec3d(0.0, (double)y, -1.0));
        offsets.add(new Vec3d(0.0, (double)y, 1.0));
        if (floor) {
            offsets.add(new Vec3d(0.0, (double)(y - 1), 0.0));
        }
        return offsets;
    }
    
    public static Vec3d[] getOffsets(final int y, final boolean floor) {
        final List<Vec3d> offsets = getOffsetList(y, floor);
        final Vec3d[] array = new Vec3d[offsets.size()];
        return offsets.toArray(array);
    }
    
    public static Vec3d[] getTrapOffsets(final boolean antiScaffold, final boolean antiStep, final boolean legs, final boolean platform, final boolean antiDrop) {
        final List<Vec3d> offsets = getTrapOffsetsList(antiScaffold, antiStep, legs, platform, antiDrop);
        final Vec3d[] array = new Vec3d[offsets.size()];
        return offsets.toArray(array);
    }
    
    public static List<Vec3d> getTrapOffsetsList(final boolean antiScaffold, final boolean antiStep, final boolean legs, final boolean platform, final boolean antiDrop) {
        final ArrayList<Vec3d> offsets = new ArrayList<Vec3d>(getOffsetList(1, false));
        offsets.add(new Vec3d(0.0, 2.0, 0.0));
        if (antiScaffold) {
            offsets.add(new Vec3d(0.0, 3.0, 0.0));
        }
        if (antiStep) {
            offsets.addAll(getOffsetList(2, false));
        }
        if (legs) {
            offsets.addAll(getOffsetList(0, false));
        }
        if (platform) {
            offsets.addAll(getOffsetList(-1, false));
            offsets.add(new Vec3d(0.0, -1.0, 0.0));
        }
        if (antiDrop) {
            offsets.add(new Vec3d(0.0, -2.0, 0.0));
        }
        return offsets;
    }
    
    public static void placeCrystalOnBlock(final BlockPos pos, final EnumHand hand, final boolean swing) {
        final RayTraceResult result = Minecraft.getMinecraft().world.rayTraceBlocks(new Vec3d(Minecraft.getMinecraft().player.posX, Minecraft.getMinecraft().player.posY + Minecraft.getMinecraft().player.getEyeHeight(), Minecraft.getMinecraft().player.posZ), new Vec3d(pos.getX() + 0.5, pos.getY() - 0.5, pos.getZ() + 0.5));
        final EnumFacing facing = (result == null || result.sideHit == null) ? EnumFacing.UP : result.sideHit;
        Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, facing, hand, 0.0f, 0.0f, 0.0f));
        if (swing) {
            Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketAnimation(hand));
        }
    }
    
    public static boolean rayTracePlaceCheck(final BlockPos pos, final boolean shouldCheck, final float height) {
        return !shouldCheck || Minecraft.getMinecraft().world.rayTraceBlocks(new Vec3d(Minecraft.getMinecraft().player.posX, Minecraft.getMinecraft().player.posY + Minecraft.getMinecraft().player.getEyeHeight(), Minecraft.getMinecraft().player.posZ), new Vec3d((double)pos.getX(), (double)(pos.getY() + height), (double)pos.getZ()), false, true, false) == null;
    }
    
    public static boolean rayTracePlaceCheck(final BlockPos pos, final boolean shouldCheck) {
        return rayTracePlaceCheck(pos, shouldCheck, 1.0f);
    }
    
    public static boolean canSeeBlock(final BlockPos p_Pos) {
        return Minecraft.getMinecraft().player != null && Minecraft.getMinecraft().world.rayTraceBlocks(new Vec3d(Minecraft.getMinecraft().player.posX, Minecraft.getMinecraft().player.posY + Minecraft.getMinecraft().player.getEyeHeight(), Minecraft.getMinecraft().player.posZ), new Vec3d((double)p_Pos.getX(), (double)p_Pos.getY(), (double)p_Pos.getZ()), false, true, false) == null;
    }
    
    public static void placeCrystalOnBlock(final BlockPos pos, final EnumHand hand) {
        final RayTraceResult result = Minecraft.getMinecraft().world.rayTraceBlocks(new Vec3d(Minecraft.getMinecraft().player.posX, Minecraft.getMinecraft().player.posY + Minecraft.getMinecraft().player.getEyeHeight(), Minecraft.getMinecraft().player.posZ), new Vec3d(pos.getX() + 0.5, pos.getY() - 0.5, pos.getZ() + 0.5));
        final EnumFacing facing = (result == null || result.sideHit == null) ? EnumFacing.UP : result.sideHit;
        Minecraft.getMinecraft().player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, facing, hand, 0.0f, 0.0f, 0.0f));
    }
    
    static {
        BlockUtil.unshift = false;
        antiDropOffsetList = new Vec3d[] { new Vec3d(0.0, -2.0, 0.0) };
        platformOffsetList = new Vec3d[] { new Vec3d(0.0, -1.0, 0.0), new Vec3d(0.0, -1.0, -1.0), new Vec3d(0.0, -1.0, 1.0), new Vec3d(-1.0, -1.0, 0.0), new Vec3d(1.0, -1.0, 0.0) };
        legOffsetList = new Vec3d[] { new Vec3d(-1.0, 0.0, 0.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, -1.0), new Vec3d(0.0, 0.0, 1.0) };
        offsetList = new Vec3d[] { new Vec3d(1.0, 1.0, 0.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(0.0, 2.0, 0.0) };
        antiStepOffsetList = new Vec3d[] { new Vec3d(-1.0, 2.0, 0.0), new Vec3d(1.0, 2.0, 0.0), new Vec3d(0.0, 2.0, 1.0), new Vec3d(0.0, 2.0, -1.0) };
        antiScaffoldOffsetList = new Vec3d[] { new Vec3d(0.0, 3.0, 0.0) };
    }
}
