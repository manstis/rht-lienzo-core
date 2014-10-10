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

package com.emitrom.lienzo.client.core.types;

import com.emitrom.lienzo.client.core.types.Point2D.Point2DJSO;
import com.emitrom.lienzo.client.core.util.GeometryException;
import com.emitrom.lienzo.client.core.util.Matrix;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;

/**
 * Transform is an affine transformation matrix.
 * <p>
 * In general, an affine transformation is a composition of rotations, translations (i.e. offsets), dilations (i.e. scaling), and shears.
 * The underlying matrix is a 3x3 matrix of which the last 3 coordinates are hardcoded, so internally we only store 6 coordinates.
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Affine_transformation">http://en.wikipedia.org/wiki/Affine_transformation</a>
 * @see <a href="http://docs.oracle.com/javase/6/docs/api/java/awt/geom/AffineTransform.html">http://docs.oracle.com/javase/6/docs/api/java/awt/geom/AffineTransform.html</a>
 * @see <a href="http://mathworld.wolfram.com/AffineTransformation.html">http://mathworld.wolfram.com/AffineTransformation.html</a>
 */
public final class Transform
{
    private final TransformJSO m_jso;

    /**
     * Constructs a new <code>Transform</code> representing the
     * Identity transformation.
     */
    public Transform()
    {
        m_jso = TransformJSO.make();
    }

    /**
     * Constructs a new <code>Transform</code> from 6 floating point
     * values representing the 6 specifiable entries of the 3x3
     * transformation matrix.
     *
     * @param m00 the X coordinate scaling element of the 3x3 matrix
     * @param m10 the Y coordinate shearing element of the 3x3 matrix
     * @param m01 the X coordinate shearing element of the 3x3 matrix
     * @param m11 the Y coordinate scaling element of the 3x3 matrix
     * @param m02 the X coordinate translation element of the 3x3 matrix
     * @param m12 the Y coordinate translation element of the 3x3 matrix
     */
    public Transform(double m00, double m10, double m01, double m11, double m02, double m12)
    {
        m_jso = TransformJSO.makeFromDoubles(m00, m10, m01, m11, m02, m12);
    }

    /**
     * Constructs a new <code>Transform</code> from 6 floating point
     * values representing the 6 specifiable entries of the 3x3
     * transformation matrix. 
     *
     * @param m an array with [m00, m10, m01, m11, m02, m12] where:
     * @param m00 the X coordinate scaling element of the 3x3 matrix
     * @param m10 the Y coordinate shearing element of the 3x3 matrix
     * @param m01 the X coordinate shearing element of the 3x3 matrix
     * @param m11 the Y coordinate scaling element of the 3x3 matrix
     * @param m02 the X coordinate translation element of the 3x3 matrix
     * @param m12 the Y coordinate translation element of the 3x3 matrix
     */
    public Transform(double[] m)
    {
        this(m[0], m[1], m[2], m[3], m[4], m[5]);
    }

    /**
     * Constructs a Transform from a TransformJSO. Used internally.
     * 
     * @param jso TransformJSO
     */
    public Transform(TransformJSO jso)
    {
        m_jso = jso;
    }

    /**
     * Returns a copy of this Transform. 
     * The original Transform is not affected.
     * 
     * @return Transform
     */
    public Transform copy()
    {
        return new Transform(m_jso.copy());
    }

    /**
     * Concatenates this transform with a translation transformation.
     * It basically moves a node with the specified offset (tx,ty).
     * 
     * This is equivalent to calling concatenate(T), where T is an
     * <code>Transform</code> represented by the following matrix:
     * <pre>
     *      [   1    0    tx  ]
     *      [   0    1    ty  ]
     *      [   0    0    1   ]
     * </pre>
     * @param tx the distance by which coordinates are translated in the
     * X axis direction
     * @param ty the distance by which coordinates are translated in the
     * Y axis direction
     * 
     * @return this Transform
     */
    public final Transform translate(double tx, double ty)
    {
        m_jso.translate(tx, ty);

        return this;
    }

