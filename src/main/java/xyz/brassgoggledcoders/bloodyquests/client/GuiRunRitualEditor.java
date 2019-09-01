package xyz.brassgoggledcoders.bloodyquests.client;

import org.lwjgl.input.Keyboard;

import betterquesting.api.client.gui.misc.IVolatileScreen;
import betterquesting.api2.client.gui.GuiScreenCanvas;
import betterquesting.api2.client.gui.controls.PanelTextField;
import betterquesting.api2.client.gui.controls.filters.FieldFilterString;
import betterquesting.api2.client.gui.misc.GuiAlign;
import betterquesting.api2.client.gui.misc.GuiTransform;
import betterquesting.api2.client.gui.panels.content.PanelTextBox;
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
		this.addPanel(new PanelTextBox(new GuiTransform(GuiAlign.MID_LEFT, 0, 0, 10, 8, 0), "Ritual Name: "));
		this.addPanel(new PanelTextField<>(new GuiTransform(GuiAlign.MID_CENTER, 0, 0, 10, 8, 0), task.targetRitualName, FieldFilterString.INSTANCE).setCallback(value -> task.targetRitualName = value));
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		Keyboard.enableRepeatEvents(false);
	}
}
