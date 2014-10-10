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

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.emitrom.lienzo.client.core.Context2D;
import com.emitrom.lienzo.client.core.shape.json.validators.ValidationContext;
import com.emitrom.lienzo.client.core.shape.json.validators.ValidationException;
import com.emitrom.lienzo.client.core.types.FastArrayList;
import com.emitrom.lienzo.shared.core.types.NodeType;
import com.google.gwt.json.client.JSONObject;

/**
 * ContainerNode acts as a Collection holder for primitives.
 * 
 * <ul>
 * <li>A ContainerNode may contain {@link Layer} or {@link Group}.</li>
 * <li>A Container handles collection operations such as add, remove and removeAll.</li>
 * </ul>
 * 
 * @param <T>
 */
public abstract class ContainerNode<M extends IDrawable<?>, T extends ContainerNode<M, T>> extends Node<T> implements IContainer<T, M>, IDrawable<T>, Iterable<M>
{
    private final FastArrayList<M> m_list = new FastArrayList<M>();

    protected ContainerNode(NodeType type)
    {
        super(type);
    }

    protected ContainerNode(NodeType type, JSONObject node, ValidationContext ctx) throws ValidationException
    {
        super(type, node, ctx);
    }

    @Override
    public T copy()
    {
        Node<?> node = copyUnchecked();

        if (null == node)
        {
            return null;
        }
        if (getNodeType() != node.getNodeType())
        {
            return null;
        }
        return node.cast();
    }

    /**
     * Returns a {@link FastArrayList} containing all children.
     */
    @Override
    public FastArrayList<M> getChildNodes()
    {
        return m_list;
    }

    @Override
    public int length()
    {
        return m_list.length();
    }

    @SuppressWarnings("unchecked")
    protected final T coerce()
    {
        return (T) this;
    }

    /**
     * Adds a primitive to the collection.
     * <p>
     * It should be noted that this operation will not have an apparent effect for an already rendered (drawn) Container.
     * In other words, if the Container has already been drawn and a new primitive is added, you'll need to invoke draw() on the
     * Container. This is done to enhance performance, otherwise, for every add we would have draws impacting performance.
     */
    @Override
    public T add(M child)
    {
        Node<?> node = child.asNode();

        node.setParent(this);

        m_list.add(child);

        return coerce();
    }

    /**
     * Removes a primitive from the container.
     * <p>
     * It should be noted that this operation will not have an apparent effect for an already rendered (drawn) Container.
     * In other words, if the Container has already been drawn and a new primitive is added, you'll need to invoke draw() on the
     * Container. This is done to enhance performance, otherwise, for every add we would have draws impacting performance.
     */
    @Override
    public T remove(M child)
    {
        Node<?> node = child.asNode();

        node.setParent(null);

        m_list.remove(child);

        return coerce();
    }

    /**
     * Removes all primitives from the collection.
     * <p>
     * It should be noted that this operation will not have an apparent effect for an already rendered (drawn) Container.
     * In other words, if the Container has already been drawn and a new primitive is added, you'll need to invoke draw() on the
     * Container. This is done to enhance performance, otherwise, for every add we would have draws impacting performance.
     */
    @Override
    public T removeAll()
    {
        m_list.removeAll();

        return coerce();
    }

    /**
     * Used internally. Draws the node in the current Context2D
     * without applying the transformation-related attributes 
     * (e.g. X, Y, ROTATION, SCALE, SHEAR, OFFSET and TRANSFORM.)
     * <p> 
     * Groups should draw their children in the current context.
     */
    @Override
    protected void drawWithoutTransforms(Context2D context)
    {
        final int size = m_list.length();

        for (int i = 0; i < size; i++)
        {
            m_list.get(i).drawWithTransforms(context);
        }
    }

    /**
     * Moves the {@link Layer} up
     */
    @Override
    public T moveUp(M node)
    {
        getChildNodes().moveUp(node);

        return coerce();
    }

    /**
     * Moves the {@link Layer} down
     */
    @Override
    public T moveDown(M node)
    {
        getChildNodes().moveDown(node);

        return coerce();
    }

    /**
     * Moves the {@link Layer} to the top
     */
    @Override
    public T moveToTop(M node)
    {
        getChildNodes().moveToTop(node);

        return coerce();
    }

    /**
     * Moves the {@link Layer} to the bottom
     */
    @Override
    public T moveToBottom(M node)
    {
        getChildNodes().moveToBottom(node);

        return coerce();
    }

    @Override
    public Iterator<M> iterator()
    {
        return new ContainerNodeIterator();
    }

    private class ContainerNodeIterator implements Iterator<M>
    {
        private int m_indx = 0;

        private int m_last = -1;

        @Override
        public boolean hasNext()
        {
            return (m_indx != length());
        }

        @Override
        public M next()
        {
            if (m_indx >= length())
            {
                throw new NoSuchElementException();
            }
            M next = getChildNodes().get(m_indx);

            m_last = m_indx++;

            return next;
        }

        @Override
        public void remove()
        {
            if (m_last == -1)
            {
                throw new IllegalStateException();
            }
            if (m_last >= length())
            {
                throw new NoSuchElementException();
            }
            M last = getChildNodes().get(m_last);

            ContainerNode.this.remove(last);

            if (m_last < m_indx)
            {
                m_indx--;
            }
            m_last = -1;
        }
    }
}