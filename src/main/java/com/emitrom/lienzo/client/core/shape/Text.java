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
import com.emitrom.lienzo.client.core.Context2D.GradientJSO;
import com.emitrom.lienzo.client.core.LienzoGlobals;
import com.emitrom.lienzo.client.core.shape.json.IFactory;
import com.emitrom.lienzo.client.core.shape.json.ShapeFactory;
import com.emitrom.lienzo.client.core.shape.json.validators.ValidationContext;
import com.emitrom.lienzo.client.core.types.LinearGradient;
import com.emitrom.lienzo.client.core.types.LinearGradient.LinearGradientJSO;
import com.emitrom.lienzo.client.core.types.PatternGradient;
import com.emitrom.lienzo.client.core.types.PatternGradient.PatternGradientJSO;
import com.emitrom.lienzo.client.core.types.RadialGradient;
import com.emitrom.lienzo.client.core.types.RadialGradient.RadialGradientJSO;
import com.emitrom.lienzo.client.core.types.TextMetrics;
import com.emitrom.lienzo.shared.core.types.ShapeType;
import com.emitrom.lienzo.shared.core.types.TextAlign;
import com.emitrom.lienzo.shared.core.types.TextBaseLine;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;

/**
 * Text implementation for Canvas.
 */
public class Text extends Shape<Text>
{
    /**
     * Constructor. Creates an instance of text.
     * 
     * @param text
     */
    public Text(String text)
    {
        super(ShapeType.TEXT);

        LienzoGlobals globals = LienzoGlobals.getInstance();

        if (null == text)
        {
            text = "";
        }
        setText(text).setFontFamily(globals.getDefaultFontFamily()).setFontStyle(globals.getDefaultFontStyle()).setFontSize(globals.getDefaultFontSize());
    }

    /**
     * Constructor. Creates an instance of text.
     * 
     * @param text 
     * @param family font family
     * @param points font size
     */
    public Text(String text, String family, double points)
    {
        super(ShapeType.TEXT);

        LienzoGlobals globals = LienzoGlobals.getInstance();

        if (null == text)
        {
            text = "";
        }
        if ((null == family) || ((family = family.trim()).isEmpty()))
        {
            family = globals.getDefaultFontFamily();
        }
        if (points <= 0)
        {
            points = globals.getDefaultFontSize();
        }
        setText(text).setFontFamily(family).setFontStyle(globals.getDefaultFontStyle()).setFontSize(points);
    }

    /**
     * Constructor. Creates an instance of text.
     * 
     * @param text
     * @param family font family
     * @param style font style (bold, italic, etc)
     * @param points font size
     */
    public Text(String text, String family, String style, double points)
    {
        super(ShapeType.TEXT);

        LienzoGlobals globals = LienzoGlobals.getInstance();

        if (null == text)
        {
            text = "";
        }
        if ((null == family) || ((family = family.trim()).isEmpty()))
        {
            family = globals.getDefaultFontFamily();
        }
        if ((null == style) || ((style = style.trim()).isEmpty()))
        {
            style = globals.getDefaultFontStyle();
        }
        if (points <= 0)
        {
            points = globals.getDefaultFontSize();
        }
        setText(text).setFontFamily(family).setFontStyle(style).setFontSize(points);
    }

    protected Text(JSONObject node)
    {
        super(ShapeType.TEXT, node);
    }

    /**
     * Draws this text
     * 
     * @param context
     */
    @Override
    public boolean prepare(Context2D context, Attributes attr, double alpha)
    {
        String text = getText();

        if ((null == text) || (text.isEmpty()))
        {
            return false;
        }
        if (attr.isDefined(Attribute.TEXT_BASELINE))
        {
            context.setTextBaseline(getTextBaseLine());
        }
        if (attr.isDefined(Attribute.TEXT_ALIGN))
        {
            context.setTextAlign(getTextAlign());
        }
        context.setTextFont(getFontStyle() + " " + getFontSize() + "pt " + getFontFamily());

        return true;
    }

    protected void fill(Context2D context, Attributes attr, double alpha)
    {
        boolean filled = attr.isDefined(Attribute.FILL);

        if ((filled) || (attr.isFillShapeForSelection()))
        {
            if (context.isSelection())
            {
                context.save();

                context.setGlobalAlpha(1);

                Layer layer = getLayer();

                context.getJSO().fillTextWithGradient(getText(), 0, 0, 0, 0, layer.getWidth(), layer.getHeight(), getColorKey());

                context.restore();

                setWasFilledFlag(true);

                return;
            }
            if (false == filled)
            {
                return;
            }
            context.save();

            doApplyShadow(context, attr);

            context.setGlobalAlpha(alpha);

            String fill = attr.getFillColor();

            if (null != fill)
            {
                context.setFillColor(fill);

                context.fillText(getText(), 0, 0);

                setWasFilledFlag(true);
            }
            else
            {
                JavaScriptObject grad = attr.getObject(Attribute.FILL.getProperty());

                if (null != grad)
                {
                    GradientJSO base = grad.cast();

                    if (LinearGradient.TYPE.equals(base.getType()))
                    {
                        context.setFillGradient(new LinearGradient((LinearGradientJSO) base));

                        context.fillText(getText(), 0, 0);

                        setWasFilledFlag(true);
                    }
                    else if (RadialGradient.TYPE.equals(base.getType()))
                    {
                        context.setFillGradient(new RadialGradient((RadialGradientJSO) base));

                        context.fillText(getText(), 0, 0);

                        setWasFilledFlag(true);
                    }
                    else if (PatternGradient.TYPE.equals(base.getType()))
                    {
                        context.setFillGradient(new PatternGradient((PatternGradientJSO) base));

                        context.fillText(getText(), 0, 0);

                        setWasFilledFlag(true);
                    }
                }
            }
            context.restore();
        }
    }

