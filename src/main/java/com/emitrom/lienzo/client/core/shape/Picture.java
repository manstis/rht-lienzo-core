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

package com.emitrom.lienzo.client.core.shape;

import com.emitrom.lienzo.client.core.Attribute;
import com.emitrom.lienzo.client.core.Context2D;
import com.emitrom.lienzo.client.core.animation.LayerRedrawManager;
import com.emitrom.lienzo.client.core.image.ImageProxy;
import com.emitrom.lienzo.client.core.image.PictureLoadedHandler;
import com.emitrom.lienzo.client.core.image.PictureLoader;
import com.emitrom.lienzo.client.core.shape.json.IFactory;
import com.emitrom.lienzo.client.core.shape.json.PostProcessNodeFactory;
import com.emitrom.lienzo.client.core.shape.json.ResourceResolver;
import com.emitrom.lienzo.client.core.shape.json.ShapeFactory;
import com.emitrom.lienzo.client.core.shape.json.validators.ValidationContext;
import com.emitrom.lienzo.client.core.types.ImageData;
import com.emitrom.lienzo.shared.core.types.DataURLType;
import com.emitrom.lienzo.shared.core.types.PictureSerializationMode;
import com.emitrom.lienzo.shared.core.types.ShapeType;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.resources.client.ImageResource;

/**
 * Image Support for Canvas
 * <ul>
 *  <li>Supports {@link ImageResource}</li>
 *  <li>Supports Image based on URL</li>
 * </ul>
 * 
 * If the <code>listening</code> attribute is set to false, it will not be drawn in the Selection Layer,
 * which means it can not be dragged or picked. This also means, it will not respond
 * to events.
 * <p>
 * The upside is that it will not need to generate a separate Image for the Selection Layer,
 * which saves memory and time, both for generating the selection layer Image and when drawing the Picture.
 */
public class Picture extends Shape<Picture>
{
    private final ImageProxy m_proxy;

    protected Picture(JSONObject node, ResourceResolver resolver)
    {
        super(ShapeType.PICTURE, node);

        resolver.resolve(this);

        m_proxy = new ImageProxy(this, true);
    }

    /**
     * Creates a Picture from a URL.
     * The following attributes are defaulted:
     * <ul>
     * <li>clippedImageStartX - 0
     * <li>clippedImageStartY - 0
     * <li>clippedImageWidth - 0 (means: use image width)
     * <li>clippedImageHeight - 0 (means: use image height)
     * <li>clippedImageDestinationWidth - 0 (means: use clippedImageWidth)
     * <li>clippedImageDestinationHeight - 0 (means: use clippedImageHeight)
     * <li>category
     * </ul>
     * 
     * @param url
     * @param listening When set to false, the Picture can't be dragged or picked,
     *      but it will be drawn faster and use less memory.
     */
    public Picture(String url, boolean listening)
    {
        this(url, true, listening, null);
    }

    /**
     * Creates a Picture from a URL.
     * The following attributes are defaulted:
     * <ul>
     * <li>clippedImageStartX - 0
     * <li>clippedImageStartY - 0
     * <li>clippedImageWidth - 0 (means: use image width)
     * <li>clippedImageHeight - 0 (means: use image height)
     * <li>clippedImageDestinationWidth - 0 (means: use clippedImageWidth)
     * <li>clippedImageDestinationHeight - 0 (means: use clippedImageHeight)
     * </ul>
     * 
     * @param url
     * @param listening When set to false, the Picture can't be dragged or picked,
     *      but it will be drawn faster and use less memory.
     * @param pictureCategory Used by {@link PictureLoader} when (all the images of) all
     *      the Pictures in a category are loaded.  Null means: use the default category.
     */
    public Picture(String url, boolean listening, String pictureCategory)
    {
        this(url, true, listening, pictureCategory);
    }

