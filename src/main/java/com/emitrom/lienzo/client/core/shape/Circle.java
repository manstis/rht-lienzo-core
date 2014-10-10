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
 * Circle with a radius. The center point is set via the X,Y attributes.
 */
public class Circle extends Shape<Circle>
{
    /**
     * Constructor. Creates an instance of a circle.
     * 
     * @param radius
     */
    public Circle(double radius)
    {
        super(ShapeType.CIRCLE);

        setRadius(radius);
    }

    protected Circle(JSONObject node)
    {
        super(ShapeType.CIRCLE, node);
    }

    /**
     * Draws this circle
     * 
     * @param context the {@link Context2D} used to draw this circle. 
     */
    @Override
    public boolean prepare(Context2D context, Attributes attr, double alpha)
    {
        context.beginPath();

        context.arc(0, 0, getRadius(), 0, Math.PI * 2, true);

        context.closePath();

        return true;
    }

    @Override
    protected boolean doStrokeExtraProperties()
    {
        return false;
    }

    /**
     * Sets this circle's radius.
     * 
     * @param radius
     * @return this Circle
     */
    public Circle setRadius(double radius)
    {
        getAttributes().setRadius(radius);

        return this;
    }

    /**
     * Gets this circle's radius.
     * 
     * @return double
     */
    public double getRadius()
    {
        return getAttributes().getRadius();
    }

    @Override
    public IFactory<?> getFactory()
    {
        return new CircleFactory();
    }

    public static class CircleFactory extends ShapeFactory<Circle>
    {
        public CircleFactory()
        {
            super(ShapeType.CIRCLE);

            addAttribute(Attribute.RADIUS, true);
        }

        @Override
        public Circle create(JSONObject node, ValidationContext ctx) throws ValidationException
        {
            return new Circle(node);
        }
    }
}
