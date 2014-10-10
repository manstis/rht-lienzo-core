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

import com.google.gwt.event.shared.GwtEvent;

public class OrientationChangeEvent extends GwtEvent<OrientationChangeHandler>
{
    private final int                                  m_width;

    private final int                                  m_height;

    public static final Type<OrientationChangeHandler> TYPE = new Type<OrientationChangeHandler>();

    public enum Orientation
    {
        PORTRAIT, LANDSCAPE
    }

    public OrientationChangeEvent(int width, int height)
    {
        m_width = width;

        m_height = height;
    }

    @Override
    public Type<OrientationChangeHandler> getAssociatedType()
    {
        return TYPE;
    }

    public int getWidth()
    {
        return m_width;
    }

    public int getHeight()
    {
        return m_height;
    }

    @Override
    protected void dispatch(OrientationChangeHandler handler)
    {
        Orientation orientationChange = Orientation.PORTRAIT;

        if (m_width > m_height)
        {
            orientationChange = Orientation.LANDSCAPE;
        }

        handler.onOrientationChange(orientationChange, m_width, m_height);
    }
}