    /**
     * Concatenates this transform with a scaling transformation.
     * This is equivalent to calling concatenate(S), where S is an
     * <code>Transform</code> represented by the following matrix:
     * <pre>
     *      [   sx   0    0   ]
     *      [   0    sy   0   ]
     *      [   0    0    1   ]
     * </pre>
     * @param sx the factor by which coordinates are scaled along the   
     * X axis direction
     * @param sy the factor by which coordinates are scaled along the
     * Y axis direction 
     * @return this Transform
     */
    public final Transform scale(double sx, double sy)
    {
        m_jso.scale(sx, sy);

        return this;
    }

    /**
     * Concatenates this transform with a scaling transformation.
     * Same as <pre>scale(scaleFactor, scaleFactor)</pre>
     * 
     * @see #scale(double, double)
     * @param scaleFactor used as the scale factor for both x and y directions 
     * @return this Transform
     */
    public final Transform scale(double scaleFactor)
    {
        m_jso.scale(scaleFactor, scaleFactor);

        return this;
    }

    /**
     * Concatenates this transform with a shearing transformation.
     * This is equivalent to calling concatenate(SH), where SH is an
     * <code>Transform</code> represented by the following matrix:
     * <pre>
     *      [   1   shx   0   ]
     *      [  shy   1    0   ]
     *      [   0    0    1   ]
     * </pre>
     * @param shx the multiplier by which coordinates are shifted in the
     * direction of the positive X axis as a factor of their Y coordinate
     * @param shy the multiplier by which coordinates are shifted in the
     * direction of the positive Y axis as a factor of their X coordinate
     * @return this Transform
     */
    public final Transform shear(double shx, double shy)
    {
        m_jso.shear(shx, shy);

        return this;
    }

    /**
     * Concatenates this transform with a rotation transformation.
     * This is equivalent to calling concatenate(R), where R is an
     * <code>Transform</code> represented by the following matrix:
     * <pre>
     *      [   cos(theta)    -sin(theta)    0   ]
     *      [   sin(theta)     cos(theta)    0   ]
     *      [       0              0         1   ]
     * </pre>
     * Rotating by a positive angle theta rotates points on the positive
     * X axis toward the positive Y axis.
     * 
     * @param theta the angle of rotation measured in radians
     * 
     * @return this Transform
     */
    public final Transform rotate(double theta)
    {
        m_jso.rotate(theta);

        return this;
    }

    /**
     * Same as {@link #concatenate(Transform)}
     * 
     * @param transform
     * 
     * @return this Transform
     */
    public final Transform multiply(Transform transform)
    {
        m_jso.multiply(transform.getJSO());

        return this;
    }

    /**
     * Concatenates a <code>Transform</code> <code>Tx</code> to
     * this <code>Transform</code> Cx in the most commonly useful
     * way to provide a new user space
     * that is mapped to the former user space by <code>Tx</code>.
     * Cx is updated to perform the combined transformation.
     * Transforming a point p by the updated transform Cx' is
     * equivalent to first transforming p by <code>Tx</code> and then
     * transforming the result by the original transform Cx like this:
     * Cx'(p) = Cx(Tx(p))  
     * In matrix notation, if this transform Cx is
     * represented by the matrix [this] and <code>Tx</code> is represented
     * by the matrix [Tx] then this method does the following:
     * <pre>
     *      [this] = [this] x [Tx]
     * </pre>
     * @param Tx the <code>Transform</code> object to be
     * concatenated with this <code>Transform</code> object.
     */
    public final Transform concatenate(Transform transform)
    {
        m_jso.multiply(transform.getJSO());

        return this;
    }

