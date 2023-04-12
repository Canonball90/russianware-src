//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.api.util.burrow;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemArmor;
import net.minecraft.inventory.EntityEquipmentSlot;
import java.util.Iterator;
import net.minecraft.inventory.Slot;
import net.minecraft.init.Items;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.item.ItemAir;
import net.minecraft.item.ItemStack;

public class InventoryUtil implements Globals
{
    public static boolean isNull(final ItemStack stack) {
        return stack == null || stack.getItem() instanceof ItemAir;
    }
    
    public static void switchToHotbarSlot(final int slot, final boolean silent) {
        if (InventoryUtil.mc.player.inventory.currentItem == slot || slot < 0) {
            return;
        }
        if (silent) {
            InventoryUtil.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            InventoryUtil.mc.playerController.updateController();
        }
        else {
            InventoryUtil.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            InventoryUtil.mc.player.inventory.currentItem = slot;
            InventoryUtil.mc.playerController.updateController();
        }
    }
    
    public static boolean isBlock(final Item item, final Class c) {
        if (item instanceof ItemBlock) {
            final Block block = ((ItemBlock)item).getBlock();
            return c.isInstance(block);
        }
        return false;
    }
    
    public static Map<Integer, ItemStack> getInventoryAndHotbarSlots() {
        return getInventorySlots(9, 44);
    }
    
    private static Map<Integer, ItemStack> getInventorySlots(final int currentI, final int last) {
        final HashMap<Integer, ItemStack> fullInventorySlots = new HashMap<Integer, ItemStack>();
        for (int current = currentI; current <= last; ++current) {
            fullInventorySlots.put(current, (ItemStack)InventoryUtil.mc.player.inventoryContainer.getInventory().get(current));
        }
        return fullInventorySlots;
    }
    
    public static List<Integer> findEmptySlots(final boolean withXCarry) {
        final ArrayList<Integer> outPut = new ArrayList<Integer>();
        for (final Map.Entry<Integer, ItemStack> entry : getInventoryAndHotbarSlots().entrySet()) {
            if (!entry.getValue().isEmpty() && entry.getValue().getItem() != Items.AIR) {
                continue;
            }
            outPut.add(entry.getKey());
        }
        if (withXCarry) {
            for (int i = 1; i < 5; ++i) {
                final Slot craftingSlot = InventoryUtil.mc.player.inventoryContainer.inventorySlots.get(i);
                final ItemStack craftingStack = craftingSlot.getStack();
                if (craftingStack.isEmpty() || craftingStack.getItem() == Items.AIR) {
                    outPut.add(i);
                }
            }
        }
        return outPut;
    }
    
    public static int findArmorSlot(final EntityEquipmentSlot type, final boolean binding) {
        int slot = -1;
        float damage = 0.0f;
        for (int i = 9; i < 45; ++i) {
            final ItemStack s = InventoryUtil.mc.player.inventoryContainer.getSlot(i).getStack();
            if (s.getItem() != Items.AIR && s.getItem() instanceof ItemArmor) {
                final ItemArmor armor;
                if ((armor = (ItemArmor)s.getItem()).getEquipmentSlot() == type) {
                    final float currentDamage = (float)(armor.damageReduceAmount + EnchantmentHelper.getEnchantmentLevel(Enchantments.PROTECTION, s));
                    final boolean cursed = binding && EnchantmentHelper.hasBindingCurse(s);
                    if (currentDamage > damage) {
                        if (!cursed) {
                            damage = currentDamage;
                            slot = i;
                        }
                    }
                }
            }
        }
        return slot;
    }
    
    public static boolean holdingItem(final Class c) {
        return isInstanceOf(InventoryUtil.mc.player.getHeldItemMainhand(), c) || isInstanceOf(InventoryUtil.mc.player.getHeldItemOffhand(), c);
    }
    
    public static boolean isInstanceOf(final ItemStack stack, final Class c) {
        if (stack == null) {
            return false;
        }
        final Item item = stack.getItem();
        if (c.isInstance(item)) {
            return true;
        }
        if (item instanceof ItemBlock) {
            final Block block = Block.getBlockFromItem(item);
            return c.isInstance(block);
        }
        return false;
    }
    
    public static int findArmorSlot(final EntityEquipmentSlot type, final boolean binding, final boolean withXCarry) {
        int slot = findArmorSlot(type, binding);
        if (slot == -1 && withXCarry) {
            float damage = 0.0f;
            for (int i = 1; i < 5; ++i) {
                final Slot craftingSlot = InventoryUtil.mc.player.inventoryContainer.inventorySlots.get(i);
                final ItemStack craftingStack = craftingSlot.getStack();
                if (craftingStack.getItem() != Items.AIR && craftingStack.getItem() instanceof ItemArmor) {
                    final ItemArmor armor;
                    if ((armor = (ItemArmor)craftingStack.getItem()).getEquipmentSlot() == type) {
                        final float currentDamage = (float)(armor.damageReduceAmount + EnchantmentHelper.getEnchantmentLevel(Enchantments.PROTECTION, craftingStack));
                        final boolean cursed = binding && EnchantmentHelper.hasBindingCurse(craftingStack);
                        if (currentDamage > damage) {
                            if (!cursed) {
                                damage = currentDamage;
                                slot = i;
                            }
                        }
                    }
                }
            }
        }
        return slot;
    }
    
    public static void switchToHotbarSlot(final Class c, final boolean silent) {
        final int slot = findHotbarBlock(c);
        if (slot > -1) {
            switchToHotbarSlot(slot, silent);
        }
    }
    
    public static int findHotbarBlock(final Class c) {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = InventoryUtil.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY) {
                if (c.isInstance(stack.getItem())) {
                    return i;
                }
                if (stack.getItem() instanceof ItemBlock) {
                    if (c.isInstance(((ItemBlock)stack.getItem()).getBlock())) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }
    
    public static class Task
    {
        private final int slot;
        private final boolean update;
        private final boolean quickClick;
        
        public Task() {
            this.update = true;
            this.slot = -1;
            this.quickClick = false;
        }
        
        public Task(final int slot) {
            this.slot = slot;
            this.quickClick = false;
            this.update = false;
        }
        
        public Task(final int slot, final boolean quickClick) {
            this.slot = slot;
            this.quickClick = quickClick;
            this.update = false;
        }
        
        public void run() {
            if (this.update) {
                Globals.mc.playerController.updateController();
            }
            if (this.slot != -1) {
                Globals.mc.playerController.windowClick(0, this.slot, 0, this.quickClick ? ClickType.QUICK_MOVE : ClickType.PICKUP, (EntityPlayer)Globals.mc.player);
            }
        }
        
        public boolean isSwitching() {
            return !this.update;
        }
    }
}
