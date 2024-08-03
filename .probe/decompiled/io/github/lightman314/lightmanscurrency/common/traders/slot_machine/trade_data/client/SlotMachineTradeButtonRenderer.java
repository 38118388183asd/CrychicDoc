package io.github.lightman314.lightmanscurrency.common.traders.slot_machine.trade_data.client;

import com.google.common.collect.Lists;
import io.github.lightman314.lightmanscurrency.LCText;
import io.github.lightman314.lightmanscurrency.api.traders.TradeContext;
import io.github.lightman314.lightmanscurrency.api.traders.trade.client.TradeRenderManager;
import io.github.lightman314.lightmanscurrency.client.gui.easy.EasyScreenHelper;
import io.github.lightman314.lightmanscurrency.client.gui.widget.button.trade.AlertData;
import io.github.lightman314.lightmanscurrency.client.gui.widget.button.trade.DisplayData;
import io.github.lightman314.lightmanscurrency.client.gui.widget.button.trade.DisplayEntry;
import io.github.lightman314.lightmanscurrency.client.util.ScreenPosition;
import io.github.lightman314.lightmanscurrency.common.traders.slot_machine.SlotMachineEntry;
import io.github.lightman314.lightmanscurrency.common.traders.slot_machine.SlotMachineTraderData;
import io.github.lightman314.lightmanscurrency.common.traders.slot_machine.trade_data.SlotMachineTrade;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;

public class SlotMachineTradeButtonRenderer extends TradeRenderManager<SlotMachineTrade> {

    public SlotMachineTradeButtonRenderer(SlotMachineTrade trade) {
        super(trade);
    }

    @Override
    public int tradeButtonWidth(TradeContext context) {
        return 128;
    }

    @Override
    public LazyOptional<ScreenPosition> arrowPosition(TradeContext context) {
        return ScreenPosition.ofOptional(36, 1);
    }

    @Override
    public DisplayData inputDisplayArea(TradeContext context) {
        return new DisplayData(1, 1, 34, 16);
    }

    @Override
    public List<DisplayEntry> getInputDisplays(TradeContext context) {
        return Lists.newArrayList(new DisplayEntry[] { DisplayEntry.of(this.trade.getCost(context)) });
    }

    @Override
    public DisplayData outputDisplayArea(TradeContext context) {
        return new DisplayData(59, 1, 68, 16);
    }

    private SlotMachineEntry getTimedEntry() {
        List<SlotMachineEntry> entries = this.trade.trader.getValidEntries();
        return entries.isEmpty() ? null : (SlotMachineEntry) entries.get((int) Minecraft.getInstance().level.m_46467_() / 20 % entries.size());
    }

    @Override
    public List<DisplayEntry> getOutputDisplays(TradeContext context) {
        SlotMachineEntry entry = this.getTimedEntry();
        if (entry == null) {
            return new ArrayList();
        } else {
            List<DisplayEntry> entries = new ArrayList();
            String odds = this.trade.trader.getOdds(entry.getWeight());
            for (ItemStack item : entry.items) {
                entries.add(DisplayEntry.of(item, item.getCount(), this.getTooltip(item, entry.getWeight(), odds)));
            }
            return entries;
        }
    }

    private List<Component> getTooltip(ItemStack stack, int weight, String odds) {
        if (stack.isEmpty()) {
            return null;
        } else {
            List<Component> tooltips = EasyScreenHelper.getTooltipFromItem(stack);
            tooltips.add(0, LCText.TOOLTIP_SLOT_MACHINE_WEIGHT.get(weight));
            tooltips.add(0, LCText.TOOLTIP_SLOT_MACHINE_ODDS.get(odds));
            return tooltips;
        }
    }

    @Override
    protected void getAdditionalAlertData(TradeContext context, List<AlertData> alerts) {
        if (context.hasTrader() && context.getTrader() instanceof SlotMachineTraderData trader) {
            if (!trader.isCreative() && !trader.hasStock()) {
                alerts.add(AlertData.warn(LCText.TOOLTIP_OUT_OF_STOCK));
            }
            if (!context.hasFunds(this.trade.getCost(context))) {
                alerts.add(AlertData.warn(LCText.TOOLTIP_CANNOT_AFFORD));
            }
        }
    }
}