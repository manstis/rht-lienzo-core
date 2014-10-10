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
import java.util.HashMap;

/**
 * PictureLoader can be used to receive notifications when a category of 
 * {@link Picture}s has loaded.
 * 
 * @see Picture#onCategoryLoaded(String, Runnable)
 * @see PictureLoadedHandler
 * @see Picture#onLoad(PictureLoadedHandler)
 */
public class PictureLoader
{
    private static final PictureLoader s_instance = new PictureLoader();

    public static final String         ALL        = "ALL";

    private HashMap<String, Category>  m_map      = new HashMap<String, Category>();

    public static final PictureLoader getInstance()
    {
        return s_instance;
    }

    /**
     * Registers an ImageProxy for a certain picture category.
     * The proxy is added to the list for that category and when all images in that 
     * category have been loaded, it invokes the callbacks that were registered for that category.
     * <p>
     * This is an internal method and should not be invoked by toolkit users.
     * 
     * @param category {@link Picture} category name
     * @param proxy {@link ImageProxy}
     */
    public final void registerProxy(String category, ImageProxy proxy)
    {
        if (false == proxy.isLoaded())
        {
            // Don't add proxy if it's already done loading
            getOrCreateCategory(category).add(proxy);
        }
    }

    /**
     * This is invoked by the {@link ImageProxy} when its image has been loaded.
     * If all images in the category have been loaded, it invokes the callbacks that were registered for that category.
     * <p>
     * This is an internal method and should not be invoked by toolkit users.
     * 
     * @param category {@link Picture} category name
     * @param proxy {@link ImageProxy}
     */
    public final void doneLoading(String category, ImageProxy proxy)
    {
        Category c = getCategory(category);

        if (c != null)
        {
            if (c.doneLoading(proxy))
            {
                // All proxies were done loading, so the callbacks were invoked
                // and the Category can be removed.
                m_map.remove(category);
            }
        }
    }

    /**
     * Registers a callback for the specified {@link Picture} category.
     * If all images in the {@picture} category have been loaded, the callback will be
     * triggered immediately.
     * 
     * @param category {@link Picture} category name
     * @param callback Runnable that will be invoked when all images in the category have been loaded
     */
    public final void registerCallback(String category, Runnable callback)
    {
        if (getCategory(category) == null)
        {
            // All proxies already loaded
            callback.run();
            return;
        }

        getOrCreateCategory(category).add(callback);
    }

    private final Category getCategory(String category)
    {
        if (category == null)
        {
            category = ALL;
        }
        return m_map.get(category);
    }

    private final Category getOrCreateCategory(String category)
    {
        if (category == null)
        {
            category = ALL;
        }
        Category c = m_map.get(category);
        
        if (c == null)
        {
            c = new Category();
            
            m_map.put(category, c);
        }
        return c;
    }

    /**
     * Category represents a {@link Picture} category used when loading
     * images by {@link PictureLoader}.
     *
     * @see PictureLoader
     */
    protected static class Category
    {
        private ArrayList<ImageProxy> m_proxies   = new ArrayList<ImageProxy>();

        private ArrayList<Runnable>   m_callbacks = new ArrayList<Runnable>();

        public void add(Runnable callback)
        {
            m_callbacks.add(callback);
        }

        public void add(ImageProxy proxy)
        {
            m_proxies.add(proxy);
        }

        public boolean doneLoading(ImageProxy proxy)
        {
            m_proxies.remove(proxy);

            // Check if all proxies are done loading.
            // If so, invoke the callbacks.
            if (m_proxies.size() == 0)
            {
                if (m_callbacks.size() > 0)
                {
                    for (int i = 0, n = m_callbacks.size(); i < n; i++)
                    {
                        m_callbacks.get(i).run();
                    }
                    m_callbacks.clear();
                }
                return true;
            }
            return false;
        }
    }
}