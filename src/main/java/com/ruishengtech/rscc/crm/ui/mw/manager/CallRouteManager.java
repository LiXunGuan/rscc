package com.ruishengtech.rscc.crm.ui.mw.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ruishengtech.rscc.crm.ui.mw.model.MWExtenRoute;
import com.ruishengtech.rscc.crm.ui.mw.send.MwMangaer;
import com.ruishengtech.rscc.crm.ui.mw.service.MWExtenRouteService;

@Component
public class CallRouteManager {

	@Autowired
    private MWExtenRouteService mwExtenRouterService;
	
	private Map<String, MWExtenRoute> routeMap = new HashMap<String, MWExtenRoute>();
	
	public void reloadCallin() {

		MwMangaer.reloadAccessNumber();
	}

	public void reloadRoute() {
		MwMangaer.reloadRoute();
	}

	public Map<String, MWExtenRoute> getRouterMap() {
		
		List<MWExtenRoute> list = mwExtenRouterService.getAll(MWExtenRoute.class);
		
		for (MWExtenRoute mwExtenRouter : list) {
			routeMap.put(mwExtenRouter.getExtension(), mwExtenRouter);
        }
		
		return routeMap;
	}

	public void loadDefaultGateway() {
        MwMangaer.reloadDefaultGateWay();
	}
	
}
