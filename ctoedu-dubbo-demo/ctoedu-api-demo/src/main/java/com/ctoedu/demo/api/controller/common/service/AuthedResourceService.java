package com.ctoedu.demo.api.controller.common.service;

import com.ctoedu.common.entity.enums.AvailableEnum;
import com.ctoedu.demo.facade.app.entity.UmsApp;
import com.ctoedu.demo.facade.resource.entity.UmsControllerResources;
import com.ctoedu.demo.facade.resource.entity.UmsMenuResources;

public class AuthedResourceService {
	
	public static String getMenuResourceLinkedNames(UmsMenuResources umr, String name){
		if(umr != null){
			UmsMenuResources menu = umr.getParent();
			if(menu != null && !(umr.getId().equals(menu.getId())) && AvailableEnum.TRUE.getValue().equals(menu.getIsAvailable())){
				name = "MENU_" + menu.getMenuName() + "," + name;
				name = getMenuResourceLinkedNames(menu, name);
			}else{
				UmsApp app = umr.getApplication();
				if(app == null){
					return null;
				}else{
					name = "APP_" + app.getName() + "," + name;
				}
			}
		}
		return name;
	}
	
	public static String getControllerResourceLinkedNames(UmsControllerResources ucr, String name){
		if(ucr != null){
			UmsControllerResources parent = ucr.getParent();
			if(parent != null && !(ucr.getId().equals(parent.getId())) && AvailableEnum.TRUE.getValue().equals(parent.getIsAvailable())){
				name = "CONTROLLER_" + parent.getControllerName() + "," + name;
				name = getControllerResourceLinkedNames(parent, name);
			}else{
				UmsMenuResources menu = ucr.getMenu();
				if(menu != null){
					name = "MENU_" + menu.getMenuName() + "," + name;
					name = getMenuResourceLinkedNames(menu, name);
				}else{
					UmsApp app = ucr.getApplication();
					if(app == null){
						return null;
					}else{
						name = "APP_" + app.getName() + "," + name;
					}
				}
			}
		}
		return name;
	}
}
