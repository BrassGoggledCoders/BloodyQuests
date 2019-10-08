package xyz.brassgoggledcoders.bloodyquests;

import java.util.*;

import WayofTime.bloodmagic.ritual.Ritual;
import betterquesting.api.questing.IQuest;
import betterquesting.api.questing.tasks.ITask;
import betterquesting.api2.client.gui.misc.IGuiRect;
import betterquesting.api2.client.gui.panels.IGuiPanel;
import betterquesting.api2.storage.DBEntry;
import betterquesting.api2.utils.ParticipantInfo;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import xyz.brassgoggledcoders.bloodyquests.client.GuiRunRitualEditor;
import xyz.brassgoggledcoders.bloodyquests.client.PanelTaskRunRitual;

public class TaskRunRitual implements ITask {

    private ArrayList<UUID> completeUsers = new ArrayList<UUID>();
    public String targetRitualName = "Water";

    @Override
    public String getUnlocalisedName() {
        return "bloodyquests.task.runritual";
    }

    public void onRitualRun(World world, EntityPlayer player, Ritual ritual) {
        if(ritual.getName().substring(6).equalsIgnoreCase(targetRitualName)) {
            setComplete(player.getUniqueID());
        }
    }

    @Override
    public ResourceLocation getFactoryID() {
        return TaskRunRitualFactory.INSTANCE.getRegistryName();
    }

    @Override
    public boolean isComplete(UUID arg0) {
        return completeUsers.contains(arg0);
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
    public void readFromNBT(NBTTagCompound tag) {
        targetRitualName = tag.getString("ID");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        tag.setString("ID", targetRitualName);
        return tag;
    }

    @Override
    public void readProgressFromNBT(NBTTagCompound tag, boolean arg1) {

    }

    @Override
    public NBTTagCompound writeProgressToNBT(NBTTagCompound tag, List<UUID> arg1) {

        return tag;
    }

    @Override
    public IGuiPanel getTaskGui(IGuiRect rect, DBEntry<IQuest> quest) {
        return new PanelTaskRunRitual(rect, this);
    }

    @Override
    public GuiScreen getTaskEditor(GuiScreen parent, DBEntry<IQuest> quest) {
        return new GuiRunRitualEditor(parent, this);
    }

    @Override
    public void detect(ParticipantInfo participant, DBEntry<IQuest> quest) {
        // Done elsewhere
    }

}
