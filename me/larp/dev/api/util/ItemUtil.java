//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.api.util;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemTool;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemArmor;
import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;

public class ItemUtil
{
    public static int getItemFromHotbar(final Item item) {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = Minecraft.getMinecraft().player.inventory.getStackInSlot(i);
            if (stack.getItem() == item) {
                slot = i;
            }
        }
        return slot;
    }
    
    public static int getItemSlot(final Class clss) {
        int itemSlot = -1;
        for (int i = 45; i > 0; --i) {
            if (Minecraft.getMinecraft().player.inventory.getStackInSlot(i).getItem().getClass() == clss) {
                itemSlot = i;
                break;
            }
        }
        return itemSlot;
    }
    
    public static int getItemSlot(final Item item) {
        int itemSlot = -1;
        for (int i = 45; i > 0; --i) {
            if (Minecraft.getMinecraft().player.inventory.getStackInSlot(i).getItem().equals(item)) {
                itemSlot = i;
                break;
            }
        }
        return itemSlot;
    }
    
    public static int getItemCount(final Item item) {
        int count = 0;
        for (int size = Minecraft.getMinecraft().player.inventory.mainInventory.size(), i = 0; i < size; ++i) {
            final ItemStack itemStack = (ItemStack)Minecraft.getMinecraft().player.inventory.mainInventory.get(i);
            if (itemStack.getItem() == item) {
                count += itemStack.getCount();
            }
        }
        final ItemStack offhandStack = Minecraft.getMinecraft().player.getHeldItemOffhand();
        if (offhandStack.getItem() == item) {
            count += offhandStack.getCount();
        }
        return count;
    }
    
    public static boolean isArmorLow(final EntityPlayer player, final int durability) {
        for (final ItemStack piece : player.inventory.armorInventory) {
            if (piece != null && getDamageInPercent(piece) >= durability) {
                continue;
            }
            return true;
        }
        return false;
    }
    
    public static int getItemDamage(final ItemStack stack) {
        return stack.getMaxDamage() - stack.getItemDamage();
    }
    
    public static float getDamageInPercent(final ItemStack stack) {
        final float green = (stack.getMaxDamage() - (float)stack.getItemDamage()) / stack.getMaxDamage();
        final float red = 1.0f - green;
        return (float)(100 - (int)(red * 100.0f));
    }
    
    public static int getRoundedDamage(final ItemStack stack) {
        return (int)getDamageInPercent(stack);
    }
    
    public static boolean hasDurability(final ItemStack stack) {
        final Item item = stack.getItem();
        return item instanceof ItemArmor || item instanceof ItemSword || item instanceof ItemTool || item instanceof ItemShield;
    }
    
    public static int getHotbarItemSlot(final Item item) {
        for (int i = 0; i < 9; ++i) {
            if (Minecraft.getMinecraft().player.inventory.getStackInSlot(i).getItem() == item) {
                return i;
            }
        }
        return -1;
    }
    
    public static void switchToSlotGhost(final int slot) {
        final Minecraft mc = Minecraft.getMinecraft();
        if (slot != -1 && mc.player.inventory.currentItem != slot) {
            mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
        }
    }
    
    public static void switchToSlotGhost(final Item item) {
        final Minecraft mc = Minecraft.getMinecraft();
        if (getHotbarItemSlot(item) != -1 && mc.player.inventory.currentItem != getHotbarItemSlot(item)) {
            switchToSlotGhost(getHotbarItemSlot(item));
        }
    }
    
    public static void switchToSlot(final int slot) {
        final Minecraft mc = Minecraft.getMinecraft();
        if (slot != -1 && mc.player.inventory.currentItem != slot) {
            mc.player.inventory.currentItem = slot;
        }
    }
    
    public static void switchToSlot(final Item item) {
        final Minecraft mc = Minecraft.getMinecraft();
        if (getHotbarItemSlot(item) != -1 && mc.player.inventory.currentItem != getHotbarItemSlot(item)) {
            mc.player.inventory.currentItem = getHotbarItemSlot(item);
        }
    }
}
