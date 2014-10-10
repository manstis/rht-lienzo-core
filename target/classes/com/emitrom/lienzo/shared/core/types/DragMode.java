/*
   Copyright (c) 2012 Emitrom LLC. All rights reserved. 
   For licensing questions, please contact us at licensing@emitrom.com

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package com.emitrom.lienzo.shared.core.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Enum to be used to constrain the Dragging Layer of a {@link Shape} or {@link Group}
 */
public enum DragMode implements EnumWithValue
{
    DRAG_LAYER("drag-layer"), SAME_LAYER("same-layer");

    private final String m_value;

    /**
     * Return String representation.
     * 
     * @return
     */
    public final String getValue()
    {
        return m_value;
    }

    /**
     * Used to safely convert from a String to an enum.
     * 
     * @param key
     * @return
     */
    public static final DragMode lookup(String key)
    {
        if ((null != key) && (false == (key = key.trim()).isEmpty()))
        {
            DragMode[] values = DragMode.values();

            for (int i = 0; i < values.length; i++)
            {
                DragMode value = values[i];

                if (value.getValue().equals(key))
                {
                    return value;
                }
            }
        }
        return DRAG_LAYER;
    }

    /**
     * Return list of enum keys.
     * 
     * @return
     */
    public static final List<String> getKeys()
    {
        ArrayList<String> keys = new ArrayList<String>();

        DragMode[] values = DragMode.values();

        for (int i = 0; i < values.length; i++)
        {
            keys.add(values[i].getValue());
        }
        return keys;
    }

    /**
     * Return list of enum values.
     * 
     * @return
     */
    public static final List<DragMode> getValues()
    {
        return Arrays.asList(DragMode.values());
    }

    private DragMode(String value)
    {
        m_value = value;
    }
}
