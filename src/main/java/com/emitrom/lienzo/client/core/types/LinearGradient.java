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

package com.emitrom.lienzo.client.core.types;

import com.emitrom.lienzo.client.core.Context2D.GradientJSO;
import com.emitrom.lienzo.client.core.shape.Shape;
import com.emitrom.lienzo.shared.core.types.IColor;

/**
 * LinearGradient defines the fill style for a {@link Shape} as a Linear Gradient. 
 */
public final class LinearGradient implements FillGradient
{
    public static final String      TYPE = "LinearGradient";

    private final LinearGradientJSO m_jso;

    public LinearGradient(LinearGradientJSO jso)
    {
        m_jso = jso;
    }

    public LinearGradient(double sx, double sy, double ex, double ey)
    {
        this(LinearGradientJSO.make(sx, sy, ex, ey));
    }

    @Override
    public String getType()
    {
        return TYPE;
    }

    public final LinearGradient addColorStop(double stop, String color)
    {
        m_jso.addColorStop(stop, color);

        return this;
    }

    public final LinearGradient addColorStop(double stop, IColor color)
    {
        m_jso.addColorStop(stop, color.getColorString());

        return this;
    }

    public final LinearGradientJSO getJSO()
    {
        return m_jso;
    }

    public static final class LinearGradientJSO extends GradientJSO
    {
        protected LinearGradientJSO()
        {

        }

        public static final native LinearGradientJSO make(double sx, double sy, double ex, double ey)
        /*-{
			return {
				start : {
					x : sx,
					y : sy,
				},
				end : {
					x : ex,
					y : ey,
				},
				colorStops : [],
				type : "LinearGradient"
			};
        }-*/;

        public final native void addColorStop(double stop, String color)
        /*-{
			this.colorStops.push({
				stop : stop,
				color : color
			});
        }-*/;
    }
}
