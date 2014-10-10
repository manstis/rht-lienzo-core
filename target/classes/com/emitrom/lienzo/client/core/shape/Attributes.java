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

import java.util.ArrayList;
import java.util.Collection;

import com.emitrom.lienzo.client.core.Attribute;
import com.emitrom.lienzo.client.core.LienzoGlobals;
import com.emitrom.lienzo.client.core.types.DashArray;
import com.emitrom.lienzo.client.core.types.DashArray.DashArrayJSO;
import com.emitrom.lienzo.client.core.types.DragBounds;
import com.emitrom.lienzo.client.core.types.DragBounds.DragBoundsJSO;
import com.emitrom.lienzo.client.core.types.FillGradient;
import com.emitrom.lienzo.client.core.types.LinearGradient;
import com.emitrom.lienzo.client.core.types.LinearGradient.LinearGradientJSO;
import com.emitrom.lienzo.client.core.types.NativeInternalType;
import com.emitrom.lienzo.client.core.types.PatternGradient;
import com.emitrom.lienzo.client.core.types.PatternGradient.PatternGradientJSO;
import com.emitrom.lienzo.client.core.types.Point2D;
import com.emitrom.lienzo.client.core.types.Point2D.Point2DJSO;
import com.emitrom.lienzo.client.core.types.Point2DArray;
import com.emitrom.lienzo.client.core.types.RadialGradient;
import com.emitrom.lienzo.client.core.types.RadialGradient.RadialGradientJSO;
import com.emitrom.lienzo.client.core.types.Shadow;
import com.emitrom.lienzo.client.core.types.Shadow.ShadowJSO;
import com.emitrom.lienzo.client.core.types.Transform;
import com.emitrom.lienzo.client.core.types.Transform.TransformJSO;
import com.emitrom.lienzo.shared.core.types.ArrowType;
import com.emitrom.lienzo.shared.core.types.DragConstraint;
import com.emitrom.lienzo.shared.core.types.DragMode;
import com.emitrom.lienzo.shared.core.types.LineCap;
import com.emitrom.lienzo.shared.core.types.LineJoin;
import com.emitrom.lienzo.shared.core.types.ImageSelectionMode;
import com.emitrom.lienzo.shared.core.types.ImageSerializationMode;
import com.emitrom.lienzo.shared.core.types.TextAlign;
import com.emitrom.lienzo.shared.core.types.TextBaseLine;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayMixed;

public class Attributes extends JavaScriptObject
{
    protected Attributes()
    {

    }

    public static final native Attributes make()
    /*-{
		return {};
    }-*/;

    public final boolean isClearLayerBeforeDraw()
    {
        if (isDefined(Attribute.CLEAR_LAYER_BEFORE_DRAW))
        {
            return getBoolean(Attribute.CLEAR_LAYER_BEFORE_DRAW.getProperty());
        }
        return true;
    }

    public final void setClearLayerBeforeDraw(boolean clear)
    {
        put(Attribute.CLEAR_LAYER_BEFORE_DRAW.getProperty(), clear);
    }

    public final boolean isTransformable()
    {
        if (isDefined(Attribute.TRANSFORMABLE))
        {
            return getBoolean(Attribute.TRANSFORMABLE.getProperty());
        }
        return true;
    }

    public final void setTransformable(boolean transformable)
    {
        put(Attribute.TRANSFORMABLE.getProperty(), transformable);
    }

    public final void setFillColor(String fill)
    {
        if (null != fill && !fill.isEmpty())
        {
            put(Attribute.FILL.getProperty(), fill);
        }
        else
        {
            delete(Attribute.FILL.getProperty());
        }
    }

    public final String getFillColor()
    {
        String fill = getString(Attribute.FILL.getProperty());

        if ((null != fill) && (false == (fill = fill.trim()).isEmpty()))
        {
            return fill;
        }
        return fill;
    }

    public final void setFillGradient(LinearGradient gradient)
    {
        if (null != gradient)
        {
            put(Attribute.FILL.getProperty(), gradient.getJSO());
        }
        else
        {
            delete(Attribute.FILL.getProperty());
        }
    }

    public final void setFillGradient(RadialGradient gradient)
    {
        if (null != gradient)
        {
            put(Attribute.FILL.getProperty(), gradient.getJSO());
        }
        else
        {
            delete(Attribute.FILL.getProperty());
        }
    }

    public final void setFillGradient(PatternGradient gradient)
    {
        if (null != gradient)
        {
            put(Attribute.FILL.getProperty(), gradient.getJSO());
        }
        else
        {
            delete(Attribute.FILL.getProperty());
        }
    }

