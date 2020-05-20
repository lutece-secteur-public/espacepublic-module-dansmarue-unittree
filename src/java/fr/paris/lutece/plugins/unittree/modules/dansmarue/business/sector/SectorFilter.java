/*
 * Copyright (c) 2002-2020, City of Paris
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * SectorFilter.
 */
public class SectorFilter implements Serializable
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 796728974399590486L;

    /** The str sector name. */
    private String            _strSectorName;

    /** The str sector num. */
    private String            _strSectorNum;

    /** The list ids sector. */
    private List<Integer>     _listIdsSector;

    /** The b exclude ids sector. */
    private boolean           _bExcludeIdsSector;

    /**
     * Constructors.
     */
    public SectorFilter( )
    {
        _strSectorName = StringUtils.EMPTY;
        _bExcludeIdsSector = false;
    }

    /**
     * Get the sector name.
     *
     * @return the sector name
     */
    public String getSectorName( )
    {
        return _strSectorName;
    }

    /**
     * Set the sector name.
     *
     * @param strSectorName
     *            the sector name
     */
    public void setSectorName( String strSectorName )
    {
        _strSectorName = strSectorName;
    }

    /**
     * Check if the filter contains the sector name.
     *
     * @return true if it contains, false otherwise
     */
    public boolean containsSectorName( )
    {
        return StringUtils.isNotBlank( _strSectorName );
    }

    /**
     * Get the list of ids sector.
     *
     * @return the list of ids sector
     */
    public List<Integer> getListIdsSector( )
    {
        List<Integer> idsSector = new ArrayList<>( );
        Collections.copy( idsSector, _listIdsSector );
        return idsSector;
    }

    /**
     * Set the list of ids sector.
     *
     * @param listIdsSector
     *            the list of ids sector
     */
    public void setListIdsSector( List<Integer> listIdsSector )
    {
        Collections.copy( _listIdsSector, listIdsSector );
    }

    /**
     * Check if the filter contains the list of ids sector.
     *
     * @return true if it contains, false otherwise
     */
    public boolean containsListIdsSector( )
    {
        return ( _listIdsSector != null ) && !_listIdsSector.isEmpty( );
    }

    /**
     * Set true if it the filter must exclude the ids sector set in {@link #_listIdsSector}.
     *
     * @param bExcludeIdsSector
     *            true if it must exclude, false otherwise
     */
    public void setExcludeIdsSector( boolean bExcludeIdsSector )
    {
        _bExcludeIdsSector = bExcludeIdsSector;
    }

    /**
     * Returns true if it the filter must exclude the ids sector set in {@link #_listIdsSector}.
     *
     * @return true if it must exclude, false otherwise
     */
    public boolean isExcludeIdsSector( )
    {
        return _bExcludeIdsSector;
    }

    /**
     * Set the num sector.
     *
     * @param strSectorNum
     *            the num sector
     */
    public void setSectorNum( String strSectorNum )
    {
        _strSectorNum = strSectorNum;
    }

    /**
     * Get the num sector.
     *
     * @return the num sector
     */
    public String getSectorNum( )
    {
        return _strSectorNum;
    }

    /**
     * Check if the filter contains the num sector.
     *
     * @return true if it contains, false otherwise
     */
    public boolean containsSectorNum( )
    {
        return StringUtils.isNotBlank( _strSectorNum );
    }
}
