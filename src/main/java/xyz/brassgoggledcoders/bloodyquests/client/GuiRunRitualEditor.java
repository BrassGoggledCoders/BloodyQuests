package xyz.brassgoggledcoders.bloodyquests.client;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import betterquesting.api.client.gui.GuiScreenThemed;
import betterquesting.api.client.gui.controls.GuiBigTextField;
import betterquesting.api.client.gui.misc.IVolatileScreen;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import xyz.brassgoggledcoders.bloodyquests.TaskRunRitual;

public class GuiRunRitualEditor extends GuiScreenThemed implements IVolatileScreen {
	TaskRunRitual task;
	private GuiTextField nameField;

	public GuiRunRitualEditor(GuiScreen parent, TaskRunRitual task) {
		super(parent, "bloodyquests.title.edit_runritual");
		this.task = task;
	}

	@Override
	public void initGui() {
		super.initGui();
		Keyboard.enableRepeatEvents(true);
		int i = (this.width - this.sizeX) / 2;
		int j = (this.height - this.sizeY) / 2;
		this.fontRendererObj.drawString("Ritual Name: ", i + 50, j + 34, 0);
		this.nameField = new GuiBigTextField(this.fontRendererObj, i + 82, j + 34, 103, 12);
		this.nameField.setMaxStringLength(30);
		this.nameField.setCanLoseFocus(false);
		this.nameField.setFocused(true);
		String txt = task.targetRitualName;
		if(txt != null)
			nameField.setText(txt);
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		this.task.targetRitualName = nameField.getText();
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.nameField.drawTextBox();
	}

	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		int i = (this.width - this.sizeX) / 2;
		int j = (this.height - this.sizeY) / 2;
		Gui.drawRect(i + 62, j + 24, 0, 0, Color.BLACK.getRGB());
	}

	@Override
	protected void mouseClicked(int mx, int my, int click) {
		super.mouseClicked(mx, my, click);

		nameField.mouseClicked(mx, my, click);
	}

	/**
	 * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
	 */
	@Override
	protected void keyTyped(char character, int keyCode) {
		super.keyTyped(character, keyCode);

		nameField.textboxKeyTyped(character, keyCode);
	}

}