    /**
     * Returns the inverse transform Tx' of this transform Tx, which
     * maps coordinates transformed by Tx back
     * to their original coordinates.
     * In other words, Tx'(Tx(p)) = p = Tx(Tx'(p)).
     * <p>
     * If this transform maps all coordinates onto a point or a line
     * then it will not have an inverse, since coordinates that do
     * not lie on the destination point or line will not have an inverse
     * mapping.
     * The <code>getDeterminant</code> method can be used to determine if this
     * transform has no inverse, in which case an exception will be
     * thrown if the <code>invert</code> method is called.
     * @see #getDeterminant
     * @exception GeometryException if the matrix cannot be inverted.
     * @return a new Transform
     */
    public final Transform getInverse() throws GeometryException
    {
        if (Math.abs(m_jso.getDeterminant()) <= Double.MIN_VALUE)
        {
            throw new GeometryException("Can't invert this matrix - determinant is near 0");
        }
        return new Transform(m_jso.getInverse());
    }

    /**
     * Transforms the specified <code>ptSrc</code> and stores the result
     * in <code>ptDst</code>.
     * If <code>ptDst</code> is <code>null</code>, a new {@link Point2D}
     * object is allocated and then the result of the transformation is
     * stored in this object.
     * In either case, <code>ptDst</code>, which contains the
     * transformed point, is returned for convenience.
     * If <code>ptSrc</code> and <code>ptDst</code> are the same
     * object, the input point is correctly overwritten with
     * the transformed point.
     * @param ptSrc the specified <code>Point2D</code> to be transformed
     * @param ptDst the specified <code>Point2D</code> that stores the
     * result of transforming <code>ptSrc</code>
     * @return the <code>ptDst</code> after transforming
     * <code>ptSrc</code> and storing the result in <code>ptDst</code>.
     */
    public final void transform(Point2D ptSrc, Point2D ptDst)
    {
        m_jso.transform(ptSrc.getJSO(), ptDst.getJSO());
    }

    /**
     * Concatenates this transform with a translation, a rotation and another translation transformation, 
     * resulting in an scaling with respect to the specified point (x,y).
     * <p>
     * Equivalent to:
     * <pre>
     *  translate(x, y);
     *  scale(scale, scale);
     *  translate(-x, -y);
     *  </pre>
     * @param scale
     * @param x
     * @param y
     */
    public Transform scaleAboutPoint(double scale, double x, double y)
    {
        translate(x, y);
        
        scale(scale, scale);
        
        translate(-x, -y);
        
        return this;
    }

    /**
     * Returns the X coordinate scaling element (m00) of the 3x3
     * affine transformation matrix.
     * @return a double value that is the X coordinate of the scaling
     *  element of the affine transformation matrix.
     */
    public double getScaleX()
    {
        return get(0);
    }

    /**
     * Returns the Y coordinate scaling element (m11) of the 3x3
     * affine transformation matrix.
     * @return a double value that is the Y coordinate of the scaling
     *  element of the affine transformation matrix.
     */
    public double getScaleY()
    {
        return get(3);
    }

    /**
    * Returns the X coordinate shearing element (m01) of the 3x3
    * affine transformation matrix.
    * @return a double value that is the X coordinate of the shearing
    *  element of the affine transformation matrix.
    */
    public double getShearX()
    {
        return get(2);
    }

    /**
     * Returns the Y coordinate shearing element (m10) of the 3x3
     * affine transformation matrix.
     * @return a double value that is the Y coordinate of the shearing
     *  element of the affine transformation matrix.
     */
    public double getShearY()
    {
        return get(1);
    }

    /**
     * Returns the X coordinate of the translation element (m02) of the
     * 3x3 affine transformation matrix.
     * @return a double value that is the X coordinate of the translation
     *  element of the affine transformation matrix.
     */
    public double getTranslateX()
    {
        return get(4);
    }

    /**
     * Returns the Y coordinate of the translation element (m12) of the
     * 3x3 affine transformation matrix.
     * @return a double value that is the Y coordinate of the translation
     *  element of the affine transformation matrix. 
     */
    public double getTranslateY()
    {
        return get(5);
    }

