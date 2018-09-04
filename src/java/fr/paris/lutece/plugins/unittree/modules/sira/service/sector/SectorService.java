/*
 * Copyright (c) 2002-2012, Mairie de Paris
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
package fr.paris.lutece.plugins.unittree.modules.sira.service.sector;

import fr.paris.lutece.plugins.unittree.business.unit.IUnitAttribute;
import fr.paris.lutece.plugins.unittree.business.unit.Unit;
import fr.paris.lutece.plugins.unittree.modules.sira.business.sector.Sector;
import fr.paris.lutece.plugins.unittree.modules.sira.business.sector.SectorFilter;
import fr.paris.lutece.plugins.unittree.modules.sira.business.sector.SectorHome;
import fr.paris.lutece.plugins.unittree.modules.sira.business.sector.SectorUnitAttribute;
import fr.paris.lutece.plugins.unittree.service.UnitErrorException;
import fr.paris.lutece.plugins.unittree.service.unit.IUnitService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

import org.apache.commons.lang.StringUtils;

import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import javax.servlet.http.HttpServletRequest;


/**
 *
 * SectorService
 *
 */
public class SectorService implements ISectorService
{
    // PARAMETERS
    private static final String PARAMETER_ID_SECTOR = "idSector";

    // PROPERTIES
    private static final String PROPERTY_SECTOR_UNIT_LINK_MULTIPLE = "unittree-sira.sector.unitLink.multiple";
    @Inject
    private IUnitService _unitService;

    // GET

