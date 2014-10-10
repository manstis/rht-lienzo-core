
package com.emitrom.lienzo.client.core.image;

import com.google.gwt.core.client.JavaScriptObject;

public final class JSImage extends JavaScriptObject
{
    protected JSImage()
    {
    }

    public static final native JSImage make(String url, ImageLoader self)
    /*-{
		var image = new $wnd.Image();

		image.onload = function() {

			if ('naturalHeight' in image) {
				if (image.naturalHeight + image.naturalWidth == 0) {
					self.@com.emitrom.lienzo.client.core.image.ImageLoader::onErrorHelper(Ljava/lang/String;)("Image " + url + " did not load completely or 0.");
					return;
				}
			} else if (image.width + image.height == 0) {
				self.@com.emitrom.lienzo.client.core.image.ImageLoader::onErrorHelper(Ljava/lang/String;)("Image " + url + " did not load completely or 0.");
				return;
			}
			self.@com.emitrom.lienzo.client.core.image.ImageLoader::onLoadedHelper()();
		};
		image.onerror = function() {

			self.@com.emitrom.lienzo.client.core.image.ImageLoader::onErrorHelper(Ljava/lang/String;)("Image " + url + " did not load.");
		};
		image.crossOrigin = '';

		image.src = url;

		return image;
    }-*/;

    public final native int getWidth()
    /*-{
		return this.width || 0;
    }-*/;

    public final native int getHeight()
    /*-{
		return this.height || 0;
    }-*/;
}