//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.impl.module.combat;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import java.util.List;
import net.minecraft.util.math.BlockPos;
import java.util.function.ToIntFunction;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import java.util.Arrays;
import me.larp.dev.api.module.Category;
import me.larp.dev.api.setting.Setting;
import net.minecraft.item.Item;
import me.larp.dev.impl.module.combat.utilz.stuff;
import me.larp.dev.api.module.Module;

public class SmartOffhand extends Module implements stuff
{
    public int totems;
    int crystals;
    boolean moving;
    boolean returnI;
    Item item;
    private final Setting health;
    private final Setting CrystalCheck;
    private final Setting mode;
    
    public SmartOffhand() {
        super("SmartOffhand", "", Category.COMBAT);
        this.moving = false;
        this.returnI = false;
        this.health = new Setting("Health", this, 1, 10, 40);
        this.CrystalCheck = new Setting("CrystalCheck", this, true);
        this.mode = new Setting("Mode", this, Arrays.asList("Crystal", "Gapple"));
    }
    
    @Override
    public void onUpdate() {
        final Setting itemSetting = null;
        if (itemSetting.getEnumValue().equalsIgnoreCase("Gapple")) {
            this.item = Items.GOLDEN_APPLE;
        }
        else {
            this.item = Items.END_CRYSTAL;
        }
        if (this.mc.currentScreen instanceof GuiContainer) {
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
        this.crystals = this.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == this.item).mapToInt(ItemStack::getCount).sum();
        if (this.shouldTotem() && this.mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
            ++this.totems;
        }
        else if (!this.shouldTotem() && this.mc.player.getHeldItemOffhand().getItem() == this.item) {
            this.crystals += this.mc.player.getHeldItemOffhand().getCount();
        }
        else {
            if (this.moving) {
                this.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
                this.moving = false;
                this.returnI = true;
                return;
            }
            if (this.mc.player.inventory.getItemStack().isEmpty()) {
                if (!this.shouldTotem() && this.mc.player.getHeldItemOffhand().getItem() == this.item) {
                    return;
                }
                if (this.shouldTotem() && this.mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
                    return;
                }
                if (!this.shouldTotem()) {
                    if (this.crystals == 0) {
                        return;
                    }
                    int t = -1;
                    for (int i = 0; i < 45; ++i) {
                        if (this.mc.player.inventory.getStackInSlot(i).getItem() == this.item) {
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
                else {
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
            }
            else {
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
    
    private boolean shouldTotem() {
        final boolean hp = this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount() <= this.health.getIntValue();
        final boolean endcrystal = !this.isCrystalsAABBEmpty();
        final Setting crystalCheck = null;
        if (crystalCheck.getBoolValue()) {
            return hp || endcrystal;
        }
        return hp;
    }
    
    private boolean isEmpty(final BlockPos pos) {
        final List<Entity> crystalsInAABB = (List<Entity>)this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(pos)).stream().filter(e -> e instanceof EntityEnderCrystal).collect(Collectors.toList());
        return crystalsInAABB.isEmpty();
    }
    
    private boolean isCrystalsAABBEmpty() {
        return this.isEmpty(this.mc.player.getPosition().add(1, 0, 0)) && this.isEmpty(this.mc.player.getPosition().add(-1, 0, 0)) && this.isEmpty(this.mc.player.getPosition().add(0, 0, 1)) && this.isEmpty(this.mc.player.getPosition().add(0, 0, -1)) && this.isEmpty(this.mc.player.getPosition());
    }
}
