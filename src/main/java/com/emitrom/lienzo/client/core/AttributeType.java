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

package com.emitrom.lienzo.client.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.emitrom.lienzo.client.core.shape.json.validators.ArrayValidator;
import com.emitrom.lienzo.client.core.shape.json.validators.AttributeTypeValidator;
import com.emitrom.lienzo.client.core.shape.json.validators.BooleanValidator;
import com.emitrom.lienzo.client.core.shape.json.validators.ColorValidator;
import com.emitrom.lienzo.client.core.shape.json.validators.DragBoundsValidator;
import com.emitrom.lienzo.client.core.shape.json.validators.EnumValidator;
import com.emitrom.lienzo.client.core.shape.json.validators.ImageTypeValidator;
import com.emitrom.lienzo.client.core.shape.json.validators.LinearGradientValidator;
import com.emitrom.lienzo.client.core.shape.json.validators.MultiTypeValidator;
import com.emitrom.lienzo.client.core.shape.json.validators.NumberValidator;
import com.emitrom.lienzo.client.core.shape.json.validators.PatternGradientValidator;
import com.emitrom.lienzo.client.core.shape.json.validators.Point2DValidator;
import com.emitrom.lienzo.client.core.shape.json.validators.RadialGradientValidator;
import com.emitrom.lienzo.client.core.shape.json.validators.ShadowValidator;
import com.emitrom.lienzo.client.core.shape.json.validators.StringValidator;
import com.emitrom.lienzo.client.core.shape.json.validators.TransformValidator;
import com.emitrom.lienzo.client.core.shape.json.validators.ValidationContext;
import com.emitrom.lienzo.client.core.shape.json.validators.ValidationException;
import com.emitrom.lienzo.shared.core.types.ArrowType;
import com.emitrom.lienzo.shared.core.types.CompositeOperation;
import com.emitrom.lienzo.shared.core.types.DragConstraint;
import com.emitrom.lienzo.shared.core.types.LineCap;
import com.emitrom.lienzo.shared.core.types.LineJoin;
import com.emitrom.lienzo.shared.core.types.PictureSerializationMode;
import com.emitrom.lienzo.shared.core.types.TextAlign;
import com.emitrom.lienzo.shared.core.types.TextBaseLine;
import com.google.gwt.json.client.JSONValue;

/**
 * Each {@link Attribute} has an AttributeType.
 * It's basically an Enum that can be extended by toolkit users.
 * <p>
 * Each AttributeType has its own {@link AttributeTypeValidator}
 * that is used when deserializing from JSON.
 */
public class AttributeType
{
    public static AttributeType    TRANSFORM_TYPE           = new AttributeType(TransformValidator.INSTANCE);

    public static AttributeType    COLOR_TYPE               = new AttributeType(ColorValidator.INSTANCE);

    public static AttributeType    STRING_TYPE              = new AttributeType(StringValidator.INSTANCE);

    public static AttributeType    NUMBER_TYPE              = new AttributeType(NumberValidator.INSTANCE);

    public static AttributeType    NUMBER_ARRAY_TYPE        = new AttributeType(new ArrayValidator(NumberValidator.INSTANCE));

    public static AttributeType    BOOLEAN_TYPE             = new AttributeType(BooleanValidator.INSTANCE);

    public static AttributeType    POINT2D_TYPE             = new AttributeType(Point2DValidator.INSTANCE);

    public static AttributeType    POINT2D_ARRAY_TYPE       = new AttributeType(new ArrayValidator(Point2DValidator.INSTANCE));

    public static AttributeType    LINEAR_GRADIENT_TYPE     = new AttributeType(LinearGradientValidator.INSTANCE);

    public static AttributeType    PATTERN_GRADIENT_TYPE    = new AttributeType(PatternGradientValidator.INSTANCE);

    public static AttributeType    RADIAL_GRADIENT_TYPE     = new AttributeType(RadialGradientValidator.INSTANCE);

    public static AttributeType    IMAGE_TYPE               = new AttributeType(ImageTypeValidator.INSTANCE);

    public static AttributeType    SHADOW_TYPE              = new AttributeType(ShadowValidator.INSTANCE);

    public static AttributeType    SERIALIZATION_MODE_TYPE  = new AttributeType(new EnumValidator<PictureSerializationMode>("SerializationMode", PictureSerializationMode.values()));

    public static AttributeType    DASH_ARRAY_TYPE          = new AttributeType(new ArrayValidator(NumberValidator.INSTANCE));

    public static AttributeType    LINE_CAP_TYPE            = new AttributeType(new EnumValidator<LineCap>("LineCap", LineCap.values()));

    public static AttributeType    LINE_JOIN_TYPE           = new AttributeType(new EnumValidator<LineJoin>("LineJoin", LineJoin.values()));

    public static AttributeType    DRAG_BOUNDS_TYPE         = new AttributeType(DragBoundsValidator.INSTANCE);

    public static AttributeType    DRAG_CONSTRAINT_TYPE     = new AttributeType(new EnumValidator<DragConstraint>("DragConstraint", DragConstraint.values()));

    public static AttributeType    TEXT_ALIGN_TYPE          = new AttributeType(new EnumValidator<TextAlign>("TextAlign", TextAlign.values()));

    public static AttributeType    TEXT_BASELINE_TYPE       = new AttributeType(new EnumValidator<TextBaseLine>("TextBaseLine", TextBaseLine.values()));

    public static AttributeType    COMPOSITE_OPERATION_TYPE = new AttributeType(new EnumValidator<CompositeOperation>("CompositeOperation", CompositeOperation.values()));

    public static AttributeType    ARROW_TYPE               = new AttributeType(new EnumValidator<ArrowType>("ArrowType", ArrowType.values()));

    public static AttributeType    FILL_TYPE                = new MultiAttributeType("Color or Gradient", COLOR_TYPE, LINEAR_GRADIENT_TYPE, PATTERN_GRADIENT_TYPE, RADIAL_GRADIENT_TYPE);

    public static AttributeType    STROKE_TYPE              = COLOR_TYPE;

    private AttributeTypeValidator m_validator;

    protected AttributeType(AttributeTypeValidator validator)
    {
        m_validator = validator;
    }

    public boolean isMultiAttributeType()
    {
        return false;
    }

    public void validate(JSONValue val, ValidationContext ctx) throws ValidationException
    {
        m_validator.validate(val, ctx);
    }

    public MultiAttributeType getAsMultiAttributeType()
    {
        return null;
    }

    public static class MultiAttributeType extends AttributeType
    {
        private final ArrayList<AttributeType> m_types = new ArrayList<AttributeType>();

        public MultiAttributeType(String typeDescription, AttributeType... types)
        {
            super(new MultiTypeValidator(typeDescription, types));

            if (types != null)
            {
                for (int i = 0; i < types.length; i++)
                {
                    AttributeType type = types[i];

                    if (type != null)
                    {
                        m_types.add(type);
                    }
                }
            }
        }

        public List<AttributeType> getTypes()
        {
            return Collections.unmodifiableList(m_types);
        }

        @Override
        public MultiAttributeType getAsMultiAttributeType()
        {
            return this;
        }

        @Override
        public boolean isMultiAttributeType()
        {
            return true;
        }
    }
}