    /**
     * Creates a Picture from a URL.
     * The following attributes are defaulted:
     * <ul>
     * <li>clippedImageDestinationWidth - 0 (means: use clippedImageWidth)
     * <li>clippedImageDestinationHeight - 0 (means: use clippedImageHeight)
     * <li>category
     * </ul>
     * 
     * @param url
     * @param sx clippedImageStartX
     * @param sy clippedImageStartY
     * @param sw clippedImageWidth
     * @param sh clippedImageHeight
     * @param listening When set to false, the Picture can't be dragged or picked,
     *      but it will be drawn faster and use less memory.
     */
    public Picture(String url, int sx, int sy, int sw, int sh, boolean listening)
    {
        this(url, sx, sy, sw, sh, listening, null);
    }

    /**
     * Creates a Picture from a URL.
     * The following attributes are defaulted:
     * <ul>
     * <li>clippedImageDestinationWidth - 0 (means: use clippedImageWidth)
     * <li>clippedImageDestinationHeight - 0 (means: use clippedImageHeight)
     * </ul>
     * 
     * @param url
     * @param sx clippedImageStartX
     * @param sy clippedImageStartY
     * @param sw clippedImageWidth
     * @param sh clippedImageHeight
     * @param listening When set to false, the Picture can't be dragged or picked,
     *      but it will be drawn faster and use less memory.
     * @param pictureCategory Used by {@link PictureLoader} when (all the images of) all
     *      the Pictures in a category are loaded.  Null means: use the default category.
     */
    public Picture(String url, int sx, int sy, int sw, int sh, boolean listening, String pictureCategory)
    {
        this(url, false, listening, pictureCategory);

        setClippedImageStartX(sx);
        setClippedImageStartY(sy);
        setClippedImageWidth(sw);
        setClippedImageHeight(sh);

        m_proxy.load();
    }

    /**
     * Creates a Picture from a URL using the default category.
     * 
     * @param url
     * @param sx clippedImageStartX
     * @param sy clippedImageStartY
     * @param sw clippedImageWidth (0 means: use image width)
     * @param sh clippedImageHeight (0 means: use image height)
     * @param dw clippedImageDestinationWidth (0 means: use clippedImageWidth)
     * @param dh clippedImageDestinationHeight (0 means: use clippedImageHeight)
     * @param listening When set to false, the Picture can't be dragged or picked,
     *      but it will be drawn faster and use less memory.
     */
    public Picture(String url, int sx, int sy, int sw, int sh, int dw, int dh, boolean listening)
    {
        this(url, sx, sy, sw, sh, dw, dh, listening, null);
    }

    /**
     * Creates a Picture from a URL.
     * 
     * @param url
     * @param sx clippedImageStartX
     * @param sy clippedImageStartY
     * @param sw clippedImageWidth (0 means: use image width)
     * @param sh clippedImageHeight (0 means: use image height)
     * @param dw clippedImageDestinationWidth (0 means: use clippedImageWidth)
     * @param dh clippedImageDestinationHeight (0 means: use clippedImageHeight)
     * @param listening When set to false, the Picture can't be dragged or picked,
     *      but it will be drawn faster and use less memory.
     * @param pictureCategory Used by {@link PictureLoader} when (all the images of) all
     *      the Pictures in a category are loaded.  Null means: use the default category.
     */
    public Picture(String url, int sx, int sy, int sw, int sh, int dw, int dh, boolean listening, String pictureCategory)
    {
        this(url, false, listening, pictureCategory);

        setClippedImageStartX(sx);
        setClippedImageStartY(sy);
        setClippedImageWidth(sw);
        setClippedImageHeight(sh);
        setClippedImageDestinationWidth(dw);
        setClippedImageDestinationHeight(dh);

        m_proxy.load();
    }