    public final FillGradient getFillGradient()
    {
        JavaScriptObject obj = getObject(Attribute.FILL.getProperty());

        if (null == obj)
        {
            return null;
        }
        String type = getString("type", obj);

        if (LinearGradient.TYPE.equals(type))
        {
            return new LinearGradient((LinearGradientJSO) obj);
        }
        else if (RadialGradient.TYPE.equals(type))
        {
            return new RadialGradient((RadialGradientJSO) obj);
        }
        else if (PatternGradient.TYPE.equals(type))
        {
            return new PatternGradient((PatternGradientJSO) obj);
        }
        return null;
    }

    public final void setStrokeColor(String stroke)
    {
        if (null != stroke && !stroke.isEmpty())
        {
            put(Attribute.STROKE.getProperty(), stroke);
        }
        else
        {
            delete(Attribute.STROKE.getProperty());
        }
    }

    public final String getStrokeColor()
    {
        return getString(Attribute.STROKE.getProperty());
    }

    public final void setLineCap(LineCap lineCap)
    {
        if (null != lineCap)
        {
            put(Attribute.LINE_CAP.getProperty(), lineCap.getValue());
        }
        else
        {
            delete(Attribute.LINE_CAP.getProperty());
        }
    }

    public final LineCap getLineCap()
    {
        return LineCap.lookup(getString(Attribute.LINE_CAP.getProperty()));
    }

    public final void setLineJoin(LineJoin lineJoin)
    {
        if (null != lineJoin)
        {
            put(Attribute.LINE_JOIN.getProperty(), lineJoin.getValue());
        }
        else
        {
            delete(Attribute.LINE_JOIN.getProperty());
        }
    }

    public final LineJoin getLineJoin()
    {
        return LineJoin.lookup(getString(Attribute.LINE_JOIN.getProperty()));
    }

    public final void setMiterLimit(double limit)
    {
        put(Attribute.MITER_LIMIT.getProperty(), limit);
    }

    public final double getMiterLimit()
    {
        if (isDefined(Attribute.MITER_LIMIT))
        {
            return getDouble(Attribute.MITER_LIMIT.getProperty());
        }
        return 10;
    }

    public final void setStrokeWidth(double width)
    {
        put(Attribute.STROKE_WIDTH.getProperty(), width);
    }

    public final double getStrokeWidth()
    {
        return getDouble(Attribute.STROKE_WIDTH.getProperty());
    }

    public final void setX(double x)
    {
        put(Attribute.X.getProperty(), x);
    }

    public final void setY(double y)
    {
        put(Attribute.Y.getProperty(), y);
    }

    public final void setVisible(boolean visible)
    {
        put(Attribute.VISIBLE.getProperty(), visible);
    }

    public final boolean isVisible()
    {
        if (isDefined(Attribute.VISIBLE))
        {
            return getBoolean(Attribute.VISIBLE.getProperty());
        }
        return true;
    }

    public final void setDraggable(boolean draggable)
    {
        put(Attribute.DRAGGABLE.getProperty(), draggable);
    }

    public final boolean isDraggable()
    {
        return getBoolean(Attribute.DRAGGABLE.getProperty());
    }

    public final void setFillShapeForSelection(boolean selection)
    {
        put(Attribute.FILL_SHAPE_FOR_SELECTION.getProperty(), selection);
    }

    public final boolean isFillShapeForSelection()
    {
        if (isDefined(Attribute.FILL_SHAPE_FOR_SELECTION))
        {
            return getBoolean(Attribute.FILL_SHAPE_FOR_SELECTION.getProperty());
        }
        return LienzoGlobals.get().getDefaultFillShapeForSelection();
    }

    public final void setListening(boolean listening)
    {
        put(Attribute.LISTENING.getProperty(), listening);
    }

    public final boolean isListening()
    {
        if (isDefined(Attribute.LISTENING))
        {
            return getBoolean(Attribute.LISTENING.getProperty());
        }
        return true;
    }

    public final void setName(String name)
    {
        if (null != name)
        {
            put(Attribute.NAME.getProperty(), name);
        }
        else
        {
            delete(Attribute.NAME.getProperty());
        }
    }

    public final void setDashArray(DashArray array)
    {
        if (null != array)
        {
            put(Attribute.DASH_ARRAY.getProperty(), array.getJSO());
        }
        else
        {
            delete(Attribute.DASH_ARRAY.getProperty());
        }
    }

    public final DashArray getDashArray()
    {
        JsArrayMixed dash = getArray(Attribute.DASH_ARRAY.getProperty());

        if (null != dash)
        {
            DashArrayJSO djso = dash.cast();

            return new DashArray(djso);
        }
        return new DashArray();
    }

