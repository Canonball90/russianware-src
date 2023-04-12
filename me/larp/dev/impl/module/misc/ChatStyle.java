//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\natha\Downloads\Minecraft-Deobfuscator3000-master\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.larp.dev.impl.module.misc;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import java.util.Date;
import java.text.SimpleDateFormat;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import java.util.Arrays;
import me.larp.dev.api.module.Category;
import me.larp.dev.api.setting.Setting;
import me.larp.dev.api.module.Module;

public class ChatStyle extends Module
{
    private final Setting timestamp;
    private final Setting bracketStyle;
    private final Setting timestampColor;
    private final Setting timestampBracket;
    private final Setting timestampBracketColor;
    private final Setting bracketColor;
    private final Setting nameColor;
    private final Setting messageColor;
    
    public ChatStyle() {
        super("ChatStyle", "", Category.MISC);
        this.timestamp = new Setting("Timestamp", this, false);
        this.bracketStyle = new Setting("Brackets", this, Arrays.asList("<->", "[-]", "{-}", ":", "None"));
        this.timestampColor = new Setting("TimeColor", this, Arrays.asList("Black", "Red", "Aqua", "Blue", "Gold", "Gray", "White", "Green", "Yellow", "Purple", "DRed", "DAqua", "DBlue", "DGray", "DGreen", "DPurple", "Reset"));
        this.timestampBracket = new Setting("TBracket", this, Arrays.asList("<->", "[-]", "{-}", "None"));
        this.timestampBracketColor = new Setting("TBColor", this, Arrays.asList("Black", "Red", "Aqua", "Blue", "Gold", "Gray", "White", "Green", "Yellow", "Purple", "DRed", "DAqua", "DBlue", "DGray", "DGreen", "DPurple", "Reset"));
        this.bracketColor = new Setting("BColor", this, Arrays.asList("Black", "Red", "Aqua", "Blue", "Gold", "Gray", "White", "Green", "Yellow", "Purple", "DRed", "DAqua", "DBlue", "DGray", "DGreen", "DPurple", "Reset"));
        this.nameColor = new Setting("NameColor", this, Arrays.asList("Black", "Red", "Aqua", "Blue", "Gold", "Gray", "White", "Green", "Yellow", "Purple", "DRed", "DAqua", "DBlue", "DGray", "DGreen", "DPurple", "Reset"));
        this.messageColor = new Setting("MsgColor", this, Arrays.asList("Black", "Red", "Aqua", "Blue", "Gold", "Gray", "White", "Green", "Yellow", "Purple", "DRed", "DAqua", "DBlue", "DGray", "DGreen", "DPurple", "Reset"));
    }
    
    @SubscribeEvent
    public void onChatReceive(final ClientChatReceivedEvent event) {
        if (this.mc.player == null || this.mc.world == null) {
            return;
        }
        final String fullMessage = event.getMessage().getUnformattedText();
        final String firstPart = fullMessage.split(" ")[0];
        final String name = firstPart.replace("<", "").replace(">", "");
        final String messageContent = fullMessage.replace(firstPart, "");
        final String time = new SimpleDateFormat("k:mm:ss").format(new Date());
        final StringBuilder message = new StringBuilder();
        if (!fullMessage.startsWith("<")) {
            return;
        }
        if (this.timestamp.getBooleanValue()) {
            this.add(time, message, this.timestampBracketColor, this.timestampBracket, this.timestampColor);
            final String enumValue = this.timestampBracket.getEnumValue();
            switch (enumValue) {
                case "<->": {
                    message.append(">");
                    break;
                }
                case "[-]": {
                    message.append("]");
                    break;
                }
                case "{-}": {
                    message.append("}");
                    break;
                }
            }
            message.append(" ");
        }
        this.add(name, message, this.bracketColor, this.bracketStyle, this.nameColor);
        final String enumValue2 = this.bracketStyle.getEnumValue();
        switch (enumValue2) {
            case "<->": {
                message.append(">");
                break;
            }
            case "[-]": {
                message.append("]");
                break;
            }
            case "{-}": {
                message.append("}");
                break;
            }
            case ":": {
                message.append(":");
                break;
            }
        }
        message.append(this.getColor(this.messageColor.getEnumValue()));
        message.append(messageContent);
        event.setMessage((ITextComponent)new TextComponentString(message.toString()));
    }
    
    private void add(final String name, final StringBuilder message, final Setting bracketColor, final Setting bracketStyle, final Setting nameColor) {
        message.append(this.getColor(bracketColor.getEnumValue()));
        final String enumValue = bracketStyle.getEnumValue();
        switch (enumValue) {
            case "<->": {
                message.append("<");
                break;
            }
            case "[-]": {
                message.append("[");
                break;
            }
            case "{-}": {
                message.append("{");
                break;
            }
        }
        message.append(this.getColor(nameColor.getEnumValue()));
        message.append(name);
        message.append(this.getColor(bracketColor.getEnumValue()));
    }
    
    public String getColor(final String color) {
        final StringBuilder text = new StringBuilder("§");
        switch (color) {
            case "Black": {
                text.append("0");
                break;
            }
            case "DBlue": {
                text.append("1");
                break;
            }
            case "DGreen": {
                text.append("2");
                break;
            }
            case "DAqua": {
                text.append("3");
                break;
            }
            case "DRed": {
                text.append("4");
                break;
            }
            case "DPurple": {
                text.append("5");
                break;
            }
            case "Gold": {
                text.append("6");
                break;
            }
            case "Gray": {
                text.append("7");
                break;
            }
            case "DGray": {
                text.append("8");
                break;
            }
            case "Blue": {
                text.append("9");
                break;
            }
            case "Green": {
                text.append("a");
                break;
            }
            case "Aqua": {
                text.append("b");
                break;
            }
            case "Red": {
                text.append("c");
                break;
            }
            case "Purple": {
                text.append("d");
                break;
            }
            case "Yellow": {
                text.append("e");
                break;
            }
            case "White": {
                text.append("f");
                break;
            }
            case "Reset": {
                text.append("r");
                break;
            }
        }
        return text.toString();
    }
}
