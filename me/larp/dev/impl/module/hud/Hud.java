//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.impl.module.hud;

import net.minecraft.util.math.MathHelper;
import me.larp.dev.api.util.MathUtil;
import java.util.Objects;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import me.larp.dev.api.clickgui.util.font.CustomFontRenderer;
import java.util.Iterator;
import net.minecraft.item.ItemStack;
import me.larp.dev.api.clickgui.util.RenderUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import java.util.Date;
import java.text.SimpleDateFormat;
import net.minecraft.entity.player.EntityPlayer;
import java.awt.Color;
import java.util.List;
import java.util.Collections;
import me.larp.dev.api.clickgui.util.font.FontUtil;
import me.larp.dev.Client;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import me.larp.dev.api.module.Category;
import me.larp.dev.api.setting.Setting;
import me.larp.dev.api.module.Module;

public class Hud extends Module
{
    Setting ArrayList;
    Setting Watermark;
    Setting xpCount;
    private final Setting invX;
    private final Setting invY;
    
    public Hud() {
        super("Hud", "", Category.HUD);
        this.ArrayList = new Setting("ArrayList", this, true);
        this.Watermark = new Setting("", this, true);
        this.xpCount = new Setting("Inventory", this, false);
        this.invX = new Setting("InvX", this, 394, 1, 780);
        this.invY = new Setting("InvY", this, 434, 1, 480);
    }
    
