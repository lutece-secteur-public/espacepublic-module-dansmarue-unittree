package fr.paris.lutece.plugins.unittree.modules.sira.service.role;

import java.util.Locale;

// Start of user code for imports

import fr.paris.lutece.portal.service.rbac.Permission;
import fr.paris.lutece.portal.service.rbac.ResourceIdService;
import fr.paris.lutece.portal.service.rbac.ResourceType;
import fr.paris.lutece.portal.service.rbac.ResourceTypeManager;
import fr.paris.lutece.util.ReferenceList;


// End of user code for imports

/**
 * SectorResourceIdService
 */
public class SectorResourceIdService extends ResourceIdService
{
    public static final String KEY_ID_RESOURCE = "UNIT_TYPE";
    public static final String PERMISSION_SUPPRIMER_SECTEUR = "MODIFY_SECTOR";

    private static final String PROPERTY_LABEL_RESOURCE_TYPE = "module.unittree.sira.unit.resourceType.label.gestionDesSecteurs";

    // Start of user code for specific constants

    // End of user code for specific constants
    /**
     * Cr�� une nouvelle instance de SectorResourceIdService
     */
    public SectorResourceIdService( )
    {
        setPluginName( "unittree-sira" );
    }

    /**
     * Enregistre les ressources Lutece
     */
    public void register( )
    {
        ResourceType rt = new ResourceType( );
        rt.setResourceIdServiceClass( SectorResourceIdService.class.getName( ) );
        rt.setPluginName( "unittree-sira" );
        rt.setResourceTypeKey( KEY_ID_RESOURCE );
        rt.setResourceTypeLabelKey( PROPERTY_LABEL_RESOURCE_TYPE );

        Permission p = null;

        p = new Permission( );
        p.setPermissionKey( PERMISSION_SUPPRIMER_SECTEUR );
        p.setPermissionTitleKey( "module.unittree.sira.unit.permission.label.gestiondessecteurs.supprimersecteur" );
        rt.registerPermission( p );

        ResourceTypeManager.registerResourceType( rt );
    }

    /**
     * Retourne une liste d'identifiants de resource ou null
     * @param locale La locale courante
     * @return Une liste d'id de resource
     */
    public ReferenceList getResourceIdList( Locale locale )
    {
        return null;
    }

    /**
     * retourne le titre de la resource sp�cifi�e par son ID ou null
     * @param strId L'id de la resource
     * @param locale La locale courante
     * @return Le titre de la resource sp�cifi�e
     */
    public String getTitle( String strId, Locale locale )
    {
        return null;
    }
}
