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

import java.util.ArrayList;

import com.emitrom.lienzo.client.core.types.ImageData;

public class ImageDataFilterChain implements ImageDataFilter
{
    private ArrayList<ImageDataFilter> m_filters = new ArrayList<ImageDataFilter>();

    public ImageDataFilterChain()
    {

    }

    public ImageDataFilterChain(ImageDataFilter... filters)
    {
        if (null != filters)
        {
            for (int i = 0; i < filters.length; i++)
            {
                add(filters[i]);
            }
        }
    }

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
        for (int i = 0; i < m_filters.size(); i++)
        {
            ImageDataFilter filter = m_filters.get(i);

            if (null != filter)
            {
                ImageData imdata = filter.filter(source, false);

                if (null != imdata)
                {
                    source = imdata;
                }
            }
        }
        return source;
    }

    public ImageDataFilterChain add(ImageDataFilter filter)
    {
        if (null != filter)
        {
            m_filters.add(filter);
        }
        return this;
    }
}
