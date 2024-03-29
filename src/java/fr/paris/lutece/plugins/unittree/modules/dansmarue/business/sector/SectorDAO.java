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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import fr.paris.lutece.plugins.unittree.business.unit.Unit;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

/**
 * SectorDAO.
 */
public class SectorDAO implements ISectorDAO
{

    /** The Constant SQL_QUERY_SELECT. */
    // SQL QUERIES
    private static final String SQL_QUERY_SELECT = " SELECT id_sector, name, number_sector FROM unittree_sector WHERE id_sector = ? ORDER BY name ASC ";

    /** The Constant SQL_QUERY_SELECT_BY_ID_UNIT. */
    private static final String SQL_QUERY_SELECT_BY_ID_UNIT = " SELECT s.id_sector, s.name, s.number_sector "
            + " FROM unittree_sector s INNER JOIN unittree_unit_sector u ON s.id_sector = u.id_sector " + " WHERE u.id_unit = ? ORDER BY s.id_sector ";

    /** The Constant SQL_QUERY_SELECT_ALL. */
    private static final String SQL_QUERY_SELECT_ALL = " SELECT id_sector, name, number_sector FROM unittree_sector ";

    /** The Constant SQL_QUERY_ADD_SECTOR_TO_UNIT. */
    private static final String SQL_QUERY_ADD_SECTOR_TO_UNIT = " INSERT INTO unittree_unit_sector ( id_unit, id_sector ) VALUES ( ?, ? ) ";

    /** The Constant SQL_QUERY_HAS_SECTOR. */
    private static final String SQL_QUERY_HAS_SECTOR = " SELECT id_unit, id_sector FROM unittree_unit_sector WHERE id_unit = ? AND id_sector = ? ";

    /** The Constant SQL_QUERY_HAS_SECTORS. */
    private static final String SQL_QUERY_HAS_SECTORS = " SELECT id_unit, id_sector FROM unittree_unit_sector WHERE id_unit = ? ";

    /** The Constant SQL_QUERY_IS_ASSOCIATED. */
    private static final String SQL_QUERY_IS_ASSOCIATED = " SELECT id_sector FROM unittree_unit_sector WHERE id_sector = ? ";

    /** The Constant SQL_QUERY_REMOVE_SECTORS_FROM_UNIT. */
    private static final String SQL_QUERY_REMOVE_SECTORS_FROM_UNIT = " DELETE FROM unittree_unit_sector WHERE id_unit = ? ";

    /** The Constant SQL_QUERY_REMOVE. */
    private static final String SQL_QUERY_REMOVE = " DELETE FROM unittree_unit_sector WHERE id_unit = ? AND id_sector = ? ";

    /** The Constant SQL_QUERY_SELECT_UNITS_WITH_NO_CHILDREN_BY_ID_SECTOR. */
    private static final String SQL_QUERY_SELECT_UNITS_WITH_NO_CHILDREN_BY_ID_SECTOR = " SELECT uu.id_unit, uu.id_parent, uu.label, uu.description "
            + " FROM unittree_unit AS uu " + " INNER JOIN unittree_unit_sector AS us ON uu.id_unit = us.id_unit "
            + " WHERE uu.id_unit NOT IN(SELECT id_parent FROM unittree_unit) AND us.id_sector = ? ";

    /** The Constant SQL_QUERY_SELECT_AVAILABLE_SECTORS. */
    private static final String SQL_QUERY_SELECT_AVAILABLE_SECTORS = " SELECT s.id_sector, s.name, s.number_sector FROM unittree_sector AS s "
            + " WHERE s.id_sector NOT IN ( SELECT us.id_sector FROM unittree_unit_sector AS us ) ";

    /** The Constant SQL_WHERE. */
    private static final String SQL_WHERE = " WHERE ";

    /** The Constant SQL_NOT. */
    private static final String SQL_NOT = " NOT ";

    /** The Constant SQL_IN. */
    private static final String SQL_IN = " IN ";

    /** The Constant SQL_AND. */
    private static final String SQL_AND = " AND ";

    /** The Constant SQL_LIKE. */
    private static final String SQL_LIKE = " LIKE ";

