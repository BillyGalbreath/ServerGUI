package net.pl3x.guithium.api.action.actions.player.screen.element;

import net.pl3x.guithium.api.action.RegisteredHandler;
import net.pl3x.guithium.api.action.actions.Cancellable;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.gui.element.Textbox;
import net.pl3x.guithium.api.player.WrappedPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Action that fires when a textbox is changed.
 */
public class TextboxChangedAction extends ElementAction implements Cancellable {
    private static final List<RegisteredHandler> handlers = new ArrayList<>();

    private String value;
    private boolean cancelled;

    /**
     * Creates a new action for when a textbox is changed.
     *
     * @param player Player that performed the action
     * @param screen Screen action was performed on
     * @param textbox Textbox action was performed on
     * @param value  New value of textbox
     */
    public TextboxChangedAction(WrappedPlayer player, Screen screen, Textbox textbox, String value) {
        super(player, screen, textbox);
        this.value = value;
    }

    /**
     * Get the textbox that was changed.
     *
     * @return Changed textbox
     */
    @NotNull
    public Textbox getElement() {
        return (Textbox) super.getElement();
    }

    /**
     * Get the new value of this textbox change.
     *
     * @return New textbox value
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Set the new value of this textbox change.
     *
     * @param value New textbox value
     */
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public @NotNull List<RegisteredHandler> getHandlers() {
        return handlers;
    }
}
