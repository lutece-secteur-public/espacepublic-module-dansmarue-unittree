<%@ page errorPage="../../../../ErrorPage.jsp" %>
<%@page import="fr.paris.lutece.plugins.unittree.modules.sira.service.role.SectorResourceIdService"%>

<jsp:include page="../../../../AdminHeader.jsp" />
<jsp:useBean id="unittreeSector" scope="session" class="fr.paris.lutece.plugins.unittree.modules.sira.web.sector.SectorJspBean" />

<% unittreeSector.init( request, fr.paris.lutece.plugins.unittree.web.UnitJspBean.RIGHT_MANAGE_UNITS ); %>
<%= unittreeSector.getModifySectors( request ) %>

<%@ include file="../../../../AdminFooter.jsp" %>