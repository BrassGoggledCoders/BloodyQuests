package xyz.brassgoggledcoders.bloodyquests.client;

import betterquesting.api2.client.gui.misc.*;
import betterquesting.api2.client.gui.panels.CanvasEmpty;
import betterquesting.api2.client.gui.panels.content.PanelTextBox;
import betterquesting.api2.utils.QuestTranslation;
import xyz.brassgoggledcoders.bloodyquests.TaskRunRitual;

public class PanelTaskRunRitual extends CanvasEmpty {
    private final String targetRitualName;

    public PanelTaskRunRitual(IGuiRect rect, TaskRunRitual task) {
        super(rect);
        targetRitualName = task.targetRitualName;
    }

    @Override
    public void initPanel() {
        super.initPanel();
        addPanel(new PanelTextBox(new GuiTransform(GuiAlign.MID_LEFT, 0, 0, 16, 8, 0), "bloodyquests.task.runritual"));
        addPanel(new PanelTextBox(new GuiTransform(GuiAlign.MID_CENTER, 0, 0, 16, 8, 0),
                QuestTranslation.translate("bloodyquests.name.run_ritual") + targetRitualName));
    }

}