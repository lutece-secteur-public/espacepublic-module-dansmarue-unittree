/*
 * Copyright (c) 2002-2022, City of Paris
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
package fr.paris.lutece.plugins.unittree.modules.dansmarue.service.role;

import java.util.Locale;

// Start of user code for imports
import fr.paris.lutece.portal.service.rbac.Permission;
import fr.paris.lutece.portal.service.rbac.ResourceIdService;
import fr.paris.lutece.portal.service.rbac.ResourceType;
import fr.paris.lutece.portal.service.rbac.ResourceTypeManager;
import fr.paris.lutece.util.ReferenceList;

// End of user code for imports

/**
 * SectorResourceIdService.
 */
public class SectorResourceIdService extends ResourceIdService
{

    /** The Constant KEY_ID_RESOURCE. */
    public static final String KEY_ID_RESOURCE = "UNIT_TYPE";

    /** The Constant PERMISSION_DELETE_SECTOR. */
    public static final String PERMISSION_DELETE_SECTOR = "MODIFY_SECTOR";

    /** The Constant PROPERTY_LABEL_RESOURCE_TYPE. */
    private static final String PROPERTY_LABEL_RESOURCE_TYPE = "module.unittree.dansmarue.unit.resourceType.label.sector.management";

    // Start of user code for specific constants

    // End of user code for specific constants
    /**
     * Constructor.
     */
    public SectorResourceIdService( )
    {
        setPluginName( "unittree-dansmarue" );
    }

    /**
     * Resource register.
     */
    @Override
    public void register( )
    {
        ResourceType rt = new ResourceType( );
        rt.setResourceIdServiceClass( SectorResourceIdService.class.getName( ) );
        rt.setPluginName( "unittree-dansmarue" );
        rt.setResourceTypeKey( KEY_ID_RESOURCE );
        rt.setResourceTypeLabelKey( PROPERTY_LABEL_RESOURCE_TYPE );

        Permission p = null;

        p = new Permission( );
        p.setPermissionKey( PERMISSION_DELETE_SECTOR );
        p.setPermissionTitleKey( "module.unittree.dansmarue.unit.permission.label.sector.management.delete.sector" );
        rt.registerPermission( p );

        ResourceTypeManager.registerResourceType( rt );
    }

    /**
     * Gets the resource id list.
     *
     * @param locale
     *            the current local
     * @return null
     */
    @Override
    public ReferenceList getResourceIdList( Locale locale )
    {
        return null;
    }

    /**
     * Gets the title.
     *
     * @param strId
     *            the resource Id
     * @param locale
     *            the current local
     * @return null
     */
    @Override
    public String getTitle( String strId, Locale locale )
    {
        return null;
    }
}
