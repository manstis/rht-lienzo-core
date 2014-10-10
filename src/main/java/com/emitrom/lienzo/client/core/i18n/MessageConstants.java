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

package com.emitrom.lienzo.client.core.i18n;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Constants;

/**
 * An I18 based interface used for Lienzo Constants.
 */
public interface MessageConstants extends Constants
{
    public static final MessageConstants MESSAGES = GWT.create(MessageConstants.class);

    @DefaultStringValue("Canvas is not supported in this browser!")
    public String getCanvasUnsupportedMessage();

    @DefaultStringValue("Playback was aborted")
    public String moviePlaybackWasAborted();

    @DefaultStringValue("Video network error")
    public String movieNetworkError();

    @DefaultStringValue("Error in decoding")
    public String movieErrorInDecoding();

    @DefaultStringValue("Format not supported")
    public String movieFormatNotSupported();

    @DefaultStringValue("Video not support in this browser")
    public String movieNotSupportedInThisBrowser();

    // ---------- Validation

    @DefaultStringValue("attribute is required")
    public String attributeIsRequired();

    @DefaultStringValue("invalid value for type {0} [{1}]")
    // type, value
    public String invalidValueForType();

    @DefaultStringValue("value should be a {0}")
    // type
    public String invalidType();

    @DefaultStringValue("attribute is invalid for type {0}")
    // type
    public String attributeIsInvalidForType();

    @DefaultStringValue("value must be [{0}]")
    // value
    public String attributeValueMustBeFixed();

    @DefaultStringValue("no NodeFactory is registered for type '{0}'")
    // type
    public String missingNodeFactory();

    @DefaultStringValue("Invalid array size. Expected value is {0}. Actual value is {1}")
    // expectedValue, actualValue
    public String invalidArraySize();

    // ---------- Attributes

    @DefaultStringValue("Width")
    public String widthLabel();

    @DefaultStringValue("Width value in pixels.")
    public String widthDescription();

    @DefaultStringValue("Height")
    public String heightLabel();

    @DefaultStringValue("Height value in pixels.")
    public String heightDescription();

    @DefaultStringValue("Corner Radius")
    public String cornerRadiusLabel();

    @DefaultStringValue("The radius of a 90 degree arc, which is used as a rounded corner.")
    public String cornerRadiusDescription();

    @DefaultStringValue("Fill")
    public String fillLabel();

    @DefaultStringValue("The color or gradient used to fill a shape.")
    public String fillDescription();

    @DefaultStringValue("Stroke")
    public String strokeLabel();

    @DefaultStringValue("The color of the outline of a shape.")
    public String strokeDescription();

    @DefaultStringValue("Stroke Width")
    public String strokeWidthLabel();

    @DefaultStringValue("Width in pixels of the outline of a shape.")
    public String strokeWidthDescription();

    @DefaultStringValue("Line Join")
    public String lineJoinLabel();

    @DefaultStringValue("Specifies how the connection of individual stroke segments will be drawn.")
    public String lineJoinDescription();

    @DefaultStringValue("X")
    public String xLabel();

    @DefaultStringValue("X coordinate.")
    public String xDescription();

    @DefaultStringValue("Y")
    public String yLabel();

    @DefaultStringValue("Y coordinate.")
    public String yDescription();

    @DefaultStringValue("Visible")
    public String visibleLabel();

    @DefaultStringValue("Indicates if the shape is visible or not.")
    public String visibleDescription();

    @DefaultStringValue("Listening")
    public String listeningLabel();

    @DefaultStringValue("Indicates if the shape is listening for events.")
    public String listeningDescription();

    @DefaultStringValue("ID")
    public String idLabel();

    @DefaultStringValue("Unique identifier for the shape.")
    public String idDescription();

    @DefaultStringValue("Name")
    public String nameLabel();

    @DefaultStringValue("Unique name given to the shape.")
    public String nameDescription();

    @DefaultStringValue("Alpha")
    public String alphaLabel();

    @DefaultStringValue("The alpha transparency for the shape.")
    public String alphaDescription();

    @DefaultStringValue("Scale")
    public String scaleLabel();

    @DefaultStringValue("Scale at which the shape is drawn.")
    public String scaleDescription();

    @DefaultStringValue("Rotation")
    public String rotationLabel();

    @DefaultStringValue("Radians used for the rotation of the shape around its position.")
    public String rotationDescription();

    @DefaultStringValue("Offset")
    public String offsetLabel();

    @DefaultStringValue("The offset at which the shape will be moved to.")
    public String offsetDescription();

    @DefaultStringValue("Draggable")
    public String draggableLabel();

