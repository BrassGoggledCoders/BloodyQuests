package xyz.brassgoggledcoders.bloodyquests;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import WayofTime.alchemicalWizardry.api.event.RitualEvent;
import WayofTime.alchemicalWizardry.common.tileEntity.TEAltar;
import WayofTime.alchemicalWizardry.common.tileEntity.TEWritingTable;
import betterquesting.api.questing.IQuest;
import betterquesting.api.questing.tasks.ITask;
import betterquesting.questing.QuestDatabase;
import betterquesting.questing.tasks.TaskRegistry;
import bq_standard.tasks.TaskCrafting;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

@Mod(modid = BloodyQuests.MODID, name = "Bloody Quests", version = "@VERSION@",
		dependencies = "required-after:bq_standard;required-after:AWWayofTime")
public class BloodyQuests {

	public static final String MODID = "bloodyquests";

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		TaskRegistry.INSTANCE.registerTask(new TaskRunRitualFactory());
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onRitualRun(RitualEvent event) {
		World world = event.mrs.getWorld();
		EntityPlayer player = world.getPlayerEntityByName(event.ownerKey);

		if(player == null || world.isRemote)
			return;

		for(Entry<TaskRunRitual, IQuest> set : getRitualTasks(player.getUniqueID()).entrySet()) {
			set.getKey().onRitualRun(world, player, event.ritualKey);
		}
	}

	// Semi-dirty hax
	@SubscribeEvent
	public void containerOpened(PlayerInteractEvent event) {
		if(event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK)
			return;

		EntityPlayer player = event.entityPlayer;
		TileEntity te = event.world.getTileEntity(event.x, event.y, event.z);

		if(player == null || event.world.isRemote || te == null)
			return;
		// TODO Swap this to use the altar event
		if(te instanceof TEAltar) {
			IInventory altar = (IInventory) te;

			for(Entry<TaskCrafting, IQuest> set : getCraftingTasks(player.getUniqueID()).entrySet()) {
				set.getKey().onItemCrafted(set.getValue(), player, altar.getStackInSlot(0));
			}
		}
		// TODO Pull request an event to blood magic
		else if(te instanceof TEWritingTable) {
			IInventory table = (IInventory) te;

			for(Entry<TaskCrafting, IQuest> set : getCraftingTasks(player.getUniqueID()).entrySet()) {
				set.getKey().onItemCrafted(set.getValue(), player, table.getStackInSlot(6));
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
