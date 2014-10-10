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

import com.emitrom.lienzo.client.core.shape.IContainer;
import com.emitrom.lienzo.client.core.shape.IJSONSerializable;
import com.emitrom.lienzo.shared.core.types.NodeType;

/**
 * ContainerNodeFactory is an abstract {@link IFactory} for {@link ContainerNode}s.
 * Sub-classes must implement the {@link #isValidForContainer(IJSONSerializable)}
 * method to indicate which child nodes can be added to the ContainerNode.
 *
 * @param <T>
 * @since 1.1
 */
public abstract class ContainerNodeFactory<T extends IJSONSerializable<T> & IContainer<?>> extends NodeFactory<T> implements IContainerFactory
{
    protected ContainerNodeFactory(NodeType type)
    {
        this(type.getValue());
    }

    protected ContainerNodeFactory(String typeName)
    {
        super(typeName);
    }
}
