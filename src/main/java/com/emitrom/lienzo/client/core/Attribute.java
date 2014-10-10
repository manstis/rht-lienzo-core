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

package com.emitrom.lienzo.client.core;

import com.emitrom.lienzo.client.core.i18n.MessageConstants;

/**
 * This class is used internally by the toolkit to provide type-safe property access. 
 */
public class Attribute
{
    private static final MessageConstants M                                = MessageConstants.MESSAGES;

    public static final Attribute         WIDTH                            = new Attribute("width", M.widthLabel(), M.widthDescription(), AttributeType.NUMBER_TYPE);

    public static final Attribute         HEIGHT                           = new Attribute("height", M.heightLabel(), M.heightDescription(), AttributeType.NUMBER_TYPE);

    public static final Attribute         CORNER_RADIUS                    = new Attribute("cornerRadius", M.cornerRadiusLabel(), M.cornerRadiusDescription(), AttributeType.NUMBER_TYPE);

    public static final Attribute         FILL                             = new Attribute("fill", M.fillLabel(), M.fillDescription(), AttributeType.FILL_TYPE);

    public static final Attribute         STROKE                           = new Attribute("stroke", M.strokeLabel(), M.strokeDescription(), AttributeType.STROKE_TYPE);

    public static final Attribute         STROKE_WIDTH                     = new Attribute("strokeWidth", M.strokeWidthLabel(), M.strokeWidthDescription(), AttributeType.NUMBER_TYPE);

    public static final Attribute         LINE_JOIN                        = new Attribute("lineJoin", M.lineJoinLabel(), M.lineJoinDescription(), AttributeType.LINE_JOIN_TYPE);

    public static final Attribute         X                                = new Attribute("x", M.xLabel(), M.xDescription(), AttributeType.NUMBER_TYPE);

    public static final Attribute         Y                                = new Attribute("y", M.yLabel(), M.yDescription(), AttributeType.NUMBER_TYPE);

    public static final Attribute         VISIBLE                          = new Attribute("visible", M.visibleLabel(), M.visibleDescription(), AttributeType.BOOLEAN_TYPE);

    public static final Attribute         LISTENING                        = new Attribute("listening", M.listeningLabel(), M.listeningDescription(), AttributeType.BOOLEAN_TYPE);

    public static final Attribute         ID                               = new Attribute("id", M.idLabel(), M.idDescription(), AttributeType.STRING_TYPE);

    public static final Attribute         NAME                             = new Attribute("name", M.nameLabel(), M.nameDescription(), AttributeType.STRING_TYPE);

    public static final Attribute         ALPHA                            = new Attribute("alpha", M.alphaLabel(), M.alphaDescription(), AttributeType.NUMBER_TYPE);

    public static final Attribute         SCALE                            = new Attribute("scale", M.scaleLabel(), M.scaleDescription(), AttributeType.POINT2D_TYPE);

    public static final Attribute         ROTATION                         = new Attribute("rotation", M.rotationLabel(), M.rotationDescription(), AttributeType.NUMBER_TYPE);

    public static final Attribute         OFFSET                           = new Attribute("offset", M.offsetLabel(), M.offsetDescription(), AttributeType.POINT2D_TYPE);

    public static final Attribute         SHEAR                            = new Attribute("shear", M.offsetLabel(), M.offsetDescription(), AttributeType.POINT2D_TYPE);

    public static final Attribute         TRANSFORM                        = new Attribute("transform", M.transformLabel(), M.transformDescription(), AttributeType.TRANSFORM_TYPE);

    public static final Attribute         DRAGGABLE                        = new Attribute("draggable", M.draggableLabel(), M.draggableDescription(), AttributeType.BOOLEAN_TYPE);

    public static final Attribute         FILL_SHAPE_FOR_SELECTION         = new Attribute("fillShapeForSelection", M.draggableLabel(), M.draggableDescription(), AttributeType.BOOLEAN_TYPE);

    public static final Attribute         DRAG_CONSTRAINT                  = new Attribute("dragConstraint", M.dragConstraintLabel(), M.dragConstraintDescription(), AttributeType.DRAG_CONSTRAINT_TYPE);

    public static final Attribute         DRAG_BOUNDS                      = new Attribute("dragBounds", M.dragBoundsLabel(), M.dragBoundsDescription(), AttributeType.DRAG_BOUNDS_TYPE);

    public static final Attribute         RADIUS                           = new Attribute("radius", M.radiusLabel(), M.radiusDescription(), AttributeType.NUMBER_TYPE);

    public static final Attribute         IMAGE                            = new Attribute("image", M.imageLabel(), M.imageDescription(), AttributeType.IMAGE_TYPE);

