/*
   Copyright (c) 2012-2014 Emitrom LLC. All rights reserved. 
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

import com.emitrom.lienzo.client.core.LienzoGlobals;
import com.emitrom.lienzo.client.core.shape.json.ContainerNodeFactory;
import com.emitrom.lienzo.client.core.shape.json.IFactory;
import com.emitrom.lienzo.client.core.shape.json.IJSONSerializable;
import com.emitrom.lienzo.client.core.shape.json.JSONDeserializer;
import com.emitrom.lienzo.client.core.shape.json.validators.ValidationContext;
import com.emitrom.lienzo.client.core.shape.json.validators.ValidationException;
import com.emitrom.lienzo.client.core.types.FastArrayList;
import com.emitrom.lienzo.client.core.types.INodeFilter;
import com.emitrom.lienzo.client.core.util.ScratchCanvas;
import com.emitrom.lienzo.shared.core.types.DataURLType;
import com.emitrom.lienzo.shared.core.types.NodeType;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

/**
 * Scene serves as a container for {@link Layer}<
 * 
 * <ul>
 * <li>A {@link Scene} can contain more than one {@link Layer}</li>
 * </ul> 
 */
public class Scene extends ContainerNode<Layer, Scene> implements IJSONSerializable<Scene>
{
    private int              m_wide    = 0;

    private int              m_high    = 0;

    private Viewport         m_owns    = null;

    private final DivElement m_element = Document.get().createDivElement();

    /**
     * Constructor. Creates an instance of a scene.
     */
    public Scene()
    {
        super(NodeType.SCENE);
    }

    protected Scene(JSONObject node, ValidationContext ctx) throws ValidationException
    {
        super(NodeType.SCENE, node, ctx);
    }

    public final boolean adopt(Viewport owns)
    {
        if ((null == m_owns) || (m_owns == owns))
        {
            m_owns = owns;

            return true;
        }
        return false;
    }

    /**
     * Returns the {@link DivElement}
     * 
     * @return {@link DivElement}
     */
    public DivElement getElement()
    {
        return m_element;
    }

    /**
     * Returns this scene's width, in pixels.
     * 
     * @return int
     */
    public int getWidth()
    {
        return m_wide;
    }

    /**
     * Returns this scene's height, in pixels
     * 
     * @return int
     */
    public int getHeight()
    {
        return m_high;
    }

    /**
     * Sets this scene's width, in pixels
     * 
     * @param wide
     * @return this Scene
     */
    public Scene setWidth(int wide)
    {
        setPixelSize(wide, m_high);

        return this;
    }

    /**
     * Sets this scene's height, in pixels
     * 
     * @param high
     * @return this Scene
     */
    public Scene setHeight(int high)
    {
        setPixelSize(m_wide, high);

        return this;
    }

    /**
     * Sets this scene's size (width and height) in pixels.
     * 
     * @param wide
     * @param high
     * @return this Scene
     */
    public final Scene setPixelSize(int wide, int high)
    {
        m_wide = wide;

        m_high = high;

        m_element.getStyle().setWidth(wide, Unit.PX);

        m_element.getStyle().setHeight(high, Unit.PX);

        final FastArrayList<Layer> layers = getChildNodes();

        if (null != layers)
        {
            final int size = layers.length();

            for (int i = 0; i < size; i++)
            {
                Layer layer = layers.get(i);

                if (null != layer)
                {
                    layer.setPixelSize(wide, high);
                }
            }
        }
        return this;
    }

    /**
     * Returns this scene.
     * 
     * @return Scene
     */
    @Override
    public final Scene getScene()
    {
        return this;
    }

    /**
     * Convenience method to return an instance of itself.
     * 
     * @return Scene
     */
    @Override
    public final Scene asScene()
    {
        return this;
    }

    /**
     * Returns an instance of this scene cast to {@link IContainer}
     * 
     * @return Scene
     */
    @Override
    public final IContainer<Scene, Layer> asContainer()
    {
        return this;
    }

