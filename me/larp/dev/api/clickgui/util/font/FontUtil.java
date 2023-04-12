//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.api.clickgui.util.font;

import net.minecraft.client.Minecraft;
import java.awt.GraphicsEnvironment;
import me.larp.dev.Client;
import net.minecraft.client.gui.FontRenderer;

public class FontUtil
{
    private static final FontRenderer fontRenderer;
    
    public static int getStringWidth(final String text) {
        return customFont() ? (Client.customFontRenderer.getStringWidth(text) + 3) : FontUtil.fontRenderer.getStringWidth(text);
    }
    
    public static void drawString(final String text, final double x, final double y, final int color) {
        if (customFont()) {
            Client.customFontRenderer.drawString(text, x, y - 1.0, color, false);
        }
        else {
            FontUtil.fontRenderer.drawString(text, (int)x, (int)y, color);
        }
    }
    
    public static void drawStringWithShadow(final String text, final double x, final double y, final int color) {
        if (customFont()) {
            Client.customFontRenderer.drawStringWithShadow(text, x, y - 1.0, color);
        }
        else {
            FontUtil.fontRenderer.drawStringWithShadow(text, (float)x, (float)y, color);
        }
    }
    
    public static void drawCenteredStringWithShadow(final String text, final float x, final float y, final int color) {
        if (customFont()) {
            Client.customFontRenderer.drawCenteredStringWithShadow(text, x, y - 1.0f, color);
        }
        else {
            FontUtil.fontRenderer.drawStringWithShadow(text, x - FontUtil.fontRenderer.getStringWidth(text) / 2.0f, y, color);
        }
    }
    
    public static void drawCenteredString(final String text, final float x, final float y, final int color) {
        if (customFont()) {
            Client.customFontRenderer.drawCenteredString(text, x, y - 1.0f, color);
        }
        else {
            FontUtil.fontRenderer.drawString(text, (int)(x - getStringWidth(text) / 2), (int)y, color);
        }
    }
    
    public static int getFontHeight() {
        return customFont() ? (Client.customFontRenderer.fontHeight / 2 - 1) : FontUtil.fontRenderer.FONT_HEIGHT;
    }
    
    public static boolean validateFont(final String font) {
        for (final String s : GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()) {
            if (s.equals(font)) {
                return true;
            }
        }
        return false;
    }
    
    private static boolean customFont() {
        return true;
    }
    
    static {
        fontRenderer = Minecraft.getMinecraft().fontRenderer;
    }
}
