package io.github.lightman314.lightmanscurrency.common.notifications.categories;

import io.github.lightman314.lightmanscurrency.api.notifications.NotificationCategory;
import io.github.lightman314.lightmanscurrency.api.notifications.NotificationCategoryType;
import io.github.lightman314.lightmanscurrency.client.gui.widget.button.icon.IconData;
import io.github.lightman314.lightmanscurrency.common.core.ModItems;
import javax.annotation.Nonnull;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

public class TraderCategory extends NotificationCategory {

    public static final NotificationCategoryType<TraderCategory> TYPE = new NotificationCategoryType<>(new ResourceLocation("lightmanscurrency", "trader"), TraderCategory::new);

    private final Item trader;

    private final long traderID;

    private final MutableComponent traderName;

    public TraderCategory(ItemLike trader, MutableComponent traderName, long traderID) {
        this.trader = trader.asItem();
        this.traderName = traderName;
        this.traderID = traderID;
    }

    public TraderCategory(CompoundTag compound) {
        if (compound.contains("Icon")) {
            this.trader = ForgeRegistries.ITEMS.getValue(new ResourceLocation(compound.getString("Icon")));
        } else {
            this.trader = ModItems.TRADING_CORE.get();
        }
        if (compound.contains("TraderName")) {
            this.traderName = Component.Serializer.fromJson(compound.getString("TraderName"));
        } else {
            this.traderName = Component.translatable("gui.lightmanscurrency.universaltrader.default");
        }
        if (compound.contains("TraderID")) {
            this.traderID = compound.getLong("TraderID");
        } else {
            this.traderID = -1L;
        }
    }

    @Nonnull
    @Override
    public IconData getIcon() {
        return IconData.of(this.trader);
    }

    @Nonnull
    @Override
    public MutableComponent getName() {
        return this.traderName;
    }

    @Nonnull
    @Override
    public NotificationCategoryType<TraderCategory> getType() {
        return TYPE;
    }

    @Override
    public boolean matches(NotificationCategory other) {
        if (other instanceof TraderCategory otherTrader) {
            return this.traderID >= 0L && this.traderID == otherTrader.traderID ? true : this.traderName.getString().contentEquals(otherTrader.traderName.getString()) && this.trader.equals(otherTrader.trader);
        } else {
            return false;
        }
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        compound.putString("Icon", ForgeRegistries.ITEMS.getKey(this.trader).toString());
        compound.putString("TraderName", Component.Serializer.toJson(this.traderName));
        compound.putLong("TraderID", this.traderID);
    }
}