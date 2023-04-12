//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.impl.module.movement;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.MovementInput;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import net.minecraftforge.client.event.InputUpdateEvent;
import me.larp.dev.api.module.Category;
import me.larp.dev.api.setting.Setting;
import me.larp.dev.api.module.Module;

public class NoSlow extends Module
{
    private Setting inInventory;
    
    public NoSlow() {
        super("NoSlow", "", Category.MOVEMENT);
        this.inInventory = new Setting("InventoryMove", this, false);
    }
    
    @SubscribeEvent
    public void onInput(final InputUpdateEvent event) {
        if (this.mc.player.isHandActive() && !this.mc.player.isRiding()) {
            final MovementInput movementInput = event.getMovementInput();
            movementInput.moveStrafe *= 5.0f;
            final MovementInput movementInput2 = event.getMovementInput();
            movementInput2.moveForward *= 5.0f;
        }
        if (this.inInventory.getBooleanValue()) {
            this.mc.player.movementInput.moveStrafe = 0.0f;
            this.mc.player.movementInput.moveForward = 0.0f;
            KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindForward.getKeyCode(), Keyboard.isKeyDown(this.mc.gameSettings.keyBindForward.getKeyCode()));
            if (Keyboard.isKeyDown(this.mc.gameSettings.keyBindForward.getKeyCode())) {
                final MovementInput movementInput = this.mc.player.movementInput;
                ++movementInput.moveForward;
                this.mc.player.movementInput.forwardKeyDown = true;
            }
            else {
                this.mc.player.movementInput.forwardKeyDown = false;
            }
            KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindBack.getKeyCode(), Keyboard.isKeyDown(this.mc.gameSettings.keyBindBack.getKeyCode()));
            if (Keyboard.isKeyDown(this.mc.gameSettings.keyBindBack.getKeyCode())) {
                final MovementInput movementInput2 = this.mc.player.movementInput;
                --movementInput2.moveForward;
                this.mc.player.movementInput.backKeyDown = true;
            }
            else {
                this.mc.player.movementInput.backKeyDown = false;
            }
            KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindLeft.getKeyCode(), Keyboard.isKeyDown(this.mc.gameSettings.keyBindLeft.getKeyCode()));
            if (Keyboard.isKeyDown(this.mc.gameSettings.keyBindLeft.getKeyCode())) {
                final MovementInput movementInput3 = this.mc.player.movementInput;
                ++movementInput3.moveStrafe;
                this.mc.player.movementInput.leftKeyDown = true;
            }
            else {
                this.mc.player.movementInput.leftKeyDown = false;
            }
            KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindRight.getKeyCode(), Keyboard.isKeyDown(this.mc.gameSettings.keyBindRight.getKeyCode()));
            if (Keyboard.isKeyDown(this.mc.gameSettings.keyBindRight.getKeyCode())) {
                final MovementInput movementInput4 = this.mc.player.movementInput;
                --movementInput4.moveStrafe;
                this.mc.player.movementInput.rightKeyDown = true;
            }
            else {
                this.mc.player.movementInput.rightKeyDown = false;
            }
            KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindJump.getKeyCode(), Keyboard.isKeyDown(this.mc.gameSettings.keyBindJump.getKeyCode()));
            this.mc.player.movementInput.jump = Keyboard.isKeyDown(this.mc.gameSettings.keyBindJump.getKeyCode());
        }
    }
    
    @Override
    public void onEnable() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @Override
    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
}
