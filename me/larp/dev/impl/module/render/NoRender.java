//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.impl.module.render;

import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import me.larp.dev.api.module.Category;
import me.larp.dev.api.setting.Setting;
import me.larp.dev.api.module.Module;

public class NoRender extends Module
{
    private Setting explosions;
    private Setting HurtCam;
    private Setting Fire;
    private Setting Portals;
    private Setting Armor;
    private Setting BossBar;
    
    public NoRender() {
        super("NoRender", Category.RENDER);
        this.explosions = new Setting("Explosions", this, false);
        this.HurtCam = new Setting("HurtCam", this, false);
        this.Fire = new Setting("FireOverlay", this, false);
        this.Portals = new Setting("PortalOverlay", this, false);
        this.Armor = new Setting("Armor", this, false);
        this.BossBar = new Setting("BossBar", this, false);
    }
    
    @SubscribeEvent
    public void explosionEvent(final ExplosionEvent event) {
        if (this.explosions.getBooleanValue()) {
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (!this.nullCheck()) {
            if (this.Portals.getBooleanValue()) {
                GuiIngameForge.renderPortal = false;
            }
            if (this.Armor.getBooleanValue()) {
                GuiIngameForge.renderArmor = false;
            }
            if (this.BossBar.getBooleanValue()) {
                GuiIngameForge.renderBossHealth = false;
            }
            if (this.HurtCam.getBooleanValue()) {
                this.mc.player.maxHurtTime = 8;
            }
        }
    }
    
    @SubscribeEvent
    public void RenderOverLay(final RenderBlockOverlayEvent event) {
        if (this.mc.player == null || this.mc.world == null) {
            return;
        }
        if (this.Fire.getBooleanValue() && event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.FIRE) {
            event.setCanceled(true);
        }
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        if (this.Portals.getBooleanValue()) {
            GuiIngameForge.renderPortal = true;
        }
        if (this.Armor.getBooleanValue()) {
            GuiIngameForge.renderArmor = true;
        }
        if (this.BossBar.getBooleanValue()) {
            GuiIngameForge.renderBossHealth = true;
        }
    }
}
