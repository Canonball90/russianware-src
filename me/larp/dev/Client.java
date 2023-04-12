//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.io.IOException;
import me.larp.dev.api.EventHandler;
import net.minecraftforge.common.MinecraftForge;
import me.larp.dev.api.manager.ConfigManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import java.awt.Font;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import me.larp.dev.api.mainMenu.MainMenu;
import me.larp.dev.api.manager.FriendManager;
import me.larp.dev.api.manager.PopManager;
import me.larp.dev.api.command.CommandManager;
import me.larp.dev.api.clickgui.ClickGUI;
import me.larp.dev.api.clickgui.util.font.CustomFontRenderer;
import me.larp.dev.api.setting.SettingManager;
import me.larp.dev.api.module.ModuleManager;
import net.minecraftforge.fml.common.Mod;

@Mod(modid = "russianware", name = "RussianWare", version = "0.1")
public class Client
{
    public static final String MODID = "russianware";
    public static final String MODNAME = "RussianWare";
    public static final String MODVER = "0.1";
    public static final String DISPLAY = "RussianWare";
    public static ModuleManager moduleManager;
    public static SettingManager settingManager;
    public static CustomFontRenderer customFontRenderer;
    public static ClickGUI clickGUI;
    public static CommandManager commandManager;
    public static PopManager popManager;
    public static FriendManager friendManager;
    public static MainMenu menu;
    
    public static String getName() {
        return "RussianWare";
    }
    
    @Mod.EventHandler
    public void initialize(final FMLInitializationEvent event) throws IOException {
        Client.commandManager = new CommandManager();
        Client.settingManager = new SettingManager();
        Client.moduleManager = new ModuleManager();
        Client.friendManager = new FriendManager();
        Client.customFontRenderer = new CustomFontRenderer(new Font("Minecraft", 0, 19), true, false);
        Client.clickGUI = new ClickGUI();
        Client.menu = new MainMenu();
        if (event.getSide().isClient()) {
            FMLCommonHandler.instance().bus().register((Object)this);
        }
        ConfigManager.loadConfig();
        Runtime.getRuntime().addShutdownHook(new ConfigManager());
        MinecraftForge.EVENT_BUS.register((Object)new EventHandler());
    }
    
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onTick(final TickEvent.ClientTickEvent event) {
        final Minecraft mc = FMLClientHandler.instance().getClient();
        if (mc.currentScreen != null && mc.currentScreen.getClass().equals(GuiMainMenu.class)) {
            mc.displayGuiScreen((GuiScreen)new MainMenu());
        }
    }
    
    public static ModuleManager getModuleManager() {
        return Client.moduleManager;
    }
    
    public static FriendManager getFriendManager() {
        return Client.friendManager;
    }
}
