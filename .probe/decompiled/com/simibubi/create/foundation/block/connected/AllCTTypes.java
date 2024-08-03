package com.simibubi.create.foundation.block.connected;

import com.simibubi.create.Create;
import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.resources.ResourceLocation;

public enum AllCTTypes implements CTType {

    HORIZONTAL(2, ConnectedTextureBehaviour.ContextRequirement.builder().horizontal().build()) {

        @Override
        public int getTextureIndex(ConnectedTextureBehaviour.CTContext context) {
            return (context.right ? 1 : 0) + (context.left ? 2 : 0);
        }
    }
    ,
    HORIZONTAL_KRYPPERS(2, ConnectedTextureBehaviour.ContextRequirement.builder().horizontal().build()) {

        @Override
        public int getTextureIndex(ConnectedTextureBehaviour.CTContext context) {
            return !context.right && !context.left ? 0 : (!context.right ? 3 : (!context.left ? 2 : 1));
        }
    }
    ,
    VERTICAL(2, ConnectedTextureBehaviour.ContextRequirement.builder().vertical().build()) {

        @Override
        public int getTextureIndex(ConnectedTextureBehaviour.CTContext context) {
            return (context.up ? 1 : 0) + (context.down ? 2 : 0);
        }
    }
    ,
    OMNIDIRECTIONAL(8, ConnectedTextureBehaviour.ContextRequirement.builder().all().build()) {

        @Override
        public int getTextureIndex(ConnectedTextureBehaviour.CTContext context) {
            int tileX = 0;
            int tileY = 0;
            int borders = (!context.up ? 1 : 0) + (!context.down ? 1 : 0) + (!context.left ? 1 : 0) + (!context.right ? 1 : 0);
            if (context.up) {
                tileX++;
            }
            if (context.down) {
                tileX += 2;
            }
            if (context.left) {
                tileY++;
            }
            if (context.right) {
                tileY += 2;
            }
            if (borders == 0) {
                if (context.topRight) {
                    tileX++;
                }
                if (context.topLeft) {
                    tileX += 2;
                }
                if (context.bottomRight) {
                    tileY += 2;
                }
                if (context.bottomLeft) {
                    tileY++;
                }
            }
            if (borders == 1) {
                if (!context.right && (context.topLeft || context.bottomLeft)) {
                    tileY = 4;
                    tileX = -1 + (context.bottomLeft ? 1 : 0) + (context.topLeft ? 1 : 0) * 2;
                }
                if (!context.left && (context.topRight || context.bottomRight)) {
                    tileY = 5;
                    tileX = -1 + (context.bottomRight ? 1 : 0) + (context.topRight ? 1 : 0) * 2;
                }
                if (!context.down && (context.topLeft || context.topRight)) {
                    tileY = 6;
                    tileX = -1 + (context.topLeft ? 1 : 0) + (context.topRight ? 1 : 0) * 2;
                }
                if (!context.up && (context.bottomLeft || context.bottomRight)) {
                    tileY = 7;
                    tileX = -1 + (context.bottomLeft ? 1 : 0) + (context.bottomRight ? 1 : 0) * 2;
                }
            }
            if (borders == 2 && (context.up && context.left && context.topLeft || context.down && context.left && context.bottomLeft || context.up && context.right && context.topRight || context.down && context.right && context.bottomRight)) {
                tileX += 3;
            }
            return tileX + 8 * tileY;
        }
    }
    ,
    CROSS(4, ConnectedTextureBehaviour.ContextRequirement.builder().axisAligned().build()) {

        @Override
        public int getTextureIndex(ConnectedTextureBehaviour.CTContext context) {
            return (context.up ? 1 : 0) + (context.down ? 2 : 0) + (context.left ? 4 : 0) + (context.right ? 8 : 0);
        }
    }
    ,
    RECTANGLE(4, ConnectedTextureBehaviour.ContextRequirement.builder().axisAligned().build()) {

        @Override
        public int getTextureIndex(ConnectedTextureBehaviour.CTContext context) {
            int x = context.left && context.right ? 2 : (context.left ? 3 : (context.right ? 1 : 0));
            int y = context.up && context.down ? 1 : (context.up ? 2 : (context.down ? 0 : 3));
            return x + y * 4;
        }
    }
    ;

    private final ResourceLocation id = Create.asResource(Lang.asId(this.name()));

    private final int sheetSize;

    private final ConnectedTextureBehaviour.ContextRequirement contextRequirement;

    private AllCTTypes(int sheetSize, ConnectedTextureBehaviour.ContextRequirement contextRequirement) {
        this.sheetSize = sheetSize;
        this.contextRequirement = contextRequirement;
        CTTypeRegistry.register(this);
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public int getSheetSize() {
        return this.sheetSize;
    }

    @Override
    public ConnectedTextureBehaviour.ContextRequirement getContextRequirement() {
        return this.contextRequirement;
    }
}