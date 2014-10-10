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

import com.emitrom.lienzo.client.core.Context2D;
import com.emitrom.lienzo.client.core.shape.Picture;
import com.emitrom.lienzo.client.core.types.ImageData;
import com.emitrom.lienzo.client.core.types.ImageLoader;
import com.emitrom.lienzo.client.core.types.ImageLoader.ImageJSO;
import com.emitrom.lienzo.client.core.util.Console;
import com.emitrom.lienzo.client.core.util.ScratchCanvas;
import com.emitrom.lienzo.shared.core.types.Color;
import com.emitrom.lienzo.shared.core.types.DataURLType;

/**
 * ImageProxy is used by {@link Picture} to load and draw the image.
 */
public class ImageProxy
{
    private Picture              m_picture;

    private ImageJSO             m_imageJSO;

    private ImageJSO             m_selectionImageJSO;

    private int                  m_x;

    private int                  m_y;

    private int                  m_width;

    private int                  m_height;

    private int                  m_destinationWidth;

    private int                  m_destinationHeight;

    private boolean              m_loaded = false;

    private String               m_category;

    private PictureLoadedHandler m_handler;

    /**
     * Creates an ImageProxy for the specified {@link Picture}.
     * If <code>load</code> is true, it will immediately call {@link #load()}
     * to load the image.
     * 
     * @param picture {@link Picture}
     * @param load If true, the image will be loaded immediately by 
     *              calling {@link #load()}.
     */
    public ImageProxy(Picture picture, boolean load)
    {
        m_picture = picture;

        if (load)
        {
            load();
        }
    }

    /**
     * Sets the {@link PictureLoadedHandler} that will be notified when the image is loaded.
     * If the image is already loaded, the handler will be invoked immediately.
     * 
     * @param handler {@link PictureLoadedHandler}
     */
    public void setPictureLoadedHandler(PictureLoadedHandler handler)
    {
        m_handler = handler;

        if (m_loaded)
        {
            m_handler.onPictureLoaded(m_picture);
        }
    }

    /**
     * Loads the image.
     */
    public void load()
    {
        final String url = m_picture.getURL();

        final long start = System.currentTimeMillis();

        m_category = m_picture.getPictureCategory();

        Console.log("registering " + url + " loaded=" + m_loaded + " time=" + (System.currentTimeMillis() - start));

        PictureLoader.getInstance().registerProxy(m_category, this);

        m_x = (int) Math.round(m_picture.getClippedImageStartX());

        m_y = (int) Math.round(m_picture.getClippedImageStartY());

        // zero means: use the actual image width/height

        m_width = (int) Math.round(m_picture.getClippedImageWidth());

        m_height = (int) Math.round(m_picture.getClippedImageHeight());

        // zero means: use the source width/height

        m_destinationWidth = (int) Math.round(m_picture.getClippedImageDestinationWidth());

        m_destinationHeight = (int) Math.round(m_picture.getClippedImageDestinationHeight());

        new ImageLoader(url)
        {
            @Override
            public void onLoaded(ImageLoader image)
            {
                Console.log("loaded " + url + " time=" + (System.currentTimeMillis() - start));

                m_imageJSO = image.getJSO();

                if (m_width == 0)
                {
                    m_width = image.getWidth();
                }
                if (m_height == 0)
                {
                    m_height = image.getHeight();
                }
                if (m_destinationWidth == 0)
                {
                    m_destinationWidth = m_width;
                }
                if (m_destinationHeight == 0)
                {
                    m_destinationHeight = m_height;
                }
                if (false == m_picture.isListening())
                {
                    doneLoading();

                    return;
                }
                // Prepare the Image for the Selection Layer.
                // Get ImageData of the image by drawing it in a temporary canvas...

                ScratchCanvas scratch = new ScratchCanvas(m_destinationWidth, m_destinationHeight);

                Context2D context = scratch.getContext();

                context.drawImage(m_imageJSO, m_x, m_y, m_width, m_height, 0, 0, m_destinationWidth, m_destinationHeight);

                ImageData imageData = context.getImageData(0, 0, m_destinationWidth, m_destinationHeight);

                // Now draw the image again, replacing each color with the color key

                scratch.clear();

                Color rgb = Color.fromColorString(m_picture.getColorKey());

                context.putImageData(new RGBIgnoreAlphaImageDataFilter(rgb.getR(), rgb.getG(), rgb.getB()).filter(imageData, true), 0, 0);

                // Load the resulting image from the temporary canvas into the selection Image

                String dataURL = scratch.toDataURL();

                new ImageLoader(dataURL)
                {
                    @Override
                    public void onLoaded(ImageLoader image)
                    {
                        m_selectionImageJSO = image.getJSO();

                        Console.log("loaded selection image " + url + " time=" + (System.currentTimeMillis() - start));

                        doneLoading();
                    }
                };
            }
        };
    }

