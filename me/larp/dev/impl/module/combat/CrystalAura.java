//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.impl.module.combat;

import net.minecraft.block.state.IBlockState;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;
import java.util.Collection;
import net.minecraft.util.NonNullList;
import net.minecraft.client.Minecraft;
import java.util.ArrayList;
import net.minecraft.util.EnumFacing;
import java.util.Iterator;
import me.larp.dev.api.util.BlockUtil;
import me.larp.dev.api.util.ItemUtil;
import net.minecraft.init.Items;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.Vec3d;
import me.larp.dev.api.util.crystal.PlayerUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.List;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.init.Blocks;
import java.awt.Color;
import me.larp.dev.api.clickgui.util.RenderUtil;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraft.network.Packet;
import me.larp.dev.mixin.accessor.ICPacketUseEntity;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.server.SPacketSpawnObject;
import me.larp.dev.impl.event.PacketReceiveEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.Comparator;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.Arrays;
import me.larp.dev.api.module.Category;
import me.larp.dev.api.util.Timer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.item.EntityEnderCrystal;
import me.larp.dev.api.setting.Setting;
import me.larp.dev.api.module.Module;

public class CrystalAura extends Module
{
    Setting renderMode;
    Setting render;
    Setting rotate;
    Setting predick;
    Setting place;
    Setting placeDelay;
    Setting breakDelay;
    Setting minDmg;
    Setting maxSelf;
    Setting range;
    EntityEnderCrystal nearestCrystal;
    BlockPos pos;
    Timer placeTimer;
    Timer breakTimer;
    
    public CrystalAura() {
        super("AutoCrystal", "", Category.COMBAT);
        this.renderMode = new Setting("RenderMode", this, Arrays.asList("Top", "Full"));
        this.render = new Setting("Render", this, true);
        this.rotate = new Setting("Rotate", this, true);
        this.predick = new Setting("Predick", this, true);
        this.place = new Setting("Place", this, true);
        this.placeDelay = new Setting("PlaceDelay", this, 0, 0, 20);
        this.breakDelay = new Setting("BreakDelay", this, 1, 0, 20);
        this.minDmg = new Setting("MinDmg", this, 8, 0, 36);
        this.maxSelf = new Setting("MaxSelfDmg", this, 8, 0, 36);
        this.range = new Setting("Range", this, 55, 10, 80);
        this.pos = null;
        this.placeTimer = new Timer();
        this.breakTimer = new Timer();
    }
    
    @SubscribeEvent
    public void onUpdate(final TickEvent.ClientTickEvent event) {
        if (this.nullCheck()) {
            this.disable();
            return;
        }
        this.nearestCrystal = (EntityEnderCrystal)this.mc.world.loadedEntityList.stream().filter(entity -> entity instanceof EntityEnderCrystal).filter(entity -> this.mc.player.getDistance(entity) <= (double)(this.range.getIntegerValue() / 10)).map(entity -> entity).min(Comparator.comparing(crystal -> this.mc.player.getDistance(crystal))).orElse(null);
        this.doCrystalAura();
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketReceiveEvent event) {
        if (!this.predick.getBooleanValue()) {
            return;
        }
        if (event.getPacket() instanceof SPacketSpawnObject) {
            final SPacketSpawnObject packet2 = (SPacketSpawnObject)event.getPacket();
            if (packet2.getType() == 51) {
                final CPacketUseEntity useEntity = new CPacketUseEntity();
                ((ICPacketUseEntity)useEntity).setEntityId(packet2.getEntityID());
                ((ICPacketUseEntity)useEntity).setAction(CPacketUseEntity.Action.ATTACK);
                this.mc.getConnection().sendPacket((Packet)useEntity);
            }
        }
    }
    