    /**
     * {@inheritDoc}
     */
    @Override
    public Sector findByPrimaryKey( int nIdSector )
    {
        return SectorHome.findByPrimaryKey( nIdSector );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Sector> findAll(  )
    {
        return SectorHome.findAll(  );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Sector> findByIdUnit( int nIdUnit )
    {
        return SectorHome.findByIdUnit( nIdUnit );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Sector> findByFilter( SectorFilter sFilter )
    {
        return SectorHome.findByFilter( sFilter );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Sector> findAvailableSectors( SectorFilter sFilter, int nIdUnit )
    {
        if ( isSectorUnitLinkOneToMany(  ) )
        {
            return buildListAvailableSectors( sFilter, nIdUnit );
        }

        return SectorHome.findAvailableSectors(  );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Unit> findUnitsWithNoChildrenByIdSector( int nIdSector )
    {
        return SectorHome.findUnitsWithNoChildrenByIdSector( nIdSector );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Integer> getIdsSectorByIdUnit( int nIdUnit )
    {
        List<Integer> listIdsSector = new ArrayList<Integer>(  );

        for ( Sector sector : findByIdUnit( nIdUnit ) )
        {
            if ( sector != null )
            {
                listIdsSector.add( sector.getIdSector(  ) );
            }
        }

        return listIdsSector;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Integer> getIdsSectorFromUnit( Unit unit )
    {
        IUnitAttribute<List<Integer>> sectorUnitAttribute = unit.getAttribute( SectorUnitAttribute.ATTRIBUTE_NAME );

        if ( sectorUnitAttribute != null )
        {
            List<Integer> listIdsSector = sectorUnitAttribute.getAttribute(  );

            if ( listIdsSector != null )
            {
                return listIdsSector;
            }
        }

        // Do not return null to avoid null pointer exceptions
        return new ArrayList<Integer>(  );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sector getSectorByGeomAndUnit( double lLng, double lLat, int nIdUnit )
    {
        return SectorHome.getSectorByGeomAndUnit( lLng, lLat, nIdUnit );
    }

    // CHECKS

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasSector( int nIdUnit, int nIdSector )
    {
        return SectorHome.hasSector( nIdUnit, nIdSector );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasSectors( int nIdUnit )
    {
        return SectorHome.hasSectors( nIdUnit );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canCreateSubUnit( int nIdUnit )
    {
        return !hasSectors( nIdUnit );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canAddSector( int nIdUnit, int nIdSector )
    {
        if ( isSectorUnitLinkOneToMany(  ) )
        {
            return !hasSector( nIdUnit, nIdSector );
        }

        for ( Unit subUnit : _unitService.getSubUnits( nIdUnit, true ) )
        {
            if ( hasSector( subUnit.getIdUnit(  ), nIdSector ) )
            {
                return true;
            }
        }

        return !SectorHome.isAssociated( nIdSector );
    }

    // DO

    /**
     * {@inheritDoc}
     */
    @Override
    public void doCreateUnit( Unit unit, HttpServletRequest request )
    {
        // Nothing to do
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doModifyUnit( Unit unit, HttpServletRequest request )
    {
        // Nothing to do
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doRemoveUnit( int nIdUnit, HttpServletRequest request )
    {
        // Remove sectors
        removeSectorsFromUnit( nIdUnit );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void populate( Unit unit )
    {
        List<Integer> listIdsSector = getIdsSectorByIdUnit( unit.getIdUnit(  ) );
        IUnitAttribute<List<Integer>> sectorUnitAttribute = new SectorUnitAttribute(  );
        sectorUnitAttribute.setAttribute( listIdsSector );
        unit.addAttribute( sectorUnitAttribute );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void populate( Unit unit, HttpServletRequest request )
        throws UnitErrorException
    {
        // Ids sector
        String[] strIdsSectors = request.getParameterValues( PARAMETER_ID_SECTOR );

        if ( ( strIdsSectors != null ) && ( strIdsSectors.length > 0 ) )
        {
            IUnitAttribute<List<Integer>> sectorUnitAttribute = new SectorUnitAttribute(  );
            List<Integer> listIdsSector = new ArrayList<Integer>(  );

            for ( String strIdSector : strIdsSectors )
            {
                if ( StringUtils.isNotBlank( strIdSector ) && StringUtils.isNumeric( strIdSector ) )
                {
                    int nIdSector = Integer.parseInt( strIdSector );
                    listIdsSector.add( nIdSector );
                }
            }

            sectorUnitAttribute.setAttribute( listIdsSector );
            unit.addAttribute( sectorUnitAttribute );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Sector> buildListAssociatedSectors( int nIdUnit )
    {
        // Fetch the associated sectors
        List<Integer> listIdsSector = getIdsSectorByIdUnit( nIdUnit );

        if ( ( listIdsSector != null ) && !listIdsSector.isEmpty(  ) )
        {
            SectorFilter sFilter = new SectorFilter(  );
            sFilter.setListIdsSector( listIdsSector );

            return findByFilter( sFilter );
        }

        return new ArrayList<Sector>(  );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Sector> buildListAvailableSectors( SectorFilter sFilter, int nIdUnit )
    {
        // Fetch the associated sectors
        sFilter.setListIdsSector( getIdsSectorByIdUnit( nIdUnit ) );
        sFilter.setExcludeIdsSector( true );

        return findByFilter( sFilter );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, List<Unit>> buildMapIdsSectorUnits( int nIdUnit, List<Sector> listSectors )
    {
        Unit unit = _unitService.getUnit( nIdUnit, false );
        Map<String, List<Unit>> map = new HashMap<String, List<Unit>>(  );

        if ( unit != null )
        {
            for ( Sector sector : listSectors )
            {
                List<Unit> listSubUnits = map.get( Integer.toString( sector.getIdSector(  ) ) );

                if ( listSubUnits == null )
                {
                    listSubUnits = new ArrayList<Unit>(  );
                }

                for ( Unit subUnit : findUnitsWithNoChildrenByIdSector( sector.getIdSector(  ) ) )
                {
                    if ( _unitService.isParent( unit, subUnit ) )
                    {
                        listSubUnits.add( subUnit );
                    }
                }

                map.put( Integer.toString( sector.getIdSector(  ) ), listSubUnits );
            }
        }

        return map;
    }

    // CRUD

    /**
     * {@inheritDoc}
     */
    @Override
    public void addSectorsToUnit( Unit unit )
    {
        if ( unit != null )
        {
            List<Integer> listIdsSectors = getIdsSectorFromUnit( unit );

            if ( ( listIdsSectors != null ) && !listIdsSectors.isEmpty(  ) )
            {
                // First create the link unit-sector to the current unit
                addSectorsToUnit( unit, listIdsSectors );

                // Then add the link to the unit parent
                Unit unitParent = _unitService.getUnit( unit.getIdParent(  ), true );

                while ( ( unitParent != null ) && ( unitParent.getIdUnit(  ) != Unit.ID_NULL ) )
                {
                    // Build the list of ids to add : the sector must not be added twice
                    List<Integer> listIdsToAdd = listIdsSectors;
                    listIdsToAdd.removeAll( getIdsSectorFromUnit( unitParent ) );
                    addSectorsToUnit( unitParent, listIdsToAdd );

                    unitParent = _unitService.getUnit( unitParent.getIdParent(  ), true );
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional( "unittree.transactionManager" )
    public void addSectorsToUnit( Unit unit, List<Integer> listIdsSector )
    {
        if ( ( unit != null ) && ( listIdsSector != null ) && !listIdsSector.isEmpty(  ) )
        {
            for ( int nIdSector : listIdsSector )
            {
                if ( canAddSector( unit.getIdUnit(  ), nIdSector ) )
                {
                    SectorHome.addSectorToUnit( unit.getIdUnit(  ), nIdSector );
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional( "unittree.transactionManager" )
    public void removeSectorsFromUnit( int nIdUnit )
    {
        List<Integer> listIdsSector = getIdsSectorByIdUnit( nIdUnit );

        if ( ( listIdsSector != null ) && !listIdsSector.isEmpty(  ) )
        {
            for ( int nIdSector : listIdsSector )
            {
                removeSectorFromUnit( nIdUnit, nIdSector );
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional( "unittree.transactionManager" )
    public void removeSectorFromUnit( int nIdUnit, int nIdSector )
    {
        Unit unit = _unitService.getUnit( nIdUnit, false );

        if ( unit != null )
        {
            // First remove the sectors from the parent unit
            Unit unitParent = _unitService.getUnit( unit.getIdParent(  ), false );

            while ( ( unitParent != null ) && ( unitParent.getIdUnit(  ) != Unit.ID_NULL ) )
            {
                if ( !hasSectorInSubUnit( unitParent, nIdSector, unit ) )
                {
                    SectorHome.removeSectorFromUnit( unitParent.getIdUnit(  ), nIdSector );
                }

                unitParent = _unitService.getUnit( unitParent.getIdParent(  ), false );
            }

            // Then remove the sectors from the unit
            SectorHome.removeSectorFromUnit( nIdUnit, nIdSector );
        }
    }

    @Override
    @Transactional( "unittree.transactionManager" )
    public void deleteSector( int nIdSector )
    {
        SectorHome.deleteSector( nIdSector );
    }

    // PRIVATE METHODS

    /**
     * Check if the given unit parent has the given id sector in a sub unit, excluding the given unitToExclude
     *
     * @param unitParent
     *            the unit parent
     * @param nIdSector
     *            the id sector
     * @param unitToExclude
     *            the unit to exclude
     * @return true if it has a sector in a sub unit, false otherwise
     */
    private boolean hasSectorInSubUnit( Unit unitParent, int nIdSector, Unit unitToExclude )
    {
        for ( Unit subUnit : _unitService.getSubUnits( unitParent.getIdUnit(  ), false ) )
        {
            if ( ( unitToExclude.getIdUnit(  ) != subUnit.getIdUnit(  ) ) &&
                    hasSector( subUnit.getIdUnit(  ), nIdSector ) )
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Check if a sector can be associated to multiple units. <br />
     * The value of this property is set by the property {@link #PROPERTY_SECTOR_UNIT_LINK_MULTIPLE} from the file unittree-sira.properties.
     *
     * @return true if a sector can be associated to multiple units, false otherwise
     */
    private boolean isSectorUnitLinkOneToMany(  )
    {
        return Boolean.valueOf( AppPropertiesService.getProperty( PROPERTY_SECTOR_UNIT_LINK_MULTIPLE ) );
    }

    /**
     * Select all the sectors for a unit except the ones linked to a given id example : for the sectors in manage_signalement ,we don't want the garden sector's in the "select" list
     *
     * @param nIdUnit
     *            the id unit
     * @param nChosenId
     *            the chosen id to avoid
     * @return a list of sectors
     */
    public List<Sector> loadByIdUnitWithoutChosenId( int nIdUnit, int nChosenId )
    {
        return SectorHome.loadByIdUnitWithoutChosenId( nIdUnit, nChosenId );
    }

    /**
     * {@inheritDoc}
     */
    public List<Sector> loadByIdUnitWithoutSpecificDeveUnits( int nIdUnit )
    {
        return SectorHome.loadByIdUnitWithoutSpecificDeveUnits( nIdUnit );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void moveSubTree( Unit unitToMove, Unit newUnitParent )
    {
        Unit parent = _unitService.getUnit( unitToMove.getIdParent(  ), false );
        List<Sector> listSector = SectorHome.findByIdUnit( unitToMove.getIdUnit(  ) );

        // if there is no sector to move
        if ( ( listSector == null ) || ( listSector.isEmpty(  ) ) )
        {
            return;
        }

        // We remove sectors of sub units from old parents
        while ( ( parent.getIdUnit(  ) != newUnitParent.getIdUnit(  ) ) &&
                !_unitService.isParent( parent, newUnitParent ) )
        {
            SectorHome.removeListSectorFromUnit( listSector, parent );
            parent = _unitService.getUnit( parent.getIdParent(  ), false );
        }

        // parent now contain the first common ancestor. This ancestor can be the root of the tree.

        // We add sectors of sub units to new parents
        Unit newParent = newUnitParent;

        while ( newParent.getIdUnit(  ) != parent.getIdUnit(  ) )
        {
            for ( Sector sector : listSector )
            {
                SectorHome.addSectorToUnit( newParent.getIdUnit(  ), sector.getIdSector(  ) );
            }

            newParent = _unitService.getUnit( newParent.getIdParent(  ), false );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Sector> findSectorsByDirectionsAndGeom( Double lng, Double lat, Integer radius, List<Integer> idUnits )
    {
        return SectorHome.findSectorsByDirectionsAndGeom( lng, lat, radius, idUnits );
    }
}
