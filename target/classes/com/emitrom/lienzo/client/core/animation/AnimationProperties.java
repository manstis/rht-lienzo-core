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

package com.emitrom.lienzo.client.core.animation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * AnimationProperties specifies which Node Attributes are modified and what their 
 * ultimate values are at the end of the animation.
 * <p>
 * <b>Note that if the IPrimitive node has a Transform attribute defined, you can't animate the following attributes: 
 * rotation, shear, scale and offset!</b>
 * <p>
 * This is because the Transform overrides those attributes. 
 * See the <a href="http://wiki.emitrom.com/wiki/index.php/Shape_Transformations">wiki</a> 
 * for a detailed explanation.
 * 
 * @see AnimationProperty
 * @see AnimationProperty.Properties
 * @see AnimationManager#add(com.emitrom.lienzo.client.core.shape.IPrimitive, AnimationTweener, AnimationProperties, int, IAnimationCallback)
 * @see AnimationTweener
 */
public class AnimationProperties
{
    private final ArrayList<AnimationProperty> m_properties = new ArrayList<AnimationProperty>();

    public static final AnimationProperties create(AnimationProperty property, AnimationProperty... properties)
    {
        return new AnimationProperties(property, properties);
    }

    public AnimationProperties()
    {
    }

    public AnimationProperties(AnimationProperty property, AnimationProperty... properties)
    {
        push(property);

        if (null != properties)
        {
            for (int i = 0; i < properties.length; i++)
            {
                push(properties[i]);
            }
        }
    }

    public List<AnimationProperty> getProperties()
    {
        return Collections.unmodifiableList(m_properties);
    }

    public AnimationProperties push(AnimationProperty property)
    {
        if (null != property)
        {
            m_properties.add(property);
        }
        return this;
    }
}