    @SubscribeEvent
    public void onRender(final RenderWorldLastEvent event) {
        if (this.nullCheck()) {
            return;
        }
        if (this.render.getBooleanValue() && this.nearestCrystal != null) {
            final Color r = new Color(RenderUtil.getRGB(6.0f, 1.0f, 0.6f));
            final BlockPos pos = this.nearestCrystal.getPosition().add(0, -1, 0);
            if (this.mc.world.getBlockState(pos).getBlock() != Blocks.OBSIDIAN && this.mc.world.getBlockState(pos).getBlock() != Blocks.BEDROCK) {
                return;
            }
            AxisAlignedBB bb;
            if (this.renderMode.getEnumValue().equals("Full")) {
                bb = RenderUtil.generateBB(pos.getX(), pos.getY(), pos.getZ());
            }
            else {
                bb = RenderUtil.generateBBTop(pos.getX(), pos.getY(), pos.getZ());
            }
            RenderUtil.drawBoxOutline(bb, r.getRed() / 255.0f, r.getGreen() / 255.0f, r.getBlue() / 255.0f, 0.5f, 0.15f);
        }
    }
    
    private void doCrystalAura() {
        double dmg = 0.5;
        final List<EntityPlayer> entities = (List<EntityPlayer>)this.mc.world.playerEntities.stream().filter(entityPlayer -> entityPlayer != this.mc.player).collect(Collectors.toList());
        for (final EntityPlayer entity2 : entities) {
            if (entity2.getHealth() > 0.0f) {
                if (this.mc.player.getDistance((Entity)entity2) > (double)(this.range.getIntegerValue() / 10)) {
                    continue;
                }
                for (final BlockPos blockPos : possiblePlacePositions((float)(this.range.getIntegerValue() / 10), true)) {
                    final double d = PlayerUtil.calcDmg(blockPos, entity2);
                    if ((d >= this.minDmg.getIntegerValue() || entity2.getHealth() + entity2.getAbsorptionAmount() <= 4.0f) && d > dmg) {
                        if (PlayerUtil.calcDmg(blockPos, (EntityPlayer)this.mc.player) > this.maxSelf.getIntegerValue()) {
                            continue;
                        }
                        dmg = d;
                        this.pos = blockPos;
                    }
                }
            }
        }
        if (dmg == 0.5) {
            return;
        }
        if (this.nearestCrystal != null && this.breakTimer.hasTimeElapsed(this.breakDelay.getIntegerValue() * 50, true)) {
            final EnumFacing side = getFirstFacing(this.nearestCrystal.getPosition());
            final BlockPos neighbour = this.nearestCrystal.getPosition().offset(side);
            final EnumFacing opposite = side.getOpposite();
            final Vec3d hitVec = new Vec3d((Vec3i)neighbour).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
            if (this.rotate.getBooleanValue()) {
                faceVector(hitVec, false);
            }
            this.mc.player.swingArm(EnumHand.OFF_HAND);
            this.mc.playerController.attackEntity((EntityPlayer)this.mc.player, (Entity)this.nearestCrystal);
        }
        if (this.place.getBooleanValue()) {
            if (ItemUtil.getHotbarItemSlot(Items.END_CRYSTAL) == -1) {
                return;
            }
            ItemUtil.switchToSlot(ItemUtil.getHotbarItemSlot(Items.END_CRYSTAL));
            final boolean mainhand = this.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL;
            final boolean offhand = this.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL;
            this.mc.playerController.updateController();
            if (offhand || mainhand) {
                final EnumFacing side2 = getFirstFacing(this.pos);
                final BlockPos neighbour2 = this.pos.offset(side2);
                final EnumFacing opposite2 = side2.getOpposite();
                final Vec3d hitVec2 = new Vec3d((Vec3i)neighbour2).add(new Vec3d(opposite2.getDirectionVec()).scale(0.5));
                if (this.rotate.getBooleanValue()) {
                    faceVector(hitVec2, false);
                }
                if (this.placeTimer.hasTimeElapsed(this.placeDelay.getIntegerValue() * 50, true)) {
                    BlockUtil.placeCrystalOnBlock(this.pos, EnumHand.MAIN_HAND);
                }
            }
        }
    }
    
