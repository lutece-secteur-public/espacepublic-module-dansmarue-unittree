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
package fr.paris.lutece.plugins.unittree.modules.dansmarue.web.sector;

import fr.paris.lutece.plugins.unittree.modules.dansmarue.business.sector.Sector;
import fr.paris.lutece.plugins.unittree.modules.dansmarue.business.sector.SectorFilter;
import fr.paris.lutece.plugins.unittree.modules.dansmarue.service.sector.ISectorService;
import fr.paris.lutece.plugins.unittree.service.unit.IUnitService;
import fr.paris.lutece.plugins.unittree.web.unit.IUnitAttributeComponent;
import fr.paris.lutece.portal.business.user.AdminUser;
import fr.paris.lutece.portal.service.util.AppLogService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * SectorUnitAttributeComponent.
 */
public class SectorUnitAttributeComponent implements IUnitAttributeComponent
{

    /** The Constant NAME. */
    private static final String NAME = "Sector unit attribute";

    /** The Constant PARAMETER_SEARCH_SECTORS. */
    // PARAMETERS
    private static final String PARAMETER_SEARCH_SECTORS = "searchSectors";

    /** The Constant PARAMETER_ID_UNIT. */
    private static final String PARAMETER_ID_UNIT = "idUnit";

    /** The Constant MARK_LIST_AVAILABLE_SECTORS. */
    // MARKS
    private static final String MARK_LIST_AVAILABLE_SECTORS = "listAvailableSectors";

    /** The Constant MARK_LIST_ASSOCIATED_SECTORS. */
    private static final String MARK_LIST_ASSOCIATED_SECTORS = "listAssociatedSectors";

    /** The Constant MARK_SECTOR_FILTER. */
    private static final String MARK_SECTOR_FILTER = "sectorFilter";

    /** The Constant MARK_HAS_SUB_UNITS. */
    private static final String MARK_HAS_SUB_UNITS = "hasSubUnits";

    /** The Constant MARK_MAP_IDS_SECTOR_UNITS. */
    private static final String MARK_MAP_IDS_SECTOR_UNITS = "mapIdsSectorUnits";

    /** The Constant TEMPLATE_ATTRIBUTE_COMPONENT. */
    // TEMPLATES
    private static final String TEMPLATE_ATTRIBUTE_COMPONENT = "modules/dansmarue/sector_unit_attribute.html";

    /** The unit service. */
    @Inject
    private IUnitService _unitService;

    /** The sector service. */
    @Inject
    private ISectorService _sectorService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void fillModel( HttpServletRequest request, AdminUser user, Map<String, Object> model )
    {
        boolean bHasSubUnits = false;
        int nIdUnit = -1;

        String strIdUnit = request.getParameter( PARAMETER_ID_UNIT );

        if ( StringUtils.isNotBlank( strIdUnit ) && StringUtils.isNumeric( strIdUnit ) )
        {
            nIdUnit = Integer.parseInt( strIdUnit );
            bHasSubUnits = _unitService.hasSubUnits( nIdUnit );
        }

        List<Sector> listAssociatedSectors = _sectorService.buildListAssociatedSectors( nIdUnit );

        // Build the sector filter for search
        SectorFilter sFilter = buildFilter( request );

        model.put( MARK_SECTOR_FILTER, sFilter );
        model.put( MARK_HAS_SUB_UNITS, bHasSubUnits );
        model.put( MARK_LIST_ASSOCIATED_SECTORS, listAssociatedSectors );
        model.put( MARK_MAP_IDS_SECTOR_UNITS, _sectorService.buildMapIdsSectorUnits( nIdUnit, listAssociatedSectors ) );

        if ( !bHasSubUnits )
        {
            model.put( MARK_LIST_AVAILABLE_SECTORS, _sectorService.findAvailableSectors( sFilter, nIdUnit ) );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName( )
    {
        return NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTemplate( )
    {
        return TEMPLATE_ATTRIBUTE_COMPONENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDisplayedInUnitForm( )
    {
        return false;
    }

    // PRIVATE METHODS

    /**
     * Check if user has clicked on the button to search sectors.
     *
     * @param request
     *            the HTTP request
     * @return true if it is a search action, false otherwise
     */
    private boolean isSearch( HttpServletRequest request )
    {
        return StringUtils.isNotBlank( request.getParameter( PARAMETER_SEARCH_SECTORS ) );
    }

    /**
     * Build the filter.
     *
     * @param request
     *            the HTTP request
     * @return a {@link SectorFilter}
     */
    @SuppressWarnings( "unchecked" )
    public SectorFilter buildFilter( HttpServletRequest request )
    {
        SectorFilter sFilter = new SectorFilter( );

        if ( isSearch( request ) )
        {
            try
            {
                BeanUtils.populate( sFilter, request.getParameterMap( ) );
            }
            catch( IllegalAccessException | InvocationTargetException e )
            {
                AppLogService.error( e.getMessage( ), e );
            }
        }

        return sFilter;
    }
}
