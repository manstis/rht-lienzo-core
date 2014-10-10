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

import com.emitrom.lienzo.client.core.LienzoGlobals;
import com.emitrom.lienzo.client.core.shape.json.ContainerNodeFactory;
import com.emitrom.lienzo.client.core.shape.json.IFactory;
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

    private final DivElement m_element = Document.get().createDivElement();

    /**
     * Constructor. Creates an instance of a scene.
     */
    public Scene()
    {
        super(NodeType.SCENE);
    }

    protected Scene(JSONObject node)
    {
        super(NodeType.SCENE, node);
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
    public Scene setPixelSize(int wide, int high)
    {
        m_wide = wide;

        m_high = high;

        m_element.getStyle().setWidth(wide, Unit.PX);

        m_element.getStyle().setHeight(high, Unit.PX);

        FastArrayList<Layer> layers = getChildNodes();

        if (null != layers)
        {
            int size = layers.length();

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
    public Scene getScene()
    {
        return this;
    }

    /**
     * Convenience method to return an instance of itself.
     * 
     * @return Scene
     */
    @Override
    public Scene asScene()
    {
        return this;
    }

    /**
     * Returns an instance of this scene cast to {@link IContainer}
     * 
     * @return Scene
     */
    @Override
    public IContainer<Layer> asContainer()
    {
        return this;
    }

    /**
     * Returns the top layer (which is drawn last)
     * 
     * @return Layer
     */
    public Layer getTopLayer()
    {
        FastArrayList<Layer> layers = getChildNodes();

        int n = layers.length();

        return n == 0 ? null : layers.get(n - 1);
    }

    /**
     * Iterates over the list of {@link Layer} and draws them all.
     */
    public void draw()
    {
        FastArrayList<Layer> layers = getChildNodes();

        if (null != layers)
        {
            int size = layers.length();

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
            FastArrayList<Layer> layers = getChildNodes();

            if (null != layers)
            {
                int size = layers.length();

                for (int i = size - 1; i >= 0; i--)
                {
                    Layer layer = layers.get(i);

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
        FastArrayList<Layer> layers = getChildNodes();

        if (null != layers)
        {
            int size = layers.length();

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
    public JSONObject toJSONObject()
    {
        JSONObject object = new JSONObject();

        object.put("type", new JSONString(getNodeType().getValue()));

        object.put("attributes", new JSONObject(getAttributes()));

        FastArrayList<Layer> list = getChildNodes();

        JSONArray children = new JSONArray();

        if (list != null)
        {
            int size = list.length();

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
    public void add(Layer layer)
    {
        if ((null != layer) && (LienzoGlobals.getInstance().isCanvasSupported()))
        {
            CanvasElement element = layer.getCanvasElement();

            layer.setPixelSize(m_wide, m_high);

            element.getStyle().setPosition(Position.ABSOLUTE);

            element.getStyle().setDisplay(Display.INLINE_BLOCK);

            getElement().appendChild(element);

            super.add(layer);

            layer.draw();
        }
    }

    /**
     * Removes a {@link Layer}
     */
    @Override
    public void remove(Layer layer)
    {
        if ((null != layer) && (LienzoGlobals.getInstance().isCanvasSupported()))
        {
            CanvasElement element = layer.getCanvasElement();

            getElement().removeChild(element);

            super.remove(layer);
        }
    }

    /**
     * Removes all {@link Layer}
     */
    @Override
    public void removeAll()
    {
        if (LienzoGlobals.getInstance().isCanvasSupported())
        {
            while (getElement().getChildCount() > 0)
            {
                CanvasElement element = getElement().getChild(0).cast();

                getElement().removeChild(element);
            }
            super.removeAll();
        }
    }

    /**
     * Moves the layer one level down in this scene.
     * 
     * @param layer
     */
    @Override
    public void moveDown(Layer layer)
    {
        if ((null != layer) && (LienzoGlobals.getInstance().isCanvasSupported()))
        {
            CanvasElement element = layer.getCanvasElement();

            int size = getElement().getChildCount();

            if (size < 2)
            {
                return;
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
    }

    /**
     * Moves the layer one level up in this scene.
     * 
     * @param layer
     */
    @Override
    public void moveUp(Layer layer)
    {
        if ((null != layer) && (LienzoGlobals.getInstance().isCanvasSupported()))
        {
            int size = getElement().getChildCount();

            if (size < 2)
            {
                return;
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
    }

    /**
     * Moves the layer to the top of the layers stack in this scene.
     * 
     * @param layer
     */
    @Override
    public void moveToTop(Layer layer)
    {
        if ((null != layer) && (LienzoGlobals.getInstance().isCanvasSupported()))
        {
            int size = getElement().getChildCount();

            if (size < 2)
            {
                return;
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
    }

    /**
     * Moves the layer to the bottom of the layers stack in this scene.
     * 
     * @param layer
     */
    @Override
    public void moveToBottom(Layer layer)
    {
        if ((null != layer) && (LienzoGlobals.getInstance().isCanvasSupported()))
        {
            int size = getElement().getChildCount();

            if (size < 2)
            {
                return;
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
    }

    /**
     * No-op, but must implement.
     * 
     * @return this Scene
     */
    @Override
    public Scene moveUp()
    {
        return this;
    }

    /**
     * No-op, but must implement.
     * 
     * @return this Scene
     */
    @Override
    public Scene moveDown()
    {
        return this;
    }

    /**
     * No-op, but must implement.
     * 
     * @return this Scene
     */
    @Override
    public Scene moveToTop()
    {
        return this;
    }

    /**
     * No-op, but must implement.
     * 
     * @return this Scene
     */
    @Override
    public Scene moveToBottom()
    {
        return this;
    }

    public final String toDataURL()
    {
        if (LienzoGlobals.getInstance().isCanvasSupported())
        {
            ScratchCanvas scratch = new ScratchCanvas(m_wide, m_high);

            FastArrayList<Layer> layers = getChildNodes();

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
        if (LienzoGlobals.getInstance().isCanvasSupported())
        {
            ScratchCanvas scratch = new ScratchCanvas(m_wide, m_high);

            FastArrayList<Layer> layers = getChildNodes();

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
        if (LienzoGlobals.getInstance().isCanvasSupported())
        {
            ScratchCanvas scratch = new ScratchCanvas(m_wide, m_high);

            FastArrayList<Layer> layers = getChildNodes();

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
        if (LienzoGlobals.getInstance().isCanvasSupported())
        {
            ScratchCanvas scratch = new ScratchCanvas(m_wide, m_high);

            FastArrayList<Layer> layers = getChildNodes();

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
    public ArrayList<Node<?>> search(INodeFilter filter)
    {
        ArrayList<Node<?>> find = new ArrayList<Node<?>>();

        if (filter.matches(this))
        {
            find.add(this);
        }
        int size = length();

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
    public boolean isValidForContainer(IJSONSerializable<?> node)
    {
        return (node instanceof Layer);
    }

    @Override
    public IFactory<?> getFactory()
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
        public Scene create(JSONObject node, ValidationContext ctx) throws ValidationException
        {
            Scene g = new Scene(node);

            JSONDeserializer.getInstance().deserializeChildren(g, node, this, ctx);

            return g;
        }

        @Override
        public boolean isValidForContainer(IContainer<?> g, IJSONSerializable<?> node)
        {
            return g.isValidForContainer(node);
        }
    }
}