    /**
     * Creates a Picture from a URL.
     * The following attributes are defaulted:
     * <ul>
     * <li>clippedImageStartX - 0
     * <li>clippedImageStartY - 0
     * <li>clippedImageWidth - 0 (means: use image width)
     * <li>clippedImageHeight - 0 (means: use image height)
     * <li>category
     * </ul>
     * 
     * @param url
     * @param dw clippedImageDestinationWidth (0 means: use clippedImageWidth)
     * @param dh clippedImageDestinationHeight (0 means: use clippedImageHeight)
     * @param listening When set to false, the Picture can't be dragged or picked,
     *      but it will be drawn faster and use less memory.
     */
    public Picture(String url, int dw, int dh, boolean listening)
    {
        this(url, dw, dh, listening, null);
    }

    /**
     * Creates a Picture from a URL.
     * The following attributes are defaulted:
     * <ul>
     * <li>clippedImageStartX - 0
     * <li>clippedImageStartY - 0
     * <li>clippedImageWidth - 0 (means: use image width)
     * <li>clippedImageHeight - 0 (means: use image height)
     * </ul>
     * 
     * @param url
     * @param dw clippedImageDestinationWidth (0 means: use clippedImageWidth)
     * @param dh clippedImageDestinationHeight (0 means: use clippedImageHeight)
     * @param listening When set to false, the Picture can't be dragged or picked,
     *      but it will be drawn faster and use less memory.
     * @param pictureCategory Used by {@link PictureLoader} when (all the images of) all
     *      the Pictures in a category are loaded.  Null means: use the default category.
     */
    public Picture(String url, int dw, int dh, boolean listening, String pictureCategory)
    {
        this(url, false, listening, pictureCategory);

        setClippedImageDestinationWidth(dw);
        setClippedImageDestinationHeight(dh);

        m_proxy.load();
    }

    /**
     * Creates a Picture from an ImageResource.
     * The following attributes are defaulted:
     * <ul>
     * <li>clippedImageStartX - 0
     * <li>clippedImageStartY - 0
     * <li>clippedImageWidth - 0 (means: use image width)
     * <li>clippedImageHeight - 0 (means: use image height)
     * <li>clippedImageDestinationWidth - 0 (means: use clippedImageWidth)
     * <li>clippedImageDestinationHeight - 0 (means: use clippedImageHeight)
     * <li>category
     * </ul>
     * 
     * @param resource ImageResource
     * @param listening When set to false, the Picture can't be dragged or picked,
     *      but it will be drawn faster and use less memory.
     */
    public Picture(ImageResource resource, boolean listening)
    {
        this(resource, true, listening, null);
    }

    /**
     * Creates a Picture from an ImageResource.
     * The following attributes are defaulted:
     * <ul>
     * <li>clippedImageStartX - 0
     * <li>clippedImageStartY - 0
     * <li>clippedImageWidth - 0 (means: use image width)
     * <li>clippedImageHeight - 0 (means: use image height)
     * <li>clippedImageDestinationWidth - 0 (means: use clippedImageWidth)
     * <li>clippedImageDestinationHeight - 0 (means: use clippedImageHeight)
     * </ul>
     * 
     * @param resource ImageResource
     * @param listening When set to false, the Picture can't be dragged or picked,
     *      but it will be drawn faster and use less memory.
     * @param pictureCategory Used by {@link PictureLoader} when (all the images of) all
     *      the Pictures in a category are loaded.  Null means: use the default category.
     */
    public Picture(ImageResource resource, boolean listening, String pictureCategory)
    {
        this(resource, true, listening, pictureCategory);
    }

    /**
     * Creates a Picture from an ImageResource.
     * The following attributes are defaulted:
     * <ul>
     * <li>clippedImageDestinationWidth - 0 (means: use clippedImageWidth)
     * <li>clippedImageDestinationHeight - 0 (means: use clippedImageHeight)
     * <li>category
     * </ul>
     * 
     * @param resource ImageResource
     * @param sx clippedImageStartX
     * @param sy clippedImageStartY
     * @param sw clippedImageWidth
     * @param sh clippedImageHeight
     * @param listening When set to false, the Picture can't be dragged or picked,
     *      but it will be drawn faster and use less memory.
     */
    public Picture(ImageResource resource, int sx, int sy, int sw, int sh, boolean listening)
    {
        this(resource, sx, sy, sw, sh, listening, null);
    }

