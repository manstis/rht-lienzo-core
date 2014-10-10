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

import com.emitrom.lienzo.client.core.shape.Text;

/**
 * Enum to create a type safe set of values for {@link Text} Alignment. 
 */
public enum TextBaseLine implements EnumWithValue
{
    ALPHABETIC("alphabetic"), BOTTOM("bottom"), HANGING("hanging"), IDEOGRAPHIC("ideographic"), MIDDLE("middle"), TOP("top");

    private final String m_value;

    private TextBaseLine(String value)
    {
        m_value = value;
    }

    public final String getValue()
    {
        return m_value;
    }

    public static final TextBaseLine lookup(String key)
    {
        if ((null != key) && (false == (key = key.trim()).isEmpty()))
        {
            TextBaseLine[] values = TextBaseLine.values();

            for (int i = 0; i < values.length; i++)
            {
                TextBaseLine value = values[i];

                if (value.getValue().equals(key))
                {
                    return value;
                }
            }
        }
        return ALPHABETIC;
    }

    public static final List<String> getKeys()
    {
        ArrayList<String> keys = new ArrayList<String>();

        TextBaseLine[] values = TextBaseLine.values();

        for (int i = 0; i < values.length; i++)
        {
            keys.add(values[i].getValue());
        }
        return keys;
    }

    public static final List<TextBaseLine> getValues()
    {
        return Arrays.asList(TextBaseLine.values());
    }
}
