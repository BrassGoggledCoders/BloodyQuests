package xyz.brassgoggledcoders.bloodyquests;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import WayofTime.alchemicalWizardry.api.event.ItemBindEvent;
import WayofTime.alchemicalWizardry.api.event.RitualEvent;
import WayofTime.alchemicalWizardry.common.tileEntity.TEAltar;
import WayofTime.alchemicalWizardry.common.tileEntity.TEWritingTable;
import betterquesting.quests.QuestDatabase;
import betterquesting.quests.QuestInstance;
import betterquesting.quests.tasks.TaskBase;
import betterquesting.quests.tasks.TaskRegistry;
import betterquesting.quests.tasks.advanced.AdvancedTaskBase;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

@Mod(modid = BloodyQuests.MODID, name = "Bloody Quests", version = "@VERSION@",
		dependencies = "required-after:bq_standard")
public class BloodyQuests {

	public static final String MODID = "bloodyquests";

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		TaskRegistry.RegisterTask(TaskRunRitual.class, new ResourceLocation(MODID, "run_ritual"));
	}

	@SubscribeEvent
	public void itemBound(ItemBindEvent event) {
		if(event.player == null || event.player.worldObj.isRemote || event.itemStack == null)
			return;

		for(Entry<AdvancedTaskBase, QuestInstance> set : GetAdvancedTasks(event.player.getUniqueID()).entrySet()) {
			set.getKey().onItemCrafted(set.getValue(), event.player, event.itemStack);
		}
	}

	@SubscribeEvent
	public void onRitualRun(RitualEvent event) {
		// FMLLog.warning(event.ownerKey, "");
		World world = event.mrs.getWorld();
		EntityPlayer player = world.getPlayerEntityByName(event.ownerKey);

		if(player == null || world.isRemote)
			return;

		for(QuestInstance quest : QuestDatabase.getActiveQuests(player.getUniqueID())) {
			for(TaskBase task : quest.tasks) {
				if(task instanceof TaskRunRitual)
					((TaskRunRitual) task).onRitualRun(world, player, event.ritualKey);
			}
		}
	}

	// Semi-dirty hax
	@SubscribeEvent
	public void containerOpened(PlayerInteractEvent event) {
		if(event.action == Action.RIGHT_CLICK_BLOCK) {
			EntityPlayer player = event.entityPlayer;
			TileEntity te = event.world.getTileEntity(event.x, event.y, event.z);

			if(player == null || event.world.isRemote || te == null)
				return;

			if(te instanceof TEAltar) {
				TEAltar altar = (TEAltar) te;

				for(Entry<AdvancedTaskBase, QuestInstance> set : GetAdvancedTasks(player.getUniqueID()).entrySet()) {
					set.getKey().onItemCrafted(set.getValue(), player, altar.getStackInSlot(0));
				}
			}
			// TODO Could use improvement, currently only works if GUI is closed and reopened
			else if(te instanceof TEWritingTable) {
				TEWritingTable table = (TEWritingTable) te;

				for(Entry<AdvancedTaskBase, QuestInstance> set : GetAdvancedTasks(player.getUniqueID()).entrySet()) {
					set.getKey().onItemCrafted(set.getValue(), player, table.getStackInSlot(6));
				}
			}
		}
	}

	HashMap<AdvancedTaskBase, QuestInstance> GetAdvancedTasks(UUID uuid) {
		HashMap<AdvancedTaskBase, QuestInstance> map = new HashMap<AdvancedTaskBase, QuestInstance>();

		for(QuestInstance quest : QuestDatabase.getActiveQuests(uuid)) {
			for(TaskBase task : quest.tasks) {
				if(task instanceof AdvancedTaskBase && !task.isComplete(uuid)) {
					map.put((AdvancedTaskBase) task, quest);
				}
			}
		}

		return map;
	}
}