    public final void setDragConstraint(DragConstraint constraint)
    {
        if (null != constraint)
        {
            put(Attribute.DRAG_CONSTRAINT.getProperty(), constraint.getValue());
        }
        else
        {
            delete(Attribute.DRAG_CONSTRAINT.getProperty());
        }
    }

    public final DragConstraint getDragConstraint()
    {
        return DragConstraint.lookup(getString(Attribute.DRAG_CONSTRAINT.getProperty()));
    }

    public final String getName()
    {
        return getString(Attribute.NAME.getProperty());
    }

    public final void setID(String id)
    {
        if (null != id)
        {
            put(Attribute.ID.getProperty(), id);
        }
        else
        {
            delete(Attribute.ID.getProperty());
        }
    }

    public final String getID()
    {
        return getString(Attribute.ID.getProperty());
    }

    public final void setRotation(double radians)
    {
        put(Attribute.ROTATION.getProperty(), radians);
    }

    public final double getRotation()
    {
        return getDouble(Attribute.ROTATION.getProperty());
    }

    public final void setRotationDegrees(double degrees)
    {
        put(Attribute.ROTATION.getProperty(), degrees * Math.PI / 180);
    }

    public final double getRotationDegrees()
    {
        return getDouble(Attribute.ROTATION.getProperty()) * 180 / Math.PI;
    }

    public final void setRadius(double radius)
    {
        put(Attribute.RADIUS.getProperty(), radius);
    }

    public final void setCornerRadius(double cornerRadius)
    {
        put(Attribute.CORNER_RADIUS.getProperty(), cornerRadius);
    }

    public final void setAlpha(double alpha)
    {
        if (alpha < 0)
        {
            alpha = 0;
        }
        if (alpha > 1)
        {
            alpha = 1;
        }
        put(Attribute.ALPHA.getProperty(), alpha);
    }

    public final void setScale(Point2D scale)
    {
        if (null != scale)
        {
            put(Attribute.SCALE.getProperty(), scale.getJSO());
        }
        else
        {
            delete(Attribute.SCALE.getProperty());
        }
    }

    public final void setScale(double scalex, double scaley)
    {
        setScale(new Point2D(scalex, scaley));
    }

    public final void setScale(double value)
    {
        setScale(new Point2D(value, value));
    }

    public final Point2D getScale()
    {
        JavaScriptObject scale = getObject(Attribute.SCALE.getProperty());

        if (null != scale)
        {
            Point2DJSO pjso = scale.cast();

            return new Point2D(pjso);
        }
        return null;
    }

    public final void setShear(double shearX, double shearY)
    {
        setShear(new Point2D(shearX, shearY));
    }

    public final void setShear(Point2D shear)
    {
        if (null != shear)
        {
            put(Attribute.SHEAR.getProperty(), shear.getJSO());
        }
        else
        {
            delete(Attribute.SHEAR.getProperty());
        }
    }

    public final Point2D getShear()
    {
        JavaScriptObject shear = getObject(Attribute.SHEAR.getProperty());

        if (null != shear)
        {
            Point2DJSO pjso = shear.cast();

            return new Point2D(pjso);
        }
        return null;
    }

    public final void setOffset(Point2D offset)
    {
        if (null != offset)
        {
            put(Attribute.OFFSET.getProperty(), offset.getJSO());
        }
        else
        {
            delete(Attribute.OFFSET.getProperty());
        }
    }

    public final void setOffset(double x, double y)
    {
        setOffset(new Point2D(x, y));
    }

    public final Point2D getOffset()
    {
        JavaScriptObject offset = getObject(Attribute.OFFSET.getProperty());

        if (null != offset)
        {
            Point2DJSO pjso = offset.cast();

            return new Point2D(pjso);
        }
        return null;
    }

    public final void setTransform(Transform transform)
    {
        if (null != transform)
        {
            put(Attribute.TRANSFORM.getProperty(), transform.getJSO());
        }
        else
        {
            delete(Attribute.TRANSFORM.getProperty());
        }
    }

    public final Transform getTransform()
    {
        JavaScriptObject xrfm = getArray(Attribute.TRANSFORM.getProperty());

        if (null != xrfm)
        {
            TransformJSO pjso = xrfm.cast();

            return new Transform(pjso);
        }
        return null;
    }

    public final void setWidth(double width)
    {
        put(Attribute.WIDTH.getProperty(), width);
    }

    public final void setHeight(double height)
    {
        put(Attribute.HEIGHT.getProperty(), height);
    }

    public final void setPoints(Point2DArray points)
    {
        if (null != points)
        {
            put(Attribute.POINTS.getProperty(), points.getJSO());
        }
        else
        {
            delete(Attribute.POINTS.getProperty());
        }
    }