    /**
     * Creates a Picture from an ImageResource.
     * The following attributes are defaulted:
     * <ul>
     * <li>clippedImageDestinationWidth - 0 (means: use clippedImageWidth)
     * <li>clippedImageDestinationHeight - 0 (means: use clippedImageHeight)
     * </ul>
     * 
     * @param resource ImageResource
     * @param sx clippedImageStartX
     * @param sy clippedImageStartY
     * @param sw clippedImageWidth
     * @param sh clippedImageHeight
     * @param listening When set to false, the Picture can't be dragged or picked,
     *      but it will be drawn faster and use less memory.
     * @param pictureCategory Used by {@link PictureLoader} when (all the images of) all
     *      the Pictures in a category are loaded.  Null means: use the default category.
     */
    public Picture(ImageResource resource, int sx, int sy, int sw, int sh, boolean listening, String pictureCategory)
    {
        this(resource, false, listening, pictureCategory);

        setClippedImageStartX(sx);
        setClippedImageStartY(sy);
        setClippedImageWidth(sw);
        setClippedImageHeight(sh);

        m_proxy.load();
    }

    /**
     * Creates a Picture from an ImageResource using the default category.
     * 
     * @param resource ImageResource
     * @param sx clippedImageStartX
     * @param sy clippedImageStartY
     * @param sw clippedImageWidth (0 means: use image width)
     * @param sh clippedImageHeight (0 means: use image height)
     * @param dw clippedImageDestinationWidth (0 means: use clippedImageWidth)
     * @param dh clippedImageDestinationHeight (0 means: use clippedImageHeight)
     * @param listening When set to false, the Picture can't be dragged or picked,
     *      but it will be drawn faster and use less memory.
     */
    public Picture(ImageResource resource, int sx, int sy, int sw, int sh, int dw, int dh, boolean listening)
    {
        this(resource, sx, sy, sw, sh, dw, dh, listening, null);
    }

    /**
     * Creates a Picture from an ImageResource.
     * 
     * @param resource ImageResource
     * @param sx clippedImageStartX
     * @param sy clippedImageStartY
     * @param sw clippedImageWidth (0 means: use image width)
     * @param sh clippedImageHeight (0 means: use image height)
     * @param dw clippedImageDestinationWidth (0 means: use clippedImageWidth)
     * @param dh clippedImageDestinationHeight (0 means: use clippedImageHeight)
     * @param listening When set to false, the Picture can't be dragged or picked,
     *      but it will be drawn faster and use less memory.
     * @param pictureCategory Used by {@link PictureLoader} when (all the images of) all
     *      the Pictures in a category are loaded.  Null means: use the default category.
     */
    public Picture(ImageResource resource, int sx, int sy, int sw, int sh, int dw, int dh, boolean listening, String pictureCategory)
    {
        this(resource, false, listening, pictureCategory);

        setClippedImageStartX(sx);
        setClippedImageStartY(sy);
        setClippedImageWidth(sw);
        setClippedImageHeight(sh);
        setClippedImageDestinationWidth(dw);
        setClippedImageDestinationHeight(dh);

        m_proxy.load();
    }

    /**
     * Creates a Picture from an ImageResource.
     * The following attributes are defaulted:
     * <ul>
     * <li>clippedImageStartX - 0
     * <li>clippedImageStartY - 0
     * <li>clippedImageWidth - 0 (means: use image width)
     * <li>clippedImageHeight - 0 (means: use image height)
     * <li>category
     * </ul>
     * 
     * @param resource ImageResource
     * @param dw clippedImageDestinationWidth (0 means: use clippedImageWidth)
     * @param dh clippedImageDestinationHeight (0 means: use clippedImageHeight)
     * @param listening When set to false, the Picture can't be dragged or picked,
     *      but it will be drawn faster and use less memory.
     */
    public Picture(ImageResource resource, int dw, int dh, boolean listening)
    {
        this(resource, dw, dh, listening, null);
    }

