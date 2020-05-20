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

import java.util.HashMap;
import java.util.Map;

import fr.paris.lutece.plugins.unittree.modules.dansmarue.service.role.SectorResourceIdService;

/**
 * Classe permettant de faire le mapping entre les jsp et leur permission associ�e.
 *
 */
public class MappingJspPermission
{
    private MappingJspPermission( )
    {
    }

    /** The Constant MAPPING_JSP_PERMISSIONS. */
    protected static final Map<String, PermissionResourceType> MAPPING_JSP_PERMISSIONS;

    static
    {
        MAPPING_JSP_PERMISSIONS = new HashMap<>( );

        MAPPING_JSP_PERMISSIONS.put( "ModifySector.jsp", new PermissionResourceType( SectorResourceIdService.KEY_ID_RESOURCE, SectorResourceIdService.PERMISSION_DELETE_SECTOR ) );

        MAPPING_JSP_PERMISSIONS.put( "DoDeleteSectors.jsp", new PermissionResourceType( SectorResourceIdService.KEY_ID_RESOURCE, SectorResourceIdService.PERMISSION_DELETE_SECTOR ) );

        MAPPING_JSP_PERMISSIONS.put( "DeleteSectors.jsp", new PermissionResourceType( SectorResourceIdService.KEY_ID_RESOURCE, SectorResourceIdService.PERMISSION_DELETE_SECTOR ) );
    }
}
