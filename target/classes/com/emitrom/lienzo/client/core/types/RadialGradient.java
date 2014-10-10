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
import com.emitrom.lienzo.shared.core.types.Color;
import com.emitrom.lienzo.shared.core.types.ColorName;
import com.emitrom.lienzo.shared.core.types.IColor;

/**
 * RadialGradient defines the fill style for a {@link Shape} as a Radial Gradient. 
 */
public final class RadialGradient implements FillGradient
{
    public static final String      TYPE = "RadialGradient";

    private final RadialGradientJSO m_jso;

    public RadialGradient(RadialGradientJSO jso)
    {
        m_jso = jso;
    }

    public RadialGradient(double sx, double sy, double sr, double ex, double ey, double er)
    {
        this(RadialGradientJSO.make(sx, sy, sr, ex, ey, er));
    }

    @Override
    public String getType()
    {
        return TYPE;
    }

    /**
     * Add color stop
     * 
     * @param stop
     * @param color
     * @return {@link RadialGradient}
     */
    public final RadialGradient addColorStop(double stop, String color)
    {
        m_jso.addColorStop(stop, color);

        return this;
    };

    /**
     * Add color stop
     * 
     * @param stop
     * @param color {@link ColorName} or {@link Color}
     * @return {@link RadialGradient}
     */
    public final RadialGradient addColorStop(double stop, IColor color)
    {
        m_jso.addColorStop(stop, color.getColorString());

        return this;
    };

    public final RadialGradientJSO getJSO()
    {
        return m_jso;
    }

    public static final class RadialGradientJSO extends GradientJSO
    {
        protected RadialGradientJSO()
        {

        }

        public static final native RadialGradientJSO make(double sx, double sy, double sr, double ex, double ey, double er)
        /*-{
			return {
				start : {
					x : sx,
					y : sy,
					radius : sr
				},
				end : {
					x : ex,
					y : ey,
					radius : er
				},
				colorStops : [],
				type : "RadialGradient"
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
