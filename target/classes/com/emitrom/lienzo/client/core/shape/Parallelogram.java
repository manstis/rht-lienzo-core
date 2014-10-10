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
 * Parallelogram defined by a width, a height and a skew factor.
 * A skew of 0 draws sides that form a 90 degree angle.
 */
public class Parallelogram extends Shape<Parallelogram>
{
    /**
     * Constructor. Creates an instance of a parallelogram.
     * 
     * @param width
     * @param height
     * @param skew a skew of 0 draws sides that form a 90 degree angle
     */
    public Parallelogram(double width, double height, double skew)
    {
        super(ShapeType.PARALLELOGRAM);

        setWidth(width).setHeight(height).setSkew(skew);
    }

    protected Parallelogram(JSONObject node, ValidationContext ctx) throws ValidationException
    {
        super(ShapeType.PARALLELOGRAM, node, ctx);
    }

    /**
     * Draws this parallelogram.
     * 
     * @param context
     */
    @Override
    public boolean prepare(Context2D context, Attributes attr, double alpha)
    {
        final double wide = getWidth();

        final double high = getHeight();

        if ((wide > 0) && (high > 0))
        {
            final double skew = getSkew();
            
            context.beginPath();

            if (skew > 0)
            {
                context.moveTo(skew, 0);

                context.lineTo(wide, 0);

                context.lineTo(wide - skew, high);

                context.lineTo(0, high);
            }
            else if (skew < 0)
            {
                context.moveTo(0, 0);

                context.lineTo(wide - Math.abs(skew), 0);

                context.lineTo(wide, high);

                context.lineTo(Math.abs(skew), high);
            }
            else
            {
                context.rect(0, 0, wide, high);
            }
            context.closePath();

            return true;
        }
        return false;
    }

    /**
     * Gets the width of this parallelogram
     * 
     * @return double
     */
    public double getWidth()
    {
        return getAttributes().getWidth();
    }

    /**
     * Sets the width of this parallelogram
     * 
     * @param width
     * @return this Parallelogram
     */
    public Parallelogram setWidth(double width)
    {
        getAttributes().setWidth(width);

        return this;
    }

    /**
     * Gets the height of this parallelogram
     * 
     * @return double
     */
    public double getHeight()
    {
        return getAttributes().getHeight();
    }

    /**
     * Sets the height of this parallelogram
     * 
     * @param height
     * @return this Parallelogram
     */
    public Parallelogram setHeight(double height)
    {
        getAttributes().setHeight(height);

        return this;
    }

    /**
     * Gets the skew of this parallelogram.
     * 
     * @return double
     */
    public double getSkew()
    {
        return getAttributes().getSkew();
    }

    /**
     * Sets the skew of this parallelogram
     * 
     * @param skew
     * @return this Parallelogram
     */
    public Parallelogram setSkew(double skew)
    {
        getAttributes().setSkew(skew);

        return this;
    }

    @Override
    public IFactory<Parallelogram> getFactory()
    {
        return new ParallelogramFactory();
    }

    public static class ParallelogramFactory extends ShapeFactory<Parallelogram>
    {
        public ParallelogramFactory()
        {
            super(ShapeType.PARALLELOGRAM);

            addAttribute(Attribute.WIDTH, true);

            addAttribute(Attribute.HEIGHT, true);

            addAttribute(Attribute.SKEW, true);
        }

        @Override
        public Parallelogram create(JSONObject node, ValidationContext ctx) throws ValidationException
        {
            return new Parallelogram(node, ctx);
        }
    }
}
