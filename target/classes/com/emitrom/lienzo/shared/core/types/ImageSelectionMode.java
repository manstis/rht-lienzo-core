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
 * Selection mode of a Picture or Sprite.
 */
public enum ImageSelectionMode implements EnumWithValue
{
    SELECT_BOUNDS("select-bounds"), SELECT_NON_TRANSPARENT("select-non-transparent");

    private final String m_value;

    private ImageSelectionMode(String value)
    {
        m_value = value;
    }

    public final String getValue()
    {
        return m_value;
    }

    public static final ImageSelectionMode lookup(String key)
    {
        if ((null != key) && (false == (key = key.trim()).isEmpty()))
        {
            ImageSelectionMode[] values = ImageSelectionMode.values();

            for (int i = 0; i < values.length; i++)
            {
                ImageSelectionMode value = values[i];

                if (value.getValue().equals(key))
                {
                    return value;
                }
            }
        }
        return SELECT_NON_TRANSPARENT;
    }

    public static final List<String> getKeys()
    {
        ArrayList<String> keys = new ArrayList<String>();

        ImageSelectionMode[] values = ImageSelectionMode.values();

        for (int i = 0; i < values.length; i++)
        {
            keys.add(values[i].getValue());
        }
        return keys;
    }

    public static final List<ImageSelectionMode> getValues()
    {
        return Arrays.asList(ImageSelectionMode.values());
    }
}
