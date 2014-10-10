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
public class Slice extends Shape<Slice>
{
    /**
     * Constructor. Creates an instance of a slice.
     * 
     * @param radius
     * @param startAngle in radians
     * @param endAngle in radians
     * @param counterClockwise
     */
    public Slice(double radius, double startAngle, double endAngle, boolean counterClockwise)
    {
        super(ShapeType.SLICE);

        setRadius(radius).setStartAngle(startAngle).setEndAngle(endAngle).setCounterClockwise(counterClockwise);
    }

    /**
     * Constructor. Creates an instance of a slice, drawn clockwise.
     * 
     * @param radius
     * @param startAngle in radians
     * @param endAngle in radians
     */
    public Slice(double radius, double startAngle, double endAngle)
    {
        super(ShapeType.SLICE);

        setRadius(radius).setStartAngle(startAngle).setEndAngle(endAngle).setCounterClockwise(false);
    }

    protected Slice(JSONObject node)
    {
        super(ShapeType.SLICE, node);
    }

    /**
     * Draws this slice.
     * 
     * @param context
     */
    @Override
    public boolean prepare(Context2D context, Attributes attr, double alpha)
    {
        double start = getStartAngle();

        double end = getEndAngle();

        if (start == end)
        {
            return false;
        }
        boolean drawPacman = true;

        if ((Math.abs(start - end) % (Math.PI * 2)) == 0)
        {
            // full circle
            drawPacman = false;
        }
        context.beginPath();

        context.arc(0, 0, getRadius(), start, end, isCounterClockwise());

        if (drawPacman)
        {
            context.lineTo(0, 0);
        }
        context.closePath();

        return true;
    }

    /**
     * Gets this slice's radius
     * 
     * @return double
     */
    public double getRadius()
    {
        return getAttributes().getRadius();
    }

    /**
     * Sets this slice's radius.
     * 
     * @param radius
     * @return this Slice.
     */
    public Slice setRadius(double radius)
    {
        getAttributes().setRadius(radius);

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
    public Slice setStartAngle(double angle)
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
    public Slice setEndAngle(double angle)
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
    public Slice setCounterClockwise(boolean counterclockwise)
    {
        getAttributes().setCounterClockwise(counterclockwise);

        return this;
    }

    @Override
    public IFactory<?> getFactory()
    {
        return new SliceFactory();
    }

    public static class SliceFactory extends ShapeFactory<Slice>
    {
        public SliceFactory()
        {
            super(ShapeType.SLICE);

            addAttribute(Attribute.RADIUS, true);

            addAttribute(Attribute.START_ANGLE, true);

            addAttribute(Attribute.END_ANGLE, true);

            addAttribute(Attribute.COUNTER_CLOCKWISE);
        }

        @Override
        public Slice create(JSONObject node, ValidationContext ctx)
        {
            return new Slice(node);
        }
    }
}
