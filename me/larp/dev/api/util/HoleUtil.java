// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.api.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

public class HoleUtil extends Vec3i
{
    private final BlockPos blockPos;
    private final HoleTypes holeTypes;
    private boolean tall;
    
    public HoleUtil(final int x, final int y, final int z, final BlockPos pos, final HoleTypes type) {
        super(x, y, z);
        this.blockPos = pos;
        this.holeTypes = type;
    }
    
    public HoleUtil(final int x, final int y, final int z, final BlockPos pos, final HoleTypes type, final boolean tall) {
        super(x, y, z);
        this.blockPos = pos;
        this.tall = tall;
        this.holeTypes = type;
    }
    
    public boolean isTall() {
        return this.tall;
    }
    
    public BlockPos getBlockPos() {
        return this.blockPos;
    }
    
    public HoleTypes getHoleTypes() {
        return this.holeTypes;
    }
    
    public enum HoleTypes
    {
        None, 
        Normal, 
        Obsidian, 
        Bedrock;
    }
}
