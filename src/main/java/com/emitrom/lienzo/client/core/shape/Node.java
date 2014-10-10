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
import java.util.Collection;

import com.emitrom.lienzo.client.core.Attribute;
import com.emitrom.lienzo.client.core.Context2D;
import com.emitrom.lienzo.client.core.event.NodeDragEndEvent;
import com.emitrom.lienzo.client.core.event.NodeDragEndHandler;
import com.emitrom.lienzo.client.core.event.NodeDragMoveEvent;
import com.emitrom.lienzo.client.core.event.NodeDragMoveHandler;
import com.emitrom.lienzo.client.core.event.NodeDragStartEvent;
import com.emitrom.lienzo.client.core.event.NodeDragStartHandler;
import com.emitrom.lienzo.client.core.event.NodeGestureChangeEvent;
import com.emitrom.lienzo.client.core.event.NodeGestureChangeHandler;
import com.emitrom.lienzo.client.core.event.NodeGestureEndEvent;
import com.emitrom.lienzo.client.core.event.NodeGestureEndHandler;
import com.emitrom.lienzo.client.core.event.NodeGestureStartEvent;
import com.emitrom.lienzo.client.core.event.NodeGestureStartHandler;
import com.emitrom.lienzo.client.core.event.NodeMouseClickEvent;
import com.emitrom.lienzo.client.core.event.NodeMouseClickHandler;
import com.emitrom.lienzo.client.core.event.NodeMouseDoubleClickEvent;
import com.emitrom.lienzo.client.core.event.NodeMouseDoubleClickHandler;
import com.emitrom.lienzo.client.core.event.NodeMouseDownEvent;
import com.emitrom.lienzo.client.core.event.NodeMouseDownHandler;
import com.emitrom.lienzo.client.core.event.NodeMouseEnterEvent;
import com.emitrom.lienzo.client.core.event.NodeMouseEnterHandler;
import com.emitrom.lienzo.client.core.event.NodeMouseExitEvent;
import com.emitrom.lienzo.client.core.event.NodeMouseExitHandler;
import com.emitrom.lienzo.client.core.event.NodeMouseMoveEvent;
import com.emitrom.lienzo.client.core.event.NodeMouseMoveHandler;
import com.emitrom.lienzo.client.core.event.NodeMouseOutEvent;
import com.emitrom.lienzo.client.core.event.NodeMouseOutHandler;
import com.emitrom.lienzo.client.core.event.NodeMouseOverEvent;
import com.emitrom.lienzo.client.core.event.NodeMouseOverHandler;
import com.emitrom.lienzo.client.core.event.NodeMouseUpEvent;
import com.emitrom.lienzo.client.core.event.NodeMouseUpHandler;
import com.emitrom.lienzo.client.core.event.NodeMouseWheelEvent;
import com.emitrom.lienzo.client.core.event.NodeMouseWheelHandler;
import com.emitrom.lienzo.client.core.event.NodeTouchCancelEvent;
import com.emitrom.lienzo.client.core.event.NodeTouchCancelHandler;
import com.emitrom.lienzo.client.core.event.NodeTouchEndEvent;
import com.emitrom.lienzo.client.core.event.NodeTouchEndHandler;
import com.emitrom.lienzo.client.core.event.NodeTouchMoveEvent;
import com.emitrom.lienzo.client.core.event.NodeTouchMoveHandler;
import com.emitrom.lienzo.client.core.event.NodeTouchStartEvent;
import com.emitrom.lienzo.client.core.event.NodeTouchStartHandler;
import com.emitrom.lienzo.client.core.shape.json.JSONDeserializer;
import com.emitrom.lienzo.client.core.types.NativeInternalType;
import com.emitrom.lienzo.client.core.types.Point2D;
import com.emitrom.lienzo.client.core.types.Transform;
import com.emitrom.lienzo.shared.core.types.NodeType;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Node is the base class for {@link ContainerNode} and {@link Shape}.
 * This class provides a lot of the scaffolding for drawable nodes.
 * 
 * @param <T>
 */
public abstract class Node<T extends Node<T>> implements IDrawable<T>, IJSONSerializable<T>
{
    static
    {
        RootPanel.get().getElement().getStyle().setProperty("webkitTapHighlightColor", "rgba(0,0,0,0)");
    }

    private final Attributes m_attr;

    private NodeType         m_type;

    private Node<?>          m_parent;

    private HandlerManager   m_events = new HandlerManager(this);

    protected Node(NodeType type)
    {
        m_type = type;

        m_attr = Attributes.make();

        setVisible(true).setListening(true);
    }

    /**
     * Only sub-classes that wish to extend a Shape should use this.
     * 
     * @param type
     */
    protected void setNodeType(NodeType type)
    {
        m_type = type;
    }

