ALTER TABLE unittree_sector ALTER COLUMN id_sector TYPE bigint;

INSERT INTO unittree_action (id_action,name_key,description_key,action_url,icon_url,action_permission,action_type) VALUES 
(7,'module.unittree.sira.unit.action.modifySector.name','module.unittree.sira.unit.action.modifySector.description','jsp/admin/plugins/unittree/modules/sira/ModifySector.jsp','images/admin/skin/plugins/unittree/modules/sira/modify_sector.png','MODIFY_SECTOR', 'unittree.unitAction');