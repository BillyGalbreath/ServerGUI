package net.pl3x.guithium.api.network;

import net.pl3x.guithium.api.network.packet.ButtonClickPacket;
import net.pl3x.guithium.api.network.packet.CheckboxTogglePacket;
import net.pl3x.guithium.api.network.packet.CloseScreenPacket;
import net.pl3x.guithium.api.network.packet.ElementPacket;
import net.pl3x.guithium.api.network.packet.HelloPacket;
import net.pl3x.guithium.api.network.packet.OpenScreenPacket;
import net.pl3x.guithium.api.network.packet.RadioTogglePacket;
import net.pl3x.guithium.api.network.packet.SliderChangePacket;
import net.pl3x.guithium.api.network.packet.TextboxChangePacket;
import net.pl3x.guithium.api.network.packet.TexturesPacket;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a packet listener.
 */
public interface PacketListener {
    /**
     * Handle button click packet.
     *
     * @param packet Button click packet to handle
     */
    void handleButtonClick(@NotNull ButtonClickPacket packet);

    /**
     * Handle checkbox toggle packet.
     *
     * @param packet Checkbox toggle packet to handle
     */
    void handleCheckboxToggle(@NotNull CheckboxTogglePacket packet);

    /**
     * Handle clock screen packet.
     *
     * @param packet Close screen packet to handle
     */
    void handleCloseScreen(@NotNull CloseScreenPacket packet);

    /**
     * Handle update element packet.
     *
     * @param packet Update element packet to handle
     */
    void handleElement(@NotNull ElementPacket packet);

    /**
     * Handle hello packet.
     *
     * @param packet Hello packet to handle
     */
    void handleHello(@NotNull HelloPacket packet);

    /**
     * Handle open screen packet.
     *
     * @param packet Open screen packet to handle
     */
    void handleOpenScreen(@NotNull OpenScreenPacket packet);

    /**
     * Handle radio toggle packet.
     *
     * @param packet Radio toggle packet to handle
     */
    void handleRadioToggle(@NotNull RadioTogglePacket packet);

    /**
     * Handle Slider change packet.
     *
     * @param packet Slider change packet to handle
     */
    void handleSliderChange(@NotNull SliderChangePacket packet);

    /**
     * Handle Textbox change packet.
     *
     * @param packet Textbox change packet to handle
     */
    void handleTextboxChange(@NotNull TextboxChangePacket packet);

    /**
     * Handle texture preload packet.
     *
     * @param packet Texture preload packet to handle
     */
    void handleTextures(@NotNull TexturesPacket packet);
}
