//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.api.clickgui.button;

import java.awt.Color;
import me.larp.dev.Client;
import me.larp.dev.api.module.Module;
import net.minecraft.client.Minecraft;

public class SettingButton
{
    public final Minecraft mc;
    private final int H;
    private Module module;
    private int X;
    private int Y;
    private double W;
    
    public SettingButton(final Module module, final int x, final int y, final double w, final int h) {
        this.mc = Minecraft.getMinecraft();
        this.module = module;
        this.X = x;
        this.Y = y;
        this.W = w;
        this.H = h;
    }
    
    public void render(final int mX, final int mY) {
    }
    
    public void mouseDown(final int mX, final int mY, final int mB) {
    }
    
    public void mouseUp(final int mX, final int mY) {
    }
    
    public void keyPress(final int key) {
    }
    
    public void close() {
    }
    
    public void drawButton(final int mX, final int mY) {
        if (this.module.isEnabled()) {
            Client.clickGUI.drawGradient(this.X, this.Y, this.X + (int)this.W, this.Y + this.H, new Color(15, 15, 15, 255).getRGB(), new Color(15, 15, 15, 255).getRGB());
            Client.clickGUI.drawGradient(this.X, this.Y, this.X + 2, this.Y + this.H, new Color(15, 15, 15, 255).getRGB(), new Color(15, 15, 15, 255).getRGB());
        }
        else {
            Client.clickGUI.drawGradient(this.X, this.Y, this.X + (int)this.W, this.Y + this.H, new Color(15, 15, 15, 255).getRGB(), new Color(70, 70, 70, 225).getRGB());
            Client.clickGUI.drawGradient(this.X, this.Y, this.X + 2, this.Y + this.H, new Color(15, 15, 15, 255).getRGB(), new Color(15, 15, 15, 255).getRGB());
        }
    }
    
    public Module getModule() {
        return this.module;
    }
    
    public void setModule(final Module module) {
        this.module = module;
    }
    
    public int getX() {
        return this.X;
    }
    
    public void setX(final int x) {
        this.X = x;
    }
    
    public int getY() {
        return this.Y;
    }
    
    public void setY(final int y) {
        this.Y = y;
    }
    
    public int getW() {
        return (int)this.W;
    }
    
    public int getH() {
        return this.H;
    }
    
    public boolean isHover(final int X, final int Y, final int W, final int H, final int mX, final int mY) {
        return mX >= X && mX <= X + W && mY >= Y && mY <= Y + H;
    }
}
