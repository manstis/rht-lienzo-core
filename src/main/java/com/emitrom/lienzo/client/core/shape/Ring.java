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

package com.emitrom.lienzo.client.core.shape;

import com.emitrom.lienzo.client.core.Attribute;
import com.emitrom.lienzo.client.core.Context2D;
import com.emitrom.lienzo.client.core.shape.json.IFactory;
import com.emitrom.lienzo.client.core.shape.json.ShapeFactory;
import com.emitrom.lienzo.client.core.shape.json.validators.ValidationContext;
import com.emitrom.lienzo.shared.core.types.ShapeType;
import com.google.gwt.json.client.JSONObject;

/**
 * A Slice is defined by a start angle and an end angle, like a slice of a pizza.
 * The angles can be specified in clockwise or counter-clockwise order.
 * Slices greater than 180 degrees (or PI radians) look like pacmans.
 */
public class Ring extends Shape<Ring>
{
    /**
     * Constructor. Creates an instance of a slice.
     * 
     * @param radius
     * @param startAngle in radians
     * @param endAngle in radians
     * @param counterClockwise
     */
    public Ring(double innerRadius, double outerRadius)
    {
        super(ShapeType.RING);

        setInnerRadius(innerRadius).setOuterRadius(outerRadius);
    }

    protected Ring(JSONObject node)
    {
        super(ShapeType.RING, node);
    }

    @Override
    protected boolean doStrokeExtraProperties()
    {
        return false;
    }

    /**
     * Draws this slice.
     * 
     * @param context
     */
    @Override
    public boolean prepare(Context2D context, Attributes attr, double alpha)
    {
        context.beginPath();

        context.arc(0, 0, getOuterRadius(), 0, Math.PI * 2, false);

        context.arc(0, 0, getInnerRadius(), 0, Math.PI * 2, true);

        context.closePath();

        return true;
    }

    @Override
    protected void stroke(Context2D context, Attributes attr, double alpha)
    {
        context.save();

        if (setStrokeParams(context, attr, alpha))
        {
            if (context.isSelection())
            {
                context.beginPath();

                context.arc(0, 0, getOuterRadius(), 0, Math.PI * 2, false);

                context.closePath();

                context.stroke();

                context.beginPath();

                context.arc(0, 0, getInnerRadius(), 0, Math.PI * 2, true);

                context.closePath();

                context.stroke();

                context.restore();

                return;
            }
            doApplyShadow(context, attr);

            context.beginPath();

            context.arc(0, 0, getOuterRadius(), 0, Math.PI * 2, false);

            context.closePath();

            context.stroke();

            context.beginPath();

            context.arc(0, 0, getInnerRadius(), 0, Math.PI * 2, true);

            context.closePath();

            context.stroke();
        }
        context.restore();
    }

    /**
     * Gets the {@link Star} inner radius.
     * 
     * @return double
     */
    public double getInnerRadius()
    {
        return getAttributes().getInnerRadius();
    }

    /**
     * Sets the {@link Star} inner radius.
     * 
     * @param radius
     * @return this Star
     */
    public Ring setInnerRadius(double radius)
    {
        getAttributes().setInnerRadius(radius);

        return this;
    }

    /**
     * Returns the {@link Star} outer radius.
     * 
     * @return double
     */
    public double getOuterRadius()
    {
        return getAttributes().getOuterRadius();
    }

    /**
     * Sets the outer radius.
     * 
     * @param radius
     * @return this Star
     */
    public Ring setOuterRadius(double radius)
    {
        getAttributes().setOuterRadius(radius);

        return this;
    }

    @Override
    public IFactory<?> getFactory()
    {
        return new RingFactory();
    }

    public static class RingFactory extends ShapeFactory<Ring>
    {
        public RingFactory()
        {
            super(ShapeType.RING);

            addAttribute(Attribute.INNER_RADIUS, true);

            addAttribute(Attribute.OUTER_RADIUS, true);
        }

        @Override
        public Ring create(JSONObject node, ValidationContext ctx)
        {
            return new Ring(node);
        }
    }
}