    /**
     * Returns the top layer (which is drawn last)
     * 
     * @return Layer
     */
    public final Layer getTopLayer()
    {
        FastArrayList<Layer> layers = getChildNodes();

        int n = layers.length();

        return n == 0 ? null : layers.get(n - 1);
    }

    /**
     * Iterates over the list of {@link Layer} and draws them all.
     */
    public final void draw()
    {
        final FastArrayList<Layer> layers = getChildNodes();

        if (null != layers)
        {
            final int size = layers.length();

            for (int i = 0; i < size; i++)
            {
                Layer layer = layers.get(i);

                if (null != layer)
                {
                    layer.draw();
                }
            }
        }
    }

    /**
     * Given a set of (x,y) coordinates, returns the {@link Shape} that is matched.
     * The {@link Shape} returned will be the one found in the upper {@link Layer}
     * Return null if no {@link Shape} is detected or found.
     * 
     * @param x
     * @param y
     * @return Shape
     */
    public final Shape<?> findShapeAtPoint(int x, int y)
    {
        if (isVisible())
        {
            final FastArrayList<Layer> layers = getChildNodes();

            if (null != layers)
            {
                int size = layers.length();

                for (int i = size - 1; i >= 0; i--)
                {
                    final Layer layer = layers.get(i);

                    if (null != layer)
                    {
                        Shape<?> shape = layer.findShapeAtPoint(x, y);

                        if (null != shape)
                        {
                            return shape;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Fires the given GWT event.
     */
    public final void fireEvent(GwtEvent<?> event)
    {
        final FastArrayList<Layer> layers = getChildNodes();

        if (null != layers)
        {
            final int size = layers.length();

            for (int i = size - 1; i >= 0; i--)
            {
                Layer layer = layers.get(i);

                if (null != layer)
                {
                    layer.fireEvent(event);
                }
            }
        }
    }

    /**
     * Returns a {@link JSONObject} representation containing the object type, attributes and its respective children.
     * 
     * @return JSONObject
     */
    @Override
    public final JSONObject toJSONObject()
    {
        final JSONObject object = new JSONObject();

        object.put("type", new JSONString(getNodeType().getValue()));

        object.put("attributes", new JSONObject(getAttributes()));

        final FastArrayList<Layer> list = getChildNodes();

        final JSONArray children = new JSONArray();

        if (list != null)
        {
            final int size = list.length();

            for (int i = 0; i < size; i++)
            {
                Layer layer = list.get(i);

                if (null != layer)
                {
                    JSONObject make = layer.toJSONObject();

                    if (null != make)
                    {
                        children.set(children.size(), make);
                    }
                }
            }
        }
        object.put("children", children);

        return object;
    }

    /**
     * Adds a {@link Layer} to the Scene.
     * A draw will be invoked after the layer is added.
     */
    @Override
    public final Scene add(Layer layer)
    {
        if ((null != layer) && (LienzoGlobals.get().isCanvasSupported()))
        {
            CanvasElement element = layer.getCanvasElement();

            layer.setPixelSize(m_wide, m_high);

            element.getStyle().setPosition(Position.ABSOLUTE);

            element.getStyle().setDisplay(Display.INLINE_BLOCK);

            getElement().appendChild(element);

            super.add(layer);

            layer.batch();
        }
        return this;
    }

    /**
     * Removes a {@link Layer}
     */
    @Override
    public final Scene remove(Layer layer)
    {
        if ((null != layer) && (LienzoGlobals.get().isCanvasSupported()))
        {
            CanvasElement element = layer.getCanvasElement();

            getElement().removeChild(element);

            super.remove(layer);
        }
        return this;
    }

    /**
     * Removes all {@link Layer}
     */
    @Override
    public final Scene removeAll()
    {
        if (LienzoGlobals.get().isCanvasSupported())
        {
            while (getElement().getChildCount() > 0)
            {
                CanvasElement element = getElement().getChild(0).cast();

                getElement().removeChild(element);
            }
            super.removeAll();
        }
        return this;
    }

    /**
     * Moves the layer one level down in this scene.
     * 
     * @param layer
     */
    @Override
    public final Scene moveDown(Layer layer)
    {
        if ((null != layer) && (LienzoGlobals.get().isCanvasSupported()))
        {
            CanvasElement element = layer.getCanvasElement();

            final int size = getElement().getChildCount();

            if (size < 2)
            {
                return this;
            }
            for (int i = 0; i < size; i++)
            {
                CanvasElement look = getElement().getChild(i).cast();

                if (look == element)
                {
                    if (i == 0)
                    {
                        break; // already at bottom
                    }
                    look = getElement().getChild(i - 1).cast();

                    getElement().insertBefore(element, look);

                    break;
                }
            }
            FastArrayList<Layer> layers = getChildNodes();

            if (null != layers)
            {
                layers.moveDown(layer);
            }
        }
        return this;
    }

    /**
     * Moves the layer one level up in this scene.
     * 
     * @param layer
     */
    @Override
    public final Scene moveUp(Layer layer)
    {
        if ((null != layer) && (LienzoGlobals.get().isCanvasSupported()))
        {
            final int size = getElement().getChildCount();

            if (size < 2)
            {
                return this;
            }
            CanvasElement element = layer.getCanvasElement();

            for (int i = 0; i < size; i++)
            {
                CanvasElement look = getElement().getChild(i).cast();

                if (look == element)
                {
                    if ((i + 1) == size)
                    {
                        break; // already at top
                    }
                    look = getElement().getChild(i + 1).cast();

                    getElement().removeChild(element);

                    getElement().insertAfter(element, look);

                    break;
                }
            }
            FastArrayList<Layer> layers = getChildNodes();

            if (null != layers)
            {
                layers.moveUp(layer);
            }
        }
        return this;
    }

    /**
     * Moves the layer to the top of the layers stack in this scene.
     * 
     * @param layer
     */
    @Override
    public final Scene moveToTop(Layer layer)
    {
        if ((null != layer) && (LienzoGlobals.get().isCanvasSupported()))
        {
            final int size = getElement().getChildCount();

            if (size < 2)
            {
                return this;
            }
            CanvasElement element = layer.getCanvasElement();

            getElement().removeChild(element);

            getElement().appendChild(element);

            FastArrayList<Layer> layers = getChildNodes();

            if (null != layers)
            {
                layers.moveToTop(layer);
            }
        }
        return this;
    }

    /**
     * Moves the layer to the bottom of the layers stack in this scene.
     * 
     * @param layer
     */
    @Override
    public final Scene moveToBottom(Layer layer)
    {
        if ((null != layer) && (LienzoGlobals.get().isCanvasSupported()))
        {
            final int size = getElement().getChildCount();

            if (size < 2)
            {
                return this;
            }
            CanvasElement element = layer.getCanvasElement();

            getElement().removeChild(element);

            getElement().insertFirst(element);

            FastArrayList<Layer> layers = getChildNodes();

            if (null != layers)
            {
                layers.moveToBottom(layer);
            }
        }
        return this;
    }

    /**
     * No-op, but must implement.
     * 
     * @return this Scene
     */
    @Override
    public final Scene moveUp()
    {
        return this;
    }

    /**
     * No-op, but must implement.
     * 
     * @return this Scene
     */
    @Override
    public final Scene moveDown()
    {
        return this;
    }

    /**
     * No-op, but must implement.
     * 
     * @return this Scene
     */
    @Override
    public final Scene moveToTop()
    {
        return this;
    }

    /**
     * No-op, but must implement.
     * 
     * @return this Scene
     */
    @Override
    public final Scene moveToBottom()
    {
        return this;
    }

    public final String toDataURL()
    {
        if (LienzoGlobals.get().isCanvasSupported())
        {
            ScratchCanvas scratch = new ScratchCanvas(m_wide, m_high);

            final FastArrayList<Layer> layers = getChildNodes();

            if (null != layers)
            {
                final int size = layers.length();

                for (int i = size - 1; i >= 0; i--)
                {
                    Layer layer = layers.get(i);

                    if ((null != layer) && (layer.isVisible()))
                    {
                        layer.drawWithTransforms(scratch.getContext());
                    }
                }
            }
            return scratch.toDataURL();
        }
        else
        {
            return "data:,";
        }
    }

    // package protected

    final String toDataURL(Layer background)
    {
        if (LienzoGlobals.get().isCanvasSupported())
        {
            ScratchCanvas scratch = new ScratchCanvas(m_wide, m_high);

            final FastArrayList<Layer> layers = getChildNodes();

            if (null != layers)
            {
                final int size = layers.length();

                if (null != background)
                {
                    background.drawWithTransforms(scratch.getContext());
                }
                for (int i = size - 1; i >= 0; i--)
                {
                    Layer layer = layers.get(i);

                    if ((null != layer) && (layer.isVisible()))
                    {
                        layer.drawWithTransforms(scratch.getContext());
                    }
                }
            }
            return scratch.toDataURL();
        }
        else
        {
            return "data:,";
        }
    }

    public final String toDataURL(DataURLType mimetype)
    {
        if (LienzoGlobals.get().isCanvasSupported())
        {
            ScratchCanvas scratch = new ScratchCanvas(m_wide, m_high);

            final FastArrayList<Layer> layers = getChildNodes();

            if (null != layers)
            {
                final int size = layers.length();

                for (int i = size - 1; i >= 0; i--)
                {
                    Layer layer = layers.get(i);

                    if ((null != layer) && (layer.isVisible()))
                    {
                        layer.drawWithTransforms(scratch.getContext());
                    }
                }
            }
            return scratch.toDataURL(mimetype);
        }
        else
        {
            return "data:,";
        }
    }

    // package protected

    final String toDataURL(DataURLType mimetype, Layer background)
    {
        if (LienzoGlobals.get().isCanvasSupported())
        {
            ScratchCanvas scratch = new ScratchCanvas(m_wide, m_high);

            final FastArrayList<Layer> layers = getChildNodes();

            if (null != layers)
            {
                final int size = layers.length();

                if (null != background)
                {
                    background.drawWithTransforms(scratch.getContext());
                }
                for (int i = size - 1; i >= 0; i--)
                {
                    Layer layer = layers.get(i);

                    if ((null != layer) && (layer.isVisible()))
                    {
                        layer.drawWithTransforms(scratch.getContext());
                    }
                }
            }
            return scratch.toDataURL(mimetype);
        }
        else
        {
            return "data:,";
        }
    }

    @Override
    public final ArrayList<Node<?>> search(INodeFilter filter)
    {
        final ArrayList<Node<?>> find = new ArrayList<Node<?>>();

        if (filter.matches(this))
        {
            find.add(this);
        }
        final int size = length();

        for (int i = 0; i < size; i++)
        {
            Layer layer = getChildNodes().get(i);

            if (null != layer)
            {
                if (filter.matches(layer))
                {
                    if (false == find.contains(layer))
                    {
                        find.add(layer);
                    }
                }
                for (Node<?> look : layer.search(filter))
                {
                    if (false == find.contains(look))
                    {
                        find.add(look);
                    }
                }
            }
        }
        return find;
    }

    @Override
    public final IFactory<Scene> getFactory()
    {
        return new SceneFactory();
    }

    public static class SceneFactory extends ContainerNodeFactory<Scene>
    {
        public SceneFactory()
        {
            super(NodeType.SCENE);
        }

        @Override
        public final Scene create(JSONObject node, ValidationContext ctx) throws ValidationException
        {
            Scene container = new Scene(node, ctx);

            JSONDeserializer.getInstance().deserializeChildren(container, node, this, ctx);

            return container;
        }

        @Override
        public final boolean addNodeForContainer(IContainer<?, ?> container, Node<?> node, ValidationContext ctx)
        {
            if (node.getNodeType() == NodeType.LAYER)
            {
                container.asScene().add(node.asLayer());

                return true;
            }
            else
            {
                try
                {
                    ctx.addBadTypeError(node.getClass().getName() + " is not a Layer");
                }
                catch (ValidationException e)
                {
                    return false;
                }
            }
            return false;
        }
    }
}