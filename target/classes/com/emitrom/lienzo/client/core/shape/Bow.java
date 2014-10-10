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
import com.emitrom.lienzo.client.core.shape.json.validators.ValidationException;
import com.emitrom.lienzo.shared.core.types.ShapeType;
import com.google.gwt.json.client.JSONObject;

/**
 * A Slice is defined by a start angle and an end angle, like a slice of a pizza.
 * The angles can be specified in clockwise or counter-clockwise order.
 * Slices greater than 180 degrees (or PI radians) look like pacmans.
 */
public class Bow extends Shape<Bow>
{
    /**
     * Constructor. Creates an instance of a slice.
     * 
     * @param radius
     * @param startAngle in radians
     * @param endAngle in radians
     * @param counterClockwise
     */
    public Bow(double innerRadius, double outerRadius, double startAngle, double endAngle, boolean counterClockwise)
    {
        super(ShapeType.BOW);

        setInnerRadius(innerRadius).setOuterRadius(outerRadius).setStartAngle(startAngle).setEndAngle(endAngle).setCounterClockwise(counterClockwise);
    }

    /**
     * Constructor. Creates an instance of a slice, drawn clockwise.
     * 
     * @param radius
     * @param startAngle in radians
     * @param endAngle in radians
     */
    public Bow(double innerRadius, double outerRadius, double startAngle, double endAngle)
    {
        super(ShapeType.BOW);

        setInnerRadius(innerRadius).setOuterRadius(outerRadius).setStartAngle(startAngle).setEndAngle(endAngle).setCounterClockwise(false);
    }

    protected Bow(JSONObject node, ValidationContext ctx) throws ValidationException
    {
        super(ShapeType.BOW, node, ctx);
    }

    /**
     * Draws this slice.
     * 
     * @param context
     */
    @Override
    public boolean prepare(Context2D context, Attributes attr, double alpha)
    {
        final double end = getEndAngle();

        final double beg = getStartAngle();

        if (beg == end)
        {
            return false;
        }
        final double ord = getOuterRadius();

        final double ird = getInnerRadius();

        final boolean ccw = isCounterClockwise();

        if ((ord > 0) && (ird > 0))
        {
            context.beginPath();

            context.arc(0, 0, ord, beg, end, ccw);

            context.arc(0, 0, ird, end, beg, (false == ccw));

            context.closePath();

            return true;
        }
        return false;
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
    public Bow setInnerRadius(double radius)
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
    public Bow setOuterRadius(double radius)
    {
        getAttributes().setOuterRadius(radius);

        return this;
    }

    /**
     * Gets the starting angle of this slice.
     * 
     * @return double in radians
     */
    public double getStartAngle()
    {
        return getAttributes().getStartAngle();
    }

    /**
     * Sets the starting angle of this slice.
     * 
     * @param angle in radians
     * @return this Slice.
     */
    public Bow setStartAngle(double angle)
    {
        getAttributes().setStartAngle(angle);

        return this;
    }

    /**
     * Gets the end angle of this slice.
     * 
     * @return double in radians
     */
    public double getEndAngle()
    {
        return getAttributes().getEndAngle();
    }

    /**
     * Gets the end angle of this slice.
     * 
     * @param angle in radians
     * @return this Slice.
     */
    public Bow setEndAngle(double angle)
    {
        getAttributes().setEndAngle(angle);

        return this;
    }

    /**
     * Returns whether the slice is drawn counter clockwise.
     * The default value is true.
     * 
     * @return boolean
     */
    public boolean isCounterClockwise()
    {
        return getAttributes().isCounterClockwise();
    }

    /**
     * Sets whether the drawing direction of this slice is counter clockwise.
     * The default value is true.
     * 
     * @param counterclockwise
     * @return this Slice
     */
    public Bow setCounterClockwise(boolean counterclockwise)
    {
        getAttributes().setCounterClockwise(counterclockwise);

        return this;
    }

    @Override
    public IFactory<Bow> getFactory()
    {
        return new BowFactory();
    }

    public static class BowFactory extends ShapeFactory<Bow>
    {
        public BowFactory()
        {
            super(ShapeType.BOW);

            addAttribute(Attribute.INNER_RADIUS, true);

            addAttribute(Attribute.OUTER_RADIUS, true);

            addAttribute(Attribute.START_ANGLE, true);

            addAttribute(Attribute.END_ANGLE, true);

            addAttribute(Attribute.COUNTER_CLOCKWISE);
        }

        @Override
        public Bow create(JSONObject node, ValidationContext ctx) throws ValidationException
        {
            return new Bow(node, ctx);
        }
    }
}
