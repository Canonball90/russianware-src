//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.impl.module.render;

import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import me.larp.dev.api.module.Category;
import me.larp.dev.api.setting.Setting;
import me.larp.dev.api.module.Module;

public class ItemViewModle extends Module
{
    private final Setting fov;
    private final Setting armPitch;
    private final Setting armYaw;
    
    public ItemViewModle() {
        super("ViewModel", "", Category.RENDER);
        this.fov = new Setting("Fov", this, 120, 90, 160);
        this.armPitch = new Setting("Arm Pitch", this, 90, 0, 360);
        this.armYaw = new Setting("Arm Yaw", this, 220, 0, 360);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.mc.world == null) {
            return;
        }
        this.mc.player.renderArmPitch = (float)this.armPitch.getIntegerValue();
    }
    
    @SubscribeEvent
    public void FOVEvent(final EntityViewRenderEvent.FOVModifier event) {
        event.setFOV((float)this.fov.getIntegerValue());
    }
}
