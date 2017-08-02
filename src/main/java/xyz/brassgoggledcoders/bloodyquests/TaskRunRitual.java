package xyz.brassgoggledcoders.bloodyquests;

import java.util.ArrayList;
import java.util.UUID;

import com.google.gson.JsonObject;

import betterquesting.api.client.gui.misc.IGuiEmbedded;
import betterquesting.api.enums.EnumSaveType;
import betterquesting.api.jdoc.IJsonDoc;
import betterquesting.api.questing.IQuest;
import betterquesting.api.questing.tasks.ITask;
import betterquesting.api.utils.JsonHelper;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.bloodyquests.client.GuiRunRitualEditor;
import xyz.brassgoggledcoders.bloodyquests.client.GuiTaskRunRitual;

public class TaskRunRitual implements ITask {

	private ArrayList<UUID> completeUsers = new ArrayList<UUID>();
	public String targetRitualName = "Water";

	@Override
	public String getUnlocalisedName() {
		return "bloodyquests.task.runritual";
	}

	public void onRitualRun(World world, EntityPlayer player, String ritualKey) {
		if(!isComplete(player.getUniqueID())) {
			if(ritualKey.substring(5).equalsIgnoreCase(targetRitualName)) {
				this.setComplete(player.getUniqueID());
			}
		}
	}

	@Override
	public void readFromJson(JsonObject json, EnumSaveType type) {
		targetRitualName = JsonHelper.GetString(json, "name", "");
	}

	@Override
	public JsonObject writeToJson(JsonObject json, EnumSaveType type) {
		json.addProperty("name", targetRitualName);
		return json;
	}

	@Override
	public void detect(EntityPlayer arg0, IQuest arg1) {
		// Done elsewhere.
	}

	@Override
	public IJsonDoc getDocumentation() {
		return null;
	}

	@Override
	public ResourceLocation getFactoryID() {
		return TaskRunRitualFactory.INSTANCE.getRegistryName();
	}

	@Override
	public GuiScreen getTaskEditor(GuiScreen parent, IQuest data) {
		return new GuiRunRitualEditor(parent, this);
	}

	@Override
	public IGuiEmbedded getTaskGui(int posX, int posY, int sizeX, int sizeY, IQuest arg4) {
		return new GuiTaskRunRitual(this, posX, posY, sizeX, sizeY);
	}

	@Override
	public boolean isComplete(UUID arg0) {
		return completeUsers.contains(arg0);
	}

	@Override
	public void resetAll() {
		completeUsers.clear();
	}

	@Override
	public void resetUser(UUID arg0) {
		completeUsers.remove(arg0);
	}

	@Override
	public void setComplete(UUID arg0) {
		completeUsers.add(arg0);
	}

	@Override
	public void update(EntityPlayer arg0, IQuest arg1) {}

}
