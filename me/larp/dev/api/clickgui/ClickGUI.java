//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.api.clickgui;

import org.lwjgl.input.Mouse;
import me.larp.dev.Client;
import java.util.Iterator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.renderer.OpenGlHelper;
import me.larp.dev.api.module.Category;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiScreen;

public class ClickGUI extends GuiScreen
{
    private final ArrayList<Window> windows;
    
    public ClickGUI() {
        this.windows = new ArrayList<Window>();
        int xOffset = 5;
        for (final Category category : Category.values()) {
            final Window window = new Window(category, xOffset, 25, 105, 15);
            this.windows.add(window);
            xOffset += 110;
        }
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.doScroll();
        for (final Window window : this.windows) {
            window.render(mouseX, mouseY);
        }
        if (OpenGlHelper.shadersSupported && this.mc.getRenderViewEntity() instanceof EntityPlayer) {
            if (this.mc.entityRenderer.getShaderGroup() != null) {
                this.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
            }
            this.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
        }
    }
    
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        for (final Window window : this.windows) {
            window.mouseDown(mouseX, mouseY, mouseButton);
        }
    }
    
    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
        for (final Window window : this.windows) {
            window.mouseUp(mouseX, mouseY);
        }
    }
    
    protected void keyTyped(final char typedChar, final int keyCode) {
        for (final Window window : this.windows) {
            window.keyPress(keyCode);
        }
        if (keyCode == 1) {
            this.mc.displayGuiScreen((GuiScreen)null);
            if (this.mc.currentScreen == null) {
                this.mc.setIngameFocus();
            }
        }
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public void drawGradient(final int left, final int top, final int right, final int bottom, final int startColor, final int endColor) {
        this.drawGradientRect(left, top, right, bottom, startColor, endColor);
    }
    
    public void onGuiClosed() {
        for (final Window window : this.windows) {
            window.close();
        }
        Client.moduleManager.getModule("ClickGUI").disable();
        if (this.mc.entityRenderer.getShaderGroup() != null) {
            this.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
        }
    }
    
    private void doScroll() {
        final int w = Mouse.getDWheel();
        if (w < 0) {
            for (final Window window : this.windows) {
                window.setY(window.getY() - 8);
            }
        }
        else if (w > 0) {
            for (final Window window : this.windows) {
                window.setY(window.getY() + 8);
            }
        }
    }
}
