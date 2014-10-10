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
import com.emitrom.lienzo.client.core.types.Point2D;
import com.emitrom.lienzo.client.core.types.Point2DArray;
import com.emitrom.lienzo.shared.core.types.ShapeType;
import com.google.gwt.json.client.JSONObject;

/**
 * A polygon is traditionally a plane figure that is bounded by a closed path, 
 * composed of a finite sequence of straight line segments.
 */
public class Polygon extends Shape<Polygon>
{
    /**
     * Constructor. Creates an instance of a polygon.
     * 
     * @param points a {@link Point2DArray} containing 3 or more points
     */
    public Polygon(Point2DArray points)
    {
        super(ShapeType.POLYGON);

        setPoints(points);
    }

    protected Polygon(JSONObject node)
    {
        super(ShapeType.POLYGON, node);
    }

    /**
     * Draws this polygon.
     * 
     * @param context
     */
    @Override
    public boolean prepare(Context2D context, Attributes attr, double alpha)
    {
        Point2DArray list = getPoints();

        if ((null != list) && (list.getLength() > 2))
        {
            Point2D point = list.getPoint(0);

            context.beginPath();

            context.moveTo(point.getX(), point.getY());

            final int leng = list.getLength();

            for (int i = 1; i < leng; i++)
            {
                point = list.getPoint(i);

                context.lineTo(point.getX(), point.getY());
            }
            context.closePath();

            return true;
        }
        return false;
    }

    /**
     * Gets this polygon's points.
     * 
     * @return {@link Point2DArray}
     */
    public Point2DArray getPoints()
    {
        return getAttributes().getPoints();
    }

    /**
     * Sets this polygon's points.
     * 
     * @param points a {@link Point2DArray} of 3 or more points
     * @return this Polygon
     */
    public Polygon setPoints(Point2DArray points)
    {
        getAttributes().setPoints(points);

        return this;
    }

    @Override
    public IFactory<?> getFactory()
    {
        return new PolygonFactory();
    }

    public static class PolygonFactory extends ShapeFactory<Polygon>
    {
        public PolygonFactory()
        {
            super(ShapeType.POLYGON);

            addAttribute(Attribute.POINTS, true);
        }

        @Override
        public Polygon create(JSONObject node, ValidationContext ctx)
        {
            return new Polygon(node);
        }
    }
}