    public final Point2DArray getPoints()
    {
        JsArray<JavaScriptObject> points = getArrayOfJSO(Attribute.POINTS.getProperty());

        if (null != points)
        {
            return new Point2DArray(points);
        }
        return new Point2DArray();
    }

    public final void setStarPoints(int points)
    {
        if (points < 5)
        {
            points = 5;
        }
        put(Attribute.STAR_POINTS.getProperty(), points);
    }

    public final void setText(String text)
    {
        if (null == text)
        {
            text = "";
        }
        put(Attribute.TEXT.getProperty(), text);
    }

    public final String getText()
    {
        String text = getString(Attribute.TEXT.getProperty());

        if (null == text)
        {
            text = "";
        }
        return text;
    }

    public final void setFontSize(double points)
    {
        if (points <= 0.0)
        {
            points = LienzoGlobals.get().getDefaultFontSize();
        }
        put(Attribute.FONT_SIZE.getProperty(), points);
    }

    public final double getFontSize()
    {
        double points = getDouble(Attribute.FONT_SIZE.getProperty());

        if (points <= 0.0)
        {
            points = LienzoGlobals.get().getDefaultFontSize();
        }
        return points;
    }

    public final void setSkew(double skew)
    {
        put(Attribute.SKEW.getProperty(), skew);
    }

    public final void setFontFamily(String family)
    {
        if ((null == family) || (family = family.trim()).isEmpty())
        {
            put(Attribute.FONT_FAMILY.getProperty(), LienzoGlobals.get().getDefaultFontFamily());
        }
        else
        {
            put(Attribute.FONT_FAMILY.getProperty(), family);
        }
    }

    public final String getFontFamily()
    {
        String family = getString(Attribute.FONT_FAMILY.getProperty());

        if ((null == family) || (family = family.trim()).isEmpty())
        {
            family = LienzoGlobals.get().getDefaultFontFamily();
        }
        return family;
    }

    public final void setFontStyle(String style)
    {
        if ((null == style) || (style = style.trim()).isEmpty())
        {
            put(Attribute.FONT_STYLE.getProperty(), LienzoGlobals.get().getDefaultFontStyle());
        }
        else
        {
            put(Attribute.FONT_STYLE.getProperty(), style);
        }
    }

    public final String getFontStyle()
    {
        String style = getString(Attribute.FONT_STYLE.getProperty());

        if ((null == style) || (style = style.trim()).isEmpty())
        {
            style = LienzoGlobals.get().getDefaultFontStyle();
        }
        return style;
    }

    public final void setTextBaseLine(TextBaseLine baseline)
    {
        if (null != baseline)
        {
            put(Attribute.TEXT_BASELINE.getProperty(), baseline.getValue());
        }
        else
        {
            delete(Attribute.TEXT_BASELINE.getProperty());
        }
    }

    public final void setTextAlign(TextAlign textAlign)
    {
        if (null != textAlign)
        {
            put(Attribute.TEXT_ALIGN.getProperty(), textAlign.getValue());
        }
        else
        {
            delete(Attribute.TEXT_ALIGN.getProperty());
        }
    }

    public final TextBaseLine getTextBaseLine()
    {
        return TextBaseLine.lookup(getString(Attribute.TEXT_BASELINE.getProperty()));
    }

    public final TextAlign getTextAlign()
    {
        return TextAlign.lookup(getString(Attribute.TEXT_ALIGN.getProperty()));
    }

    public final void setTextPadding(double padding)
    {
        put(Attribute.TEXT_PADDING.getProperty(), padding);
    }

    public final void setShadow(Shadow shadow)
    {
        if (null != shadow)
        {
            put(Attribute.SHADOW.getProperty(), shadow.getJSO());
        }
        else
        {
            delete(Attribute.SHADOW.getProperty());
        }
    }

    public final Shadow getShadow()
    {
        JavaScriptObject shadow = getObject(Attribute.SHADOW.getProperty());

        if (null != shadow)
        {
            ShadowJSO sjso = shadow.cast();

            return new Shadow(sjso);
        }
        return null;
    }

    public final void setStartAngle(double startAngle)
    {
        put(Attribute.START_ANGLE.getProperty(), startAngle);
    }

    public final void setEndAngle(double endAngle)
    {
        put(Attribute.END_ANGLE.getProperty(), endAngle);
    }

    public final void setCounterClockwise(boolean counterClockwise)
    {
        put(Attribute.COUNTER_CLOCKWISE.getProperty(), counterClockwise);
    }

    public final void setControlPoints(Point2DArray controlPoints)
    {
        if (null != controlPoints)
        {
            put(Attribute.CONTROL_POINTS.getProperty(), controlPoints.getJSO());
        }
        else
        {
            delete(Attribute.CONTROL_POINTS.getProperty());
        }
    }

