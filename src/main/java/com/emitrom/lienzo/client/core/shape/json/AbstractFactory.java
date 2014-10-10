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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import com.emitrom.lienzo.client.core.Attribute;
import com.emitrom.lienzo.client.core.AttributeType;
import com.emitrom.lienzo.client.core.shape.IJSONSerializable;

/**
 * AbstractFactory is an abstract implementation of {@IFactory}.
 *
 * @param <T>
 * @since 1.1
 */
public abstract class AbstractFactory<T extends IJSONSerializable<T>> implements IFactory<T>
{
    private final ArrayList<Attribute>       m_requiredAttributes = new ArrayList<Attribute>();

    private final HashMap<String, Attribute> m_attributeMap       = new HashMap<String, Attribute>();

    private String                           m_typeName;

    protected AbstractFactory(String typeName)
    {
        m_typeName = typeName;
    }

    /**
     * Only factories that wish to extend other factories should use this.
     * 
     * @param typeName
     */
    protected void setTypeName(String typeName)
    {
        m_typeName = typeName;
    }

    @Override
    public String getTypeName()
    {
        return m_typeName;
    }

    protected void addAttribute(Attribute attr, boolean required)
    {
        // Allow setting the attribute twice to override the requiredness
        // with a different value.
        
        if (!m_attributeMap.containsKey(attr.getProperty())) m_attributeMap.put(attr.getProperty(), attr);

        if (required)
        {
            if (!m_requiredAttributes.contains(attr)) m_requiredAttributes.add(attr);
        }
        else
        {
            m_requiredAttributes.remove(attr);
        }
    }

    /**
     * Add optional attribute
     * @param attr
     */
    protected void addAttribute(Attribute attr)
    {
        addAttribute(attr, false);
    }

    public Collection<Attribute> getAttributeSheet()
    {
        return Collections.unmodifiableCollection(m_attributeMap.values());
    }

    public Collection<Attribute> getRequiredAttributes()
    {
        return Collections.unmodifiableCollection(m_requiredAttributes);
    }

    public AttributeType getAttributeType(String type)
    {
        return m_attributeMap.get(type).getType();
    }
}