    /**
     * Draws the image in the {@link Context2D}.
     * 
     * @param context {@link Context2D}
     */
    public void drawImage(Context2D context)
    {
        if (m_imageJSO != null)
        {
            context.drawImage(m_imageJSO, m_x, m_y, m_width, m_height, 0, 0, m_destinationWidth, m_destinationHeight);
        }
    }

    /**
     * Draws the selection layer image in the {@link Context2D}.
     * 
     * @param context {@link Context2D}
     */
    public void drawSelectionImage(Context2D context)
    {
        if (m_selectionImageJSO != null)
        {
            context.drawImage(m_selectionImageJSO, 0, 0);
        }
    }

    /**
     * Returns the (main) image (JavaSciptObject).
     * 
     * @return {@link ImageJSO}
     */
    public ImageJSO getImageJSO()
    {
        return m_imageJSO;
    }

    /**
     * Returns the image (JavaSciptObject) for the selection layer.
     * 
     * @return {@link ImageJSO}
     */
    public ImageJSO getSelectionImageJSO()
    {
        return m_selectionImageJSO;
    }

    /**
     * Returns whether the image has been loaded and whether the
     * selection layer image has been prepared (if needed.)
     * 
     * @return
     */
    public boolean isLoaded()
    {
        return m_loaded;
    }

    /**
     * Returns an ImageData object that can be used for further image processing
     * e.g. by image filters.
     * 
     * @return ImageData
     */
    public ImageData getImageData()
    {
        if (false == m_loaded)
        {
            return null;
        }
        ScratchCanvas scratch = new ScratchCanvas(m_destinationWidth, m_destinationHeight);

        Context2D context = scratch.getContext();

        context.drawImage(m_imageJSO, m_x, m_y, m_width, m_height, 0, 0, m_destinationWidth, m_destinationHeight);

        ImageData imageData = context.getImageData(0, 0, m_destinationWidth, m_destinationHeight);

        return imageData;
    }

    /**
     * Returns the "data:" URL
     * 
     * @param mimeType If null, defaults to DataURLType.PNG
     * @return String
     */
    public String toDataURL(DataURLType mimeType)
    {
        if (false == m_loaded)
        {
            return null;
        }
        ScratchCanvas scratch = new ScratchCanvas(m_destinationWidth, m_destinationHeight);

        Context2D context = scratch.getContext();

        context.drawImage(m_imageJSO, m_x, m_y, m_width, m_height, 0, 0, m_destinationWidth, m_destinationHeight);

        if (mimeType == null)
        {
            return scratch.toDataURL();
        }
        else return scratch.toDataURL(mimeType);
    }

    protected void doneLoading()
    {
        m_loaded = true;

        if (m_handler != null)
        {
            m_handler.onPictureLoaded(m_picture);
        }
        PictureLoader.getInstance().doneLoading(m_category, ImageProxy.this);
    }
}
