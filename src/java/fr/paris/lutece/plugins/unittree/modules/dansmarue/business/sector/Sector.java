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

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Sector.
 */
@JsonIgnoreProperties( ignoreUnknown = true )
public class Sector
{

    /** The n id sector. */
    private int _nIdSector;

    /** The str name. */
    private String _strName;

    /** The str number sector. */
    private String _strNumberSector;

    /**
     * Get the id sector.
     *
     * @return the id sector
     */
    public int getIdSector( )
    {
        return _nIdSector;
    }

    /**
     * Set the id sector.
     *
     * @param nIdSector
     *            the id sector
     */
    public void setIdSector( int nIdSector )
    {
        _nIdSector = nIdSector;
    }

    /**
     * Get the name.
     *
     * @return the name
     */
    public String getName( )
    {
        return _strName;
    }

    /**
     * Set the name.
     *
     * @param strName
     *            the name
     */
    public void setName( String strName )
    {
        _strName = strName;
    }

    /**
     * Get the number sector.
     *
     * @return the number sector
     */
    public String getNumberSector( )
    {
        return _strNumberSector;
    }

    /**
     * Set the number sector.
     *
     * @param strNumberSector
     *            the number sector
     */
    public void setNumberSector( String strNumberSector )
    {
        _strNumberSector = strNumberSector;
    }

    /**
     * To string representation.
     *
     * @return formatted string
     */
    public String getFormatNameNumber( )
    {
        return _strName + " - " + _strNumberSector;
    }

    /**
     * To string representation (invert of {@link #getFormatNameNumber()}.
     *
     * @return formatted string
     */
    public String getFormatNumberName( )
    {
        return _strNumberSector + " - " + _strName;
    }
}
