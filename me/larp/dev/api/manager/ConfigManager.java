//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.api.manager;

import me.larp.dev.api.setting.Setting;
import java.util.Iterator;
import me.larp.dev.api.util.FriendUtil;
import java.io.IOException;
import me.larp.dev.api.util.FileUtil;
import java.util.stream.Collector;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.function.Function;
import me.larp.dev.api.module.Module;
import me.larp.dev.Client;
import java.util.ArrayList;
import java.io.File;
import net.minecraft.client.Minecraft;

public class ConfigManager extends Thread
{
    private static final Minecraft mc;
    private static final File mainFolder;
    private static final String ENABLED_MODULES = "EnabledModules.txt";
    private static final String SETTINGS = "Settings.txt";
    private static final String BINDS = "Binds.txt";
    private static final String FRIENDS = "Friends.txt";
    
    @Override
    public void run() {
        if (!ConfigManager.mainFolder.exists() && !ConfigManager.mainFolder.mkdirs()) {
            System.out.println("Failed to create config folder");
        }
        try {
            FileUtil.saveFile(new File(ConfigManager.mainFolder.getAbsolutePath(), "EnabledModules.txt"), Client.moduleManager.getEnabledModules().stream().map((Function<? super Object, ?>)Module::getName).collect((Collector<? super Object, ?, ArrayList<String>>)Collectors.toCollection((Supplier<R>)ArrayList::new)));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileUtil.saveFile(new File(ConfigManager.mainFolder.getAbsolutePath(), "Settings.txt"), getSettings());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileUtil.saveFile(new File(ConfigManager.mainFolder.getAbsolutePath(), "Binds.txt"), Client.moduleManager.getModules().stream().map(module -> module.getName() + ":" + module.getBind()).collect((Collector<? super Object, ?, ArrayList<String>>)Collectors.toCollection((Supplier<R>)ArrayList::new)));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileUtil.saveFile(new File(ConfigManager.mainFolder.getAbsolutePath(), "Friends.txt"), Client.friendManager.getFriends().stream().map((Function<? super Object, ?>)FriendUtil::getName).collect((Collector<? super Object, ?, ArrayList<String>>)Collectors.toCollection((Supplier<R>)ArrayList::new)));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void loadConfig() throws IOException {
        if (!ConfigManager.mainFolder.exists()) {
            return;
        }
        try {
            for (final String s : FileUtil.loadFile(new File(ConfigManager.mainFolder.getAbsolutePath(), "EnabledModules.txt"))) {
                try {
                    Client.moduleManager.getModule(s).enable();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            for (final String s : FileUtil.loadFile(new File(ConfigManager.mainFolder.getAbsolutePath(), "Settings.txt"))) {
                try {
                    final String[] split = s.split(":");
                    saveSetting(Client.settingManager.getSetting(split[1], split[0]), split[2]);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            for (final String s : FileUtil.loadFile(new File(ConfigManager.mainFolder.getAbsolutePath(), "Binds.txt"))) {
                try {
                    final String[] split = s.split(":");
                    Client.moduleManager.getModule(split[0]).setBind(Integer.parseInt(split[1]));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            for (final String s : FileUtil.loadFile(new File(ConfigManager.mainFolder.getAbsolutePath(), "Friends.txt"))) {
                try {
                    Client.getFriendManager().addFriend(s);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
    }
    
    private static ArrayList<String> getSettings() {
        final ArrayList<String> content = new ArrayList<String>();
        for (final Setting setting : Client.settingManager.getSettings()) {
            switch (setting.getType()) {
                case BOOLEAN: {
                    content.add(String.format("%s:%s:%s", setting.getName(), setting.getModule().getName(), setting.getBooleanValue()));
                    continue;
                }
                case INTEGER: {
                    content.add(String.format("%s:%s:%s", setting.getName(), setting.getModule().getName(), setting.getIntegerValue()));
                    continue;
                }
                case ENUM: {
                    content.add(String.format("%s:%s:%s", setting.getName(), setting.getModule().getName(), setting.getEnumValue()));
                    continue;
                }
            }
        }
        return content;
    }
    
    private static void saveSetting(final Setting setting, final String value) {
        switch (setting.getType()) {
            case BOOLEAN: {
                setting.setBooleanValue(Boolean.parseBoolean(value));
                break;
            }
            case INTEGER: {
                setting.setIntegerValue(Integer.parseInt(value));
                break;
            }
            case ENUM: {
                setting.setEnumValue(value);
                break;
            }
        }
    }
    
    static {
        mc = Minecraft.getMinecraft();
        mainFolder = new File(ConfigManager.mc.gameDir + File.separator + "Project-Larp");
    }
}
