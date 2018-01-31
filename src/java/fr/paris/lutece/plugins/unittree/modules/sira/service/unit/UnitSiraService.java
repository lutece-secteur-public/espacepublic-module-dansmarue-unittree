package fr.paris.lutece.plugins.unittree.modules.sira.service.unit;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import fr.paris.lutece.plugins.unittree.business.unit.Unit;
import fr.paris.lutece.plugins.unittree.modules.sira.business.unit.IUnitSiraDAO;

public class UnitSiraService implements IUnitSiraService

{
    @Autowired
    private IUnitSiraDAO _dao;

    @Override
    public List<Unit> getUnitsLeafsByGeom( Double lng, Double lat )
    {
        return _dao.getUnitsLeafsByGeom( lng, lat );
    }

    @Override
    public Unit findUnitByGeomAndTypeSignalement( Double lng, Double lat, Integer typeSignalementId )
    {
        return _dao.findUnitByGeomAndTypeSignalement( lng, lat, ( long ) typeSignalementId );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Unit> findUnitsByGeom( Double lng, Double lat, Integer radius )
    {
        return _dao.findUnitsByGeom( lng, lat, radius );
    }

    @Override
    public Unit findUnitByGeomAndTypeEquipement( Double lng, Double lat, Integer typeEquipementId )
    {
        return _dao.findUnitByGeomAndTypeEquipement( lng, lat, ( long ) typeEquipementId );
    }

}
