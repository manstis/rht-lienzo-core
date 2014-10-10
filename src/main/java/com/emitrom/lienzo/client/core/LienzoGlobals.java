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

package com.emitrom.lienzo.client.core;

import com.emitrom.lienzo.client.core.types.DashArray;
import com.emitrom.lienzo.client.core.types.ImageData;
import com.emitrom.lienzo.client.core.util.ScratchCanvas;
import com.emitrom.lienzo.shared.core.types.ColorName;
import com.emitrom.lienzo.shared.core.types.IColor;
import com.emitrom.lienzo.shared.core.types.LayerClearMode;
import com.emitrom.lienzo.shared.core.types.LineCap;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.shared.GWT;

/**
 * A Global Configuration Manager.
 */
public final class LienzoGlobals
{
    public static final double         DEFAULT_FONT_SIZE        = 48;

    public static final String         DEFAULT_FONT_STYLE       = "normal";

    public static final String         DEFAULT_FONT_FAMILY      = "Helvetica";

    private static final LienzoGlobals s_instance               = new LienzoGlobals();

    private double                     m_strokeWidth            = 1;

    private double                     m_backingStorePixelRatio = 0;

    private String                     m_strokeColor            = "black";

    private boolean                    m_fillShapeForSelection  = true;

    private boolean                    m_globalLineDashSupport  = true;

    private boolean                    m_nativeLineDashSupport  = false;

    private boolean                    m_nativeLineDashExamine  = false;

    private final boolean              m_canvasSupported        = Canvas.isSupported();

    private LayerClearMode             m_layerClearMode         = LayerClearMode.CLEAR;

    private LienzoGlobals()
    {
    }

    public static final LienzoGlobals getInstance()
    {
        return s_instance;
    }

    public final void setDefaultFillShapeForSelection(boolean fill)
    {
        m_fillShapeForSelection = fill;
    }

    public final boolean getDefaultFillShapeForSelection()
    {
        return m_fillShapeForSelection;
    }

    public final void setDefaultStrokeWidth(double width)
    {
        if (width > 0)
        {
            m_strokeWidth = width;
        }
        else
        {
            m_strokeWidth = 1;
        }
    }

    public final double getDefaultStrokeWidth()
    {
        return m_strokeWidth;
    }

    public final void setDefaultStrokeColor(IColor color)
    {
        if (color != null)
        {
            m_strokeColor = color.getColorString();
        }
        else
        {
            m_strokeColor = "black";
        }
    }

    public final String getDefaultStrokeColor()
    {
        return m_strokeColor;
    }

    /**
     * Returns true if the Canvas element is supported.
     * @return
     */
    public final boolean isCanvasSupported()
    {
        return m_canvasSupported;
    }

    public final boolean isLineDashSupported()
    {
        return ((isGlobalLineDashSupported()) && (isNativeLineDashSupported()));
    }

    public final boolean isGlobalLineDashSupported()
    {
        return m_globalLineDashSupport;
    }

    public final void setGlobalLineDashSupported(boolean supported)
    {
        m_globalLineDashSupport = supported;
    }

    public final boolean isNativeLineDashSupported()
    {
        if (false == m_nativeLineDashExamine)
        {
            m_nativeLineDashSupport = examineNativeLineDashSupported();

            m_nativeLineDashExamine = true;
        }
        return m_nativeLineDashSupport;
    }

    public final double getDefaultFontSize()
    {
        return DEFAULT_FONT_SIZE;
    }

    public final String getDefaultFontStyle()
    {
        return DEFAULT_FONT_STYLE;
    }

    public final String getDefaultFontFamily()
    {
        return DEFAULT_FONT_FAMILY;
    }

    public final LayerClearMode getLayerClearMode()
    {
        return m_layerClearMode;
    }

    public final native double getDevicePixelRatio()
    /*-{
		return $wnd.devicePixelRatio || 1;
    }-*/;

    public final void setLayerClearMode(LayerClearMode mode)
    {
        if (null != mode)
        {
            m_layerClearMode = mode;
        }
    }

    public final double getBackingStorePixelRatio()
    {
        if (m_backingStorePixelRatio != 0)
        {
            return m_backingStorePixelRatio;
        }
        if (isCanvasSupported())
        {
            try
            {
                ScratchCanvas scratch = new ScratchCanvas(1, 1);

                m_backingStorePixelRatio = scratch.getContext().getBackingStorePixelRatio();
            }
            catch (Exception e)
            {
                m_backingStorePixelRatio = 1;

                GWT.log("Backing Store Pixel Ratio failed ", e);
            }
        }
        else
        {
            m_backingStorePixelRatio = 1;
        }
        return m_backingStorePixelRatio;
    }

    private final boolean examineNativeLineDashSupported()
    {
        if (isCanvasSupported())
        {
            try
            {
                ScratchCanvas scratch = new ScratchCanvas(20, 10);

                Context2D context = scratch.getContext();

                context.setStrokeWidth(10);

                context.setLineCap(LineCap.BUTT);

                context.setStrokeColor(ColorName.BLUE);

                context.beginPath();

                context.moveTo(0, 5);

                context.lineTo(20, 5);

                context.stroke();

                context.setStrokeColor(ColorName.RED);

                context.setLineDash(new DashArray(5, 5));

                context.beginPath();

                context.moveTo(0, 5);

                context.lineTo(20, 5);

                context.stroke();

                ImageData backing = context.getImageData(0, 0, 20, 10);

                if (null != backing)
                {
                    if ((backing.getRedAt(3, 5) == 255) && (backing.getBlueAt(3, 5) == 0) && (backing.getGreenAt(3, 5) == 0))
                    {
                        if ((backing.getRedAt(8, 5) == 0) && (backing.getBlueAt(8, 5) == 255) && (backing.getGreenAt(8, 5) == 0))
                        {
                            return true;
                        }
                    }
                }
            }
            catch (Exception e)
            {
                GWT.log("Line Dash test failed ", e); // FF 22 dev mode does not like line dashes
            }
        }
        return false;
    }
}
