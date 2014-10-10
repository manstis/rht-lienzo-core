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
 * Rectangle is defined by a width and a height.
 * It may have rounded corners.
 */
public class Rectangle extends Shape<Rectangle>
{
    /**
     * Constructor. Creates an instance of a rectangle.
     * 
     * @param width
     * @param height
     */
    public Rectangle(double width, double height)
    {
        super(ShapeType.RECTANGLE);

        setWidth(width).setHeight(height);
    }

    /**
     * Constructor. Creates an instance of rectangle with rounded corners. 
     * 
     * @param width
     * @param height
     * @param cornerRadius
     */
    public Rectangle(double width, double height, double cornerRadius)
    {
        super(ShapeType.RECTANGLE);

        setWidth(width).setHeight(height).setCornerRadius(cornerRadius);
    }

    protected Rectangle(JSONObject node)
    {
        super(ShapeType.RECTANGLE, node);
    }

    /**
     * Draws this rectangle.
     * 
     * @param context
     */
    @Override
    public boolean prepare(Context2D context, Attributes attr, double alpha)
    {
        context.beginPath();

        double r = getCornerRadius();

        if (r != 0)
        {
            double w = getWidth();

            double h = getHeight();

            context.moveTo(r, 0);

            context.lineTo(w - r, 0);

            context.arc(w - r, r, r, Math.PI * 3 / 2, 0, false);

            context.lineTo(w, h - r);

            context.arc(w - r, h - r, r, 0, Math.PI / 2, false);

            context.lineTo(r, h);

            context.arc(r, h - r, r, Math.PI / 2, Math.PI, false);

            context.lineTo(0, r);

            context.arc(r, r, r, Math.PI, Math.PI * 3 / 2, false);
        }
        else
        {
            context.rect(0, 0, attr.getWidth(), attr.getHeight());
        }
        context.closePath();

        return true;
    }

    /**
     * Gets the width of this rectangle.
     * 
     * @return double
     */
    public double getWidth()
    {
        return getAttributes().getWidth();
    }

    /**
     * Gets the height of this rectangle.
     * 
     * @param width
     * @return this Rectangle
     */
    public Rectangle setWidth(double width)
    {
        getAttributes().setWidth(width);

        return this;
    }

    /**
     * Gets the height of this rectangle.
     * 
     * @return double
     */
    public double getHeight()
    {
        return getAttributes().getHeight();
    }

    /**
     * Sets the height of this rectangle.
     * 
     * @param height
     * @return this Rectangle
     */
    public Rectangle setHeight(double height)
    {
        getAttributes().setHeight(height);

        return this;
    }

    /**
     * Gets the corner radius, if this rectangle has rounded corners.
     * 
     * @return double the value returned is 0 if the rectangle has no rounded corners.
     */
    public double getCornerRadius()
    {
        return getAttributes().getCornerRadius();
    }

    /**
     * Sets the radius for this rectangle's rounded corners
     *  
     * @param radius
     * @return this Rectangle
     */
    public Rectangle setCornerRadius(double radius)
    {
        getAttributes().setCornerRadius(radius);

        return this;
    }

    @Override
    public IFactory<?> getFactory()
    {
        return new RectangleFactory();
    }

    public static class RectangleFactory extends ShapeFactory<Rectangle>
    {
        public RectangleFactory()
        {
            super(ShapeType.RECTANGLE);

            addAttribute(Attribute.WIDTH, true);

            addAttribute(Attribute.HEIGHT, true);

            addAttribute(Attribute.CORNER_RADIUS);
        }

        @Override
        public Rectangle create(JSONObject node, ValidationContext ctx)
        {
            return new Rectangle(node);
        }
    }
}
