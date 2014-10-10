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

/**
 * NodeType is an extensible enumeration of top-level node types used in the Lienzo toolkit.
 */
public class NodeType
{
    public static final NodeType SCENE      = new NodeType("Scene");

    public static final NodeType LAYER      = new NodeType("Layer");

    public static final NodeType GROUP      = new NodeType("Group");

    public static final NodeType SHAPE      = new NodeType("Shape");

    public static final NodeType VIEWPORT   = new NodeType("Viewport");

    public static final NodeType GRID_LAYER = new NodeType("GridLayer");

    private final String         m_value;

    public NodeType(String value)
    {
        m_value = value;
    }

    @Override
    public final String toString()
    {
        return m_value;
    }

    public final String getValue()
    {
        return m_value;
    }
}
