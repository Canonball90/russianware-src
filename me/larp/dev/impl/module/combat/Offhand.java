//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.impl.module.combat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import me.larp.dev.api.util.LoggerUtil;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.init.Items;
import java.util.Arrays;
import me.larp.dev.api.module.Category;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import me.larp.dev.api.setting.Setting;
import me.larp.dev.api.module.Module;

public class Offhand extends Module
{
    private final Setting health;
    private final Setting mode;
    NonNullList<ItemStack> inv;
    int TotemCache;
    int InvID;
    
    public Offhand() {
        super("Offhand", Category.COMBAT);
        this.health = new Setting("Health", this, 4, 0, 36);
        this.mode = new Setting("Mode", this, Arrays.asList("Totem", "Gap", "Crystal"));
    }
    
    @Override
    public void onEnable() {
        this.inv = (NonNullList<ItemStack>)this.mc.player.inventory.mainInventory;
        this.InvID = 0;
        while (this.InvID < this.inv.size()) {
            if (this.inv.get(this.InvID) != ItemStack.EMPTY && ((ItemStack)this.inv.get(this.InvID)).getItem() == Items.TOTEM_OF_UNDYING) {
                this.TotemCache = this.InvID;
                break;
            }
            ++this.InvID;
        }
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.mc.player.getHealth() <= this.health.getIntegerValue() || this.mode.getEnumValue().equalsIgnoreCase("Totem")) {
            if (this.mc.player.getHeldItemOffhand().getItem() != Items.TOTEM_OF_UNDYING) {
                this.replaceOffHand(this.TotemCache);
                this.inv = (NonNullList<ItemStack>)this.mc.player.inventory.mainInventory;
                this.InvID = 0;
                while (this.InvID < this.inv.size()) {
                    if (this.inv.get(this.InvID) != ItemStack.EMPTY && ((ItemStack)this.inv.get(this.InvID)).getItem() == Items.TOTEM_OF_UNDYING) {
                        this.TotemCache = this.InvID;
                    }
                    ++this.InvID;
                }
            }
        }
        else if (this.mc.player.getHealth() > this.health.getIntegerValue() && this.mode.getEnumValue().equalsIgnoreCase("Gapple")) {
            if (this.mc.player.getHeldItemOffhand().getItem() != Items.GOLDEN_APPLE) {
                LoggerUtil.sendMessage("Putting Gapple In Offhand!");
                this.inv = (NonNullList<ItemStack>)this.mc.player.inventory.mainInventory;
                this.InvID = 0;
                while (this.InvID < this.inv.size()) {
                    if (this.inv.get(this.InvID) != ItemStack.EMPTY && ((ItemStack)this.inv.get(this.InvID)).getItem() == Items.GOLDEN_APPLE) {
                        this.replaceOffHand(this.InvID);
                    }
                    ++this.InvID;
                }
            }
        }
        else if (this.mc.player.getHealth() > this.health.getIntegerValue() && this.mode.getEnumValue().equalsIgnoreCase("Crystal") && this.mc.player.getHeldItemOffhand().getItem() != Items.END_CRYSTAL) {
            LoggerUtil.sendMessage("Putting Crystal In Offhand!");
            this.inv = (NonNullList<ItemStack>)this.mc.player.inventory.mainInventory;
            this.InvID = 0;
            while (this.InvID < this.inv.size()) {
                if (this.inv.get(this.InvID) != ItemStack.EMPTY && ((ItemStack)this.inv.get(this.InvID)).getItem() == Items.END_CRYSTAL) {
                    this.replaceOffHand(this.InvID);
                }
                ++this.InvID;
            }
        }
    }
    
    public void replaceOffHand(final int InvID) {
        if (this.mc.player.openContainer instanceof ContainerPlayer) {
            this.mc.playerController.windowClick(0, (InvID < 9) ? (InvID + 36) : InvID, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
            this.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
            this.mc.playerController.windowClick(0, (InvID < 9) ? (InvID + 36) : InvID, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
        }
    }
}
