// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.api.setting;

import java.util.Iterator;
import me.larp.dev.api.module.Module;
import java.util.ArrayList;

public class SettingManager
{
    private final ArrayList<Setting> settings;
    
    public SettingManager() {
        this.settings = new ArrayList<Setting>();
    }
    
    public void addSetting(final Setting setting) {
        this.settings.add(setting);
    }
    
    public ArrayList<Setting> getSettings(final Module module) {
        final ArrayList<Setting> sets = new ArrayList<Setting>();
        for (final Setting setting : this.settings) {
            if (setting.getModule().equals(module)) {
                sets.add(setting);
            }
        }
        return sets;
    }
    
    public Setting getSetting(final String moduleName, final String name) {
        for (final Setting setting : this.settings) {
            if (setting.getModule().getName().equalsIgnoreCase(moduleName) && setting.getName().equalsIgnoreCase(name)) {
                return setting;
            }
        }
        return null;
    }
    
    public ArrayList<Setting> getSettings() {
        return this.settings;
    }
}
