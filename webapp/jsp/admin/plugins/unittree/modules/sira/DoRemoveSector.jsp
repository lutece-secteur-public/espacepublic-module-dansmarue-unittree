<%@ page errorPage="../../../../ErrorPage.jsp" %>

<jsp:useBean id="unittreeSector" scope="session" class="fr.paris.lutece.plugins.unittree.modules.dansmarue.web.sector.SectorJspBean" />

<% unittreeSector.init( request, fr.paris.lutece.plugins.unittree.web.UnitJspBean.RIGHT_MANAGE_UNITS ); %>
<% response.sendRedirect( unittreeSector.doRemoveSector( request ) ); %>