    @Override
    protected void stroke(Context2D context, Attributes attr, double alpha)
    {
        context.save();

        if (setStrokeParams(context, attr, alpha))
        {
            if (context.isSelection())
            {
                context.beginPath();

                context.strokeText(getText(), 0, 0);

                context.closePath();
            }
            else
            {
                doApplyShadow(context, attr);

                context.beginPath();

                context.strokeText(getText(), 0, 0);

                context.closePath();
            }
        }
        context.restore();
    }

    /**
     * Returns TextMetrics, which includes an approximate value for
     * height. As close as we can estimate it at this time.
     * 
     * @param context
     * @return TextMetric or null if the text is empty or null
     */
    public TextMetrics measure(Context2D context)
    {
        TextMetrics size = null;

        String text = getText();

        if ((null == text) || (text.isEmpty()))
        {
            return size;
        }
        context.save();

        context.setTextAlign(TextAlign.LEFT);

        context.setTextBaseline(TextBaseLine.ALPHABETIC);

        context.setTextFont(getFontStyle() + " " + getFontSize() + "pt " + getFontFamily());

        double width = getStrokeWidth();

        if (width == 0)
        {
            width = 1;
        }
        context.setStrokeWidth(width);

        context.transform(getAbsoluteTransform());

        size = context.measureText(text);

        double height = context.measureText("M").getWidth();

        size.setHeight(height - height / 6);

        context.restore();

        return size;
    }

    /**
     * Returns the {@link Text} String
     * 
     * @return String
     */
    public String getText()
    {
        return getAttributes().getText();
    }

    /**
     * Sets the {@link Text} String
     * 
     * @return this Text
     */
    public Text setText(String text)
    {
        getAttributes().setText(text);

        return this;
    }

    /**
     * Returns the Font Family.
     * 
     * @return String
     */
    public String getFontFamily()
    {
        return getAttributes().getFontFamily();
    }

    /**
     * Sets the {@link Text} Font Family
     * 
     * @return this Text
     */
    public Text setFontFamily(String family)
    {
        getAttributes().setFontFamily(family);

        return this;
    }

    /**
     * Returns the Font Style.
     * 
     * @return String
     */
    public String getFontStyle()
    {
        return getAttributes().getFontStyle();
    }

    /**
     * Sets the Font Style.
     * 
     * @param style
     * @return this Text
     */
    public Text setFontStyle(String style)
    {
        getAttributes().setFontStyle(style);

        return this;
    }

    /**
     * Returns the Font Size.
     * 
     * @return double
     */
    public double getFontSize()
    {
        return getAttributes().getFontSize();
    }

    /**
     * Sets the Font Size.
     * 
     * @param size
     * @return this Text
     */
    public Text setFontSize(double size)
    {
        getAttributes().setFontSize(size);

        return this;
    }

    /**
     * Returns the {@link TextAlign}
     * 
     * @return {@link TextAlign}
     */
    public TextAlign getTextAlign()
    {
        return getAttributes().getTextAlign();
    }

    /**
     * Sets the {@link TextAlign}
     * 
     * @param align
     * @return this Text
     */
    public Text setTextAlign(TextAlign align)
    {
        getAttributes().setTextAlign(align);

        return this;
    }

    /**
     * Returns the {@link TextBaseLine}
     * 
     * @return {@link TextBaseLine}
     */
    public TextBaseLine getTextBaseLine()
    {
        return getAttributes().getTextBaseLine();
    }

    /**
     * Sets the {@link TextBaseLine}
     * 
     * @param baseline
     * @return this Text
     */
    public Text setTextBaseLine(TextBaseLine baseline)
    {
        getAttributes().setTextBaseLine(baseline);

        return this;
    }

    @Override
    public IFactory<?> getFactory()
    {
        return new TextFactory();
    }

    public static class TextFactory extends ShapeFactory<Text>
    {
        public TextFactory()
        {
            super(ShapeType.TEXT);

            addAttribute(Attribute.TEXT, true);

            addAttribute(Attribute.FONT_SIZE);

            addAttribute(Attribute.FONT_STYLE);

            addAttribute(Attribute.FONT_FAMILY);

            addAttribute(Attribute.TEXT_ALIGN);

            addAttribute(Attribute.TEXT_BASELINE);
        }

        @Override
        public Text create(JSONObject node, ValidationContext ctx)
        {
            return new Text(node);
        }
    }
}
