//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.api.clickgui.button;

import java.text.DecimalFormat;
import net.minecraft.client.gui.Gui;
import me.larp.dev.api.clickgui.util.font.FontUtil;
import me.larp.dev.api.clickgui.util.RenderUtil;
import me.larp.dev.api.clickgui.ClickGUI;
import java.awt.Color;
import me.larp.dev.Client;
import me.larp.dev.api.module.Module;
import me.larp.dev.api.setting.Setting;

public class SliderButton extends SettingButton
{
    private final Setting setting;
    protected boolean dragging;
    protected int sliderWidth;
    
    SliderButton(final Module module, final Setting setting, final int X, final int Y, final int W, final int H) {
        super(module, X, Y, W, H);
        this.dragging = false;
        this.sliderWidth = 0;
        this.setting = setting;
    }
    
    protected void updateSlider(final int mouseX) {
    }
    
    @Override
    public void render(final int mX, final int mY) {
        this.updateSlider(mX);
        final ClickGUI clickGUI = Client.clickGUI;
        ClickGUI.drawRect(this.getX(), this.getY(), this.getX() + this.getW(), this.getY() + this.getH(), new Color(15, 15, 15, 255).getRGB());
        RenderUtil.drawGradientSideways(this.getX() + 2, this.getY(), this.getX() + this.sliderWidth + 4, this.getY() + this.getH(), new Color(0, 0, 0, 255).getRGB(), new Color(194, 8, 85, 255).getRGB());
        FontUtil.drawStringWithShadow(this.setting.getName(), (float)(this.getX() + 3), (float)(this.getY() + 4), new Color(255, 255, 255, 255).getRGB());
        FontUtil.drawStringWithShadow(String.valueOf(this.setting.getIntegerValue()), this.getX() + this.getW() - FontUtil.getStringWidth(String.valueOf(this.setting.getIntegerValue())) - 2.0f, (float)(this.getY() + 4), new Color(255, 255, 255, 255).getRGB());
        Gui.drawRect(this.getX(), this.getY(), this.getX() + 1, this.getY() + 15, new Color(0, 0, 0, 255).getRGB());
        Gui.drawRect(this.getX() + 104, this.getY(), this.getX() + 105, this.getY() + 15, new Color(0, 0, 0, 255).getRGB());
    }
    
    @Override
    public void mouseDown(final int mX, final int mY, final int mB) {
        if (this.isHover(this.getX(), this.getY(), this.getW(), this.getH() - 1, mX, mY)) {
            this.dragging = true;
        }
    }
    
    @Override
    public void mouseUp(final int mouseX, final int mouseY) {
        this.dragging = false;
    }
    
    @Override
    public void close() {
        this.dragging = false;
    }
    
    public static class IntSlider extends SliderButton
    {
        private final Setting intSetting;
        
        public IntSlider(final Module module, final Setting setting, final int X, final int Y, final int W, final int H) {
            super(module, setting, X, Y, W, H);
            this.intSetting = setting;
        }
        
        @Override
        protected void updateSlider(final int mouseX) {
            final double diff = Math.min(this.getW(), Math.max(0, mouseX - this.getX()));
            final double min = this.intSetting.getMinIntegerValue();
            final double max = this.intSetting.getMaxIntegerValue();
            this.sliderWidth = (int)((this.getW() - 6) * (this.intSetting.getIntegerValue() - min) / (max - min));
            if (this.dragging) {
                if (diff == 0.0) {
                    this.intSetting.setIntegerValue(this.intSetting.getMinIntegerValue());
                }
                else {
                    final DecimalFormat format = new DecimalFormat("##");
                    final String newValue = format.format(diff / this.getW() * (max - min) + min);
                    this.intSetting.setIntegerValue(Integer.parseInt(newValue));
                }
            }
        }
    }
}
