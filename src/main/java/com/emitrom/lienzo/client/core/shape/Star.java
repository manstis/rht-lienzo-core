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
 * Star is defined by an inner radius, an outer radius and the number of points.
 * The center points is at (0,0) unless additional attributes are set.
 */
public class Star extends Shape<Star>
{
    /**
     * Constructor. Creates an instance of a star.  Visually, there is an enclosing
     * circle which all the tips of the star touch, and an inner circle where all the
     * vertices of the star's arms touch.  The distance between the inner and the outer
     * circle define how long the star's arms are.
     * 
     * @param points number of points in this star.
     * @param innerRadius radius of the inner circle.
     * @param outerRadius radius of the enclosing circle.
     */
    public Star(int points, double innerRadius, double outerRadius)
    {
        super(ShapeType.STAR);

        setStarPoints(points).setInnerRadius(innerRadius).setOuterRadius(outerRadius);
    }

    protected Star(JSONObject node)
    {
        super(ShapeType.STAR, node);
    }

    /**
     * Draws this star.
     * 
     * @param context
     */
    @Override
    public boolean prepare(Context2D context, Attributes attr, double alpha)
    {
        final int s = getStarPoints();

        final double ir = getInnerRadius();

        final double or = getOuterRadius();

        if ((s > 4) && (ir != 0) && (or != 0) && (or > ir))
        {
            context.beginPath();

            context.moveTo(0, 0 - or);

            for (int n = 1; n < s * 2; n++)
            {
                double radius = n % 2 == 0 ? or : ir;

                context.lineTo(radius * Math.sin(n * Math.PI / s), -1 * radius * Math.cos(n * Math.PI / s));
            }
            context.closePath();

            return true;
        }
        return false;
    }

    /**
     * Returns the number of Stars points.
     * 
     * @return int
     */
    public int getStarPoints()
    {
        return getAttributes().getStarPoints();
    }

    /**
     * Sets the number of Star points.
     * 
     * If the value passed is less than 5, it will be replaced by 5.
     * 
     * @param points
     * @return this Star
     */
    public Star setStarPoints(int points)
    {
        getAttributes().setStarPoints(points);

        return this;
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
    public Star setInnerRadius(double radius)
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
    public Star setOuterRadius(double radius)
    {
        getAttributes().setOuterRadius(radius);

        return this;
    }

    @Override
    public IFactory<?> getFactory()
    {
        return new StarFactory();
    }

    public static class StarFactory extends ShapeFactory<Star>
    {
        public StarFactory()
        {
            super(ShapeType.STAR);

            addAttribute(Attribute.STAR_POINTS);

            addAttribute(Attribute.INNER_RADIUS);

            addAttribute(Attribute.OUTER_RADIUS);
        }

        @Override
        public Star create(JSONObject node, ValidationContext ctx)
        {
            return new Star(node);
        }
    }
}
