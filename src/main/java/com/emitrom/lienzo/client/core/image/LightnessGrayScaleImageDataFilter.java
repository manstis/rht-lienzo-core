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
import com.google.gwt.canvas.dom.client.CanvasPixelArray;

/**
 * A class that allows for easy creation of a Light Gray Scale Image Filter.
 */
public class LightnessGrayScaleImageDataFilter implements ImageDataFilter
{
    public static final LightnessGrayScaleImageDataFilter INSTANCE = new LightnessGrayScaleImageDataFilter();

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
        for (int i = 0; i < length; i += PIXEL_SZ)
        {
            int r = data.get(i + R_OFFSET);

            int g = data.get(i + G_OFFSET);

            int b = data.get(i + B_OFFSET);

            int v = (int) ((((Math.max(Math.max(r, g), b) + Math.min(Math.min(r, g), b))) / 2.0) + 0.5);

            data.set(i + R_OFFSET, v);

            data.set(i + G_OFFSET, v);

            data.set(i + B_OFFSET, v);
        }
        return source;
    }
}