    @DefaultStringValue("Indicates if the shape can be dragged.")
    public String draggableDescription();

    @DefaultStringValue("Drag Constraint")
    public String dragConstraintLabel();

    @DefaultStringValue("Drag constraints for the shape limit how the shape can be dragged.")
    public String dragConstraintDescription();

    @DefaultStringValue("Drag Bounds")
    public String dragBoundsLabel();

    @DefaultStringValue("Drag bounds determine where the shape can be dragged.")
    public String dragBoundsDescription();

    @DefaultStringValue("Radius")
    public String radiusLabel();

    @DefaultStringValue("The radius of a circle or arc.")
    public String radiusDescription();

    @DefaultStringValue("Image")
    public String imageLabel();

    @DefaultStringValue("An image.")
    public String imageDescription();

    @DefaultStringValue("Pre-Draw Layer Clear")
    public String clearLayerBeforeDrawLabel();

    @DefaultStringValue("Indicates if the layer should be cleared before drawing on it.")
    public String clearLayerBeforeDrawDescription();

    @DefaultStringValue("Zoomable")
    public String zoomableLabel();

    @DefaultStringValue("Indicates whether the layer should be affected by zoom operations.")
    public String zoomableDescription();

    @DefaultStringValue("Text")
    public String textLabel();

    @DefaultStringValue("Text.")
    public String textDescription();

    @DefaultStringValue("Font Size")
    public String fontSizeLabel();

    @DefaultStringValue("Text font size in points. i.e., 24")
    public String fontSizeDescription();

    @DefaultStringValue("Font Family")
    public String fontFamilyLabel();

    @DefaultStringValue("Text font family. i.e., Tahoma")
    public String fontFamilyDescription();

    @DefaultStringValue("Font Style")
    public String fontStyleFamilyLabel();

    @DefaultStringValue("Text font style. e.g., bold, italic, normal, etc.")
    public String fontStyleFamilyDescription();

    @DefaultStringValue("Text Padding")
    public String textPaddingLabel();

    @DefaultStringValue("Amount of padding in pixels that surrounds the text.")
    public String textPaddingDescription();

    @DefaultStringValue("Points")
    public String pointsLabel();

    @DefaultStringValue("Number of points the shape has.")
    public String pointsDescription();

    @DefaultStringValue("Star points.")
    public String starPointsLabel();

    @DefaultStringValue("Number of points the star has.")
    public String starPointsDescription();

    @DefaultStringValue("Line Cap")
    public String lineCapLabel();

    @DefaultStringValue("Specifies how the end of a shapes stroke will be drawn.")
    public String lineCapDescription();

    @DefaultStringValue("Dash Array")
    public String dashArrayLabel();

    @DefaultStringValue("The outline of the shape will be drawn as a dashed line. The dash array specifies how the dashes are drawn.")
    public String dashArrayDescription();

    @DefaultStringValue("Sides")
    public String sidesLabel();

    @DefaultStringValue("Number of sides the shape has.")
    public String sidesDescription();

    @DefaultStringValue("Outer Radius")
    public String outerRadiusLabel();

    @DefaultStringValue("The radius of the shape's enclosing circle.")
    public String outerRadiusDescription();

    @DefaultStringValue("Inner Radius")
    public String innerRadiusLabel();

    @DefaultStringValue("The radius of the smallest circle able to fit inside the shape.")
    public String innerRadiusDescription();

    @DefaultStringValue("Skew")
    public String skewLabel();

    @DefaultStringValue("The position where the shape will be skewed to.")
    public String skewDescription();

    @DefaultStringValue("Shadow")
    public String shadowLabel();

    @DefaultStringValue("The value for the shape's shadow.")
    public String shadowDescription();

    @DefaultStringValue("Start Angle")
    public String startAngleLabel();

    @DefaultStringValue("The start angle of a slice.")
    public String startAngleDescription();

    @DefaultStringValue("End Angle")
    public String endAngleLabel();

    @DefaultStringValue("The end angle of a slice.")
    public String endAngleDescription();

    @DefaultStringValue("Counter Clockwise")
    public String counterClockwiseLabel();

    @DefaultStringValue("Indicates if the shape should be drawn counter clockwise.")
    public String counterClockwiseDescription();

    @DefaultStringValue("Control Points")
    public String controlPointsLabel();

    @DefaultStringValue("The control points of a Bezier or Quadratic curve.")
    public String controlPointsDescription();

    @DefaultStringValue("Text Baseline")
    public String textBaseLineLabel();

    @DefaultStringValue("Vertical positioning for the text in the canvas.")
    public String textBaseLineDescription();

