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
package fr.paris.lutece.plugins.unittree.modules.dansmarue.business.sector;

import java.util.List;

import fr.paris.lutece.plugins.unittree.business.unit.Unit;
import fr.paris.lutece.portal.service.plugin.Plugin;

/**
 * ISectorDAO.
 */
public interface ISectorDAO
{

    /**
     * Load a sector.
     *
     * @param nIdSector
     *            the id sector
     * @param plugin
     *            the plugin
     * @return an instance of {@link Sector}
     */
    Sector load( int nIdSector, Plugin plugin );

    /**
     * Load all sectors.
     *
     * @param plugin
     *            the plugin
     * @return a list of {@link Sector}
     */
    List<Sector> loadAll( Plugin plugin );

    /**
     * Load sectors from a given id unit.
     *
     * @param nIdUnit
     *            the id unit
     * @param plugin
     *            the plugin
     * @return a list of {@link Sector}
     */
    List<Sector> loadByIdUnit( int nIdUnit, Plugin plugin );

    /**
     * Select all the sectors for a unit except the ones linked to a given id example : for the sectors in manage_signalement ,we don't want the garden sector's
     * in the "select" list.
     *
     * @param nIdUnit
     *            the id unit
     * @param nChosenId
     *            the chosen id to avoid
     * @param plugin
     *            the plugin
     * @return a list of sectors
     */
    List<Sector> loadByIdUnitWithoutChosenId( int nIdUnit, int nChosenId, Plugin plugin );

    /**
     * Select all the sectors for a unit except the specific deve's unit.
     *
     * @param nIdUnit
     *            the id unit
     * @param plugin
     *            the plugin
     * @return a list of sectors
     */
    List<Sector> loadByIdUnitWithoutSpecificDeveUnit( int nIdUnit, Plugin plugin );

    /**
     * Load sectors by ids sector.
     *
     * @param sFilter
     *            the filter
     * @param plugin
     *            the plugin
     * @return a list of {@link Sector}
     */
    List<Sector> loadByFilter( SectorFilter sFilter, Plugin plugin );

    /**
     * Load available sectors that are not associated to any unit.
     *
     * @param plugin
     *            the plugin
     * @return a list of {@link Sector}
     */
    List<Sector> loadAvailableSectors( Plugin plugin );

    /**
     * Load units with no children that possesses the given id sector.
     *
     * @param nIdSector
     *            the id sector
     * @param plugin
     *            the plugin
     * @return a list of {@link Unit}
     */
    List<Unit> loadUnitsWithNoChildrenByIdSector( int nIdSector, Plugin plugin );

    /**
     * Add a sector to an unit.
     *
     * @param nIdUnit
     *            the id unit
     * @param nIdSector
     *            the id sector
     * @param plugin
     *            the plugin
     */
    void addSectorToUnit( int nIdUnit, int nIdSector, Plugin plugin );

    /**
     * Check if the given id unit has the given sector.
     *
     * @param nIdUnit
     *            the id unit
     * @param nIdSector
     *            the id sector
     * @param plugin
     *            the plugin
     * @return true if the unit has the sector, false otherwise
     */
    boolean hasSector( int nIdUnit, int nIdSector, Plugin plugin );

    /**
     * Check if the given id unit has sectors.
     *
     * @param nIdUnit
     *            the id unit
     * @param plugin
     *            the plugin
     * @return true if the unit has sectors, false otherwise
     */
    boolean hasSectors( int nIdUnit, Plugin plugin );

    /**
     * Check if the given id sector can be associated to an unit.
     *
     * @param nIdSector
     *            the id sector
     * @param plugin
     *            the plugin
     * @return true if it can be associated, false otherwise
     */
    boolean isAssociated( int nIdSector, Plugin plugin );

    /**
     * Remove sectors from a given id unit.
     *
     * @param nIdUnit
     *            the id unit
     * @param plugin
     *            the plugin
     */
    void removeSectorsFromUnit( int nIdUnit, Plugin plugin );

    /**
     * Remove sectors from a given id unit.
     *
     * @param nIdUnit
     *            the id unit
     * @param nIdSector
     *            the id sector
     * @param plugin
     *            the plugin
     */
    void removeSectorFromUnit( int nIdUnit, int nIdSector, Plugin plugin );

    /**
     * Delete sector from a given id sector.
     *
     * @param nIdSector
     *            the id sector
     * @param plugin
     *            the plugin
     */
    void deleteSector( int nIdSector, Plugin plugin );

    /**
     * Gets the sector by geom and unit id.
     *
     * @param lLng
     *            the lng
     * @param lLat
     *            the lat
     * @param nIdUnit
     *            the id of an unit
     * @param plugin
     *            the plugin
     * @return the sector by geom and unit id.
     */
    Sector getSectorByGeomAndUnit( double lLng, double lLat, int nIdUnit, Plugin plugin );

    /**
     * Gets the sector by geom and type signalement.
     *
     * @param lng
     *            the lng
     * @param lat
     *            the lat
     * @param nIdTypeSignalement
     *            the id type signalement
     * @return the sector by geom and type signalement
     */
    Sector getSectorByGeomAndTypeSignalement( Double lng, Double lat, Integer nIdTypeSignalement );

    /**
     * Gets the sector by geom and type equipement.
     *
     * @param lng
     *            the lng
     * @param lat
     *            the lat
     * @param nIdTypeEquipement
     *            the id type equipement
     * @return the sector by geom and type equipement
     */
    Sector getSectorByGeomAndTypeEquipement( Double lng, Double lat, Integer nIdTypeEquipement );

    /**
     * Gets the sector by geom and type signalement.
     *
     * @param lng
     *            the lng
     * @param lat
     *            the lat
     * @param nIdUnitParent
     *            the unit id of the parent
     * @return the sector by geom and type signalement
     */
    public Sector getSectorByGeomAndIdUnitParent( Double lng, Double lat, Integer nIdUnitParent );

    /**
     * Remove a list of sectors from a list of units.
     *
     * @param listSector
     *            The list of serctors to remove
     * @param unit
     *            The unit to remove the sectors of.
     * @param plugin
     *            The plugin
     */
    void removeListSectorFromUnit( List<Sector> listSector, Unit unit, Plugin plugin );

    /**
     * Finds sectors which are from the given directions, and within a radius of the location.
     *
     * @param lng
     *            Longitude of the location
     * @param lat
     *            Latitude of the location
     * @param nRadius
     *            Radius within the location
     * @param nIdUnits
     *            Id of the unit directions
     * @return List of sectors matching those args
     */
    List<Sector> findSectorsByDirectionsAndGeom( Double lng, Double lat, Integer nRadius, List<Integer> nIdUnits );

    /**
     * Finds a direction from a sector id.
     *
     * @param nIdSector
     *            the sector id linked to the direction unit
     * @return the id of the direction unit
     */
    int getDirectionUnitIdBySectorId( int nIdSector );
}