    public static final Attribute         CLEAR_LAYER_BEFORE_DRAW          = new Attribute("clearLayerBeforeDraw", M.clearLayerBeforeDrawLabel(), M.clearLayerBeforeDrawDescription(), AttributeType.BOOLEAN_TYPE);

    public static final Attribute         TRANSFORMABLE                    = new Attribute("transformable", M.zoomableLabel(), M.zoomableDescription(), AttributeType.BOOLEAN_TYPE);

    public static final Attribute         TEXT                             = new Attribute("text", M.textLabel(), M.textDescription(), AttributeType.STRING_TYPE);

    public static final Attribute         FONT_SIZE                        = new Attribute("fontSize", M.fontSizeLabel(), M.fontSizeDescription(), AttributeType.NUMBER_TYPE);

    public static final Attribute         FONT_FAMILY                      = new Attribute("fontFamily", M.fontFamilyLabel(), M.fontFamilyDescription(), AttributeType.STRING_TYPE);

    public static final Attribute         FONT_STYLE                       = new Attribute("fontStyle", M.fontStyleFamilyLabel(), M.fontStyleFamilyDescription(), AttributeType.STRING_TYPE);

    public static final Attribute         TEXT_PADDING                     = new Attribute("textPadding", M.textPaddingLabel(), M.textPaddingDescription(), AttributeType.NUMBER_TYPE);

    public static final Attribute         POINTS                           = new Attribute("points", M.pointsLabel(), M.pointsDescription(), AttributeType.POINT2D_ARRAY_TYPE);

    public static final Attribute         STAR_POINTS                      = new Attribute("starPoints", M.starPointsLabel(), M.starPointsDescription(), AttributeType.NUMBER_TYPE);

    public static final Attribute         LINE_CAP                         = new Attribute("lineCap", M.lineCapLabel(), M.lineCapDescription(), AttributeType.LINE_CAP_TYPE);

    public static final Attribute         DASH_ARRAY                       = new Attribute("dashArray", M.dashArrayLabel(), M.dashArrayDescription(), AttributeType.DASH_ARRAY_TYPE);

    public static final Attribute         DASH_OFFSET                      = new Attribute("dashOffset", M.dashArrayLabel(), M.dashArrayDescription(), AttributeType.NUMBER_TYPE);

    public static final Attribute         SIDES                            = new Attribute("sides", M.sidesLabel(), M.sidesDescription(), AttributeType.NUMBER_TYPE);

    public static final Attribute         OUTER_RADIUS                     = new Attribute("outerRadius", M.outerRadiusLabel(), M.outerRadiusDescription(), AttributeType.NUMBER_TYPE);

    public static final Attribute         INNER_RADIUS                     = new Attribute("innerRadius", M.innerRadiusLabel(), M.innerRadiusDescription(), AttributeType.NUMBER_TYPE);

    public static final Attribute         SKEW                             = new Attribute("skew", M.skewLabel(), M.skewDescription(), AttributeType.NUMBER_TYPE);

    public static final Attribute         SHADOW                           = new Attribute("shadow", M.shadowLabel(), M.shadowDescription(), AttributeType.SHADOW_TYPE);

    public static final Attribute         START_ANGLE                      = new Attribute("startAngle", M.startAngleLabel(), M.startAngleDescription(), AttributeType.NUMBER_TYPE);

    public static final Attribute         END_ANGLE                        = new Attribute("endAngle", M.endAngleLabel(), M.endAngleDescription(), AttributeType.NUMBER_TYPE);

    public static final Attribute         COUNTER_CLOCKWISE                = new Attribute("counterClockwise", M.counterClockwiseLabel(), M.counterClockwiseDescription(), AttributeType.BOOLEAN_TYPE);

    public static final Attribute         CONTROL_POINTS                   = new Attribute("controlPoints", M.controlPointsLabel(), M.controlPointsDescription(), AttributeType.POINT2D_ARRAY_TYPE);

    public static final Attribute         TEXT_BASELINE                    = new Attribute("textBaseline", M.textBaseLineLabel(), M.textBaseLineDescription(), AttributeType.TEXT_BASELINE_TYPE);

    public static final Attribute         TEXT_ALIGN                       = new Attribute("textAlign", M.textAlignLabel(), M.textAlignDescription(), AttributeType.TEXT_ALIGN_TYPE);

    public static final Attribute         CLIPPED_IMAGE_WIDTH              = new Attribute("clippedImageWidth", M.clippedImageWidthLabel(), M.clippedImageWidthDescription(), AttributeType.NUMBER_TYPE);

    public static final Attribute         CLIPPED_IMAGE_HEIGHT             = new Attribute("clippedImageHeight", M.clippedImageHeightLabel(), M.clippedImageHeightDescription(), AttributeType.NUMBER_TYPE);