    @DefaultStringValue("Text Align")
    public String textAlignLabel();

    @DefaultStringValue("Horizontal positioning for the text in the canvas.")
    public String textAlignDescription();

    @DefaultStringValue("Clipped Image Width")
    public String clippedImageWidthLabel();

    @DefaultStringValue("The width of the clipped image (i.e., x coordinate where clipping ends).")
    public String clippedImageWidthDescription();

    @DefaultStringValue("Clipped Image Height")
    public String clippedImageHeightLabel();

    @DefaultStringValue("The height of the clipped image (i.e., y coordinate where clipping ends).")
    public String clippedImageHeightDescription();

    @DefaultStringValue("Clipped Image Destination Width")
    public String clippedImageDestinationWidthLabel();

    @DefaultStringValue("The destination width of the clipped image.")
    public String clippedImageDestinationWidthDescription();

    @DefaultStringValue("Clipped Image Destination Height")
    public String clippedImageDestinationHeightLabel();

    @DefaultStringValue("The destination height of the clipped image.")
    public String clippedImageDestinationHeightDescription();

    @DefaultStringValue("Clipped Image X")
    public String clippedImageStartXLabel();

    @DefaultStringValue("The x coordinate where clipping for the image begins.")
    public String clippedImageStartXDescription();

    @DefaultStringValue("Clipped Image Y")
    public String clippedImageStartYLabel();

    @DefaultStringValue("The y coordinate where clipping for the image begins.")
    public String clippedImageStartYDescription();

    @DefaultStringValue("Picture Category")
    public String pictureCategoryLabel();

    @DefaultStringValue("Picture Category used when reporting which images loaded.")
    public String pictureCategoryDescription();

    @DefaultStringValue("Serialization Mode")
    public String serializationModeLabel();

    @DefaultStringValue("Used when deserializing a Picture.")
    public String serializationModeDescription();

    @DefaultStringValue("Resource ID")
    public String resourceIDLabel();

    @DefaultStringValue("Used when deserializing a Picture with SerializationMode.RESOURCE_ID.")
    public String resourceIDDescription();

    @DefaultStringValue("URL")
    public String urlLabel();

    @DefaultStringValue("Source URL for the image.")
    public String urlDescription();

    @DefaultStringValue("Loop")
    public String loopLabel();

    @DefaultStringValue("Indicates if the movie should loop.")
    public String loopDescription();

    @DefaultStringValue("Volume")
    public String volumeLabel();

    @DefaultStringValue("The movie's (audio-only or video) volume.")
    public String volumeDescription();

    @DefaultStringValue("Base Width")
    public String baseWidthLabel();

    @DefaultStringValue("The width of the non-pointy end of an arrow.")
    public String baseWidthDescription();

    @DefaultStringValue("Head Width")
    public String headWidthLabel();

    @DefaultStringValue("The width of the side of the triangle formed by the tip of the arrow, which is parallel to the base.")
    public String headWidthDescription();

    @DefaultStringValue("Arrow Angle")
    public String arrowAngleLabel();

    @DefaultStringValue("The angle between the midline and the outer diagonal of the arrow's tip.")
    public String arrowAngleDescription();

    @DefaultStringValue("Base Angle")
    public String baseAngleLabel();

    @DefaultStringValue("The angle between the outer diagonal and the inner diagonal of the arrow's tip.")
    public String baseAngleDescription();

    @DefaultStringValue("Arrow Type")
    public String arrowTypeLabel();

    @DefaultStringValue("Indicates at which end the tip of the arrow should be.")
    public String arrowTypeDescription();

    @DefaultStringValue("Transform")
    public String transformLabel();

    @DefaultStringValue("The transformation matrix.")
    public String transformDescription();

    @DefaultStringValue("Miter Limit")
    public String miterLimitTypeLabel();

    @DefaultStringValue("The pixel limit Miter LineJoins extend.")
    public String miterLimitTypeDescription();

    @DefaultStringValue("Curve Factor")
    public String curveFactorTypeLabel();

    @DefaultStringValue("The curvyness factor applied to curves on a spline.")
    public String curveFactorTypeDescription();

    @DefaultStringValue("Angle Factor")
    public String angleFactorTypeLabel();

    @DefaultStringValue("The angle factor applied to curves on a spline.")
    public String angleFactorTypeDescription();

    @DefaultStringValue("Line Flatten")
    public String lineFlattenTypeLabel();

    @DefaultStringValue("If we flatten 3 co-linear points on a spline.")
    public String lineFlattenTypeDescription();
}
