package xyz.brassgoggledcoders.bloodyquests.client;

import betterquesting.client.gui.GuiQuesting;
import betterquesting.client.gui.misc.GuiEmbedded;
import betterquesting.client.themes.ThemeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import xyz.brassgoggledcoders.bloodyquests.TaskRunRitual;

public class GuiTaskRunRitual extends GuiEmbedded {
	private String targetRitualName;

	public GuiTaskRunRitual(TaskRunRitual task, GuiQuesting screen, int posX, int posY, int sizeX, int sizeY) {
		super(screen, posX, posY, sizeX, sizeY);
		targetRitualName = task.targetRitualName;
	}

	@Override
	public void drawGui(int mx, int my, float partialTick) {
		Minecraft.getMinecraft().fontRendererObj.drawString(I18n.format("bloodyquests.task.runritual", new Object[0]),
				posX + sizeX / 2
						- Minecraft.getMinecraft().fontRendererObj
								.getStringWidth(I18n.format("bloodyquests.task.runritual", "")) / 2,
				posY, ThemeRegistry.curTheme().textColor().getRGB());
		Minecraft.getMinecraft().fontRendererObj.drawString("Ritual: " + targetRitualName,
				posX + sizeX / 2
						- Minecraft.getMinecraft().fontRendererObj
								.getStringWidth("Ritual: " + I18n.format(targetRitualName, new Object[0])) / 2,
				posY + 20, ThemeRegistry.curTheme().textColor().getRGB());
	}

}