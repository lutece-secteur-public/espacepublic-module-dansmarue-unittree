/*
 * Copyright (c) 2002-2020, City of Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.unittree.modules.dansmarue.service.impl;



/**
 * PermissionResourceType.
 */
public class PermissionResourceType
{
    
    /** The str permission. */
    private String _strPermission;
    
    /** The str resource type. */
    private String _strResourceType;

    /**
     * Creates a new PermissionTypeRessource.java object.
     *
     * @param strResourceType
     *         the resourceType
     * @param strPermission
     *         the permission
     */
    public PermissionResourceType( String strResourceType, String strPermission )
    {
        super(  );
        _strResourceType = strResourceType;
        _strPermission = strPermission;
    }

    /**
     * Sets the permission.
     *
     * @param strPermission            the permission to set
     */
    public void setPermission( String strPermission )
    {
        _strPermission = strPermission;
    }

    /**
     * Gets the permission.
     *
     * @return the permission
     */
    public String getPermission(  )
    {
        return _strPermission;
    }

    /**
     * Sets the ressource type.
     *
     * @param strResourceType            the resourceType to set
     */
    public void setRessourceType( String strResourceType )
    {
        _strResourceType = strResourceType;
    }

    /**
     * Gets the ressource type.
     *
     * @return the ressourceType
     */
    public String getRessourceType(  )
    {
        return _strResourceType;
    }
}