    public static final Attribute         CLIPPED_IMAGE_START_X            = new Attribute("clippedImageStartX", M.clippedImageStartXLabel(), M.clippedImageStartXDescription(), AttributeType.NUMBER_TYPE);

    public static final Attribute         CLIPPED_IMAGE_START_Y            = new Attribute("clippedImageStartY", M.clippedImageStartYLabel(), M.clippedImageStartYDescription(), AttributeType.NUMBER_TYPE);

    public static final Attribute         CLIPPED_IMAGE_DESTINATION_WIDTH  = new Attribute("clippedImageDestinationWidth", M.clippedImageDestinationWidthLabel(), M.clippedImageDestinationWidthDescription(), AttributeType.NUMBER_TYPE);

    public static final Attribute         CLIPPED_IMAGE_DESTINATION_HEIGHT = new Attribute("clippedImageDestinationHeight", M.clippedImageDestinationHeightLabel(), M.clippedImageDestinationHeightDescription(), AttributeType.NUMBER_TYPE);

    public static final Attribute         PICTURE_CATEGORY                 = new Attribute("pictureCategory", M.pictureCategoryLabel(), M.pictureCategoryDescription(), AttributeType.STRING_TYPE);

    public static final Attribute         SERIALIZATION_MODE               = new Attribute("serializationMode", M.serializationModeLabel(), M.serializationModeDescription(), AttributeType.SERIALIZATION_MODE_TYPE);

    public static final Attribute         RESOURCE_ID                      = new Attribute("resourceID", M.resourceIDLabel(), M.resourceIDDescription(), AttributeType.STRING_TYPE);

    public static final Attribute         URL                              = new Attribute("url", M.urlLabel(), M.urlDescription(), AttributeType.STRING_TYPE);

    public static final Attribute         LOOP                             = new Attribute("loop", M.loopLabel(), M.loopDescription(), AttributeType.BOOLEAN_TYPE);

    public static final Attribute         VOLUME                           = new Attribute("volume", M.volumeLabel(), M.volumeDescription(), AttributeType.NUMBER_TYPE);

    public static final Attribute         BASE_WIDTH                       = new Attribute("baseWidth", M.baseWidthLabel(), M.baseWidthDescription(), AttributeType.NUMBER_TYPE);

    public static final Attribute         HEAD_WIDTH                       = new Attribute("headWidth", M.headWidthLabel(), M.headWidthDescription(), AttributeType.NUMBER_TYPE);

    public static final Attribute         ARROW_ANGLE                      = new Attribute("arrowAngle", M.arrowAngleLabel(), M.arrowAngleDescription(), AttributeType.NUMBER_TYPE);

    public static final Attribute         BASE_ANGLE                       = new Attribute("baseAngle", M.baseAngleLabel(), M.baseAngleDescription(), AttributeType.NUMBER_TYPE);

    public static final Attribute         ARROW_TYPE                       = new Attribute("arrowType", M.arrowTypeLabel(), M.arrowTypeDescription(), AttributeType.ARROW_TYPE);

    public static final Attribute         MITER_LIMIT                      = new Attribute("miterLimit", M.miterLimitTypeLabel(), M.miterLimitTypeDescription(), AttributeType.NUMBER_TYPE);

    public static final Attribute         CURVE_FACTOR                     = new Attribute("curveFactor", M.curveFactorTypeLabel(), M.curveFactorTypeDescription(), AttributeType.NUMBER_TYPE);

    public static final Attribute         ANGLE_FACTOR                     = new Attribute("angleFactor", M.angleFactorTypeLabel(), M.angleFactorTypeDescription(), AttributeType.NUMBER_TYPE);

    public static final Attribute         LINE_FLATTEN                     = new Attribute("lineFlatten", M.lineFlattenTypeLabel(), M.lineFlattenTypeDescription(), AttributeType.BOOLEAN_TYPE);

    public static final Attribute         TOP_WIDTH                        = new Attribute("topWidth", M.widthLabel(), M.widthDescription(), AttributeType.NUMBER_TYPE);

    public static final Attribute         BOTTOM_WIDTH                     = new Attribute("bottomWidth", M.widthLabel(), M.widthDescription(), AttributeType.NUMBER_TYPE);

    private final String                  m_property;

    private final String                  m_label;

    private final String                  m_description;

    private final AttributeType           m_type;

    public Attribute(String property, String label, String description, AttributeType type)
    {
        m_type = type;

        m_label = label;

        m_property = property;

        m_description = description;
    }

    public final AttributeType getType()
    {
        return m_type;
    }

    public final String getProperty()
    {
        return m_property;
    }

    public final String getLabel()
    {
        return m_label;
    }

    public final String getDescription()
    {
        return m_description;
    }

    @Override
    public final String toString()
    {
        return m_property;
    }
}
