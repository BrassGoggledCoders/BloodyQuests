package xyz.brassgoggledcoders.bloodyquests.client;

import betterquesting.api.client.gui.GuiElement;
import betterquesting.api.client.gui.misc.IGuiEmbedded;
import betterquesting.client.themes.ThemeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import xyz.brassgoggledcoders.bloodyquests.TaskRunRitual;

public class GuiTaskRunRitual extends GuiElement implements IGuiEmbedded {
	private Minecraft mc;
	private String targetRitualName;

	private int posX = 0;
	private int posY = 0;
	private int sizeX = 0;

	public GuiTaskRunRitual(TaskRunRitual task, int posX, int posY, int sizeX, int sizeY) {
		this.mc = Minecraft.getMinecraft();
		targetRitualName = task.targetRitualName;
		this.posX = posX;
		this.posY = posY;
		this.sizeX = sizeX;
	}

	@Override
	public void drawBackground(int arg0, int arg1, float arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawForeground(int mx, int my, float partialTick) {
		mc.fontRenderer.drawString(I18n.format("bloodyquests.task.runritual", new Object[0]),
				posX + sizeX / 2 - mc.fontRenderer.getStringWidth(I18n.format("bloodyquests.task.runritual", "")) / 2,
				posY, ThemeRegistry.INSTANCE.getCurrentTheme().getTextColor());
		mc.fontRenderer.drawString("Ritual: " + targetRitualName,
				posX + sizeX / 2
						- mc.fontRenderer.getStringWidth("Ritual: " + I18n.format(targetRitualName, new Object[0])) / 2,
				posY + 20, ThemeRegistry.INSTANCE.getCurrentTheme().getTextColor());
	}

	@Override
	public void onKeyTyped(char arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMouseClick(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMouseScroll(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}