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
package fr.paris.lutece.plugins.unittree.modules.dansmarue.service.unit;

import fr.paris.lutece.plugins.unittree.business.unit.Unit;

import java.util.List;

/**
 * The Interface IUnitSiraService.
 */
public interface IUnitSiraService
{

    /**
     * Return the unit by location and signalement type.
     *
     * @param lng
     *            longitude of the location
     * @param lat
     *            latitude of the location
     * @param typeSignalementId
     *            the signalement type id
     * @return Unit by location and signalement type
     */
    Unit findUnitByGeomAndTypeSignalement( Double lng, Double lat, Integer typeSignalementId );

    /**
     * Return the unit by location and equipement type.
     *
     * @param lng
     *            longitude of the location
     * @param lat
     *            latitude of the location
     * @param typeEquipementId
     *            the equipement type id
     * @return Unit by location and equipement type
     */
    Unit findUnitByGeomAndTypeEquipement( Double lng, Double lat, Integer typeEquipementId );

    /**
     * Return a list of Unit by location.
     *
     * @param lng
     *            longitude of the location
     * @param lat
     *            latitude of the location
     * @return Unit list by location
     */
    List<Unit> getUnitsLeafsByGeom( Double lng, Double lat );

    /**
     * Search for all units within a radius from a given location.
     *
     * @param lng
     *            longitude of the location
     * @param lat
     *            latitude of the location
     * @param radius
     *            radius to search from
     * @return Units within a given radius from a location
     */
    List<Unit> findUnitsByGeom( Double lng, Double lat, Integer radius );
}
