//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.api.clickgui.button;

import me.larp.dev.api.clickgui.util.RenderUtil;
import org.lwjgl.input.Keyboard;
import me.larp.dev.api.clickgui.util.font.FontUtil;
import net.minecraft.client.gui.Gui;
import java.awt.Color;
import me.larp.dev.api.module.Module;

public class BindButton extends SettingButton
{
    private final Module module;
    private boolean binding;
    
    public BindButton(final Module module, final int x, final int y, final int w, final int h) {
        super(module, x, y, w, h);
        this.module = module;
    }
    
    @Override
    public void render(final int mX, final int mY) {
        Gui.drawRect(this.getX(), this.getY(), this.getX() + 105, this.getY() + 15, new Color(15, 15, 15, 255).getRGB());
        FontUtil.drawStringWithShadow("Bind", (float)(this.getX() + 3), (float)(this.getY() + 4), new Color(255, 255, 255, 255).getRGB());
        if (this.binding) {
            FontUtil.drawStringWithShadow("...", (float)(this.getX() + this.getW() - 6 - FontUtil.getStringWidth("...")), (float)(this.getY() + 4), new Color(255, 255, 255, 255).getRGB());
        }
        else {
            try {
                FontUtil.drawStringWithShadow(Keyboard.getKeyName(this.module.getBind()), (float)(this.getX() + this.getW() - 3 - FontUtil.getStringWidth(Keyboard.getKeyName(this.module.getBind()))), (float)(this.getY() + 4), new Color(255, 255, 255, 255).getRGB());
            }
            catch (Exception e) {
                FontUtil.drawStringWithShadow("NONE", (float)(this.getX() + this.getW() - FontUtil.getStringWidth("NONE")), (float)(this.getY() + 4), new Color(255, 255, 255, 255).getRGB());
            }
        }
        Gui.drawRect(this.getX(), this.getY(), this.getX() + 1, this.getY() + 15, new Color(0, 0, 0, 255).getRGB());
        Gui.drawRect(this.getX() + 104, this.getY(), this.getX() + 105, this.getY() + 15, new Color(0, 0, 0, 255).getRGB());
        RenderUtil.drawGradientSideways(this.getX(), this.getY() + 15, this.getX() + 105, this.getY() + 16, new Color(255, 255, 255, 255).getRGB(), new Color(255, 0, 0, 255).getRGB());
    }
    
    @Override
    public void mouseDown(final int mX, final int mY, final int mB) {
        if (this.isHover(this.getX(), this.getY(), this.getW(), this.getH() - 1, mX, mY)) {
            this.binding = !this.binding;
        }
    }
    
    @Override
    public void keyPress(final int key) {
        if (this.binding) {
            if (key == 211 || key == 1 || key == 14 || key == 0) {
                this.getModule().setBind(256);
            }
            else {
                this.getModule().setBind(key);
            }
            this.binding = false;
        }
    }
}
