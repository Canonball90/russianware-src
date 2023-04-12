//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.impl.module.combat;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.init.Blocks;
import java.util.Collection;
import java.util.function.Predicate;
import net.minecraft.util.NonNullList;
import me.larp.dev.api.util.RenderUtil;
import java.awt.Color;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.RayTraceResult;
import java.util.Iterator;
import java.util.List;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import me.larp.dev.Client;
import net.minecraft.init.Items;
import net.minecraft.util.EnumHand;
import java.util.Comparator;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import me.larp.dev.mixin.accessor.ICPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayer;
import me.larp.dev.impl.event.PacketEvent;
import java.util.Objects;
import net.minecraft.potion.Potion;
import net.minecraft.util.math.MathHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.util.CombatRules;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.world.Explosion;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.Entity;
import java.util.Arrays;
import me.larp.dev.api.module.Category;
import net.minecraft.util.math.BlockPos;
import java.util.ArrayList;
import me.larp.dev.api.setting.Setting;
import me.larp.dev.api.module.Module;

public class AutoCrystal2b2t extends Module
{
    private final Setting attackSpeed;
    private final Setting placeSpeed;
    private final Setting placeRange;
    private final Setting attackRange;
    private final Setting minDamage;
    private final Setting enemyRange;
    private final Setting silentSwitch;
    private final Setting multiPlace;
    private final Setting rotate;
    private final Setting onlyOwn;
    private final Setting facePlaceHealth;
    private final Setting itemSwitch;
    private final Setting color;
    private final Setting red;
    private final Setting green;
    private final Setting blue;
    private final Setting alpha;
    private final Setting rainbowSpeed;
    private final ArrayList<BlockPos> ownCrystals;
    private BlockPos render;
    private long placeSystemTime;
    private long breakSystemTime;
    private long multiPlaceSystemTime;
    private long antiStuckSystemTime;
    private boolean togglePitch;
    private boolean switchCooldown;
    private boolean isSpoofingAngles;
    private double yaw;
    private double pitch;
    private float hue;
    
    public AutoCrystal2b2t() {
        super("AutoCrystal2b2t", Category.COMBAT);
        this.attackSpeed = new Setting("AttackSpeed", this, 17, 0, 20);
        this.placeSpeed = new Setting("PlaceSpeed", this, 18, 0, 20);
        this.placeRange = new Setting("PlaceRange", this, 5, 1, 10);
        this.attackRange = new Setting("AttackRange", this, 4, 1, 10);
        this.minDamage = new Setting("MinDamage", this, 4, 0, 16);
        this.enemyRange = new Setting("EnemyRange", this, 9, 1, 20);
        this.silentSwitch = new Setting("SilentSwitch", this, true);
        this.multiPlace = new Setting("MultiPlace", this, true);
        this.rotate = new Setting("Rotate", this, true);
        this.onlyOwn = new Setting("OnlyOwn", this, true);
        this.facePlaceHealth = new Setting("FacePlaceHealth", this, 7, 0, 36);
        this.itemSwitch = new Setting("ItemSwitch", this, true);
        this.color = new Setting("Color", this, Arrays.asList("Static", "Rainbow"));
        this.red = new Setting("Red", this, 255, 0, 255);
        this.green = new Setting("Green", this, 20, 0, 255);
        this.blue = new Setting("Blue", this, 20, 0, 255);
        this.alpha = new Setting("Alpha", this, 100, 0, 255);
        this.rainbowSpeed = new Setting("RainbowSpeed", this, 5, 0, 10);
        this.ownCrystals = new ArrayList<BlockPos>();
        this.placeSystemTime = -1L;
        this.breakSystemTime = -1L;
        this.multiPlaceSystemTime = -1L;
        this.antiStuckSystemTime = -1L;
        this.togglePitch = false;
        this.switchCooldown = false;
        this.hue = 0.0f;
    }
    
