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

package com.emitrom.lienzo.client.core.event;

import com.emitrom.lienzo.client.widget.DragContext;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

abstract class AbstractNodeDragEvent<H extends EventHandler> extends GwtEvent<H> implements INodeXYEvent
{
    private final DragContext m_dragContext;

    public static class Type<H> extends GwtEvent.Type<H>
    {

    }

    public AbstractNodeDragEvent(DragContext dragContext)
    {
        m_dragContext = dragContext;
    }

    @Override
    public int getX()
    {
        return m_dragContext.getEventX();
    }

    public DragContext getDragContext()
    {
        return m_dragContext;
    }

    @Override
    public int getY()
    {
        return m_dragContext.getEventY();
    }

    @Override
    public GwtEvent<?> getNodeEvent()
    {
        return this;
    }
}