    public final Point2DArray getControlPoints()
    {
        JsArray<JavaScriptObject> points = getArrayOfJSO(Attribute.CONTROL_POINTS.getProperty());

        if (null != points)
        {
            return new Point2DArray(points);
        }
        return new Point2DArray();
    }

    public final double getX()
    {
        return getDouble(Attribute.X.getProperty());
    }

    public final double getY()
    {
        return getDouble(Attribute.Y.getProperty());
    }

    public final double getRadius()
    {
        return getDouble(Attribute.RADIUS.getProperty());
    }

    public final double getCornerRadius()
    {
        return getDouble(Attribute.CORNER_RADIUS.getProperty());
    }

    public final double getWidth()
    {
        return getDouble(Attribute.WIDTH.getProperty());
    }

    public final double getHeight()
    {
        return getDouble(Attribute.HEIGHT.getProperty());
    }

    public final int getStarPoints()
    {
        int points = getInteger(Attribute.STAR_POINTS.getProperty());

        if (points < 5)
        {
            points = 5;
        }
        return points;
    }

    public final int getSides()
    {
        int sides = getInteger(Attribute.SIDES.getProperty());

        if (sides < 3)
        {
            sides = 3;
        }
        return sides;
    }

    public final void setSides(int sides)
    {
        if (sides < 3)
        {
            sides = 3;
        }
        put(Attribute.SIDES.getProperty(), sides);
    }

    public final double getStartAngle()
    {
        return getDouble(Attribute.START_ANGLE.getProperty());
    }

    public final double getEndAngle()
    {
        return getDouble(Attribute.END_ANGLE.getProperty());
    }

    public final boolean isCounterClockwise()
    {
        return getBoolean(Attribute.COUNTER_CLOCKWISE.getProperty());
    }

    public final double getSkew()
    {
        return getDouble(Attribute.SKEW.getProperty());
    }

    public final double getInnerRadius()
    {
        return getDouble(Attribute.INNER_RADIUS.getProperty());
    }

    public final void setInnerRadius(double radius)
    {
        put(Attribute.INNER_RADIUS.getProperty(), radius);
    }

    public final void setOuterRadius(double radius)
    {
        put(Attribute.OUTER_RADIUS.getProperty(), radius);
    }

    public final double getOuterRadius()
    {
        return getDouble(Attribute.OUTER_RADIUS.getProperty());
    }

    public final double getAlpha()
    {
        double alpha = getDouble(Attribute.ALPHA.getProperty());

        if (alpha < 0)
        {
            alpha = 0;
        }
        if (alpha > 1)
        {
            alpha = 1;
        }
        return alpha;
    }

    public final void setOffset(double xy)
    {
        setOffset(new Point2D(xy, xy));
    }

    public final DragBounds getDragBounds()
    {
        JavaScriptObject bounds = getObject(Attribute.DRAG_BOUNDS.getProperty());

        if (null != bounds)
        {
            DragBoundsJSO djso = bounds.cast();

            return new DragBounds(djso);
        }
        return null;
    }

    public final void setDragBounds(DragBounds bounds)
    {
        if (null != bounds)
        {
            put(Attribute.DRAG_BOUNDS.getProperty(), bounds.getJSO());
        }
        else
        {
            delete(Attribute.DRAG_BOUNDS.getProperty());
        }
    }

    public final DragMode getDragMode()
    {
        return DragMode.lookup(getString(Attribute.DRAG_MODE.getProperty()));
    }

    public final void setDragMode(DragMode mode)
    {
        if (null != mode)
        {
            put(Attribute.DRAG_MODE.getProperty(), mode.getValue());
        }
        else
        {
            delete(Attribute.DRAG_MODE.getProperty());
        }
    }

    public final void setClippedImageStartX(int clippedImageStartX)
    {
        put(Attribute.CLIPPED_IMAGE_START_X.getProperty(), clippedImageStartX);
    }

    public final int getClippedImageStartX()
    {
        return getInteger(Attribute.CLIPPED_IMAGE_START_X.getProperty());
    }

    public final void setClippedImageStartY(int clippedImageStartY)
    {
        put(Attribute.CLIPPED_IMAGE_START_Y.getProperty(), clippedImageStartY);
    }

    public final int getClippedImageStartY()
    {
        return getInteger(Attribute.CLIPPED_IMAGE_START_Y.getProperty());
    }

    public final void setClippedImageWidth(int clippedImageWidth)
    {
        put(Attribute.CLIPPED_IMAGE_WIDTH.getProperty(), clippedImageWidth);
    }

