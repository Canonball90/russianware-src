// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.api.module;

public enum Category
{
    COMBAT("Combat"), 
    EXPLOIT("Exploit"), 
    RENDER("Render"), 
    MOVEMENT("Movement"), 
    MISC("Miscellaneous"), 
    HUD("Hud");
    
    private String name;
    
    private Category(final String name) {
        this.setName(name);
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
}
