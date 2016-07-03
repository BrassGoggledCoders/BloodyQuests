package xyz.brassgoggledcoders.bloodyquests.client;

import org.lwjgl.input.Keyboard;

import com.google.gson.JsonObject;

import betterquesting.client.gui.GuiQuesting;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class GuiRunRitualEditor extends GuiQuesting {
	private GuiTextField nameField;
	JsonObject data;

	public GuiRunRitualEditor(GuiScreen parent, JsonObject data) {
		super(parent, "bloodyquests.title.edit_runritual");
		this.data = data;
	}

	@Override
	public void initGui() {
		super.initGui();
		Keyboard.enableRepeatEvents(true);
		int i = (this.width - this.sizeX) / 2;
		int j = (this.height - this.sizeY) / 2;
		// this.fontRendererObj.drawString("Ritual Name: ", i + 50, j + 34, 0);
		this.nameField = new GuiTextField(this.fontRendererObj, i + 82, j + 34, 103, 12);
		// this.nameField.setMaxStringLength(30);
		// this.nameField.setCanLoseFocus(false);
		this.nameField.setFocused(true);
		this.nameField.setTextColor(-1);
		this.nameField.setDisabledTextColour(-1);
		// this.nameField.setText("AW001Water");
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		this.data.addProperty("targetRitualName", this.nameField.getText());
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.nameField.drawTextBox();
	}

	@Override
	protected void keyTyped(char c, int codePoint) {
		if(this.nameField.textboxKeyTyped(c, codePoint) && Character.isAlphabetic(codePoint)) {
			this.nameField.setText(this.nameField.getText() + c);
		}
		else {
			super.keyTyped(c, codePoint);
		}
	}
}