    public final int getClippedImageWidth()
    {
        return getInteger(Attribute.CLIPPED_IMAGE_WIDTH.getProperty());
    }

    public final void setClippedImageHeight(int clippedImageHeight)
    {
        put(Attribute.CLIPPED_IMAGE_HEIGHT.getProperty(), clippedImageHeight);
    }

    public final int getClippedImageHeight()
    {
        return getInteger(Attribute.CLIPPED_IMAGE_HEIGHT.getProperty());
    }

    public final void setClippedImageDestinationWidth(int clippedImageDestinationWidth)
    {
        put(Attribute.CLIPPED_IMAGE_DESTINATION_WIDTH.getProperty(), clippedImageDestinationWidth);
    }

    public final int getClippedImageDestinationWidth()
    {
        return getInteger(Attribute.CLIPPED_IMAGE_DESTINATION_WIDTH.getProperty());
    }

    public final void setClippedImageDestinationHeight(int clippedImageDestinationHeight)
    {
        put(Attribute.CLIPPED_IMAGE_DESTINATION_HEIGHT.getProperty(), clippedImageDestinationHeight);
    }

    public final int getClippedImageDestinationHeight()
    {
        return getInteger(Attribute.CLIPPED_IMAGE_DESTINATION_HEIGHT.getProperty());
    }

    public final void setSerializationMode(ImageSerializationMode mode)
    {
        if (null != mode)
        {
            put(Attribute.SERIALIZATION_MODE.getProperty(), mode.getValue());
        }
        else
        {
            delete(Attribute.SERIALIZATION_MODE.getProperty());
        }
    }

    public final ImageSerializationMode getSerializationMode()
    {
        return ImageSerializationMode.lookup(getString(Attribute.SERIALIZATION_MODE.getProperty()));
    }

    public final void setImageSelectionMode(ImageSelectionMode mode)
    {
        if (null != mode)
        {
            put(Attribute.IMAGE_SELECTION_MODE.getProperty(), mode.getValue());
        }
        else
        {
            delete(Attribute.IMAGE_SELECTION_MODE.getProperty());
        }
    }

    public final ImageSelectionMode getImageSelectionMode()
    {
        return ImageSelectionMode.lookup(getString(Attribute.IMAGE_SELECTION_MODE.getProperty()));
    }

    public final void setBaseWidth(double baseWidth)
    {
        put(Attribute.BASE_WIDTH.getProperty(), baseWidth);
    }

    public final double getBaseWidth()
    {
        return getDouble(Attribute.BASE_WIDTH.getProperty());
    }

    public final void setHeadWidth(double headWidth)
    {
        put(Attribute.HEAD_WIDTH.getProperty(), headWidth);
    }

    public final double getHeadWidth()
    {
        return getDouble(Attribute.HEAD_WIDTH.getProperty());
    }

    public final void setArrowAngle(double arrowAngle)
    {
        put(Attribute.ARROW_ANGLE.getProperty(), arrowAngle);
    }

    public final double getArrowAngle()
    {
        return getDouble(Attribute.ARROW_ANGLE.getProperty());
    }

    public final void setBaseAngle(double baseAngle)
    {
        put(Attribute.BASE_ANGLE.getProperty(), baseAngle);
    }

    public final double getBaseAngle()
    {
        return getDouble(Attribute.BASE_ANGLE.getProperty());
    }

    public final void setArrowType(ArrowType arrowType)
    {
        if (null != arrowType)
        {
            put(Attribute.ARROW_TYPE.getProperty(), arrowType.getValue());
        }
        else
        {
            delete(Attribute.ARROW_TYPE.getProperty());
        }
    }

    public final ArrowType getArrowType()
    {
        return ArrowType.lookup(getString(Attribute.ARROW_TYPE.getProperty()));
    }

    public final void setURL(String url)
    {
        if (null != url)
        {
            put(Attribute.URL.getProperty(), url);
        }
        else
        {
            delete(Attribute.URL.getProperty());
        }
    }

    public final String getURL()
    {
        return getString(Attribute.URL.getProperty());
    }

    public final void setLoop(boolean loop)
    {
        put(Attribute.LOOP.getProperty(), loop);
    }

    public final boolean isLoop()
    {
        if (isDefined(Attribute.LOOP))
        {
            return getBoolean(Attribute.LOOP.getProperty());
        }
        return false;
    }

    public final void setPlaybackRate(double rate)
    {
        put(Attribute.PLAYBACK_RATE.getProperty(), rate);
    }

    public final double getPlaybackRate()
    {
        if (isDefined(Attribute.PLAYBACK_RATE))
        {
            return getDouble(Attribute.PLAYBACK_RATE.getProperty());
        }
        return 1.0;
    }

