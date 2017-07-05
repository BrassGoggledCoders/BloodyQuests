package xyz.brassgoggledcoders.bloodyquests;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import WayofTime.bloodmagic.api.event.RitualEvent;
import WayofTime.bloodmagic.tile.TileAlchemyTable;
import WayofTime.bloodmagic.tile.TileAltar;
import betterquesting.api.questing.IQuest;
import betterquesting.api.questing.tasks.ITask;
import betterquesting.questing.QuestDatabase;
import betterquesting.questing.tasks.TaskRegistry;
import bq_standard.tasks.TaskCrafting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = BloodyQuests.MODID, name = "Bloody Quests", version = "@VERSION@",
		dependencies = "required-after:bloodmagic; required-after:bq_standard")
public class BloodyQuests {

	public static final String MODID = "bloodyquests";

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		TaskRegistry.INSTANCE.registerTask(new TaskRunRitualFactory());
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onRitualRun(RitualEvent.RitualActivatedEvent event) {
		World world = event.mrs.getWorldObj();
		EntityPlayer player = event.player;

		if(player == null || world.isRemote)
			return;

		for(Entry<TaskRunRitual, IQuest> set : getRitualTasks(player.getUniqueID()).entrySet()) {
			set.getKey().onRitualRun(world, player, event.ritual);
		}
	}

	// Semi-dirty hax
	@SubscribeEvent
	public void containerOpened(PlayerInteractEvent.RightClickBlock event) {
		EntityPlayer player = event.getEntityPlayer();
		TileEntity te = event.getWorld().getTileEntity(event.getPos());

		if(player == null || event.getWorld().isRemote || te == null)
			return;
		// TODO Swap this to use the altar event
		if(te instanceof TileAltar) {
			TileAltar altar = (TileAltar) te;

			for(Entry<TaskCrafting, IQuest> set : getCraftingTasks(player.getUniqueID()).entrySet()) {
				set.getKey().onItemCrafted(set.getValue(), player, altar.getStackInSlot(0));
			}
		}
		// TODO Pull request an event to blood magic
		else if(te instanceof TileAlchemyTable) {
			TileAlchemyTable table = (TileAlchemyTable) te;

			for(Entry<TaskCrafting, IQuest> set : getCraftingTasks(player.getUniqueID()).entrySet()) {
				set.getKey().onItemCrafted(set.getValue(), player, table.getStackInSlot(TileAlchemyTable.outputSlot));
			}
		}
	}

	// FIXME
	HashMap<TaskCrafting, IQuest> getCraftingTasks(UUID uuid) {
		HashMap<TaskCrafting, IQuest> map = new HashMap<TaskCrafting, IQuest>();

		for(IQuest quest : QuestDatabase.INSTANCE.getAllValues()) {
			for(ITask task : quest.getTasks().getAllValues()) {
				if(task instanceof TaskCrafting && !task.isComplete(uuid)) {
					map.put((TaskCrafting) task, quest);
				}
			}
		}

		return map;
	}

	HashMap<TaskRunRitual, IQuest> getRitualTasks(UUID uuid) {
		HashMap<TaskRunRitual, IQuest> map = new HashMap<TaskRunRitual, IQuest>();

		for(IQuest quest : QuestDatabase.INSTANCE.getAllValues()) {
			for(ITask task : quest.getTasks().getAllValues()) {
				if(task instanceof TaskRunRitual && !task.isComplete(uuid)) {
					map.put((TaskRunRitual) task, quest);
				}
			}
		}

		return map;
	}
}
