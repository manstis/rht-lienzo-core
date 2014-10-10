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

public class IsoscelesTrapezoid extends Shape<IsoscelesTrapezoid>
{
    public IsoscelesTrapezoid(double topwidth, double bottomwidth, double height)
    {
        super(ShapeType.ISOSCELES_TRAPEZOID);

        setTopWidth(topwidth).setBottomWidth(bottomwidth).setHeight(height);
    }

    protected IsoscelesTrapezoid(JSONObject node)
    {
        super(ShapeType.ISOSCELES_TRAPEZOID, node);
    }

    @Override
    protected boolean prepare(Context2D context, Attributes attr, double alpha)
    {
        final double hig = getHeight();

        final double top = getTopWidth();

        final double bot = getBottomWidth();

        final double sub = Math.abs(top - bot);

        context.beginPath();

        if (0 == sub)
        {
            context.rect(0, 0, top, hig);
        }
        else
        {
            if (top > bot)
            {
                context.moveTo(0, 0);

                context.lineTo(top, 0);

                context.lineTo((sub / 2.0) + bot, hig);

                context.lineTo((sub / 2.0), hig);
            }
            else
            {
                context.moveTo((sub / 2.0), 0);

                context.lineTo((sub / 2.0) + top, 0);

                context.lineTo(bot, hig);

                context.lineTo(0, hig);
            }
        }
        context.closePath();

        return true;
    }

    public IsoscelesTrapezoid setTopWidth(double topwidth)
    {
        getAttributes().setTopWidth(topwidth);

        return this;
    }

    public double getTopWidth()
    {
        return getAttributes().getTopWidth();
    }

    public IsoscelesTrapezoid setBottomWidth(double bottomwidth)
    {
        getAttributes().setBottomWidth(bottomwidth);

        return this;
    }

    public double getBottomWidth()
    {
        return getAttributes().getBottomWidth();
    }

    public IsoscelesTrapezoid setHeight(double height)
    {
        getAttributes().setHeight(height);

        return this;
    }

    public double getHeight()
    {
        return getAttributes().getHeight();
    }

    @Override
    public IFactory<?> getFactory()
    {
        return new IsoscelesTrapezoidFactory();
    }

    public static class IsoscelesTrapezoidFactory extends ShapeFactory<IsoscelesTrapezoid>
    {
        public IsoscelesTrapezoidFactory()
        {
            super(ShapeType.ISOSCELES_TRAPEZOID);

            addAttribute(Attribute.TOP_WIDTH, true);

            addAttribute(Attribute.BOTTOM_WIDTH, true);

            addAttribute(Attribute.HEIGHT, true);
        }

        @Override
        public IsoscelesTrapezoid create(JSONObject node, ValidationContext ctx)
        {
            return new IsoscelesTrapezoid(node);
        }
    }
}