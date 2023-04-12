//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.api.clickgui;

import me.larp.dev.api.clickgui.button.SettingButton;
import me.larp.dev.api.clickgui.util.font.FontUtil;
import org.lwjgl.opengl.Display;
import net.minecraft.client.gui.Gui;
import java.awt.Color;
import java.util.Iterator;
import me.larp.dev.api.module.Module;
import me.larp.dev.Client;
import net.minecraft.client.Minecraft;
import me.larp.dev.api.module.Category;
import me.larp.dev.api.clickgui.button.ModuleButton;
import java.util.ArrayList;
import net.minecraft.client.gui.ScaledResolution;

public class Window
{
    ScaledResolution scaledRes;
    private final ArrayList<ModuleButton> buttons;
    private final Category category;
    private final int W;
    private final int H;
    private final ArrayList<ModuleButton> buttonsBeforeClosing;
    private int X;
    private int Y;
    private int dragX;
    private int dragY;
    private boolean open;
    private boolean dragging;
    private int showingButtonCount;
    private boolean opening;
    private boolean closing;
    
    public Window(final Category category, final int x, final int y, final int w, final int h) {
        this.scaledRes = new ScaledResolution(Minecraft.getMinecraft());
        this.buttons = new ArrayList<ModuleButton>();
        this.buttonsBeforeClosing = new ArrayList<ModuleButton>();
        this.open = true;
        this.category = category;
        this.X = x;
        this.Y = y;
        this.W = w;
        this.H = h;
        int yOffset = this.Y + this.H;
        for (final Module module : Client.moduleManager.getModules(category)) {
            final ModuleButton button = new ModuleButton(module, this.X, yOffset, this.W, this.H);
            this.buttons.add(button);
            yOffset += this.H;
        }
        this.showingButtonCount = this.buttons.size();
    }
    
    public void render(final int mX, final int mY) {
        Gui.drawRect(0, 0, 970, 2, new Color(0, 0, 0, 100).getRGB());
        FontUtil.drawString("Future++", 100.0, Display.getWidth() / 2 + 300, -1);
        if (this.dragging) {
            this.X = this.dragX + mX;
            this.Y = this.dragY + mY;
        }
        Gui.drawRect(this.X, this.Y + 1, this.X + this.W, this.Y + this.H, new Color(15, 15, 15).getRGB());
        Gui.drawRect(this.X, this.Y + 1, this.X + 1, this.Y + this.H, new Color(0, 0, 0).getRGB());
        Gui.drawRect(this.X + 104, this.Y + 1, this.X + this.W, this.Y + this.H, new Color(0, 0, 0).getRGB());
        Gui.drawRect(this.X, this.Y + 1, this.X + this.W, this.Y + 2, new Color(0, 0, 0).getRGB());
        Gui.drawRect(this.X, this.Y + 14, this.X + this.W, this.Y + 15, new Color(15, 15, 15).getRGB());
        FontUtil.drawStringWithShadow(this.category.getName(), this.X + 3, this.Y + 5, new Color(255, 255, 255, 255).getRGB());
        if (this.open || this.opening || this.closing) {
            int modY = this.Y + this.H;
            int moduleRenderCount = 0;
            for (final ModuleButton moduleButton : this.buttons) {
                if (++moduleRenderCount < this.showingButtonCount + 1) {
                    moduleButton.setX(this.X);
                    moduleButton.setY(modY);
                    moduleButton.render(mX, mY);
                    if (!moduleButton.isOpen() && this.opening && this.buttonsBeforeClosing.contains(moduleButton)) {
                        moduleButton.processRightClick();
                    }
                    modY += this.H;
                    if (!moduleButton.isOpen() && !moduleButton.isOpening() && !moduleButton.isClosing()) {
                        continue;
                    }
                    int settingRenderCount = 0;
                    for (final SettingButton settingButton : moduleButton.getButtons()) {
                        if (++settingRenderCount < moduleButton.getShowingModuleCount() + 1) {
                            settingButton.setX(this.X);
                            settingButton.setY(modY);
                            settingButton.render(mX, mY);
                            modY += this.H;
                        }
                    }
                }
            }
        }
        if (this.opening) {
            ++this.showingButtonCount;
            if (this.showingButtonCount == this.buttons.size()) {
                this.opening = false;
                this.open = true;
                this.buttonsBeforeClosing.clear();
            }
        }
        if (this.closing) {
            --this.showingButtonCount;
            if (this.showingButtonCount == 0 || this.showingButtonCount == 1) {
                this.closing = false;
                this.open = false;
            }
        }
    }
    
    public void mouseDown(final int mX, final int mY, final int mB) {
        if (this.isHover(this.X, this.Y, this.W, this.H, mX, mY)) {
            if (mB == 0) {
                this.dragging = true;
                this.dragX = this.X - mX;
                this.dragY = this.Y - mY;
            }
            else if (mB != 1 || !this.open || this.opening || !this.closing) {}
        }
        if (this.open) {
            for (final ModuleButton button : this.buttons) {
                button.mouseDown(mX, mY, mB);
            }
        }
    }
    
    public void mouseUp(final int mX, final int mY) {
        this.dragging = false;
        if (this.open) {
            for (final ModuleButton button : this.buttons) {
                button.mouseUp(mX, mY);
            }
        }
    }
    
    public void keyPress(final int key) {
        if (this.open) {
            for (final ModuleButton button : this.buttons) {
                button.keyPress(key);
            }
        }
    }
    
    public void close() {
        for (final ModuleButton button : this.buttons) {
            button.close();
        }
    }
    
    private boolean isHover(final int X, final int Y, final int W, final int H, final int mX, final int mY) {
        return mX >= X && mX <= X + W && mY >= Y && mY <= Y + H;
    }
    
    public int getY() {
        return this.Y;
    }
    
    public void setY(final int y) {
        this.Y = y;
    }
}
