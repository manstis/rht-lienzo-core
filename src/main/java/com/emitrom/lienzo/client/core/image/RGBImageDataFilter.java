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

package com.emitrom.lienzo.client.core.image;

import com.emitrom.lienzo.client.core.types.ImageData;
import com.emitrom.lienzo.shared.core.types.IColor;
import com.google.gwt.canvas.dom.client.CanvasPixelArray;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * An Image filter to convert all pixels in the CanvasPixelArray to an RGB color.
 * 
 * <ul>
 * 	<li>
 * 		Alpha is maintained in this filter.
 *  </li>
 * </ui>
 */
public class RGBImageDataFilter implements ImageDataFilter
{
    private int     m_r;

    private int     m_g;

    private int     m_b;

    private boolean m_isnative = true;

    public RGBImageDataFilter()
    {

    }

    public RGBImageDataFilter(int r, int g, int b)
    {
        m_r = r;

        m_g = g;

        m_b = b;
    }

    public RGBImageDataFilter(IColor col)
    {
        m_r = col.getR();

        m_g = col.getG();

        m_b = col.getB();
    }

    /**
     * @return Red (R) component of the RGB color
     */
    public int getR()
    {
        return m_r;
    }

    /**
     * @param r Red (R) component of the RGB color
     */
    public RGBImageDataFilter setR(int r)
    {
        m_r = r;

        return this;
    }

    /**
     * @return Green (G) component of the RGB color
     */
    public int getG()
    {
        return m_g;
    }

    /**
     * @param g Green (G) component of the RGB color
     */
    public RGBImageDataFilter setG(int g)
    {
        m_g = g;

        return this;
    }

    /**
     * @return Blue (B) component of the RGB color
     */
    public int getB()
    {
        return m_b;
    }

    /**
     * @param Blue (B) component of the RGB color
     */
    public RGBImageDataFilter setB(int b)
    {
        m_b = b;

        return this;
    }

    public boolean isNative()
    {
        return m_isnative;
    }

    public RGBImageDataFilter setNative(boolean isnative)
    {
        m_isnative = isnative;

        return this;
    }

    /**
     * Return an {@link ImageData} that is transformed based on the passed in RGB color.
     */
    @Override
    public ImageData filter(ImageData source, boolean copy)
    {
        if (null == source)
        {
            return null;
        }
        final int length = ((source.getWidth() * source.getHeight()) * PIXEL_SZ);

        if (copy)
        {
            source = source.copy();
        }
        final CanvasPixelArray data = source.getData();

        if (null == data)
        {
            return source;
        }
        if (isNative())
        {
            filter0(data, length, getR(), getG(), getB());
        }
        else
        {
            for (int i = 0; i < length; i += PIXEL_SZ)
            {
                data.set(i + R_OFFSET, getR());

                data.set(i + G_OFFSET, getG());

                data.set(i + B_OFFSET, getB());
            }
        }
        return source;
    }

    private final native void filter0(JavaScriptObject pixa, int length, int r, int g, int b)
    /*-{
		var data = pixa;

		for (var i = 0; i < length; i += 4) {

			data[i + 0] = r;

			data[i + 1] = g;

			data[i + 2] = b;
		}
    }-*/;
}
