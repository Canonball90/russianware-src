//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.api.clickgui.button;

import me.larp.dev.api.clickgui.util.font.FontUtil;
import net.minecraft.client.gui.Gui;
import java.awt.Color;
import me.larp.dev.api.module.Module;
import me.larp.dev.api.setting.Setting;

public class BoolButton extends SettingButton
{
    private final Setting setting;
    
    public BoolButton(final Module module, final Setting setting, final int X, final int Y, final int W, final int H) {
        super(module, X, Y, W, H);
        this.setting = setting;
    }
    
    @Override
    public void render(final int mX, final int mY) {
        if (this.setting.getBooleanValue()) {
            Gui.drawRect(this.getX(), this.getY(), this.getX() + 105, this.getY() + 15, new Color(100, 0, 0, 255).getRGB());
        }
        else {
            Gui.drawRect(this.getX(), this.getY(), this.getX() + 105, this.getY() + 15, new Color(15, 15, 15, 255).getRGB());
        }
        FontUtil.drawStringWithShadow(this.setting.getName(), (float)(this.getX() + 3), (float)(this.getY() + 4), new Color(175, 175, 175, 255).getRGB());
        Gui.drawRect(this.getX(), this.getY(), this.getX() + 1, this.getY() + 15, new Color(0, 0, 0, 255).getRGB());
        Gui.drawRect(this.getX() + 104, this.getY(), this.getX() + 105, this.getY() + 15, new Color(0, 0, 0, 255).getRGB());
    }
    
    @Override
    public void mouseDown(final int mX, final int mY, final int mB) {
        if (this.isHover(this.getX(), this.getY(), this.getW(), this.getH() - 1, mX, mY) && (mB == 0 || mB == 1)) {
            this.setting.setBooleanValue(!this.setting.getBooleanValue());
        }
    }
}
