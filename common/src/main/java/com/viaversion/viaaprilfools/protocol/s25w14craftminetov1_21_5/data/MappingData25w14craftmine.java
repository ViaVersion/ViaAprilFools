/*
 * This file is part of ViaAprilFools - https://github.com/ViaVersion/ViaAprilFools
 * Copyright (C) 2021-2026 the original authors
 *                         - RK_01/RaphiMC
 *                         - Florian Reuth <git@florianreuth.de>
 * Copyright (C) 2023-2026 ViaVersion and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.viaversion.viaaprilfools.protocol.s25w14craftminetov1_21_5.data;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaaprilfools.api.data.VAFBackwardsMappingData;
import com.viaversion.viaaprilfools.api.data.VAFMappingDataLoader;
import com.viaversion.viaaprilfools.protocol.v1_21_5to25w14craftmine.Protocol1_21_5To_25w14craftmine;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;

public final class MappingData25w14craftmine extends VAFBackwardsMappingData {

    private final Map<String, String> TRANSLATIONS = new HashMap<>();
    private CompoundTag[] WORLD_EFFECTS;

    public MappingData25w14craftmine() {
        super("25w14craftmine", "1.21.5", Protocol1_21_5To_25w14craftmine.class);
    }

    @Override
    protected void loadExtras(CompoundTag data) {
        super.loadExtras(data);
        final JsonObject jsonObject = VAFMappingDataLoader.INSTANCE.loadFromDataDir("translations-25w14craftmine.json");
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            TRANSLATIONS.put(entry.getKey(), entry.getValue().getAsString());
        }

        final CompoundTag worldEffects = VAFMappingDataLoader.INSTANCE.loadNBTFromFile("world-effects.nbt");
        WORLD_EFFECTS = new CompoundTag[worldEffects.size()];
        for (Map.Entry<String, Tag> effect : worldEffects) {
            final int index = Integer.parseInt(effect.getKey());
            WORLD_EFFECTS[index] = (CompoundTag) effect.getValue();
        }
    }

    public String getTranslation(final String key) {
        return TRANSLATIONS.getOrDefault(key, null);
    }

    public CompoundTag getWorldEffect(final int id) {
        if (id < 0 || id >= WORLD_EFFECTS.length) {
            return null;
        } else {
            return WORLD_EFFECTS[id].copy();
        }
    }

}
