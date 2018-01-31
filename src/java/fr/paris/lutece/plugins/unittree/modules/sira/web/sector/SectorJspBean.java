/*
 * Copyright (c) 2002-2012, Mairie de Paris
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
package fr.paris.lutece.plugins.unittree.modules.sira.web.sector;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import fr.paris.lutece.plugins.unittree.business.unit.Unit;
import fr.paris.lutece.plugins.unittree.modules.sira.business.sector.SectorFilter;
import fr.paris.lutece.plugins.unittree.modules.sira.service.role.SectorResourceIdService;
import fr.paris.lutece.plugins.unittree.modules.sira.service.sector.ISectorService;
import fr.paris.lutece.plugins.unittree.service.UnitErrorException;
import fr.paris.lutece.plugins.unittree.service.unit.IUnitService;
import fr.paris.lutece.plugins.unittree.service.unit.UnitResourceIdService;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.rbac.RBACService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.web.admin.PluginAdminPageJspBean;
import fr.paris.lutece.portal.web.constants.Messages;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.url.UrlItem;


/**
 *
 * SectorJspBean
 *
 */
public class SectorJspBean extends PluginAdminPageJspBean
{
    private static final String BEAN_SECTOR_SERVICE = "unittree-sira.sectorService";
    private static final String BEAN_UNIT_SERVICE = "unittree.unitService";

    // MESSAGES
    private static final String MESSAGE_CONFIRMATION_DELETE_TYPE_OBJET = "module.unittree.sira.message.deletesector.confirmation";
    private static final String MESSAGE_TITLE_DELETE_TYPE_OBJET = "module.unittree.sira.messagetitle.deletesector.confirmation";
    private static final String MESSAGE_ERROR = "module.unittree.sira.message.error.erroroccur";
    private static final String MESSAGE_ERROR_UNIT_NOT_FOUND = "module.unittree.sira.message.error.unitNotFound";

    // ANCHOR
    private static final String ANCHOR_ASSOCIATED_SECTORS = "sira-associated-sectors";

    // PARAMETERS
    private static final String PARAMETER_ID_UNIT = "idUnit";
    private static final String PARAMETER_ID_SECTOR = "idSector";

    // JSP
    private static final String JSP_MODIFY_UNIT = "../../ModifyUnit.jsp";
    private static final String JSP_MODIFY_SECTOR = "ModifySector.jsp";
    private static final String JSP_DELETE_SECTOR = "jsp/admin/plugins/unittree/modules/sira/DoDeleteSectors.jsp";
    private static final String TEMPLATE_DELETE_SECTOR = "admin/plugins/unittree/modules/sira/sector_delete.html";
    private ISectorService _sectorService = SpringContextService.getBean( BEAN_SECTOR_SERVICE );
    private IUnitService _unitService = SpringContextService.getBean( BEAN_UNIT_SERVICE );

    //MARKER
    private static final String MARK_LIST_AVAILABLE_SECTORS = "listAvailableSectors";

