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

import com.emitrom.lienzo.client.core.Context2D.GradientJSO;
import com.emitrom.lienzo.client.core.shape.Shape;
import com.emitrom.lienzo.client.core.types.ImageLoader.ImageJSO;
import com.emitrom.lienzo.shared.core.types.FillRepeat;

/**
 * PatternGradient defines the fill style for a {@link Shape} as a Pattern Gradient. 
 */
public final class PatternGradient implements FillGradient
{
    public static final String       TYPE = "PatternGradient";

    private final PatternGradientJSO m_jso;

    public PatternGradient(PatternGradientJSO jso)
    {
        m_jso = jso;
    }

    public PatternGradient(ImageLoader loader)
    {
        this(PatternGradientJSO.make(loader.getJSO(), FillRepeat.REPEAT.getValue()));
    }

    public PatternGradient(ImageLoader loader, FillRepeat repeat)
    {
        this(PatternGradientJSO.make(loader.getJSO(), repeat.getValue()));
    }

    @Override
    public String getType()
    {
        return TYPE;
    }

    public final PatternGradientJSO getJSO()
    {
        return m_jso;
    }

    public static final class PatternGradientJSO extends GradientJSO
    {
        protected PatternGradientJSO()
        {

        }

        public static final native PatternGradientJSO make(ImageJSO image, String repeat)
        /*-{
			return {
				image : image,
				repeat : repeat,
				type : "PatternGradient"
			}
        }-*/;

    }
}
