package xyz.brassgoggledcoders.bloodyquests;

import com.google.gson.JsonObject;

import WayofTime.bloodmagic.api.ritual.Ritual;
import betterquesting.client.gui.GuiQuesting;
import betterquesting.client.gui.misc.GuiEmbedded;
import betterquesting.quests.tasks.TaskBase;
import betterquesting.utils.JsonHelper;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.brassgoggledcoders.bloodyquests.client.GuiRunRitualEditor;
import xyz.brassgoggledcoders.bloodyquests.client.GuiTaskRunRitual;

public class TaskRunRitual extends TaskBase {

	public String targetRitualName;
	
	@Override
	public String getUnlocalisedName() {
		return "bloodyquests.task.runritual";
	}

	public void onRitualRun(World world, EntityPlayer player, Ritual ritual) {
		if(!isComplete(player.getUniqueID()))
		{
			FMLLog.warning(targetRitualName, "");
			if(ritual.getName() == targetRitualName)
			{
				this.setCompletion(player.getUniqueID(), true);
				FMLLog.warning("true", "");
			}
		}
	}
	
	@Override
	public void writeToJson(JsonObject json)
	{
		super.writeToJson(json);
		json.addProperty("targetRitualName", targetRitualName);
	}
	
	@Override
	public void readFromJson(JsonObject json)
	{
		super.readFromJson(json);
		targetRitualName = JsonHelper.GetString(json, "targetRitualName", "");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen GetEditor(GuiScreen parent, JsonObject data)
	{
		return new GuiRunRitualEditor(parent, data);
	}

	@Override
	public GuiEmbedded getGui(GuiQuesting screen, int posX, int posY, int sizeX, int sizeY)
	{
		return new GuiTaskRunRitual(this, screen, posX, posY, sizeX, sizeY);
	}

}