    /**
     * Constructor used by deserialization code.
     * 
     * @param type
     * @param node
     */
    protected Node(NodeType type, JSONObject node)
    {
        m_type = type;

        if (null == node)
        {
            m_attr = Attributes.make();

            return;
        }
        JSONValue aval = node.get("attributes");

        if (null == aval)
        {
            m_attr = Attributes.make();

            return;
        }
        JSONObject aobj = aval.isObject();

        if (null == aobj)
        {
            m_attr = Attributes.make();

            return;
        }
        JavaScriptObject ajso = aobj.getJavaScriptObject();

        if (null == ajso)
        {
            m_attr = Attributes.make();

            return;
        }
        m_attr = ajso.cast();

        if (NativeInternalType.BOOLEAN != m_attr.typeOf(Attribute.VISIBLE))
        {
            setVisible(true);
        }
        if (NativeInternalType.BOOLEAN != m_attr.typeOf(Attribute.LISTENING))
        {
            setListening(true);
        }
    }

    @SuppressWarnings("unchecked")
    protected final <M> M cast()
    {
        return (M) this;
    }

    /**
     * Returns a copy of this Node.
     * 
     * @return T
     */
    public abstract T copy();

    protected Node<?> copyUnchecked()
    {
        return (Node<?>) JSONDeserializer.getInstance().fromString(toJSONString(), false); // don't validate
    }

    /**
     * Serializes this Node as a JSON string.
     * The JSON string can be deserialized with 
     * {@link JSONDeserializer#fromString(String)}.
     * 
     * @return JSON string
     */
    public String toJSONString()
    {
        JSONObject object = toJSONObject();

        if (null != object)
        {
            return object.toString();
        }
        return null;
    }

    /**
     * Returns the collection of {@link Attribute} for this object.
     * 
     * @return Collection&lt;Attribute&gt;
     */
    public Collection<Attribute> getAttributeSheet()
    {
        return getFactory().getAttributeSheet();
    }

    /**
     * Returns the collection of required {@link Attribute} for this object.
     * 
     * @return Collection&lt;Attribute&gt;
     */
    public Collection<Attribute> getRequiredAttributes()
    {
        return getFactory().getRequiredAttributes();
    }

    protected void setParent(Node<?> parent)
    {
        m_parent = parent;
    }

    public Node<?> getParent()
    {
        return m_parent;
    }

    /**
     * Returns the Layer that this Node is on.
     * 
     * @return {@link Layer}
     */
    public Layer getLayer()
    {
        Node<?> parent = getParent(); // change, no iteration, no testing, no casting, recurses upwards to a Layer, and Layer returns itself, CYCLES!!!

        if (null != parent)
        {
            return parent.getLayer();
        }
        return null;
    }

    /**
     * Returns the Scena that this Node is on.
     * 
     * @return Scene
     */
    @Override
    public Scene getScene()
    {
        Node<?> parent = getParent(); // change, no iteration, no testing, no casting, recurses upwards to a Scene, and Scene returns itself, CYCLES!!!

        if (null != parent)
        {
            return parent.getScene();
        }
        return null;
    }

    /**
     * Returns the Viewport that this Node is on.
     */
    public Viewport getViewport()
    {
        Node<?> parent = getParent(); // change, no iteration, no testing, no casting, recurses upwards to a Viewport, and Viewport returns itself, CYCLES!!!

        if (null != parent)
        {
            return parent.getViewport();
        }
        return null;
    }

    public HandlerManager getHandlerManager()
    {
        return m_events;
    }

    /**
     * Returns the node's {@link NodeType}.
     * @return {@link NodeType}
     */
    public NodeType getNodeType()
    {
        return m_type;
    }

    public final Attributes getAttributes()
    {
        return m_attr;
    }

    /**
     * Used internally. Applies the node's transform-related attributes
     * to the current context, draws the node (and it's children, if any)
     * and restores the context.
     */
    public void drawWithTransforms(Context2D context)
    {
        if (context.isDrag() || isVisible())
        {
            context.save();

            Transform xfrm = getCombinedTransform();

            context.transform(xfrm);

            drawWithoutTransforms(context);

            context.restore();
        }
    }

    /**
     * Used internally. Draws the node in the current Context2D
     * without applying the transformation-related attributes 
     * (e.g. X, Y, ROTATION, SCALE, SHEAR, OFFSET and TRANSFORM.)
     * <p> 
     * Shapes should apply the non-Transform related attributes (such a colors, strokeWidth etc.)
     * and draw the Shape's details (such as the the actual lines and fills) 
     * and Groups should draw their children.
     * 
     * @param context
     */
    protected void drawWithoutTransforms(Context2D context)
    {
    }

