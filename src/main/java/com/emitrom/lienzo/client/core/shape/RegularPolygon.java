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
 * In Euclidean geometry, a regular polygon is a polygon that is equiangular (all angles are equal in measure) 
 * and equilateral (all sides have the same length).  All regular polygons fit perfectly inside a circle.
 */
public class RegularPolygon extends Shape<RegularPolygon>
{
    /**
     * Constructor. Creates an instance of a regular polygon.
     * 
     * @param sides number of sides
     * @param radius size of the encompassing circle
     */
    public RegularPolygon(int sides, double radius)
    {
        super(ShapeType.REGULAR_POLYGON);

        setRadius(radius).setSides(sides);
    }

    protected RegularPolygon(JSONObject node)
    {
        super(ShapeType.REGULAR_POLYGON, node);
    }

    /**
     * Draws this regular polygon
     * 
     * @context
     */
    @Override
    public boolean prepare(Context2D context, Attributes attr, double alpha)
    {
        final int s = getSides();

        final double r = getRadius();

        if ((s > 2) && (r > 0))
        {
            context.beginPath();

            context.moveTo(0, 0 - r);

            for (int n = 1; n < s; n++)
            {
                context.lineTo(r * Math.sin(n * 2 * Math.PI / s), -1 * r * Math.cos(n * 2 * Math.PI / s));
            }
            context.closePath();

            return true;
        }
        return false;
    }

    /**
     * Gets this regular polygon's encompassing circle's radius.
     * 
     * @return double
     */
    public double getRadius()
    {
        return getAttributes().getRadius();
    }

    /**
     * Sets the size of this regular polygon, expressed by the radius of the enclosing circle.
     * 
     * @param radius
     * @return this RegularPolygon
     */
    public RegularPolygon setRadius(double radius)
    {
        getAttributes().setRadius(radius);

        return this;
    }

    /**
     * Gets the number of sides this regular polygon has.
     * 
     * @return int
     */
    public int getSides()
    {
        return getAttributes().getSides();
    }

    /**
     * Sets the number of sides this regular polygon has.
     * 
     * @param sides
     * @return this RegularPolygon
     */
    public RegularPolygon setSides(int sides)
    {
        getAttributes().setSides(sides);

        return this;
    }

    @Override
    public IFactory<?> getFactory()
    {
        return new RegularPolygonFactory();
    }

    public static class RegularPolygonFactory extends ShapeFactory<RegularPolygon>
    {
        public RegularPolygonFactory()
        {
            super(ShapeType.REGULAR_POLYGON);

            addAttribute(Attribute.RADIUS);

            addAttribute(Attribute.SIDES);
        }

        @Override
        public RegularPolygon create(JSONObject node, ValidationContext ctx)
        {
            return new RegularPolygon(node);
        }
    }
}