    public final void setVolume(double volume)
    {
        if (volume > 1.0)
        {
            volume = 1.0;
        }
        else if (volume < 0.0)
        {
            volume = 0.0;
        }
        put(Attribute.VOLUME.getProperty(), volume);
    }

    public final double getVolume()
    {
        if (isDefined(Attribute.VOLUME))
        {
            double volume = getDouble(Attribute.VOLUME.getProperty());

            if (volume > 1.0)
            {
                volume = 1.0;
            }
            else if (volume < 0.0)
            {
                volume = 0.0;
            }
            return volume;
        }
        return 0.0;
    }

    public final void setAutoPlay(boolean play)
    {
        put(Attribute.AUTO_PLAY.getProperty(), play);
    }

    public final boolean isAutoPlay()
    {
        if (isDefined(Attribute.AUTO_PLAY))
        {
            return getBoolean(Attribute.AUTO_PLAY.getProperty());
        }
        return false;
    }

    public final void setShowPoster(boolean show)
    {
        put(Attribute.SHOW_POSTER.getProperty(), show);
    }

    public final boolean isShowPoster()
    {
        if (isDefined(Attribute.SHOW_POSTER))
        {
            return getBoolean(Attribute.SHOW_POSTER.getProperty());
        }
        return false;
    }

    public final double getCurveFactor()
    {
        if (isDefined(Attribute.CURVE_FACTOR))
        {
            double factor = getDouble(Attribute.CURVE_FACTOR.getProperty());

            if (factor <= 0)
            {
                return 0.5;
            }
            if (factor > 1)
            {
                return 1;
            }
            return factor;
        }
        return 0.5;
    }

    public final void setCurveFactor(double factor)
    {
        if (factor <= 0)
        {
            factor = 0.5;
        }
        else if (factor > 1)
        {
            factor = 1;
        }
        put(Attribute.CURVE_FACTOR.getProperty(), factor);
    }

    public final double getAngleFactor()
    {
        if (isDefined(Attribute.ANGLE_FACTOR))
        {
            double factor = getDouble(Attribute.ANGLE_FACTOR.getProperty());

            if (factor < 0)
            {
                return 0;
            }
            if (factor > 1)
            {
                return 1;
            }
            return factor;
        }
        return 0;
    }

    public final boolean getLineFlatten()
    {
        if (isDefined(Attribute.LINE_FLATTEN))
        {
            return getBoolean(Attribute.LINE_FLATTEN.getProperty());
        }
        return false;
    }

    public final void setLineFlatten(boolean flat)
    {
        put(Attribute.LINE_FLATTEN.getProperty(), flat);
    }

    public final void setAngleFactor(double factor)
    {
        if (factor < 0)
        {
            factor = 0;
        }
        else if (factor > 1)
        {
            factor = 1;
        }
        put(Attribute.ANGLE_FACTOR.getProperty(), factor);
    }

    public final void setTopWidth(double topwidth)
    {
        put(Attribute.TOP_WIDTH.getProperty(), topwidth);
    }

    public final double getTopWidth()
    {
        return getDouble(Attribute.TOP_WIDTH.getProperty());
    }

    public final void setBottomWidth(double bottomwidth)
    {
        put(Attribute.BOTTOM_WIDTH.getProperty(), bottomwidth);
    }

    public final double getBottomWidth()
    {
        return getDouble(Attribute.BOTTOM_WIDTH.getProperty());
    }

    public final void setDashOffset(double offset)
    {
        put(Attribute.DASH_OFFSET.getProperty(), offset);
    }

    public final double getDashOffset()
    {
        return getDouble(Attribute.DASH_OFFSET.getProperty());
    }

    public final void put(String name, String value)
    {
        if (null != value)
        {
            put0(name, value.substring(0));
        }
        else
        {
            delete(name);
        }
    }

    public final native boolean isEmpty()
    /*-{
		var that = this;

		for ( var i in that) {
			return false;
		}
		return true;
    }-*/;

    private final native void put0(String name, String value)
    /*-{
		this[name] = value;
    }-*/;

    private final native void put(String name, int value)
    /*-{
		this[name] = value;
    }-*/;

    public final native void put(String name, double value)
    /*-{
		this[name] = value;
    }-*/;

    public final native void put(String name, boolean value)
    /*-{
		this[name] = value;
    }-*/;

    public final native void put(String name, JavaScriptObject value)
    /*-{
		this[name] = value;
    }-*/;

    public final Collection<String> getKeysCollection()
    {
        ArrayList<String> list = new ArrayList<String>();

        fillKeysCollection(list);

        return list;
    }

