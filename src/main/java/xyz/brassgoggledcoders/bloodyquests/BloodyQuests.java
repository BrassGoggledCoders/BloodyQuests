package xyz.brassgoggledcoders.bloodyquests;

import java.util.List;

import WayofTime.bloodmagic.event.RitualEvent;
import WayofTime.bloodmagic.tile.TileAlchemyTable;
import WayofTime.bloodmagic.tile.TileAltar;
import betterquesting.api.api.ApiReference;
import betterquesting.api.api.QuestingAPI;
import betterquesting.api.questing.IQuest;
import betterquesting.api.questing.tasks.ITask;
import betterquesting.api2.cache.CapabilityProviderQuestCache;
import betterquesting.api2.cache.QuestCache;
import betterquesting.api2.storage.DBEntry;
import betterquesting.api2.utils.ParticipantInfo;
import betterquesting.questing.QuestDatabase;
import betterquesting.questing.tasks.TaskRegistry;
import bq_standard.tasks.TaskCrafting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = BloodyQuests.MODID, name = "Bloody Quests", version = "@VERSION@", dependencies = "required-after:bloodmagic; required-after:bq_standard")
@EventBusSubscriber
public class BloodyQuests {

    public static final String MODID = "bloodyquests";

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        TaskRegistry.INSTANCE.register(new TaskRunRitualFactory());
    }

    @SubscribeEvent
    public static void onRitualRun(RitualEvent.RitualActivatedEvent event) {
        World world = event.getRitualStone().getWorldObj();
        EntityPlayer player = event.getPlayer();

        if(player == null || world.isRemote) {
            return;
        }

        QuestCache qc = player.getCapability(CapabilityProviderQuestCache.CAP_QUEST_CACHE, null);
        if(qc != null) {
            List<DBEntry<IQuest>> activeQuests = QuestDatabase.INSTANCE.bulkLookup(qc.getActiveQuests());

            activeQuests.stream().map(entry -> entry.getValue())
                    .filter(quest -> quest.isUnlocked(player.getUniqueID()) && quest.canSubmit(player))
                    .forEach(quest -> quest.getTasks().getEntries().stream()
                            .filter(task -> task.getValue() instanceof TaskRunRitual)
                            .forEach(task -> ((TaskRunRitual) task.getValue()).onRitualRun(world, player,
                                    event.getRitual())));
        }
    }

    // Semi-dirty hax - TODO PR Crafting events to BM
    @SubscribeEvent
    public static void rightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        EntityPlayer player = event.getEntityPlayer();
        TileEntity te = event.getWorld().getTileEntity(event.getPos());

        if(player != null && !event.getWorld().isRemote && te != null) {
            ParticipantInfo pInfo = new ParticipantInfo(player);
            if(te instanceof TileAltar) {
                TileAltar altar = (TileAltar) te;
                List<DBEntry<IQuest>> actQuest = QuestingAPI.getAPI(ApiReference.QUEST_DB)
                        .bulkLookup(pInfo.getSharedQuests());
                for(DBEntry<IQuest> entry : actQuest) {
                    for(DBEntry<ITask> task : entry.getValue().getTasks().getEntries()) {
                        if(task.getValue() instanceof TaskCrafting) {
                            ((TaskCrafting) task.getValue()).onItemCraft(pInfo, entry, altar.getStackInSlot(0));
                        }
                    }
                }
            }
            else if(te instanceof TileAlchemyTable) {
                TileAlchemyTable table = (TileAlchemyTable) te;
                List<DBEntry<IQuest>> actQuest = QuestingAPI.getAPI(ApiReference.QUEST_DB)
                        .bulkLookup(pInfo.getSharedQuests());
                for(DBEntry<IQuest> entry : actQuest) {
                    for(DBEntry<ITask> task : entry.getValue().getTasks().getEntries()) {
                        if(task.getValue() instanceof TaskCrafting) {
                            ((TaskCrafting) task.getValue()).onItemCraft(pInfo, entry,
                                    table.getStackInSlot(TileAlchemyTable.outputSlot));
                        }
                    }
                }
            }
        }
    }
}
