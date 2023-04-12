// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.api.setting;

import java.util.List;
import me.larp.dev.api.module.Module;

public class Setting
{
    private final String name;
    private final Module module;
    private final SettingType type;
    private boolean booleanValue;
    private int integerValue;
    private int minIntegerValue;
    private int maxIntegerValue;
    private String enumValue;
    private List<String> enumValues;
    
    public Setting(final String name, final Module module, final int intValue, final int intMinValue, final int intMaxValue) {
        this.name = name;
        this.module = module;
        this.integerValue = intValue;
        this.minIntegerValue = intMinValue;
        this.maxIntegerValue = intMaxValue;
        this.type = SettingType.INTEGER;
    }
    
    public Setting(final String name, final Module module, final boolean boolValue) {
        this.name = name;
        this.module = module;
        this.booleanValue = boolValue;
        this.type = SettingType.BOOLEAN;
    }
    
    public Setting(final String name, final Module module, final List<String> enumValues) {
        this.name = name;
        this.module = module;
        this.enumValue = enumValues.get(0);
        this.enumValues = enumValues;
        this.type = SettingType.ENUM;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Module getModule() {
        return this.module;
    }
    
    public SettingType getType() {
        return this.type;
    }
    
    public boolean getBooleanValue() {
        return this.booleanValue;
    }
    
    public void setBooleanValue(final boolean booleanValue) {
        this.booleanValue = booleanValue;
    }
    
    public int getIntegerValue() {
        return this.integerValue;
    }
    
    public void setIntegerValue(final int integerValue) {
        this.integerValue = integerValue;
    }
    
    public int getMinIntegerValue() {
        return this.minIntegerValue;
    }
    
    public int getMaxIntegerValue() {
        return this.maxIntegerValue;
    }
    
    public boolean isInteger() {
        return this.type.equals("int");
    }
    
    public boolean isBoolean() {
        return this.type.equals("boolean");
    }
    
    public boolean isEnum() {
        return this.type.equals("enum");
    }
    
    public String getEnumValue() {
        return this.enumValue;
    }
    
    public void setEnumValue(final String enumValue) {
        this.enumValue = (this.enumValues.contains(enumValue) ? enumValue : this.enumValue);
    }
    
    public List<String> getEnumValues() {
        return this.enumValues;
    }
    
    public boolean isEnabled() {
        return false;
    }
    
    public Object getValue() {
        return null;
    }
    
    public float getIntValue() {
        return 0.0f;
    }
    
    public boolean get_value(final boolean b) {
        return false;
    }
    
    public boolean getBoolValue() {
        return false;
    }
}
