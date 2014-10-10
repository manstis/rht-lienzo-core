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

import com.google.gwt.core.client.JavaScriptObject;

/**
 * JSO Wrapper providing convenience methods to load images. 
 */
public abstract class ImageLoader
{
    protected ImageJSO m_jso;

    private boolean    m_loaded = false;

    public ImageLoader(String url)
    {
        m_jso = ImageJSO.make(url, this);
    }

    /**
     * Return true if the image was already loaded.
     * @return boolean
     */
    public final boolean isLoaded()
    {
        return m_loaded;
    }

    /**
     * Get width
     * @return int
     */
    public final int getWidth()
    {
        return m_jso.getWidth();
    }

    /**
     * Get Height.
     * @return int
     */
    public final int getHeight()
    {
        return m_jso.getHeight();
    }

    public abstract void onLoaded(ImageLoader image);

    /**
     * Get the JSO
     * @return {@link ImageJSO}
     */
    public final ImageJSO getJSO()
    {
        return m_jso;
    }

    @SuppressWarnings("unused")
    private final void onLoadedHelper()
    {
        m_loaded = true;

        onLoaded(this);
    }

    public static final class ImageJSO extends JavaScriptObject
    {
        protected ImageJSO()
        {

        }

        public static final native ImageJSO make(String url, ImageLoader self)
        /*-{
			var image = new $wnd.Image();

			image.onload = function() {
				self.@com.emitrom.lienzo.client.core.types.ImageLoader::onLoadedHelper()();
			}
			image.src = url;

			return image;
        }-*/;

        public final native int getWidth()
        /*-{
			return this.width;
        }-*/;

        public final native int getHeight()
        /*-{
			return this.height;
        }-*/;
    }
}
