//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.impl.module.combat;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.function.ToIntFunction;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import me.larp.dev.api.module.Category;
import me.larp.dev.api.setting.Setting;
import me.larp.dev.api.module.Module;

public class AutoTotem extends Module
{
    Setting soft;
    private int totems;
    boolean moving;
    boolean returnI;
    
    public AutoTotem() {
        super("AutoTotem", "", Category.COMBAT);
        this.soft = new Setting("Soft", this, false);
        this.moving = false;
        this.returnI = false;
        this.addSetting(this.soft);
    }
    
    @SubscribeEvent
    public void onUpdate(final TickEvent.ClientTickEvent event) {
        if (this.nullCheck() || this.mc.currentScreen instanceof GuiContainer) {
            return;
        }
        if (this.returnI) {
            int t = -1;
            for (int i = 0; i < 45; ++i) {
                if (this.mc.player.inventory.getStackInSlot(i).isEmpty()) {
                    t = i;
                    break;
                }
            }
            if (t == -1) {
                return;
            }
            this.mc.playerController.windowClick(0, (t < 9) ? (t + 36) : t, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
            this.returnI = false;
        }
        this.totems = this.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
        if (this.mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
            ++this.totems;
        }
        else {
            if (this.soft.getBooleanValue() && !this.mc.player.getHeldItemOffhand().isEmpty()) {
                return;
            }
            if (this.moving) {
                this.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
                this.moving = false;
                if (!this.mc.player.inventory.getItemStack().isEmpty()) {
                    this.returnI = true;
                }
                return;
            }
            if (this.mc.player.inventory.getItemStack().isEmpty()) {
                if (this.totems == 0) {
                    return;
                }
                int t = -1;
                for (int i = 0; i < 45; ++i) {
                    if (this.mc.player.inventory.getStackInSlot(i).getItem() == Items.TOTEM_OF_UNDYING) {
                        t = i;
                        break;
                    }
                }
                if (t == -1) {
                    return;
                }
                this.mc.playerController.windowClick(0, (t < 9) ? (t + 36) : t, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
                this.moving = true;
            }
            else if (!this.soft.getBooleanValue()) {
                int t = -1;
                for (int i = 0; i < 45; ++i) {
                    if (this.mc.player.inventory.getStackInSlot(i).isEmpty()) {
                        t = i;
                        break;
                    }
                }
                if (t == -1) {
                    return;
                }
                this.mc.playerController.windowClick(0, (t < 9) ? (t + 36) : t, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
            }
        }
    }
}
