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

package com.emitrom.lienzo.client.widget;

import com.emitrom.lienzo.client.core.LienzoGlobals;
import com.emitrom.lienzo.client.core.event.OrientationChangeEvent;
import com.emitrom.lienzo.client.core.event.OrientationChangeEvent.Orientation;
import com.emitrom.lienzo.client.core.event.ResizeChangeEvent;
import com.emitrom.lienzo.client.core.event.ResizeEndEvent;
import com.emitrom.lienzo.client.core.event.ResizeStartEvent;
import com.emitrom.lienzo.client.core.i18n.MessageConstants;
import com.emitrom.lienzo.client.core.mediator.IMediator;
import com.emitrom.lienzo.client.core.mediator.Mediators;
import com.emitrom.lienzo.client.core.shape.Layer;
import com.emitrom.lienzo.client.core.shape.Scene;
import com.emitrom.lienzo.client.core.shape.Viewport;
import com.emitrom.lienzo.shared.core.types.DataURLType;
import com.emitrom.lienzo.shared.core.types.IColor;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;

/**
 * LienzoPanel acts as a Container for a {@link Viewport}.
 * 
 * <ul>
 * <li>An application will typically be composed of one or more LienzoPanels.</li>
 * <li>A LienzoPanel takes width and height as input parameters.</li>
 * <li>A {@link Viewport} will contain one main {@link Scene}</li>
 * <li>The main {@link Scene} can contain multiple {@link Layer}.</li>
 * </ul> 
 */
public class LienzoPanel extends FocusPanel
{
    private final LienzoHandlerManager m_events;

    private final int                  m_wide;

    private final int                  m_high;

    private Viewport                   m_view;

    private boolean                    m_resizing                     = false;

    private Timer                      m_resize_timer;

    private int                        m_resize_check_repeat_interval = 750;

    private Orientation                m_orientation;

    public LienzoPanel(int wide, int high)
    {
        m_wide = wide;

        m_high = high;

        m_view = new Viewport(wide, high);

        if (LienzoGlobals.getInstance().isCanvasSupported())
        {
            getElement().appendChild(m_view.getElement());

            setPixelSize(wide, high);

            m_events = new LienzoHandlerManager(this);

            initResizeTimer();

            addHandlers();

        }
        else
        {
            add(new Label(MessageConstants.MESSAGES.getCanvasUnsupportedMessage()));

            m_events = null;
        }

    }

    /**
     * Adds a layer to the {@link LienzoPanel}.
     * It should be noted that this action will cause a {@link Layer} draw operation, painting all children in the Layer.
     * 
     * @param layer
     * @return
     */
    public LienzoPanel add(Layer layer)
    {
        getScene().add(layer);

        return this;
    }

    /**
     * Removes a layer from the {@link LienzoPanel}.
     * It should be noted that this action will cause a {@link Layer} draw operation, painting all children in the Layer.
     * 
     * @param layer
     * @return
     */
    public LienzoPanel remove(Layer layer)
    {
        getScene().remove(layer);

        return this;
    }

    /**
     * Removes all layer from the {@link LienzoPanel}.
     * It should be noted that this action will cause a {@link Layer} draw operation, painting all children in the Layer.
     * 
     * @param layer
     * @return
     */
    public LienzoPanel removeAll()
    {
        getScene().removeAll();

        return this;
    }

    /**
     * Sets the size in pixels of the {@link LienzoPanel}
     * Sets the size in pixels of the {@link Viewport} contained and automatically added to the instance of the {@link LienzoPanel}
     */
    @Override
    public void setPixelSize(int wide, int high)
    {
        super.setPixelSize(wide, high);

        m_view.setPixelSize(wide, high);
    }

    /**
     * Sets the type of cursor to be used when hovering above the element.
     * @param cursor
     */
    public void setCursor(final Cursor cursor)
    {
        Scheduler.get().scheduleDeferred(new ScheduledCommand()
        {
            @Override
            public void execute()
            {
                getElement().getStyle().setCursor(cursor);
            }
        });
    }

    /**
     * Returns the {@link Viewport} main {@link Scene}
     * @return
     */
    public Scene getScene()
    {
        return m_view.getScene();
    }

    /**
     * Returns the automatically create {@link Viewport} instance.
     * @return
     */
    public Viewport getViewport()
    {
        return m_view;
    }

    /**
     * Sets the {@link Viewport} background {@link Layer}
     * 
     * @param layer
     */
    public void setBackgroundLayer(Layer layer)
    {
        m_view.setBackgroundLayer(layer);
    }

    /**
     * Returns the {@link Viewport} Drag {@link Layer}
     * 
     * @return
     */
    public Layer getDragLayer()
    {
        return m_view.getDraglayer();
    }

