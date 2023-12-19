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
package fr.paris.lutece.plugins.unittree.modules.dansmarue.business.unit;

import fr.paris.lutece.plugins.unittree.business.unit.Unit;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * UnitSiraDAO.
 */
public class UnitSiraDAO implements IUnitSiraDAO
{

    /** The Constant SQL_GEOM_VALUE. */
    private static final String SQL_GEOM_VALUE = ")', 4326))";

    /** The Constant SQL_QUERY_SELECTED_VALUES. */
    private static final String SQL_QUERY_SELECTED_VALUES = "SELECT unit.id_unit, unit.id_parent, unit.label, unit.description  FROM unittree_unit AS unit ";

    /** The Constant SQL_QUERY_SELECT_UNIT_BY_GEOM. */
    private static final String SQL_QUERY_SELECT_UNIT_BY_GEOM =
            "SELECT DISTINCT uu.id_unit, uu.id_parent, uu.label, uu.description FROM unittree_unit uu" + " INNER JOIN unittree_unit_sector uus ON uus.id_unit = uu.id_unit"
                    + " INNER JOIN unittree_sector us ON us.id_sector = uus.id_sector" + " WHERE ST_DWithin(ST_Transform(geom,3857),ST_Transform(ST_SetSRID(ST_MakePoint(?,?),4326),3857),?)=true"
                    + " AND uu.id_unit != 0";
    private static final String SQL_QUERY_SELECT_BY_SECTOR    =
            " SELECT uu.id_unit, uu.id_parent, uu.label, uu.description " + " FROM unittree_unit_sector uus INNER JOIN unittree_unit uu ON uus.id_unit = uu.id_unit"
                    + " INNER JOIN unittree_sector us ON us.id_sector = uus.id_sector WHERE us.id_sector = ? ";
    private static final String SQL_ORDER_BY_LABEL_ASC        = " ORDER BY label ASC ";

    /**
     * Find unit by geom and type signalement.
     *
     * @param lng
     *            the lng
     * @param lat
     *            the lat
     * @param typeSignalementId
     *            the type signalement id
     * @return the unit
     */
    @Override
    public Unit findUnitByGeomAndTypeSignalement( Double lng, Double lat, Long typeSignalementId )
    {
        StringBuilder stringBuilder = new StringBuilder( );
        stringBuilder.append( SQL_QUERY_SELECTED_VALUES );
        stringBuilder.append( "INNER JOIN unittree_unit_sector AS unit_sector ON unit_sector.id_unit = unit.id_unit  " );
        stringBuilder.append( "INNER JOIN unittree_sector AS sector ON sector.id_sector = unit_sector.id_sector" );
        stringBuilder.append( " INNER JOIN signalement_type_signalement AS type_signalement ON type_signalement.fk_id_unit = unit.id_unit" );
        stringBuilder.append( " WHERE type_signalement.id_type_signalement = ? " );
        stringBuilder.append( " AND ST_Contains(sector.geom, ST_GeomFromText('POINT(" );
        stringBuilder.append( lng );
        stringBuilder.append( " " );
        stringBuilder.append( lat );
        stringBuilder.append( SQL_GEOM_VALUE );

        DAOUtil daoUtil = new DAOUtil( stringBuilder.toString( ) );
        int index = 1;
        daoUtil.setLong( index, typeSignalementId );

        return getSingleUnit( daoUtil );
    }

    /**
     * Find unit by geom and type equipement.
     *
     * @param lng
     *            the lng
     * @param lat
     *            the lat
     * @param typeEquipementId
     *            the type equipement id
     * @return the unit
     */
    @Override
    public Unit findUnitByGeomAndTypeEquipement( Double lng, Double lat, Long typeEquipementId )
    {
        StringBuilder stringBuilder = new StringBuilder( );
        stringBuilder.append( SQL_QUERY_SELECTED_VALUES );
        stringBuilder.append( "INNER JOIN unittree_unit_sector AS unit_sector ON unit_sector.id_unit = unit.id_unit  " );
        stringBuilder.append( "INNER JOIN unittree_sector AS sector ON sector.id_sector = unit_sector.id_sector" );
        stringBuilder.append( " INNER JOIN equipement_type_equipement AS type_equipement ON type_equipement.fk_id_unit = unit.id_unit" );
        stringBuilder.append( " WHERE type_equipement.id_type_equipement = ? " );
        stringBuilder.append( " AND ST_Contains(sector.geom, ST_GeomFromText('POINT(" );
        stringBuilder.append( lng );
        stringBuilder.append( " " );
        stringBuilder.append( lat );
        stringBuilder.append( SQL_GEOM_VALUE );

        DAOUtil daoUtil = new DAOUtil( stringBuilder.toString( ) );
        int index = 1;
        daoUtil.setLong( index, typeEquipementId );

        return getSingleUnit( daoUtil );
    }

