/*
 * Copyright (c) 2002-2021, City of Paris
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
package fr.paris.lutece.plugins.unittree.modules.dansmarue.business.sector;

import java.util.List;

import fr.paris.lutece.plugins.unittree.business.unit.Unit;
import fr.paris.lutece.plugins.unittree.service.UnitTreePlugin;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;

/**
 * SectorHome.
 */
public final class SectorHome
{

    /** The Constant BEAN_SECTOR_DAO. */
    private static final String BEAN_SECTOR_DAO = "unittree-dansmarue.sectorDAO";

    /** The plugin. */
    private static Plugin _plugin = PluginService.getPlugin( UnitTreePlugin.PLUGIN_NAME );

    /** The dao. */
    private static ISectorDAO _dao = SpringContextService.getBean( BEAN_SECTOR_DAO );

    /**
     * Private constructor.
     */
    private SectorHome( )
    {
    }

    /**
     * Find a sector from its primary key.
     *
     * @param nIdSector
     *            the id sector
     * @return an instance of {@link Sector}
     */
    public static Sector findByPrimaryKey( int nIdSector )
    {
        return _dao.load( nIdSector, _plugin );
    }

    /**
     * Find all sectors.
     *
     * @return the sectors
     */
    public static List<Sector> findAll( )
    {
        return _dao.loadAll( _plugin );
    }

    /**
     * Find sectors from a given id unit.
     *
     * @param nIdUnit
     *            the id unit
     * @return a list of {@link Sector}
     */
    public static List<Sector> findByIdUnit( int nIdUnit )
    {
        return _dao.loadByIdUnit( nIdUnit, _plugin );
    }

    /**
     * Find sectors from a given id unit.
     *
     * @param sFilter
     *            the filter
     * @return a list of {@link Sector}
     */
    public static List<Sector> findByFilter( SectorFilter sFilter )
    {
        return _dao.loadByFilter( sFilter, _plugin );
    }

    /**
     * Load available sectors that are not associated to any unit.
     *
     * @return a list of {@link Sector}
     */
    public static List<Sector> findAvailableSectors( )
    {
        return _dao.loadAvailableSectors( _plugin );
    }

    /**
     * Load units with no children that possesses the given id sector.
     *
     * @param nIdSector
     *            the id sector
     * @return a list of {@link Unit}
     */
    public static List<Unit> findUnitsWithNoChildrenByIdSector( int nIdSector )
    {
        return _dao.loadUnitsWithNoChildrenByIdSector( nIdSector, _plugin );
    }

    /**
     * Add a sector to an unit.
     *
     * @param nIdUnit
     *            the id unit
     * @param nIdSector
     *            the id sector
     */
    public static void addSectorToUnit( int nIdUnit, int nIdSector )
    {
        _dao.addSectorToUnit( nIdUnit, nIdSector, _plugin );
    }

    /**
     * Check if the unit has a sector.
     *
     * @param nIdUnit
     *            the id unit
     * @param nIdSector
     *            the id sector
     * @return true if the unit has the sector, false otherwise
     */
    public static boolean hasSector( int nIdUnit, int nIdSector )
    {
        return _dao.hasSector( nIdUnit, nIdSector, _plugin );
    }

    /**
     * Check if the unit has a sectors.
     *
     * @param nIdUnit
     *            the id unit
     * @return true if the unit has sectors, false otherwise
     */
    public static boolean hasSectors( int nIdUnit )
    {
        return _dao.hasSectors( nIdUnit, _plugin );
    }

    /**
     * Check if the given id sector can be associated to an unit.
     *
     * @param nIdSector
     *            the id sector
     * @return true if it can be associated, false otherwise
     */
    public static boolean isAssociated( int nIdSector )
    {
        return _dao.isAssociated( nIdSector, _plugin );
    }

    /**
     * Remove the sectors from an unit.
     *
     * @param nIdUnit
     *            the id unit
     */
    public static void removeSectorsFromUnit( int nIdUnit )
    {
        _dao.removeSectorsFromUnit( nIdUnit, _plugin );
    }

    /**
     * Remove the sectors from an unit.
     *
     * @param nIdUnit
     *            the id unit
     * @param nIdSector
     *            the id sector
     */
    public static void removeSectorFromUnit( int nIdUnit, int nIdSector )
    {
        _dao.removeSectorFromUnit( nIdUnit, nIdSector, _plugin );
    }

    /**
     * Remove a list of sectors from a list of units.
     *
     * @param listSector
     *            The list of serctors to remove
     * @param unit
     *            The unit to remove the sectors of.
     */
    public static void removeListSectorFromUnit( List<Sector> listSector, Unit unit )
    {
        if ( ( listSector != null ) && ( !listSector.isEmpty( ) ) )
        {
            _dao.removeListSectorFromUnit( listSector, unit, _plugin );
        }
    }

    /**
     * Delete the sector.
     *
     * @param nIdSector
     *            the idSector
     */
    public static void deleteSector( int nIdSector )
    {
        _dao.deleteSector( nIdSector, _plugin );
    }

    /**
     * Gets the sector by geom and unit id.
     *
     * @param lLng
     *            the lng
     * @param lLat
     *            the lat
     * @param nIdUnit
     *            the id of an unit
     * @return the sector by geom and unit id.
     */
    public static Sector getSectorByGeomAndUnit( double lLng, double lLat, int nIdUnit )
    {
        return _dao.getSectorByGeomAndUnit( lLng, lLat, nIdUnit, _plugin );
    }

    /**
     * Select all the sectors for a unit except the ones linked to a given id example : for the sectors in manage_signalement ,we don't want the garden sector's
     * in the "select" list.
     *
     * @param nIdUnit
     *            the idUnit
     * @param nChosenId
     *            the chosenId
     * @return a list of sectors
     */
    public static List<Sector> loadByIdUnitWithoutChosenId( int nIdUnit, int nChosenId )
    {
        return _dao.loadByIdUnitWithoutChosenId( nIdUnit, nChosenId, _plugin );
    }

    /**
     * Select all the sectors for a unit except specific deve unit.
     *
     * @param nIdUnit
     *            the id unit
     * @return a list of sectors
     */
    public static List<Sector> loadByIdUnitWithoutSpecificDeveUnits( int nIdUnit )
    {
        return _dao.loadByIdUnitWithoutSpecificDeveUnit( nIdUnit, _plugin );
    }

    /**
     * Finds sectors which are from the given directions, and within a radius of the location.
     *
     * @param lng
     *            Longitude of the location
     * @param lat
     *            Latitude of the location
     * @param radius
     *            Radius within the location
     * @param idUnits
     *            Id of the unit directions
     * @return List of sectors matching those args
     */
    public static List<Sector> findSectorsByDirectionsAndGeom( Double lng, Double lat, Integer radius, List<Integer> idUnits )
    {
        return _dao.findSectorsByDirectionsAndGeom( lng, lat, radius, idUnits );
    }
}
