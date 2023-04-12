//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.impl.module.movement;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import me.larp.dev.api.module.Category;
import me.larp.dev.api.setting.Setting;
import me.larp.dev.api.module.Module;

public class EntityFly extends Module
{
    private final Setting verticalSpeed;
    private final Setting speed;
    
    public EntityFly() {
        super("EntityFly", "", Category.MOVEMENT);
        this.verticalSpeed = new Setting("VerticalSpeed", this, 5, 5, 10);
        this.speed = new Setting("Speed", this, 4, 2, 10);
        this.addSetting(this.verticalSpeed);
        this.addSetting(this.speed);
    }
    
    @SubscribeEvent
    public void onUpdate(final TickEvent.ClientTickEvent event) {
        if (this.mc.world == null) {
            return;
        }
        if (this.mc.player.isRiding()) {
            this.mc.player.getRidingEntity().setNoGravity(true);
            this.mc.player.getRidingEntity().motionY = 0.0;
            if (this.mc.gameSettings.keyBindJump.isKeyDown()) {
                this.mc.player.getRidingEntity().motionY = this.verticalSpeed.getIntegerValue() / 5;
            }
            if (this.mc.gameSettings.keyBindSprint.isKeyDown()) {
                this.mc.player.getRidingEntity().motionY = this.verticalSpeed.getIntegerValue() / -5;
            }
            final double[] normalDir = this.directionSpeed(this.speed.getIntegerValue() / 2);
            if (this.mc.player.movementInput.moveStrafe != 0.0f || this.mc.player.movementInput.moveForward != 0.0f) {
                this.mc.player.getRidingEntity().motionX = normalDir[0];
                this.mc.player.getRidingEntity().motionZ = normalDir[1];
            }
            else {
                this.mc.player.getRidingEntity().motionX = 0.0;
                this.mc.player.getRidingEntity().motionZ = 0.0;
            }
        }
    }
    
    private double[] directionSpeed(final double speed) {
        float forward = this.mc.player.movementInput.moveForward;
        float side = this.mc.player.movementInput.moveStrafe;
        float yaw = this.mc.player.prevRotationYaw + (this.mc.player.rotationYaw - this.mc.player.prevRotationYaw) * this.mc.getRenderPartialTicks();
        if (forward != 0.0f) {
            if (side > 0.0f) {
                yaw += ((forward > 0.0f) ? -45 : 45);
            }
            else if (side < 0.0f) {
                yaw += ((forward > 0.0f) ? 45 : -45);
            }
            side = 0.0f;
            if (forward > 0.0f) {
                forward = 1.0f;
            }
            else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        final double sin = Math.sin(Math.toRadians(yaw + 90.0f));
        final double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        final double posX = forward * speed * cos + side * speed * sin;
        final double posZ = forward * speed * sin - side * speed * cos;
        return new double[] { posX, posZ };
    }
}