    @SubscribeEvent
    public void onRender(final RenderGameOverlayEvent.Post event) {
        if (this.mc.world == null) {
            return;
        }
        final ScaledResolution resolution = new ScaledResolution(this.mc);
        if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) {
            if (this.ArrayList.getBooleanValue()) {
                final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
                int y = 2;
                final ArrayList<String> list = new ArrayList<String>();
                for (final Module mod : Client.moduleManager.getModules()) {
                    if (mod.isEnabled()) {
                        list.add(mod.getName());
                    }
                }
                list.sort((s1, s2) -> FontUtil.getStringWidth(s1) - Minecraft.getMinecraft().fontRenderer.getStringWidth(s2));
                Collections.reverse(list);
                final int r = 255;
                final int g = 255;
                final int b = 255;
                for (final String name : list) {
                    final CustomFontRenderer fr = Client.customFontRenderer;
                    fr.drawStringWithShadow(name, (float)(sr.getScaledWidth() - fr.getStringWidth(name) - 3), (float)y, new Color(138, 43, 226, 255).getRGB());
                    y += 11;
                }
            }
            if (this.Watermark.getBooleanValue()) {
                final int X = 5;
                final int Y = 7;
                final int W = 67;
                final int H = 16;
                String ping = "NONE";
                for (final EntityPlayer player : this.mc.world.playerEntities) {
                    if (player.getName() == this.mc.player.getName()) {
                        ping = this.getPing(player) + "MS";
                    }
                }
                final String server = Minecraft.getMinecraft().isSingleplayer() ? "singleplayer".toUpperCase() : this.mc.getCurrentServerData().serverIP.toUpperCase();
                final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                final Date date = new Date();
                final String waterma = "Wassup, " + Minecraft.getMinecraft().getSession().getUsername() + " :D";
                FontUtil.drawStringWithShadow(waterma, X + 430, Y + 3, new Color(255, 0, 255).getRGB());
                final String watermax = "RussianWare 0.1";
                FontUtil.drawStringWithShadow(watermax, X + 0, Y + 200, new Color(186, 85, 211).getRGB());
                final String ipserver = Minecraft.getMinecraft().isSingleplayer() ? "Singleplayer" : ("IP: " + Minecraft.getMinecraft().getCurrentServerData().serverIP);
                FontUtil.drawStringWithShadow(ipserver, X + 0, Y + 210, new Color(147, 112, 219).getRGB());
                final String mypinglol = "Ping: " + ping;
                FontUtil.drawStringWithShadow(mypinglol, X + 0, Y + 220, new Color(138, 43, 226).getRGB());
                final String myfpslol = Minecraft.getDebugFPS() + " FPS";
                FontUtil.drawStringWithShadow(myfpslol, X + 0, Y + 230, new Color(148, 0, 211).getRGB());
                final int speedinkm = getSpeedInKM();
                final String speed = speedinkm + " Km/h";
                FontUtil.drawStringWithShadow(speed, X + 0, Y + 240, new Color(153, 50, 204).getRGB());
                final float tickrate = getTickRate();
                final String tpsrightnow = tickrate + " TPS";
                FontUtil.drawStringWithShadow(tpsrightnow, X + 0, Y + 250, new Color(139, 0, 139).getRGB());
                final double scale = Math.pow(10.0, 1.0);
                String nether = "";
                if (this.mc.player.dimension == 0) {
                    nether = " (Nether: " + Math.round(this.mc.player.posX * scale / scale) / 8L + " " + Math.round(this.mc.player.posY * scale / scale) + " " + Math.round(this.mc.player.posZ * scale / scale) / 8L + ")";
                }
                else {
                    nether = " (Overworld: " + Math.round(this.mc.player.posX * scale / scale) * 8L + " " + Math.round(this.mc.player.posY * scale / scale) + " " + Math.round(this.mc.player.posZ * scale / scale) * 8L + ")";
                }
                final String coords = "XYZ: " + Math.round(this.mc.player.posX * scale) / scale + " " + Math.round(this.mc.player.posY * scale) / scale + " " + Math.round(this.mc.player.posZ * scale) / scale + "" + nether;
                FontUtil.drawStringWithShadow(coords, X + 0, Y + 487, new Color(238, 130, 238).getRGB());
            }
            if (this.xpCount.getBooleanValue()) {
                GlStateManager.pushMatrix();
                RenderHelper.enableGUIStandardItemLighting();
                Gui.drawRect(this.invX.getIntegerValue() - 1, this.invY.getIntegerValue(), this.invX.getIntegerValue(), this.invY.getIntegerValue() + 57, new Color(255, 0, 0, 255).getRGB());
                Gui.drawRect(this.invX.getIntegerValue() + 177, this.invY.getIntegerValue(), this.invX.getIntegerValue() + 178, this.invY.getIntegerValue() + 57, new Color(255, 0, 0, 255).getRGB());
                Gui.drawRect(this.invX.getIntegerValue() + 177, this.invY.getIntegerValue() - 1, this.invX.getIntegerValue() + 178, this.invY.getIntegerValue() + 58, new Color(255, 255, 255, 255).getRGB());
                RenderUtil.drawGradientSideways(this.invX.getIntegerValue() - 1, this.invY.getIntegerValue() - 1, this.invX.getIntegerValue() + 178, this.invY.getIntegerValue(), new Color(255, 0, 0, 255).getRGB(), new Color(255, 255, 255, 255).getRGB());
                RenderUtil.drawGradientSideways(this.invX.getIntegerValue() - 1, this.invY.getIntegerValue() - 1, this.invX.getIntegerValue() + 178, this.invY.getIntegerValue(), new Color(255, 0, 0, 255).getRGB(), new Color(255, 255, 255, 255).getRGB());
                RenderUtil.drawGradientSideways(this.invX.getIntegerValue() - 1, this.invY.getIntegerValue() + 57, this.invX.getIntegerValue() + 178, this.invY.getIntegerValue() + 58, new Color(255, 0, 0, 255).getRGB(), new Color(255, 255, 255, 255).getRGB());
                RenderUtil.drawGradientSideways(this.invX.getIntegerValue() - 1, this.invY.getIntegerValue() - 1, this.invX.getIntegerValue() + 178, this.invY.getIntegerValue() + 58, new Color(255, 0, 0, 85).getRGB(), new Color(255, 255, 255, 85).getRGB());
                for (int i = 0; i < 27; ++i) {
                    final ItemStack item_stack = (ItemStack)this.mc.player.inventory.mainInventory.get(i + 9);
                    final int item_position_x = this.invX.getIntegerValue() + i % 9 * 20;
                    final int item_position_y = this.invY.getIntegerValue() + i / 9 * 20;
                    this.mc.getRenderItem().renderItemAndEffectIntoGUI(item_stack, item_position_x, item_position_y);
                    this.mc.getRenderItem().renderItemOverlayIntoGUI(this.mc.fontRenderer, item_stack, item_position_x, item_position_y, (String)null);
                }
                this.mc.getRenderItem().zLevel = -5.0f;
                RenderHelper.disableStandardItemLighting();
                GlStateManager.popMatrix();
            }
        }
    }
    
    public int getPing(final EntityPlayer player) {
        int ping = 0;
        try {
            ping = (int)MathUtil.clamp((float)Objects.requireNonNull(this.mc.getConnection()).getPlayerInfo(player.getUniqueID()).getResponseTime(), 1.0f, 300.0f);
        }
        catch (NullPointerException ex) {}
        return ping;
    }
    
    public static int getSpeedInKM() {
        final double deltaX = Minecraft.getMinecraft().player.posX - Minecraft.getMinecraft().player.prevPosX;
        final double deltaZ = Minecraft.getMinecraft().player.posZ - Minecraft.getMinecraft().player.prevPosZ;
        final float l_Distance = MathHelper.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        final double l_KMH = Math.floor(l_Distance / 1000.0f / 1.3888889E-5f);
        return (int)l_KMH;
    }
    
    public static float getTickRate() {
        final float[] ticks = new float[20];
        int tickCount = 0;
        float tickRate = 0.0f;
        for (int i = 0; i < ticks.length; ++i) {
            final float tick = ticks[i];
            if (tick > 0.0f) {
                tickRate += tick;
                ++tickCount;
            }
        }
        return MathHelper.clamp(tickRate / tickCount, 0.0f, 20.0f);
    }
}
