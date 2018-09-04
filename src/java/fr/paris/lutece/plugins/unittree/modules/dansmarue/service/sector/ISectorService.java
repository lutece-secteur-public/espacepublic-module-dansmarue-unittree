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
package fr.paris.lutece.plugins.unittree.modules.dansmarue.service.sector;

import fr.paris.lutece.plugins.unittree.business.unit.Unit;
import fr.paris.lutece.plugins.unittree.modules.dansmarue.business.sector.Sector;
import fr.paris.lutece.plugins.unittree.modules.dansmarue.business.sector.SectorFilter;
import fr.paris.lutece.plugins.unittree.service.unit.IUnitAttributeService;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 *
 * ISectorService
 *
 */
public interface ISectorService extends IUnitAttributeService
{
    /**
     * Find a sector from its primary key
     *
     * @param nIdSector
     *            the id sector
     * @return an instance of {@link Sector}
     */
    Sector findByPrimaryKey( int nIdSector );

    /**
     * find a list of sectors from a given id unit
     *
     * @param nIdUnit
     *            the id unit
     * @return a list of {@link Sector}
     */
    List<Sector> findByIdUnit( int nIdUnit );

    /**
     * Select all the sectors for a unit except the ones linked to a given id example : for the sectors in manage_signalement ,we don't want the garden sector's in the "select" list
     *
     * @param nIdUnit
     *            the id unit
     * @param nChosenId
     *            the chosen id to avoid
     * @return a list of sectors
     */
    List<Sector> loadByIdUnitWithoutChosenId( int nIdUnit, int nChosenId );

    /**
     * Find all sectors
     *
     * @return a list of {@link Sector}
     */
    List<Sector> findAll(  );

    /**
     * Find sectors by ids sector
     *
     * @param sFilter
     *            the filter
     * @return a list of {@link Sector}
     */
    List<Sector> findByFilter( SectorFilter sFilter );

    /**
     * Find the available sectors that are not associated to any unit
     *
     * @param sFilter
     *            the filter
     * @param nIdUnit
     *            the id unit
     * @return a list of {@link Sector}
     */
    List<Sector> findAvailableSectors( SectorFilter sFilter, int nIdUnit );

    /**
     * Load units with no children that possesses the given id sector
     *
     * @param nIdSector
     *            the id sector
     * @return a list of {@link Unit}
     */
    List<Unit> findUnitsWithNoChildrenByIdSector( int nIdSector );

    /**
     * Get the ids sector from a given id unit.
     *
     * This method will fetch the data in the <strong>database</strong>.
     *
     * @param nIdUnit
     *            the id unit
     * @return a list of ids sector
     */
    List<Integer> getIdsSectorByIdUnit( int nIdUnit );

    /**
     * Get the ids sector from the given unit.
     *
     * This method will fetch the data from the additional attributes of the unit
     *
     * @param unit
     *            the unit
     * @return a list of ids sector
     */
    List<Integer> getIdsSectorFromUnit( Unit unit );

    /**
     * Check if the unit has sectors
     *
     * @param nIdUnit
     *            the id unit
     * @return true if the unit has sector, false otherwise
     */
    boolean hasSectors( int nIdUnit );

    /**
     * Check if the unit has the sector
     *
     * @param nIdUnit
     *            the id unit
     * @param nIdSector
     *            the id sector
     * @return true if the unit has the sector, false otherwise
     */
    boolean hasSector( int nIdUnit, int nIdSector );

    /**
     * Check if the we can add the given sector-unit link
     *
     * @param nIdUnit
     *            the id unit
     * @param nIdSector
     *            the id sector
     * @return true if it can be associated, false otherwise
     */
    boolean canAddSector( int nIdUnit, int nIdSector );

    /**
     * Add the sectors to the unit and to its parent
     *
     * @param unit
     *            the unit
     */
    void addSectorsToUnit( Unit unit );

    /**
     * Build the list of associated sectors from a given id unit
     *
     * @param nIdUnit
     *            the id unit
     * @return a list of {@link Sector}
     */
    List<Sector> buildListAssociatedSectors( int nIdUnit );

    /**
     * Build the list of available sectors from a given filter and a given id unit
     *
     * @param sFilter
     *            the filter
     * @param nIdUnit
     *            the id unit
     * @return a list of {@link Sector}
     */
    List<Sector> buildListAvailableSectors( SectorFilter sFilter, int nIdUnit );

    /**
     * Build a map of idsector : list of units
     *
     * This map represents, for a given id sector, the list of lowest level units (those with no children) that are associated to the id sector.
     *
     * @param nIdUnit
     *            the id unit parent
     * @param listSectors
     *            the list of sectors
     * @return a map of idsector : list of units
     */
    Map<String, List<Unit>> buildMapIdsSectorUnits( int nIdUnit, List<Sector> listSectors );

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
    Sector getSectorByGeomAndUnit( double lLng, double lLat, int nIdUnit );

    /**
     * Add sectors to the unit
     *
     * @param unit
     *            the unit
     * @param listIdsSector
     *            the list of ids sector
     */
    @Transactional( "unittree-dansmarue.transactionManager" )
    void addSectorsToUnit( Unit unit, List<Integer> listIdsSector );

    /**
     * Remove the sectors from an unit
     *
     * @param nIdUnit
     *            the id unit
     */
    @Transactional( "unittree-dansmarue.transactionManager" )
    void removeSectorsFromUnit( int nIdUnit );

    /**
     * Remove the sectors from an unit
     *
     * @param nIdUnit
     *            the id unit
     * @param nIdSector
     *            the id sector
     */
    @Transactional( "unittree-dansmarue.transactionManager" )
    void removeSectorFromUnit( int nIdUnit, int nIdSector );

    /**
     * Delete the sector
     *
     * @param nIdSector
     *            the id sector
     */
    @Transactional( "unittree-dansmarue.transactionManager" )
    void deleteSector( int nIdSector );

    /**
     * Select all the sectors for a unit except specific deve unit
     *
     * @param idUnit
     *            the id unit
     * @return a list of sectors
     */
    List<Sector> loadByIdUnitWithoutSpecificDeveUnits( int idUnit );

    /**
     * Finds sectors which are from the given directions, and within a radius of the location
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
    List<Sector> findSectorsByDirectionsAndGeom( Double lng, Double lat, Integer radius, List<Integer> idUnits );
}
