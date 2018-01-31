package fr.paris.lutece.plugins.unittree.modules.sira.service.unit;

import java.util.List;

import fr.paris.lutece.plugins.unittree.business.unit.Unit;

public interface IUnitSiraService
{

    Unit findUnitByGeomAndTypeSignalement( Double lng, Double lat, Integer typeSignalementId );

    Unit findUnitByGeomAndTypeEquipement( Double lng, Double lat, Integer typeEquipementId );

    List<Unit> getUnitsLeafsByGeom( Double lng, Double lat );

    /**
     * Search for all units withing a radius from a given location
     * 
     * @param lng
     *            longitude of the location
     * @param lat
     *            latitude of the location
     * @param radius
     *            radius to search from
     * @return Units withing a given radius from a location
     */
    List<Unit> findUnitsByGeom( Double lng, Double lat, Integer radius );

}