    /**
     * Returns the underlying matrix values.
     * 
     * @param i index into the array [m00, m10, m01, m11, m02, m12]
     * @return matrix value
     */
    public final double get(int i)
    {
        return m_jso.get(i);
    }

    /**
     * Returns the TransformJSO Javascript object. Used internally.
     * @return TransformJSO
     */
    public final TransformJSO getJSO()
    {
        return m_jso;
    }

    /**
     * Returns a string representation of the underlying values for debugging purposes.
     * The values are: [m00, m10, m01, m11, m02, m12]
     * 
     * @return String e.g. "[1,0,0,1,0,0]"
     */
    public final String toString()
    {
        return new JSONArray(m_jso).toString();
    }

    /**
     * Returns a Transform that converts 
     * the 3 source points into the 3 destination points.
     * 
     * @param src       Array with 3 (different) source points
     * @param target    Array with 3 target points
     * @return Transform
     */
    public static final Transform create3PointTransform(Point2DArray src, Point2DArray target)
    {
        // Determine T so that:
        //
        // T * P = P' for each Point
        //
        // where T = (a b c)
        // (d e f)
        //
        // T * P => x' = (ax + by + c)
        // y' = (dx + ey + f)
        //
        // Given are 3 points and their projections: P1, P1', P2, P2', P3, P3'.
        // P1 is (x1, y1) P1' is (x1', y1')
        //
        // (ax1 + by1 + c ) = x1'
        // ( dx1 + ey1 + f) = y1'
        // (ax2 + by2 + c ) = x2'
        // ( dx2 + ey2 + f) = y2'
        // (ax3 + by3 + c ) = x3'
        // ( dx3 + ey3 + f) = y3'

        Point2D p1 = src.getPoint(0);
        Point2D p2 = src.getPoint(1);
        Point2D p3 = src.getPoint(2);

        Point2D p1_ = target.getPoint(0);
        Point2D p2_ = target.getPoint(1);
        Point2D p3_ = target.getPoint(2);

        double[][] eq = { { p1.getX(), p1.getY(), 1, 0, 0, 0 }, { 0, 0, 0, p1.getX(), p1.getY(), 1 }, { p2.getX(), p2.getY(), 1, 0, 0, 0 }, { 0, 0, 0, p2.getX(), p2.getY(), 1 }, { p3.getX(), p3.getY(), 1, 0, 0, 0 }, { 0, 0, 0, p3.getX(), p3.getY(), 1 }, };

        double[][] s = { { p1_.getX(), p1_.getY(), p2_.getX(), p2_.getY(), p3_.getX(), p3_.getY() } };
        Matrix m = new Matrix(eq);
        Matrix rhs = new Matrix(s).transpose();
        Matrix T = m.solve(rhs);

        double[][] d = T.getData();
        return new Transform(d[0][0], d[3][0], d[1][0], d[4][0], d[2][0], d[5][0]);
    }

    /**
     * Creates a Transform for a viewport. The visible area is defined by the rectangle
     * [x, y, width, height] and the viewport's width and height.
     * 
     * @param x X coordinate of the top-left corner of the new view area.
     * @param y Y coordinate of the top-left corner of the new view area.
     * @param width Width of the new view area.
     * @param height Height of the new View area.
     * @param viewportWidth Width of the Viewport.
     * @param viewportHeight Height of the Viewport.
     * @return Transform
     */
    public static Transform createViewportTransform(double x, double y, double width, double height, double viewportWidth, double viewportHeight)
    {
        if (width <= 0 || height <= 0)
        {
            return null;
        }
        double scaleX = viewportWidth / width;

        double scaleY = viewportHeight / height;

        double scale;

        if (scaleX > scaleY)
        {
            // use scaleY

            scale = scaleY;

            double dw = viewportWidth / scale - width;

            x -= dw / 2;

            width += dw;
        }
        else
        {
            scale = scaleX;

            double dh = viewportHeight / scale - height;

            y -= dh / 2;

            height += dh;
        }
        // x' = m[0] + x*m[1] y' = m[2] + y*m[3]

        double m02 = -x * scale;

        double m12 = -y * scale;

        return new Transform(scale, 0, 0, scale, m02, m12);
    }