    /**
     * Sets the frequency in milliseconds at which the
     * timer will check to see if a resize operation is taking place.
     * 
     * Default value is 750 milliseconds.
     * 
     * The value is used to determine when to trigger a ResizeEndEvent
     * 
     * @param resizeCheckRepeatInterval
     */
    public void setResizeCheckRepeatInterval(int resizeCheckRepeatInterval)
    {
        m_resize_check_repeat_interval = resizeCheckRepeatInterval;
    }

    /**
     * Returns the resize check repeat interval that is being used
     * to check if there is a resize operation taking place.
     * 
     * The value is used to determine when to trigger a ResizeEndEvent
     * 
     * @return
     */
    public int setResizeCheckRepeatInterval()
    {
        return m_resize_check_repeat_interval;
    }

    /**
     * Gets the width in pixels.
     * 
     * @return
     */
    public int getWidth()
    {
        return m_wide;
    }

    /**
     * Returns the height.
     * 
     * @return
     */
    public int getHeight()
    {
        return m_high;
    }

    /**
     * Returns a JSON representation of the {@link Viewport} children.
     * @return
     */
    public String toJSONString()
    {
        return m_view.toJSONString();
    }

    public final String toDataURL()
    {
        return m_view.toDataURL();
    }

    public final String toDataURL(boolean includeBackgroundLayer)
    {
        return m_view.toDataURL(includeBackgroundLayer);
    }

    public final String toDataURL(DataURLType mimetype)
    {
        return m_view.toDataURL(mimetype);
    }

    public final String toDataURL(DataURLType mimetype, boolean includeBackgroundLayer)
    {
        return m_view.toDataURL(mimetype, includeBackgroundLayer);
    }

    /**
     * Sets the background color of the LienzoPanel.
     * 
     * @param color String
     * @return this LienzoPanel
     */
    public LienzoPanel setBackgroundColor(String color)
    {
        getElement().getStyle().setBackgroundColor(color);

        return this;
    }

    /**
     * Sets the background color of the LienzoPanel.
     * 
     * @param color IColor, i.e. ColorName or Color
     * @return this LienzoPanel
     */
    public LienzoPanel setBackgroundColor(IColor color)
    {
        return setBackgroundColor(color.getColorString());
    }

    /**
     * Returns the background color of this LienzoPanel.
     * Will return null if no color was set, in which case it's probably "white",
     * unless it was changed via CSS rules.
     * 
     * @return String
     */
    public String getBackgroundColor()
    {
        return getElement().getStyle().getBackgroundColor();
    }

    /**
     * Returns the {@link Mediators} for this panels {@link Viewport}.
     * Mediators can be used to e.g. to add zoom operations.
     * 
     * @return Mediators
     */
    public Mediators getMediators()
    {
        return m_view.getMediators();
    }

    /**
     * Add a mediator to the stack of {@link Mediators} for this panels {@link Viewport}.
     * The one that is added last, will be called first.
     * 
     * Mediators can be used to e.g. to add zoom operations.
     * 
     * @param mediator IMediator
     */
    public void pushMediator(IMediator mediator)
    {
        m_view.pushMediator(mediator);
    }

    public static native void enableWindowMouseWheelScroll(boolean enabled)
    /*-{
		$wnd.mousewheel = function() {
			return enabled;
		}
    }-*/;

    private void initResizeTimer()
    {
        m_resize_timer = new Timer()
        {
            @Override
            public void run()
            {
                m_resizing = false;

                if (!m_resizing)
                {
                    int w = getElement().getParentElement().getClientWidth();

                    int h = getElement().getParentElement().getClientHeight();

                    getViewport().getHandlerManager().fireEvent(new ResizeEndEvent(w, h));

                    cancel();
                }
            }
        };
    }

    private void addHandlers()
    {
        Window.addResizeHandler(new ResizeHandler()
        {
            @Override
            public void onResize(ResizeEvent event)
            {
                int w = getElement().getParentElement().getClientWidth();

                int h = getElement().getParentElement().getClientHeight();

                setPixelSize(w, h);

                if (!m_resizing)
                {
                    m_resizing = true;

                    getViewport().getHandlerManager().fireEvent(new ResizeStartEvent(w, h));

                    m_resize_timer.scheduleRepeating(m_resize_check_repeat_interval);
                }
                m_resizing = true;

                getViewport().getHandlerManager().fireEvent(new ResizeChangeEvent(w, h));

                Orientation orientation;

                if (w > h)
                {
                    orientation = Orientation.LANDSCAPE;
                }
                else
                {
                    orientation = Orientation.PORTRAIT;
                }
                if (orientation != m_orientation)
                {
                    m_orientation = orientation;

                    getViewport().getHandlerManager().fireEvent(new OrientationChangeEvent(w, h));
                }
                Scheduler.get().scheduleDeferred(new ScheduledCommand()
                {
                    @Override
                    public void execute()
                    {
                        m_view.draw();
                    }
                });
            }
        });
    }
}