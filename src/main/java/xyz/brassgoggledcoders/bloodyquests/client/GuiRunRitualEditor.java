package xyz.brassgoggledcoders.bloodyquests.client;

import org.lwjgl.input.Keyboard;

import betterquesting.api.client.gui.misc.IVolatileScreen;
import betterquesting.api2.client.gui.GuiScreenCanvas;
import betterquesting.api2.client.gui.controls.PanelButton;
import betterquesting.api2.client.gui.controls.PanelTextField;
import betterquesting.api2.client.gui.controls.filters.FieldFilterString;
import betterquesting.api2.client.gui.misc.*;
import betterquesting.api2.client.gui.panels.CanvasEmpty;
import betterquesting.api2.client.gui.panels.CanvasTextured;
import betterquesting.api2.client.gui.panels.content.PanelTextBox;
import betterquesting.api2.client.gui.themes.presets.PresetColor;
import betterquesting.api2.client.gui.themes.presets.PresetTexture;
import betterquesting.api2.utils.QuestTranslation;
import net.minecraft.client.gui.GuiScreen;
import xyz.brassgoggledcoders.bloodyquests.TaskRunRitual;

public class GuiRunRitualEditor extends GuiScreenCanvas implements IVolatileScreen {
    TaskRunRitual task;

    public GuiRunRitualEditor(GuiScreen parent, TaskRunRitual task) {
        super(parent);
        this.task = task;
    }

    @Override
    public void initPanel() {
        super.initPanel();
        Keyboard.enableRepeatEvents(true);
        CanvasTextured cvBackground = new CanvasTextured(new GuiTransform(), PresetTexture.PANEL_MAIN.getTexture());
        this.addPanel(cvBackground);
        cvBackground.addPanel(new PanelTextBox(new GuiTransform(GuiAlign.TOP_EDGE, new GuiPadding(16, 16, 16, -32), 0),
                QuestTranslation.translate("bloodyquests.title.edit_run_ritual")).setAlignment(1)
                        .setColor(PresetColor.TEXT_HEADER.getColor()));
        cvBackground.addPanel(new PanelTextBox(new GuiTransform(GuiAlign.MID_LEFT, new GuiPadding(16, 16, 16, 0), 0),
                QuestTranslation.translate("bloodyquests.name.edit_run_ritual")).setAlignment(1)
                        .setColor(PresetColor.TEXT_MAIN.getColor()));
        cvBackground.addPanel(new PanelTextField<>(new GuiTransform(GuiAlign.MID_CENTER, -40, -8, 80, 16, 0),
                task.targetRitualName, FieldFilterString.INSTANCE).setCallback(value -> task.targetRitualName = value));
        cvBackground.addPanel(new PanelButton(new GuiTransform(GuiAlign.BOTTOM_CENTER, -100, -32, 200, 16, 0), -1,
                QuestTranslation.translate("gui.done")) {
            @Override
            public void onButtonClick() {
                mc.displayGuiScreen(parent);
            }
        });
        CanvasEmpty cvRight = new CanvasEmpty(new GuiTransform(GuiAlign.HALF_RIGHT, new GuiPadding(8, 32, 16, 24), 0));
        cvBackground.addPanel(cvRight);
        cvRight.addPanel(new PanelTextBox(new GuiTransform(GuiAlign.MID_CENTER, new GuiPadding(0, 4, 0, -16), 0),
                QuestTranslation.translate("bloodyquests.desc.edit_runritual"))
                        .setColor(PresetColor.TEXT_MAIN.getColor()));

    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
    }
}
