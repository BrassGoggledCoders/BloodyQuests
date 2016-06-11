package xyz.brassgoggledcoders.bloodyquests;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import WayofTime.bloodmagic.tile.TileAlchemyTable;
import WayofTime.bloodmagic.tile.TileAltar;
import betterquesting.quests.QuestDatabase;
import betterquesting.quests.QuestInstance;
import betterquesting.quests.tasks.TaskBase;
import betterquesting.quests.tasks.advanced.AdvancedTaskBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid=BloodyQuests.MODID, name="Bloody Quests", version="@VERSION@", dependencies="required-after:BloodMagic; required-after:bq_standard")
public class BloodyQuests {
	
	public static final String MODID = "bloodyquests";
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	//Semi-dirty hax
	@SubscribeEvent
	public void containerOpened(PlayerInteractEvent.RightClickBlock event)
	{
		EntityPlayer player = event.getEntityPlayer();
		TileEntity te = event.getWorld().getTileEntity(event.getPos());
		
		if(player == null || event.getWorld().isRemote || te == null)
			return;
		
		if(te instanceof TileAltar) {
			TileAltar altar = (TileAltar) te;

			for(Entry<AdvancedTaskBase,QuestInstance> set : GetAdvancedTasks(player.getUniqueID()).entrySet())
			{
				set.getKey().onItemCrafted(set.getValue(), player, altar.getStackInSlot(0));
			}
		}
		//TODO Could use improvement, currently only works if GUI is closed and reopened
		else if(te instanceof TileAlchemyTable)
		{
			TileAlchemyTable table = (TileAlchemyTable) te;
			
			for(Entry<AdvancedTaskBase,QuestInstance> set : GetAdvancedTasks(player.getUniqueID()).entrySet())
			{
				set.getKey().onItemCrafted(set.getValue(), player, table.getStackInSlot(TileAlchemyTable.outputSlot));
			}
		}
	}
	
	HashMap<AdvancedTaskBase, QuestInstance> GetAdvancedTasks(UUID uuid)
	{
		HashMap<AdvancedTaskBase, QuestInstance> map = new HashMap<AdvancedTaskBase, QuestInstance>();
		
		for(QuestInstance quest : QuestDatabase.getActiveQuests(uuid))
		{
			for(TaskBase task : quest.tasks)
			{
				if(task instanceof AdvancedTaskBase && !task.isComplete(uuid))
				{
					map.put((AdvancedTaskBase)task, quest);
				}
			}
		}
		
		return map;
	}
}
