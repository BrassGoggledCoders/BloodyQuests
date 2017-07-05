package xyz.brassgoggledcoders.bloodyquests;

import com.google.gson.JsonObject;

import betterquesting.api.enums.EnumSaveType;
import betterquesting.api.misc.IFactory;
import net.minecraft.util.ResourceLocation;

public class TaskRunRitualFactory implements IFactory<TaskRunRitual> {

	public static final TaskRunRitualFactory INSTANCE = new TaskRunRitualFactory();

	@Override
	public TaskRunRitual createNew() {
		return new TaskRunRitual();
	}

	@Override
	public ResourceLocation getRegistryName() {
		return new ResourceLocation(BloodyQuests.MODID, "runritual");
	}

	@Override
	public TaskRunRitual loadFromJson(JsonObject json) {
		TaskRunRitual task = new TaskRunRitual();
		task.readFromJson(json, EnumSaveType.CONFIG);
		return task;
	}

}
