//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.api.util.burrow;

import java.util.Random;
import net.minecraft.client.Minecraft;

public interface Globals
{
    public static final Minecraft mc = Minecraft.getMinecraft();
    public static final Random random = new Random();
    public static final char SECTIONSIGN = '§';
    
    default boolean nullCheck() {
        return Globals.mc.player == null || Globals.mc.world == null;
    }
}
