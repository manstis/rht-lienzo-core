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
 * Arcs are defined by a center point, a radius, a starting angle, an ending angle, and the drawing direction (either clockwise or counterclockwise).
 */
public class Arc extends Shape<Arc>
{
    /**
     * Constructor. Creates an instance of an arc.
     * 
     * @param radius radius of the circle
     * @param startAngle starting angle (in radians) of this arc
     * @param endAngle end angle (in radians) of this arc
     * @param counterClockwise 
     *            direction in which the arc is drawn.  True draws the arc counter clockwise;
     *            false draws the arc clockwise.
     *          
     */
    public Arc(double radius, double startAngle, double endAngle, boolean counterClockwise)
    {
        super(ShapeType.ARC);

        setRadius(radius).setStartAngle(startAngle).setEndAngle(endAngle).setCounterClockwise(counterClockwise);
    }

    /**
     * Constructor. Creates an instance of an arc, drawn clockwise.
     * 
     * @param radius radius of the circle
     * @param startAngle starting angle (in radians) of this arc
     * @param endAngle end angle (in radians) of this arc
     */
    public Arc(double radius, double startAngle, double endAngle)
    {
        super(ShapeType.ARC);

        setRadius(radius).setStartAngle(startAngle).setEndAngle(endAngle).setCounterClockwise(false);
    }

    protected Arc(JSONObject node)
    {
        super(ShapeType.ARC, node);
    }

    /**
     * Draws this arc.
     * 
     * @param context the {@link Context2D} used to draw this arc.
     */
    @Override
    public boolean prepare(Context2D context, Attributes attr, double alpha)
    {
        context.beginPath();

        context.arc(0, 0, getRadius(), getStartAngle(), getEndAngle(), isCounterClockwise());

        return true;
    }

    /**
     * Gets this arc's radius.
     * 
     * @return double
     */
    public double getRadius()
    {
        return getAttributes().getRadius();
    }

    /**
     * Sets this arc's radius.
     * 
     * @param radius
     * @return this Arc
     */
    public Arc setRadius(double radius)
    {
        getAttributes().setRadius(radius);

        return this;
    }

    /**
     * Gets the starting angle of this arc.
     * 
     * @return double (in radians)
     */
    public double getStartAngle()
    {
        return getAttributes().getStartAngle();
    }

    /**
     * Sets the starting angle of this arc.
     * 
     * @param angle (in radians)
     * @return this Arc
     */
    public Arc setStartAngle(double angle)
    {
        getAttributes().setStartAngle(angle);

        return this;
    }

    /**
     * Gets the end angle of this arc.
     * 
     * @return double (in radians)
     */
    public double getEndAngle()
    {
        return getAttributes().getEndAngle();
    }

    /**
     * Sets the end angle of this arc.
     * 
     * @param angle (in radians)
     * @return this Arc
     */
    public Arc setEndAngle(double angle)
    {
        getAttributes().setEndAngle(angle);

        return this;
    }

    /**
     * Returns whether the drawing direction of this arc is counter clockwise.
     * 
     * @return boolean
     */
    public boolean isCounterClockwise()
    {
        return getAttributes().isCounterClockwise();
    }

    /**
     * Sets the drawing direction for this arc.
     * 
     * @param counterClockwise If true, it's drawn counter clockwise.
     * @return this Arc
     */
    public Arc setCounterClockwise(boolean counterClockwise)
    {
        getAttributes().setCounterClockwise(counterClockwise);

        return this;
    }

    @Override
    public IFactory<?> getFactory()
    {
        return new ArcFactory();
    }

    public static class ArcFactory extends ShapeFactory<Arc>
    {
        public ArcFactory()
        {
            super(ShapeType.ARC);

            addAttribute(Attribute.RADIUS, true);

            addAttribute(Attribute.START_ANGLE, true);

            addAttribute(Attribute.END_ANGLE, true);

            addAttribute(Attribute.COUNTER_CLOCKWISE);
        }

        @Override
        public Arc create(JSONObject node, ValidationContext ctx)
        {
            return new Arc(node);
        }
    }
}