    /** The Constant SQL_UPPER. */
    private static final String SQL_UPPER = " UPPER ";

    /** The Constant SQL_FILTER_NAME. */
    private static final String SQL_FILTER_NAME = " name ";

    /** The Constant SQL_FILTER_NUMBER_SECTOR. */
    private static final String SQL_FILTER_NUMBER_SECTOR = " number_sector ";

    /** The Constant SQL_FILTER_ID_SECTOR. */
    private static final String SQL_FILTER_ID_SECTOR = " id_sector ";

    /** The Constant SQL_ORDER_BY_NAME_ASC. */
    private static final String SQL_ORDER_BY_NAME_ASC = " ORDER BY name ASC ";

    /** The Constant SQL_QUERY_SELECT_BY_ID_UNIT_WITHOUT_CHOSEN_ID. */
    private static final String SQL_QUERY_SELECT_BY_ID_UNIT_WITHOUT_CHOSEN_ID = "SELECT s.id_sector, s.name, s.number_sector, unit.label FROM unittree_sector s INNER JOIN unittree_unit_sector u ON s.id_sector = u.id_sector  INNER JOIN unittree_unit unit ON u.id_unit = unit.id_unit WHERE s.id_sector NOT IN ( SELECT sector.id_sector FROM unittree_sector sector INNER JOIN unittree_unit_sector unit_sector ON sector.id_sector = unit_sector.id_sector WHERE unit_sector.id_unit = ? ) AND unit.id_unit IN ({0}) ORDER BY s.id_sector ";

    /** The Constant SQL_QUERY_SELECT_BY_ID_UNIT_WITHOUT_SPECIFIC_DEVE_UNIT. */
    private static final String SQL_QUERY_SELECT_BY_ID_UNIT_WITHOUT_SPECIFIC_DEVE_UNIT = "SELECT s.id_sector, s.name, s.number_sector, unit.label FROM unittree_sector s INNER JOIN unittree_unit_sector u ON s.id_sector = u.id_sector  INNER JOIN unittree_unit unit ON u.id_unit = unit.id_unit WHERE s.id_sector NOT IN ( SELECT sector.id_sector FROM unittree_sector sector INNER JOIN unittree_unit_sector unit_sector ON sector.id_sector = unit_sector.id_sector WHERE unit_sector.id_unit = 260 ) AND unit.id_unit = ? ORDER BY s.id_sector ";

    /** The Constant SQL_QUERY_DELETE_SECTOR. */
    private static final String SQL_QUERY_DELETE_SECTOR = "DELETE FROM unittree_sector WHERE id_sector = ? ";

    /** The Constant SQL_QUERY_DELETE_LIST_SECTORS_FROM_LIST_UNITS. */
    private static final String SQL_QUERY_DELETE_LIST_SECTORS_FROM_LIST_UNITS = " DELETE FROM unittree_unit_sector WHERE id_unit = ? AND id_sector IN ( ";

    /** The Constant SQL_QUERY_SELECT_SECTOR_BY_DIRECTION_AND_GEOM. */
    private static final String SQL_QUERY_SELECT_SECTOR_BY_DIRECTION_AND_GEOM = "SELECT DISTINCT us.id_sector, us.name, us.number_sector"
            + " FROM unittree_unit_sector uus" + " INNER JOIN unittree_sector us ON us.id_sector = uus.id_sector"
            + " WHERE ST_DWithin(ST_Transform(geom,3857),ST_Transform(ST_SetSRID(ST_MakePoint(?,?),4326),3857),?)=true" + " AND uus.id_unit IN ({0})";

    /** The Constant SQL_QUERY_SELECT_DIRECTION_ID_BY_SECTOR_ID. */
    private static final String SQL_QUERY_SELECT_DIRECTION_ID_BY_SECTOR_ID = "SELECT unit.id_unit FROM unittree_unit_sector uus INNER JOIN unittree_unit unit ON unit.id_unit = uus.id_unit WHERE id_sector = ? AND unit.id_parent = 0";

    /** The Constant OPEN_BRACKET. */
    // CONSTANTS
    private static final String OPEN_BRACKET = " ( ";

