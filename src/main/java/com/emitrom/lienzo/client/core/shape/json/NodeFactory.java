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

package com.emitrom.lienzo.client.core.shape.json;

import com.emitrom.lienzo.client.core.Attribute;
import com.emitrom.lienzo.client.core.shape.IJSONSerializable;
import com.emitrom.lienzo.client.core.shape.Node;
import com.emitrom.lienzo.shared.core.types.NodeType;

/**
 * NodeFactory is an abstract {@link IFactory} for classes that extend {@link Node}.
 * It adds the default Node attributes.
 *
 * @param <T>
 * @since 1.1
 */
public abstract class NodeFactory<T extends IJSONSerializable<T>> extends AbstractFactory<T>
{
    protected NodeFactory(NodeType type)
    {
        this(type.getValue());
    }

    protected NodeFactory(String typeName)
    {
        super(typeName);

        addAttribute(Attribute.ID);
        addAttribute(Attribute.NAME);
        addAttribute(Attribute.VISIBLE);
        addAttribute(Attribute.LISTENING);
        addAttribute(Attribute.TRANSFORM);
    }

    /**
     * Only factories that wish to extend other factories should use this.
     * 
     * @param type {@link NodeType}
     */
    protected void setNodeType(NodeType type)
    {
        setTypeName(type.getValue());
    }
}
