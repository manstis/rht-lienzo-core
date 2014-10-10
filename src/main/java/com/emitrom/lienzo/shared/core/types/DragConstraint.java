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
 * Enum to be used to constrain the Dragging Area of a {@link ShapeType}
 */
public enum DragConstraint implements EnumWithValue
{
    HORIZONTAL("horizontal"), VERTICAL("vertical"), NONE("none");

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
    public static final DragConstraint lookup(String key)
    {
        if ((null != key) && (false == (key = key.trim()).isEmpty()))
        {
            DragConstraint[] values = DragConstraint.values();

            for (int i = 0; i < values.length; i++)
            {
                DragConstraint value = values[i];

                if (value.getValue().equals(key))
                {
                    return value;
                }
            }
        }
        return NONE;
    }

    /**
     * Return list of enum keys.
     * 
     * @return
     */
    public static final List<String> getKeys()
    {
        ArrayList<String> keys = new ArrayList<String>();

        DragConstraint[] values = DragConstraint.values();

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
    public static final List<DragConstraint> getValues()
    {
        return Arrays.asList(DragConstraint.values());
    }

    private DragConstraint(String value)
    {
        m_value = value;
    }
}
