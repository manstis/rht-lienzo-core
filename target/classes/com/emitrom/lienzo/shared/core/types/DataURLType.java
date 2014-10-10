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
 * DataURLType defines export format for toDataURL
 */
public enum DataURLType implements EnumWithValue
{
    PNG("image/png;"), JPG("image/jpg;");

    private final String m_value;

    private DataURLType(String value)
    {
        m_value = value;
    }

    public final String getValue()
    {
        return m_value;
    }

    public static final DataURLType lookup(String key)
    {
        if ((null != key) && (false == (key = key.trim()).isEmpty()))
        {
            DataURLType[] values = DataURLType.values();

            for (int i = 0; i < values.length; i++)
            {
                DataURLType value = values[i];

                if (value.getValue().equals(key))
                {
                    return value;
                }
            }
        }
        return PNG;
    }

    public static final List<String> getKeys()
    {
        ArrayList<String> keys = new ArrayList<String>();

        DataURLType[] values = DataURLType.values();

        for (int i = 0; i < values.length; i++)
        {
            keys.add(values[i].getValue());
        }
        return keys;
    }

    public static final List<DataURLType> getValues()
    {
        return Arrays.asList(DataURLType.values());
    }
}