    /**
     * Creates a Picture from an ImageResource.
     * The following attributes are defaulted:
     * <ul>
     * <li>clippedImageStartX - 0
     * <li>clippedImageStartY - 0
     * <li>clippedImageWidth - 0 (means: use image width)
     * <li>clippedImageHeight - 0 (means: use image height)
     * </ul>
     * 
     * @param resource ImageResource
     * @param dw clippedImageDestinationWidth (0 means: use clippedImageWidth)
     * @param dh clippedImageDestinationHeight (0 means: use clippedImageHeight)
     * @param listening When set to false, the Picture can't be dragged or picked,
     *      but it will be drawn faster and use less memory.
     * @param pictureCategory Used by {@link PictureLoader} when (all the images of) all
     *      the Pictures in a category are loaded.  Null means: use the default category.
     */
    public Picture(ImageResource resource, int dw, int dh, boolean listening, String pictureCategory)
    {
        this(resource, false, listening, pictureCategory);

        setClippedImageDestinationWidth(dw);
        setClippedImageDestinationHeight(dh);

        m_proxy.load();
    }

    protected Picture(String url, boolean load, boolean listening, String pictureCategory)
    {
        super(ShapeType.PICTURE);

        setURL(url);

        setListening(listening);

        if (pictureCategory != null) setPictureCategory(pictureCategory);

        m_proxy = new ImageProxy(this, load);
    }

    protected Picture(ImageResource resource, boolean load, boolean listening, String pictureCategory)
    {
        this(resource.getSafeUri().asString(), load, listening, pictureCategory);
        // resource.getSafeUri().asString() is the same as resource.getURL() - which is deprecated
    }

    /**
     * Returns whether the image of the Picture is loaded.
     * 
     * @return boolean
     */
    public boolean isLoaded()
    {
        return m_proxy.isLoaded();
    }

    /**
     * Sets the handler that will be invoked when the image of the Picture has loaded.
     * The handler may be invoked immediately if the image has already been loaded.
     * 
     * @param handler
     * @return Picture
     */
    public Picture onLoad(PictureLoadedHandler handler)
    {
        m_proxy.setPictureLoadedHandler(handler);

        return this;
    }

    public ImageData getImageData()
    {
        return m_proxy.getImageData();
    }

    /**
     * Returns the data: URL
     * 
     * @param mimeType If null, defaults to DataURLType.PNG
     * @return String
     */
    public String toDataURL(DataURLType mimeType)
    {
        return m_proxy.toDataURL(mimeType);
    }

    /**
     * Serializes this shape as a {@link JSONObject}
     * 
     * @return JSONObject
     */
    @Override
    public JSONObject toJSONObject()
    {
        JSONObject attr = new JSONObject(getAttributes());

        PictureSerializationMode mode = getSerializationMode();

        if (mode == PictureSerializationMode.DATA_URL)
        {
            // TODO support different image formats
            attr.put("url", new JSONString(toDataURL(null)));
        }
        else if (mode == PictureSerializationMode.RESOURCE_ID)
        {
            // TODO where do we make sure that it has a resourceID
            // TODO should we remove the URL here?
        }
        JSONObject object = new JSONObject();

        object.put("type", new JSONString(getShapeType().getValue()));

        object.put("attributes", attr);

        return object;
    }

    public static void onCategoryLoaded(String category, Runnable callback)
    {
        PictureLoader.getInstance().registerCallback(category, callback);
    }

