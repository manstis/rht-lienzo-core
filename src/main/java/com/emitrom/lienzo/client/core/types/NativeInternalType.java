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

package com.emitrom.lienzo.client.core.types;

import com.emitrom.lienzo.shared.core.types.EnumWithValue;

/**
 * Used internally by the toolkit to map Javascript types.
 */
public enum NativeInternalType implements EnumWithValue
{
    STRING("string"), NUMBER("number"), BOOLEAN("boolean"), FUNCTION("function"), ARRAY("array"), OBJECT("object"), UNDEFINED("undefined");

    private final String m_value;

    private NativeInternalType(String value)
    {
        m_value = value;
    }

    public final String getValue()
    {
        return m_value;
    }
}
