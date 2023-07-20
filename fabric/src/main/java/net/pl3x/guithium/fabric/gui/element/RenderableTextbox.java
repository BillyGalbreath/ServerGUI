package net.pl3x.guithium.fabric.gui.element;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.pl3x.guithium.api.gui.Vec2;
import net.pl3x.guithium.api.gui.element.Textbox;
import net.pl3x.guithium.api.network.packet.TextboxChangePacket;
import net.pl3x.guithium.fabric.Guithium;
import net.pl3x.guithium.fabric.gui.screen.RenderableScreen;
import org.jetbrains.annotations.NotNull;

public class RenderableTextbox extends RenderableWidget {
    private MutableComponent suggestion;

    public RenderableTextbox(@NotNull RenderableScreen screen, @NotNull Textbox textbox) {
        super(screen, textbox);
    }

    @Override
    @NotNull
    public Textbox getElement() {
        return (Textbox) super.getElement();
    }

    @Override
    @NotNull
    public EditBox getWidget() {
        return (EditBox) super.getWidget();
    }

    @Override
    public void init(@NotNull Minecraft client, int width, int height) {
        this.suggestion = adventureToVanilla(getElement().getSuggestion());

        Vec2 size = getElement().getSize();
        if (size == null) {
            size = Vec2.of(120, 20);
        }

        size = Vec2.of(size.getX() - 2, size.getY() - 2);

        calcScreenPos(
                size.getX(),
                size.getY()
        );

        this.centerX = (int) (this.posX + size.getX() / 2);
        this.centerX = (int) (this.posY + size.getY() / 2);

        EditBox editbox = new EditBox(
                client.font,
                this.posX + 1,
                this.posY + 1,
                (int) size.getX(),
                (int) size.getY(),
                Component.empty()
        ) {
            @Override
            public void render(GuiGraphics gfx, int mouseX, int mouseY, float delta) {
                if (!this.visible) {
                    return;
                }
                rotate(gfx, this.getX(), this.getY(), this.width, this.height, getElement().getRotation());
                scale(gfx, this.getX(), this.getY(), this.width, this.height, getElement().getScale());
                this.isHovered = mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + this.width && mouseY < this.getY() + this.height;
                renderWidget(gfx, mouseX, mouseY, delta);
            }

            @Override
            public void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
                // do nothing
            }
        };

        setWidget(editbox);

        editbox.setValue(getElement().getValue() == null ? "" : getElement().getValue());
        editbox.setHint(this.suggestion);
        editbox.setBordered(getElement().isBordered() == null || Boolean.TRUE.equals(getElement().isBordered()));
        editbox.setCanLoseFocus(getElement().canLoseFocus() == null || Boolean.TRUE.equals(getElement().canLoseFocus()));
        editbox.setEditable(getElement().isEditable() == null || Boolean.TRUE.equals(getElement().isEditable()));
        if (getElement().getMaxLength() != null) {
            editbox.setMaxLength(getElement().getMaxLength());
        }
        if (getElement().getTextColor() != null) {
            editbox.setTextColor(getElement().getTextColor());
        }
        if (getElement().getTextColorUneditable() != null) {
            editbox.setTextColorUneditable(getElement().getTextColorUneditable());
        }
        if (getElement().onChange() != null) {
            editbox.setResponder(value -> {
                Guithium.instance().getNetworkHandler().getConnection()
                    .send(new TextboxChangePacket(getScreen().getScreen(), getElement(), value));
            });
        }
    }
}
