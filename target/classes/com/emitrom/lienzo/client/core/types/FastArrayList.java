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

import com.google.gwt.core.client.JavaScriptObject;

/**
 * A facade implementation in JavaScript for fast Lists.
 */
public final class FastArrayList<M>
{
    private final FastArrayListJSO<M> m_jso;

    public FastArrayList()
    {
        m_jso = FastArrayListJSO.make().cast();
    }

    public FastArrayList(M value, M... values)
    {
        this();

        add(value);

        if ((null != values) && (values.length > 0))
        {
            for (int i = 0; i < values.length; i++)
            {
                add(values[i]);
            }
        }
    }

    private FastArrayList(FastArrayListJSO<M> jso)
    {
        if (null == jso)
        {
            m_jso = FastArrayListJSO.make().cast();
        }
        else
        {
            m_jso = jso;
        }
    }

    /**
     * Return the List's size.
     * @return int
     */
    public final int length()
    {
        return m_jso.length();
    }

    /**
     * Return the primitive found at the specified index.
     * @param index
     * @return
     */
    public final M get(int index)
    {
        if ((index >= 0) && (index < length()))
        {
            return m_jso.get(index);
        }
        return null;
    }

    /**
     * Add a value to the List
     * @param value
     */
    public final void add(M value)
    {
        m_jso.add(value);
    }

    /**
     * Add a value to the List
     * @param value
     */
    public final void add(int i, M value)
    {
        m_jso.add(i, value);
    }

    /**
     * Return true if the List contains the passed in value.
     * 
     * @param value
     * @return boolean
     */
    public final boolean contains(M value)
    {
        return m_jso.contains(value);
    }

    /**
     * Remove all values from the List.
     */
    public final void removeAll()
    {
        m_jso.removeAll();
    }

    /**
     * Remove the value passed in as argument from the List.
     * @param value
     */
    public final void remove(M value)
    {
        m_jso.remove(value);
    }

    public final void unshift(M value)
    {
        m_jso.unshift(value);
    }

    public final void moveUp(M value)
    {
        m_jso.moveUp(value);
    }

    public final void moveDown(M value)
    {
        m_jso.moveDown(value);
    }

    public final void moveToTop(M value)
    {
        if ((length() < 2) || (false == contains(value)))
        {
            return;
        }
        remove(value);

        add(value);
    }

    public final void moveToBottom(M value)
    {
        if ((length() < 2) || (false == contains(value)))
        {
            return;
        }
        remove(value);

        unshift(value);
    }

    public final M pop()
    {
        return m_jso.pop();
    }

    public final M shift()
    {
        return m_jso.shift();
    }

    public final void splice(int beg, int removed, M value)
    {
        m_jso.splice(beg, removed, value);
    }

    public final void splice(int beg, int removed)
    {
        m_jso.splice(beg, removed);
    }

    public final void reverse()
    {
        m_jso.reverse();
    }

    public final FastArrayList<M> copy()
    {
        return new FastArrayList<M>(m_jso.copy());
    }

    public final FastArrayList<M> concat(FastArrayList<M> value)
    {
        if (null == value)
        {
            return new FastArrayList<M>(m_jso.copy());
        }
        return new FastArrayList<M>(m_jso.concat(value.m_jso));
    }

    public final FastArrayList<M> slice(int beg)
    {
        return new FastArrayList<M>(m_jso.slice(beg));
    }

    public final FastArrayList<M> slice(int beg, int end)
    {
        return new FastArrayList<M>(m_jso.slice(beg, end));
    }

    private static final class FastArrayListJSO<M> extends JavaScriptObject
    {
        protected FastArrayListJSO()
        {

        }

        public static final JavaScriptObject make()
        {
            return JavaScriptObject.createArray();
        }

        public final native int length()
        /*-{
			return this.length;
        }-*/;

        public final native M get(int indx)
        /*-{
			return this[indx];
        }-*/;

        public final native void add(M value)
        /*-{
			this[this.length] = value;
        }-*/;

        public final native void add(int i, M value)
        /*-{
			this[i] = value;
        }-*/;

        public final native boolean contains(M value)
        /*-{
			for ( var i = 0; i < this.length; i++) {
				if (this[i] == value) {
					return true;
				}
			}
			return false;
        }-*/;

        public final native void removeAll()
        /*-{
			this.length = 0;
        }-*/;

        public final native void remove(M value)
        /*-{
			for ( var i = 0; i < this.length; i++) {
				if (this[i] == value) {
					this.splice(i, 1);
					break;
				}
			}
        }-*/;

        public final native void moveUp(M value)
        /*-{
			var leng = this.length;

			if (leng < 2) {
				return;
			}
			for ( var i = 0; i < leng; i++) {
				if (this[i] == value) {
					var j = i + 1;
					if (j != leng) {
						this[i] = this[j];
						this[j] = value;
					}
					break;
				}
			}
        }-*/;

        public final native void moveDown(M value)
        /*-{
			var leng = this.length;

			if (leng < 2) {
				return;
			}
			for ( var i = 0; i < leng; i++) {
				if (this[i] == value) {
					if (i != 0) {
						var j = i - 1;
						this[i] = this[j];
						this[j] = value;
					}
					break;
				}
			}
        }-*/;

        public final native void unshift(M value)
        /*-{
			this.unshift(value);
        }-*/;

        public final native void splice(int beg, int removed, M value)
        /*-{
			this.splice(beg, removed, value);
        }-*/;

        public final native void splice(int beg, int removed)
        /*-{
			this.splice(beg, removed);
        }-*/;

        public final native M shift()
        /*-{
			return this.shift();
        }-*/;

        public final native M pop()
        /*-{
			return this.pop();
        }-*/;

        public final native void push(M value)
        /*-{
			this[this.length] = value;
        }-*/;

        public final native void reverse()
        /*-{
			this.reverse();
        }-*/;

        public final native FastArrayListJSO<M> concat(FastArrayListJSO<M> value)
        /*-{
			return this.concat(value);
        }-*/;

        public final native FastArrayListJSO<M> copy()
        /*-{
			return this.concat();
        }-*/;

        public final native FastArrayListJSO<M> slice(int beg)
        /*-{
			return this.slice(beg);
        }-*/;

        public final native FastArrayListJSO<M> slice(int beg, int end)
        /*-{
			return this.slice(beg, end);
        }-*/;
    }
}
