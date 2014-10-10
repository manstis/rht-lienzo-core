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

package com.emitrom.lienzo.client.core.shape;

import java.util.ArrayList;

import com.emitrom.lienzo.client.core.types.FastArrayList;
import com.emitrom.lienzo.client.core.types.INodeFilter;

/**
 * Interface to be implemented by all primitive collections. 
 */
public interface IContainer<M> extends Iterable<M>
{
    /**
     * Gets all nodes in this container.
     * 
     * @return FastArrayList
     */
    public FastArrayList<M> getChildNodes();

    /**
     * Adds a node to this container
     * 
     * @param node
     */
    public void add(M node);

    /**
     * Removes the given node from the container.
     * 
     * @param node
     */
    public void remove(M node);

    /**
     * Removes all nodes from this cotainer.
     */
    public void removeAll();

    /**
     * Moves the node one layer up.
     * 
     * @param node
     */
    public void moveUp(M node);

    /**
     * Modes the node one layer down
     * 
     * @param node
     */
    public void moveDown(M node);

    /**
     * Moves the node to the top of the layer stack
     * 
     * @param node
     */
    public void moveToTop(M node);

    /**
     * Moves the node to the bottom of the layer stack
     * @param node
     */
    public void moveToBottom(M node);

    /**
     * Searches and returns all {@link Node} that match the {@link com.emitrom.lienzo.client.core.types.INodeFilter}
     * 
     * @param filter
     * @return ArrayList
     */
    public ArrayList<Node<?>> search(INodeFilter filter);

    public boolean isValidForContainer(IJSONSerializable<?> node);
}