    private static List<BlockPos> getSphere(final BlockPos loc, final float r, final int h, final boolean hollow, final boolean sphere, final int plusY) {
        final List<BlockPos> circleblocks = new ArrayList<BlockPos>();
        final int cx = loc.getX();
        final int cy = loc.getY();
        final int cz = loc.getZ();
        for (int x = cx - (int)r; x <= cx + r; ++x) {
            for (int z = cz - (int)r; z <= cz + r; ++z) {
                for (int y = sphere ? (cy - (int)r) : (cy - h); y < (sphere ? (cy + r) : ((float)(cy + h))); ++y) {
                    final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0);
                    if (dist < r * r && (!hollow || dist >= (r - 1.0f) * (r - 1.0f))) {
                        final BlockPos l = new BlockPos(x, y + plusY, z);
                        circleblocks.add(l);
                    }
                }
            }
        }
        return circleblocks;
    }
    
    private static BlockPos getPlayerPos() {
        final Minecraft mc = Minecraft.getMinecraft();
        return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
    }
    
    private static List<BlockPos> possiblePlacePositions(final float placeRange, final boolean specialEntityCheck) {
        final NonNullList<BlockPos> positions = (NonNullList<BlockPos>)NonNullList.create();
        positions.addAll((Collection)getSphere(getPlayerPos(), placeRange, (int)placeRange, false, true, 0).stream().filter(pos -> canPlaceCrystal(pos, specialEntityCheck)).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
        return (List<BlockPos>)positions;
    }
    
    private static boolean canPlaceCrystal(final BlockPos blockPos, final boolean specialEntityCheck) {
        final Minecraft mc = Minecraft.getMinecraft();
        final BlockPos boost = blockPos.add(0, 1, 0);
        final BlockPos boost2 = blockPos.add(0, 2, 0);
        try {
            if (mc.world.getBlockState(blockPos).getBlock() != Blocks.BEDROCK && mc.world.getBlockState(blockPos).getBlock() != Blocks.OBSIDIAN) {
                return false;
            }
            if (mc.world.getBlockState(boost).getBlock() != Blocks.AIR || mc.world.getBlockState(boost2).getBlock() != Blocks.AIR) {
                return false;
            }
            if (!specialEntityCheck) {
                return mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost)).isEmpty() && mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost2)).isEmpty();
            }
            for (final Entity entity : mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost))) {
                if (entity instanceof EntityEnderCrystal) {
                    continue;
                }
                return false;
            }
            for (final Entity entity : mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost2))) {
                if (entity instanceof EntityEnderCrystal) {
                    continue;
                }
                return false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public static Vec3d getEyesPos() {
        final Minecraft mc = Minecraft.getMinecraft();
        return new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ);
    }
    
    private static float[] getLegitRotations(final Vec3d vec) {
        final Minecraft mc = Minecraft.getMinecraft();
        final Vec3d eyesPos = getEyesPos();
        final double diffX = vec.x - eyesPos.x;
        final double diffY = vec.y - eyesPos.y;
        final double diffZ = vec.z - eyesPos.z;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        final float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[] { mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - mc.player.rotationYaw), mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - mc.player.rotationPitch) };
    }
    
    private static void faceVector(final Vec3d vec, final boolean normalizeAngle) {
        final Minecraft mc = Minecraft.getMinecraft();
        final float[] rotations = getLegitRotations(vec);
        mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(rotations[0], normalizeAngle ? ((float)MathHelper.normalizeAngle((int)rotations[1], 360)) : rotations[1], mc.player.onGround));
    }
    
    private static EnumFacing getFirstFacing(final BlockPos pos) {
        final Iterator<EnumFacing> iterator = getPossibleSides(pos).iterator();
        if (iterator.hasNext()) {
            final EnumFacing facing = iterator.next();
            return facing;
        }
        return null;
    }
    
    private static List<EnumFacing> getPossibleSides(final BlockPos pos) {
        final Minecraft mc = Minecraft.getMinecraft();
        final List<EnumFacing> facings = new ArrayList<EnumFacing>();
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbour = pos.offset(side);
            if (mc.world.getBlockState(neighbour).getBlock().canCollideCheck(mc.world.getBlockState(neighbour), false)) {
                final IBlockState blockState = mc.world.getBlockState(neighbour);
                if (!blockState.getMaterial().isReplaceable()) {
                    facings.add(side);
                }
            }
        }
        return facings;
    }
}
