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

package com.emitrom.lienzo.client.core.util;

import com.emitrom.lienzo.client.core.Context2D;
import com.emitrom.lienzo.client.core.LienzoGlobals;
import com.emitrom.lienzo.client.core.NativeContext2D;
import com.emitrom.lienzo.client.core.image.BlobImageReadyCallback;
import com.emitrom.lienzo.client.core.image.JSImage;
import com.emitrom.lienzo.client.core.image.JSImageExtractionHandler;
import com.emitrom.lienzo.client.core.image.ImageLoader;
import com.emitrom.lienzo.shared.core.types.DataURLType;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.dom.client.Document;

public final class ScratchCanvas
{
    private final int           m_wide;

    private final int           m_high;

    private final CanvasElement m_element;

    private final Context2D     m_context;

    public ScratchCanvas(int wide, int high)
    {
        m_wide = wide;

        m_high = high;

        if (LienzoGlobals.get().isCanvasSupported())
        {
            m_element = Document.get().createCanvasElement();

            m_element.setWidth(wide);

            m_element.setHeight(high);

            m_context = new Context2D(getNativeContext2D(m_element));
        }
        else
        {
            m_element = null;

            m_context = null;
        }
    }

    public final void clear()
    {
        Context2D context = getContext();

        if (null != context)
        {
            context.clearRect(0, 0, m_wide, m_high);
        }
    }

    public final void setPixelSize(int wide, int high)
    {
        m_element.setWidth(wide);

        m_element.setHeight(high);
    }

    public final CanvasElement getElement()
    {
        return m_element;
    }

    public final int getWidth()
    {
        return m_wide;
    }

    public final int getHeight()
    {
        return m_high;
    }

    public final Context2D getContext()
    {
        return m_context;
    }

    public final String toDataURL()
    {
        if (null != m_element)
        {
            return toDataURL(m_element);
        }
        else
        {
            return "data:,";
        }
    }

    public final String toDataURL(DataURLType mimetype)
    {
        if (null != m_element)
        {
            if (null == mimetype)
            {
                mimetype = DataURLType.PNG;
            }
            return toDataURL(m_element, mimetype.getValue());
        }
        else
        {
            return "data:,";
        }
    }

    public final void getJSImage(final JSImageExtractionHandler handler)
    {
        if (isBlobAPISupported())
        {
            getImageFromBlob(new BlobImageReadyCallback()
            {
                @Override
                public void onBlobImageReady(JSImage image)
                {
                    handler.onSuccess(image);
                }
            });
        }
        else
        {
            new ImageLoader(toDataURL())
            {
                @Override
                public void onLoaded(ImageLoader loader)
                {
                    handler.onSuccess(loader.getJSImage());
                }

                @Override
                public void onError(ImageLoader loader, String message)
                {
                    handler.onFailure(message);
                }
            };
        }
    }

    public final boolean isBlobAPISupported()
    {
        return LienzoGlobals.get().isBlobAPIEnabled() && (null != m_element) && isBlobAPISupported0(m_element);
    }

    public final void getImageFromBlob(BlobImageReadyCallback callback)
    {
        if (false == isBlobAPISupported())
        {
            throw new IllegalArgumentException("Blob Images are not supported");
        }
        getImageFromBlob0(m_element, callback);
    }

    private static native final void getImageFromBlob0(CanvasElement element, BlobImageReadyCallback callback)
    /*-{
		var blobber = function(blob) {
			url = $wnd.URL.createObjectURL(blob);

			var image = new $wnd.Image();

			image.onload = function() {

				$wnd.URL.revokeObjectURL(url);

				callback.@com.emitrom.lienzo.client.core.image.BlobImageReadyCallback::onBlobImageReady(Lcom/emitrom/lienzo/client/core/image/JSImage;)(image);
			}
			image.src = url;
		};
		element.toBlob(blobber);
    }-*/;

    private static native final boolean isBlobAPISupported0(CanvasElement element)
    /*-{
		return !!element.toBlob;
    }-*/;

    private static native final String toDataURL(CanvasElement element)
    /*-{
		return element.toDataURL();
    }-*/;

    // TODO other arguments, e.g. for image/jpeg The second argument, if it is a number in the range 0.0 to 1.0 inclusive, must be treated as the desired quality level. If it is not a number or is outside that range, the user agent must use its default value, as if the argument had been omitted.

    private static native final String toDataURL(CanvasElement element, String mimetype)
    /*-{
		return element.toDataURL(mimetype);
    }-*/;

    private static final native NativeContext2D getNativeContext2D(CanvasElement element)
    /*-{
		return element.getContext("2d");
    }-*/;
}