    /**
     * Returns the absolute transform by concatenating the transforms
     * of all its ancestors from the Viewport down to this node's parent.
     * 
     * @return {@link Transform}
     */
    public Transform getAbsoluteTransform()
    {
        Transform xfrm = new Transform();

        ArrayList<Node<?>> list = new ArrayList<Node<?>>();

        list.add(this);

        Node<?> parent = this.getParent();

        while (parent != null)
        {
            list.add(parent);

            parent = parent.getParent();
        }
        int size = list.size(); // TODO no need to use a list

        for (int i = size - 1; i >= 0; i--)
        {
            xfrm.multiply(list.get(i).getCombinedTransform());
        }
        return xfrm;
    }

    protected Transform getCombinedTransform()
    {
        Transform xfrm = new Transform();

        Attributes attr = getAttributes();

        double x = attr.getX();

        double y = attr.getY();

        if ((x != 0) || (y != 0))
        {
            xfrm.translate(x, y);
        }
        Transform t2 = getTransform();

        if (t2 != null) // Use the Transform if it's defined
        {
            xfrm.multiply(t2);
        }
        else
        // Otherwise use ROTATION, SCALE and OFFSET
        {
            double r = attr.getRotation();

            if (r != 0)
            {
                xfrm.rotate(r);
            }
            if (attr.isDefined(Attribute.SCALE))
            {
                Point2D scale = attr.getScale();

                if (null != scale)
                {
                    x = scale.getX();

                    y = scale.getY();

                    if ((x != 1) || (y != 1))
                    {
                        xfrm.scale(x, y);
                    }
                }
            }
            if (attr.isDefined(Attribute.SHEAR))
            {
                Point2D shear = attr.getShear();

                if (null != shear)
                {
                    x = shear.getX();

                    y = shear.getY();

                    if ((x != 0) || (y != 0))
                    {
                        xfrm.shear(x, y);
                    }
                }
            }
            if (attr.isDefined(Attribute.OFFSET))
            {
                Point2D offset = attr.getOffset();

                if (null != offset)
                {
                    x = offset.getX();

                    y = offset.getY();

                    if ((x != 0) || (y != 0))
                    {
                        xfrm.translate(x, y);
                    }
                }
            }
        }
        return xfrm;
    }

    public T setTransform(Transform transform)
    {
        getAttributes().setTransform(transform);

        return cast();
    }

    public Transform getTransform()
    {
        return getAttributes().getTransform();
    }

    /**
     * Sets whether the node is visible.
     * 
     * @param visible
     * @return this Node
     */
    public T setVisible(boolean visible)
    {
        m_attr.setVisible(visible);

        return cast();
    }

    @Override
    public boolean isVisible()
    {
        return m_attr.isVisible();
    }

    /**
     * Sets whether this node is listening for events.
     * 
     * @param listening
     * @return this Node
     */
    public T setListening(boolean listening)
    {
        m_attr.setListening(listening);

        return cast();
    }

    @Override
    public boolean isListening()
    {
        return m_attr.isListening();
    }

    /**
     * Sets the name of this Node.
     * 
     * @param name
     * @return this Node
     */
    public T setName(String name)
    {
        m_attr.setName(name);

        return cast();
    }

    /**
     * Returns the name of this Node.
     * @return String
     */
    public String getName()
    {
        return m_attr.getName();
    }

    /**
     * Sets the ID of this node.
     * 
     * @param id
     * @return
     */
    public T setID(String id)
    {
        m_attr.setID(id);

        return cast();
    }

    /**
     * Returns the ID of this node.
     * @return String
     */
    public String getID()
    {
        return m_attr.getID();
    }

    /**
     * Returns this Node
     * @return Node
     */
    @Override
    public Node<?> asNode()
    {
        return this;
    }

    @Override
    public IContainer<?> asContainer()
    {
        return null;
    }

    @Override
    public IPrimitive<?> asPrimitive()
    {
        return null;
    }

    @Override
    public Scene asScene()
    {
        return null;
    }

    @Override
    public boolean isEventHandled(Type<?> type)
    {
        if ((null != m_events) && (isListening()) && (((isVisible()) || (type == NodeDragStartEvent.getType()) || (type == NodeDragMoveEvent.getType()))))
        {
            return ((m_events.isEventHandled(type)) && ((m_events.getHandlerCount(type) > 0)));
        }
        return false;
    }

    @Override
    public void fireEvent(GwtEvent<?> event)
    {
        if ((null != m_events) && (isListening()) && (((isVisible()) || (event.getAssociatedType() == NodeDragStartEvent.getType()) || (event.getAssociatedType() == NodeDragMoveEvent.getType()))))
        {
            m_events.fireEvent(event);
        }
    }

