package fr.paris.lutece.plugins.unittree.modules.sira.business.unit;

import java.util.List;

import fr.paris.lutece.plugins.unittree.business.unit.Unit;

public interface IUnitSiraDAO
{

    /**
     * Gets the units by sector geom.
     *
     * @param lng
     *            the lng
     * @param lat
     *            the lat
     * @return the units by sector geom
     */
    List<Unit> getUnitsLeafsByGeom( Double lng, Double lat );

    Unit findUnitByGeomAndTypeSignalement( Double lng, Double lat, Long typeSignalementId );

    Unit getUnitByUnitParentAndGeom( Double lng, Double lat, int idUnit );

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

    Unit findUnitByGeomAndTypeEquipement( Double lng, Double lat, Long typeSEquipementId );
}
