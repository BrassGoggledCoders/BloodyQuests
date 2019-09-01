package xyz.brassgoggledcoders.bloodyquests;

import betterquesting.api.questing.tasks.ITask;
import betterquesting.api2.registry.IFactoryData;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class TaskRunRitualFactory implements IFactoryData<ITask, NBTTagCompound> {

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
	public TaskRunRitual loadFromData(NBTTagCompound tag) {
		TaskRunRitual task = new TaskRunRitual();
		task.readFromNBT(tag);
		return task;
	}

}