    /** The Constant CLOSED_BRACKET. */
    private static final String CLOSED_BRACKET = " ) ";

    /** The Constant QUESTION_MARK. */
    private static final String QUESTION_MARK = " ? ";

    /** The Constant COMMA. */
    private static final String COMMA = " , ";

    /** The Constant PERCENT. */
    private static final String PERCENT = "%";

    /**
     * {@inheritDoc}
     */
    @Override
    public Sector load( int nIdSector, Plugin plugin )
    {
        Sector sector = null;

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1, nIdSector );
        daoUtil.executeQuery( );

        if ( daoUtil.next( ) )
        {
            int nIndex = 1;

            sector = new Sector( );
            sector.setIdSector( daoUtil.getInt( nIndex++ ) );
            sector.setName( daoUtil.getString( nIndex++ ) );
            sector.setNumberSector( daoUtil.getString( nIndex ) );
        }

        daoUtil.close( );

        return sector;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Sector> loadAll( Plugin plugin )
    {
        List<Sector> listSectors = new ArrayList<>( );

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_ALL + SQL_ORDER_BY_NAME_ASC, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            int nIndex = 1;

            Sector sector = new Sector( );
            sector.setIdSector( daoUtil.getInt( nIndex++ ) );
            sector.setName( daoUtil.getString( nIndex++ ) );
            sector.setNumberSector( daoUtil.getString( nIndex ) );

            listSectors.add( sector );
        }

        daoUtil.close( );

        return listSectors;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Sector> loadByIdUnit( int nIdUnit, Plugin plugin )
    {
        List<Sector> listSectors = new ArrayList<>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_ID_UNIT, plugin );
        daoUtil.setInt( 1, nIdUnit );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            int nIndex = 1;

