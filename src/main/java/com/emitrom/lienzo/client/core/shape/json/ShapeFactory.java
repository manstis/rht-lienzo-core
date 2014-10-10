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

import com.emitrom.lienzo.client.core.Attribute;
import com.emitrom.lienzo.client.core.shape.IJSONSerializable;
import com.emitrom.lienzo.client.core.shape.Shape;
import com.emitrom.lienzo.shared.core.types.ShapeType;

/**
 * ShapeFactory is an abstract {@link IFactory} for classes that extend {@link Shape}.
 * It adds the default Node and Shape attributes.
 *
 * @param <T>
 * @since 1.1
 */
public abstract class ShapeFactory<T extends IJSONSerializable<T>> extends NodeFactory<T>
{
    protected ShapeFactory(ShapeType type)
    {
        super(type.getValue());

        addAttribute(Attribute.X);
        addAttribute(Attribute.Y);
        addAttribute(Attribute.ALPHA);
        addAttribute(Attribute.FILL);
        addAttribute(Attribute.STROKE);
        addAttribute(Attribute.STROKE_WIDTH);
        addAttribute(Attribute.DRAGGABLE);
        addAttribute(Attribute.SCALE);
        addAttribute(Attribute.SHEAR);
        addAttribute(Attribute.ROTATION);
        addAttribute(Attribute.OFFSET);
        addAttribute(Attribute.SHADOW);
        addAttribute(Attribute.LINE_CAP);
        addAttribute(Attribute.LINE_JOIN);
        addAttribute(Attribute.MITER_LIMIT);
        addAttribute(Attribute.DRAG_CONSTRAINT);
        addAttribute(Attribute.DRAG_BOUNDS);
        addAttribute(Attribute.DASH_ARRAY);
        addAttribute(Attribute.DASH_OFFSET);
        addAttribute(Attribute.FILL_SHAPE_FOR_SELECTION);
    }

    /**
     * Only factories that wish to extend other factories should use this.
     * 
     * @param type {@link ShapeType}
     */
    protected void setShapeType(ShapeType type)
    {
        setTypeName(type.getValue());
    }
}
