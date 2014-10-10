
package com.emitrom.lienzo.client.core.util;

import com.emitrom.lienzo.client.core.Context2D;
import com.emitrom.lienzo.client.core.types.ImageData;
import com.emitrom.lienzo.shared.core.types.Color;

public final class ColorExtractor
{
    private static final ScratchCanvas s_canvas = new ScratchCanvas(2, 2);

    public static final Color extract(String color)
    {
        s_canvas.clear();

        Context2D context = s_canvas.getContext();

        context.setFillColor(color);

        context.fillRect(0, 0, 2, 2);

        ImageData data = context.getImageData(0, 0, 2, 2);

        return new Color(data.getRedAt(1, 1), data.getGreenAt(1, 1), data.getBlueAt(1, 1), (((double) data.getAlphaAt(1, 1)) / 255.0));
    }
}