            Sector sector = new Sector( );
            sector.setIdSector( daoUtil.getInt( nIndex++ ) );
            sector.setName( daoUtil.getString( nIndex++ ) );
            sector.setNumberSector( daoUtil.getString( nIndex ) );
            listSectors.add( sector );
        }

        daoUtil.close( );

        return listSectors;
    }

    /**
     * Select all the sectors for a unit except the ones linked to a given id example : for the sectors in manage_signalement ,we don't want the garden sector's
     * in the "select" list.
     *
     * @param idUnits
     *            the id units
     * @param nChosenId
     *            the n chosen id
     * @param plugin
     *            the plugin
     * @return the list
     */
    @Override
    public List<Sector> loadByIdUnitWithoutChosenId( List<Integer> idUnits , int nChosenId, Plugin plugin )
    {
        List<Sector> listSectors = new ArrayList<>( );

        int listeLength = idUnits.size( );
        Character [ ] array = new Character [ listeLength];

        for ( int i = 0; i < listeLength; i++ )
        {
            array [i] = '?';
        }

        String unionQuery = StringUtils.join( array, COMMA );

        String sqlQuery = MessageFormat.format( SQL_QUERY_SELECT_BY_ID_UNIT_WITHOUT_CHOSEN_ID, unionQuery );
        DAOUtil daoUtil = new DAOUtil( sqlQuery );
        int index = 1;
        daoUtil.setInt( index++ , nChosenId );

        for ( Integer idUnit : idUnits )
        {
            daoUtil.setInt( index++, idUnit );
        }

        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            int nIndex = 1;

            Sector sector = new Sector( );
            sector.setIdSector( daoUtil.getInt( nIndex++ ) );
            sector.setName( daoUtil.getString( nIndex++ ) );
            sector.setNumberSector( daoUtil.getString( nIndex ) );
            listSectors.add( sector );
        }

        daoUtil.close( );

        return listSectors;
    }

    /**
     * Select all the sectors for a unit except the specific unit of DEVE.
     *
     * @param nIdUnit
     *            the n id unit
     * @param plugin
     *            the plugin
     * @return the list
     */
    @Override
    public List<Sector> loadByIdUnitWithoutSpecificDeveUnit( int nIdUnit, Plugin plugin )
    {
        List<Sector> listSectors = new ArrayList<>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_ID_UNIT_WITHOUT_SPECIFIC_DEVE_UNIT );
        daoUtil.setInt( 1, nIdUnit );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            int nIndex = 1;

            Sector sector = new Sector( );
            sector.setIdSector( daoUtil.getInt( nIndex++ ) );
            sector.setName( daoUtil.getString( nIndex++ ) );
            sector.setNumberSector( daoUtil.getString( nIndex ) );
            listSectors.add( sector );
        }

        daoUtil.close( );

        return listSectors;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Sector> loadByFilter( SectorFilter sFilter, Plugin plugin )
    {
        List<Sector> listSectors = new ArrayList<>( );
        DAOUtil daoUtil = new DAOUtil( buildSQLQuery( sFilter ), plugin );
        setFilterValue( sFilter, daoUtil );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            int nIndex = 1;

            Sector sector = new Sector( );
            sector.setIdSector( daoUtil.getInt( nIndex++ ) );
            sector.setName( daoUtil.getString( nIndex++ ) );
            sector.setNumberSector( daoUtil.getString( nIndex ) );
            listSectors.add( sector );
        }

        daoUtil.close( );

        return listSectors;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Sector> loadAvailableSectors( Plugin plugin )
    {
        List<Sector> listSectors = new ArrayList<>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_AVAILABLE_SECTORS, plugin );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            int nIndex = 1;

            Sector sector = new Sector( );
            sector.setIdSector( daoUtil.getInt( nIndex++ ) );
            sector.setName( daoUtil.getString( nIndex++ ) );
            sector.setNumberSector( daoUtil.getString( nIndex ) );
            listSectors.add( sector );
        }

        daoUtil.close( );

        return listSectors;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Unit> loadUnitsWithNoChildrenByIdSector( int nIdSector, Plugin plugin )
    {
        List<Unit> listUnits = new ArrayList<>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_UNITS_WITH_NO_CHILDREN_BY_ID_SECTOR, plugin );
        daoUtil.setInt( 1, nIdSector );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            int nIndex = 1;
            Unit unit = new Unit( );
            unit.setIdUnit( daoUtil.getInt( nIndex++ ) );
            unit.setIdParent( daoUtil.getInt( nIndex++ ) );
            unit.setLabel( daoUtil.getString( nIndex++ ) );
            unit.setDescription( daoUtil.getString( nIndex ) );
            listUnits.add( unit );
        }

        daoUtil.close( );

        return listUnits;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void addSectorToUnit( int nIdUnit, int nIdSector, Plugin plugin )
    {
        int nIndex = 1;

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_ADD_SECTOR_TO_UNIT, plugin );
        daoUtil.setInt( nIndex++, nIdUnit );
        daoUtil.setInt( nIndex, nIdSector );

        daoUtil.executeUpdate( );
        daoUtil.close( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasSector( int nIdUnit, int nIdSector, Plugin plugin )
    {
        boolean bHasSector = false;
        int nIndex = 1;
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_HAS_SECTOR, plugin );
        daoUtil.setInt( nIndex++, nIdUnit );
        daoUtil.setInt( nIndex, nIdSector );
        daoUtil.executeQuery( );

        if ( daoUtil.next( ) )
        {
            bHasSector = true;
        }

        daoUtil.close( );

        return bHasSector;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAssociated( int nIdSector, Plugin plugin )
    {
        boolean bIsAssociated = false;
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_IS_ASSOCIATED, plugin );
        daoUtil.setInt( 1, nIdSector );
        daoUtil.executeQuery( );

        if ( daoUtil.next( ) )
        {
            bIsAssociated = true;
        }

        daoUtil.close( );

        return bIsAssociated;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasSectors( int nIdUnit, Plugin plugin )
    {
        boolean bHasSector = false;
        int nIndex = 1;
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_HAS_SECTORS, plugin );
        daoUtil.setInt( nIndex, nIdUnit );
        daoUtil.executeQuery( );

        if ( daoUtil.next( ) )
        {
            bHasSector = true;
        }

        daoUtil.close( );

        return bHasSector;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeSectorsFromUnit( int nIdUnit, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_REMOVE_SECTORS_FROM_UNIT, plugin );
        daoUtil.setInt( 1, nIdUnit );
        daoUtil.executeUpdate( );
        daoUtil.close( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeSectorFromUnit( int nIdUnit, int nIdSector, Plugin plugin )
    {
        int nIndex = 1;
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_REMOVE, plugin );
        daoUtil.setInt( nIndex++, nIdUnit );
        daoUtil.setInt( nIndex, nIdSector );
        daoUtil.executeUpdate( );
        daoUtil.close( );
    }

    /**
     * Delete sector.
     *
     * @param nIdSector
     *            the n id sector
     * @param plugin
     *            the plugin
     */
    @Override
    public void deleteSector( int nIdSector, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_SECTOR, plugin );
        daoUtil.setInt( 1, nIdSector );
        daoUtil.executeUpdate( );
        daoUtil.close( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sector getSectorByGeomAndUnit( double lLng, double lLat, int nIdUnit, Plugin plugin )
    {
        String sql = "SELECT unittree_sector.id_sector, unittree_sector.\"name\", unittree_sector.number_sector FROM unittree_sector INNER JOIN unittree_unit_sector ON unittree_unit_sector.id_sector = unittree_sector.id_sector INNER JOIN unittree_unit ON unittree_unit.id_unit = unittree_unit_sector.id_unit WHERE ST_Contains(unittree_sector.geom, ST_GeomFromText('POINT("
                + lLng + " " + lLat + ")', 4326)) AND unittree_unit.id_unit = ?";
        DAOUtil daoUtil = new DAOUtil( sql, plugin );
        daoUtil.setInt( 1, nIdUnit );
        daoUtil.executeQuery( );

        Sector sector = null;

        if ( daoUtil.next( ) )
        {
            int nIndex = 1;

            sector = new Sector( );
            sector.setIdSector( daoUtil.getInt( nIndex++ ) );
            sector.setName( daoUtil.getString( nIndex++ ) );
            sector.setNumberSector( daoUtil.getString( nIndex ) );
        }

        daoUtil.close( );

        return sector;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sector getSectorByGeomAndTypeSignalement( Double lng, Double lat, Integer idTypeSignalement )
    {
        DAOUtil daoUtil = new DAOUtil(
                "SELECT unittree_sector.id_sector, unittree_sector.\"name\", unittree_sector.number_sector FROM unittree_sector INNER JOIN unittree_unit_sector ON unittree_unit_sector.id_sector = unittree_sector.id_sector INNER JOIN unittree_unit ON unittree_unit.id_unit = unittree_unit_sector.id_unit INNER JOIN signalement_type_signalement on signalement_type_signalement.fk_id_unit = unittree_unit.id_unit WHERE ST_Contains(unittree_sector.geom, ST_GeomFromText('POINT("
                        + lng + " " + lat + ")', 4326)) AND signalement_type_signalement.id_type_signalement = ?" );
        daoUtil.setInt( 1, idTypeSignalement );
        daoUtil.executeQuery( );

        Sector sector = null;

        if ( daoUtil.next( ) )
        {
            int nIndex = 1;

            sector = new Sector( );
            sector.setIdSector( daoUtil.getInt( nIndex++ ) );
            sector.setName( daoUtil.getString( nIndex++ ) );
            sector.setNumberSector( daoUtil.getString( nIndex ) );
        }

        daoUtil.close( );

        return sector;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sector getSectorByGeomAndTypeEquipement( Double lng, Double lat, Integer idTypeEquipement )
    {
        DAOUtil daoUtil = new DAOUtil(
                "SELECT unittree_sector.id_sector, unittree_sector.\"name\", unittree_sector.number_sector FROM unittree_sector INNER JOIN unittree_unit_sector ON unittree_unit_sector.id_sector = unittree_sector.id_sector INNER JOIN unittree_unit ON unittree_unit.id_unit = unittree_unit_sector.id_unit INNER JOIN equipement_type_equipement on equipement_type_equipement.fk_id_unit = unittree_unit.id_unit WHERE ST_Contains(unittree_sector.geom, ST_GeomFromText('POINT("
                        + lng + " " + lat + ")', 4326)) AND equipement_type_equipement.id_type_equipement = ?" );
        daoUtil.setInt( 1, idTypeEquipement );
        daoUtil.executeQuery( );

        Sector sector = null;

        if ( daoUtil.next( ) )
        {
            int nIndex = 1;

            sector = new Sector( );
            sector.setIdSector( daoUtil.getInt( nIndex++ ) );
            sector.setName( daoUtil.getString( nIndex++ ) );
            sector.setNumberSector( daoUtil.getString( nIndex ) );
        }

        daoUtil.close( );

        return sector;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeListSectorFromUnit( List<Sector> listSector, Unit unit, Plugin plugin )
    {
        StringBuilder sbSql = new StringBuilder( SQL_QUERY_DELETE_LIST_SECTORS_FROM_LIST_UNITS );

        int listSectorSize = listSector.size( ) - 1;
        for ( int nIndexList = 0; nIndexList < listSectorSize; nIndexList++ )
        {
            sbSql.append( QUESTION_MARK );
            sbSql.append( COMMA );
        }

        sbSql.append( QUESTION_MARK );
        sbSql.append( CLOSED_BRACKET );

        DAOUtil daoUtil = new DAOUtil( sbSql.toString( ), plugin );
        int nIndex = 1;
        daoUtil.setInt( nIndex, unit.getIdUnit( ) );

        for ( Sector sector : listSector )
        {
            daoUtil.setInt( ++nIndex, sector.getIdSector( ) );
        }

        daoUtil.executeUpdate( );
        daoUtil.close( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sector getSectorByGeomAndIdUnitParent( Double lng, Double lat, Integer idUnitParent )
    {
        DAOUtil daoUtil = new DAOUtil( "SELECT unittree_sector.id_sector, unittree_sector.\"name\", unittree_sector.number_sector FROM unittree_sector "
                + " INNER JOIN unittree_unit_sector ON unittree_unit_sector.id_sector = unittree_sector.id_sector "
                + " WHERE ST_Contains(unittree_sector.geom, ST_GeomFromText('POINT(" + lng + " " + lat + ")', 4326)) AND unittree_unit_sector.id_unit = ?" );
        daoUtil.setInt( 1, idUnitParent );
        daoUtil.executeQuery( );

        Sector sector = null;

        if ( daoUtil.next( ) )
        {
            int nIndex = 1;

            sector = new Sector( );
            sector.setIdSector( daoUtil.getInt( nIndex++ ) );
            sector.setName( daoUtil.getString( nIndex++ ) );
            sector.setNumberSector( daoUtil.getString( nIndex ) );
        }

        daoUtil.close( );

        return sector;
    }

    /**
     * Build the SQL query.
     *
     * @param sFilter
     *            the filter
     * @return the SQL query
     */
    private String buildSQLQuery( SectorFilter sFilter )
    {
        StringBuilder sbSQL = new StringBuilder( SQL_QUERY_SELECT_ALL );
        int nIndex = 1;

        if ( sFilter.containsListIdsSector( ) )
        {
            nIndex = addSQLWhereOr( sbSQL, nIndex );
            sbSQL.append( SQL_FILTER_ID_SECTOR );

            if ( sFilter.isExcludeIdsSector( ) )
            {
                sbSQL.append( SQL_NOT );
            }

            sbSQL.append( SQL_IN ).append( OPEN_BRACKET );

            int listIdsSectorSize = sFilter.getListIdsSector( ).size( ) - 1;
            for ( int nIndexList = 0; nIndexList < listIdsSectorSize; nIndexList++ )
            {
                sbSQL.append( QUESTION_MARK );
                sbSQL.append( COMMA );
            }

            sbSQL.append( QUESTION_MARK ).append( CLOSED_BRACKET );
        }

        if ( sFilter.containsSectorName( ) )
        {
            nIndex = addSQLWhereOr( sbSQL, nIndex );
            sbSQL.append( SQL_UPPER ).append( OPEN_BRACKET ).append( SQL_FILTER_NAME ).append( CLOSED_BRACKET );
            sbSQL.append( SQL_LIKE );
            sbSQL.append( SQL_UPPER ).append( OPEN_BRACKET ).append( QUESTION_MARK ).append( CLOSED_BRACKET );
        }

        if ( sFilter.containsSectorNum( ) )
        {
            addSQLWhereOr( sbSQL, nIndex );
            sbSQL.append( SQL_UPPER ).append( OPEN_BRACKET ).append( SQL_FILTER_NUMBER_SECTOR ).append( CLOSED_BRACKET );
            sbSQL.append( SQL_LIKE );
            sbSQL.append( SQL_UPPER ).append( OPEN_BRACKET ).append( QUESTION_MARK ).append( CLOSED_BRACKET );
        }

        sbSQL.append( SQL_ORDER_BY_NAME_ASC );

        return sbSQL.toString( );
    }

    /**
     * Set the filter value.
     *
     * @param sFilter
     *            the filter
     * @param daoUtil
     *            the DAOUtil
     */
    private void setFilterValue( SectorFilter sFilter, DAOUtil daoUtil )
    {
        int nIndex = 1;

        if ( sFilter.containsListIdsSector( ) )
        {
            for ( int nIdSector : sFilter.getListIdsSector( ) )
            {
                daoUtil.setInt( nIndex++, nIdSector );
            }
        }

        if ( sFilter.containsSectorName( ) )
        {
            daoUtil.setString( nIndex++, PERCENT + sFilter.getSectorName( ) + PERCENT );
        }

        if ( sFilter.containsSectorNum( ) )
        {
            daoUtil.setString( nIndex, PERCENT + sFilter.getSectorNum( ) + PERCENT );
        }
    }

    /**
     * Add a <b>WHERE</b> or a <b>OR</b> depending of the index. <br/>
     * <ul>
     * <li>if <code>nIndex</code> == 1, then we add a <b>WHERE</b></li>
     * <li>if <code>nIndex</code> != 1, then we add a <b>AND</b></li>
     * </ul>
     *
     * @param sbSQL
     *            the SQL query
     * @param nIndex
     *            the index
     * @return the new index
     */
    private int addSQLWhereOr( StringBuilder sbSQL, int nIndex )
    {
        if ( nIndex == 1 )
        {
            sbSQL.append( SQL_WHERE );
        }
        else
        {
            sbSQL.append( SQL_AND );
        }

        return nIndex + 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Sector> findSectorsByDirectionsAndGeom( Double lng, Double lat, Integer radius, List<Integer> idUnits )
    {
        int listeLength = idUnits.size( );
        Character [ ] array = new Character [ listeLength];

        for ( int i = 0; i < listeLength; i++ )
        {
            array [i] = '?';
        }

        String unionQuery = StringUtils.join( array, COMMA );

        String sqlQuery = MessageFormat.format( SQL_QUERY_SELECT_SECTOR_BY_DIRECTION_AND_GEOM, unionQuery );
        DAOUtil daoUtil = new DAOUtil( sqlQuery );
        int nIndex = 1;
        daoUtil.setDouble( nIndex++, lng );
        daoUtil.setDouble( nIndex++, lat );
        daoUtil.setInt( nIndex++, radius );

        for ( Integer idUnit : idUnits )
        {
            daoUtil.setInt( nIndex++, idUnit );
        }

        daoUtil.executeQuery( );

        List<Sector> sectorList = new ArrayList<>( );

        while ( daoUtil.next( ) )
        {
            nIndex = 1;

            Sector sector = new Sector( );
            sector.setIdSector( daoUtil.getInt( nIndex++ ) );
            sector.setName( daoUtil.getString( nIndex++ ) );
            sector.setNumberSector( daoUtil.getString( nIndex ) );
            sectorList.add( sector );
        }

        daoUtil.close( );

        return sectorList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDirectionUnitIdBySectorId( int idSector )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_DIRECTION_ID_BY_SECTOR_ID );
        int nIndex = 1;
        daoUtil.setInt( nIndex, idSector );
        daoUtil.executeQuery( );

        int idUnit = -1;

        if ( daoUtil.next( ) )
        {
            nIndex = 1;
            idUnit = daoUtil.getInt( nIndex );
        }

        daoUtil.close( );

        return idUnit;
    }
}
