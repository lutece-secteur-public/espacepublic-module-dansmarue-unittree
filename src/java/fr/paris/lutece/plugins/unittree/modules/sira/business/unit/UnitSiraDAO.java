package fr.paris.lutece.plugins.unittree.modules.sira.business.unit;

import java.util.ArrayList;
import java.util.List;

import fr.paris.lutece.plugins.unittree.business.unit.Unit;
import fr.paris.lutece.util.sql.DAOUtil;

public class UnitSiraDAO implements IUnitSiraDAO
{

    private static final String SQL_QUERY_SELECT_UNIT_BY_GEOM = "SELECT DISTINCT uu.id_unit, uu.id_parent, uu.label, uu.description FROM unittree_unit uu"
            + " INNER JOIN unittree_unit_sector uus ON uus.id_unit = uu.id_unit" + " INNER JOIN unittree_sector us ON us.id_sector = uus.id_sector"
            + " WHERE ST_DWithin(ST_Transform(geom,3857),ST_Transform(ST_SetSRID(ST_MakePoint(?,?),4326),3857),?)=true" + " AND uu.id_unit != 0";

    @Override
    public Unit findUnitByGeomAndTypeSignalement( Double lng, Double lat, Long typeSignalementId )
    {
        StringBuilder stringBuilder = new StringBuilder( );
        stringBuilder.append( "SELECT unit.id_unit, unit.id_parent, unit.label, unit.description  FROM unittree_unit AS unit " );
        stringBuilder.append( "INNER JOIN unittree_unit_sector AS unit_sector ON unit_sector.id_unit = unit.id_unit  " );
        stringBuilder.append( "INNER JOIN unittree_sector AS sector ON sector.id_sector = unit_sector.id_sector" );
        stringBuilder.append( " INNER JOIN signalement_type_signalement AS type_signalement ON type_signalement.fk_id_unit = unit.id_unit" );
        stringBuilder.append( " WHERE type_signalement.id_type_signalement = ? " );
        stringBuilder.append( " AND ST_Contains(sector.geom, ST_GeomFromText('POINT(" );
        stringBuilder.append( lng );
        stringBuilder.append( " " );
        stringBuilder.append( lat );
        stringBuilder.append( ")', 4326))" );
        DAOUtil daoUtil = new DAOUtil( stringBuilder.toString( ) );
        int index = 1;
        daoUtil.setLong( index++, typeSignalementId );
        return getSingleUnit( daoUtil );
    }

    @Override
    public Unit findUnitByGeomAndTypeEquipement( Double lng, Double lat, Long typeEquipementId )
    {
        StringBuilder stringBuilder = new StringBuilder( );
        stringBuilder.append( "SELECT unit.id_unit, unit.id_parent, unit.label, unit.description  FROM unittree_unit AS unit " );
        stringBuilder.append( "INNER JOIN unittree_unit_sector AS unit_sector ON unit_sector.id_unit = unit.id_unit  " );
        stringBuilder.append( "INNER JOIN unittree_sector AS sector ON sector.id_sector = unit_sector.id_sector" );
        stringBuilder.append( " INNER JOIN equipement_type_equipement AS type_equipement ON type_equipement.fk_id_unit = unit.id_unit" );
        stringBuilder.append( " WHERE type_equipement.id_type_equipement = ? " );
        stringBuilder.append( " AND ST_Contains(sector.geom, ST_GeomFromText('POINT(" );
        stringBuilder.append( lng );
        stringBuilder.append( " " );
        stringBuilder.append( lat );
        stringBuilder.append( ")', 4326))" );
        DAOUtil daoUtil = new DAOUtil( stringBuilder.toString( ) );
        int index = 1;
        daoUtil.setLong( index++, typeEquipementId );
        return getSingleUnit( daoUtil );
    }

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

        daoUtil.free( );

        return unit;
    }

    @Override
    public Unit getUnitByUnitParentAndGeom( Double lng, Double lat, int idUnit )
    {
        DAOUtil daoUtil = new DAOUtil( "SELECT unit.id_unit, unit.id_parent, unit.label, unit.description  FROM unittree_unit AS unit "
                + "INNER JOIN unittree_unit_sector AS unit_sector ON unit_sector.id_unit = unit.id_unit " + "INNER JOIN unittree_sector AS sector ON sector.id_sector = unit_sector.id_sector "
                + "WHERE unit.id_parent = ? AND ST_Contains(sector.geom, ST_GeomFromText('POINT(" + lng + " " + lat + ")', 4326))" );
        daoUtil.setInt( 1, idUnit );
        return getSingleUnit( daoUtil );
    }

    @Override
    public List<Unit> getUnitsLeafsByGeom( Double lng, Double lat )
    {
        DAOUtil daoUtil = new DAOUtil(
                "SELECT DISTINCT unit.id_unit, unit.id_parent, unit.label, unit.description FROM unittree_unit AS unit  INNER JOIN unittree_unit_sector AS unit_sector ON unit_sector.id_unit = unit.id_unit  INNER JOIN unittree_sector AS sector ON sector.id_sector = unit_sector.id_sector  LEFT OUTER JOIN unittree_unit AS son ON son.id_parent = unit.id_unit WHERE ST_Contains(sector.geom, ST_GeomFromText('POINT("
                        + lng + " " + lat + ")', 4326)) AND son.id_parent IS NULL" );
        return loadListUnit( daoUtil );
    }

    private List<Unit> loadListUnit( DAOUtil daoUtil )
    {
        daoUtil.executeQuery( );

        List<Unit> listUnits = new ArrayList<Unit>( );
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

        daoUtil.free( );

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
        daoUtil.setInt( nIndex++, radius );

        daoUtil.executeQuery( );

        List<Unit> listUnits = new ArrayList<Unit>( );
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

        daoUtil.free( );
        return listUnits;
    }

}
