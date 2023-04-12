//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.impl.module.combat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.client.gui.inventory.GuiContainer;
import me.larp.dev.api.util.LoggerUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.init.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import java.util.Objects;
import net.minecraft.potion.Potion;
import me.larp.dev.api.module.Category;
import me.larp.dev.api.setting.Setting;
import me.larp.dev.impl.module.combat.utilz.extra;
import me.larp.dev.api.module.Module;

public class Quiver extends Module implements extra
{
    Setting speed;
    Setting strength;
    int randomVariation;
    
    public Quiver() {
        super("Quiver", "", Category.COMBAT);
        this.speed = new Setting("Speed", this, true);
        this.strength = new Setting("Strength", this, true);
    }
    
    @Override
    public void update() {
        final PotionEffect speedEffect = this.mc.player.getActivePotionEffect((Potion)Objects.requireNonNull(Potion.getPotionById(1)));
        final PotionEffect strengthEffect = this.mc.player.getActivePotionEffect((Potion)Objects.requireNonNull(Potion.getPotionById(5)));
        final boolean hasSpeed = speedEffect != null;
        final boolean hasStrength = strengthEffect != null;
        if (this.mc.player.inventory.currentItem == this.find_bow_hotbar()) {
            this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(0.0f, -90.0f, true));
        }
        if (this.strength.get_value(true) && !hasStrength && this.mc.player.inventory.getCurrentItem().getItem() == Items.BOW && this.isArrowInInventory("Arrow of Strength")) {
            if (this.mc.player.getItemInUseMaxCount() >= this.getBowCharge()) {
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, this.mc.player.getHorizontalFacing()));
                this.mc.player.stopActiveHand();
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                this.mc.player.setActiveHand(EnumHand.MAIN_HAND);
            }
            else if (this.mc.player.getItemInUseMaxCount() == 0) {
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                this.mc.player.setActiveHand(EnumHand.MAIN_HAND);
            }
        }
        if (this.speed.get_value(true) && !hasSpeed && this.mc.player.inventory.getCurrentItem().getItem() == Items.BOW && this.isArrowInInventory("Arrow of Speed")) {
            if (this.mc.player.getItemInUseMaxCount() >= this.getBowCharge()) {
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, this.mc.player.getHorizontalFacing()));
                this.mc.player.stopActiveHand();
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                this.mc.player.setActiveHand(EnumHand.MAIN_HAND);
            }
            else if (this.mc.player.getItemInUseMaxCount() == 0) {
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                this.mc.player.setActiveHand(EnumHand.MAIN_HAND);
            }
        }
    }
    
    private int find_bow_hotbar() {
        for (int i = 0; i < 9; ++i) {
            if (this.mc.player.inventory.getStackInSlot(i).getItem() == Items.END_CRYSTAL) {
                return i;
            }
        }
        return -1;
    }
    
    private boolean isArrowInInventory(final String type) {
        boolean inInv = false;
        for (int i = 0; i < 36; ++i) {
            final ItemStack itemStack = this.mc.player.inventory.getStackInSlot(i);
            if (itemStack.getItem() == Items.TIPPED_ARROW && itemStack.getDisplayName().equalsIgnoreCase(type)) {
                inInv = true;
                this.switchArrow(i);
                break;
            }
        }
        return inInv;
    }
    
    private void switchArrow(final int oldSlot) {
        LoggerUtil.sendMessage("Switching arrows!");
        final int bowSlot = this.mc.player.inventory.currentItem;
        int placeSlot = bowSlot + 1;
        if (placeSlot > 8) {
            placeSlot = 1;
        }
        if (placeSlot != oldSlot) {
            if (this.mc.currentScreen instanceof GuiContainer) {
                return;
            }
            this.mc.playerController.windowClick(0, oldSlot, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
            this.mc.playerController.windowClick(0, placeSlot, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
            this.mc.playerController.windowClick(0, oldSlot, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
        }
    }
    
    private int getBowCharge() {
        if (this.randomVariation == 0) {
            this.randomVariation = 1;
        }
        return 1 + this.randomVariation;
    }
}
