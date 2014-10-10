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

package com.emitrom.lienzo.client.core.shape.json;

import java.util.Set;

import com.emitrom.lienzo.client.core.Attribute;
import com.emitrom.lienzo.client.core.AttributeType;
import com.emitrom.lienzo.client.core.shape.IContainer;
import com.emitrom.lienzo.client.core.shape.IJSONSerializable;
import com.emitrom.lienzo.client.core.shape.Node;
import com.emitrom.lienzo.client.core.shape.json.validators.ValidationContext;
import com.emitrom.lienzo.client.core.shape.json.validators.ValidationException;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

/**
 * JSONDeserializer deserializes {@link IJSONSerializable} objects from JSON strings.
 * IJSONSerializables can be serialized with {@link IJSONSerializable#toJSONString()}
 *
 * @see Node#toJSONString()
 */
public final class JSONDeserializer
{
    private static final JSONDeserializer s_instance = new JSONDeserializer();

    public static final JSONDeserializer getInstance() // questionable? do we allow sub-classing? this is always a problem with singletons. Should the class be final?
    {
        return s_instance;
    }

    private JSONDeserializer()
    {
        // There can be only one. Therefore it's a Singleton and can't be sub-classed, all methods and variables can be final
    }

    /**
     * Parses the JSON string and returns the {@link IJSONSerializable}.
     * Same as {@link #fromString(String, boolean)} with validate set to true.
     * 
     * @param string JSON string as produced by {@link IJSONSerializable#toJSONString()}
     * @return IJSONSerializable
     */
    public final IJSONSerializable<?> fromString(String string)
    {
        return fromString(string, true);
    }

    /**
     * Parses the JSON string and returns the IJSONSerializable.
     * If validate is true, it will attempt to validate the attributes and types of child nodes etc.
     * If validate is false, it assumes the JSON string is correct
     * (this is a little faster.)
     * 
     * @param string JSON string as produced by {@link IJSONSerializable#toJSONString()}
     * @param validate Whether to validate the attributes and child node types
     * @return IJSONSerializable
     */
    public final IJSONSerializable<?> fromString(String string, boolean validate)
    {
        if ((null == string) || (string = string.trim()).isEmpty())
        {
            return null;
        }
        JSONValue value = JSONParser.parseStrict(string);

        if (null == value)
        {
            return null;
        }
        JSONObject json = value.isObject();

        if (null == json)
        {
            return null;
        }
        try
        {
            ValidationContext ctx = new ValidationContext();

            ctx.setValidate(validate);

            ctx.setStopOnError(true); // bail if an error is encountered

            return fromJSON(json, ctx);
        }
        catch (ValidationException e)
        {
            return null;
        }
    }

    /**
     * Parses the JSON string and returns the IJSONSerializable.
     * Use this method if you need to parse JSON that may contain one or more errors.
     * <pre>
     * ValidationContext ctx = new ValidationContext();
     * ctx.setValidate(true);
     * ctx.setStopOnError(false); // find all errors
     * IJSONSerializable<?> node = JSONDeserializer.getInstance().fromString(jsonString, ctx);
     * if (ctx.getErrorCount() > 0)
     * {
     *   Console.log(ctx.getDebugString());
     * }
     * </pre>
     * 
     * @param string JSON string as produced by {@link IJSONSerializable#toJSONString()}
     * @param ctx ValidationContext
     * @return IJSONSerializable
     */
    public final IJSONSerializable<?> fromString(String string, ValidationContext ctx)
    {
        if ((null == string) || (string = string.trim()).isEmpty())
        {
            return null;
        }
        JSONValue value = JSONParser.parseStrict(string);

        if (null == value)
        {
            return null;
        }
        JSONObject json = value.isObject();

        if (null == json)
        {
            return null;
        }
        try
        {
            return fromJSON(json, ctx);
        }
        catch (ValidationException e)
        {
            return null;
        }
    }