    /**
     * Draws the image on the canvas.
     * 
     * @param context
     */
    @Override
    public boolean prepare(Context2D context, Attributes attr, double alpha)
    {
        context.save();

        if (context.isSelection())
        {
            context.setGlobalAlpha(1);

            m_proxy.drawSelectionImage(context);

            context.restore();
        }
        else
        {
            context.setGlobalAlpha(alpha);

            doApplyShadow(context, attr);

            m_proxy.drawImage(context);

            context.restore();
        }
        return false;
    }

    public String getPictureCategory()
    {
        return getAttributes().getPictureCategory();
    }

    public Picture setPictureCategory(String pictureCategory)
    {
        getAttributes().setPictureCategory(pictureCategory);

        return this;
    }

    public PictureSerializationMode getSerializationMode()
    {
        return getAttributes().getSerializationMode();
    }

    public Picture setSerializationMode(PictureSerializationMode serializationMode)
    {
        getAttributes().setSerializationMode(serializationMode);

        return this;
    }

    public String getResourceID()
    {
        return getAttributes().getResourceID();
    }

    public Picture setResourceID(String resourceID)
    {
        getAttributes().setResourceID(resourceID);

        return this;
    }

    /**
     * Returns the x coordinate of the picture's clip region. 
     * The default value is 0.
     * 
     * @return double
     */
    public double getClippedImageStartX()
    {
        return getAttributes().getClippedImageStartX();
    }

    /**
     * Sets the x coordinate of the picture's clip region. 
     * The default value is 0.
     * 
     * @param sx
     * @return Picture this picture
     */
    public Picture setClippedImageStartX(double sx)
    {
        getAttributes().setClippedImageStartX(sx);

        return this;
    }

    /**
     * Returns the y coordinate of the picture's clip region. 
     * The default value is 0.
     * 
     * @return double
     */
    public double getClippedImageStartY()
    {
        return getAttributes().getClippedImageStartY();
    }

    /**
     * Returns the y coordinate of the picture's clip region. 
     * The default value is 0.
     * 
     * @param sy
     * @return Picture this picture
     */
    public Picture setClippedImageStartY(double clippedImageStartY)
    {
        getAttributes().setClippedImageStartY(clippedImageStartY);

        return this;
    }

    /**
     * Returns the width of the picture's clip region. 
     * If the value is not set, it defaults to 0, which means it will
     * use the width of the loaded image.
     * 
     * @return double
     */
    public double getClippedImageWidth()
    {
        return getAttributes().getClippedImageWidth();
    }

    /**
     * Sets the width of the picture's clip region. 
     * If the value is not set, it defaults to 0, which means it will
     * use the width of the loaded image.
     * 
     * @param clippedImageWidth 
     * @return Picture this picture
     */
    public Picture setClippedImageWidth(double clippedImageWidth)
    {
        getAttributes().setClippedImageWidth(clippedImageWidth);

        return this;
    }

    /**
     * Returns the height of the picture's clip region. 
     * If the value is not set, it defaults to 0, which means it will
     * use the height of the loaded image.
     * 
     * @return double
     */
    public double getClippedImageHeight()
    {
        return getAttributes().getClippedImageHeight();
    }

    /**
     * Sets the height of the picture's clip region. 
     * If the value is not set, it defaults to 0, which means it will
     * use the height of the loaded image.
     * 
     * @param clippedImageHeight
     * @return Picture this picture
     */
    public Picture setClippedImageHeight(double clippedImageHeight)
    {
        getAttributes().setClippedImageHeight(clippedImageHeight);

        return this;
    }

    /**
     * Returns the width of the destination region. 
     * The default value is 0, which means it will use the clippedImageWidth.
     * 
     * @return double
     */
    public double getClippedImageDestinationWidth()
    {
        return getAttributes().getClippedImageDestinationWidth();
    }

