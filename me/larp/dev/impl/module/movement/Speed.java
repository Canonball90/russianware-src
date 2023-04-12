//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.impl.module.movement;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.entity.Entity;
import me.larp.dev.impl.event.MoveEvent;
import me.larp.dev.impl.event.WalkEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import me.larp.dev.mixin.accessor.IMinecraft;
import me.larp.dev.mixin.accessor.ITimer;
import me.larp.dev.api.util.PlayerUtil;
import java.util.Arrays;
import me.larp.dev.api.module.Category;
import me.larp.dev.api.setting.Setting;
import me.larp.dev.api.module.Module;

public class Speed extends Module
{
    private final Setting mode;
    private final Setting speed;
    private final Setting useTimer;
    private final Setting timerSpeed;
    private int currentStage;
    private double currentSpeed;
    private double distance;
    private int cooldown;
    
    public Speed() {
        super("Speed", Category.MOVEMENT);
        this.mode = new Setting("Mode", this, Arrays.asList("Strafe", "TP"));
        this.speed = new Setting("Speed", this, 9, 1, 100);
        this.useTimer = new Setting("UseTimer", this, false);
        this.timerSpeed = new Setting("TimerSpeed", this, 7, 1, 20);
    }
    
    @Override
    public void onEnable() {
        this.currentSpeed = PlayerUtil.vanillaSpeed();
        if (!this.mc.player.onGround) {
            this.currentStage = 3;
        }
    }
    
    @Override
    public void onDisable() {
        this.currentSpeed = 0.0;
        this.currentStage = 2;
        ((ITimer)((IMinecraft)this.mc).getTimer()).setTickLength(50.0f);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (!this.nullCheck()) {
            if (this.useTimer.getBooleanValue()) {
                ((ITimer)((IMinecraft)this.mc).getTimer()).setTickLength(50.0f / ((this.timerSpeed.getIntegerValue() + 100) / 100.0f));
            }
            else {
                ((ITimer)((IMinecraft)this.mc).getTimer()).setTickLength(50.0f);
            }
        }
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayer(final WalkEvent event) {
        this.distance = Math.sqrt((this.mc.player.posX - this.mc.player.prevPosX) * (this.mc.player.posX - this.mc.player.prevPosX) + (this.mc.player.posZ - this.mc.player.prevPosZ) * (this.mc.player.posZ - this.mc.player.prevPosZ));
    }
    
    @SubscribeEvent
    public void onMove(final MoveEvent event) {
        if (this.mode.getEnumValue().equalsIgnoreCase("Strafe")) {
            float forward = this.mc.player.movementInput.moveForward;
            float strafe = this.mc.player.movementInput.moveStrafe;
            float yaw = this.mc.player.rotationYaw;
            if (this.currentStage == 1 && PlayerUtil.isMoving()) {
                this.currentStage = 2;
                this.currentSpeed = 1.1799999475479126 * PlayerUtil.vanillaSpeed() - 0.01;
            }
            else if (this.currentStage == 2) {
                this.currentStage = 3;
                if (PlayerUtil.isMoving()) {
                    event.setY(this.mc.player.motionY = 0.4);
                    if (this.cooldown > 0) {
                        --this.cooldown;
                    }
                    this.currentSpeed *= this.speed.getIntegerValue() / 5.0f;
                }
            }
            else if (this.currentStage == 3) {
                this.currentStage = 4;
                this.currentSpeed = this.distance - 0.66 * (this.distance - PlayerUtil.vanillaSpeed());
            }
            else {
                if (this.mc.world.getCollisionBoxes((Entity)this.mc.player, this.mc.player.getEntityBoundingBox().offset(0.0, this.mc.player.motionY, 0.0)).size() > 0 || this.mc.player.collidedVertically) {
                    this.currentStage = 1;
                }
                this.currentSpeed = this.distance - this.distance / 159.0;
            }
            this.currentSpeed = Math.max(this.currentSpeed, PlayerUtil.vanillaSpeed());
            if (forward == 0.0f && strafe == 0.0f) {
                event.setX(0.0);
                event.setZ(0.0);
                this.currentSpeed = 0.0;
            }
            else if (forward != 0.0f) {
                if (strafe >= 1.0f) {
                    yaw += ((forward > 0.0f) ? -45.0f : 45.0f);
                    strafe = 0.0f;
                }
                else if (strafe <= -1.0f) {
                    yaw += ((forward > 0.0f) ? 45.0f : -45.0f);
                    strafe = 0.0f;
                }
                if (forward > 0.0f) {
                    forward = 1.0f;
                }
                else if (forward < 0.0f) {
                    forward = -1.0f;
                }
            }
            final double motionX = Math.cos(Math.toRadians(yaw + 90.0f));
            final double motionZ = Math.sin(Math.toRadians(yaw + 90.0f));
            if (this.cooldown == 0) {
                event.setX(forward * this.currentSpeed * motionX + strafe * this.currentSpeed * motionZ);
                event.setZ(forward * this.currentSpeed * motionZ - strafe * this.currentSpeed * motionX);
            }
            if (forward == 0.0f && strafe == 0.0f) {
                event.setX(0.0);
                event.setZ(0.0);
            }
        }
        else if (PlayerUtil.isMoving() && this.mc.player.onGround) {
            for (double d = 0.0625; d < this.speed.getIntegerValue() / 10.0f; d += 0.262) {
                float yaw = this.mc.player.prevRotationYaw + (this.mc.player.rotationYaw - this.mc.player.prevRotationYaw) * this.mc.getRenderPartialTicks();
                if (this.mc.player.movementInput.moveForward != 0.0f) {
                    if (this.mc.player.movementInput.moveStrafe > 0.0f) {
                        yaw += ((this.mc.player.movementInput.moveForward > 0.0f) ? -45 : 45);
                    }
                    else if (this.mc.player.movementInput.moveStrafe < 0.0f) {
                        yaw += ((this.mc.player.movementInput.moveForward > 0.0f) ? 45 : -45);
                    }
                    this.mc.player.movementInput.moveStrafe = 0.0f;
                    if (this.mc.player.movementInput.moveForward > 0.0f) {
                        this.mc.player.movementInput.moveForward = 1.0f;
                    }
                    else if (this.mc.player.movementInput.moveForward < 0.0f) {
                        this.mc.player.movementInput.moveForward = -1.0f;
                    }
                }
                final double motionX = Math.cos(Math.toRadians(yaw + 90.0f));
                final double motionZ = Math.sin(Math.toRadians(yaw + 90.0f));
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX + this.mc.player.movementInput.moveForward * d * motionX + this.mc.player.movementInput.moveStrafe * d * motionZ, this.mc.player.posY, this.mc.player.posZ + (this.mc.player.movementInput.moveForward * d * motionZ - this.mc.player.movementInput.moveStrafe * d * motionX), this.mc.player.onGround));
            }
            this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX + this.mc.player.motionX, 0.0, this.mc.player.posZ + this.mc.player.motionZ, this.mc.player.onGround));
        }
    }
}