    /**
     * Creates a IJSONSerializable from the JSONObject, using the ValidationContext.
     * <p>
     * You should only call this when you're writing your own node class
     * and you're building a custom {@link IFactory}.
     * 
     * @param json JSONObject
     * @param ctx ValidationContext
     * @return IJSONSerializable
     * @throws ValidationException
     */
    public final IJSONSerializable<?> fromJSON(JSONObject json, ValidationContext ctx) throws ValidationException
    {
        if (null == json)
        {
            return null;
        }
        String type = null;

        IFactory<?> factory = null;

        JSONValue tval = json.get("type");

        ctx.push("type");

        if (null == tval)
        {
            ctx.addRequiredError();
        }
        else
        {
            JSONString styp = tval.isString();

            if (null == styp)
            {
                ctx.addBadTypeError("String");
            }
            else
            {
                type = styp.stringValue();

                factory = FactoryRegistry.getInstance().getFactory(type);

                if (null == factory)
                {
                    ctx.addMissingNodeFactoryError(type);
                }
            }
        }
        ctx.pop(); // type

        if (null == factory)
        {
            return null;
        }
        else
        {
            if (ctx.isValidate())
            {
                // we don't need to validate during a copy operation!

                validateAttributes(json, factory, type, ctx);
            }
            if (factory instanceof PostProcessNodeFactory)
            {
                IJSONSerializable<?> node = factory.create(json, ctx);

                if (null != node)
                {
                    ((PostProcessNodeFactory) factory).process(node);
                }
                return node;
            }
            else
            {
                return factory.create(json, ctx);
            }
        }
    }

    protected final void validateAttributes(JSONObject json, IFactory<?> factory, String type, ValidationContext ctx) throws ValidationException
    {
        JSONValue aval = json.get("attributes");

        if (null == aval)
        {
            return; // OK - 'attributes' is optional
        }
        ctx.push("attributes");

        JSONObject aobj = aval.isObject();

        if (aobj == null)
        {
            ctx.addBadTypeError("Object");

            return;
        }
        else
        {
            // Make sure all required attributes are defined (and not null)

            Set<String> keys = aobj.keySet();

            for (Attribute attr : factory.getRequiredAttributes())
            {
                String attrName = attr.getProperty();

                ctx.push(attrName);

                if (false == keys.contains(attrName))
                {
                    ctx.addRequiredError(); // value is missing
                }
                else
                {
                    JSONValue jval = aobj.get(attrName);

                    if (((jval == null) || (jval.isNull() != null)))
                    {
                        ctx.addRequiredError(); // value is null
                    }
                }
                ctx.pop(); // attrName
            }
            // Now check the attribute values

            for (String attrName : keys)
            {
                ctx.push(attrName);

                AttributeType atyp = factory.getAttributeType(attrName);

                if (atyp == null)
                {
                    ctx.addInvalidAttributeError(type);
                }
                else
                {
                    atyp.validate(aobj.get(attrName), ctx);
                }
                ctx.pop(); // attrName
            }
        }
        ctx.pop(); // attributes
    }

    /**
     * Creates the child nodes for a {@link IJSONSerializable} that implements 
     * {@link IContainer} from a JSONObject node.
     * <p>
     * You should only call this when you're writing your own {@link IContainer} class
     * and you're building a custom {@link IFactory}.
     * 
     * @param g IContainer
     * @param node parent JSONObject
     * @param containerFactory IContainerFactory
     * @param ctx ValidationContext
     * @throws ValidationException
     */
    @SuppressWarnings("unchecked")
    public final void deserializeChildren(@SuppressWarnings("rawtypes") IContainer g, JSONObject node, IContainerFactory containerFactory, ValidationContext ctx) throws ValidationException
    {
        // TODO I couldn't get the template parameters of IContainer to work with
        // GroupFactory and LayerFactory, so I'll use @SuppressWarnings

        JSONValue kidsVal = node.get("children");

        if (kidsVal == null)
        {
            return; // OK - 'children' is optional
        }
        ctx.push("children");

        JSONArray arr = kidsVal.isArray();

        if (arr == null)
        {
            ctx.addBadTypeError("Array");
        }
        else
        {
            final int size = arr.size();

            for (int i = 0, n = size; i < n; i++)
            {
                ctx.pushIndex(i);

                JSONValue kidVal = arr.get(i);

                JSONObject kidObj = kidVal.isObject();

                if (kidObj == null)
                {
                    ctx.addBadTypeError("Object");
                }
                else
                {
                    IJSONSerializable<?> kidNode = fromJSON(kidObj, ctx);

                    if (kidNode != null)
                    {
                        if (containerFactory.isValidForContainer(g, kidNode))
                        {
                            g.add(kidNode);
                        }
                    }
                }
                ctx.pop(); // index
            }
        }
        ctx.pop(); // children
    }
}
