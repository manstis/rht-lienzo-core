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
public class RGBImageDataFilter extends AbstractBaseRGBImageDataFilter<RGBImageDataFilter>
{
    public RGBImageDataFilter()
    {
    }

    public RGBImageDataFilter(int r, int g, int b)
    {
        super(r, g, b);
    }

    public RGBImageDataFilter(IColor color)
    {
        super(color);
    }
    
    public RGBImageDataFilter(String color)
    {
        super(color);
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
        if (copy)
        {
            source = source.copy();
        }
        if (false == isActive())
        {
            return source;
        }
        final int length = getLength(source);
        
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