    private final native void fillKeysCollection(Collection<String> keys)
    /*-{
		var self = this;

		for ( var name in self) {
			if ((self.hasOwnProperty(name)) && (self[name] !== undefined)) {
				keys.@java.util.Collection::add(Ljava/lang/Object;)(name);
			}
		}
    }-*/;

    public final int getInteger(String name)
    {
        if (typeOf(name) == NativeInternalType.NUMBER)
        {
            return getInteger0(name);
        }
        return 0;
    }

    public final double getDouble(String name)
    {
        if (typeOf(name) == NativeInternalType.NUMBER)
        {
            return getDouble0(name);
        }
        return 0;
    }

    public final void putDouble(String name, double value)
    {
        put(name, value);
    }

    public final Point2D getPoint2D(String name)
    {
        JavaScriptObject offset = getObject(name);

        if (null != offset)
        {
            Point2DJSO pjso = offset.cast();

            return new Point2D(pjso);
        }
        return null;
    }

    public final void putPoint2D(String name, Point2D point)
    {
        if (null != point)
        {
            put(name, point.getJSO());
        }
        else
        {
            delete(Attribute.SCALE.getProperty());
        }
    }

    public final String getString(String name)
    {
        if (typeOf(name) == NativeInternalType.STRING)
        {
            return getString0(name);
        }
        return null;
    }

    public final boolean getBoolean(String name)
    {
        if (typeOf(name) == NativeInternalType.BOOLEAN)
        {
            return getBoolean0(name);
        }
        return false;
    }

    public final JavaScriptObject getObject(String name)
    {
        if (typeOf(name) == NativeInternalType.OBJECT)
        {
            return getObject0(name);
        }
        return null;
    }

    public final JsArray<JavaScriptObject> getArrayOfJSO(String name)
    {
        if (typeOf(name) == NativeInternalType.ARRAY)
        {
            return getArrayOfJSO0(name);
        }
        return null;
    }

    public final JsArrayMixed getArray(String name)
    {
        if (typeOf(name) == NativeInternalType.ARRAY)
        {
            return getArray0(name);
        }
        return null;
    }

    public final boolean isDefined(Attribute attr)
    {
        if (null == attr)
        {
            return false;
        }
        String prop = attr.getProperty();

        if (null == prop)
        {
            return false;
        }
        return isDefined0(prop);
    }

    private final native boolean isDefined0(String name)
    /*-{
		return this.hasOwnProperty(String(name));
    }-*/;

    private final native double getDouble0(String name)
    /*-{
		return this[name];
    }-*/;

    private final native int getInteger0(String name)
    /*-{
		return Math.round(this[name]);
    }-*/;

    private final native String getString0(String name)
    /*-{
		return this[name];
    }-*/;

    private final native boolean getBoolean0(String name)
    /*-{
		return this[name];
    }-*/;

    private final native JavaScriptObject getObject0(String name)
    /*-{
		return this[name];
    }-*/;

    private final native JsArray<JavaScriptObject> getArrayOfJSO0(String name)
    /*-{
		return this[name];
    }-*/;

    private final native JsArrayMixed getArray0(String name)
    /*-{
		return this[name];
    }-*/;

    public final native void delete(String name)
    /*-{
		delete this[name];
    }-*/;

    public final NativeInternalType typeOf(Attribute attr)
    {
        if (null != attr)
        {
            return typeOf(attr.getProperty());
        }
        return NativeInternalType.UNDEFINED;
    }

    public final native NativeInternalType typeOf(String name)
    /*-{
		if (this.hasOwnProperty(String(name)) && (this[name] !== undefined)) {

			var valu = this[name];

			var type = typeof valu;

			switch (type) {
			case 'string': {
				return @com.emitrom.lienzo.client.core.types.NativeInternalType::STRING;
			}
			case 'boolean': {
				return @com.emitrom.lienzo.client.core.types.NativeInternalType::BOOLEAN;
			}
			case 'number': {
				if (isFinite(valu)) {
					return @com.emitrom.lienzo.client.core.types.NativeInternalType::NUMBER;
				}
				return @com.emitrom.lienzo.client.core.types.NativeInternalType::UNDEFINED;
			}
			case 'object': {
				if ((valu instanceof Array) || (valu instanceof $wnd.Array)) {
					return @com.emitrom.lienzo.client.core.types.NativeInternalType::ARRAY;
				}
				return @com.emitrom.lienzo.client.core.types.NativeInternalType::OBJECT;
			}
			case 'function': {
				return @com.emitrom.lienzo.client.core.types.NativeInternalType::FUNCTION;
			}
			}
		}
		return @com.emitrom.lienzo.client.core.types.NativeInternalType::UNDEFINED;
    }-*/;

    public static final native String getString(String name, JavaScriptObject obj)
    /*-{
		return obj[name];
    }-*/;

}