    /**
     * Gets the single unit.
     *
     * @param daoUtil
     *            the dao util
     * @return the single unit
     */
    private Unit getSingleUnit( DAOUtil daoUtil )
    {
        Unit unit = null;
        int nIndex = 1;
        daoUtil.executeQuery( );

        if ( daoUtil.next( ) )
        {
            nIndex = 1;

            unit = new Unit( );
            unit.setIdUnit( daoUtil.getInt( nIndex++ ) );
            unit.setIdParent( daoUtil.getInt( nIndex++ ) );
            unit.setLabel( daoUtil.getString( nIndex++ ) );
            unit.setDescription( daoUtil.getString( nIndex ) );
        }

        daoUtil.close( );

        return unit;
    }

    /**
     * Gets the unit by unit parent and geom.
     *
     * @param lng
     *            the lng
     * @param lat
     *            the lat
     * @param idUnit
     *            the id unit
     * @return the unit by unit parent and geom
     */
    @Override
    public Unit getUnitByUnitParentAndGeom( Double lng, Double lat, int idUnit )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTED_VALUES + "INNER JOIN unittree_unit_sector AS unit_sector ON unit_sector.id_unit = unit.id_unit "
                + "INNER JOIN unittree_sector AS sector ON sector.id_sector = unit_sector.id_sector "
                + "WHERE unit.id_parent = ? AND ST_Contains(sector.geom, ST_GeomFromText('POINT(" + lng + " " + lat + SQL_GEOM_VALUE );
        daoUtil.setInt( 1, idUnit );

        return getSingleUnit( daoUtil );
    }

    /**
     * Gets the units leafs by geom.
     *
     * @param lng
     *            the lng
     * @param lat
     *            the lat
     * @return the units leafs by geom
     */
    @Override
    public List<Unit> getUnitsLeafsByGeom( Double lng, Double lat )
    {
        DAOUtil daoUtil = new DAOUtil(
                "SELECT DISTINCT unit.id_unit, unit.id_parent, unit.label, unit.description FROM unittree_unit AS unit  INNER JOIN unittree_unit_sector AS unit_sector ON unit_sector.id_unit = unit.id_unit  INNER JOIN unittree_sector AS sector ON sector.id_sector = unit_sector.id_sector  LEFT OUTER JOIN unittree_unit AS son ON son.id_parent = unit.id_unit WHERE ST_Contains(sector.geom, ST_GeomFromText('POINT("
                        + lng + " " + lat + ")', 4326)) AND son.id_parent IS NULL" );

        return loadListUnit( daoUtil );
    }

    /**
     * Load list unit.
     *
     * @param daoUtil
     *            the dao util
     * @return the list
     */
    private List<Unit> loadListUnit( DAOUtil daoUtil )
    {
        daoUtil.executeQuery( );

        List<Unit> listUnits = new ArrayList<>( );

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
    public List<Unit> findUnitsByGeom( Double lng, Double lat, Integer radius )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_UNIT_BY_GEOM );
        int nIndex = 1;
        daoUtil.setDouble( nIndex++, lng );
        daoUtil.setDouble( nIndex++, lat );
        daoUtil.setInt( nIndex, radius );

        daoUtil.executeQuery( );

        List<Unit> listUnits = new ArrayList<>( );

        while ( daoUtil.next( ) )
        {
            nIndex = 1;

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
    public List<Unit> findBySectorId( int nIdSector )
    {
        List<Unit> listUnits = new ArrayList<Unit>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_SECTOR + SQL_ORDER_BY_LABEL_ASC );
        daoUtil.setInt( 1, nIdSector );
        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            int nIndex = 1;
            Unit unit = new Unit( );
            unit.setIdUnit( daoUtil.getInt( nIndex++ ) );
            unit.setIdParent( daoUtil.getInt( nIndex++ ) );
            unit.setLabel( daoUtil.getString( nIndex++ ) );
            unit.setDescription( daoUtil.getString( nIndex++ ) );
            listUnits.add( unit );
        }

        daoUtil.free( );

        return listUnits;
    }
}