    protected final <H extends EventHandler> HandlerRegistration addEnsureHandler(Type<H> type, H handler)
    {
        return m_events.addHandler(type, handler);
    }

    public HandlerRegistration addNodeMouseClickHandler(NodeMouseClickHandler handler)
    {
        return addEnsureHandler(NodeMouseClickEvent.getType(), handler);
    }

    public HandlerRegistration addNodeMouseDoubleClickHandler(NodeMouseDoubleClickHandler handler)
    {
        return addEnsureHandler(NodeMouseDoubleClickEvent.getType(), handler);
    }

    public HandlerRegistration addNodeMouseDownHandler(NodeMouseDownHandler handler)
    {
        return addEnsureHandler(NodeMouseDownEvent.getType(), handler);
    }

    public HandlerRegistration addNodeMouseMoveHandler(NodeMouseMoveHandler handler)
    {
        return addEnsureHandler(NodeMouseMoveEvent.getType(), handler);
    }

    public HandlerRegistration addNodeMouseOutHandler(NodeMouseOutHandler handler)
    {
        return addEnsureHandler(NodeMouseOutEvent.getType(), handler);
    }

    public HandlerRegistration addNodeMouseOverHandler(NodeMouseOverHandler handler)
    {
        return addEnsureHandler(NodeMouseOverEvent.getType(), handler);
    }

    public HandlerRegistration addNodeMouseExitHandler(NodeMouseExitHandler handler)
    {
        return addEnsureHandler(NodeMouseExitEvent.getType(), handler);
    }

    public HandlerRegistration addNodeMouseEnterHandler(NodeMouseEnterHandler handler)
    {
        return addEnsureHandler(NodeMouseEnterEvent.getType(), handler);
    }

    public HandlerRegistration addNodeMouseUpHandler(NodeMouseUpHandler handler)
    {
        return addEnsureHandler(NodeMouseUpEvent.getType(), handler);
    }

    public HandlerRegistration addNodeMouseWheelHandler(NodeMouseWheelHandler handler)
    {
        return addEnsureHandler(NodeMouseWheelEvent.getType(), handler);
    }

    public HandlerRegistration addNodeTouchCancelHandler(NodeTouchCancelHandler handler)
    {
        return addEnsureHandler(NodeTouchCancelEvent.getType(), handler);
    }

    public HandlerRegistration addNodeTouchEndHandler(NodeTouchEndHandler handler)
    {
        return addEnsureHandler(NodeTouchEndEvent.getType(), handler);
    }

    public HandlerRegistration addNodeTouchMoveHandler(NodeTouchMoveHandler handler)
    {
        return addEnsureHandler(NodeTouchMoveEvent.getType(), handler);
    }

    public HandlerRegistration addNodeTouchStartHandler(NodeTouchStartHandler handler)
    {
        return addEnsureHandler(NodeTouchStartEvent.getType(), handler);
    }

    public HandlerRegistration addNodeGestureStartHandler(NodeGestureStartHandler handler)
    {
        return addEnsureHandler(NodeGestureStartEvent.getType(), handler);
    }

    public HandlerRegistration addNodeGestureEndHandler(NodeGestureEndHandler handler)
    {
        return addEnsureHandler(NodeGestureEndEvent.getType(), handler);
    }

    public HandlerRegistration addNodeGestureChangeHandler(NodeGestureChangeHandler handler)
    {
        return addEnsureHandler(NodeGestureChangeEvent.getType(), handler);
    }

    public HandlerRegistration addNodeDragEndHandler(NodeDragEndHandler handler)
    {
        return addEnsureHandler(NodeDragEndEvent.getType(), handler);
    }

    public HandlerRegistration addNodeDragMoveHandler(NodeDragMoveHandler handler)
    {
        return addEnsureHandler(NodeDragMoveEvent.getType(), handler);
    }

    public HandlerRegistration addNodeDragStartHandler(NodeDragStartHandler handler)
    {
        return addEnsureHandler(NodeDragStartEvent.getType(), handler);
    }

    @Override
    public String toString()
    {
        return toJSONString();
    }

    @Override
    public int hashCode()
    {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == this)
        {
            return true;
        }
        if (null == obj)
        {
            return false;
        }
        if (false == (obj instanceof Node))
        {
            return false;
        }
        Node<?> node = ((Node<?>) obj);

        if (this.getNodeType() != node.getNodeType())
        {
            return false;
        }
        if ((this instanceof Shape) && (node instanceof Shape))
        {
            if (((Shape<?>) this).getShapeType() != ((Shape<?>) node).getShapeType())
            {
                return false;
            }
        }
        return toString().equals(obj.toString());
    }
}