    public float calculateDamage(final double posX, final double posY, final double posZ, final Entity entity) {
        final double size = entity.getDistance(posX, posY, posZ) / 12.0;
        final Vec3d vec3d = new Vec3d(posX, posY, posZ);
        final double blockDensity = entity.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
        final double v = (1.0 - size) * blockDensity;
        final float damage = (float)(int)((v * v + v) / 2.0 * 7.0 * 12.0 + 1.0);
        double finals = 1.0;
        if (entity instanceof EntityLivingBase) {
            finals = this.getBlastReduction((EntityLivingBase)entity, this.getDamageMultiplied(damage), new Explosion((World)this.mc.world, (Entity)this.mc.player, posX, posY, posZ, 6.0f, false, true));
        }
        return (float)finals;
    }
    
    public float getBlastReduction(final EntityLivingBase entity, final float damage, final Explosion explosion) {
        float d = damage;
        if (entity instanceof EntityPlayer) {
            final EntityPlayer ep = (EntityPlayer)entity;
            final DamageSource ds = DamageSource.causeExplosionDamage(explosion);
            d = CombatRules.getDamageAfterAbsorb(d, (float)ep.getTotalArmorValue(), (float)ep.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
            final int k = EnchantmentHelper.getEnchantmentModifierDamage(ep.getArmorInventoryList(), ds);
            final float f = MathHelper.clamp((float)k, 0.0f, 20.0f);
            d *= 1.0f - f / 25.0f;
            if (entity.isPotionActive((Potion)Objects.requireNonNull(Potion.getPotionById(11)))) {
                d -= d / 4.0f;
            }
        }
        else {
            d = CombatRules.getDamageAfterAbsorb(d, (float)entity.getTotalArmorValue(), (float)entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
        }
        return d;
    }
    
    private float getDamageMultiplied(final float damage) {
        final int diff = this.mc.world.getDifficulty().getId();
        return damage * ((diff == 0) ? 0.0f : ((diff == 2) ? 1.0f : ((diff == 1) ? 0.5f : 1.5f)));
    }
    
    public boolean canBlockBeSeen(final BlockPos blockPos) {
        return this.mc.world.rayTraceBlocks(new Vec3d(this.mc.player.posX, this.mc.player.posY + this.mc.player.getEyeHeight(), this.mc.player.posZ), new Vec3d((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ()), false, true, false) == null;
    }
    
    @SubscribeEvent
    public void onPacket(final PacketEvent.Send event) {
        if (this.mc.player == null || this.mc.world == null) {
            return;
        }
        if (event.getPacket() instanceof CPacketPlayer && this.isSpoofingAngles) {
            ((ICPacketPlayer)event.getPacket()).setYaw((float)this.yaw);
            ((ICPacketPlayer)event.getPacket()).setPitch((float)this.pitch);
        }
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.mc.player == null || this.mc.world == null) {
            return;
        }
        final EntityEnderCrystal crystal = (EntityEnderCrystal)this.mc.world.loadedEntityList.stream().filter(entity -> entity instanceof EntityEnderCrystal).min(Comparator.comparing(c -> this.mc.player.getDistance(c))).orElse(null);
        if (crystal != null && this.mc.player.getDistance((Entity)crystal) <= this.attackRange.getIntegerValue()) {
            if (System.nanoTime() / 1000000L - this.breakSystemTime >= 420 - this.attackSpeed.getIntegerValue() * 20 && (!this.onlyOwn.getBooleanValue() || this.ownCrystals.contains(this.render))) {
                this.lookAtPacket(crystal.posX, crystal.posY, crystal.posZ, (EntityPlayer)this.mc.player);
                this.mc.playerController.attackEntity((EntityPlayer)this.mc.player, (Entity)crystal);
                this.mc.player.swingArm(EnumHand.MAIN_HAND);
                this.ownCrystals.remove(this.render);
                this.breakSystemTime = System.nanoTime() / 1000000L;
            }
            if (this.multiPlace.getBooleanValue()) {
                if (System.nanoTime() / 1000000L - this.multiPlaceSystemTime >= 20 * this.placeSpeed.getIntegerValue() && System.nanoTime() / 1000000L - this.antiStuckSystemTime <= 400 + (400 - this.attackSpeed.getIntegerValue() * 20)) {
                    this.multiPlaceSystemTime = System.nanoTime() / 1000000L;
                    return;
                }
            }
            else if (System.nanoTime() / 1000000L - this.antiStuckSystemTime <= 400 + (400 - this.attackSpeed.getIntegerValue() * 20)) {
                return;
            }
        }
        else {
            this.resetRotation();
        }
        int crystalSlot = (this.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL) ? this.mc.player.inventory.currentItem : -1;
        if (crystalSlot == -1) {
            for (int l = 0; l < 9; ++l) {
                if (this.mc.player.inventory.getStackInSlot(l).getItem() == Items.END_CRYSTAL) {
                    crystalSlot = l;
                    break;
                }
            }
        }
        boolean offhand = false;
        if (this.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
            offhand = true;
        }
        else if (crystalSlot == -1) {
            return;
        }
        BlockPos finalPos = null;
        final List<BlockPos> blocks = this.findCrystalBlocks();
        final ArrayList<Entity> entities = (ArrayList<Entity>)this.mc.world.playerEntities.stream().filter(entityPlayer -> !Client.friendManager.isFriend(entityPlayer.getName())).collect(Collectors.toCollection(ArrayList::new));
        double damage = 0.5;
        for (final Entity entity2 : entities) {
            if (entity2 != this.mc.player && ((EntityLivingBase)entity2).getHealth() > 0.0f && this.mc.player.getDistanceSq(entity2) <= this.enemyRange.getIntegerValue() * this.enemyRange.getIntegerValue()) {
                for (final BlockPos blockPos : blocks) {
                    if (this.canBlockBeSeen(blockPos) || this.mc.player.getDistanceSq(blockPos) <= 25.0) {
                        final double b = entity2.getDistanceSq(blockPos);
                        if (b > 56.2) {
                            continue;
                        }
                        final double d = this.calculateDamage(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5, entity2);
                        if ((d < this.minDamage.getIntegerValue() && ((EntityLivingBase)entity2).getHealth() + ((EntityLivingBase)entity2).getAbsorptionAmount() > this.facePlaceHealth.getIntegerValue()) || d <= damage) {
                            continue;
                        }
                        final double self = this.calculateDamage(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5, (Entity)this.mc.player);
                        if (this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount() - self <= 7.0 || self > d) {
                            continue;
                        }
                        damage = d;
                        finalPos = blockPos;
                    }
                }
            }
        }
        if (damage == 0.5) {
            this.render = null;
            this.resetRotation();
            return;
        }
        this.render = finalPos;
        if (!offhand && this.mc.player.inventory.currentItem != crystalSlot) {
            if (this.itemSwitch.getBooleanValue()) {
                this.mc.player.inventory.currentItem = crystalSlot;
                this.resetRotation();
                this.switchCooldown = true;
            }
            return;
        }
        this.lookAtPacket(finalPos.getX() + 0.5, finalPos.getY() - 0.5, finalPos.getZ() + 0.5, (EntityPlayer)this.mc.player);
        final RayTraceResult result = this.mc.world.rayTraceBlocks(new Vec3d(this.mc.player.posX, this.mc.player.posY + this.mc.player.getEyeHeight(), this.mc.player.posZ), new Vec3d(finalPos.getX() + 0.5, finalPos.getY() - 0.5, finalPos.getZ() + 0.5));
        EnumFacing f;
        if (result != null && result.sideHit != null) {
            f = result.sideHit;
        }
        else {
            f = EnumFacing.UP;
        }
        if (this.switchCooldown) {
            this.switchCooldown = false;
            return;
        }
        if (System.nanoTime() / 1000000L - this.placeSystemTime >= this.placeSpeed.getIntegerValue() * 5) {
            this.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(finalPos, f, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
            this.ownCrystals.add(finalPos);
            this.antiStuckSystemTime = System.nanoTime() / 1000000L;
            this.placeSystemTime = System.nanoTime() / 1000000L;
        }
        if (this.isSpoofingAngles) {
            if (this.togglePitch) {
                final EntityPlayerSP player;
                final EntityPlayerSP player = player = this.mc.player;
                player.rotationPitch += 4.0E-4f;
                this.togglePitch = false;
            }
            else {
                final EntityPlayerSP player2;
                final EntityPlayerSP player = player2 = this.mc.player;
                player2.rotationPitch -= 4.0E-4f;
                this.togglePitch = true;
            }
        }
    }
    
    @SubscribeEvent
    public void onWorldRender(final RenderWorldLastEvent event) {
        if (this.mc.player == null || this.mc.world == null) {
            return;
        }
        if (this.render != null) {
            this.hue += this.rainbowSpeed.getIntegerValue() / 1000.0f;
            final int rgb = Color.HSBtoRGB(this.hue, 1.0f, 1.0f);
            final int r = rgb >> 16 & 0xFF;
            final int g = rgb >> 8 & 0xFF;
            final int b = rgb & 0xFF;
            if (this.color.getEnumValue().equals("Rainbow")) {
                RenderUtil.drawBoxFromBlockpos(this.render, r / 255.0f, g / 255.0f, b / 255.0f, this.alpha.getIntegerValue() / 255.0f);
            }
            else {
                RenderUtil.drawBoxFromBlockpos(this.render, this.red.getIntegerValue() / 255.0f, this.green.getIntegerValue() / 255.0f, this.blue.getIntegerValue() / 255.0f, this.alpha.getIntegerValue() / 255.0f);
            }
        }
    }
    
    private void lookAtPacket(final double px, final double py, final double pz, final EntityPlayer me) {
        final double[] v = this.calculateLookAt(px, py, pz, me);
        this.setYawAndPitch((float)v[0], (float)v[1]);
    }
    
    private double[] calculateLookAt(final double px, final double py, final double pz, final EntityPlayer me) {
        double dirX = me.posX - px;
        double dirY = me.posY - py;
        double dirZ = me.posZ - pz;
        final double len = Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        dirX /= len;
        dirY /= len;
        dirZ /= len;
        double pitch = Math.asin(dirY);
        double yaw = Math.atan2(dirZ, dirX);
        pitch = pitch * 180.0 / 3.141592653589793;
        yaw = yaw * 180.0 / 3.141592653589793;
        yaw += 90.0;
        return new double[] { yaw, pitch };
    }
    
    public BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(this.mc.player.posX), Math.floor(this.mc.player.posY), Math.floor(this.mc.player.posZ));
    }
    
    private List<BlockPos> findCrystalBlocks() {
        final NonNullList<BlockPos> positions = (NonNullList<BlockPos>)NonNullList.create();
        positions.addAll((Collection)this.getSphere(this.getPlayerPos(), (float)this.placeRange.getIntegerValue()).stream().filter(this::canPlaceCrystal).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
        return (List<BlockPos>)positions;
    }
    
    private boolean canPlaceCrystal(final Object o) {
        final BlockPos blockPos = (BlockPos)o;
        final BlockPos boost = blockPos.add(0, 1, 0);
        final BlockPos boost2 = blockPos.add(0, 2, 0);
        return (this.mc.world.getBlockState(blockPos).getBlock() == Blocks.BEDROCK || this.mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN) && this.mc.world.getBlockState(boost).getBlock() == Blocks.AIR && this.mc.world.getBlockState(boost2).getBlock() == Blocks.AIR && this.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost)).isEmpty() && this.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(boost2)).isEmpty();
    }
    
    private List<BlockPos> getSphere(final BlockPos loc, final float r) {
        final List<BlockPos> blocks = new ArrayList<BlockPos>();
        final int cx = loc.getX();
        final int cy = loc.getY();
        final int cz = loc.getZ();
        for (int x = cx - (int)r; x <= cx + r; ++x) {
            for (int z = cz - (int)r; z <= cz + r; ++z) {
                for (int y = cy - (int)r; y < cy + r; ++y) {
                    final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (cy - y) * (cy - y);
                    if (dist < r * r) {
                        final BlockPos l = new BlockPos(x, y, z);
                        blocks.add(l);
                    }
                }
            }
        }
        return blocks;
    }
    
    private void setYawAndPitch(final float yaw1, final float pitch1) {
        this.yaw = yaw1;
        this.pitch = pitch1;
        this.isSpoofingAngles = true;
    }
    
    private void resetRotation() {
        if (this.isSpoofingAngles) {
            this.yaw = this.mc.player.rotationYaw;
            this.pitch = this.mc.player.rotationPitch;
            this.isSpoofingAngles = false;
        }
    }
    
    @Override
    public void onDisable() {
        this.render = null;
        this.resetRotation();
    }
}