    /**
     * Sets the width of the destination region. 
     * The default value is 0, which means it will use the clippedImageWidth.
     * 
     * @param clippedImageDestinationWidth
     * @return Picture
     */
    public Picture setClippedImageDestinationWidth(double clippedImageDestinationWidth)
    {
        getAttributes().setClippedImageDestinationWidth(clippedImageDestinationWidth);

        return this;
    }

    /**
     * Returns the height of the destination region. 
     * The default value is 0, which means it will use the clippedImageHeight.
     * <p>
     * Setting this value will cause the image to be scaled.
     * This can be used to reduce the memory footprint of the Image
     * used in the selection layer. 
     * <p>
     * Note that further scaling can be achieved via the <code>scale</code>
     * or <code>transform</code> attributes, which apply to all Shapes.
     * 
     * @return double
     */
    public double getClippedImageDestinationHeight()
    {
        return getAttributes().getClippedImageDestinationHeight();
    }

    /**
     * Sets the height of the destination region. 
     * The default value is 0, which means it will use the clippedImageHeight.
     * <p>
     * Setting this value will cause the image to be scaled.
     * This can be used to reduce the memory footprint of the Image
     * used in the selection layer. 
     * <p>
     * Note that further scaling can be achieved via the <code>scale</code>
     * or <code>transform</code> attributes, which apply to all Shapes.
     * 
     * @param clippedImageDestinationHeight
     * @return Picture
     */
    public Picture setClippedImageDestinationHeight(double clippedImageDestinationHeight)
    {
        getAttributes().setClippedImageDestinationHeight(clippedImageDestinationHeight);

        return this;
    }

    /**
     * Returns the URL of the image. For ImageResources, this return the
     * value of ImageResource.getSafeUri().asString().
     * 
     * @return String
     */
    public String getURL()
    {
        return getAttributes().getURL();
    }

    /**
     * Sets the URL of the image. For ImageResources, this should be the
     * value of ImageResource.getSafeUri().asString().
     * 
     * @param url
     * @return Picture
     */
    public Picture setURL(String url)
    {
        getAttributes().setURL(url);

        return this;
    }

    @Override
    public IFactory<?> getFactory()
    {
        return new PictureFactory();
    }

    public static class PictureFactory extends ShapeFactory<Picture> implements PostProcessNodeFactory
    {
        public PictureFactory()
        {
            super(ShapeType.PICTURE);

            addAttribute(Attribute.URL); // NOTE: not required by JSON deserializer!

            addAttribute(Attribute.CLIPPED_IMAGE_START_X);

            addAttribute(Attribute.CLIPPED_IMAGE_START_Y);

            addAttribute(Attribute.CLIPPED_IMAGE_WIDTH);

            addAttribute(Attribute.CLIPPED_IMAGE_HEIGHT);

            addAttribute(Attribute.CLIPPED_IMAGE_DESTINATION_WIDTH);

            addAttribute(Attribute.CLIPPED_IMAGE_DESTINATION_HEIGHT);

            addAttribute(Attribute.PICTURE_CATEGORY);

            addAttribute(Attribute.SERIALIZATION_MODE);

            addAttribute(Attribute.RESOURCE_ID);
        }

        @Override
        public Picture create(JSONObject node, ValidationContext ctx)
        {
            ResourceResolver resolver = ResourceResolver.getInstance();

            return new Picture(node, resolver);
        }

        @Override
        public void process(final IJSONSerializable<?> node)
        {
            if (false == (node instanceof Picture))
            {
                return;
            }
            final Picture self = (Picture) node;

            if (self.isLoaded())
            {
                return;
            }
            else
            {
                self.onLoad(new PictureLoadedHandler()
                {
                    @Override
                    public void onPictureLoaded(Picture picture)
                    {
                        Layer layer;

                        if ((layer = picture.getLayer()) != null)
                        {
                            if (layer.getParent() != null)
                            {
                                LayerRedrawManager.get().schedule(layer);
                            }
                        }
                    }
                });
            }
        }
    }
}
