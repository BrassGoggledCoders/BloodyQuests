package xyz.brassgoggledcoders.bloodyquests.client;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import com.google.gson.JsonObject;

import betterquesting.client.gui.GuiQuesting;
import betterquesting.utils.JsonHelper;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;

public class GuiRunRitualEditor extends GuiQuesting
{
	private GuiTextField nameField;
	JsonObject data;
	
	public GuiRunRitualEditor(GuiScreen parent, JsonObject data)
	{
		super(parent, "bloodyquests.title.edit_runritual");
		this.data = data;
	}
	
	@Override
	public void initGui()
	{
		super.initGui();
		Keyboard.enableRepeatEvents(true);
        int i = (this.width - this.sizeX) / 2;
        int j = (this.height - this.sizeY) / 2;
        this.fontRendererObj.drawString("Ritual Name: ", i + 50, j + 34, 0);
        this.nameField = new GuiTextField(0, this.fontRendererObj, i + 82, j + 34, 103, 12);
        this.nameField.setMaxStringLength(30);
        this.nameField.setCanLoseFocus(false);
        this.nameField.setFocused(true);
        String txt = JsonHelper.GetString(data, "targetRitualName", "");
		if(txt != null)
		nameField.setText(txt);
	}

    @Override
	public void onGuiClosed()
    {
        super.onGuiClosed();
        this.data.addProperty("targetRitualName", this.nameField.getText());
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
        this.nameField.drawTextBox();
    }
    
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        int i = (this.width - this.sizeX) / 2;
        int j = (this.height - this.sizeY) / 2;
        Gui.drawRect(i + 62, j + 24, 0, 0, Color.BLACK.getRGB());
    }
}

