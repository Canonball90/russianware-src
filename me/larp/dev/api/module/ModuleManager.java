// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.api.module;

import java.util.stream.Collector;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.lang.reflect.Field;
import java.util.Iterator;
import me.larp.dev.Client;
import me.larp.dev.api.setting.Setting;
import me.larp.dev.impl.module.hud.Hud;
import me.larp.dev.impl.module.hud.ClickGUI;
import me.larp.dev.impl.module.render.FullBright;
import me.larp.dev.impl.module.render.NoRender;
import me.larp.dev.impl.module.render.ItemViewModle;
import me.larp.dev.impl.module.render.HoleESP;
import me.larp.dev.impl.module.render.VoidESP;
import me.larp.dev.impl.module.render.Chams;
import me.larp.dev.impl.module.movement.OldfagNoFall;
import me.larp.dev.impl.module.movement.Sprint;
import me.larp.dev.impl.module.movement.EntityFly;
import me.larp.dev.impl.module.movement.ReverseStep;
import me.larp.dev.impl.module.movement.Step;
import me.larp.dev.impl.module.movement.NoSlow;
import me.larp.dev.impl.module.movement.AutoWalk;
import me.larp.dev.impl.module.movement.AutoJump;
import me.larp.dev.impl.module.movement.Anchor;
import me.larp.dev.impl.module.misc.DiscordRPCModule;
import me.larp.dev.impl.module.misc.FakePlayer;
import me.larp.dev.impl.module.misc.FastUse;
import me.larp.dev.impl.module.misc.GreenText;
import me.larp.dev.impl.module.misc.ChatSuffix;
import me.larp.dev.impl.module.misc.ChatStyle;
import me.larp.dev.impl.module.exploit.Burrow;
import me.larp.dev.impl.module.exploit.EchestBP;
import me.larp.dev.impl.module.exploit.QueueSkip;
import me.larp.dev.impl.module.exploit.AntiHunger;
import me.larp.dev.impl.module.exploit.PacketMine;
import me.larp.dev.impl.module.exploit.PacketCanceller;
import me.larp.dev.impl.module.combat.Velocity;
import me.larp.dev.impl.module.combat.Criticals;
import me.larp.dev.impl.module.combat.KillAura;
import me.larp.dev.impl.module.exploit.HitBox;
import me.larp.dev.impl.module.combat.CrystalAura;
import me.larp.dev.impl.module.combat.AutoCrystal2b2t;
import me.larp.dev.impl.module.combat.Quiver;
import me.larp.dev.impl.module.combat.SmartOffhand;
import me.larp.dev.impl.module.combat.Offhand;
import me.larp.dev.impl.module.combat.AutoTrap;
import me.larp.dev.impl.module.combat.AutoTotem;
import java.util.ArrayList;

public class ModuleManager
{
    private final ArrayList<Module> modules;
    
    public ModuleManager() {
        (this.modules = new ArrayList<Module>()).add(new AutoTotem());
        this.modules.add(new AutoTrap());
        this.modules.add(new Offhand());
        this.modules.add(new SmartOffhand());
        this.modules.add(new Quiver());
        this.modules.add(new AutoCrystal2b2t());
        this.modules.add(new CrystalAura());
        this.modules.add(new HitBox());
        this.modules.add(new KillAura());
        this.modules.add(new Criticals());
        this.modules.add(new Velocity());
        this.modules.add(new PacketCanceller());
        this.modules.add(new PacketMine());
        this.modules.add(new AntiHunger());
        this.modules.add(new QueueSkip());
        this.modules.add(new EchestBP());
        this.modules.add(new Burrow());
        this.modules.add(new ChatStyle());
        this.modules.add(new ChatSuffix());
        this.modules.add(new GreenText());
        this.modules.add(new FastUse());
        this.modules.add(new FakePlayer());
        this.modules.add(new DiscordRPCModule());
        this.modules.add(new Anchor());
        this.modules.add(new AutoJump());
        this.modules.add(new AutoWalk());
        this.modules.add(new NoSlow());
        this.modules.add(new Step());
        this.modules.add(new ReverseStep());
        this.modules.add(new EntityFly());
        this.modules.add(new Sprint());
        this.modules.add(new OldfagNoFall());
        this.modules.add(new Chams());
        this.modules.add(new VoidESP());
        this.modules.add(new HoleESP());
        this.modules.add(new ItemViewModle());
        this.modules.add(new NoRender());
        this.modules.add(new FullBright());
        this.modules.add(new ClickGUI("ClickGUI", "Toggle modules by clicking on them", Category.HUD));
        this.modules.add(new Hud());
        for (final Module module : this.modules) {
            for (final Field declaredField : module.getClass().getDeclaredFields()) {
                declaredField.setAccessible(true);
                if (declaredField.getType() == Setting.class) {
                    try {
                        Client.settingManager.addSetting((Setting)declaredField.get(module));
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    
    public static boolean isModuleEnabled(final String zoom) {
        return false;
    }
    
    public ArrayList<Module> getModules() {
        return this.modules;
    }
    
    public Module getModule(final String name) {
        for (final Module module : this.modules) {
            if (module.getName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }
    
    public ArrayList<Module> getModules(final Category category) {
        final ArrayList<Module> mods = new ArrayList<Module>();
        for (final Module module : this.modules) {
            if (module.getCategory().equals(category)) {
                mods.add(module);
            }
        }
        return mods;
    }
    
    public ArrayList<Module> getEnabledModules() {
        return this.modules.stream().filter(Module::isEnabled).collect((Collector<? super Object, ?, ArrayList<Module>>)Collectors.toCollection((Supplier<R>)ArrayList::new));
    }
    
    public Module getModuleByName(final String moduleName) {
        return null;
    }
}