    /**
     * Javascript class to store the Transform matrix values.
     * It's an array with 6 values:
     * 
     * [0] m00, [1] m10, [2] m01, [3] m11, [4] m02, [5] m12
     *
     */
    public static final class TransformJSO extends JavaScriptObject
    {
        protected TransformJSO()
        {

        }

        public static final native TransformJSO make()
        /*-{
			return [ 1, 0, 0, 1, 0, 0 ];
        }-*/;

        public static final native TransformJSO makeFromDoubles(double m00, double m10, double m01, double m11, double m02, double m12)
        /*-{
			return [ m00, m10, m01, m11, m02, m12 ];
        }-*/;

        public final native void translate(double x, double y)
        /*-{
			this[4] += this[0] * x + this[2] * y;

			this[5] += this[1] * x + this[3] * y;
        }-*/;

        public final native TransformJSO copy()
        /*-{
			return [ this[0], this[1], this[2], this[3], this[4], this[5] ];
        }-*/;

        public final native void scale(double sx, double sy)
        /*-{
			this[0] *= sx;

			this[3] *= sy;
        }-*/;

        public final native void shear(double shx, double shy)
        /*-{
			var m00 = this[0];

			var m10 = this[1];

			this[0] += shy * this[2];

			this[1] += shy * this[3];

			this[2] += shx * m00;

			this[3] += shx * m10;
        }-*/;

        public final native void rotate(double rad)
        /*-{
			var c = Math.cos(rad);

			var s = Math.sin(rad);

			var m11 = this[0] * c + this[2] * s;

			var m12 = this[1] * c + this[3] * s;

			var m21 = this[0] * -s + this[2] * c;

			var m22 = this[1] * -s + this[3] * c;

			this[0] = m11;

			this[1] = m12;

			this[2] = m21;

			this[3] = m22;
        }-*/;

        public final native void multiply(TransformJSO transform)
        /*-{
			var m11 = this[0] * transform[0] + this[2] * transform[1];

			var m12 = this[1] * transform[0] + this[3] * transform[1];

			var m21 = this[0] * transform[2] + this[2] * transform[3];

			var m22 = this[1] * transform[2] + this[3] * transform[3];

			var dx = this[0] * transform[4] + this[2] * transform[5] + this[4];

			var dy = this[1] * transform[4] + this[3] * transform[5] + this[5];

			this[0] = m11;

			this[1] = m12;

			this[2] = m21;

			this[3] = m22;

			this[4] = dx;

			this[5] = dy;
        }-*/;

        public final native double getDeterminant()
        /*-{
			return this[0] * this[3] - this[2] * this[1]; // m00 * m11 - m01 * m10
        }-*/;

        public final native TransformJSO getInverse()
        /*-{
			//[0] m00, [1] m10, [2] m01, [3] m11, [4] m02, [5] m12
			var m00 = this[0];
			var m10 = this[1];
			var m01 = this[2];
			var m11 = this[3];
			var m02 = this[4];
			var m12 = this[5];

			var det = m00 * m11 - m01 * m10;

			return [ m11 / det, -m10 / det, -m01 / det, m00 / det,
					(m01 * m12 - m11 * m02) / det,
					(m10 * m02 - m00 * m12) / det ];
        }-*/;

        public final native double get(int i)
        /*-{
			return this[i];
        }-*/;

        public final native void transform(Point2DJSO src, Point2DJSO target)
        /*-{
			var x = src.x;
			var y = src.y;
			target.x = x * this[0] + y * this[2] + this[4];
			target.y = x * this[1] + y * this[3] + this[5];
        }-*/;
    }
}
