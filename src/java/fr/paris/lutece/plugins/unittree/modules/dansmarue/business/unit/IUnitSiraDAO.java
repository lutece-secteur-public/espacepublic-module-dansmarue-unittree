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

import java.util.List;

/**
 * IUnitSiraDAO.
 */
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
    Unit findUnitByGeomAndTypeSignalement( Double lng, Double lat, Long typeSignalementId );

    /**
     * Gets the unit by unit parent and geom.
     *
     * @param lng
     *            the lng
     * @param lat
     *            the lat
     * @param nIdUnit
     *            the n id unit
     * @return the unit by unit parent and geom
     */
    Unit getUnitByUnitParentAndGeom( Double lng, Double lat, int nIdUnit );

    /**
     * Search for all units withing a radius from a given location.
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

    /**
     * Find unit by geom and type equipement.
     *
     * @param lng
     *            the lng
     * @param lat
     *            the lat
     * @param typeSEquipementId
     *            the type S equipement id
     * @return the unit
     */
    Unit findUnitByGeomAndTypeEquipement( Double lng, Double lat, Long typeSEquipementId );
}
