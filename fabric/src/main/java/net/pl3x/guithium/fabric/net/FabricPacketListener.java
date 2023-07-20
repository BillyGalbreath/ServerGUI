package net.pl3x.guithium.fabric.net;

import net.minecraft.client.Minecraft;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.gui.element.Element;
import net.pl3x.guithium.api.network.PacketListener;
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
import net.pl3x.guithium.fabric.Guithium;
import net.pl3x.guithium.fabric.gui.element.RenderableElement;
import net.pl3x.guithium.fabric.gui.screen.RenderableScreen;
import org.jetbrains.annotations.NotNull;

public class FabricPacketListener implements PacketListener {
    @Override
    public void handleButtonClick(@NotNull ButtonClickPacket packet) {
        // server does not send this packet to the client
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void handleCheckboxToggle(@NotNull CheckboxTogglePacket packet) {
        // server does not send this packet to the client
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void handleCloseScreen(@NotNull CloseScreenPacket packet) {
        // try to remove from HUD
        if (Guithium.instance().getHudManager().remove(packet.getScreenKey()) == null) {
            // not on HUD, so lets get current open screen
            if (Minecraft.getInstance().screen instanceof RenderableScreen screen) {
                // check if screen keys match
                if (screen.getScreen().getKey().equals(packet.getScreenKey())) {
                    // close the screen
                    screen.close();
                }
            }
        }
    }

    @Override
    public void handleElement(@NotNull ElementPacket packet) {
        Element element = packet.getElement();

        if (Minecraft.getInstance().screen instanceof RenderableScreen currentScreen) {
            RenderableElement renderableElement = currentScreen.getElements().get(element.getKey());
            if (renderableElement != null) {
                renderableElement.updateElement(element);
                return;
            }
        }

        for (RenderableScreen renderableScreen : Guithium.instance().getHudManager().getAll().values()) {
            RenderableElement renderableElement = renderableScreen.getElements().get(element.getKey());
            if (renderableElement != null) {
                renderableElement.updateElement(element);
            }
        }
    }

    @Override
    public void handleHello(@NotNull HelloPacket packet) {
        int protocol = packet.getProtocol();
        if (protocol == net.pl3x.guithium.api.Guithium.PROTOCOL) {
            System.out.println("Server responded with correct protocol (" + protocol + ")");
        } else {
            System.out.println("Server responded with a different protocol (" + protocol + ")");
            /*Minecraft.getInstance().getToasts().addToast(
                new SystemToast(
                    SystemToast.SystemToastIds.TUTORIAL_HINT,
                    Component.literal("Guithium Error"),
                    Component.literal("Protocol mismatch. Disabling mod.")
                )
            );*/
        }
    }

    @Override
    public void handleOpenScreen(@NotNull OpenScreenPacket packet) {
        Screen screen = packet.getScreen();

        RenderableScreen renderableScreen = new RenderableScreen(screen);

        if (screen.getType() == Screen.Type.HUD) {
            Guithium.instance().getHudManager().add(renderableScreen);
        } else {
            renderableScreen.open();
        }
    }

    @Override
    public void handleRadioToggle(@NotNull RadioTogglePacket packet) {
        // server does not send this packet to the client
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void handleSliderChange(@NotNull SliderChangePacket packet) {
        // server does not send this packet to the client
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void handleTextboxChange(@NotNull final TextboxChangePacket packet) {
        // server does not send this packet to the client
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void handleTextures(@NotNull TexturesPacket packet) {
        packet.getTextures().forEach((key, texture) -> {
            // add texture to manager
            Guithium.instance().getTextureManager().add(key, texture.getUrl());
        });
    }
}
