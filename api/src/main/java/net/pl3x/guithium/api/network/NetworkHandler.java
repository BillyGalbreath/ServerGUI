package net.pl3x.guithium.api.network;

import com.google.common.io.ByteArrayDataInput;
import net.pl3x.guithium.api.Guithium;
import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.network.packet.ButtonClickPacket;
import net.pl3x.guithium.api.network.packet.CheckboxTogglePacket;
import net.pl3x.guithium.api.network.packet.CloseScreenPacket;
import net.pl3x.guithium.api.network.packet.ElementPacket;
import net.pl3x.guithium.api.network.packet.HelloPacket;
import net.pl3x.guithium.api.network.packet.OpenScreenPacket;
import net.pl3x.guithium.api.network.packet.Packet;
import net.pl3x.guithium.api.network.packet.RadioTogglePacket;
import net.pl3x.guithium.api.network.packet.SliderChangePacket;
import net.pl3x.guithium.api.network.packet.TextboxChangePacket;
import net.pl3x.guithium.api.network.packet.TexturesPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Represents a network handler.
 */
public abstract class NetworkHandler {
    /**
     * Identifier for Guithium's plugin messaging channel.
     */
    public final static Key CHANNEL = Key.of(Guithium.MOD_ID + ":" + Guithium.MOD_ID);

    private final Map<Key, Function<ByteArrayDataInput, ? extends Packet>> packets = new HashMap<>();

    /**
     * Creates a new network handler
     */
    public NetworkHandler() {
        // register incoming packet handlers
        registerHandler(ButtonClickPacket.KEY, ButtonClickPacket::new);
        registerHandler(CheckboxTogglePacket.KEY, CheckboxTogglePacket::new);
        registerHandler(CloseScreenPacket.KEY, CloseScreenPacket::new);
        registerHandler(ElementPacket.KEY, ElementPacket::new);
        registerHandler(HelloPacket.KEY, HelloPacket::new);
        registerHandler(OpenScreenPacket.KEY, OpenScreenPacket::new);
        registerHandler(RadioTogglePacket.KEY, RadioTogglePacket::new);
        registerHandler(SliderChangePacket.KEY, SliderChangePacket::new);
        registerHandler(TextboxChangePacket.KEY, TextboxChangePacket::new);
        registerHandler(TexturesPacket.KEY, TexturesPacket::new);
    }

    /**
     * Register the network packet listeners
     */
    public abstract void register();

    /**
     * Register incoming packet handlers.
     *
     * @param key      Identifying key
     * @param function Packet handler
     */
    protected void registerHandler(@NotNull Key key, @NotNull Function<ByteArrayDataInput, ? extends Packet> function) {
        this.packets.put(key, function);
    }

    /**
     * Receive data from server.
     *
     * @param listener Packet listener
     * @param in       Data received
     */
    public void receive(@NotNull PacketListener listener, @NotNull ByteArrayDataInput in) {
        Packet packet = getPacket(in);
        if (packet == null) {
            System.out.printf("Received unknown packet from server%n");
            return;
        }

        // handle packet
        packet.handle(listener);
    }

    /**
     * Create a new packet from input byte array.
     *
     * @param in Input byte array
     * @return New packet, or null if unknown packet data
     */
    @Nullable
    protected Packet getPacket(@NotNull ByteArrayDataInput in) {
        // verify protocol
        int protocol = in.readInt();
        if (protocol != Guithium.PROTOCOL) {
            System.out.printf("Received packet with invalid protocol (%d) from server%n", protocol);
            return null;
        }

        // get registered packet handler
        Key packetId = Key.of(in.readUTF());
        Function<ByteArrayDataInput, ? extends Packet> function = this.packets.get(packetId);
        Packet packet = function == null ? null : function.apply(in);
        if (packet == null) {
            System.out.printf("Received unknown packet (%s) from player%n", packetId);
            return null;
        }
        return packet;
    }
}
