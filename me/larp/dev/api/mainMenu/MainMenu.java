//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.api.mainMenu;

import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiWorldSelection;
import me.larp.dev.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.gui.GuiScreen;

public class MainMenu extends GuiScreen implements GuiYesNoCallback
{
    private String[] mainBtns;
    private int currentButton;
    
    public MainMenu() {
        this.mainBtns = new String[] { "Singleplayer", "Multiplayer", "AltManager", "Settings", "Quit" };
        this.currentButton = 0;
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        GlStateManager.disableAlpha();
        this.drawDefaultBackground();
        this.drawString(Minecraft.getMinecraft().fontRenderer, "russianware v0.1", 2, 3, 16562691);
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)(this.width / 2), (float)(this.height / 2 - Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT), 0.0f);
        GlStateManager.scale(2.0f, 2.0f, 1.0f);
        GlStateManager.translate((float)(-(this.width / 2)), (float)(-(this.height / 2 - Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT)), 0.0f);
        this.drawCenteredString(Minecraft.getMinecraft().fontRenderer, Client.getName(), this.width / 2, this.height / 2 - Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT, 9371903);
        GlStateManager.popMatrix();
        this.drawCenteredString(Minecraft.getMinecraft().fontRenderer, "Best client for oldfag.org, 3l3d.xyz", this.width / 2, this.height / 2 + 20, -1);
        for (int i = 0; i < this.mainBtns.length; ++i) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(10.0f, (float)(this.height / 2 - Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT + i * 20), 0.0f);
            GlStateManager.scale(1.3f, 1.3f, 1.0f);
            GlStateManager.translate(-10.0f, (float)(-(this.height / 2 - Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT + i * 20)), 0.0f);
            final float x = 10.0f;
            final float y = (float)(this.height / 2 - Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT + i * 20);
            final boolean hover = mouseX >= x && mouseY >= y && mouseX < x + Minecraft.getMinecraft().fontRenderer.getStringWidth(this.mainBtns[i]) + 20.0f && mouseY < y + Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT;
            this.drawString(Minecraft.getMinecraft().fontRenderer, this.mainBtns[i], 10, this.height / 2 - Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT + i * 20, hover ? -1 : 4360181);
            GlStateManager.popMatrix();
        }
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        for (int i = 0; i < this.mainBtns.length; ++i) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(10.0f, (float)(this.height / 2 - Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT + i * 20), 0.0f);
            GlStateManager.scale(1.3f, 1.3f, 1.0f);
            GlStateManager.translate(-10.0f, (float)(-(this.height / 2 - Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT + i * 20)), 0.0f);
            final float x = 10.0f;
            final float y = (float)(this.height / 2 - Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT + i * 20);
            if (mouseX >= x && mouseY >= y && mouseX < x + Minecraft.getMinecraft().fontRenderer.getStringWidth(this.mainBtns[i]) + 20.0f && mouseY < y + Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT) {
                final String s = this.mainBtns[i];
                switch (s) {
                    case "Singleplayer": {
                        Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new GuiWorldSelection((GuiScreen)this));
                        break;
                    }
                    case "Multiplayer": {
                        Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new GuiMultiplayer((GuiScreen)this));
                    }
                    case "Settings": {
                        Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new GuiOptions((GuiScreen)this, Minecraft.getMinecraft().gameSettings));
                        break;
                    }
                    case "Quit": {
                        Minecraft.getMinecraft().shutdown();
                        break;
                    }
                }
            }
            GlStateManager.popMatrix();
        }
    }
    
    public void onGuiClosed() {
    }
}
