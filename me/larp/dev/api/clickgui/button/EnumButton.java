//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.api.clickgui.button;

import java.util.Iterator;
import net.minecraft.client.gui.Gui;
import me.larp.dev.api.clickgui.util.font.FontUtil;
import java.awt.Color;
import me.larp.dev.Client;
import me.larp.dev.api.module.Module;
import me.larp.dev.api.setting.Setting;

public class EnumButton extends SettingButton
{
    private final Setting setting;
    
    public EnumButton(final Module module, final Setting setting, final int X, final int Y, final int W, final int H) {
        super(module, X, Y, W, H);
        this.setting = setting;
    }
    
    @Override
    public void render(final int mX, final int mY) {
        Client.clickGUI.drawGradient(this.getX(), this.getY(), this.getX() + 105, this.getY() + 15, new Color(15, 15, 15, 255).getRGB(), new Color(15, 15, 15, 255).getRGB());
        FontUtil.drawStringWithShadow(this.setting.getName(), (float)(this.getX() + 3), (float)(this.getY() + 4), new Color(255, 255, 255, 255).getRGB());
        FontUtil.drawStringWithShadow(this.setting.getEnumValue(), (float)(this.getX() + this.getW() - FontUtil.getStringWidth(this.setting.getEnumValue())), (float)(this.getY() + 4), new Color(255, 255, 255, 255).getRGB());
        Gui.drawRect(this.getX(), this.getY(), this.getX() + 1, this.getY() + 15, new Color(0, 0, 0, 255).getRGB());
        Gui.drawRect(this.getX() + 104, this.getY(), this.getX() + 105, this.getY() + 15, new Color(0, 0, 0, 255).getRGB());
    }
    
    @Override
    public void mouseDown(final int mX, final int mY, final int mB) {
        if (this.isHover(this.getX(), this.getY(), this.getW(), this.getH() - 1, mX, mY)) {
            if (mB == 0) {
                int i = 0;
                int enumIndex = 0;
                for (final String enumName : this.setting.getEnumValues()) {
                    if (enumName.equals(this.setting.getEnumValue())) {
                        enumIndex = i;
                    }
                    ++i;
                }
                if (enumIndex == this.setting.getEnumValues().size() - 1) {
                    this.setting.setEnumValue(this.setting.getEnumValues().get(0));
                }
                else {
                    ++enumIndex;
                    i = 0;
                    for (final String enumName : this.setting.getEnumValues()) {
                        if (i == enumIndex) {
                            this.setting.setEnumValue(enumName);
                        }
                        ++i;
                    }
                }
            }
            else if (mB == 1) {
                int i = 0;
                int enumIndex = 0;
                for (final String enumName : this.setting.getEnumValues()) {
                    if (enumName.equals(this.setting.getEnumValue())) {
                        enumIndex = i;
                    }
                    ++i;
                }
                if (enumIndex == 0) {
                    this.setting.setEnumValue(this.setting.getEnumValues().get(this.setting.getEnumValues().size() - 1));
                }
                else {
                    --enumIndex;
                    i = 0;
                    for (final String enumName : this.setting.getEnumValues()) {
                        if (i == enumIndex) {
                            this.setting.setEnumValue(enumName);
                        }
                        ++i;
                    }
                }
            }
        }
    }
}