    /**
     * Do add sectors
     * @param request the HTTP request
     * @return the JSP return
     */
    public String doAddSectors( HttpServletRequest request )
    {
        String strIdUnit = request.getParameter( PARAMETER_ID_UNIT );

        if ( StringUtils.isBlank( strIdUnit ) || !StringUtils.isNumeric( strIdUnit ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        int nIdUnit = Integer.parseInt( strIdUnit );
        Unit unit = _unitService.getUnit( nIdUnit, true );

        if ( unit == null )
        {
            return AdminMessageService.getMessageUrl( request, MESSAGE_ERROR_UNIT_NOT_FOUND, AdminMessage.TYPE_STOP );
        }

        // Check permissions
        if ( !_unitService.isAuthorized( unit, UnitResourceIdService.PERMISSION_MODIFY, getUser(  ) ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.USER_ACCESS_DENIED, AdminMessage.TYPE_STOP );
        }

        try
        {
            _sectorService.populate( unit, request );
        }
        catch ( UnitErrorException e )
        {
            return AdminMessageService.getMessageUrl( request, e.getI18nErrorMessage(  ), AdminMessage.TYPE_STOP );
        }

        _sectorService.addSectorsToUnit( unit );

        UrlItem url = new UrlItem( JSP_MODIFY_UNIT );
        url.addParameter( PARAMETER_ID_UNIT, nIdUnit );

        return url.getUrl(  );
    }

    /**
     * Do remove sectors
     * @param request the HTTP request
     * @return the JSP return
     */
    public String doRemoveSector( HttpServletRequest request )
    {
        String strIdUnit = request.getParameter( PARAMETER_ID_UNIT );

        if ( StringUtils.isBlank( strIdUnit ) || !StringUtils.isNumeric( strIdUnit ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.MANDATORY_FIELDS, AdminMessage.TYPE_STOP );
        }

        int nIdUnit = Integer.parseInt( strIdUnit );
        Unit unit = _unitService.getUnit( nIdUnit, true );

        if ( unit == null )
        {
            return AdminMessageService.getMessageUrl( request, MESSAGE_ERROR_UNIT_NOT_FOUND, AdminMessage.TYPE_STOP );
        }

        // Check permissions
        if ( !_unitService.isAuthorized( unit, UnitResourceIdService.PERMISSION_MODIFY, getUser(  ) ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.USER_ACCESS_DENIED, AdminMessage.TYPE_STOP );
        }

        String strIdSector = request.getParameter( PARAMETER_ID_SECTOR );

        if ( StringUtils.isNotBlank( strIdSector ) && StringUtils.isNumeric( strIdSector ) )
        {
            int nIdSector = Integer.parseInt( strIdSector );
            _sectorService.removeSectorFromUnit( nIdUnit, nIdSector );
        }

        UrlItem url = new UrlItem( JSP_MODIFY_UNIT );
        url.addParameter( PARAMETER_ID_UNIT, nIdUnit );
        url.setAnchor( ANCHOR_ASSOCIATED_SECTORS );

        return url.getUrl(  );
    }

    public String getDeleteSectors( HttpServletRequest request )
    {
        String strJspBack = JSP_MODIFY_SECTOR;
        String strIdSector = request.getParameter( PARAMETER_ID_SECTOR );
        int nIdSector = 0;

        try
        {
            nIdSector = Integer.parseInt( strIdSector );
        }
        catch ( NumberFormatException e )
        {
            return AdminMessageService.getMessageUrl( request, MESSAGE_ERROR,
                    AdminMessage.TYPE_STOP );
        }

        Map<String, Object> urlParam = new HashMap<String, Object>( );
        urlParam.put( PARAMETER_ID_SECTOR, nIdSector );

        return AdminMessageService.getMessageUrl( request, MESSAGE_TITLE_DELETE_TYPE_OBJET, null,
                MESSAGE_CONFIRMATION_DELETE_TYPE_OBJET, JSP_DELETE_SECTOR, "_self", AdminMessage.TYPE_CONFIRMATION,
                urlParam, strJspBack );

    }

    public String doDeleteSectors( HttpServletRequest request )
    {
        String strIdSector = request.getParameter( PARAMETER_ID_SECTOR );

        if ( StringUtils.isNotBlank( strIdSector ) && StringUtils.isNumeric( strIdSector ) )
        {
            int nIdSector = Integer.parseInt( strIdSector );
            _sectorService.deleteSector( nIdSector );
        }

        UrlItem url = new UrlItem( JSP_MODIFY_SECTOR );
        url.addParameter( PARAMETER_ID_UNIT, 0 );
        return url.getUrl( );
    }

    public String getModifySectors( HttpServletRequest request )
    {

        Map<String, Object> model = new HashMap<String, Object>( );


        int nIdUnit = -1;

        String strIdUnit = request.getParameter( PARAMETER_ID_UNIT );

        if ( StringUtils.isNotBlank( strIdUnit ) && StringUtils.isNumeric( strIdUnit ) )
        {
            nIdUnit = Integer.parseInt( strIdUnit );
        }

        SectorUnitAttributeComponent sectorUnitAttributeComponent = new SectorUnitAttributeComponent( );
        // Build the sector filter for search
        SectorFilter sFilter = sectorUnitAttributeComponent.buildFilter( request );

        Unit targetUnit = _unitService.getUnit( nIdUnit, false );
        // Check permissions
        if ( !RBACService.isAuthorized( targetUnit, SectorResourceIdService.PERMISSION_SUPPRIMER_SECTEUR, getUser( ) ) )
        {
            return AdminMessageService.getMessageUrl( request, Messages.USER_ACCESS_DENIED, AdminMessage.TYPE_STOP );
        }

        model.put( MARK_LIST_AVAILABLE_SECTORS, _sectorService.findAvailableSectors( sFilter, nIdUnit ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_DELETE_SECTOR, getLocale( ), model );
        return getAdminPage( template.getHtml( ) );

    }

}
