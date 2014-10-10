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

package com.emitrom.lienzo.client.core.shape.json;

import com.emitrom.lienzo.client.core.shape.Picture;
import com.emitrom.lienzo.shared.core.types.PictureSerializationMode;
import com.google.gwt.resources.client.ImageResource;

/**
 * ResourceResolver is used when deserializing {@link Picture}s.
 * Currently, its main purpose is to resolve resource IDs to URLs for Pictures
 * with {@link PictureSerializationMode} value of RESOURCE_ID.
 * 
 * @since 1.1
 */
public class ResourceResolver
{
    private static ResourceResolver s_instance = new ResourceResolver();

    public void resolve(Picture p)
    {
        if (p.getSerializationMode() == PictureSerializationMode.RESOURCE_ID)
        {
            String id = p.getResourceID();

            String url = resolvePictureResourceID(p, id);

            if (url != null)
            {
                p.setURL(url);
            }
        }
    }

    protected String resolvePictureResourceID(Picture p, String resourceID)
    {
        // Override this method to return the image URL.
        // For ImageResources, return getURL(imageResource)
        
        return null;
    }

    protected String getURL(ImageResource resource)
    {
        return resource.getSafeUri().asString();
    }

    public static ResourceResolver getInstance()
    {
        return s_instance;
    }

    public static void setInstance(ResourceResolver instance)
    {
        s_instance = instance;
    }
}
