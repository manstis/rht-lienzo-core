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
public enum TextAlign implements EnumWithValue
{
    CENTER("center"), END("end"), LEFT("left"), RIGHT("right"), START("start");

    private final String m_value;

    private TextAlign(String value)
    {
        m_value = value;
    }

    public final String getValue()
    {
        return m_value;
    }

    public static final TextAlign lookup(String key)
    {
        if ((null != key) && (false == (key = key.trim()).isEmpty()))
        {
            TextAlign[] values = TextAlign.values();

            for (int i = 0; i < values.length; i++)
            {
                TextAlign value = values[i];

                if (value.getValue().equals(key))
                {
                    return value;
                }
            }
        }
        return START;
    }

    public static final List<String> getKeys()
    {
        ArrayList<String> keys = new ArrayList<String>();

        TextAlign[] values = TextAlign.values();

        for (int i = 0; i < values.length; i++)
        {
            keys.add(values[i].getValue());
        }
        return keys;
    }

    public static final List<TextAlign> getValues()
    {
        return Arrays.asList(TextAlign.values());
    }
}
