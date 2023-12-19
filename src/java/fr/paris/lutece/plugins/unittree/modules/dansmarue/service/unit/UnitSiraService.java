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
import fr.paris.lutece.plugins.unittree.modules.dansmarue.business.unit.IUnitSiraDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * The Class UnitSiraService.
 */
public class UnitSiraService implements IUnitSiraService
{

    /** The dao. */
    @Autowired
    private IUnitSiraDAO _dao;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Unit> getUnitsLeafsByGeom( Double lng, Double lat )
    {
        return _dao.getUnitsLeafsByGeom( lng, lat );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Unit findUnitByGeomAndTypeSignalement( Double lng, Double lat, Integer typeSignalementId )
    {
        return _dao.findUnitByGeomAndTypeSignalement( lng, lat, (long) typeSignalementId );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Unit> findUnitsByGeom( Double lng, Double lat, Integer radius )
    {
        return _dao.findUnitsByGeom( lng, lat, radius );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Unit findUnitByGeomAndTypeEquipement( Double lng, Double lat, Integer typeEquipementId )
    {
        return _dao.findUnitByGeomAndTypeEquipement( lng, lat, ( long ) typeEquipementId );
    }

    /**
     * {@inheritDoc}
     */
    public List<Unit> findBySectorId( int nIdSector )
    {
        return _dao.findBySectorId( nIdSector );
    }
}
