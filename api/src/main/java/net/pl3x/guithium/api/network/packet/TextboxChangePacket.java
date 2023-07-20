package net.pl3x.guithium.api.network.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.gui.element.Textbox;
import net.pl3x.guithium.api.network.PacketListener;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a packet containing textbox change information.
 */
public class TextboxChangePacket extends Packet {
    /**
     * Unique identifying key
     */
    public static final Key KEY = Key.of("packet:textbox_change");

    private final Key screen;
    private final Key textbox;
    private final String value;

    /**
     * Creates a new textbox change packet.
     *
     * @param screen Screen textbox was changed on
     * @param textbox Textbox that was changed
     * @param value  New value of textbox
     */
    public TextboxChangePacket(@NotNull Screen screen, @NotNull Textbox textbox, String value) {
        super(KEY);
        this.screen = screen.getKey();
        this.textbox = textbox.getKey();
        this.value = value;
    }

    /**
     * Creates a new textbox change packet.
     *
     * @param in Input byte array
     */
    public TextboxChangePacket(@NotNull ByteArrayDataInput in) {
        super(KEY);
        this.screen = Key.of(in.readUTF());
        this.textbox = Key.of(in.readUTF());
        this.value = in.readUTF();
    }

    /**
     * Get the screen the textbox was changed on.
     *
     * @return Textbox's screen
     */
    @NotNull
    public Key getScreen() {
        return this.screen;
    }

    /**
     * Get the textbox that was changed.
     *
     * @return Changed textbox
     */
    @NotNull
    public Key getTextbox() {
        return this.textbox;
    }

    /**
     * Get the new value of the textbox.
     *
     * @return Textbox's new value
     */
    public String getValue() {
        return this.value;
    }

    @Override
    public void handle(@NotNull PacketListener listener) {
        listener.handleTextboxChange(this);
    }

    @Override
    @NotNull
    public ByteArrayDataOutput write() {
        ByteArrayDataOutput out = out(this);
        out.writeUTF(getScreen().toString());
        out.writeUTF(getTextbox().toString());
        out.writeUTF(getValue());
        return out;
    }
}
